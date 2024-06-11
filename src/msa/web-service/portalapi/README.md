# Getting Started

### Generate keystore

keytool -genkey -alias portal-keystore -keyalg RSA -keystore portal-keystore.jks -keysize 2048

Pass: 123qwe

keytool -importkeystore -srckeystore portalkeystore.jks -destkeystore portalkeystore.jks -deststoretype pkcs12
