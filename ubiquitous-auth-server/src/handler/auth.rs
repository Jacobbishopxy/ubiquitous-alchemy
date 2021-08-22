use std::convert::TryInto;
use std::sync::Arc;

use actix_identity::Identity;
use actix_web::{web, HttpResponse, ResponseError};
use serde::Deserialize;

use crate::error::ServiceError;
use crate::model::{Permission, Role, User};
use crate::service::{encryption, Persistence};

#[derive(Debug, Deserialize)]
pub struct AuthReq {
    pub email: String,
    pub password: String,
}

#[derive(Debug, Deserialize)]
pub struct AlterRoleReq {
    pub email: String,
    pub role: String,
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

pub async fn alter_user_role(
    alter_role_req: web::Json<AlterRoleReq>,
    id: Identity,
    persistence: web::Data<Arc<Persistence>>,
) -> HttpResponse {
    match id.identity() {
        Some(i) => match serde_json::from_str::<User>(&i) {
            Ok(u) => {
                if u.role.is_permitted(Permission::AlterUserRole) {
                    let role: Result<Role, ServiceError> = alter_role_req.role.clone().try_into();
                    let role = match role {
                        Ok(r) => r,
                        Err(e) => return e.error_response(),
                    };
                    match persistence
                        .alter_user_role(&alter_role_req.email, role)
                        .await
                    {
                        Ok(_) => HttpResponse::Accepted().finish(),
                        Err(e) => return e.error_response(),
                    }
                } else {
                    HttpResponse::Unauthorized()
                        .body("you don't have the right to alter user's role".to_owned())
                }
            }
            Err(e) => e.error_response(),
        },
        None => HttpResponse::Unauthorized().body("unauthorized".to_owned()),
    }
}
