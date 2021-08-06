use std::str::FromStr;
use std::time::Duration;

use rbatis::core::db::DBPoolOptions;
use rbatis::crud::{Skip, CRUD};
use rbatis::executor::Executor;
use rbatis::rbatis::Rbatis;

use crate::constant::CONFIG;
use crate::error::{ServiceError, ServiceResult};
use crate::model::{Invitation, User};

pub struct Persistence {
    rb: Rbatis,
}

impl Persistence {
    pub async fn new() -> ServiceResult<Self> {
        let rb = Rbatis::new();
        let mut opt = DBPoolOptions::new();

        opt.connect_timeout = Duration::new(5, 0);
        rb.link_opt(&CONFIG.database_url, &opt).await?;

        Ok(Persistence { rb })
    }

    pub async fn initialize(&self, required: bool) -> ServiceResult<()> {
        if required {
            let init_invitation = r#"
            CREATE TABLE IF NOT EXISTS
            invitation(
                id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                email VARCHAR(100) NOT NULL,
                expires_at TIMESTAMP NOT NULL
            )
            "#;
            self.rb.exec(init_invitation, &vec![]).await?;

            let init_user = r#"
            CREATE TABLE IF NOT EXISTS
            users(
                email VARCHAR(100) PRIMARY KEY,
                nickname VARCHAR(100) NOT NULL,
                hash VARCHAR(122) NOT NULL,
                role VARCHAR(50) NOT NULL,
                created_at TIMESTAMP NOT NULL
            )
            "#;
            self.rb.exec(init_user, &vec![]).await?;
        }
        Ok(())
    }

    pub async fn get_invitation_by_id(&self, id: &str) -> ServiceResult<Option<Invitation>> {
        let id = uuid::Uuid::from_str(id)?;
        Ok(self.rb.fetch_by_column("id", &id).await?)
    }

    pub async fn get_invitation_by_email_and_latest_expired(
        &self,
        email: &str,
    ) -> ServiceResult<Option<Invitation>> {
        let w = self
            .rb
            .new_wrapper()
            .eq("email", email)
            .order_by(false, &["expires_at"])
            .limit(1);

        let r: Option<Invitation> = self.rb.fetch_by_wrapper(&w).await?;
        Ok(r)
    }

    pub async fn save_invitation(&self, invitation: &Invitation) -> ServiceResult<Invitation> {
        self.rb.save(invitation, &[Skip::Column("id")]).await?;

        self.get_invitation_by_email_and_latest_expired(&invitation.email)
            .await
            .and_then(|op| match op {
                Some(i) => Ok(i),
                None => Err(ServiceError::InternalServerError(
                    "invitation not found".to_owned(),
                )),
            })
    }

    pub async fn get_user_by_email(&self, email: &str) -> ServiceResult<Option<User>> {
        Ok(self.rb.fetch_by_column("email", &email.to_owned()).await?)
    }

    pub async fn save_user(&self, user: &User) -> ServiceResult<()> {
        Ok(self.rb.save(user, &[]).await.map(|_| ())?)
    }
}

#[cfg(test)]
mod persistence_test {
    use std::{assert_matches::assert_matches, convert::TryInto};

    use crate::model::user::Role;

    use super::*;

    #[actix_rt::test]
    async fn init_test() {
        let p = Persistence::new().await.unwrap();

        let res = p.initialize(true).await;

        assert_matches!(res, Ok(_));
    }

    #[actix_rt::test]
    async fn save_invitation_test() {
        let p = Persistence::new().await.unwrap();

        let invitation: Invitation = "jacob@example.com".into();

        let res = p.save_invitation(&invitation).await;

        assert_matches!(res, Ok(_));
    }

    #[actix_rt::test]
    async fn get_invitation_by_email_test() {
        let p = Persistence::new().await.unwrap();

        let invitation = "jacob@example.com";

        let res = p
            .get_invitation_by_email_and_latest_expired(&invitation)
            .await;

        println!("{:#?}", res);

        assert_matches!(res, Ok(_));
    }

    #[actix_rt::test]
    async fn get_invitation_by_id_test() {
        let p = Persistence::new().await.unwrap();

        let res = p
            .get_invitation_by_id("82834e08-6d73-4d29-9006-c240b4c3aa42")
            .await;

        println!("{:#?}", res);

        assert_matches!(res, Ok(_));
    }

    #[actix_rt::test]
    async fn save_user_test() {
        let p = Persistence::new().await.unwrap();

        let role: Role = "admin".to_owned().try_into().unwrap();
        let user = User::from_details("Jacob", "jacob@example.com", "pwd", role);

        let res = p.save_user(&user).await;

        assert_matches!(res, Ok(_));
    }

    #[actix_rt::test]
    async fn get_user_test() {
        let p = Persistence::new().await.unwrap();

        let user = "jacob@example.com";

        let res = p.get_user_by_email(user).await;

        assert_matches!(res, Ok(_));
    }
}
