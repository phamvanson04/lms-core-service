# Learnify LMS - API Design Documentation

## Overview

This document describes the RESTful API design for the Learnify Learning Management System. The API follows REST principles, uses JWT authentication, and returns JSON responses.

## Base Information

-    **Base URL**: `http://localhost:8081/api/v1`
-    **Authentication**: JWT tokens (HTTP-only cookies)
-    **Content-Type**: `application/json`
-    **Response Format**: JSON

## Authentication Flow

### JWT Token Structure

-    **Access Token**: Short-lived token (15 minutes) for API requests
-    **Refresh Token**: Long-lived token (7 days) for obtaining new access tokens
-    Both tokens stored in HTTP-only cookies for security

### Cookie Names

-    `access_token`: Contains JWT access token
-    `refresh_token`: Contains JWT refresh token

---

## API Endpoints

## 1. Authentication APIs

### 1.1 Register New User

**Endpoint**: `POST /auth/register`

**Description**: Register a new student account.

**Request Body**:

```json
{
	"username": "john_doe",
	"email": "john@example.com",
	"password": "SecurePass123!",
	"fullName": "John Doe",
	"phone": "+1234567890"
}
```

**Validation Rules**:

-    `username`: 3-50 characters, alphanumeric and underscore only
-    `email`: Valid email format, unique
-    `password`: Minimum 8 characters, must contain uppercase, lowercase, number, special character
-    `fullName`: 2-100 characters
-    `phone`: Optional, valid phone format

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Registration successful",
	"data": {
		"username": "john_doe",
		"email": "john@example.com",
		"fullName": "John Doe",
		"phone": "+1234567890",
		"roles": ["STUDENT"]
	}
}
```

**Error Responses**:

-    `400 Bad Request`: Validation error
-    `409 Conflict`: Email or username already exists

---

### 1.2 Login

**Endpoint**: `POST /auth/login`

**Description**: Authenticate user and receive JWT tokens.

**Request Body**:

```json
{
	"email": "john@example.com",
	"password": "SecurePass123!"
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Login successful",
	"data": {
		"user": {
			"id": "uuid",
			"username": "john_doe",
			"email": "john@example.com",
			"fullName": "John Doe",
			"avatarUrl": "https://...",
			"roles": ["STUDENT"]
		}
	}
}
```

**Headers (Set-Cookie)**:

```
Set-Cookie: access_token=<jwt_token>; HttpOnly; Path=/; Max-Age=900
Set-Cookie: refresh_token=<jwt_token>; HttpOnly; Path=/; Max-Age=604800
```

**Error Responses**:

-    `401 Unauthorized`: Invalid credentials
-    `403 Forbidden`: Account disabled or deleted

---

### 1.3 Logout

**Endpoint**: `POST /auth/logout`

**Description**: Logout user and clear JWT tokens.

**Authentication**: Required (access_token cookie)

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Logout successful"
}
```

**Headers (Set-Cookie)**:

```
Set-Cookie: access_token=; HttpOnly; Path=/; Max-Age=0
Set-Cookie: refresh_token=; HttpOnly; Path=/; Max-Age=0
```

---

### 1.4 Refresh Token

**Endpoint**: `POST /auth/refresh-token`

**Description**: Obtain new access token using refresh token.

**Authentication**: Required (refresh_token cookie)

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Token refreshed successfully"
}
```

**Headers (Set-Cookie)**:

```
Set-Cookie: access_token=<new_jwt_token>; HttpOnly; Path=/; Max-Age=900
```

**Error Responses**:

-    `401 Unauthorized`: Invalid or expired refresh token

---

### 1.5 Request OTP (Forgot Password)

**Endpoint**: `POST /auth/otp/request`

**Description**: Request OTP code for password reset.

**Request Body**:

```json
{
	"email": "john@example.com"
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "OTP sent to your email",
	"data": {
		"expiresIn": 300
	}
}
```

---

### 1.6 Verify OTP

**Endpoint**: `POST /auth/otp/verify`

**Description**: Verify OTP code for password reset.

**Request Body**:

```json
{
	"email": "john@example.com",
	"otp": "123456"
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "OTP verified successfully",
	"data": {
		"resetToken": "uuid-token-for-password-reset"
	}
}
```

**Error Responses**:

-    `400 Bad Request`: Invalid or expired OTP

---

### 1.7 Reset Password

**Endpoint**: `POST /auth/otp/reset-password`

**Description**: Reset password using verified OTP token.

**Request Body**:

```json
{
	"resetToken": "uuid-token-from-verify",
	"newPassword": "NewSecurePass123!"
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Password reset successful"
}
```

---

## 2. User Profile APIs

### 2.1 Get Current User Profile

**Endpoint**: `GET /users/profile`

**Authentication**: Required

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"id": "uuid",
		"username": "john_doe",
		"email": "john@example.com",
		"fullName": "John Doe",
		"phone": "+1234567890",
		"avatarUrl": "https://...",
		"bio": "Learning enthusiast",
		"roles": ["STUDENT"],
		"createdAt": "2024-01-01T00:00:00Z"
	}
}
```

---

### 2.2 Update User Profile

**Endpoint**: `PUT /users/profile`

**Authentication**: Required

**Request Body**:

```json
{
	"fullName": "John Updated Doe",
	"phone": "+1234567890",
	"bio": "Updated bio"
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Profile updated successfully",
	"data": {
		"id": "uuid",
		"username": "john_doe",
		"email": "john@example.com",
		"fullName": "John Updated Doe",
		"phone": "+1234567890",
		"bio": "Updated bio"
	}
}
```

---

### 2.3 Upload Avatar

**Endpoint**: `POST /users/avatar`

**Authentication**: Required

**Content-Type**: `multipart/form-data`

**Request Body**:

```
file: <image_file>
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Avatar uploaded successfully",
	"data": {
		"avatarUrl": "https://cloudinary.com/..."
	}
}
```

**Error Responses**:

-    `400 Bad Request`: Invalid file format (only JPG, PNG allowed)
-    `413 Payload Too Large`: File size exceeds 5MB

---

### 2.4 Change Password

**Endpoint**: `PUT /users/change-password`

**Authentication**: Required

**Request Body**:

```json
{
	"currentPassword": "OldPassword123!",
	"newPassword": "NewPassword123!"
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Password changed successfully"
}
```

**Error Responses**:

-    `400 Bad Request`: Current password incorrect

---

## 3. Course APIs

### 3.1 List All Courses (Public)

**Endpoint**: `GET /courses`

**Authentication**: Optional

**Query Parameters**:

-    `page` (default: 0): Page number
-    `size` (default: 20): Items per page
-    `search`: Search by title or description
-    `category`: Filter by category ID
-    `level`: Filter by level (BEGINNER, INTERMEDIATE, ADVANCED)
-    `minPrice`: Minimum price filter
-    `maxPrice`: Maximum price filter
-    `sort`: Sort field (price, createdAt, rating)
-    `direction`: Sort direction (asc, desc)

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"content": [
			{
				"id": "uuid",
				"title": "Complete Java Programming",
				"slug": "complete-java-programming",
				"shortDescription": "Learn Java from scratch",
				"thumbnailUrl": "https://...",
				"price": 99.99,
				"discountPrice": 79.99,
				"level": "BEGINNER",
				"language": "EN",
				"durationHours": 40,
				"rating": 4.5,
				"totalStudents": 1250,
				"instructor": {
					"id": "uuid",
					"fullName": "Jane Instructor",
					"avatarUrl": "https://..."
				},
				"category": {
					"id": "uuid",
					"name": "Programming"
				},
				"isPublished": true,
				"publishedAt": "2024-01-01T00:00:00Z"
			}
		],
		"pageable": {
			"pageNumber": 0,
			"pageSize": 20,
			"totalElements": 100,
			"totalPages": 5
		}
	}
}
```

---

### 3.2 Get Course Details

**Endpoint**: `GET /courses/{courseId}`

**Authentication**: Optional

**Path Parameters**:

-    `courseId`: Course UUID or slug

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"id": "uuid",
		"title": "Complete Java Programming",
		"slug": "complete-java-programming",
		"description": "Full course description...",
		"shortDescription": "Learn Java from scratch",
		"thumbnailUrl": "https://...",
		"price": 99.99,
		"discountPrice": 79.99,
		"level": "BEGINNER",
		"language": "EN",
		"durationHours": 40,
		"rating": 4.5,
		"totalStudents": 1250,
		"totalReviews": 350,
		"instructor": {
			"id": "uuid",
			"fullName": "Jane Instructor",
			"avatarUrl": "https://...",
			"bio": "Experienced Java developer"
		},
		"category": {
			"id": "uuid",
			"name": "Programming",
			"description": "Programming courses"
		},
		"sections": [
			{
				"id": "uuid",
				"title": "Introduction",
				"description": "Getting started",
				"orderIndex": 0,
				"lessons": [
					{
						"id": "uuid",
						"title": "Welcome to the course",
						"durationMinutes": 10,
						"orderIndex": 0,
						"isFree": true
					}
				]
			}
		],
		"isEnrolled": false,
		"isPublished": true,
		"publishedAt": "2024-01-01T00:00:00Z",
		"createdAt": "2023-12-01T00:00:00Z"
	}
}
```

---

### 3.3 Create Course (Instructor Only)

**Endpoint**: `POST /courses`

**Authentication**: Required (INSTRUCTOR or ADMIN role)

**Request Body**:

```json
{
	"title": "Complete Java Programming",
	"description": "Full course description",
	"shortDescription": "Learn Java from scratch",
	"price": 99.99,
	"level": "BEGINNER",
	"language": "EN",
	"categoryId": "uuid"
}
```

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Course created successfully",
	"data": {
		"id": "uuid",
		"title": "Complete Java Programming",
		"slug": "complete-java-programming",
		"isPublished": false,
		"createdAt": "2024-01-01T00:00:00Z"
	}
}
```

---

### 3.4 Update Course

**Endpoint**: `PUT /courses/{courseId}`

**Authentication**: Required (Course instructor or ADMIN)

**Request Body**: Same as Create Course

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Course updated successfully",
	"data": {
		/* Updated course object */
	}
}
```

---

### 3.5 Delete Course (Soft Delete)

**Endpoint**: `DELETE /courses/{courseId}`

**Authentication**: Required (Course instructor or ADMIN)

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Course deleted successfully"
}
```

---

### 3.6 Publish Course

**Endpoint**: `POST /courses/{courseId}/publish`

**Authentication**: Required (Course instructor or ADMIN)

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Course published successfully",
	"data": {
		"id": "uuid",
		"isPublished": true,
		"publishedAt": "2024-01-01T00:00:00Z"
	}
}
```

---

### 3.7 Unpublish Course

**Endpoint**: `POST /courses/{courseId}/unpublish`

**Authentication**: Required (Course instructor or ADMIN)

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Course unpublished successfully"
}
```

---

## 4. Course Section APIs

### 4.1 Create Section

**Endpoint**: `POST /courses/{courseId}/sections`

**Authentication**: Required (Course instructor or ADMIN)

**Request Body**:

```json
{
	"title": "Introduction",
	"description": "Getting started with the course",
	"orderIndex": 0
}
```

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Section created successfully",
	"data": {
		"id": "uuid",
		"title": "Introduction",
		"orderIndex": 0,
		"courseId": "uuid"
	}
}
```

---

### 4.2 Update Section

**Endpoint**: `PUT /sections/{sectionId}`

**Authentication**: Required (Course instructor or ADMIN)

**Request Body**:

```json
{
	"title": "Updated Introduction",
	"description": "Updated description",
	"orderIndex": 0
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Section updated successfully",
	"data": {
		/* Updated section */
	}
}
```

---

### 4.3 Delete Section

**Endpoint**: `DELETE /sections/{sectionId}`

**Authentication**: Required (Course instructor or ADMIN)

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Section deleted successfully"
}
```

---

## 5. Lesson APIs

### 5.1 Create Lesson

**Endpoint**: `POST /sections/{sectionId}/lessons`

**Authentication**: Required (Course instructor or ADMIN)

**Request Body**:

```json
{
	"title": "Welcome to the course",
	"description": "Introduction lesson",
	"content": "Lesson text content",
	"videoUrl": "https://...",
	"durationMinutes": 10,
	"orderIndex": 0,
	"isFree": true
}
```

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Lesson created successfully",
	"data": {
		"id": "uuid",
		"title": "Welcome to the course",
		"durationMinutes": 10,
		"isFree": true
	}
}
```

---

### 5.2 Get Lesson Details

**Endpoint**: `GET /lessons/{lessonId}`

**Authentication**: Required (Must be enrolled in course or course instructor)

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"id": "uuid",
		"title": "Welcome to the course",
		"description": "Introduction lesson",
		"content": "Full lesson text content",
		"videoUrl": "https://...",
		"durationMinutes": 10,
		"orderIndex": 0,
		"isFree": false,
		"isCompleted": true,
		"completedAt": "2024-01-15T00:00:00Z"
	}
}
```

---

### 5.3 Update Lesson

**Endpoint**: `PUT /lessons/{lessonId}`

**Authentication**: Required (Course instructor or ADMIN)

**Request Body**: Same as Create Lesson

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Lesson updated successfully",
	"data": {
		/* Updated lesson */
	}
}
```

---

### 5.4 Delete Lesson

**Endpoint**: `DELETE /lessons/{lessonId}`

**Authentication**: Required (Course instructor or ADMIN)

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Lesson deleted successfully"
}
```

---

### 5.5 Mark Lesson as Completed

**Endpoint**: `POST /lessons/{lessonId}/complete`

**Authentication**: Required (Student enrolled in course)

**Request Body**:

```json
{
	"watchTimeSeconds": 600
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Lesson marked as completed",
	"data": {
		"lessonId": "uuid",
		"isCompleted": true,
		"completedAt": "2024-01-15T00:00:00Z",
		"courseProgress": 25
	}
}
```

---

## 6. Enrollment APIs

### 6.1 Enroll in Course

**Endpoint**: `POST /enrollments`

**Authentication**: Required (STUDENT role)

**Request Body**:

```json
{
	"courseId": "uuid"
}
```

**Note**: Enrollment is typically done after successful payment. This endpoint may be called internally after payment completion.

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Enrolled successfully",
	"data": {
		"id": "uuid",
		"courseId": "uuid",
		"studentId": "uuid",
		"enrolledAt": "2024-01-01T00:00:00Z",
		"status": "ACTIVE",
		"progressPercentage": 0
	}
}
```

---

### 6.2 Get My Enrollments

**Endpoint**: `GET /enrollments/me`

**Authentication**: Required

**Query Parameters**:

-    `page` (default: 0)
-    `size` (default: 20)
-    `status`: Filter by status (ACTIVE, COMPLETED, EXPIRED)

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"content": [
			{
				"id": "uuid",
				"course": {
					"id": "uuid",
					"title": "Complete Java Programming",
					"thumbnailUrl": "https://...",
					"instructor": {
						"fullName": "Jane Instructor"
					}
				},
				"enrolledAt": "2024-01-01T00:00:00Z",
				"progressPercentage": 45,
				"status": "ACTIVE",
				"lastAccessedAt": "2024-01-15T00:00:00Z"
			}
		],
		"pageable": {
			"pageNumber": 0,
			"pageSize": 20,
			"totalElements": 5,
			"totalPages": 1
		}
	}
}
```

---

### 6.3 Get Enrollment Progress

**Endpoint**: `GET /enrollments/{enrollmentId}/progress`

**Authentication**: Required (Student owner or instructor)

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"enrollmentId": "uuid",
		"courseId": "uuid",
		"progressPercentage": 45,
		"completedLessons": 18,
		"totalLessons": 40,
		"lessonsProgress": [
			{
				"lessonId": "uuid",
				"lessonTitle": "Introduction",
				"isCompleted": true,
				"completedAt": "2024-01-05T00:00:00Z",
				"watchTimeSeconds": 600
			}
		],
		"quizResults": [
			{
				"quizId": "uuid",
				"quizTitle": "Chapter 1 Quiz",
				"score": 85,
				"isPassed": true,
				"attemptedAt": "2024-01-06T00:00:00Z"
			}
		],
		"assignmentSubmissions": [
			{
				"assignmentId": "uuid",
				"assignmentTitle": "Project 1",
				"status": "GRADED",
				"score": 90,
				"submittedAt": "2024-01-10T00:00:00Z"
			}
		]
	}
}
```

---

## 7. Quiz APIs

### 7.1 Create Quiz

**Endpoint**: `POST /lessons/{lessonId}/quizzes`

**Authentication**: Required (Course instructor or ADMIN)

**Request Body**:

```json
{
	"title": "Chapter 1 Quiz",
	"description": "Test your knowledge",
	"passingScore": 70,
	"timeLimitMinutes": 30,
	"maxAttempts": 3,
	"questions": [
		{
			"questionText": "What is Java?",
			"questionType": "MULTIPLE_CHOICE",
			"options": [
				{ "key": "A", "value": "Programming language" },
				{ "key": "B", "value": "Coffee" },
				{ "key": "C", "value": "Island" },
				{ "key": "D", "value": "All of the above" }
			],
			"correctAnswer": "A",
			"points": 10
		}
	]
}
```

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Quiz created successfully",
	"data": {
		"id": "uuid",
		"title": "Chapter 1 Quiz",
		"totalQuestions": 10,
		"totalPoints": 100
	}
}
```

---

### 7.2 Get Quiz Details

**Endpoint**: `GET /quizzes/{quizId}`

**Authentication**: Required (Enrolled student or instructor)

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"id": "uuid",
		"title": "Chapter 1 Quiz",
		"description": "Test your knowledge",
		"passingScore": 70,
		"timeLimitMinutes": 30,
		"maxAttempts": 3,
		"attemptsRemaining": 2,
		"questions": [
			{
				"id": "uuid",
				"questionText": "What is Java?",
				"questionType": "MULTIPLE_CHOICE",
				"options": [
					{ "key": "A", "value": "Programming language" },
					{ "key": "B", "value": "Coffee" }
				],
				"points": 10
			}
		]
	}
}
```

**Note**: `correctAnswer` field is not included in student view

---

### 7.3 Submit Quiz Attempt

**Endpoint**: `POST /quizzes/{quizId}/attempts`

**Authentication**: Required (Student enrolled in course)

**Request Body**:

```json
{
	"answers": [
		{
			"questionId": "uuid",
			"answer": "A"
		},
		{
			"questionId": "uuid",
			"answer": "B"
		}
	]
}
```

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Quiz submitted successfully",
	"data": {
		"attemptId": "uuid",
		"score": 85,
		"maxScore": 100,
		"isPassed": true,
		"correctAnswers": 17,
		"totalQuestions": 20,
		"completedAt": "2024-01-15T00:00:00Z",
		"results": [
			{
				"questionId": "uuid",
				"yourAnswer": "A",
				"correctAnswer": "A",
				"isCorrect": true,
				"points": 10
			}
		]
	}
}
```

---

### 7.4 Get Quiz Attempts

**Endpoint**: `GET /quizzes/{quizId}/attempts`

**Authentication**: Required

**Response (200 OK)**:

```json
{
	"success": true,
	"data": [
		{
			"id": "uuid",
			"score": 85,
			"maxScore": 100,
			"isPassed": true,
			"attemptNumber": 1,
			"startedAt": "2024-01-15T10:00:00Z",
			"completedAt": "2024-01-15T10:25:00Z"
		}
	]
}
```

---

## 8. Assignment APIs

### 8.1 Create Assignment

**Endpoint**: `POST /courses/{courseId}/assignments`

**Authentication**: Required (Course instructor or ADMIN)

**Request Body**:

```json
{
	"title": "Final Project",
	"description": "Build a complete application",
	"dueDate": "2024-02-01T23:59:59Z",
	"maxScore": 100
}
```

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Assignment created successfully",
	"data": {
		"id": "uuid",
		"title": "Final Project",
		"dueDate": "2024-02-01T23:59:59Z",
		"maxScore": 100
	}
}
```

---

### 8.2 Get Assignment Details

**Endpoint**: `GET /assignments/{assignmentId}`

**Authentication**: Required (Enrolled student or instructor)

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"id": "uuid",
		"title": "Final Project",
		"description": "Build a complete application",
		"dueDate": "2024-02-01T23:59:59Z",
		"maxScore": 100,
		"mySubmission": {
			"id": "uuid",
			"status": "GRADED",
			"score": 90,
			"submittedAt": "2024-01-25T00:00:00Z",
			"feedback": "Great work!"
		}
	}
}
```

---

### 8.3 Submit Assignment

**Endpoint**: `POST /assignments/{assignmentId}/submissions`

**Authentication**: Required (Student enrolled in course)

**Content-Type**: `multipart/form-data`

**Request Body**:

```
content: "My assignment submission text"
file: <file_attachment>
```

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Assignment submitted successfully",
	"data": {
		"id": "uuid",
		"assignmentId": "uuid",
		"content": "My assignment submission text",
		"attachmentUrl": "https://...",
		"submittedAt": "2024-01-25T00:00:00Z",
		"status": "PENDING"
	}
}
```

---

### 8.4 Grade Assignment Submission

**Endpoint**: `PUT /submissions/{submissionId}/grade`

**Authentication**: Required (Course instructor or ADMIN)

**Request Body**:

```json
{
	"score": 90,
	"feedback": "Excellent work! Keep it up."
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Submission graded successfully",
	"data": {
		"id": "uuid",
		"score": 90,
		"feedback": "Excellent work! Keep it up.",
		"status": "GRADED",
		"gradedAt": "2024-01-26T00:00:00Z"
	}
}
```

---

## 9. Review APIs

### 9.1 Create Course Review

**Endpoint**: `POST /courses/{courseId}/reviews`

**Authentication**: Required (Student enrolled in course)

**Request Body**:

```json
{
	"rating": 5,
	"comment": "Excellent course! Highly recommended."
}
```

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Review created successfully",
	"data": {
		"id": "uuid",
		"courseId": "uuid",
		"rating": 5,
		"comment": "Excellent course! Highly recommended.",
		"createdAt": "2024-01-20T00:00:00Z"
	}
}
```

**Error Responses**:

-    `400 Bad Request`: User not enrolled in course
-    `409 Conflict`: User already reviewed this course

---

### 9.2 Get Course Reviews

**Endpoint**: `GET /courses/{courseId}/reviews`

**Authentication**: Optional

**Query Parameters**:

-    `page` (default: 0)
-    `size` (default: 20)
-    `rating`: Filter by rating (1-5)

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"content": [
			{
				"id": "uuid",
				"student": {
					"id": "uuid",
					"fullName": "John Doe",
					"avatarUrl": "https://..."
				},
				"rating": 5,
				"comment": "Excellent course!",
				"createdAt": "2024-01-20T00:00:00Z"
			}
		],
		"pageable": {
			"pageNumber": 0,
			"pageSize": 20,
			"totalElements": 150,
			"totalPages": 8
		},
		"averageRating": 4.6
	}
}
```

---

### 9.3 Update Review

**Endpoint**: `PUT /reviews/{reviewId}`

**Authentication**: Required (Review owner)

**Request Body**:

```json
{
	"rating": 4,
	"comment": "Updated review comment"
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Review updated successfully",
	"data": {
		/* Updated review */
	}
}
```

---

### 9.4 Delete Review

**Endpoint**: `DELETE /reviews/{reviewId}`

**Authentication**: Required (Review owner or ADMIN)

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Review deleted successfully"
}
```

---

## 10. Order & Payment APIs

### 10.1 Create Order

**Endpoint**: `POST /orders`

**Authentication**: Required

**Request Body**:

```json
{
	"courseIds": ["uuid1", "uuid2"],
	"couponCode": "NEWYEAR2024"
}
```

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Order created successfully",
	"data": {
		"orderId": "uuid",
		"orderCode": "ORD-20240115-001",
		"items": [
			{
				"courseId": "uuid1",
				"courseTitle": "Complete Java Programming",
				"price": 99.99,
				"discount": 10.0
			}
		],
		"totalAmount": 189.98,
		"discountAmount": 20.0,
		"finalAmount": 169.98,
		"status": "PENDING",
		"createdAt": "2024-01-15T00:00:00Z"
	}
}
```

---

### 10.2 Get Order Details

**Endpoint**: `GET /orders/{orderId}`

**Authentication**: Required (Order owner or ADMIN)

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"id": "uuid",
		"orderCode": "ORD-20240115-001",
		"user": {
			"id": "uuid",
			"fullName": "John Doe",
			"email": "john@example.com"
		},
		"items": [
			{
				"courseId": "uuid",
				"courseTitle": "Complete Java Programming",
				"price": 99.99,
				"discount": 10.0
			}
		],
		"totalAmount": 99.99,
		"discountAmount": 10.0,
		"finalAmount": 89.99,
		"status": "COMPLETED",
		"payment": {
			"id": "uuid",
			"amount": 89.99,
			"paymentMethod": "VNPAY",
			"transactionId": "VNPAY123456",
			"status": "SUCCESS",
			"paidAt": "2024-01-15T10:00:00Z"
		},
		"createdAt": "2024-01-15T00:00:00Z"
	}
}
```

---

### 10.3 Get My Orders

**Endpoint**: `GET /orders/me`

**Authentication**: Required

**Query Parameters**:

-    `page` (default: 0)
-    `size` (default: 20)
-    `status`: Filter by status (PENDING, COMPLETED, CANCELLED)

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"content": [
			{
				"id": "uuid",
				"orderCode": "ORD-20240115-001",
				"finalAmount": 89.99,
				"status": "COMPLETED",
				"createdAt": "2024-01-15T00:00:00Z"
			}
		],
		"pageable": {
			"pageNumber": 0,
			"pageSize": 20,
			"totalElements": 5,
			"totalPages": 1
		}
	}
}
```

---

### 10.4 Initiate Payment

**Endpoint**: `POST /payments/initiate`

**Authentication**: Required

**Request Body**:

```json
{
	"orderId": "uuid",
	"paymentMethod": "VNPAY",
	"returnUrl": "https://learnify.com/payment/callback"
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Payment initiated",
	"data": {
		"paymentId": "uuid",
		"paymentUrl": "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html?...",
		"transactionId": "VNPAY123456"
	}
}
```

---

### 10.5 Payment Callback

**Endpoint**: `GET /payments/callback`

**Authentication**: Not required (handled by payment gateway)

**Query Parameters**: Varies by payment provider (VNPAY, MOMO)

**Response**: Redirect to frontend with payment status

---

### 10.6 Verify Payment

**Endpoint**: `POST /payments/{paymentId}/verify`

**Authentication**: Required

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Payment verified successfully",
	"data": {
		"paymentId": "uuid",
		"orderId": "uuid",
		"status": "SUCCESS",
		"paidAt": "2024-01-15T10:00:00Z",
		"enrollments": [
			{
				"courseId": "uuid",
				"enrollmentId": "uuid"
			}
		]
	}
}
```

---

## 11. Coupon APIs

### 11.1 Validate Coupon

**Endpoint**: `POST /coupons/validate`

**Authentication**: Required

**Request Body**:

```json
{
	"code": "NEWYEAR2024",
	"courseIds": ["uuid1", "uuid2"]
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"couponId": "uuid",
		"code": "NEWYEAR2024",
		"discountType": "PERCENTAGE",
		"discountValue": 20.0,
		"isValid": true,
		"message": "Coupon applied successfully"
	}
}
```

**Error Responses**:

-    `404 Not Found`: Coupon not found
-    `400 Bad Request`: Coupon expired or max uses exceeded

---

### 11.2 Create Coupon (Admin Only)

**Endpoint**: `POST /coupons`

**Authentication**: Required (ADMIN role)

**Request Body**:

```json
{
	"code": "NEWYEAR2024",
	"discountType": "PERCENTAGE",
	"discountValue": 20.0,
	"maxUses": 100,
	"validFrom": "2024-01-01T00:00:00Z",
	"validUntil": "2024-01-31T23:59:59Z",
	"isActive": true
}
```

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Coupon created successfully",
	"data": {
		"id": "uuid",
		"code": "NEWYEAR2024",
		"discountType": "PERCENTAGE",
		"discountValue": 20.0,
		"maxUses": 100,
		"usedCount": 0,
		"isActive": true
	}
}
```

---

## 12. Notification APIs

### 12.1 Get My Notifications

**Endpoint**: `GET /notifications/me`

**Authentication**: Required

**Query Parameters**:

-    `page` (default: 0)
-    `size` (default: 20)
-    `isRead`: Filter by read status (true/false)

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"content": [
			{
				"id": "uuid",
				"title": "New course enrolled",
				"message": "You have successfully enrolled in Complete Java Programming",
				"type": "COURSE",
				"isRead": false,
				"createdAt": "2024-01-15T00:00:00Z"
			}
		],
		"pageable": {
			"pageNumber": 0,
			"pageSize": 20,
			"totalElements": 10,
			"totalPages": 1
		},
		"unreadCount": 5
	}
}
```

---

### 12.2 Mark Notification as Read

**Endpoint**: `PUT /notifications/{notificationId}/read`

**Authentication**: Required (Notification owner)

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Notification marked as read",
	"data": {
		"id": "uuid",
		"isRead": true,
		"readAt": "2024-01-16T00:00:00Z"
	}
}
```

---

### 12.3 Mark All Notifications as Read

**Endpoint**: `PUT /notifications/read-all`

**Authentication**: Required

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "All notifications marked as read",
	"data": {
		"updatedCount": 5
	}
}
```

---

### 12.4 Delete Notification

**Endpoint**: `DELETE /notifications/{notificationId}`

**Authentication**: Required (Notification owner)

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "Notification deleted successfully"
}
```

---

## 13. Category APIs

### 13.1 Get All Categories

**Endpoint**: `GET /categories`

**Authentication**: Optional

**Response (200 OK)**:

```json
{
	"success": true,
	"data": [
		{
			"id": "uuid",
			"name": "Programming",
			"description": "Learn programming languages",
			"courseCount": 150
		},
		{
			"id": "uuid",
			"name": "Design",
			"description": "Graphic and UI/UX design",
			"courseCount": 85
		}
	]
}
```

---

### 13.2 Create Category (Admin Only)

**Endpoint**: `POST /categories`

**Authentication**: Required (ADMIN role)

**Request Body**:

```json
{
	"name": "Programming",
	"description": "Learn programming languages"
}
```

**Response (201 Created)**:

```json
{
	"success": true,
	"message": "Category created successfully",
	"data": {
		"id": "uuid",
		"name": "Programming",
		"description": "Learn programming languages"
	}
}
```

---

## 14. Admin APIs

### 14.1 Get All Users (Admin Only)

**Endpoint**: `GET /admin/users`

**Authentication**: Required (ADMIN role)

**Query Parameters**:

-    `page` (default: 0)
-    `size` (default: 20)
-    `role`: Filter by role (ADMIN, INSTRUCTOR, STUDENT)
-    `search`: Search by name or email

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"content": [
			{
				"id": "uuid",
				"username": "john_doe",
				"email": "john@example.com",
				"fullName": "John Doe",
				"roles": ["STUDENT"],
				"createdAt": "2024-01-01T00:00:00Z",
				"isDeleted": false
			}
		],
		"pageable": {
			"pageNumber": 0,
			"pageSize": 20,
			"totalElements": 1000,
			"totalPages": 50
		}
	}
}
```

---

### 14.2 Update User Role (Admin Only)

**Endpoint**: `PUT /admin/users/{userId}/roles`

**Authentication**: Required (ADMIN role)

**Request Body**:

```json
{
	"roles": ["INSTRUCTOR", "STUDENT"]
}
```

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "User roles updated successfully",
	"data": {
		"userId": "uuid",
		"roles": ["INSTRUCTOR", "STUDENT"]
	}
}
```

---

### 14.3 Delete User (Admin Only)

**Endpoint**: `DELETE /admin/users/{userId}`

**Authentication**: Required (ADMIN role)

**Response (200 OK)**:

```json
{
	"success": true,
	"message": "User deleted successfully"
}
```

---

### 14.4 Get Platform Statistics (Admin Only)

**Endpoint**: `GET /admin/statistics`

**Authentication**: Required (ADMIN role)

**Response (200 OK)**:

```json
{
	"success": true,
	"data": {
		"totalUsers": 10000,
		"totalCourses": 500,
		"totalEnrollments": 25000,
		"totalRevenue": 1500000.0,
		"revenueThisMonth": 150000.0,
		"newUsersThisMonth": 500,
		"newCoursesThisMonth": 20,
		"activeEnrollments": 18000,
		"completedEnrollments": 7000
	}
}
```

---

## Error Response Format

All API errors follow this structure:

```json
{
	"success": false,
	"message": "Error message",
	"errors": [
		{
			"field": "email",
			"message": "Email is required"
		}
	],
	"timestamp": "2024-01-15T10:00:00Z",
	"path": "/api/v1/auth/register"
}
```

### Common HTTP Status Codes

| Code | Meaning               | Usage                                       |
| ---- | --------------------- | ------------------------------------------- |
| 200  | OK                    | Successful GET, PUT, DELETE request         |
| 201  | Created               | Successful POST request creating a resource |
| 400  | Bad Request           | Invalid request data or validation error    |
| 401  | Unauthorized          | Missing or invalid authentication token     |
| 403  | Forbidden             | User lacks permission for this action       |
| 404  | Not Found             | Requested resource not found                |
| 409  | Conflict              | Resource conflict (e.g., duplicate email)   |
| 500  | Internal Server Error | Unexpected server error                     |

---

## Rate Limiting

-    **Authentication endpoints** (/auth/\*): 5 requests per minute per IP
-    **General endpoints**: 100 requests per minute per user
-    **File upload endpoints**: 10 requests per minute per user

**Rate Limit Headers**:

```
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 95
X-RateLimit-Reset: 1642252800
```

---

## Pagination

All list endpoints support pagination with these query parameters:

-    `page`: Page number (0-indexed)
-    `size`: Items per page (max 100)
-    `sort`: Sort field
-    `direction`: Sort direction (asc, desc)

**Response includes pagination metadata**:

```json
{
	"pageable": {
		"pageNumber": 0,
		"pageSize": 20,
		"totalElements": 100,
		"totalPages": 5,
		"first": true,
		"last": false
	}
}
```

---

## File Upload Constraints

-    **Avatar images**: Max 5MB, JPG/PNG only
-    **Lesson videos**: Use external service (Cloudinary, Vimeo)
-    **Assignment attachments**: Max 10MB, PDF/DOC/DOCX/ZIP allowed

---

## Security Headers

All API responses include security headers:

```
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
Strict-Transport-Security: max-age=31536000; includeSubDomains
```

---

## CORS Configuration

Allowed origins:

-    Development: `http://localhost:3000`, `http://localhost:4200`
-    Production: `https://learnify.com`

Allowed methods: GET, POST, PUT, DELETE, PATCH

Allowed headers: Authorization, Content-Type, Accept

---

## WebSocket Endpoints (Future Enhancement)

**Real-time notifications**:

-    Endpoint: `ws://localhost:8081/ws/notifications`
-    Authentication: JWT token in connection params

---

## Versioning

API versioning is handled via URL path (`/api/v1/`).

Future versions will use `/api/v2/`, `/api/v3/`, etc., maintaining backward compatibility for at least one major version.

---

## API Testing

### Sample cURL Commands

**Login**:

```bash
curl -X POST http://localhost:8081/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@learnify.com","password":"Admin@123"}' \
  -c cookies.txt
```

**Get Profile** (using saved cookies):

```bash
curl -X GET http://localhost:8081/api/v1/users/profile \
  -b cookies.txt
```

**Create Course**:

```bash
curl -X POST http://localhost:8081/api/v1/courses \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{
    "title": "Complete Java Programming",
    "description": "Learn Java from scratch",
    "price": 99.99,
    "level": "BEGINNER",
    "categoryId": "uuid"
  }'
```

---

## API Documentation Tools

-    **Swagger UI**: Available at `http://localhost:8081/swagger-ui.html` (if configured)
-    **OpenAPI Spec**: Available at `http://localhost:8081/v3/api-docs`

---

## Change Log

| Version | Date       | Changes             |
| ------- | ---------- | ------------------- |
| 1.0.0   | 2024-01-01 | Initial API release |

---

## Support

For API support, contact: support@learnify.com

---

**End of API Documentation**
