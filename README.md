# receipt-processor

This project implements a receipt processing web service using Java and Spring Boot.

## Running the Application with Docker

### Prerequisites
- Docker installed.

### Steps to Run
1. Make sure the Dockerfile is in the folder.

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY /receiptprocessor/target/receiptprocessor-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

2. Build the Docker image:
   ```sh
   docker build -t receipt-processor .
   ```

3. Run the Docker container:
   ```sh
   docker run -p 8080:8080 receipt-processor
   ```

## API Endpoints

### Process Receipts
- **Path:** `/receipts/process`
- **Method:** `POST`
- **Payload:** JSON receipt
- **Response:** JSON containing an id for the receipt.
- **Example Response:**
{ "id": "7fb1377b-b223-49d9-a31a-5a02701dd310" }


### Get Points
- **Path:** `/receipts/{id}/points`
- **Method:** `GET`
- **Response:** A JSON object containing the number of points awarded.
- **Example Response:**
{ "points": 32 }
