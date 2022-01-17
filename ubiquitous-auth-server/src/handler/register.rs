use std::sync::Arc;

use actix_web::{web, HttpResponse, ResponseError};

use crate::model::User;
use crate::service::Persistence;

// confirm user invitation id & email then register user
pub async fn register_user(
    invitation_id: web::Path<String>,
    persistence: web::Data<Arc<Persistence>>,
) -> HttpResponse {
    // check whether invitation is in database
    let invitation = match persistence.get_invitation_by_id(&invitation_id).await {
        Ok(op) => op,
        Err(e) => return e.error_response(),
    };
    // check whether invitation is expired
    match invitation {
        Some(inv) => {
            // if inv is not expired, copy user register info from invitation table and save it to user table
            if inv.expires_at > chrono::Local::now().naive_local() {
                let user = User::from_details(inv.nickname, inv.email, inv.hash);
                if let Err(e) = user {
                    return e.error_response();
                }
                // save user into 'user' table
                match persistence.save_user(user.unwrap()).await {
                    Ok(_) => HttpResponse::Ok().finish(),
                    Err(e) => e.error_response(),
                }
            } else {
                HttpResponse::BadRequest().body("Invitation is expired")
            }
        }
        None => HttpResponse::BadRequest().body("Invalid invitation"),
    }
}
