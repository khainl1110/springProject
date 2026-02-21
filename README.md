# Student Management
This project demonstrate student management with spring boot and sql

Database is set up through Docker in docker/postgresql/dockerfile

Schema: 
- course: id (PK), code (art101), name, description
- course junction table: course_id, prerequisite_id (another course)
- enrollment: id (PK), completed (true or false), course_id, student_id
- student: id (PK), email, name
- class_score: id (PK), assessment_type, max_score, recorded_at, score, enrollment_id

Current functionalities included: add course/student/enrollment
- Course can add prerequisites, if student didn't complete prerequisites, they are not allow to enroll in the courses
- Student can add scores and query scores based on class or their entire score

## Set up
- In order to get this running, set up a local .env file with db values and url, db_url and db_username (Supabase)
# What I learn
- How to use Hibernate to map to SQL
- JPA method naming convetion to build queries without using SQL: https://dev.to/krishna-nayak/spring-data-jpa-method-naming-conventions-build-queries-without-writing-sql-23o5
- N+1 fixes using DTO object
- Implemented defensive null-safety pattern for Enrollment object
