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

/// collect user register info and send an invitation email to user
pub async fn send_invitation(
    invitation_req: web::Json<InvitationReq>,
    persistence: web::Data<Arc<Persistence>>,
) -> HttpResponse {
    // hash user password
    let hash = match encryption::hash_password(&invitation_req.password) {
        Ok(pwd) => pwd,
        Err(e) => return e.error_response(),
    };

    // new `Invitation` instance, notice user role is default as 'visitor' (see `from_details`)
    let inv = Invitation::from_details(&invitation_req.nickname, &invitation_req.email, &hash);
    // persist invitation into database
    let invitation = match persistence.save_invitation(&inv).await {
        Ok(i) => i,
        Err(e) => return e.error_response(),
    };

    // new an email service
    let es = EmailService::new();
    // send invitation email
    match es.send_invitation(&invitation) {
        Ok(_) => HttpResponse::Ok().finish(),
        Err(e) => e.error_response(),
    }
}
