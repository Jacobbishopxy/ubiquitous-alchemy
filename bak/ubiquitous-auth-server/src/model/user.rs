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
#[derive(Debug, Serialize, Deserialize, Clone, Copy, PartialEq, Eq)]
pub enum Role {
    #[serde(rename = "admin")]
    Admin,
    #[serde(rename = "visitor")]
    Visitor,
    #[serde(rename = "editor")]
    Editor,
    #[serde(rename = "supervisor")]
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
        match value.to_lowercase().as_str() {
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

// `enum` is not yet unsupported by rbatis crate, temporary use string
// pub const ROLE_TYPE: &str = r#"
//     DO $$ BEGIN
//         IF NOT EXISTS (
//             SELECT *
//             FROM pg_type typ
//             INNER JOIN pg_namespace nsp
//             ON nsp.oid = typ.typnamespace
//             WHERE nsp.nspname = current_schema()
//             AND typ.typname = 'ai'
//         ) THEN
//         CREATE TYPE RoleType AS ENUM (
//             'admin',
//             'visitor',
//             'editor',
//             'supervisor'
//         );
//         END IF;
//     END$$;
// "#;

pub const USER_TABLE: &str = r#"
    CREATE TABLE IF NOT EXISTS
    users(
        email VARCHAR PRIMARY KEY,
        nickname VARCHAR NOT NULL,
        hash VARCHAR NOT NULL,
        role VARCHAR NOT NULL,
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
#[derive(Debug, Serialize, Deserialize, Clone, PartialEq, Eq)]
pub struct User {
    pub email: String,
    pub nickname: String,
    pub hash: String,
    pub role: Role,
    pub created_at: NaiveDateTime,
}

impl User {
    pub fn from_details<T>(nickname: T, email: T, password: T) -> ServiceResult<Self>
    where
        T: Into<String>,
    {
        // default role is visitor
        let hash = hash_password(&password.into())?;
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

#[cfg(test)]
mod test_user {
    use super::*;

    #[test]
    fn test_user_from_details() {
        let user = User::from_details("nickname", "email", "password").unwrap();
        assert_eq!(user.email, "email");
        assert_eq!(user.nickname, "nickname");
        assert_eq!(user.role, Role::Visitor);
    }

    #[test]
    fn test_user_from_str() {
        let user = Role::from_str("admin").unwrap();
        assert_eq!(user, Role::Admin);
    }

    #[test]
    fn test_user_json() {
        let user = User::from_details("nickname", "email", "password").unwrap();
        let json = serde_json::to_string(&user).unwrap();

        // println!("{:?}", json);

        let user_from_str: User = serde_json::from_str(&json).unwrap();

        // println!("{:?}", user);

        assert_eq!(user, user_from_str);
    }
}
