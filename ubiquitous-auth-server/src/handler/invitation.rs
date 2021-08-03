use actix_web::{web, HttpResponse};
use serde::Deserialize;

use crate::service::Persistence;

#[derive(Deserialize)]
pub struct InvitationReq {
    pub email: String,
}

#[allow(dead_code)]
pub async fn post_invitation(
    invitation_req: web::Json<InvitationReq>,
    persistence: web::Data<Persistence>,
) -> HttpResponse {
    todo!()
}
