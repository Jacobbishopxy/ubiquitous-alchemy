use std::convert::TryInto;

use super::error::ServiceError;

#[derive(Clone)]
pub struct Config {
    pub database_url: String,
    pub is_secure: bool,
    pub sending_email_addr: String,
    pub secret_key: String,
    pub secret_len: u32,
    pub smtp_username: String,
    pub smtp_password: String,
    pub smtp_host: String,
    pub smtp_port: u32,
    pub invitation_page: String,
    pub invitation_message: String,
    pub persistence_init: bool,
    pub service_host: String,
    pub service_port: String,
    pub cookie_duration_secs: u32,
}

pub enum CFGValue {
    String(String),
    UInt(u32),
    Bool(bool),
}

lazy_static::lazy_static! {
    pub static ref CONFIG: Config = {
        let database_url = dotenv::var("DATABASE_URL").expect("Expected DATABASE_URL to be set in env!");
        let is_secure = dotenv::var("IS_SECURE").expect("Expected IS_SECURE to be set in env!").parse::<bool>().expect("IS_SECURE parse err!");
        let sending_email_addr = dotenv::var("SENDING_EMAIL_ADDRESS").expect("Expected SENDING_EMAIL_ADDRESS to be set in env!");
        let secret_key = dotenv::var("SECRET_KEY").expect("Expected SECRET_KEY to be set in env!");
        let secret_len = dotenv::var("SECRET_LEN").expect("Expected SECRET_LEN to be set in env!").parse::<u32>().expect("SECRET_KEY parse err!");
        let smtp_username = dotenv::var("SMTP_USERNAME").expect("Expected SMTP_USERNAME to be set in env!");
        let smtp_password = dotenv::var("SMTP_PASSWORD").expect("Expected SMTP_PASSWORD to be set in env!");
        let smtp_host = dotenv::var("SMTP_HOST").expect("Expected SMTP_HOST to be set in env!");
        let smtp_port = dotenv::var("SMTP_PORT").expect("Expected SMTP_PORT to be set in env!").parse::<u32>().expect("SMTP_PORT parse err!");
        let invitation_page = dotenv::var("INVITATION_PAGE").expect("Expected INVITATION_PAGE to be set in env!");
        let invitation_message = dotenv::var("INVITATION_MESSAGE").expect("Expected INVITATION_MESSAGE to be set in env!");
        let service_host = dotenv::var("SERVICE_HOST").expect("Expected SERVICE_HOST to be set in env!");
        let service_port = dotenv::var("SERVICE_PORT").expect("Expected SERVICE_PORT to be set in env!");
        let persistence_init = dotenv::var("PERSISTENCE_INIT").expect("Expected PERSISTENCE_INIT to be set in env!").parse::<bool>().expect("PERSISTENCE_INIT parse err!");
        let cookie_duration_secs = dotenv::var("COOKIE_DURATION_SECS").map_or(0, |s| s.parse::<u32>().expect("SMTP_PORT parse err!"));

        Config {
            database_url,
            is_secure,
            sending_email_addr,
            secret_key,
            secret_len,
            smtp_username,
            smtp_password,
            smtp_host,
            smtp_port,
            invitation_page,
            invitation_message,
            persistence_init,
            service_host,
            service_port,
            cookie_duration_secs,
        }
    };
}

impl TryInto<u32> for &CFGValue {
    type Error = ServiceError;

    fn try_into(self) -> Result<u32, Self::Error> {
        match self {
            CFGValue::String(_) => Err(ServiceError::InternalServerError(
                "error: parse string to u32".to_owned(),
            )),
            CFGValue::UInt(u) => Ok(u.to_owned()),
            CFGValue::Bool(_) => Err(ServiceError::InternalServerError(
                "error: parse string to bool".to_owned(),
            )),
        }
    }
}

impl TryInto<String> for &CFGValue {
    type Error = ServiceError;

    fn try_into(self) -> Result<String, Self::Error> {
        match self {
            CFGValue::String(s) => Ok(s.to_owned()),
            CFGValue::UInt(_) => Err(ServiceError::InternalServerError(
                "error: parse uint to string".to_owned(),
            )),
            CFGValue::Bool(_) => Err(ServiceError::InternalServerError(
                "error: parse uint to bool".to_owned(),
            )),
        }
    }
}

impl TryInto<bool> for &CFGValue {
    type Error = ServiceError;

    fn try_into(self) -> Result<bool, Self::Error> {
        match self {
            CFGValue::String(_) => Err(ServiceError::InternalServerError(
                "error: parse uint to string".to_owned(),
            )),
            CFGValue::UInt(_) => Err(ServiceError::InternalServerError(
                "error: parse uint to string".to_owned(),
            )),
            CFGValue::Bool(b) => Ok(b.to_owned()),
        }
    }
}
