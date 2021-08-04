use actix_identity::Identity;
use actix_web::ResponseError;
use actix_web::{dev::Payload, web, FromRequest, HttpRequest, HttpResponse};
use futures::future::{err, ok, Ready};
use serde::Deserialize;

use crate::error::{ServiceError, ServiceResult};
use crate::model::SimpleUser;
use crate::service::Persistence;

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
        if let Ok(identity) = Identity::from_request(req, payload).into_inner() {
            if let Some(user_json) = identity.identity() {
                if let Ok(user) = serde_json::from_str(&user_json) {
                    return ok(user);
                }
            }
        }
        err(ServiceError::Unauthorized("unauthorized".to_owned()).into())
    }
}

pub async fn login(
    auth_req: web::Json<AuthReq>,
    id: Identity,
    persistence: web::Data<Persistence>,
) -> HttpResponse {
    let user = match persistence.get_user_by_email(&auth_req.email).await {
        Ok(op) => op,
        Err(e) => return e.error_response(),
    };
    match user {
        Some(u) => {
            let user_string = serde_json::to_string(&u).unwrap();
            id.remember(user_string);
            HttpResponse::Ok().finish()
        }
        None => HttpResponse::BadRequest().body("Invalid user"),
    }
}

pub async fn logout(id: Identity) -> HttpResponse {
    id.forget();
    HttpResponse::Ok().finish()
}

pub async fn check_alive(logged_user: LoggedUser) -> HttpResponse {
    HttpResponse::Ok().json(logged_user)
}
