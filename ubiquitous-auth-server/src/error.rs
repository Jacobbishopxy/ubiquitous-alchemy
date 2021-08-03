use actix_web::{HttpResponse, ResponseError};
use thiserror::Error;

pub type ServiceResult<T> = Result<T, ServiceError>;

#[derive(Error, Debug)]
pub enum ServiceError {
    #[error("internal server error {0}")]
    InternalServerError(String),

    #[error("bad request {0}")]
    BadRequest(String),

    #[error("unauthorized {0}")]
    Unauthorized(String),

    #[error("lettre error")]
    LettreError(#[from] lettre::error::Error),

    #[error("email address error")]
    LettreAddressError(#[from] lettre::address::AddressError),

    #[error("smtp error")]
    LettreSmtpError(#[from] lettre::transport::smtp::Error),

    #[error("persistence error")]
    PersistenceError(#[from] rbatis::Error),

    #[error("web error")]
    WebError(#[from] actix_web::Error),
}

impl ResponseError for ServiceError {
    fn error_response(&self) -> HttpResponse {
        match self {
            ServiceError::InternalServerError(e) => {
                HttpResponse::InternalServerError().body(e.to_owned())
            }
            ServiceError::BadRequest(e) => HttpResponse::BadRequest().body(e.to_owned()),
            ServiceError::Unauthorized(e) => HttpResponse::Unauthorized().body(e.to_owned()),
            ServiceError::LettreError(e) => HttpResponse::InternalServerError().body(e.to_string()),
            ServiceError::LettreAddressError(e) => {
                HttpResponse::InternalServerError().body(e.to_string())
            }
            ServiceError::LettreSmtpError(e) => {
                HttpResponse::InternalServerError().body(e.to_string())
            }
            ServiceError::PersistenceError(e) => {
                HttpResponse::InternalServerError().body(e.to_string())
            }
            ServiceError::WebError(e) => e.error_response(),
        }
    }
}
