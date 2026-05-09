# GuiaFacilMaven

GuiaFacilMaven is a Java Maven project used to collect utility and tax payment documents for real estate clients, including:

- IPTU (property tax)
- Electricity bills
- Water bills

## Project overview

This project uses Selenium to automate access to provider websites in Goiânia and download the required payment guides.

To handle the IPTU portal captcha challenge, the project integrates with the Death By Captcha API.

## Tech stack

- Java 8
- Maven
- Selenium
- Apache POI

## Build

```bash
mvn clean package
```
