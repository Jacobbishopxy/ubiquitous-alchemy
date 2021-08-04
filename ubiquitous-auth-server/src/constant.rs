use std::{collections::HashMap, convert::TryInto};

use super::error::ServiceError;

pub enum CFGValue {
    String(String),
    UInt(u32),
    Bool(bool),
}

lazy_static::lazy_static! {
    pub static ref CFG: HashMap<&'static str, CFGValue> = {

        dotenv::from_path(".env").ok();

        let mut map = HashMap::new();

        let database_url = dotenv::var("DATABASE_URL").expect("Expected DATABASE_URL to be set in env!");
        let sending_email_addr = dotenv::var("SENDING_EMAIL_ADDRESS").expect("Expected SENDING_EMAIL_ADDRESS to be set in env!");
        let secret_key = dotenv::var("SECRET_KEY").expect("Expected SECRET_KEY to be set in env!");
        let secret_len = dotenv::var("SECRET_LEN").expect("Expected SECRET_LEN to be set in env!").parse::<u32>().expect("SECRET_KEY parse err!");
        let security_domain = dotenv::var("SECURITY_DOMAIN").expect("Expected SECURITY_DOMAIN to be set in env!");
        let smtp_username = dotenv::var("SMTP_USERNAME").expect("Expected SMTP_USERNAME to be set in env!");
        let smtp_password = dotenv::var("SMTP_PASSWORD").expect("Expected SMTP_PASSWORD to be set in env!");
        let smtp_host = dotenv::var("SMTP_HOST").expect("Expected SMTP_HOST to be set in env!");
        let smtp_port = dotenv::var("SMTP_PORT").expect("Expected SMTP_PORT to be set in env!").parse::<u32>().expect("SMTP_PORT parse err!");
        let service_host = dotenv::var("SERVICE_HOST").expect("Expected SERVICE_HOST to be set in env!");
        let service_port = dotenv::var("SERVICE_PORT").expect("Expected SERVICE_PORT to be set in env!");
        let persistence_init = dotenv::var("PERSISTENCE_INIT").expect("Expected PERSISTENCE_INIT to be set in env!").parse::<bool>().expect("PERSISTENCE_INIT parse err!");

        map.insert("DATABASE_URL", CFGValue::String(database_url));
        map.insert("SENDING_EMAIL_ADDRESS", CFGValue::String(sending_email_addr));
        map.insert("SECRET_KEY", CFGValue::String(secret_key));
        map.insert("SECRET_LEN", CFGValue::UInt(secret_len));
        map.insert("SECURITY_DOMAIN", CFGValue::String(security_domain));
        map.insert("SMTP_USERNAME", CFGValue::String(smtp_username));
        map.insert("SMTP_PASSWORD", CFGValue::String(smtp_password));
        map.insert("SMTP_HOST", CFGValue::String(smtp_host));
        map.insert("SMTP_PORT", CFGValue::UInt(smtp_port));
        map.insert("SERVICE_HOST", CFGValue::String(service_host));
        map.insert("SERVICE_PORT", CFGValue::String(service_port));
        map.insert("PERSISTENCE_INIT", CFGValue::Bool(persistence_init));

        map
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
