# FX Deals

## Overview
The FX Deals project processes foreign exchange transactions from uploaded files, validates the data, and stores it in a MySQL database.
It supports csv file formats and includes comprehensive validation to ensure data integrity.

## Modules
- [Controller](controller/README.md)
- [Factory](factory/README.md)
- [Parser](parser/README.md)
- [Processor](processor/README.md)
- [Validator](validator/README.md)

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Maven or Gradle
- Docker
- Postman or any other API testing tool

### Building the Project

1. **Build with Maven**: Build the project using Maven.
    ```bash
    mvn clean install
    ```

### Running the Application
1. **Run the Application using docker**: 
This will run mysql container + execute the sql commands under ./scripts/db.sql, and start the application on port 8080
    ```bash
    docker compose up
    ```

### Using the Application

#### Uploading a Transaction File
1. **Open Postman**: Open Postman or any other API testing tool.
2. **Create a New Request**:
    - **Method**: POST
    - **URL**: `http://localhost:8080/api/deals/upload`
3. **Add File**:
    - **Type**: Form-Data
    - **Key**: `file`
    - **Value**: Choose the file you want to upload (e.g., `sample-files/sample.csv`).
4. **Send Request**: Click the "Send" button to upload the file.

### Example Request
Here is an example of how to upload a file using `curl`:
```bash
curl -X POST -F 'file=@sample-files/sample.csv' http://localhost:8080/api/deals/upload
"# FX-Deals-Task" 
"# FX-Deals-Task" 
