use actix_web::{web, HttpResponse};
use serde::Deserialize;

use crate::service::Persistence;

#[derive(Debug, Deserialize)]
pub struct UserReq {
    pub email: String,
    pub password: String,
}

#[allow(dead_code)]
pub async fn register_user(
    invitation_id: web::Path<String>,
    user_req: web::Json<UserReq>,
    persistence: web::Data<Persistence>,
) -> HttpResponse {
    todo!()
}
