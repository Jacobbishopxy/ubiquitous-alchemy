use std::sync::Arc;

use actix_web::{web, HttpResponse, ResponseError};
use serde::Deserialize;

use crate::model::User;
use crate::service::Persistence;

#[derive(Debug, Deserialize)]
pub struct UserReq {
    pub email: String,
}

pub async fn register_user(
    invitation_id: web::Path<String>,
    user_req: web::Json<UserReq>,
    persistence: web::Data<Arc<Persistence>>,
) -> HttpResponse {
    let invitation = match persistence.get_invitation_by_id(&invitation_id).await {
        Ok(op) => op,
        Err(e) => return e.error_response(),
    };
    match invitation {
        Some(inv) => {
            if inv.expires_at > chrono::Local::now().naive_local() && user_req.email == inv.email {
                let user = User::from_details(inv.nickname, inv.email, inv.hash);

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
