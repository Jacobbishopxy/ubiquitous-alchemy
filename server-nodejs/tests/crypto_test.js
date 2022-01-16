import crypto from 'crypto'

const aes256gcm = (key) => {

  const encrypt = (str) => {
    const iv = new crypto.randomBytes(12)
    const cipher = crypto.createCipheriv('aes-256-gcm', key, iv)

    let enc1 = cipher.update(str, 'utf8')
    let enc2 = cipher.final()
    return Buffer.concat([enc1, enc2, iv, cipher.getAuthTag()]).toString("base64")
  }

  const decrypt = (enc) => {
    enc = Buffer.from(enc, "base64")
    const iv = enc.slice(enc.length - 28, enc.length - 16)
    const tag = enc.slice(enc.length - 16)
    enc = enc.slice(0, enc.length - 28)
    const decipher = crypto.createDecipheriv('aes-256-gcm', key, iv)
    decipher.setAuthTag(tag)
    let str = decipher.update(enc, null, 'utf8')
    str += decipher.final('utf8')
    return str
  }

  return {
    encrypt,
    decrypt,
  }
}

function str2ab(str) {
  var buf = new ArrayBuffer(str.length * 2) // 2 bytes for each char
  var bufView = new Uint16Array(buf)
  for (var i = 0, strLen = str.length; i < strLen; i++) {
    bufView[i] = str.charCodeAt(i)
  }
  return buf
}

const key = 'secret_key_for_hash_password_and_verify'

const cipher = aes256gcm(Buffer.alloc(32, key))

{
  const msg = "{\"email\":\"jacob@example.com\",\"nickname\":\"Jacob\"}"

  const ct = cipher.encrypt(msg)
  console.log(ct.toString('base64'))

  const pt = cipher.decrypt(ct)

  console.log(pt)
}
