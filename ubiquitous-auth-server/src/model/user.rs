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

#[derive(PartialEq, Eq)]
pub enum Permission {
    AlterUserRole,
    AlterUserGeneralInfo,
    AlterSelfGeneralInfo,
}

impl Role {
    pub fn permissions(&self) -> Vec<Permission> {
        match self {
            Role::Admin => vec![Permission::AlterUserRole, Permission::AlterSelfGeneralInfo],
            Role::Visitor => vec![Permission::AlterSelfGeneralInfo],
            Role::Editor => vec![Permission::AlterSelfGeneralInfo],
            Role::Supervisor => vec![
                Permission::AlterUserRole,
                Permission::AlterUserGeneralInfo,
                Permission::AlterSelfGeneralInfo,
            ],
        }
    }

    pub fn is_permitted(&self, permission: Permission) -> bool {
        self.permissions().contains(&permission)
    }
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
    // only allow to be altered by admin+
    pub role: Role,
    pub created_at: NaiveDateTime,
}

impl User {
    pub fn from_details<T>(nickname: T, email: T, pwd: T) -> Self
    where
        T: Into<String>,
    {
        // default role is visitor
        User {
            nickname: nickname.into(),
            email: email.into(),
            hash: pwd.into(),
            role: Role::Visitor,
            created_at: chrono::Local::now().naive_local(),
        }
    }
}
