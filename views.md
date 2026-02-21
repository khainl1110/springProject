-- 1. Drop existing (if any)
DROP VIEW IF EXISTS course_leaderboard;

-- 2. Create updated view
CREATE VIEW course_leaderboard AS
SELECT 
  c.name as course_name,
  s.email,
  s.name as student_name,
  cs.grade,
  RANK() OVER (PARTITION BY c.id ORDER BY cs.grade DESC) as rank,
  COUNT(*) OVER (PARTITION BY c.id) as total_students
FROM student s
JOIN enrollment e ON s.id = e.student_id
JOIN course c ON e.course_id = c.id
JOIN class_score cs ON cs.enrollment_id = e.id;

-- 3. View student score
SELECT * FROM course_leaderboard WHERE rank <= 3 ORDER BY course_name, rank;