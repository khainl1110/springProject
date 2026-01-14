# API Reference

This document lists available REST endpoints implemented in the controllers.

## EnrollmentController (base path: /enrollment)
- GET /enrollment
  - Description: Returns all Enrollment entities (may include circular references).
  - Response: `Iterable<Enrollment>`

- GET /enrollment/all
  - Description: Returns all enrollments mapped to `EnrollmentDto` to avoid circular references.
  - Response: `List<EnrollmentDto>`

- POST /enrollment
  - Description: Create an enrollment for a student in a course after checking prerequisites.
  - Request body: JSON `{ "studentId": Long, "courseId": Long }` (see internal `EnrollmentRequest`).
  - Responses:
    - `201 Created`: EnrollmentDto of created enrollment
    - `400 Bad Request`: missing studentId/courseId or missing prerequisites
    - `404 Not Found`: student or course not found
    - `409 Conflict`: student already enrolled in the course

- PUT /enrollment/{id}/complete
  - Description: Mark an enrollment as completed.
  - Path parameter: `id` (Long)
  - Responses:
    - `200 OK`: EnrollmentDto of updated enrollment
    - `404 Not Found`: enrollment not found

Source: [src/main/java/com/example/backend/controller/EnrollmentController.java](src/main/java/com/example/backend/controller/EnrollmentController.java)

## StudentController (base path: /student)
- GET /student/{email}
  - Description: Return the `Student` (with enrollments) for the given email.
  - Path parameter: `email` (String)
  - Response: `Student`

- GET /student/{email}/details
  - Description: Return a summary map with `name` and `enrollments` where each enrollment contains course name and prerequisite names.
  - Path parameter: `email` (String)
  - Response: `Map<String, Object>` with `name` and `enrollments` list

- POST /student
  - Description: Create a new student. `email` is required.
  - Request body: `Student` JSON
  - Responses:
    - `200 OK`: created `Student`
    - `400 Bad Request`: missing email
    - `409 Conflict`: email already exists

- PUT /student
  - Description: Update an existing student (identified by email in payload). Updates `name`.
  - Request body: `Student` JSON (must include `email`)
  - Responses:
    - `200 OK`: updated `Student`
    - `400 Bad Request`: missing email
    - `404 Not Found`: student not found

- DELETE /student
  - Description: Delete all students.
  - Responses:
    - `204 No Content` on success

Source: [src/main/java/com/example/backend/controller/StudentController.java](src/main/java/com/example/backend/controller/StudentController.java)

## CourseController (base path: /course)
- GET /course
  - Description: Return all courses.
  - Response: `Iterable<Course>`

- POST /course
  - Description: Create a new course.
  - Request body: `Course` JSON
  - Response: saved course `toString()` (controller currently returns String)

Source: [src/main/java/com/example/backend/controller/CourseController.java](src/main/java/com/example/backend/controller/CourseController.java)
