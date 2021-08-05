use chrono::NaiveDateTime;
use rbatis::crud_table;
use serde::{Deserialize, Serialize};
use uuid::Uuid;

#[crud_table(table_name:"invitation" | formats_pg:"id:{}::uuid,expires_at:{}::timestamp")]
#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct Invitation {
    pub id: Option<Uuid>,
    pub email: String,
    pub expires_at: NaiveDateTime,
}

impl<T> From<T> for Invitation
where
    T: Into<String>,
{
    fn from(email: T) -> Self {
        Invitation {
            id: None,
            email: email.into(),
            expires_at: chrono::Local::now().naive_local() + chrono::Duration::hours(24),
        }
    }
}
