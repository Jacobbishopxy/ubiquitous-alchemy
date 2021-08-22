use chrono::NaiveDateTime;
use rbatis::crud_table;
use serde::{Deserialize, Serialize};
use uuid::Uuid;

#[crud_table(table_name:"invitation" | formats_pg:"id:{}::uuid,expires_at:{}::timestamp")]
#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct Invitation {
    pub id: Option<Uuid>,
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
