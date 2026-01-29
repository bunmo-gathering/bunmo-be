# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Development Commands

### Build and Run
```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Clean and rebuild
./gradlew clean build
```

### Testing
```bash
# Run all tests
./gradlew test

# Run a specific test class
./gradlew test --tests "io.github.bunmo.SpecificTestClass"

# Run a specific test method
./gradlew test --tests "io.github.bunmo.SpecificTestClass.testMethod"

# Run tests with verbose output
./gradlew test --info
```

### Database
```bash
# Start MySQL database via Docker Compose
docker-compose up -d

# Stop database
docker-compose down

# View database logs
docker-compose logs -f mysql
```

**Database Connection Details:**
- Host: localhost:23306
- Database: bunmo
- Username: bunmo_user
- Password: bunmo_password

### API Documentation
- Swagger UI: http://localhost:18080/swagger-ui
- API Docs: http://localhost:18080/api-docs
- Application runs on port: 18080

## Project Architecture

### Technology Stack
- **Framework**: Spring Boot 3.5.9
- **Java Version**: 17
- **Build Tool**: Gradle
- **Database**: MySQL 8.0
- **Security**: Spring Security + JWT (jjwt 0.12.3)
- **API Documentation**: SpringDoc OpenAPI 2.8.15
- **ORM**: Spring Data JPA with Hibernate
- **HTTP Client**: Spring WebFlux WebClient (for OAuth2)

### Package Structure

The project follows a **modular domain-driven architecture**:

```
io.github.bunmo/
├── common/                 # Cross-cutting concerns
│   ├── exception/          # BusinessException, ErrorCode interface
│   └── web/                # ApiResponse, ResultCode, GlobalControllerAdvice, HealthCheckController
├── auth/                   # OAuth2 authentication domain
│   ├── config/             # KakaoOAuthProperties
│   ├── controller/         # AuthController, AuthControllerDoc
│   ├── dto/                # AuthResultCode, request/response DTOs
│   ├── exception/          # OAuthErrorCode
│   ├── infrastructure/
│   │   ├── domain/         # SocialAccount entity, Provider enum
│   │   └── repository/     # SocialAccountRepository
│   └── service/            # AuthService, KakaoOAuthService
├── member/                 # Member domain (user management)
│   ├── exception/          # MemberErrorCode
│   └── infrastructure/
│       ├── domain/         # JPA entities (Member), enums (RoleType, ActiveType, Gender),
│       │                   # value objects (MemberProfile, MemberLocation)
│       └── repository/     # Data access layer
├── gathering/              # Gathering domain (event management)
│   └── [entities]          # Gathering, GatheringParticipant, GatheringLocation, ProductCategory
├── security/               # JWT & Spring Security configuration
│   ├── config/             # SecurityConfig
│   ├── exception/          # Auth-specific exceptions (AuthErrorCode, AuthException)
│   ├── jwt/                # JWT utilities (JwtUtil, JwtFilter, handlers)
│   └── service/            # CustomUserDetailsService, CustomUserDetails
└── report/                 # User reporting/moderation
    └── domain/             # ReportedUserHistory
```

**Key Architectural Patterns:**

1. **Layered Architecture**: Domain modules organized with infrastructure, repository, service, and controller layers
2. **Value Objects**: Extensive use of `@Embeddable` records (MemberProfile, MemberLocation, GatheringDetail, GatheringLocation)
3. **JPA Auditing**: Entities use `@CreatedDate` and `@LastModifiedDate` for automatic timestamp management
4. **Lazy Loading**: Foreign key relationships use `FetchType.LAZY` to prevent N+1 queries
5. **Factory Methods**: Domain objects use static `from()` methods for construction

### Authentication & Security

**JWT-based Stateless Authentication:**
- Access Token expiry: 24 hours (86400000ms)
- Refresh Token expiry: 3 days (259200000ms)
- Token flow: JwtFilter → JwtUtil (validation) → CustomUserDetailsService (user loading) → SecurityContext

**Protected Endpoints:**
- All `/api/v1/**` endpoints require authentication
- Public endpoints: `/api/v1/oauth2/kakao`, API docs/Swagger endpoints

**OAuth2 Integration (Kakao):**
- Endpoint: `POST /api/v1/oauth2/kakao` - Authorization code → JWT token exchange
- Flow: Authorization Code → KakaoOAuthService → KakaoTokenResponse → KakaoUserInfoResponse → Member/SocialAccount → JWT
- New member registration: Creates `PENDING` status member with linked SocialAccount
- Existing member login: Validates member status, returns JWT tokens
- Error codes: `OAUTH_001` ~ `OAUTH_004` for Kakao-specific errors

**Important Security Notes:**
- Session management is `STATELESS` (no server-side sessions)
- CORS configured to allow localhost:6004
- Custom exception handlers: `JwtAuthenticationEntryPoint`, `JwtAccessDeniedHandler`

### API Response Structure

All API endpoints return a standardized response format:

```json
{
  "code": "SUCCESS_001 or ERROR_CODE",
  "message": "Human-readable message",
  "data": {...},
  "errors": [{"field": "fieldName", "message": "validation error"}]
}
```

**Error Handling:**
- Global exception handling via `GlobalControllerAdvice`
- All business exceptions extend `BusinessException`
- Domain-specific error codes implement `ErrorCode` interface
- Auth errors use `AuthErrorCode` enum (UNAUTHORIZED_001 through UNAUTHORIZED_005)

**Response Conventions:**
- Success responses use `ResultCode` interface (implement per domain)
- Error responses include HTTP status codes via `ErrorCode`
- Null fields excluded via `@JsonInclude(NON_NULL)`
- Field validation errors included in `errors` array

### Domain Models

**Member Entity:**
- Fields: role (ADMIN/MEMBER), activeType (ACTIVE/INACTIVE/BANNED/WITHDRAWAL/PENDING)
- Embedded: MemberProfile (nickname, gender, profileImageUrl), MemberLocation (x, y, address)
- High-precision coordinates: BigDecimal (17,14) for latitude, (16,14) for longitude
- Factory method: `Member.createPendingMember()` for OAuth registration
- Auto-audited: createdAt, updatedAt

**SocialAccount Entity:**
- Links members to OAuth providers (one member can have multiple social accounts)
- Fields: memberId, provider (KAKAO), providerId (unique identifier from OAuth provider)
- Factory method: `SocialAccount.create(memberId, provider, providerId)`

**Gathering Entity:**
- Fields: owner_id, maxParticipantCount, gatheringTime
- Embedded: GatheringDetail (name, introduction, openChatLink), GatheringLocation
- Relations: ManyToOne → ProductCategory, OneToMany → GatheringParticipant (CASCADE.ALL, orphanRemoval)

**GatheringParticipant:**
- Join table entity linking members to gatherings
- Fields: gathering (ManyToOne, LAZY), memberId, joinedAt

**Geolocation:**
- All location fields use high-precision BigDecimal for accurate spatial queries
- Both Member and Gathering have embedded location value objects

### Development Conventions

**When Adding New Features:**

1. **Domain Separation**: Create new packages for new domains under `io.github.bunmo`
2. **Error Codes**: Implement domain-specific `ErrorCode` enum with code format: `DOMAIN_XXX`
3. **Success Codes**: Implement `ResultCode` enum for success scenarios
4. **Value Objects**: Use `@Embeddable` records for grouped related fields
5. **Exceptions**: Throw `BusinessException` with appropriate `ErrorCode`
6. **Dependency Injection**: Use `@RequiredArgsConstructor` with final fields
7. **Transactions**: Apply `@Transactional` on service layer methods
8. **Factory Methods**: Use static `from()` methods for domain object creation

**Code Style:**
- Use Lombok annotations (`@Getter`, `@RequiredArgsConstructor`, `@Slf4j`)
- Prefer accessor methods (e.g., `email()`) over getters for domain entities
- Use enums for all fixed value sets (statuses, types, categories)
- Apply JPA auditing for timestamp fields

**Branch Workflow:**
- Main branch: `develop`
- Feature branches: `feat/<domain>/<feature-name>` (e.g., `feat/auth/oauth-kakao`)

## Configuration Notes

### Spring Profiles
- **dev** (default): Local development with hardcoded values in `application-dev.yml`
- **prod**: Production with environment variables from `.env` file
- Set via: `SPRING_PROFILES_ACTIVE=dev|prod`

### Application Properties
- Base configuration: `src/main/resources/application.yml` (common settings)
- Profile-specific: `application-dev.yml`, `application-prod.yml`
- JPA DDL mode: `update` (auto-update schema)
- SQL logging enabled (Hibernate `show_sql`, `format_sql`)

### Environment Variables (Production)
Copy `.env.example` to `.env` and configure:
```bash
# Database
DATABASE_URL=jdbc:mysql://localhost:3306/bunmo?...
DATABASE_USERNAME=your_db_username
DATABASE_PASSWORD=your_db_password

# JWT
JWT_SECRET=your_base64_encoded_secret_key
JWT_ACCESS_TOKEN_EXPIRE_TIME=86400000
JWT_REFRESH_TOKEN_EXPIRE_TIME=259200000

# Kakao OAuth
KAKAO_CLIENT_ID=kakao_client_id
KAKAO_CLIENT_SECRET=kakao_client_secret
KAKAO_REDIRECT_URI=http://localhost:18080/api/v1/oauth2/kakao
```

### Kakao OAuth Configuration
- Token URI: `https://kauth.kakao.com/oauth/token`
- User Info URI: `https://kapi.kakao.com/v2/user/me`
- Properties class: `KakaoOAuthProperties` (`oauth2.kakao.*` prefix)
