![CI](https://github.com/kunalhiray7/loan-application-service/workflows/CI/badge.svg?branch=master)

# Loan Application Service

A simple microservice that provides RESTful APIs to manage loan applications from customers.

### Prerequisites
* You should have [npm](https://www.npmjs.com/) already installed.
* You will need [newman](https://github.com/postmanlabs/newman) and [json-server](https://github.com/typicode/json-server) for running the application
and integration tests. Please refer `setup.sh` for more details.

### Building project and Running tests
* Run `./gradlew clean build` for building the project and running the unit test cases.

### How to run locally??
* Run the mock `json-server` as mentioned in the `Prerequisites` section.
* Run the `loan-application-service` by running `./gradlew bootrun`.
* The server should be running on `http://localhost:8080`.

### Swagger-UI
* Visit `http://localhost:8080/swagger-ui.html` for API documentation and testing the API.

### Integration Tests
* The integration tests are written using `newman`.
* Refer point #2 from `Prerequisites` before running integration tests.
* Execute `./integration-tests.sh` from `integration-tests` folder. The result of the integration tests will printed on console. 

### CI - Continuous Integration
* Every commit on master will trigger the GitHub action for CI. It will make sure that all the tests are green.

### ADR - Architecture Design Records
* All architecture decisions are recorded and maintained in `./adr` directory.

### Logging
* If you have ELK and logstash running on `localhost:5044`, you will see the logs in Kibana. 
However, this may not always work due to missing configuration and version mismatch. You can see the logs on console if running locally.

### Monitoring
* The application monitoring is achieved through Spring Actuator and Prometheus.
* Please refer `./monitoring/README.md` for more details.
* You can see the Prometheus dashboard at `http://localhost:9090`.
* You can see the Grafana dashboard at `http://localhost:3000`.
* The Grafana dashboard is configured with a prometheus dashboard. If it is not loaded by default, 
please add the `datasource` again and refresh the dashboard.

### Working with mock json-server
* The mock `json-server` follows RESTful specification.
* If you want to add new mock `customer` you can hit `POST http://localhost:8000/api/customers` with json body like-
````json
{
    "userId": 3,
    "firstName": "Steve",
    "lastName": "Smith",
    "email": "stevesmith@example.com",
    "phone": "+49 9876543210"
}
````
