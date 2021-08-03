use std::{convert::TryInto, sync::Arc};

use actix_web::{App, HttpServer};
use ubiquitous_auth_server::{constant::CFG, service::Persistence};

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    std::env::set_var("RUST_LOG", "actix_server=info,actix_web=info");

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

    let persistence = Persistence::new(&conn).await.expect("Err");
    let persistence = Arc::new(persistence);

    HttpServer::new(move || App::new().app_data(persistence.to_owned()))
        .bind(format!("{}:{}", host, port))?
        .run()
        .await
}
