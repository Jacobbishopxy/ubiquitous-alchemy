use std::sync::Arc;

use actix_identity::Identity;
use actix_web::{web, HttpResponse, ResponseError};
use serde::Deserialize;

use crate::model::User;
use crate::service::{encryption, Persistence};

#[derive(Debug, Deserialize)]
pub struct AuthReq {
    pub email: String,
    pub password: String,
}

pub async fn login(
    auth_req: web::Json<AuthReq>,
    id: Identity,
    persistence: web::Data<Arc<Persistence>>,
) -> HttpResponse {
    let user = match persistence.get_user_by_email(&auth_req.email).await {
        Ok(op) => op,
        Err(e) => return e.error_response(),
    };
    if let Some(u) = user {
        match encryption::verify_password(&u.hash, &auth_req.password) {
            Ok(b) => {
                if b {
                    let user_string = serde_json::to_string(&u).unwrap();
                    id.remember(user_string);
                    return HttpResponse::Found().append_header(("auth", "/")).finish();
                } else {
                    return HttpResponse::BadRequest().body("Password incorrect");
                }
            }
            Err(e) => return e.error_response(),
        }
    }

    HttpResponse::BadRequest().body("Invalid user")
}

pub async fn check_alive(id: Identity) -> HttpResponse {
    match id.identity() {
        Some(i) => match serde_json::from_str::<User>(&i) {
            Ok(u) => HttpResponse::Ok().json(u),
            Err(e) => e.error_response(),
        },
        None => HttpResponse::Unauthorized().body("unauthorized".to_owned()),
    }
}

pub async fn logout(id: Identity) -> HttpResponse {
    id.forget();
    HttpResponse::Found().append_header(("auth", "/")).finish()
}
