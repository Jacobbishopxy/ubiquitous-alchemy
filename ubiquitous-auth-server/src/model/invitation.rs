use chrono::NaiveDateTime;
use rbatis::crud_table;
use serde::{Deserialize, Serialize};
use uuid::Uuid;

#[crud_table(table_name:invitation | formats_pg:"id:{}::uuid")]
#[derive(Debug, Serialize, Deserialize)]
pub struct Invitation {
    pub id: Option<Uuid>,
    pub email: String,
    pub expires_at: NaiveDateTime,
}
