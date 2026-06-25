# Digital Banking Portal
### Java Web Application | JSP + Servlets + JDBC + MySQL

A full-featured Internet Banking System built using Core Java, JSP, Servlets, JDBC, and MySQL.
Developed as a portfolio project targeting Digital Banking domain roles.

---

## Features
- User Registration & Login (Session management)
- Account Dashboard (Balance Inquiry)
- Fund Transfer (with balance validation)
- Transaction History (last 10 transactions)
- Beneficiary Management (Add / Delete)
- Logout

---

## Tech Stack
| Layer      | Technology                  |
|------------|-----------------------------|
| Frontend   | JSP, HTML, CSS              |
| Backend    | Java Servlets (javax.servlet)|
| Database   | MySQL + JDBC                |
| Server     | Apache Tomcat 9+            |
| Build Tool | Maven                       |

---

## Project Structure
```
DigitalBankingPortal/
├── database/
│   └── schema.sql                  ← Run this first in MySQL
├── src/main/
│   ├── java/com/banking/
│   │   ├── model/                  ← POJOs (User, Account, Transaction, Beneficiary)
│   │   ├── dao/                    ← Database access layer (JDBC)
│   │   ├── servlet/                ← Servlet controllers
│   │   └── util/                   ← DBConnection utility
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── views/              ← JSP pages
│       │   └── web.xml
│       └── css/style.css
└── pom.xml
```

---

## Setup Instructions

### Prerequisites
- JDK 11+
- Apache Tomcat 9+
- MySQL 8+
- Maven 3+
- IntelliJ IDEA / Eclipse

### Step 1: Database Setup
```sql
-- Open MySQL and run:
source /path/to/database/schema.sql
```

### Step 2: Configure DB Connection
Edit `src/main/java/com/banking/util/DBConnection.java`:
```java
private static final String PASSWORD = "your_mysql_password";
```

### Step 3: Build
```bash
mvn clean package
```
This generates `target/DigitalBankingPortal-1.0.war`

### Step 4: Deploy
- Copy the `.war` file to `tomcat/webapps/`
- Start Tomcat: `bin/startup.sh` (Linux) or `bin/startup.bat` (Windows)
- Open: `http://localhost:8080/DigitalBankingPortal-1.0/login`

### Sample Login Credentials
| Username   | Password  |
|------------|-----------|
| john_doe   | john123   |
| jane_smith | jane123   |

---

## Key Concepts Demonstrated
- **OOP**: Model classes with encapsulation, DAO pattern
- **Collections**: ArrayList for transaction/beneficiary lists
- **JDBC**: PreparedStatement, ResultSet, Connection management
- **Servlets**: doGet/doPost, HttpSession, RequestDispatcher
- **JSP**: Scriptlets, EL expressions, form handling
- **SQL**: DDL, DML, Foreign Keys, Normalization

---

*Built by Yash Jain | MCA 2027 | VIT Chennai*
