# Learnify LMS - Learning Management System

A comprehensive Learning Management System (LMS) platform built with Spring Boot 3.2.1, providing complete features for managing online courses, assignments, quizzes, and user administration.

## 📋 Version Information

| Component           | Version |
| ------------------- | ------- |
| **Spring Boot**     | 3.2.1   |
| **Spring Security** | 6.2.1   |
| **Spring Data JPA** | 3.2.1   |
| **Java**            | 21      |
| **Gradle**          | 8.7+    |
| **PostgreSQL JDBC** | 42.7.1  |
| **Lombok**          | 1.18.38 |
| **MapStruct**       | 1.6.3   |
| **jjwt (JWT)**      | 0.12.3  |
| **Redis (Lettuce)** | 6.2.5   |
| **Cloudinary SDK**  | 1.36.0  |
| **Spring Mail**     | 3.2.1   |

## 🛠️ Technology Stack

### Backend Framework

-    **Spring Boot 3.2.1** - Core framework for building the application
-    **Spring Security 6.2.1** - JWT authentication & authorization
-    **Spring Data JPA** - Object-Relational Mapping & database access
-    **Spring Web** - RESTful API & MVC support

### Database & Caching

-    **PostgreSQL** - Primary relational database
-    **Hibernate** - JPA ORM implementation
-    **Redis (Lettuce)** - Token blacklist & distributed caching

### API & Serialization

-    **Spring REST** - RESTful web services
-    **Jackson** - JSON serialization/deserialization
-    **jackson-datatype-jsr310** - Java 8 date/time type support

### Authentication & Security

-    **jjwt 0.12.3** - JWT token generation & validation
-    **BCrypt** - Password hashing & verification

### Code Generation & Mapping

-    **Lombok** - Reduce boilerplate code
-    **MapStruct 1.5.5.Final** - Entity-DTO mapping at compile time
-    **Jakarta Validation** - Data validation & constraint handling

### File & Media Management

-    **Cloudinary SDK** - Cloud-based image/file storage service

### Email Service

-    **Spring Mail** - Email sending capabilities with SMTP

### Build Tool

-    **Gradle 8.7+** - Project build, dependency management & packaging

## 📋 System Requirements

| Component      | Minimum Version |
| -------------- | --------------- |
| **Java JDK**   | 21              |
| **Gradle**     | 8.7             |
| **PostgreSQL** | 14.0            |
| **Redis**      | 7.0 (optional)  |

## 🚀 Installation & Setup

### Step 1: Clone Repository

```bash
git clone https://github.com/yourusername/learnify-lms.git
cd learnify_lsm
```

### Step 2: Create Database

```sql
CREATE DATABASE learnify_db;
```

### Step 3: Configure Environment

Create file `src/main/resources/application.yml`:

```yaml
spring:
     datasource:
          url: jdbc:postgresql://localhost:5432/learnify_db
          username: postgres
          password: your_password
          driver-class-name: org.postgresql.Driver

     jpa:
          hibernate:
               ddl-auto: update
          database-platform: org.hibernate.dialect.PostgreSQL10Dialect

     redis:
          host: localhost
          port: 6379

     mail:
          host: smtp.gmail.com
          port: 587
          username: your-email@gmail.com
          password: your-app-password
          properties:
               mail.smtp.auth: true
               mail.smtp.starttls.enable: true

server:
     port: 8081
     servlet:
          context-path: /api
```

### Step 4: Build Project

```bash
./gradlew clean build
```

### Step 5: Run Application

```bash
./gradlew bootRun
```

Application will start at: **http://localhost:8081**

## 📚 Authentication Architecture

### JWT Token Structure

-    **Subject (sub)**: User ID (UUID)
-    **Custom Claim (role)**: User role (ADMIN, INSTRUCTOR, STUDENT)
-    **Storage**: HttpOnly Cookies (secure transmission)
-    **Token Names**:
     -    `accessToken` - Access token (short-lived: 1 hour)
     -    `refreshToken` - Refresh token (long-lived: 7 days)

### Authentication Flow

1. User submits login credentials → `POST /api/v1/auth/login`
2. Server validates credentials and generates JWT tokens
3. Tokens are set in HttpOnly secure cookies
4. `JwtAuthenticationFilter` intercepts each request and validates token
5. Token is parsed to extract userId and role
6. `CustomUserDetails` is created and set in SecurityContext
7. Controller receives `@AuthenticationPrincipal CustomUserDetails` for user info

### Exception Handling

-    All unauthorized requests return standardized `BaseResponse` with 401 status
-    `CustomAuthenticationEntryPoint` handles all authentication failures uniformly
-    `GlobalExceptionHandler` processes application-level exceptions

## 🔒 User Roles & Permissions

| Role           | Permissions                                         |
| -------------- | --------------------------------------------------- |
| **ADMIN**      | Manage all system resources, users, and courses     |
| **INSTRUCTOR** | Create & manage own courses, view enrollments       |
| **STUDENT**    | Enroll in courses, submit assignments, take quizzes |

## 📂 Directory Structure

```
src/main/java/com/learnify/lms/
├── config/                           # Configuration classes
│   ├── CloudinaryConfig.java         # Cloudinary storage configuration
│   ├── CorsConfig.java               # CORS policy configuration
│   ├── RedisConfig.java              # Redis cache configuration
│   └── SecurityConfig.java           # Spring Security configuration
│
├── controller/                       # REST API Controllers
│   ├── IdentityController.java       # Authentication endpoints
│   └── UserController.java           # User profile endpoints
│
├── dto/                              # Data Transfer Objects
│   ├── request/                      # Request DTOs
│   │   └── auth/                     # Authentication request DTOs
│   └── response/                     # Response DTOs
│       ├── BaseResponse.java         # Standard response wrapper
│       └── auth/                     # Authentication response DTOs
│
├── exception/                        # Exception handling
│   ├── ApiException.java             # Custom API exception
│   ├── ErrorCode.java                # Standardized error codes (200+ codes)
│   └── GlobalExceptionHandler.java   # Global exception handler
│
├── mapper/                           # MapStruct Entity-DTO Mappers
│   ├── IdentityMapper.java           # Authentication entity mapping
│   └── UserMapper.java               # User entity mapping
│
├── model/                            # JPA Entity Classes
│   ├── Users.java                    # User entity
│   ├── Courses.java                  # Course entity
│   ├── Enrollments.java              # Enrollment entity
│   ├── Assignments.java              # Assignment entity
│   ├── Submissions.java              # Submission entity
│   ├── Quizzes.java                  # Quiz entity
│   ├── QuizAttempts.java             # Quiz attempt entity
│   ├── Questions.java                # Question entity
│   ├── Lessons.java                  # Lesson entity
│   ├── Categories.java               # Course category entity
│   ├── Payments.java                 # Payment transaction entity
│   ├── Reviews.java                  # Course review entity
│   ├── BaseEntity.java               # Base entity with audit fields
│   └── common/                       # Enums & common classes
│       └── Role.java                 # User role enumeration
│
├── repository/                       # Spring Data Repositories
│   ├── IdentityRepository.java       # User data repository
│   └── (Other entity repositories)
│
├── security/                         # Security & JWT
│   ├── jwt/
│   │   ├── JwtAuthenticationFilter.java        # JWT validation filter
│   │   ├── JwtTokenProvider.java               # JWT generation & validation
│   │   ├── TokenBlacklistService.java          # Token blacklist management
│   │   └── CustomAuthenticationEntryPoint.java # Unified auth error handling
│   └── userdetails/
│       ├── CustomUserDetails.java              # Lightweight principal object
│       └── CustomUserDetailsResolver.java      # @AuthenticationPrincipal resolver
│
├── service/                          # Business Logic
│   ├── interfaces/
│   │   ├── auth/
│   │   │   └── IdentityService.java  # Authentication service interface
│   │   └── users/
│   │       └── UserService.java      # User management service interface
│   └── impl/
│       ├── auth/
│       │   └── IdentityServiceImpl.java
│       └── users/
│           └── UserServiceImpl.java
│
├── utils/                            # Utility Classes
│   ├── ValidationUtil.java           # Data validation utilities
│   ├── FormatName.java               # String formatting utilities
│   ├── auth/                         # Authentication-related utilities
│   │   ├── AuthConstants.java        # Auth constants & messages
│   │   ├── CookieUtil.java           # Cookie management utilities
│   │   └── RequestUtil.java          # HTTP request utilities
│   └── users/                        # User-related utilities
│
└── LearnifyLmsApplication.java       # Application entry point
```

## 📖 API Endpoints

### Authentication (Public)

```
POST   /api/v1/auth/register          - User registration with email verification
POST   /api/v1/auth/login             - User login (sets JWT cookies)
POST   /api/v1/auth/logout            - User logout (clears cookies)
POST   /api/v1/auth/forgot-password   - Request password reset (sends OTP)
POST   /api/v1/auth/reset-password    - Reset password with OTP verification
POST   /api/v1/auth/refresh-token     - Refresh JWT token
```

### User Profile (Authenticated)

```
GET    /api/users/me                  - Get current user profile
```

### Courses (Public)

```
GET    /api/courses                   - List all courses
GET    /api/courses/{id}              - Get course details
```

## 🔄 Response Format

All API responses follow standardized `BaseResponse` format:

**Success Response:**

```json
{
	"success": true,
	"status": 200,
	"message": "Operation successful",
	"data": {
		/* actual data */
	},
	"timestamp": "2025-12-30T22:00:00"
}
```

**Error Response:**

```json
{
	"success": false,
	"status": 401,
	"message": null,
	"error": {
		"code": "UNAUTHORIZED",
		"message": "Authentication required"
	},
	"timestamp": "2025-12-30T22:00:00"
}
```

## 🔐 Security Features

-    ✅ JWT-based authentication with HttpOnly cookies
-    ✅ Role-based access control (RBAC)
-    ✅ Password hashing with BCrypt
-    ✅ Token blacklist system with Redis
-    ✅ CORS protection
-    ✅ CSRF protection disabled for API (REST stateless)
-    ✅ Secure cookie attributes (HttpOnly, Secure, SameSite=Strict)
-    ✅ Input validation with Jakarta Validation
-    ✅ SQL injection prevention via JPA parameterized queries

## 📊 Database Schema

Key entities with relationships:

-    **Users** ← many-to-many → **Courses** (via Enrollments)
-    **Courses** ← one-to-many → **Lessons**
-    **Courses** ← one-to-many → **Assignments**
-    **Courses** ← one-to-many → **Quizzes**
-    **Quizzes** ← one-to-many → **Questions**
-    **Users** ← one-to-many → **Submissions**, **QuizAttempts**, **Reviews**

## 🧪 Development Notes

-    Use `@AuthenticationPrincipal CustomUserDetails userDetails` in controllers to inject current user
-    All services use dependency injection via Lombok's `@RequiredArgsConstructor`
-    MapStruct generates implementations at compile time
-    Database uses PostgreSQL with Hibernate auto-schema generation (ddl-auto: update)
-    Token expiration: accessToken (1 hour), refreshToken (7 days)

## 📱 Mô tả Chi tiết Ứng dụng

### Giới thiệu

**Learnify LMS** là một nền tảng quản lý học tập toàn diện được thiết kế để hỗ trợ các tổ chức giáo dục trong việc:

-    Tạo và quản lý khóa học trực tuyến
-    Quản lý học viên và giáo viên
-    Theo dõi tiến độ học tập
-    Quản lý bài tập và kiểm tra
-    Xử lý thanh toán khóa học
-    Cấp độ quyền hạn dựa trên vai trò

### Các Tính Năng Chính

#### 1. Quản Lý Người Dùng

-    **Đăng ký tài khoản** với xác minh email
-    **Xác thực hai chiều** (OTP qua email)
-    **Quản lý hồ sơ** cá nhân
-    **Phân quyền** theo vai trò (Admin, Instructor, Student)
-    **Khôi phục mật khẩu** qua OTP
-    **Quản lý phiên** với JWT tokens

```
Quy trình Đăng ký:
1. User gửi POST /api/v1/auth/register
2. System validate dữ liệu (email, password, phone)
3. Check duplicate email/phone
4. Hash password với BCrypt
5. Lưu user vào database
6. Gửi email welcome
7. Trả về RegistrationResponse
```

#### 2. Quản Lý Khóa Học

-    **Tạo khóa học** - Giáo viên tạo khóa học mới
-    **Lên lịch bài giảng** - Chia bài học thành các unit
-    **Phân bài tập** - Gán assignment cho từng bài học
-    **Tạo kiểm tra** - Quiz với câu hỏi multiple choice
-    **Đánh giá** - Tính điểm và cho phép review
-    **Phân loại** - Phân loại khóa học theo danh mục

```
Cấu trúc một Khóa Học:
Course
├── Lesson 1
│   ├── Assignment 1
│   └── Assignment 2
├── Lesson 2
│   ├── Quiz 1 (Question 1-5)
│   └── Assignment 3
└── Lesson 3
    └── Quiz 2 (Question 1-10)
```

#### 3. Đăng Ký Khóa Học

-    **Enroll tự do** - Học viên đăng ký khóa học công khai
-    **Enroll trả phí** - Xử lý thanh toán qua hệ thống
-    **Kiểm tra điều kiện** - Validate nhu cầu tiên quyết
-    **Quản lý sức chứa** - Giới hạn số lượng học viên
-    **Hủy đăng ký** - Hỗ trợ hoàn tiền theo chính sách

```
Quy trình Thanh toán:
1. Student chọn khóa học trả phí
2. System kiểm tra sức chứa
3. Student chọn phương thức thanh toán
4. Payment gateway xử lý giao dịch
5. System ghi nhận Payment record
6. Tạo Enrollment record
7. Student có quyền truy cập khóa học
```

#### 4. Giao Bài Tập

-    **Tạo assignment** với deadline
-    **Upload tài liệu** hướng dẫn
-    **Gửi bài làm** (file hoặc text)
-    **Chấm điểm** theo rubric
-    **Feedback** từ giáo viên
-    **Theo dõi tiến độ** nộp bài

```
Vòng đời Assignment:
DRAFT → PUBLISHED → IN_PROGRESS → SUBMITTED → GRADED → COMPLETED
       |                              |
       └──────REVISION─────────────┘
```

#### 5. Quản Lý Kiểm Tra

-    **Tạo quiz** với câu hỏi đa dạng
-    **Thiết lập thời gian** làm bài
-    **Tính điểm tự động** cho trắc nghiệm
-    **Đặt giới hạn cố gắng** cho mỗi quiz
-    **Xem kết quả** chi tiết
-    **Phân tích** hiệu suất

```
Cấu trúc Quiz:
Quiz
├── Settings
│   ├── Duration (minutes)
│   ├── Pass score (%)
│   ├── Max attempts
│   └── Shuffle questions (true/false)
└── Questions (5-100 câu)
    ├── Question 1 (Multiple choice)
    ├── Question 2 (True/False)
    └── ...
```

#### 6. Theo Dõi Tiến độ Học Tập

-    **Dashboard học viên** - Xem tiến độ từng khóa
-    **Tính GPA** - Trung bình điểm tổng
-    **Cảnh báo deadline** - Thông báo bài tập sắp hết hạn
-    **Báo cáo** - Xuất báo cáo tiến độ
-    **So sánh** - Xem xếp hạng trong lớp (nếu bật)

#### 7. Hệ Thống Đánh Giá

-    **Review & Rating** - Học viên đánh giá khóa học
-    **Feedback viết** - Nhận xét chi tiết
-    **Star rating** - Từ 1-5 sao
-    **Moderasi** - Admin phê duyệt review
-    **Thống kê** - Tính rating trung bình

### Các Roles và Quyền Hạn

#### 👨‍💼 ADMIN (Quản trị viên)

```
✓ Quản lý toàn bộ người dùng (Create, Read, Update, Delete)
✓ Phê duyệt giáo viên mới
✓ Quản lý danh mục khóa học
✓ Xem báo cáo tổng hợp hệ thống
✓ Quản lý thanh toán
✓ Cấu hình hệ thống
✓ Quản lý email templates
✓ Xem analytics toàn bộ nền tảng
```

#### 👨‍🏫 INSTRUCTOR (Giáo viên)

```
✓ Tạo và quản lý khóa học của mình
✓ Tạo bài giảng (lessons)
✓ Tạo assignment và quiz
✓ Chấm điểm bài tập
✓ Xem danh sách học viên enrolled
✓ Gửi thông báo cho học viên
✓ Xem báo cáo hiệu suất lớp
✓ Quản lý nội dung (upload, edit, delete)
✗ Không thể xem khóa học của giáo viên khác
```

#### 👨‍🎓 STUDENT (Học viên)

```
✓ Duyệt và đăng ký khóa học
✓ Xem tài liệu bài giảng
✓ Nộp bài tập
✓ Làm quiz
✓ Xem điểm số của mình
✓ Xem feedback từ giáo viên
✓ Đánh giá khóa học
✓ Tải tài liệu khóa học
✗ Không thể tạo khóa học
✗ Không thể chấm điểm
```

### Use Cases Chính

#### Use Case 1: Học viên Đăng ký Khóa Học

```
1. Học viên truy cập danh sách khóa học
2. Tìm kiếm khóa học quan tâm
3. Xem chi tiết: giáo viên, mô tả, rating
4. Nếu khóa học trả phí:
   - Thêm vào giỏ hàng
   - Checkout và thanh toán
5. Nếu khóa học miễn phí:
   - Click "Enroll"
6. Hệ thống:
   - Tạo Enrollment record
   - Gửi email xác nhận
   - Đưa khóa học vào "My Courses"
```

#### Use Case 2: Giáo viên Tạo Assignment

```
1. Giáo viên login vào dashboard
2. Chọn một khóa học
3. Chọn lesson từ khóa học
4. Click "Create Assignment"
5. Điền thông tin:
   - Tiêu đề bài tập
   - Mô tả chi tiết
   - File hướng dẫn
   - Deadline
   - Loại nộp (file/text)
6. Click "Publish"
7. Hệ thống:
   - Lưu assignment
   - Gửi thông báo cho học viên
8. Học viên nhận được thông báo nộp bài
```

#### Use Case 3: Học viên Nộp Bài Tập

```
1. Học viên xem Assignment trong khóa học
2. Download file hướng dẫn
3. Làm bài tập trên máy tính cá nhân
4. Upload file hoặc paste text
5. Xem preview bài nộp
6. Click "Submit"
7. Hệ thống:
   - Lưu submission
   - Ghi nhận timestamp
   - Gửi xác nhận cho học viên
8. Giáo viên nhận được thông báo
```

#### Use Case 4: Giáo viên Chấm Điểm

```
1. Giáo viên vào Assignment
2. Xem danh sách submissions
3. Click vào submission của học viên
4. Xem bài làm đã nộp
5. Viết feedback
6. Nhập điểm số
7. Click "Grade"
8. Hệ thống:
   - Lưu grade
   - Gửi email thông báo học viên
   - Cập nhật progress bar
```

#### Use Case 5: Học viên Làm Quiz

```
1. Học viên truy cập Quiz
2. Xem thông tin: số câu, thời gian, pass score
3. Click "Start Quiz"
4. Hệ thống:
   - Shuffle câu hỏi (nếu bật)
   - Bắt đầu countdown timer
5. Học viên:
   - Đọc câu hỏi
   - Chọn đáp án
   - Xem preview trước khi submit
6. Click "Submit"
7. Hệ thống:
   - Chấm điểm tự động
   - Ghi nhận điểm
   - Hiển thị kết quả ngay
   - Gửi email với kết quả
```

### Quy Trình Xác Thực (Authentication)

```
┌─────────────────────────────────────────┐
│   Người dùng gửi credentials            │
│   POST /api/v1/auth/login               │
│   Body: {email, password}               │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│   Server validate credentials           │
│   - Check email exists                  │
│   - Check password match (BCrypt)       │
└──────────────┬──────────────────────────┘
               │
               ├─ INVALID → 401 Unauthorized
               │
               ▼
┌─────────────────────────────────────────┐
│   Generate JWT Tokens                   │
│   - accessToken (short-lived: 1 hour)   │
│   - refreshToken (long-lived: 7 days)   │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│   Set tokens in HttpOnly Cookies        │
│   - Cookie: accessToken                 │
│   - Cookie: refreshToken                │
│   - HttpOnly: true                      │
│   - Secure: true                        │
│   - SameSite: Strict                    │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│   Return LoginResponse                  │
│   {                                     │
│     userId: "uuid",                     │
│     email: "user@example.com",          │
│     fullName: "John Doe",               │
│     role: "STUDENT",                    │
│     avatar: "url"                       │
│   }                                     │
└─────────────────────────────────────────┘

Request tiếp theo:
┌─────────────────────────────────────────┐
│   GET /api/users/me                     │
│   Cookie: accessToken=...               │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│   JwtAuthenticationFilter:              │
│   - Extract token từ cookie             │
│   - Validate token signature            │
│   - Parse userId & role                 │
│   - Create CustomUserDetails            │
│   - Set trong SecurityContext           │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│   Controller:                           │
│   @AuthenticationPrincipal               │
│   CustomUserDetails userDetails         │
│   - userId: đã extract từ token         │
│   - role: đã extract từ token           │
└─────────────────────────────────────────┘
```

### Error Handling

Ứng dụng sử dụng **200+ error codes** được định nghĩa trong `ErrorCode.java`:

```
4xx - Client Errors:
  1000-1099: Bad Request (validation, invalid input)
  1100-1199: Unauthorized (authentication, expired token)
  1200-1299: Forbidden (insufficient permissions)
  1300-1399: Not Found (resource not found)
  1400-1499: Conflict (duplicate, constraint violation)

5xx - Server Errors:
  2000+: Internal Server Error
```

Mỗi error response trả về:

```json
{
	"success": false,
	"status": 400,
	"message": null,
	"error": {
		"code": "VALIDATION_ERROR",
		"message": "Email must be in a valid format"
	},
	"timestamp": "2025-12-30T22:15:00"
}
```

### Performance & Optimization

-    **Caching**: Redis cache cho course listings, student profiles
-    **Token Blacklist**: Redis lưu blacklist tokens khi logout
-    **Lazy Loading**: Relationships được load theo yêu cầu
-    **Pagination**: API endpoints hỗ trợ phân trang
-    **Query Optimization**: Named queries, fetch join
-    **Connection Pool**: HikariCP cho database connections

### Bảo Mật

| Tính năng            | Chi tiết                             |
| -------------------- | ------------------------------------ |
| **Password**         | BCrypt hashing (strength 12)         |
| **JWT**              | HS256 signature, expiration checks   |
| **Cookies**          | HttpOnly, Secure, SameSite=Strict    |
| **CORS**             | Configured for frontend origins      |
| **Token Rotation**   | Refresh token endpoint               |
| **Rate Limiting**    | Có thể implement qua Spring Security |
| **SQL Injection**    | JPA parameterized queries            |
| **CSRF**             | Disabled cho API (REST stateless)    |
| **Input Validation** | Jakarta Validation annotations       |

### Mở Rộng Tương Lai

-    [ ] Liveclass video integration (Zoom API)
-    [ ] Mobile app (React Native)
-    [ ] AI-powered grading
-    [ ] Gamification (badges, leaderboards)
-    [ ] Analytics dashboard
-    [ ] Payment gateway integration (Stripe, PayPal)
-    [ ] Certificate generation
-    [ ] Email automation workflows

## 📝 License

MIT
