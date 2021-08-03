use std::future::Ready;

use actix_identity::Identity;
use actix_web::{dev::Payload, web, FromRequest, HttpRequest, HttpResponse};
use serde::Deserialize;

use crate::{
    error::{ServiceError, ServiceResult},
    model::SimpleUser,
    service::Persistence,
};

#[derive(Debug, Deserialize)]
pub struct AuthReq {
    pub email: String,
    pub password: String,
}

pub type LoggedUser = SimpleUser;

impl FromRequest for LoggedUser {
    type Config = ();

    type Error = ServiceError;

    type Future = Ready<ServiceResult<LoggedUser>>;

    fn from_request(req: &HttpRequest, payload: &mut Payload) -> Self::Future {
        todo!()
    }
}

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
