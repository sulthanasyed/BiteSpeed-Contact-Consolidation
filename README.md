# BiteSpeed Identity Reconciliation

BiteSpeed Identity Reconciliation is a Spring Boot-based backend service developed to solve a real-world identity management problem, where users may be identified by either email or phone number or both across multiple records.
This service intelligently links such records by identifying existing contacts and establishing relationships between them to ensure accurate and consistent identity representation.


## 🚀 Features

- Accepts `email` and/or `phoneNumber` as input.
- Detects existing and related contacts.
- Ensures the earliest created contact is the `primary`.
- Adds new contacts as `secondary` if a match is found.
- Supports storing contact data in PostgreSQL.
- Dockerized for easy deployment.
- Deployable to Render.com.


## 🔧 Tech Stack

- Java 17+
- Spring Boot
- PostgreSQL
- Maven
- JPA / Hibernate
- Render.com (for deployment)


## 🚀 API Endpoint

### `POST /identify`

**Request Body**
```json
{
  "email": "sulthana@gmail.com",
  "phoneNumber": "1234567890"
}
```
**Response**
```json
{
  "contact": {
    "primaryContactId": 1,
    "emails": ["sulthana@gmail.com", "syed@gmail.com"],
    "phoneNumbers": ["1234567890"],
    "secondaryContactIds": [2]
  }
}
```


## ⚙️ Setup Instructions

### 1. Clone the Repository
```
   git clone https://github.com/sulthanasyed/BiteSpeed-Identity-Reconciliation.git
   
   cd BiteSpeed-Identity-Reconciliation
```

### 2. Configure PostgreSQL
Create a PostgreSQL database and note the credentials.
```
- Host
- Port
- Database
- Username
- Password
```

### 3. Update application.properties
    spring.datasource.url=jdbc:postgresql://<host>:<port>/<database>
    spring.datasource.username=<username>
    spring.datasource.password=<password>
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect


## ▶️ Run Locally
```
./mvnw spring-boot:run
```
   Access: http://localhost:8080/identify

## 🐳 Docker Instructions
### Build the Image
    docker build -t identity-reconciliation .
### Run the Container
    docker run -p 8080:8080 identity-reconciliation

## ☁️ Deploying to Render.com
### Prerequisites
- Dockerfile present in root.
- PostgreSQL database created on Render.
### Steps
```
1. Push Your Code to GitHub
2. Create a New Web Service from Render Dashboard
3. Select your GitHub repo. 
4. Choose Docker as environment. 
5. Build Command: ./mvnw clean install
6. Start Command: java -jar target/identity-reconciliation.jar
```

##   ✅ Run Tests

```./mvnw test```



## 📂 Project Structure

```
   src/
   ├── main/
   │   ├── java/
   │   │   └── com.bitespeed.contact_consolidation/
   │   │       ├── controller/
   │   │       ├── service/
   │   │       ├── repository/
   │   │       ├── model/
   │   │       ├── dto/
   │   │       └── exception/
   │   └── resources/
   │       └── application.properties
   ├── test/
   │   └── java/
   │       └── com.bitespeed.contact_consolidation/
   │           └── service/
   └── pom.xml
```


## 🧑‍💻 Author

    Nageena Sulthana
    Backend Developer | Java | Spring Boot