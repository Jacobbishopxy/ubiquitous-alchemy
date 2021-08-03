use actix_identity::Identity;
use actix_web::{web, HttpResponse};
use serde::Deserialize;

use crate::{model::SimpleUser, service::Persistence};

#[derive(Debug, Deserialize)]
pub struct AuthReq {
    pub email: String,
    pub password: String,
}

pub type LoggedUser = SimpleUser;

pub async fn login(
    auth_req: web::Json<AuthReq>,
    id: Identity,
    persistence: web::Data<Persistence>,
) -> HttpResponse {
    todo!()
}

pub async fn logout(id: Identity) -> HttpResponse {
    todo!()
}

pub async fn check_alive(logged_user: LoggedUser) -> HttpResponse {
    todo!()
}
