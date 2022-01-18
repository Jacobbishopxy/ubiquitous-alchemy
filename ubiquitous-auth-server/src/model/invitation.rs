use chrono::NaiveDateTime;
use rbatis::crud_table;
use serde::{Deserialize, Serialize};

pub const INVITATION_TABLE: &str = r#"
    CREATE TABLE IF NOT EXISTS
    invitation(
        id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
        email VARCHAR NOT NULL,
        nickname VARCHAR NOT NULL,
        hash VARCHAR NOT NULL,
        expires_at TIMESTAMP NOT NULL
    )
"#;

/// invitation table
/// id: uuid, primary key, auto generated
/// nickname: string
/// email: string
/// hash: string, password in hashed form
/// expires_at: timestamp
#[crud_table(table_name:"invitation" | formats_pg:"id:{}::uuid,expires_at:{}::timestamp")]
#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct Invitation {
    pub id: Option<String>,
    pub nickname: String,
    pub email: String,
    pub hash: String,
    pub expires_at: NaiveDateTime,
}

impl Invitation {
    pub fn from_details<T>(nickname: T, email: T, hash: T) -> Self
    where
        T: Into<String>,
    {
        Invitation {
            id: None,
            nickname: nickname.into(),
            email: email.into(),
            hash: hash.into(),
            expires_at: chrono::Local::now().naive_local() + chrono::Duration::hours(24),
        }
    }
}
