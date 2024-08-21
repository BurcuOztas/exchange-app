Exchange Application
--------------------
This is a Spring Boot application for currency conversion and fetching exchange rates using an external API. The application also supports storing and retrieving conversion history.

Features
-------------
Currency Conversion: Convert an amount from one currency to another.
Exchange Rates: Get real-time exchange rates.
History: View past conversion transactions.

Technologies
--------------
Java 17
Spring Boot
H2 Database
Docker
Getting Started
Prerequisites
Java 17
Docker
Running the Application
Clone the repository:

bash
Copy code
git clone https://github.com/BurcuOztas/exchange-app.git
cd exchange-app
Build and run with Docker:

bash
Copy code
docker-compose up --build
The app will be available at http://localhost:8080.

Access the H2 Database Console:
--------------------------------

Go to http://localhost:8080/h2-console.
Use:
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: password
API Endpoints
GET /api/exchange/rate: Get exchange rate between two currencies.
POST /api/conversion: Convert an amount between currencies.
GET /api/conversion/history/all: View conversion history.

Configuration
-------------
External API settings are in application.properties:

properties
----------
Copy code
external.api.url=http://data.fixer.io/api
external.api.key=fcbfcdf92765a20d896c6b46e94b646f

License
--------
This project is licensed under the MIT License.