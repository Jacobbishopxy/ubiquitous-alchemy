use chrono::NaiveDateTime;
use rbatis::crud_table;
use serde::{Deserialize, Serialize};

#[crud_table(table_name::user)]
#[derive(Debug, Serialize, Deserialize)]
pub struct User {
    pub email: String,
    pub hash: String,
    pub created_at: NaiveDateTime,
}

impl User {
    pub fn from_details<S: Into<String>, T: Into<String>>(email: S, pwd: T) -> Self {
        User {
            email: email.into(),
            hash: pwd.into(),
            created_at: chrono::Local::now().naive_local(),
        }
    }
}

#[derive(Debug, Serialize, Deserialize)]
pub struct SimpleUser {
    pub email: String,
}

impl From<User> for SimpleUser {
    fn from(user: User) -> Self {
        SimpleUser { email: user.email }
    }
}
