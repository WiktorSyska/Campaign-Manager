#  Campaign Manager

A web-based campaign management system that allows users to create, update, and manage advertising campaigns based on locations (towns) and keywords. Integrated with an account fund system to ensure financial consistency.

##  Tech Stack

- **Backend**: Java 17+, Spring Boot
- **Frontend**: HTML, Bootstrap, Vanilla JS / React components
- **Database**: JPA / Hibernate, H2
- **API**: RESTful
- **Validation**: Hibernate Validator (JSR-380)
- **Build Tool**: Maven
- **Styling**: Bootstrap 5

##  Getting Started

### Backend

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-user/campaign-manager.git
   cd campaign-manager
   ```

2. **Run the application**
   Using your IDE or via terminal:
   ```bash
   mvn spring-boot:run
   ```

3. **Access the API**
   Visit: `http://localhost:8080/api`

### Frontend

**Open `index.html` in your browser**
   Found in `src/main/resources/static/`.



##  Features

- ✅ Create, update, and delete campaigns
- ✅ Account fund management with balance validation
- ✅ Frontend form validation (min. amounts, radius, required fields)
- ✅ Dynamic UI with modals, tables, Select2 dropdowns
- ✅ Export campaign list to CSV

##  Project Structure

```
── main
  ├── java
  |   └── campaing_manager
  |      ├── config/             # CORS and app configs
  |      ├── controller/         # REST API endpoints
  |      ├── dto/                # Data Transfer Objects
  |      ├── model/              # Database entities
  |      ├── repository/         # JPA repositories
  |      ├── service/            # Business logic
  |      └── Application.java    # Main Spring Boot app
  └── res   
     ├── static/               
     |  ├── css/                 # CSS
     |  ├── js/                  # JS
     |  └── index.html           # HTML
     └── application.properties  # Application properties
```

##  API Endpoints

### Campaigns

- `GET /api/campaigns` – list all campaigns
- `GET /api/campaigns/{id}` – get campaign by ID
- `POST /api/campaigns` – create a new campaign
- `PUT /api/campaigns/{id}` – update campaign
- `DELETE /api/campaigns/{id}` – delete campaign

### Towns

- `GET /api/towns` – list of available towns

### Keywords

- `GET /api/keywords` – list of available keywords
- `GET /api/keywords/search?q=term` – search for keywords

### Account

- `GET /api/account/balance` – get current account balance  
  *(Funds are automatically managed during campaign creation/updates)*

##  Validation & Error Handling

- Field-level validation using JSR-380 annotations
- Centralized error handling with meaningful messages (`GlobalExceptionHandler`)
- Transactional operations ensure consistency during fund changes

##  Test Data

Automatically loaded via the `DataLoader` class on startup:

- **Account**: `Emerald Account` with 10,000.00 $ balance
- **Towns**: 15 cities (e.g., London, Tokyo, Barcelona)
- **Keywords**: 30+ sample keywords across industries


##  Testing

This project includes a comprehensive test suite covering controllers, services, and integration behavior.

###  Types of Tests

- **Unit Tests**: Services such as `CampaignService`, `KeywordService`, and `TownService` are tested with mocked repositories using JUnit and Mockito.
- **Controller Tests**: REST API endpoints are tested using `@WebMvcTest` with `MockMvc`, covering both success and error scenarios.
- **Integration Tests**: End-to-end validation of application context, database connectivity, and API response correctness with `@SpringBootTest`.

###  Tools

- JUnit 5 (Jupiter)
- Spring Boot Test (`@WebMvcTest`, `@SpringBootTest`)
- Mockito (`@MockBean`, `@InjectMocks`)
- TestRestTemplate (for integration testing)

To run all tests:
```bash
mvn test
```


