use crate::error::{ServiceError, ServiceResult};
use crate::model::Invitation;
use crate::util::{EmailAddress, InsecureEmailHelper};

pub struct EmailService {
    sender: String,
    helper: InsecureEmailHelper,
}

impl EmailService {
    pub fn new(
        sender: String,
        username: String,
        password: String,
        host: String,
        port: u16,
    ) -> Self {
        let helper = InsecureEmailHelper::new(username, password, host, port);

        Self { sender, helper }
    }

    // TODO: message as config
    pub fn send_invitation(&self, invitation: &Invitation) -> ServiceResult<()> {
        let subject = "You have been invited to join Ubiquitous Alchemy";
        let id = match invitation.id {
            Some(i) => i,
            None => {
                return Err(ServiceError::InternalServerError(
                    "invitation id error".to_owned(),
                ))
            }
        };
        let body = format!(
            r#"
            Please click on the link below to complete registration. <br/>
            <a href=\"http://localhost:3000/register.html?id={}&email={}\">
            http://localhost:3030/register</a> <br/>
            your Invitation expires on <strong>{}</strong>
            "#,
            id,
            invitation.email,
            invitation
                .expires_at
                .format("%I:%M %p %A, %-d %B, %C%y")
                .to_string()
        );

        self.helper
            .clone()
            .add_sender(EmailAddress::new(None, &self.sender))?
            .add_recipient(EmailAddress::new(None, &invitation.email))?
            .send(subject, &body)
    }
}
