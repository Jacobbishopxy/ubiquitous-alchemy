use std::{convert::TryInto, sync::Arc};

use actix_identity::{CookieIdentityPolicy, IdentityService};
use actix_web::{web, App, HttpServer};

use ubiquitous_auth_server::{constant::CFG, handler, service::Persistence};

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    std::env::set_var("RUST_LOG", "actix_server=info,actix_web=info");

    let init_var = init();
    let (host, port) = (init_var.host.clone(), init_var.port.clone());

    let persistence = Persistence::new(&init_var.conn)
        .await
        .expect("Persistence connection error");

    persistence
        .initialize(init_var.init)
        .await
        .expect("Table initializing failed");

    let persistence = web::Data::new(Arc::new(persistence));

    // TODO: config max age
    HttpServer::new(move || {
        let ids = IdentityService::new(
            CookieIdentityPolicy::new(init_var.secret_key.as_bytes())
                .name("auth")
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
    init: bool,
}

fn init() -> InitVar {
    let conn: String = CFG
        .get("DATABASE_URL")
        .unwrap()
        .clone()
        .try_into()
        .expect("DATABASE_URL convert error");
    let host: String = CFG
        .get("SERVICE_HOST")
        .unwrap()
        .clone()
        .try_into()
        .expect("SERVICE_HOST convert error");
    let port: String = CFG
        .get("SERVICE_PORT")
        .unwrap()
        .clone()
        .try_into()
        .expect("SERVICE_PORT convert error");
    let secret_key: String = CFG
        .get("SECRET_KEY")
        .unwrap()
        .clone()
        .try_into()
        .expect("SECRET_KEY convert error");
    let init: bool = CFG
        .get("PERSISTENCE_INIT")
        .unwrap()
        .clone()
        .try_into()
        .expect("PERSISTENCE_INIT convert error");

    InitVar {
        conn,
        host,
        port,
        secret_key,
        init,
    }
}
