use std::convert::TryInto;

use crate::constant::CFG;
use crate::error::ServiceResult;
use crate::util::encryption_helper::EncryptionHelper;

pub fn hash_password(password: &str) -> ServiceResult<String> {
    let salt = CFG.get("SECRET_KEY").unwrap().clone().try_into()?;
    let n = CFG.get("SECRET_LEN").unwrap().clone().try_into()?;
    let ecp = EncryptionHelper::new(n, salt);

    Ok(ecp.hash(password))
}

pub fn verify_password(hash: &str, password: &str) -> ServiceResult<bool> {
    let salt = CFG.get("SECRET_KEY").unwrap().clone().try_into()?;
    let n = CFG.get("SECRET_LEN").unwrap().clone().try_into()?;
    let ecp = EncryptionHelper::new(n, salt);

    match ecp.verify(password, hash.to_owned()) {
        Ok(_) => Ok(true),
        Err(_) => Ok(false),
    }
}
