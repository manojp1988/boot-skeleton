# boot-skeleton

This project aims to provide a skeleton to develop spring boot backend application for the UI front end.

## Configurations added

- logback configuration externalized. Also configured rolling file configuration to archive old log files. And MDC is configured to add unique id for the transaction.
- ehcache configured.
- environment specific properties file controlled using the env variable -Dspring.profiles.active=dev
- Added configuration to provide war file as artifact in case if required.
- Spring security configured.
- Spring actuator enabled for all types.
- Flyway db migration configured.
- JWT Based authentication added.
- CORS Filter added.
- Auditing capabilities added

## Settings
- To generate keystore run the following command.
         
        keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650 -storepass password
  
