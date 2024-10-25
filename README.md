# Application 1 : Rule Engine with AST

## Description
The AST-Based Rule Engine is a Java application designed to parse, create, and evaluate complex rules using an Abstract Syntax Tree (AST) structure. This rule engine can be used for eligibility assessments based on various attributes like age, department, income, and spend. It provides an API to manage rules, enabling users to create, combine, and evaluate custom rules effectively.

## Features

- **Rule Creation**: Allows users to create rules based on attributes like age, department, income, and spend.
- **Rule Combination**: Supports combining multiple rules using an Abstract Syntax Tree (AST) structure, enabling more complex logic.
- **Rule Evaluation**: Provides functionality to evaluate user-defined rules against given attributes to determine eligibility.
- **API Management**: Offers REST full APIs to manage rules, including endpoints for rule creation, combination, evaluation, and deletion.
- **3-Tier Architecture**: The project is designed with a 3-tier architecture, separating the UI, API, and data storage layers.
- **Database Integration**: Stores rule data persistently in a relational database using Hibernate.
- **Error Handling**: Includes robust error-handling mechanisms to ensure smooth operation even with complex rules.

## Installation

### Prerequisites
- **Java 17** or higher
- **Spring Boot**
- **MySQL** (for database setup)
- **Maven** (for dependency management)
- **Postman** (for testing APIs)

### Steps

1. **Clone the Repository**  
   Clone the project repository from GitHub:
   ```bash
   git clone https://github.com/nidak7/ast-rule-engine.git
   cd ast-rule-engine

### Configuration

Before running the application, you need to configure the `application.properties` file located in `src/main/resources`. Below are the required properties and how to set them up.

#### Step 1: Database Configuration
You need to provide your MySQL database connection details in the `application.properties` file.

1. Open the `application.properties` file.
2. Set the following properties:

```properties
spring.application.name=ast-project
spring.datasource.url=jdbc:mysql://localhost:3306/zeotap_assignment_1
spring.datasource.username=<your-database-username>
spring.datasource.password=<your-database-password>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
 ```
## Usage

Once the application is running, you can use **Postman** to test the following endpoints.

### API Endpoints

#### 1. **Create a Rule**
- **Endpoint**: `POST /api/rules`
- **Description**: Creates a new rule based on attributes like age, department, income, and spend.
- **Request Body Example**:
  ```json
  {
    "attribute": "age",
    "operator": ">",
    "value": "18"
  }
  ```
- **Expected Response**:
  ```json
  {
    "id": 1,
    "attribute": "age",
    "operator": ">",
    "value": "18",
    "message": "Rule created successfully."
  }
  ```

#### 2. **Combine Rules**
- **Endpoint**: `POST /api/rules/combine`
- **Description**: Combines two rules using an operator (e.g., `AND`, `OR`) to create more complex conditions.
- **Request Body Example**:
  ```json
  {
    "rule1Id": 1,
    "rule2Id": 2,
    "operator": "AND"
  }
  ```
- **Expected Response**:
  ```json
  {
    "combinedRuleId": 3,
    "message": "Rules combined successfully."
  }
  ```

#### 3. **Evaluate a Rule**
- **Endpoint**: `POST /api/rules/evaluate`
- **Description**: Evaluates a rule based on provided attributes to determine if it meets the specified criteria.
- **Request Body Example**:
  ```json
  {
    "attributes": {
      "age": 25,
      "income": 50000
    },
    "ruleId": 1
  }
  ```
- **Expected Response**:
  ```json
  {
    "result": true,
    "message": "Rule evaluation successful."
  }
  ```

#### 4. **Modify a Rule**
- **Endpoint**: `PUT /api/rules/modify`
- **Description**: Modifies an existing rule based on provided parameters, including the rule itself, the attribute to change, the new operator, and the new value.
- **Request Body Example**:
  ```json
  {
    "rule": "age > 18",
    "attribute": "age",
    "newOperator": "<",
    "newValue": "25"
  }
  ```
- **Expected Response**:
  ```json
  {
    "rule": "age < 25",
    "message": "Rule modified successfully."
  }
  ```

