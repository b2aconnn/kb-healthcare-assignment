# KB Healthcare Assignment

## 기술 스택

### Backend
- **Java**: 17
- **Spring Boot**: 3.5.10
- **Spring Data JPA**
- **Spring Security** (암호화만)

### Database
- **MySQL**: 8.0

## 실행 방법

### 데이터베이스 설정

```bash
# Docker Compose로 MySQL 실행
docker-compose up -d

# 데이터베이스 정보
# Host: localhost:3306
# Database: kbhealthcare
# Username: kbhealthcare
# Password: kbhealthcare
```

### 애플리케이션 실행

```bash
# 빌드
./gradlew build

# 실행
./gradlew bootRun
```

## API 엔드포인트 명세

### 헬스 데이터 API

#### 삼성 헬스 데이터 저장
```http
POST /api/activities/samsung
Content-Type: application/json

{
  "recordkey": "user123",
  "data": {
    "entries": [
      {
        "period": {
          "from": "2024-11-15 00:00:00",
          "to": "2024-11-15 00:10:00"
        },
        "distance": {"unit": "km", "value": 0.04223},
        "calories": {"unit": "kcal", "value": 2.03},
        "steps": 54
      }
    ],
    "source": {
      "mode": 1,
      "product": {"name": "Galaxy Watch", "vender": "Samsung"},
      "name": "Samsung Health",
      "type": "health"
    }
  },
  "lastUpdate": "2024-11-15T00:10:00+09:00",
  "type": "health"
}
```

#### 애플 헬스 데이터 저장
```http
POST /api/activities/apple
Content-Type: application/json

{
  "recordkey": "user123",
  "data": {
    "memo": "Apple Watch 데이터",
    "entries": [
      {
        "steps": "54",
        "period": {
          "to": "2024-11-15T00:10:00+09:00",
          "from": "2024-11-15T00:00:00+09:00"
        },
        "distance": {"value": 0.04223, "unit": "km"},
        "calories": {"value": 2.03, "unit": "kcal"}
      }
    ],
    "source": {
      "product": {"name": "Apple Watch", "vender": "Apple"},
      "type": "health",
      "mode": 1,
      "name": "Apple Health"
    }
  },
  "type": "health",
  "lastUpdate": "2024-11-15T00:10:00+09:00"
}
```

#### 일별 요약 조회
```http
localhost:8080/api/activities/daily?recordkey=7836887b-b12a-440f-af0f-851546504b13&fromDate=2024-01-01&toDate=2024-12-31
```

#### 월별 요약 조회
```http
localhost:8080/api/activities/monthly?recordkey=7836887b-b12a-440f-af0f-851546504b13&fromDate=2024-01-01&toDate=2024-12-31
```

### 사용자 API

#### 회원가입
```http
POST /api/users/signup
Content-Type: application/json

{
  "name": "홍길동",
  "nickname": "hong123",
  "email": "hong@example.com",
  "password": "password123"
}
```

#### 로그인
```http
POST /api/users/login
Content-Type: application/json

{
  "email": "hong@example.com",
  "password": "password123"
}
```

## 데이터베이스 설계

### ERD

```mermaid
erDiagram
    users {
        bigint id PK
        varchar name
        varchar nickname UK
        varchar email UK
        varchar password
        varchar recordkey
        datetime created_at
        datetime updated_at
    }
    
    health_activity {
        bigint id PK
        varchar recordkey
        int steps
        decimal calories
        decimal distance
        datetime period_from
        datetime period_to
        enum source_type
        datetime created_at
        datetime updated_at
    }
    
    daily_summary {
        bigint id PK
        varchar recordkey
        date date
        int total_steps
        decimal total_calories
        decimal total_distance
        enum source_type
        datetime created_at
        datetime updated_at
    }
    
    monthly_summary {
        bigint id PK
        varchar recordkey
        varchar summary_month
        int total_steps
        decimal total_calories
        decimal total_distance
        enum source_type
        datetime created_at
        datetime updated_at
    }
    
    users ||--o{ health_activity : recordkey
    users ||--o{ daily_summary : recordkey
    users ||--o{ monthly_summary : recordkey
```

### 테이블 설명

#### users 테이블
- 사용자 정보 저장
- email과 nickname에 유니크 제약 조건

#### health_activity 테이블
- 원본 헬스 활동 데이터 저장
- Samsung Health와 Apple Health 데이터 수집

#### daily_summary 테이블
- 일별로 집계된 헬스 데이터
- health_activity를 기반으로 자동 집계

#### monthly_summary 테이블
- 월별로 집계된 헬스 데이터
- daily_summary를 기반으로 자동 집계

### 데이터 흐름

1. 클라이언트가 health_activity 데이터 저장
2. 시스템이 자동으로 daily_summary로 집계
3. 시스템이 자동으로 monthly_summary로 집계

## 데이터 조회 결과 제출
./aggerate 폴더에 csv 파일로 저장


## 프로젝트 구조 설명

### 패키지 구조

```
com.kbhealthcare.assignment/
├── domain/                  # 도메인 영역
│   ├── activity/          
│   │   ├── HealthActivity.java
│   │   ├── DailySummary.java
│   │   ├── MonthlySummary.java
│   │   ├── SourceType.java
│   │   └── repositories/
│   └── user/               
│       ├── User.java  
│       └── repositories/
├── application/            # 애플리케이션 영역
│   ├── activity/          
│   ├── user/              
│   └── aggregation/       # 집계 서비스
├── infrastructure/         # 인프라 영역
│   ├── activity/          
│   └── user/              
├── interfaces/            # 인터페이스 영역
│   ├── activity/          
│   └── user/              
└── config/                # 설정 클래스
```

### 클래스 다이어그램

```mermaid
classDiagram
    %% Domain Layer
    class User {
        +id: Long
        +name: String
        +nickname: String
        +email: String
        +password: String
    }
    
    class HealthActivity {
        +id: Long
        +recordkey: String
        +steps: Integer
        +calories: BigDecimal
        +distance: BigDecimal
        +periodFrom: LocalDateTime
        +periodTo: LocalDateTime
        +sourceType: SourceType
    }
    
    class DailySummary {
        +id: Long
        +recordkey: String
        +date: LocalDate
        +totalSteps: Integer
        +totalCalories: BigDecimal
        +totalDistance: BigDecimal
        +sourceType: SourceType
    }
    
    class MonthlySummary {
        +id: Long
        +recordkey: String
        +yearMonth: String
        +totalSteps: Integer
        +totalCalories: BigDecimal
        +totalDistance: BigDecimal
        +sourceType: SourceType
    }
    
    class SourceType {
        <<enumeration>>
        SAMSUNG
        APPLE
    }
    
    %% Repository Interfaces
    class UserRepository {
        <<interface>>
        +save(User)
        +findByEmail(String)
        +existsByEmail(String)
        +existsByNickname(String)
    }
    
    class HealthActivityRepository {
        <<interface>>
        +bulkInsertIgnore(List)
        +findByRecordkeyAndDateRange(String, LocalDate, LocalDate)
    }
    
    class DailySummaryRepository {
        <<interface>>
        +upsertDailySummaries(List)
        +findByCondition(DailyCondition)
    }
    
    class MonthlySummaryRepository {
        <<interface>>
        +upsertMonthlySummaries(List)
        +findByCondition(MonthlyCondition)
    }
    
    %% Application Layer
    class UserService {
        -userRepository: UserRepository
        +signup(SignUpRequest)
        +login(LoginRequest)
    }
    
    class HealthActivityService {
        -healthActivityRepository: HealthActivityRepository
        -dailySummaryRepository: DailySummaryRepository
        -monthlySummaryRepository: MonthlySummaryRepository
        -aggregationService: AggregationService
        +saveSamsungHealthData(SamsungHealthCommand)
        +saveAppleHealthData(AppleHealthCommand)
        +aggregateDaily(List)
        +aggregateMonthly()
    }
    
    class AggregationService {
        +aggregateDaily(List)
        +aggregateMonthly(List)
    }
    
    %% Infrastructure Layer
    class UserRepositoryImpl {
        -jpaRepository: UserJpaRepository
    }
    
    class HealthActivityRepositoryImpl {
        -jpaRepository: HealthActivityJpaRepository
        -jdbcTemplate: JdbcTemplate
    }
    
    class DailySummaryRepositoryImpl {
        -jpaRepository: DailySummaryJpaRepository
    }
    
    class MonthlySummaryRepositoryImpl {
        -jpaRepository: MonthlySummaryJpaRepository
    }
    
    %% Relationships
    HealthActivity --> SourceType
    DailySummary --> SourceType
    MonthlySummary --> SourceType
    
    UserService --> UserRepository
    HealthActivityService --> HealthActivityRepository
    HealthActivityService --> DailySummaryRepository
    HealthActivityService --> MonthlySummaryRepository
    HealthActivityService --> AggregationService
    
    UserRepositoryImpl ..|> UserRepository
    HealthActivityRepositoryImpl ..|> HealthActivityRepository
    DailySummaryRepositoryImpl ..|> DailySummaryRepository
    MonthlySummaryRepositoryImpl ..|> MonthlySummaryRepository
```