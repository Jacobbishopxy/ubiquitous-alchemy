use crate::constant::CONFIG;
use crate::error::{ServiceError, ServiceResult};
use crate::model::Invitation;
use crate::util::{EmailAddress, EmailHelper};

pub struct EmailService {
    helper: EmailHelper,
}

impl Default for EmailService {
    fn default() -> Self {
        let helper = EmailHelper::new(
            CONFIG.is_secure,
            CONFIG.smtp_username.clone(),
            CONFIG.smtp_password.clone(),
            CONFIG.smtp_host.clone(),
            CONFIG.smtp_port as u16,
        );
        Self { helper }
    }
}

impl EmailService {
    pub fn new() -> Self {
        Self::default()
    }

    // send an invitation email
    pub fn send_invitation(&self, invitation: &Invitation) -> ServiceResult<()> {
        let id = match &invitation.id {
            Some(i) => i,
            None => {
                return Err(ServiceError::InternalServerError(
                    "invitation id error".to_owned(),
                ))
            }
        };
        let invitation_page = &CONFIG.invitation_page;
        let expires_at = invitation
            .expires_at
            .format("%I:%M %p %A, %-d %B, %C%y")
            .to_string();
        let body = format!(
            r#"
            Please click on the link below to complete registration.
            <br/>
            <a href="{}/register?id={}&email={}">
            {}/register
            </a>
            <br/>
            Your Invitation expires on <strong>{}</strong>
            "#,
            invitation_page, id, invitation.email, invitation_page, expires_at
        );
        let sender = &CONFIG.sending_email_addr;
        let subject = &CONFIG.invitation_message;
        self.helper
            .clone()
            .add_sender(EmailAddress::new(None, sender))?
            .add_recipient(EmailAddress::new(None, &invitation.email))?
            .send(subject, &body)
    }
}

#[test]
fn test_send_invitation() {
    use std::assert_matches::assert_matches;

    use super::*;

    let es = EmailService::new();

    // Change it to an available email address
    let mut invitation = Invitation::from_details("jacob", "jacobxy@qq.com", "hashed_pw");
    invitation.id = Some("02207087-ab01-4a57-ad8a-bcbcddf500ea".to_owned());

    let res = es.send_invitation(&invitation);

    assert_matches!(res, Ok(_));
}
