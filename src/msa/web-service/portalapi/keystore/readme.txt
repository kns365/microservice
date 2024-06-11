keytool -genkey -alias portalkeystore -keyalg RSA -keystore portalkeystore.jks -keysize 2048

Pass: 123qwe

keytool -importkeystore -srckeystore portalkeystore.jks -destkeystore portalkeystore.jks -deststoretype pkcs12