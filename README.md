# Payments REST API
This API consists of a payment model using Java 8 resources and application of techniques related to the test pyramid.
It's an example of one that sells digital goods like music downloads, videos and images to be used in advertising campaigns.
The model is based on three entities: product, customer and payment. 
The REST API exposes features that provide relevant information about payments. These payments are fictitious data inserted via database migrations.
Has a service layer to handle business rules and a REST communication interface with JSON data structure.

## About The Project
The purpose of this API is to test some features available from Java 8 and apply concepts from the test pyramid.

### Key Features
* Payments
* REST API
* Unit Tests
* Lambdas
* Default Methods
* Method References
* Streams and Collectors
* Java Time
* Integration Tests
* End-to-end Tests
* Containers

### API Resources

<br>

<span style="color:#006600">**GET**</span> ```/payments-api/v1/payments/sorted-by-date``` (list payments sorted by date)
<br>
**Response sample:**
```javascript
[{
	"date": "2021-06-06T00:00:00",
	"customer": "João da Silva",
	"products": [{
		"name": "U2 Album"
	}, {
		"name": "Oasis Album"
	}, {
		"name": "The Killers Album"
	}]
}]
```

<br>

<span style="color:#006600">**GET**</span> ```/payments-api/v1/payments/total-amount``` (total payments made over time)
<br>
**Response sample:**
```javascript
{"totalAmount":1315.00}
```

<br>

<span style="color:#006600">**GET**</span> ```/payments-api/v1/payments/top-selling-products``` (list the best selling products)
<br>
**Response sample:**
```javascript
[{
	"product": "Oasis Album",
	"quantity": 3
}, {
	"product": "The Killers Album",
	"quantity": 2
}, {
	"product": "ACDC Album",
	"quantity": 1
}, {
	"product": "U2 Album",
	"quantity": 1
}]
```

<br>

<span style="color:#006600">**GET**</span> ```/payments-api/v1/payments/top-billing-products``` (list the most billed products)
<br>
**Response sample:**
```javascript
[{
	"product": "Oasis Album",
	"amount": 300.00
}, {
	"product": "The Killers Album",
	"amount": 250.00
}, {
	"product": "U2 Album",
	"amount": 150.00
}, {
	"product": "ACDC Album",
	"amount": 90.00
}]
```

<br>

<span style="color:#006600">**GET**</span> ```/payments-api/v1/payments/products-by-customer``` (list the products sold by each customer)
<br>
**Response sample:**
```javascript
[{
	"customer": "Paulo Costa",
	"products": [{
		"name": "The Killers Album"
	}, {
		"name": "Calvin Harris Album"
	}, {
		"name": "Oasis Album"
	}]
}]
```

<br>

<span style="color:#006600">**GET**</span> ```/payments-api/v1/payments/customers-billing``` (lists the amount billed by customer)
<br>
**Response sample:**
```javascript
[{
	"customer": "João da Silva",
	"amount": 375.00
}, {
	"customer": "Paulo Costa",
	"amount": 310.00
}]
```

<br>

<span style="color:#006600">**GET**</span> ```/payments-api/v1/payments/monthly-billing``` (list monthly billing)
<br>
**Response sample:**
```javascript
[{
	"year": 2021,
	"month": 6,
	"amount": 1135.00
}, {
	"year": 2021,
	"month": 5,
	"amount": 180.00
}]
```

### Built With
* [IntelliJ IDEA](https://www.jetbrains.com/pt-br/idea/) - The IDE used
* [Java 8](https://www.java.com/pt-BR/) - Execution platform
* [Spring Boot - 2.5.0](https://spring.io/projects/spring-boot) - Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run"
* [PostgreSQL](https://www.postgresql.org/) - The database used
* [Project Lombok](https://projectlombok.org/) - Repetitive code reduction
* [JUnit](https://junit.org/junit5/) - Unit and integration tests
* [Flyway](https://flywaydb.org/) - Version control for database
* [MapStruct](https://mapstruct.org/) - Code generator that greatly simplifies the implementation of mappings between Java bean types based on a convention over configuration approach.
* [Docker](https://www.docker.com/) - Operating system-level virtualization.
* [H2 Database](https://www.h2database.com/html/main.html) - In memory database for integration tests
* [Mockito](https://site.mockito.org/) - Tasty mocking framework for unit tests
* [REST Assured](https://rest-assured.io/ - Testing and validating REST services

## Getting Started
To get a local copy up and running follow these simple steps.

### Prerequisites
This project requires Java 8 or higher and use an IDE of your choice that supports Maven.

### Installation
Clone the repo
```
git clone https://github.com/ezequieljuliano/payments-api.git
```

## Usage
Open the project in your preferred IDE and run the main class **PaymentsApplication**.

Use Postman, or your browser to call the main resource or:
```
curl http://localhost:8080/payments-api/v1/payments
```

## Usage with Docker
Execute the command below in the terminal to generate the application's artifact and build the application's docker image:
```
mvn clean package dockerfile:build
```

Now execute:
```
docker-compose up
```

### Junit Tests
Run the unit tests and check that everything is fine. 
Unit tests, integration tests and end-to-end tests were implemented.

## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the APACHE LICENSE. See `LICENSE` for more information.

## Contact

To contact us use the options:
* E-mail  : ezequieljuliano@gmail.com
* Twitter : [@ezequieljuliano](https://twitter.com/ezequieljuliano)
* Linkedin: [ezequieljuliano](https://www.linkedin.com/in/ezequieljuliano)

## Project Link
[https://github.com/ezequieljuliano/payments-api](https://github.com/ezequieljuliano/payments-api)