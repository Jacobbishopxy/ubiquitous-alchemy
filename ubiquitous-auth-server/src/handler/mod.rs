pub mod auth;
pub mod invitation;
pub mod register;

pub use auth::{alter_user_role, check_alive, login, logout};
pub use invitation::send_invitation;
pub use register::register_user;

use actix_web::HttpResponse;

pub async fn welcome() -> HttpResponse {
    HttpResponse::Ok().body("Welcome to ubiquitous-alchemy auth server!")
}
