use std::sync::Arc;

use actix_identity::{CookieIdentityPolicy, IdentityService};
use actix_web::{web, App, HttpServer};

use ubiquitous_auth_server::constant::CONFIG;
use ubiquitous_auth_server::{handler, service};

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    std::env::set_var("RUST_LOG", "actix_server=info,actix_web=info");

    let (host, port) = (CONFIG.service_host.clone(), CONFIG.service_port.clone());

    let persistence = service::Persistence::new()
        .await
        .expect("Persistence connection error");

    persistence
        .initialize(CONFIG.persistence_init)
        .await
        .expect("Table initializing failed");

    let persistence = web::Data::new(Arc::new(persistence));

    HttpServer::new(move || {
        let mut cip = CookieIdentityPolicy::new(CONFIG.secret_key.as_bytes())
            .name("auth")
            .secure(false);
        if CONFIG.cookie_duration_secs != 0 {
            cip = cip.max_age_secs(CONFIG.cookie_duration_secs as i64)
        }

        let ids = IdentityService::new(cip);

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
