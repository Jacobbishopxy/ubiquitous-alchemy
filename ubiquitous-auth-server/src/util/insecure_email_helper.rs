use std::convert::{TryFrom, TryInto};

use lettre::message::{Mailbox, MessageBuilder};
use lettre::transport::smtp::authentication::{Credentials, Mechanism};
use lettre::transport::smtp::SmtpTransportBuilder;
use lettre::{Message, SmtpTransport, Transport};

use crate::error::{ServiceError, ServiceResult};

pub struct EmailAddress<'a> {
    pub name: Option<&'a str>,
    pub address: &'a str,
}

impl<'a> EmailAddress<'a> {
    pub fn new(name: Option<&'a str>, address: &'a str) -> Self {
        Self { name, address }
    }
}

impl<'a> TryInto<Mailbox> for EmailAddress<'a> {
    type Error = ServiceError;

    fn try_into(self) -> ServiceResult<Mailbox> {
        let name = self.name.map_or("", |n| n);
        Ok(Mailbox::try_from((name, self.address))?)
    }
}

#[derive(Clone)]
pub struct InsecureEmailHelper {
    mailer_builder: SmtpTransportBuilder,
    message_builder: MessageBuilder,
}

impl InsecureEmailHelper {
    pub fn new(username: String, password: String, host: String, port: u16) -> Self {
        let server = format!("{:?}:{:?}", host, port);
        let creds = Credentials::new(username, password);
        let mailer = SmtpTransport::builder_dangerous(server)
            .credentials(creds)
            .authentication(vec![Mechanism::Plain]);

        InsecureEmailHelper {
            mailer_builder: mailer,
            message_builder: Message::builder(),
        }
    }

    pub fn add_sender(mut self, sender: EmailAddress) -> ServiceResult<Self> {
        self.message_builder = self.message_builder.from(sender.try_into()?);

        Ok(self)
    }

    pub fn add_reply(mut self, replier: EmailAddress) -> ServiceResult<Self> {
        self.message_builder = self.message_builder.reply_to(replier.try_into()?);

        Ok(self)
    }

    pub fn add_replies(mut self, repliers: Vec<EmailAddress>) -> ServiceResult<Self> {
        for e in repliers {
            self = self.add_reply(e)?;
        }

        Ok(self)
    }

    pub fn add_recipient(mut self, recipient: EmailAddress) -> ServiceResult<Self> {
        self.message_builder = self.message_builder.to(recipient.try_into()?);

        Ok(self)
    }

    pub fn add_recipients(mut self, recipients: Vec<EmailAddress>) -> ServiceResult<Self> {
        for e in recipients {
            self = self.add_recipient(e)?;
        }

        Ok(self)
    }

    pub fn send(&self, subject: &str, body: &str) -> ServiceResult<()> {
        let email = self
            .message_builder
            .clone()
            .subject(subject)
            .body(body.to_owned())?;
        let mailer = self.mailer_builder.clone().build();

        match mailer.send(&email) {
            Ok(_) => Ok(()),
            Err(e) => Err(e)?,
        }
    }

    pub fn reset_message_builder(&mut self) {
        self.message_builder = Message::builder();
    }
}
