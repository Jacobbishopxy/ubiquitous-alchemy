
import CryptoES from "crypto-es"

// {

//   let encrypted = CryptoES.DES.encrypt("Message", "Secret Passphrase")
//   console.log(1, encrypted)

//   let decrypted = CryptoES.DES.decrypt(encrypted, "Secret Passphrase")
//   console.log(2, decrypted.toString(CryptoES.enc.Utf8))

// }


{
  const key = 'secret_key_for_hash_password_and_verify'

  const msg = "{\"email\":\"jacob@example.com\",\"nickname\":\"Jacob\"}"

  let encrypted = CryptoES.AES.encrypt(msg, key)

  console.log(encrypted.toString())

  let decrypted = CryptoES.AES.decrypt(encrypted, key).toString(CryptoES.enc.Utf8)

  console.log(decrypted)
}

// {
//   const key = 'secret_key_for_hash_password_and_verify'

//   let msg = 'q0f9jKLDboNKvSBCcCrqocu2Ydz1M/+irt5dBQvGFlZ1w+LOgdkKnC6Iu64ydHkE6/eZev8lfIQg0SNtxt+Rb9x6iNpwwXJ5lJ0yJwfC7pOIqmqb3E2112y89UG12f37RTNJ6/6isCygYWB8zBNa+v/tE0mT6ePR1sPGYgiKvdU0Yu+vGm4eHZo6gv3QE+zgFbLT2aUjvav3Cqc0mKpSqS1rOvYxr2jrbPz5Q23nysIVDPpC9Lv6PmwlMN5gkGpXlwcVn2CdFHyxEHfqbflE2mtUf1oL/g=='

//   msg = msg.toString('base64')

//   console.log(msg)

//   let decrypted = CryptoES.AES.decrypt(msg, key).toString()

//   console.log(decrypted)
// }

