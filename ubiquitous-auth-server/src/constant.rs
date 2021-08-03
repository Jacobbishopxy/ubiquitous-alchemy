use std::{collections::HashMap, convert::TryInto};

use super::error::ServiceError;

pub enum CFGValue {
    String(String),
    UInt(u32),
}

lazy_static::lazy_static! {
    pub static ref CFG: HashMap<&'static str, CFGValue> = {

        dotenv::from_path("sas/.env").ok();

        let mut map = HashMap::new();

        let secret_key = dotenv::var("SECRET_KEY").expect("Expected SECRET_KEY to be set in env!");
        let secret_len = dotenv::var("SECRET_KEY").expect("Expected SECRET_LEN to be set in env!").parse::<u32>().expect("SECRET_KEY parse err!");
        let smtp_username = dotenv::var("SMTP_USERNAME").expect("Expected SMTP_USERNAME to be set in env!");
        let smtp_password = dotenv::var("SMTP_PASSWORD").expect("Expected SMTP_PASSWORD to be set in env!");
        let smtp_host = dotenv::var("SMTP_HOST").expect("Expected SMTP_HOST to be set in env!");
        let smtp_port = dotenv::var("SMTP_PORT").expect("Expected SMTP_PORT to be set in env!");

        map.insert("SECRET_KEY", CFGValue::String(secret_key));

        map.insert("SECRET_LEN", CFGValue::UInt(secret_len));

        map.insert("SMTP_USERNAME", CFGValue::String(smtp_username));

        map.insert("SMTP_PASSWORD", CFGValue::String(smtp_password));

        map.insert("SMTP_HOST", CFGValue::String(smtp_host));

        map.insert("SMTP_PORT", CFGValue::String(smtp_port));

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
        }
    }
}
