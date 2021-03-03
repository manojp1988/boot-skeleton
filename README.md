# boot-skeleton

This project aims to provide a skeleton to develop spring boot backend application for the UI front end.

## Configurations added

- logback configuration externalized. Also configured rolling file configuration to archive old log files. And MDC is configured to add unique id for the transaction.
- ehcache configured.
- environment specific properties file controlled using the env variable -Dspring.profiles.active=dev
- Added configuration to provide war file as artifact in case if required.
