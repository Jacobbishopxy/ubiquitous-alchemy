use std::sync::Arc;

use actix_web::{web, HttpResponse, ResponseError};
use serde::Deserialize;

use crate::{
    model::Invitation,
    service::{encryption, EmailService, Persistence},
};

#[derive(Deserialize)]
pub struct InvitationReq {
    pub nickname: String,
    pub email: String,
    pub password: String,
}

pub async fn post_invitation(
    invitation_req: web::Json<InvitationReq>,
    persistence: web::Data<Arc<Persistence>>,
) -> HttpResponse {
    let es = EmailService::new();

    let hash = match encryption::hash_password(&invitation_req.password) {
        Ok(pwd) => pwd,
        Err(e) => return e.error_response(),
    };
    let inv = Invitation::from_details(&invitation_req.nickname, &invitation_req.email, &hash);

    let invitation = match persistence.save_invitation(&inv).await {
        Ok(i) => i,
        Err(e) => return e.error_response(),
    };

    match es.send_invitation(&invitation) {
        Ok(_) => HttpResponse::Ok().finish(),
        Err(e) => e.error_response(),
    }
}
