use std::num::NonZeroU32;

use ring::{digest, pbkdf2};

use crate::error::{ServiceError, ServiceResult};

const CREDENTIAL_LEN: usize = digest::SHA512_256_OUTPUT_LEN;
type PasswordHash = [u8; CREDENTIAL_LEN];

pub struct EncryptionHelper {
    n_iter: NonZeroU32,
    salt: String,
}

impl EncryptionHelper {
    pub fn new(n: u32, salt: String) -> Self {
        EncryptionHelper {
            n_iter: NonZeroU32::new(n).unwrap(),
            salt,
        }
    }

    fn hash_to_string(hash: PasswordHash) -> String {
        let mut res = String::from("");
        for i in hash.iter() {
            res.push(*i as char)
        }

        res
    }

    fn string_to_hash(string: String) -> PasswordHash {
        let mut res = [0u8; CREDENTIAL_LEN];
        for (i, c) in string.chars().enumerate() {
            res[i] = c as u8;
        }

        res
    }

    pub fn hash(&self, password: &str) -> String {
        let mut pbkdf2_hash = [0u8; CREDENTIAL_LEN];
        pbkdf2::derive(
            pbkdf2::PBKDF2_HMAC_SHA512,
            self.n_iter,
            self.salt.as_bytes(),
            password.as_bytes(),
            &mut pbkdf2_hash,
        );
        EncryptionHelper::hash_to_string(pbkdf2_hash)
    }

    pub fn verify(&self, password: &str, hashed_str: String) -> ServiceResult<()> {
        let previously_derived = EncryptionHelper::string_to_hash(hashed_str);

        pbkdf2::verify(
            pbkdf2::PBKDF2_HMAC_SHA512,
            self.n_iter,
            self.salt.as_bytes(),
            password.as_bytes(),
            &previously_derived as &[u8],
        )
        .map_err(|e| ServiceError::InternalServerError(e.to_string()))
    }
}
