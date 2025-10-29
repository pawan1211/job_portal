🧑‍💼 Job Portal REST API

A Spring Boot–based Job Portal Application where recruiters can post jobs and candidates can search and apply for them.
Built with Spring Boot, Spring Security (JWT), Spring Data JPA, and MySQL.

🚀 Features

Candidate & Recruiter registration with role-based authentication

JWT-based login & token validation

Recruiters can post and manage jobs

Candidates can search and apply for jobs

Secure endpoints for protected access

Docker & environment-ready configuration

⚙️ Tech Stack
Layer	Technology
Backend	Spring Boot 3, Java 17
Security	Spring Security + JWT
Database	MySQL
ORM	Spring Data JPA
Build Tool	Maven
Containerization	Docker (optional)
🧩 Project Structure
src/
 ├── main/java/com/example/job_portal/
 │   ├── controller/        # REST controllers
 │   ├── model/             # JPA entities
 │   ├── dto/               # dto
 │   ├── repository/        # Spring Data repositories
 │   ├── service/           # Business logic layer
 │   ├── security/          # JWT filter & configuration
 │   └── JobPortalApplication.java
 └── resources/
     ├── application.properties
     └── schema.sql / data.sql (optional)

🛠️ Setup Instructions
1. Clone the Repository
git clone https://github.com/yourusername/job-portal.git
cd job-portal

2. Configure the Database

Update your src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/job_portal
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=your_secret_key_which_is_long_enough
jwt.expiration=86400000
server.port=8080

3. Build & Run
mvn clean install
mvn spring-boot:run

4. (Optional) Run in Docker
docker build -t job-portal-api .
docker run -p 8080:8080 job-portal-api

🔐 Authentication Flow

Register as a Candidate or Recruiter

Login to receive a JWT Token

Use this token for secured API calls (in the header):

Authorization: Bearer <your_jwt_token>

📬 API Documentation (Postman Collection)
1️⃣ Auth APIs
Method	Endpoint	Description

POST	/api/auth/register/candidate	Register new candidate

POST	/api/auth/register/recruiter	Register new recruiter

POST	/api/auth/login	Login and get JWT token

Example: Register Candidate
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "experience": 3.5,
  "skills": "Java, Spring Boot, MySQL",
  "location": "Pune"
}

2️⃣ Candidate APIs
Method	Endpoint	Description
GET	/api/candidates/profile	View candidate profile

PUT	/api/candidates/profile	Update candidate profile

3️⃣ Job APIs
Method	Endpoint	Description
GET	/api/jobs	List all jobs (public)

GET	/api/jobs/{id}	View job details

GET	/api/jobs/search?skill=Java&location=Bangalore	Search jobs

POST	/api/jobs	Post new job (Recruiter only)

GET	/api/jobs/mine	View jobs posted by recruiter

POST	/api/jobs/{id}/apply	Apply to a job (Candidate only)

GET	/api/jobs/applications/{jobId}	View applicants for a specific job (Recruiter only)
💡 Example Search

GET /api/jobs/search?skill=Java&location=Bangalore

✅ Expected Response:

[
  {
    "id": 1,
    "title": "Backend Developer",
    "description": "Spring Boot microservices experience required",
    "requiredSkills": "Java, Spring Boot, MySQL",
    "location": "Bangalore",
    "experienceRequired": 2
  }
]

⚠️ Common Errors
Error	Cause
403 Forbidden	Missing or invalid JWT token
401 Unauthorized	Token expired or incorrect credentials
500 Server Error	Database connection or serialization issue
