use std::time::Duration;

use rbatis::core::db::DBPoolOptions;
use rbatis::crud::CRUD;
use rbatis::rbatis::Rbatis;

use crate::error::{ServiceError, ServiceResult};
use crate::model::{Invitation, SimpleUser, User};

pub struct Persistence {
    pub conn: String,
    rb: Rbatis,
}

impl Persistence {
    pub async fn new(conn: &str) -> ServiceResult<Self> {
        let rb = Rbatis::new();
        let mut opt = DBPoolOptions::new();

        opt.connect_timeout = Duration::new(5, 0);
        rb.link_opt(conn, &opt).await?;

        Ok(Persistence {
            conn: conn.to_owned(),
            rb,
        })
    }

    pub async fn init_table(&self) -> ServiceResult<()> {
        todo!()
    }

    pub async fn get_invitation_by_email(&self, email: &str) -> ServiceResult<Option<Invitation>> {
        Ok(self.rb.fetch_by_column("email", &email.to_owned()).await?)
    }

    pub async fn save_invitation(&self, invitation: &Invitation) -> ServiceResult<Invitation> {
        self.rb.save(invitation, &[]).await?;

        self.get_invitation_by_email(&invitation.email)
            .await
            .and_then(|op| match op {
                Some(i) => Ok(i),
                None => Err(ServiceError::InternalServerError(
                    "invitation not found".to_owned(),
                )),
            })
    }

    pub async fn save_user(&self, _user: &User) -> ServiceResult<User> {
        todo!()
    }

    pub async fn get_user_by_email(&self, _user: &SimpleUser) -> ServiceResult<User> {
        todo!()
    }
}
