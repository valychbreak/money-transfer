# money-transfer
Small web application, demonstrating asset transferring from one account to another

## Stack
 - Java 12
 - [Spark Java](https://github.com/perwendel/spark)
 - [Guice](https://github.com/google/guice)
 - [Hibernate](https://hibernate.org/)
 - [HSQLDB - in-memory DB](http://hsqldb.org/)
 - [Project Lombok](https://projectlombok.org/)
 - [Rest assured](https://github.com/rest-assured/rest-assured)
 
## Running the application

1. Build project:

`mvn clean install -DskipTests`

2. Run jar file:

`java -jar target/money-transfer-1.0-SNAPSHOT.jar`

## Running tests

All tests:

`mvn test`

/transfer route test:

`mvn test -Dtest=AssetTransferRouteTest`

## Testing endpoints

Endpoint for transfer:

`curl -d "sender_account=<account_number>&receiver_account=<account_number>&asset_amount=<amount>" -X POST http://localhost:8080/transfer`

For the sake of testing, 2 accounts are being created on startup:
 - 19806578940000111122223333
 - 19806578940000999988887777

To check their balance:

`curl -X GET http://localhost:8080/account?account_number=19806578940000111122223333`

Result: `{"id":1,"number":"19806578940000111122223333","balance":{"amount":1000000.00}}`

`curl -X GET http://localhost:8080/account?account_number=19806578940000999988887777`

Result: `{"id":2,"number":"19806578940000999988887777","balance":{"amount":1000000.00}}}`

### Example

Transfering assets between accounts above:

`curl -d "sender_account=19806578940000111122223333&receiver_account=19806578940000999988887777&asset_amount=15789.56" -X POST http://localhost:8080/transfer`

Checking balances of both accounts:

`curl -X GET http://localhost:8080/account?account_number=19806578940000111122223333`

`curl -X GET http://localhost:8080/account?account_number=19806578940000999988887777`
