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