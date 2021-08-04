use std::sync::Arc;

use actix_web::{web, HttpResponse, ResponseError};
use serde::Deserialize;

use crate::model::User;
use crate::service::Persistence;
use crate::util::encryption;

#[derive(Debug, Deserialize)]
pub struct UserReq {
    pub email: String,
    pub password: String,
}

pub async fn register_user(
    invitation_id: web::Path<String>,
    user_req: web::Json<UserReq>,
    persistence: web::Data<Arc<Persistence>>,
) -> HttpResponse {
    let invitation = match persistence.get_invitation_by_email(&invitation_id).await {
        Ok(op) => op,
        Err(e) => return e.error_response(),
    };
    match invitation {
        Some(inv) => {
            if inv.expires_at > chrono::Local::now().naive_local() {
                let password = match encryption::hash_password(&user_req.0.password) {
                    Ok(pwd) => pwd,
                    Err(e) => return e.error_response(),
                };
                let user = User::from_details(user_req.0.email, password);
                match persistence.save_user(&user).await {
                    Ok(_) => return HttpResponse::Ok().finish(),
                    Err(e) => return e.error_response(),
                }
            }

            HttpResponse::BadRequest().body("Invitation is expired")
        }
        None => HttpResponse::BadRequest().body("Invalid invitation"),
    }
}
