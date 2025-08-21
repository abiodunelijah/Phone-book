# Phone Book Application

A comprehensive REST API for managing contact addresses built with Spring Boot, JPA, and MySQL.

## Features

### Contact Management
- **Create Contact**: Add new contacts with complete address information
- **View All Contacts**: Retrieve a list of all stored contacts
- **View Single Contact**: Get detailed information for a specific contact by ID
- **Update Contact**: Modify existing contact information (partial updates supported)
- **Delete Contact**: Remove contacts from the phone book
- **Search Contacts**: Find contacts using flexible search criteria

### Contact Information Fields
- First Name
- Last Name
- Other Name (optional)
- Email Address
- Phone Number
- Physical Address
- Auto-generated ID and creation timestamp

### Advanced Search Capabilities
Search for contacts using any combination of:
- First Name (partial match, case-insensitive)
- Last Name (partial match, case-insensitive)  
- Email Address (partial match, case-insensitive)
- Phone Number (partial match, case-insensitive)

## Technology Stack

- **Backend Framework**: Spring Boot 3.5.4
- **Database**: MySQL
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Java Version**: 21
- **Additional Libraries**:
  - Lombok (for reducing boilerplate code)
  - MySQL Connector/J
  - Spring Boot Test

## API Endpoints

### Base URL: `/api/v1/contacts`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/create-contact` | Create a new contact |
| GET | `/all-contacts` | Get all contacts |
| GET | `/contact-address/{id}` | Get contact by ID |
| PATCH | `/update-contact/{id}` | Update existing contact |
| DELETE | `/delete-contact/{id}` | Delete contact by ID |
| GET | `/search-contact` | Search contacts with query parameters |

### Search Parameters
- `firstName` - Search by first name
- `lastName` - Search by last name
- `email` - Search by email address
- `phoneNumber` - Search by phone number

Multiple parameters can be combined for more specific searches.

## Database Configuration

The application uses MySQL database with the following default configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/phonebook
spring.datasource.username=coder2client
spring.datasource.password=pastoral2u
spring.jpa.hibernate.ddl-auto=update
```

## Getting Started

### Prerequisites
- Java 21 or higher
- MySQL Server
- Maven 3.6+ (or use included Maven wrapper)

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd phonebook
   ```

2. **Create MySQL Database**
   ```sql
   CREATE DATABASE phonebook;
   ```

3. **Configure Database**
   Update `src/main/resources/application.properties` with your MySQL credentials if different from defaults.

4. **Run the Application**
   ```bash
   # Using Maven wrapper (recommended)
   ./mvnw spring-boot:run
   
   # Or using installed Maven
   mvn spring-boot:run
   ```

5. **Access the API**
   The application will start on `http://localhost:8080`

## Example API Usage

### Create a Contact
```http
POST http://localhost:8080/api/v1/contacts/create-contact
Content-Type: application/json

{
  "firstName": "Sam",
  "lastName": "Barth",
  "otherName": "Howels",
  "email": "sam@mail.com",
  "phoneNumber": "0807644322",
  "address": "Abuja"
}
```

### Search Contacts
```http
GET http://localhost:8080/api/v1/contacts/search-contact?email=sam@mail.com&phoneNumber=8858473879
```

### Update Contact
```http
PATCH http://localhost:8080/api/v1/contacts/update-contact/1
Content-Type: application/json

{
  "firstName": "Updated Name",
  "address": "New Address"
}
```

## Error Handling

The application includes comprehensive error handling with:
- **Global Exception Handler**: Centralized error processing
- **Custom Exceptions**: `ResourceNotFoundException` for missing resources
- **Structured Error Response**: Consistent error format with timestamp, message, path, and status code

## Architecture

The application follows a layered architecture:

- **Controller Layer**: REST endpoints (`ContactAddressController`)
- **Service Layer**: Business logic (`ContactAddressService` & `ContactAddressServiceImpl`)
- **Repository Layer**: Data access (`ContactAddressRepository`)
- **Entity Layer**: JPA entities (`ContactAddress`)
- **DTO Layer**: Data transfer objects (`ContactAddressDTO`)
- **Mapper Layer**: Entity-DTO conversion (`ContactAddressMapper`)

## Testing

Run tests using:
```bash
./mvnw test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is open source and available under the [Apache License](LICENSE).
