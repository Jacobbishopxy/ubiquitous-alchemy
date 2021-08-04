use crate::error::{ServiceError, ServiceResult};
use crate::model::Invitation;
use crate::util::{EmailAddress, EmailHelper};

pub struct EmailService {
    sender: String,
    helper: EmailHelper,
}

impl EmailService {
    pub fn new(
        sender: String,
        username: String,
        password: String,
        host: String,
        port: u16,
    ) -> Self {
        let helper = EmailHelper::new(username, password, host, port);

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
            Please click on the link below to complete registration.
            <br/>
            <a href="http://localhost:3000/register.html?id={}&email={}">
            http://localhost:3030/register
            </a>
            <br/>
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

#[test]
fn test_send_invitation() {
    use std::assert_matches::assert_matches;
    use std::convert::TryInto;

    use uuid::Uuid;

    use super::*;
    use crate::constant::CFG;

    let sender = CFG
        .get("SENDING_EMAIL_ADDRESS")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");
    let smtp_username = CFG
        .get("SMTP_USERNAME")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");
    let smtp_password = CFG
        .get("SMTP_PASSWORD")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");
    let smtp_host = CFG
        .get("SMTP_HOST")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");
    let smtp_port: u32 = CFG
        .get("SMTP_PORT")
        .unwrap()
        .clone()
        .try_into()
        .expect("Err");

    let es = EmailService::new(
        sender,
        smtp_username,
        smtp_password,
        smtp_host,
        smtp_port as u16,
    );

    let mut invitation: Invitation = "jacobxy@qq.com".into();
    invitation.id = Some(Uuid::parse_str("02207087-ab01-4a57-ad8a-bcbcddf500ea").unwrap());

    let res = es.send_invitation(&invitation);

    assert_matches!(res, Ok(_));
}
