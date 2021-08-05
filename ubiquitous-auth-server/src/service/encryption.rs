use crate::constant::CONFIG;
use crate::error::ServiceResult;
use crate::util::encryption_helper::EncryptionHelper;

pub fn hash_password(password: &str) -> ServiceResult<String> {
    let salt = CONFIG.secret_key.to_owned();
    let n = CONFIG.secret_len;
    let ecp = EncryptionHelper::new(n, salt);

    Ok(ecp.hash(password))
}

pub fn verify_password(hash: &str, password: &str) -> ServiceResult<bool> {
    let salt = CONFIG.secret_key.to_owned();
    let n = CONFIG.secret_len;
    let ecp = EncryptionHelper::new(n, salt);

    match ecp.verify(password, hash.to_owned()) {
        Ok(_) => Ok(true),
        Err(_) => Ok(false),
    }
}
