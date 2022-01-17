use std::str::FromStr;
use std::time::Duration;

use rbatis::core::db::DBPoolOptions;
use rbatis::crud::{Skip, CRUD};
use rbatis::executor::Executor;
use rbatis::rbatis::Rbatis;

use super::hash_password;
use crate::constant::CONFIG;
use crate::error::{ServiceError, ServiceResult};
use crate::model::{Invitation, User, UserAlteration, INVITATION_TABLE, USER_TABLE};

pub struct Persistence {
    rb: Rbatis,
}

impl Persistence {
    /// constructor
    pub async fn new() -> ServiceResult<Self> {
        let rb = Rbatis::new();
        let mut opt = DBPoolOptions::new();

        opt.connect_timeout = Duration::new(5, 0);
        let url = &CONFIG.database_url;
        rb.link_opt(url, opt).await?;

        Ok(Persistence { rb })
    }

    /// table migrations
    pub async fn initialize(&self, required: bool) -> ServiceResult<()> {
        if required {
            // invitation table
            self.rb.exec(INVITATION_TABLE, vec![]).await?;

            // users table
            self.rb.exec(USER_TABLE, vec![]).await?;
        }
        Ok(())
    }

    /// find invitation by id
    pub async fn get_invitation_by_id(&self, id: &str) -> ServiceResult<Option<Invitation>> {
        let id = uuid::Uuid::from_str(id)?;
        Ok(self.rb.fetch_by_column("id", &id).await?)
    }

    /// find invitation by email and latest expired (in case of invited several times)
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

        let r: Option<Invitation> = self.rb.fetch_by_wrapper(w).await?;
        Ok(r)
    }

    /// save an invitation
    pub async fn save_invitation(&self, invitation: &Invitation) -> ServiceResult<Invitation> {
        // skip "id" column and let Postgres to auto gen "id"
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

    /// get user by email
    pub async fn get_user_by_email(&self, email: &str) -> ServiceResult<Option<User>> {
        Ok(self.rb.fetch_by_column("email", &email.to_owned()).await?)
    }

    /// save user & alter user
    pub async fn save_user(&self, user: User) -> ServiceResult<()> {
        Ok(self.rb.save(&user, &[]).await.map(|_| ())?)
    }

    /// alter user role (admin permission)
    pub async fn alter_user_info(&self, alter: UserAlteration) -> ServiceResult<()> {
        let w = self.rb.new_wrapper().eq("email", &alter.email);

        let user: Option<User> = self.rb.fetch_by_wrapper(w.clone()).await?;

        match user {
            Some(mut u) => {
                if let Some(nickname) = alter.nickname {
                    u.nickname = nickname;
                }
                if let Some(ref password) = alter.password {
                    u.hash = hash_password(password)?;
                }
                if let Some(role) = alter.role {
                    u.role = role;
                }

                let r = self.rb.update_by_wrapper(&u, w, &[]).await?;
                if r == 1 {
                    Ok(())
                } else {
                    Err(ServiceError::InternalServerError(format!(
                        "update err, effect {:?} row",
                        r
                    )))
                }
            }
            None => Err(ServiceError::InternalServerError(
                "user not found".to_owned(),
            )),
        }
    }
}

#[cfg(test)]
mod persistence_test {
    use std::assert_matches::assert_matches;

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

        let invitation = Invitation::from_details("jacob", "jacob@example.com", "hashed_pw");

        let res = p.save_invitation(&invitation).await;

        assert_matches!(res, Ok(_));
    }

    #[actix_rt::test]
    async fn get_invitation_by_email_test() {
        let p = Persistence::new().await.unwrap();

        let res = p
            .get_invitation_by_email_and_latest_expired("jacob@example.com")
            .await;

        assert_matches!(res, Ok(_));
    }

    #[actix_rt::test]
    async fn get_invitation_by_id_test() {
        let p = Persistence::new().await.unwrap();

        let res = p
            .get_invitation_by_id("82834e08-6d73-4d29-9006-c240b4c3aa42")
            .await;

        assert_matches!(res, Ok(_));
    }

    #[actix_rt::test]
    async fn save_user_test() {
        let p = Persistence::new().await.unwrap();

        let user = User::from_details("Jacob", "jacob@example.com", "123456").unwrap();

        let res = p.save_user(user).await;

        assert_matches!(res, Ok(_));
    }

    #[actix_rt::test]
    async fn get_user_test() {
        let p = Persistence::new().await.unwrap();

        let user = "jacob@example.com";

        let res = p.get_user_by_email(user).await;

        assert_matches!(res, Ok(_));
    }

    #[actix_rt::test]
    async fn alter_user_test() {
        let p = Persistence::new().await.unwrap();

        let mut alter = UserAlteration::new("jacob@example.com");
        alter.nickname("Jacob(1)");
        alter.password("12345678");
        alter.role("admin").unwrap();

        let res = p.alter_user_info(alter).await;

        assert_matches!(res, Ok(_))
    }
}
