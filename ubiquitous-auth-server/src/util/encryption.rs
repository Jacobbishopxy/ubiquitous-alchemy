use std::convert::TryInto;

use crate::constant::CFG;
use crate::error::ServiceResult;
use crate::util::encryption_helper::EncryptionHelper;

pub fn hash_password(password: &str) -> ServiceResult<String> {
    let salt: String = CFG.get("SECRET_KEY").unwrap().clone().try_into()?;
    let n: u32 = CFG.get("SECRET_LEN").unwrap().clone().try_into()?;
    let ecp = EncryptionHelper::new(n, salt);

    Ok(ecp.hash(password))
}
