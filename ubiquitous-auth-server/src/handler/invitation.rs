use std::convert::TryInto;
use std::sync::Arc;

use actix_web::{web, HttpResponse};
use serde::Deserialize;

use crate::constant::CFG;
use crate::error::ServiceResult;
use crate::service::{EmailService, Persistence};

#[derive(Deserialize)]
pub struct InvitationReq {
    pub email: String,
}

pub async fn post_invitation(
    invitation_req: web::Json<InvitationReq>,
    persistence: web::Data<Arc<Persistence>>,
) -> HttpResponse {
    match create_invitation_and_send_email(invitation_req.0.email, persistence).await {
        Ok(_) => HttpResponse::Ok().finish(),
        Err(e) => HttpResponse::InternalServerError().body(e.to_string()),
    }
}

async fn create_invitation_and_send_email(
    email: String,
    persistence: web::Data<Arc<Persistence>>,
) -> ServiceResult<()> {
    let sender = CFG
        .get("SENDING_EMAIL_ADDRESS")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");
    let smtp_username = CFG
        .get("SMTP_USERNAME")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");
    let smtp_password = CFG
        .get("SMTP_PASSWORD")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");
    let smtp_host = CFG
        .get("SMTP_HOST")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");
    let smtp_port: u32 = CFG
        .get("SMTP_PORT")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");

    let es = EmailService::new(
        sender,
        smtp_username,
        smtp_password,
        smtp_host,
        smtp_port as u16,
    );

    let invitation = persistence.save_invitation(&email.into()).await?;

    es.send_invitation(&invitation)
}
