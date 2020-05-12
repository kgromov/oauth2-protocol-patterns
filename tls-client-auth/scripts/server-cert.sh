## create the CA certificate - PEM passphrase -> 'privatekey1234'
openssl req -x509 -sha256 -days 3650 -newkey rsa:4096 -keyout rootCA.key -out rootCA.crt

## certificate signing request (CSR)
openssl req -new -newkey rsa:4096 -keyout localhost.key -out localhost.csr

## sign the request with our rootCA.crt certificate and its private key
openssl x509 -req -CA rootCA.crt -CAkey rootCA.key -in localhost.csr -out localhost.crt -days 365 -CAcreateserial -extfile localhost.ext

## print server certificate
openssl x509 -in localhost.crt -text

## Create PKCS 12 archive
openssl pkcs12 -export -out localhost.p12 -name "localhost" -inkey localhost.key -in localhost.crt

## create a keystore.jks repository and import the localhost.p12 file with a single command
keytool -importkeystore -srckeystore localhost.p12 -srcstoretype PKCS12 -destkeystore keystore.jks -deststoretype JKS


## Mutual SSL - Client configuration

## create a truststore.jks file and import the rootCA.crt using keytool
keytool -import -trustcacerts -noprompt -alias ca -ext san=dns:localhost,ip:127.0.0.1 -file rootCA.crt -keystore truststore.jks

## certificate signing request (CSR)
openssl req -new -newkey rsa:4096 -nodes -keyout clientBob.key -out clientBob.csr

## sign the request with our CA
openssl x509 -req -CA rootCA.crt -CAkey rootCA.key -in clientBob.csr -out clientBob.crt -days 365 -CAcreateserial

## package the signed certificate and the private key into the PKCS file
openssl pkcs12 -export -out clientBob.p12 -name "clientBob" -inkey clientBob.key -in clientBob.crt