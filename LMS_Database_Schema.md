# LMS Database Schema Documentation

## Overview

Learnify LMS uses PostgreSQL database with Flyway migration for schema management. The database is designed to support a complete Learning Management System with features including: user management, courses, lessons, quizzes, assignments, enrollments, payments, and notifications.

## Database Information

-    **Database Name**: `lms_learnify`
-    **Database Engine**: PostgreSQL 18.1+
-    **Migration Tool**: Flyway 9.22.3
-    **ORM**: Hibernate 6.4.1 with JPA
-    **Schema Management**: `ddl-auto: none` (Flyway quản lý hoàn toàn)

## Common Fields (BaseEntity)

All tables inherit the following fields from `BaseEntity`:

| Field        | Type      | Description                                |
| ------------ | --------- | ------------------------------------------ |
| `id`         | UUID      | Primary key, auto-generated                |
| `created_at` | TIMESTAMP | Creation timestamp (auto-generated)        |
| `updated_at` | TIMESTAMP | Last update timestamp (auto-generated)     |
| `deleted_at` | TIMESTAMP | Soft delete timestamp, NULL if not deleted |

## Core Tables

### 1. Users Table

Manages user information in the system.

**Table Name**: `users`

| Column     | Type         | Constraints      | Description                          |
| ---------- | ------------ | ---------------- | ------------------------------------ |
| id         | UUID         | PK               | User ID                              |
| email      | VARCHAR(100) | NOT NULL, UNIQUE | Login email address                  |
| username   | VARCHAR(50)  | NOT NULL, UNIQUE | Display username                     |
| password   | VARCHAR(255) | NOT NULL         | BCrypt hashed password               |
| full_name  | VARCHAR(100) | NOT NULL         | Full name                            |
| phone      | VARCHAR(20)  | UNIQUE           | Phone number                         |
| avatar_url | TEXT         |                  | Avatar image URL                     |
| bio        | TEXT         |                  | User biography, personal description |
| created_at | TIMESTAMP    | NOT NULL         | Creation timestamp                   |
| updated_at | TIMESTAMP    | NOT NULL         | Last update timestamp                |
| deleted_at | TIMESTAMP    |                  | Soft delete timestamp                |

**Indexes**:

-    `idx_users_email` ON (email)
-    `idx_users_username` ON (username)
-    `idx_users_created_at` ON (created_at)

**Relationships**:

-    Many-to-Many với `role` qua `user_roles`
-    One-to-Many với `courses` (as instructor)
-    One-to-Many với `enrollments` (as student)
-    One-to-Many với `reviews`
-    One-to-Many với `orders`
-    One-to-Many với `notifications`

### 2. Role Table

Manages system roles.

**Table Name**: `role`

| Column    | Type        | Constraints      | Description                           |
| --------- | ----------- | ---------------- | ------------------------------------- |
| id        | UUID        | PK               | Role ID                               |
| role_name | VARCHAR(20) | NOT NULL, UNIQUE | Role name: ADMIN, INSTRUCTOR, STUDENT |

**Default Roles**:

-    `ADMIN`: System administrator
-    `INSTRUCTOR`: Course instructor/creator
-    `STUDENT`: Student/learner

### 3. User_Roles Table (Junction)

Many-to-Many relationship between Users and Roles.

**Table Name**: `user_roles`

| Column  | Type | Constraints   | Description |
| ------- | ---- | ------------- | ----------- |
| user_id | UUID | FK → users.id | User ID     |
| role_id | UUID | FK → role.id  | Role ID     |

**Primary Key**: (user_id, role_id)

### 4. Permission Table

Manages system permissions.

**Table Name**: `permission`

| Column          | Type    | Constraints      | Description                           |
| --------------- | ------- | ---------------- | ------------------------------------- |
| id              | UUID    | PK               | Permission ID                         |
| permission_code | VARCHAR | NOT NULL, UNIQUE | Permission code (e.g., COURSE_CREATE) |

### 5. Role_Permissions Table (Junction)

Many-to-Many relationship between Roles and Permissions.

**Table Name**: `role_permissions`

| Column        | Type | Constraints        | Description   |
| ------------- | ---- | ------------------ | ------------- |
| role_id       | UUID | FK → role.id       | Role ID       |
| permission_id | UUID | FK → permission.id | Permission ID |

**Primary Key**: (role_id, permission_id)

## Course Management Tables

### 6. Categories Table

Course categorization.

**Table Name**: `categories`

| Column      | Type         | Constraints      | Description           |
| ----------- | ------------ | ---------------- | --------------------- |
| id          | UUID         | PK               | Category ID           |
| name        | VARCHAR(100) | NOT NULL, UNIQUE | Category name         |
| description | TEXT         |                  | Category description  |
| created_at  | TIMESTAMP    | NOT NULL         | Creation timestamp    |
| updated_at  | TIMESTAMP    | NOT NULL         | Last update timestamp |
| deleted_at  | TIMESTAMP    |                  | Soft delete timestamp |

**Indexes**:

-    `idx_categories_name` ON (name)

### 7. Courses Table

Manages course information.

**Table Name**: `courses`

| Column            | Type          | Constraints        | Description                      |
| ----------------- | ------------- | ------------------ | -------------------------------- |
| id                | UUID          | PK                 | Course ID                        |
| title             | VARCHAR(200)  | NOT NULL           | Course title                     |
| slug              | VARCHAR(200)  | UNIQUE             | URL-friendly slug                |
| description       | TEXT          |                    | Detailed description             |
| short_description | VARCHAR(500)  |                    | Brief description                |
| thumbnail_url     | TEXT          |                    | Thumbnail image URL              |
| price             | DECIMAL(10,2) | NOT NULL           | Course price                     |
| discount_price    | DECIMAL(10,2) |                    | Discounted price                 |
| level             | VARCHAR(20)   |                    | BEGINNER, INTERMEDIATE, ADVANCED |
| language          | VARCHAR(20)   |                    | Language code (VN, EN)           |
| duration_hours    | INTEGER       |                    | Duration in hours                |
| is_published      | BOOLEAN       | DEFAULT FALSE      | Publication status               |
| published_at      | TIMESTAMP     |                    | Publication timestamp            |
| instructor_id     | UUID          | FK → users.id      | Course instructor                |
| category_id       | UUID          | FK → categories.id | Course category                  |
| created_at        | TIMESTAMP     | NOT NULL           | Creation timestamp               |
| updated_at        | TIMESTAMP     | NOT NULL           | Last update timestamp            |
| deleted_at        | TIMESTAMP     |                    | Soft delete timestamp            |

**Indexes**:

-    `idx_courses_instructor_id` ON (instructor_id)
-    `idx_courses_category_id` ON (category_id)
-    `idx_courses_slug` ON (slug)
-    `idx_courses_published` ON (is_published)

### 8. Course_Section Table

Divides courses into sections.

**Table Name**: `course_section`

| Column      | Type         | Constraints     | Description           |
| ----------- | ------------ | --------------- | --------------------- |
| id          | UUID         | PK              | Section ID            |
| title       | VARCHAR(200) | NOT NULL        | Section title         |
| description | TEXT         |                 | Section description   |
| order_index | INTEGER      | NOT NULL        | Display order         |
| course_id   | UUID         | FK → courses.id | Parent course         |
| created_at  | TIMESTAMP    | NOT NULL        | Creation timestamp    |
| updated_at  | TIMESTAMP    | NOT NULL        | Last update timestamp |
| deleted_at  | TIMESTAMP    |                 | Soft delete timestamp |

**Indexes**:

-    `idx_section_course_id` ON (course_id)

### 9. Lessons Table

Manages lessons within each section.

**Table Name**: `lessons`

| Column           | Type         | Constraints            | Description            |
| ---------------- | ------------ | ---------------------- | ---------------------- |
| id               | UUID         | PK                     | Lesson ID              |
| title            | VARCHAR(200) | NOT NULL               | Lesson title           |
| description      | TEXT         |                        | Lesson description     |
| content          | TEXT         |                        | Text content           |
| video_url        | TEXT         |                        | Lesson video URL       |
| duration_minutes | INTEGER      |                        | Duration in minutes    |
| order_index      | INTEGER      | NOT NULL               | Order within section   |
| is_free          | BOOLEAN      | DEFAULT FALSE          | Free preview available |
| section_id       | UUID         | FK → course_section.id | Parent section         |
| created_at       | TIMESTAMP    | NOT NULL               | Creation timestamp     |
| updated_at       | TIMESTAMP    | NOT NULL               | Last update timestamp  |
| deleted_at       | TIMESTAMP    |                        | Soft delete timestamp  |

**Indexes**:

-    `idx_lessons_section_id` ON (section_id)

## Learning Progress Tables

### 10. Enrollments Table

Manages student course enrollments.

**Table Name**: `enrollments`

| Column              | Type        | Constraints     | Description                |
| ------------------- | ----------- | --------------- | -------------------------- |
| id                  | UUID        | PK              | Enrollment ID              |
| student_id          | UUID        | FK → users.id   | Student user               |
| course_id           | UUID        | FK → courses.id | Enrolled course            |
| enrolled_at         | TIMESTAMP   | NOT NULL        | Enrollment timestamp       |
| completed_at        | TIMESTAMP   |                 | Completion timestamp       |
| progress_percentage | INTEGER     | DEFAULT 0       | Progress percentage        |
| status              | VARCHAR(20) | NOT NULL        | ACTIVE, COMPLETED, EXPIRED |
| certificate_url     | TEXT        |                 | Certificate URL            |
| created_at          | TIMESTAMP   | NOT NULL        | Creation timestamp         |
| updated_at          | TIMESTAMP   | NOT NULL        | Last update timestamp      |
| deleted_at          | TIMESTAMP   |                 | Soft delete timestamp      |

**Indexes**:

-    `idx_enrollments_student_id` ON (student_id)
-    `idx_enrollments_course_id` ON (course_id)

**Unique Constraint**: (student_id, course_id) - One enrollment per student per course

### 11. Lesson_Progress Table

Tracks individual lesson completion progress.

**Table Name**: `lesson_progress`

| Column             | Type      | Constraints         | Description                 |
| ------------------ | --------- | ------------------- | --------------------------- |
| id                 | UUID      | PK                  | Progress ID                 |
| enrollment_id      | UUID      | FK → enrollments.id | Related enrollment          |
| lesson_id          | UUID      | FK → lessons.id     | Lesson being tracked        |
| is_completed       | BOOLEAN   | DEFAULT FALSE       | Completion status           |
| completed_at       | TIMESTAMP |                     | Completion timestamp        |
| watch_time_seconds | INTEGER   | DEFAULT 0           | Video watch time in seconds |
| created_at         | TIMESTAMP | NOT NULL            | Creation timestamp          |
| updated_at         | TIMESTAMP | NOT NULL            | Last update timestamp       |
| deleted_at         | TIMESTAMP |                     | Soft delete timestamp       |

**Indexes**:

-    `idx_progress_enrollment_id` ON (enrollment_id)
-    `idx_progress_lesson_id` ON (lesson_id)

**Unique Constraint**: (enrollment_id, lesson_id)

## Assessment Tables

### 12. Quizzes Table

Manages quizzes and assessments.

**Table Name**: `quizzes`

| Column             | Type         | Constraints     | Description              |
| ------------------ | ------------ | --------------- | ------------------------ |
| id                 | UUID         | PK              | Quiz ID                  |
| title              | VARCHAR(200) | NOT NULL        | Quiz title               |
| description        | TEXT         |                 | Quiz description         |
| passing_score      | INTEGER      | NOT NULL        | Passing score percentage |
| time_limit_minutes | INTEGER      |                 | Time limit in minutes    |
| max_attempts       | INTEGER      |                 | Maximum attempts allowed |
| lesson_id          | UUID         | FK → lessons.id | Associated lesson        |
| created_at         | TIMESTAMP    | NOT NULL        | Creation timestamp       |
| updated_at         | TIMESTAMP    | NOT NULL        | Last update timestamp    |
| deleted_at         | TIMESTAMP    |                 | Soft delete timestamp    |

**Indexes**:

-    `idx_quizzes_lesson_id` ON (lesson_id)

### 13. Questions Table

Manages quiz questions.

**Table Name**: `questions`

| Column         | Type         | Constraints     | Description                 |
| -------------- | ------------ | --------------- | --------------------------- |
| id             | UUID         | PK              | Question ID                 |
| quiz_id        | UUID         | FK → quizzes.id | Parent quiz                 |
| question_text  | TEXT         | NOT NULL        | Question content            |
| question_type  | VARCHAR(20)  | NOT NULL        | MULTIPLE_CHOICE, TRUE_FALSE |
| options        | JSONB        |                 | Answer options (JSON array) |
| correct_answer | VARCHAR(255) | NOT NULL        | Correct answer key          |
| points         | INTEGER      | NOT NULL        | Points value                |
| created_at     | TIMESTAMP    | NOT NULL        | Creation timestamp          |
| updated_at     | TIMESTAMP    | NOT NULL        | Last update timestamp       |
| deleted_at     | TIMESTAMP    |                 | Soft delete timestamp       |

**Indexes**:

-    `idx_questions_quiz_id` ON (quiz_id)

### 14. Quiz_Attempts Table

Stores student quiz attempts.

**Table Name**: `quiz_attempts`

| Column       | Type      | Constraints     | Description            |
| ------------ | --------- | --------------- | ---------------------- |
| id           | UUID      | PK              | Attempt ID             |
| quiz_id      | UUID      | FK → quizzes.id | Quiz attempted         |
| student_id   | UUID      | FK → users.id   | Student user           |
| score        | INTEGER   | NOT NULL        | Score achieved         |
| max_score    | INTEGER   | NOT NULL        | Maximum possible score |
| is_passed    | BOOLEAN   | DEFAULT FALSE   | Pass/fail status       |
| started_at   | TIMESTAMP | NOT NULL        | Start timestamp        |
| completed_at | TIMESTAMP |                 | Submission timestamp   |
| answers      | JSONB     |                 | Student answers (JSON) |
| created_at   | TIMESTAMP | NOT NULL        | Creation timestamp     |
| updated_at   | TIMESTAMP | NOT NULL        | Last update timestamp  |
| deleted_at   | TIMESTAMP |                 | Soft delete timestamp  |

**Indexes**:

-    `idx_attempts_quiz_id` ON (quiz_id)
-    `idx_attempts_student_id` ON (student_id)

### 15. Assignments Table

Manages course assignments.

**Table Name**: `assignments`

| Column      | Type         | Constraints     | Description             |
| ----------- | ------------ | --------------- | ----------------------- |
| id          | UUID         | PK              | Assignment ID           |
| title       | VARCHAR(200) | NOT NULL        | Assignment title        |
| description | TEXT         |                 | Assignment requirements |
| due_date    | TIMESTAMP    |                 | Submission deadline     |
| max_score   | INTEGER      | NOT NULL        | Maximum score           |
| course_id   | UUID         | FK → courses.id | Parent course           |
| created_at  | TIMESTAMP    | NOT NULL        | Creation timestamp      |
| updated_at  | TIMESTAMP    | NOT NULL        | Last update timestamp   |
| deleted_at  | TIMESTAMP    |                 | Soft delete timestamp   |

**Indexes**:

-    `idx_assignments_course_id` ON (course_id)

### 16. Submissions Table

Manages student assignment submissions.

**Table Name**: `submissions`

| Column         | Type        | Constraints         | Description           |
| -------------- | ----------- | ------------------- | --------------------- |
| id             | UUID        | PK                  | Submission ID         |
| assignment_id  | UUID        | FK → assignments.id | Parent assignment     |
| student_id     | UUID        | FK → users.id       | Submitting student    |
| content        | TEXT        |                     | Submission content    |
| attachment_url | TEXT        |                     | Attachment file URL   |
| submitted_at   | TIMESTAMP   | NOT NULL            | Submission timestamp  |
| graded_at      | TIMESTAMP   |                     | Grading timestamp     |
| score          | INTEGER     |                     | Score given           |
| feedback       | TEXT        |                     | Instructor feedback   |
| status         | VARCHAR(20) | NOT NULL            | PENDING, GRADED       |
| created_at     | TIMESTAMP   | NOT NULL            | Creation timestamp    |
| updated_at     | TIMESTAMP   | NOT NULL            | Last update timestamp |
| deleted_at     | TIMESTAMP   |                     | Soft delete timestamp |

**Indexes**:

-    `idx_submissions_assignment_id` ON (assignment_id)
-    `idx_submissions_student_id` ON (student_id)

## Rating & Review Tables

### 17. Reviews Table

Manages course reviews from students.

**Table Name**: `reviews`

| Column     | Type      | Constraints     | Description             |
| ---------- | --------- | --------------- | ----------------------- |
| id         | UUID      | PK              | Review ID               |
| course_id  | UUID      | FK → courses.id | Reviewed course         |
| student_id | UUID      | FK → users.id   | Reviewing student       |
| rating     | INTEGER   | NOT NULL (1-5)  | Star rating (1-5)       |
| comment    | TEXT      |                 | Detailed review comment |
| created_at | TIMESTAMP | NOT NULL        | Creation timestamp      |
| updated_at | TIMESTAMP | NOT NULL        | Last update timestamp   |
| deleted_at | TIMESTAMP |                 | Soft delete timestamp   |

**Indexes**:

-    `idx_reviews_course_id` ON (course_id)
-    `idx_reviews_student_id` ON (student_id)

**Unique Constraint**: (course_id, student_id) - One review per student per course

## Payment & Order Tables

### 18. Orders Table

Manages course purchase orders.

**Table Name**: `orders`

| Column          | Type          | Constraints   | Description                   |
| --------------- | ------------- | ------------- | ----------------------------- |
| id              | UUID          | PK            | Order ID                      |
| order_code      | VARCHAR(50)   | UNIQUE        | Unique order code             |
| user_id         | UUID          | FK → users.id | Purchasing user               |
| total_amount    | DECIMAL(10,2) | NOT NULL      | Total amount                  |
| discount_amount | DECIMAL(10,2) | DEFAULT 0     | Discount applied              |
| final_amount    | DECIMAL(10,2) | NOT NULL      | Final payment amount          |
| status          | VARCHAR(20)   | NOT NULL      | PENDING, COMPLETED, CANCELLED |
| created_at      | TIMESTAMP     | NOT NULL      | Creation timestamp            |
| updated_at      | TIMESTAMP     | NOT NULL      | Last update timestamp         |
| deleted_at      | TIMESTAMP     |               | Soft delete timestamp         |

**Indexes**:

-    `idx_orders_user_id` ON (user_id)
-    `idx_orders_order_code` ON (order_code)

### 19. Order_Item Table

Order line items (courses in order).

**Table Name**: `order_item`

| Column     | Type          | Constraints     | Description            |
| ---------- | ------------- | --------------- | ---------------------- |
| id         | UUID          | PK              | Item ID                |
| order_id   | UUID          | FK → orders.id  | Parent order           |
| course_id  | UUID          | FK → courses.id | Purchased course       |
| price      | DECIMAL(10,2) | NOT NULL        | Price at purchase time |
| discount   | DECIMAL(10,2) | DEFAULT 0       | Item discount          |
| created_at | TIMESTAMP     | NOT NULL        | Creation timestamp     |
| updated_at | TIMESTAMP     | NOT NULL        | Last update timestamp  |
| deleted_at | TIMESTAMP     |                 | Soft delete timestamp  |

**Indexes**:

-    `idx_order_items_order_id` ON (order_id)
-    `idx_order_items_course_id` ON (course_id)

### 20. Payments Table

Manages payment transactions.

**Table Name**: `payments`

| Column         | Type          | Constraints    | Description              |
| -------------- | ------------- | -------------- | ------------------------ |
| id             | UUID          | PK             | Payment ID               |
| order_id       | UUID          | FK → orders.id | Related order            |
| amount         | DECIMAL(10,2) | NOT NULL       | Payment amount           |
| payment_method | VARCHAR(50)   | NOT NULL       | VNPAY, MOMO, COD         |
| transaction_id | VARCHAR(100)  |                | External transaction ID  |
| status         | VARCHAR(20)   | NOT NULL       | PENDING, SUCCESS, FAILED |
| paid_at        | TIMESTAMP     |                | Payment completion time  |
| created_at     | TIMESTAMP     | NOT NULL       | Creation timestamp       |
| updated_at     | TIMESTAMP     | NOT NULL       | Last update timestamp    |
| deleted_at     | TIMESTAMP     |                | Soft delete timestamp    |

**Indexes**:

-    `idx_payments_order_id` ON (order_id)
-    `idx_payments_transaction_id` ON (transaction_id)

### 21. Coupon Table

Manages discount coupons.

**Table Name**: `coupon`

| Column         | Type          | Constraints  | Description           |
| -------------- | ------------- | ------------ | --------------------- |
| id             | UUID          | PK           | Coupon ID             |
| code           | VARCHAR(50)   | UNIQUE       | Coupon code           |
| discount_type  | VARCHAR(20)   | NOT NULL     | PERCENTAGE, FIXED     |
| discount_value | DECIMAL(10,2) | NOT NULL     | Discount value        |
| max_uses       | INTEGER       |              | Maximum usage count   |
| used_count     | INTEGER       | DEFAULT 0    | Current usage count   |
| valid_from     | TIMESTAMP     | NOT NULL     | Valid from date       |
| valid_until    | TIMESTAMP     | NOT NULL     | Expiration date       |
| is_active      | BOOLEAN       | DEFAULT TRUE | Active status         |
| created_at     | TIMESTAMP     | NOT NULL     | Creation timestamp    |
| updated_at     | TIMESTAMP     | NOT NULL     | Last update timestamp |
| deleted_at     | TIMESTAMP     |              | Soft delete timestamp |

**Indexes**:

-    `idx_coupon_code` ON (code)

## Notification Table

### 22. Notifications Table

Manages user notifications.

**Table Name**: `notifications`

| Column     | Type         | Constraints   | Description                |
| ---------- | ------------ | ------------- | -------------------------- |
| id         | UUID         | PK            | Notification ID            |
| user_id    | UUID         | FK → users.id | Recipient user             |
| title      | VARCHAR(200) | NOT NULL      | Notification title         |
| message    | TEXT         | NOT NULL      | Notification content       |
| type       | VARCHAR(50)  | NOT NULL      | COURSE, ASSIGNMENT, SYSTEM |
| is_read    | BOOLEAN      | DEFAULT FALSE | Read status                |
| read_at    | TIMESTAMP    |               | Read timestamp             |
| created_at | TIMESTAMP    | NOT NULL      | Creation timestamp         |
| updated_at | TIMESTAMP    | NOT NULL      | Last update timestamp      |
| deleted_at | TIMESTAMP    |               | Soft delete timestamp      |

**Indexes**:

-    `idx_notifications_user_id` ON (user_id)
-    `idx_notifications_is_read` ON (is_read)

## Database Constraints Summary

### Foreign Key Constraints

-    All foreign keys use `ON DELETE CASCADE` or `ON DELETE SET NULL`
-    Referential integrity enforced at database level

### Unique Constraints

-    `users.email`: UNIQUE
-    `users.username`: UNIQUE
-    `users.phone`: UNIQUE
-    `role.role_name`: UNIQUE
-    `categories.name`: UNIQUE
-    `courses.slug`: UNIQUE
-    `orders.order_code`: UNIQUE
-    `coupon.code`: UNIQUE
-    `(enrollments.student_id, enrollments.course_id)`: UNIQUE
-    `(reviews.course_id, reviews.student_id)`: UNIQUE
-    `(lesson_progress.enrollment_id, lesson_progress.lesson_id)`: UNIQUE

### Check Constraints

-    `reviews.rating`: CHECK (rating >= 1 AND rating <= 5)
-    `courses.price`: CHECK (price >= 0)
-    `enrollments.progress_percentage`: CHECK (progress_percentage >= 0 AND progress_percentage <= 100)

## Soft Delete Pattern

All tables use soft delete pattern with `deleted_at` column:

-    `deleted_at IS NULL`: Active record
-    `deleted_at IS NOT NULL`: Soft deleted record
-    Entity classes use `@Where(clause = "deleted_at IS NULL")` annotation

## Migration Files

### V1\_\_init_schema.sql

Creates all base tables and indexes.

### V2\_\_seed_admin.sql

Seeds initial data:

-    3 roles: ADMIN, INSTRUCTOR, STUDENT
-    Admin user:
     -    Email: `admin@learnify.com`
     -    Password: `Admin@123`
     -    BCrypt hash: `$2a$10$.2PuNduogWXaCrPTMsIP8e7MSrli6/i0X3Dab4aq3Zyw9h/JlRLka`

### V3\_\_add_deleted_at_column.sql

Adds `deleted_at` column to all tables for soft delete support.

## Security Considerations

### Password Storage

-    Passwords được hash bằng BCrypt với default strength
-    Không bao giờ lưu plain text password

### Sensitive Data

-    Email addresses được index để tăng tốc lookup
-    Phone numbers là optional và unique

### Audit Trail

-    Tất cả tables có `created_at` và `updated_at`
-    Soft delete giữ lại lịch sử data

## Performance Optimization

### Indexes Strategy

-    Primary keys: UUID with btree index
-    Foreign keys: Indexed for JOIN performance
-    Frequently queried columns: email, username, slug, order_code
-    Status/flag columns: is_published, is_read, status

### Query Optimization

-    Use `@Where` clause để filter deleted records
-    Lazy loading cho relationships
-    Pagination cho list queries

## Backup & Recovery

-    Regular automated backups recommended
-    Point-in-time recovery capability
-    Flyway baseline để migrate existing databases

## Database Seeding

### Development Data

Run `seed_data.sql` to create:

-    Roles (ADMIN, INSTRUCTOR, STUDENT)
-    Admin user with known password
-    Sample categories, courses (optional)

### Production Data

-    Only seed roles and admin user
-    Do not seed sample data

## Connection Pool Configuration

```yaml
spring:
     datasource:
          hikari:
               maximum-pool-size: 10
               minimum-idle: 5
               connection-timeout: 30000
               idle-timeout: 600000
               max-lifetime: 1800000
```

## Monitoring & Maintenance

### Regular Tasks

-    Monitor slow queries
-    Update statistics
-    Vacuum tables (PostgreSQL)
-    Check index usage
-    Monitor connection pool

### Health Checks

-    Connection pool status
-    Active connections
-    Long-running queries
-    Database size

## Future Enhancements

-    Full-text search indexes
-    Table partitioning for large tables
-    Materialized views for reporting
-    Caching strategy (Redis)
-    Database sharding for scalability

## Contact & Support

For database schema questions or modifications, contact the development team.

---

**Last Updated**: March 2, 2026  
**Version**: 1.0  
**Database Version**: PostgreSQL 18.1
