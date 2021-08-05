use std::convert::TryFrom;

use chrono::NaiveDateTime;
use rbatis::crud_table;
use serde::{Deserialize, Serialize};

use crate::error::ServiceError;

#[derive(Debug, Serialize, Deserialize)]
pub enum Role {
    Admin,
    Visitor,
    Editor,
    Supervisor,
}

impl TryFrom<String> for Role {
    type Error = ServiceError;

    fn try_from(value: String) -> Result<Self, Self::Error> {
        match &value.to_lowercase()[..] {
            "admin" => Ok(Self::Admin),
            "visitor" => Ok(Self::Visitor),
            "editor" => Ok(Self::Editor),
            "supervisor" => Ok(Self::Supervisor),
            _ => Err(ServiceError::InternalServerError(
                "Role parsing error".to_owned(),
            )),
        }
    }
}

#[crud_table(table_name:"users" | formats_pg:"created_at:{}::timestamp")]
#[derive(Debug, Serialize, Deserialize)]
pub struct User {
    pub email: String,
    pub nickname: String,
    pub hash: String,
    pub role: Role,
    pub created_at: NaiveDateTime,
}

impl User {
    pub fn from_details<N, E, P, R>(nickname: N, email: E, pwd: P, role: R) -> Self
    where
        N: Into<String>,
        E: Into<String>,
        P: Into<String>,
        R: Into<Role>,
    {
        User {
            nickname: nickname.into(),
            email: email.into(),
            hash: pwd.into(),
            role: role.into(),
            created_at: chrono::Local::now().naive_local(),
        }
    }
}
