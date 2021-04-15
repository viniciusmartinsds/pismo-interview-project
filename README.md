# Pismo Interview Project

## About the project
* Java 11
* Spring Boot
* JUnit5
* [Testcontainer](https://www.testcontainers.org) (DB integration test)
* Redis (cache)
* PostgreSQL (database)
* [Pitest](https://pitest.org) (mutation test)
* [Liquibase](https://www.liquibase.org) (Quickly and organize DB changes)
* [OpenAPI Swagger](https://swagger.io/specification/) ([API-First Approach](https://swagger.io/resources/articles/adopting-an-api-first-approach/)), Project Definitions: [Swagger](https://github.com/viniciusmartinsds/pismo-interview-project/blob/main/src/main/resources/swagger.yml)


## Diagrams defined for system flows

### Create Account Diagram (*localhost:8080/pismo-interview/v1/accounts*)
![Create account diagram](/src/main/resources/diagrams/create_account.png)

### Get Account Diagram (*localhost:8080/pismo-interview/v1/accounts/:accountId*)
![Create account diagram](/src/main/resources/diagrams/get_account.png)

### Create Transaction Diagram (*localhost:8080/pismo-interview/v1/transactions*)
![Create account diagram](/src/main/resources/diagrams/create_transaction.png)

#### Contracts: http://localhost:8080/pismo-interview/v1/swagger-ui/

## Build and Run the project
In the root folder, we need to execute the [Docker Compose](https://github.com/viniciusmartinsds/pismo-interview-project/blob/main/docker-compose.yml) file(Docker must be installed).
On our terminal, run the following command: 
```bash
docker-compose up -d
```
will up the PostgreSQL and Redis instance, wait for everything starts.

After that, on our root folder run [maven](https://maven.apache.org) command:
```bash
mvn clean verify spring-boot:run
```
and the project will starts;

## Run our mutation test
It's very important mutation tests on our project, mostly to accurence our code.
To run that, on our root folder, run the command: 
```bash
mvn clean verify org.pitest:pitest-maven:mutationCoverage
```
After finish, all tests are saved at the folder target/pit-reports/* where * is the test date, just open the folder and the index.html file with our report will be there, just open it.
