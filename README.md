
`# Currency Discount Calculator

## Overview

The Currency Discount Calculator is a Spring Boot application that calculates the final amount to be paid after applying discounts based on user types (Employee, Affiliate, Customer) and converting the total amount to a specified currency. The application utilizes a strategy design pattern to apply different discount strategies based on the user type.

## Features

- **Discount Calculation**: Calculates discounts for different user types using the Strategy Pattern.
- **Currency Conversion**: Converts the final amount to a target currency based on real-time exchange rates.
- **RESTful API**: Provides an endpoint to calculate the total amount based on user details and item list.

## Technologies Used

- Java 17
- Spring Boot 3.4.4
- Spring Web
- Spring Security (optional, for token-based authentication)
- JUnit 5
- Mockito for unit testing

## UML Class Diagram
![img_2.png](img_2.png)

## Setup

### Prerequisites

- Java Development Kit (JDK) 17
- Apache Maven
- Redis (for caching, optional)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/currency-discount-calculator.git
   cd currency-discount-calculator` 

2.  **Build the project**:

    bash

    Copy code

    `mvn clean install`

3.  **Run the application**:

    bash

    Copy code

    `mvn spring-boot:run`

4.  **Access the application**: Open your browser and navigate to `http://localhost:8080/api/calculate`.


## API Usage

### Endpoint

**POST** `/api/calculate`

### Request Body

The request body should be in JSON format as follows:

json

Copy code

`{
"user": {
"userType": "EMPLOYEE", // Options: EMPLOYEE, AFFILIATE, CUSTOMER
"customerTenure": 1 // Required for CUSTOMER type
},
"totalAmount": 1450.0,
"originalCurrency": "USD",
"targetCurrency": "EUR",
"items": [
{
"itemId": "1", // Unique identifier for the item
"name": "Laptop",
"category": "Non-Groceries",
"price": 1200.0
},
{
"itemId": "2",
"name": "Monitor",
"category": "Non-Groceries",
"price": 250.0
},
{
"itemId": "3",
"name": "Bread",
"category": "GROCERIES",
"price": 2.0
}
]
}`

### Response

The response will return the converted amount after applying the discounts:

json

Copy code

`{
"convertedAmount": 1400.0
}`

## Testing

To run the unit tests, use the following command:

bash

Copy code

`mvn test`

### Example Test Cases

-   The test cases verify the functionality of discount calculations for different user types.
-   Mocking is utilized for dependencies to ensure isolated testing.




## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

-   Thanks to the Spring Boot community for their extensive documentation and support.
-   Special thanks to contributors for their feedback and contributions.

## Contributing

If you'd like to contribute to this project, please fork the repository and submit a pull request.

For any issues or feature requests, please create an issue on GitHub.

markdown

Copy code

``### Notes:

- Replace `https://github.com/yourusername/currency-discount-calculator.git` with the actual URL of your repository.
- Ensure that any specific configuration or dependencies needed for Redis or other components are mentioned under the "Setup" section if applicable.
- This `README.md` provides a structured overview, making it easier for users and developers to understand and contribute to your project.``

4o mini