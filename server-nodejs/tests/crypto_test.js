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


function toUTF8Array(str) {
  let utf8 = []
  for (let i = 0; i < str.length; i++) {
    let charcode = str.charCodeAt(i)
    if (charcode < 0x80) utf8.push(charcode)
    else if (charcode < 0x800) {
      utf8.push(0xc0 | (charcode >> 6),
        0x80 | (charcode & 0x3f))
    }
    else if (charcode < 0xd800 || charcode >= 0xe000) {
      utf8.push(0xe0 | (charcode >> 12),
        0x80 | ((charcode >> 6) & 0x3f),
        0x80 | (charcode & 0x3f))
    }
    // surrogate pair
    else {
      i++
      // UTF-16 encodes 0x10000-0x10FFFF by
      // subtracting 0x10000 and splitting the
      // 20 bits of 0x0-0xFFFFF into two halves
      charcode = 0x10000 + (((charcode & 0x3ff) << 10)
        | (str.charCodeAt(i) & 0x3ff))
      utf8.push(0xf0 | (charcode >> 18),
        0x80 | ((charcode >> 12) & 0x3f),
        0x80 | ((charcode >> 6) & 0x3f),
        0x80 | (charcode & 0x3f))
    }
  }
  return utf8
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
