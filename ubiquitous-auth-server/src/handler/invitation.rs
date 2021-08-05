use std::sync::Arc;

use actix_web::{web, HttpResponse, ResponseError};
use serde::Deserialize;

use crate::service::{EmailService, Persistence};

#[derive(Deserialize)]
pub struct InvitationReq {
    pub email: String,
}

pub async fn post_invitation(
    invitation_req: web::Json<InvitationReq>,
    persistence: web::Data<Arc<Persistence>>,
) -> HttpResponse {
    let es = EmailService::new();

    let invitation = match persistence
        .save_invitation(&invitation_req.0.email.into())
        .await
    {
        Ok(i) => i,
        Err(e) => return e.error_response(),
    };

    match es.send_invitation(&invitation) {
        Ok(_) => HttpResponse::Ok().finish(),
        Err(e) => HttpResponse::InternalServerError().body(e.to_string()),
    }
}
