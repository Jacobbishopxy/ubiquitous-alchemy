//! Model: user
//!
//!  

use std::str::FromStr;

use chrono::NaiveDateTime;
use serde::{Deserialize, Serialize};

use crate::error::{ServiceError, ServiceResult};
use crate::service::encryption::hash_password;

// TODO: role & permission enhancement
/// user role
#[derive(Debug, Serialize, Deserialize, Clone)]
pub enum Role {
    Admin,
    Visitor,
    Editor,
    Supervisor,
}

/// role permission
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

impl FromStr for Role {
    type Err = ServiceError;

    fn from_str(value: &str) -> Result<Self, Self::Err> {
        match value {
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

pub const USER_TABLE: &str = r#"
CREATE TABLE IF NOT EXISTS
users(
    email VARCHAR(100) PRIMARY KEY,
    nickname VARCHAR(100) NOT NULL,
    hash VARCHAR(122) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL
)
"#;

/// users table
/// email: string
/// nickname: string
/// hash: string, hashed password
/// role: user role, only allow to be altered by admin+
/// created_at: timestamp
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
    pub fn from_details<T>(nickname: T, email: T, pwd: T) -> ServiceResult<Self>
    where
        T: Into<String>,
    {
        // default role is visitor
        let hash = hash_password(&pwd.into())?;
        let user = User {
            nickname: nickname.into(),
            email: email.into(),
            hash,
            role: Role::Visitor,
            created_at: chrono::Local::now().naive_local(),
        };
        Ok(user)
    }
}

#[derive(Debug, Serialize, Deserialize)]
pub struct UserInfo {
    pub email: String,
    pub nickname: String,
    pub role: Role,
}

impl From<User> for UserInfo {
    fn from(user: User) -> Self {
        UserInfo {
            email: user.email,
            nickname: user.nickname,
            role: user.role,
        }
    }
}

#[derive(Debug, Serialize, Deserialize)]
pub struct UserAlteration {
    pub email: String,
    pub nickname: Option<String>,
    pub password: Option<String>,
    pub role: Option<Role>,
}

impl UserAlteration {
    pub fn new(email: &str) -> Self {
        UserAlteration {
            email: email.to_owned(),
            nickname: None,
            password: None,
            role: None,
        }
    }

    pub fn nickname(&mut self, nickname: &str) -> &mut Self {
        self.nickname = Some(nickname.to_owned());
        self
    }

    pub fn password(&mut self, password: &str) -> &mut Self {
        self.password = Some(password.to_owned());
        self
    }

    pub fn role(&mut self, role: &str) -> ServiceResult<&mut Self> {
        self.role = Some(Role::from_str(role)?);
        Ok(self)
    }
}
