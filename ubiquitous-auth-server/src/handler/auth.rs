use std::sync::Arc;

use actix_identity::Identity;
use actix_web::{web, HttpResponse, ResponseError};
use serde::Deserialize;

use crate::service::Persistence;
use crate::util::encryption;

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
                    return HttpResponse::Ok().body("Success");
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
        Some(i) => HttpResponse::Ok().body(i),
        None => HttpResponse::Unauthorized().body("unauthorized".to_owned()),
    }
}

pub async fn logout(id: Identity) -> HttpResponse {
    match id.identity() {
        Some(i) => println!("forgetting {:?}", i),
        None => println!("forget failed"),
    }

    id.forget();
    HttpResponse::Found().append_header(("auth", "/")).finish()
}
