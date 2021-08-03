use std::{convert::TryInto, sync::Arc};

use actix_identity::{CookieIdentityPolicy, IdentityService};
use actix_web::{web, App, HttpServer};

use ubiquitous_auth_server::{constant::CFG, handler, service::Persistence};

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    std::env::set_var("RUST_LOG", "actix_server=info,actix_web=info");

    let init_var = init();
    let (host, port) = (init_var.host.clone(), init_var.port.clone());

    let persistence = Persistence::new(&init_var.conn).await.expect("Err");
    let persistence = Arc::new(persistence);

    HttpServer::new(move || {
        let ids = IdentityService::new(
            CookieIdentityPolicy::new(init_var.secret_key.as_bytes())
                .name("auth")
                .path("/")
                .domain(&init_var.domain)
                .max_age_secs(86400 * 30) // 30 days duration
                .secure(false),
        );

        App::new()
            .app_data(persistence.to_owned())
            .wrap(ids)
            .service(
                web::scope("/api")
                    .service(
                        web::resource("/invitation")
                            .route(web::post().to(handler::post_invitation)),
                    )
                    .service(
                        web::resource("/register/{invitation_id}")
                            .route(web::post().to(handler::register_user)),
                    )
                    .service(
                        web::resource("/auth")
                            .route(web::post().to(handler::login))
                            .route(web::delete().to(handler::logout))
                            .route(web::get().to(handler::check_alive)),
                    ),
            )
    })
    .bind(format!("{}:{}", host, port))?
    .run()
    .await
}

#[derive(Clone)]
struct InitVar {
    conn: String,
    host: String,
    port: String,
    secret_key: String,
    domain: String,
}

fn init() -> InitVar {
    let conn: String = CFG
        .get("DATABASE_URL")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");
    let host: String = CFG
        .get("SERVICE_HOST")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");
    let port: String = CFG
        .get("SERVICE_PORT")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");
    let secret_key: String = CFG
        .get("SECRET_KEY")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");
    let domain: String = CFG
        .get("SECURITY_DOMAIN")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");

    InitVar {
        conn,
        host,
        port,
        secret_key,
        domain,
    }
}
