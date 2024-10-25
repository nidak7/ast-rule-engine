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
