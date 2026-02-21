- Index in class_score
- Verify index 
SELECT schemaname, tablename, indexname, indexdef
FROM pg_indexes 
WHERE schemaname = 'public' 
  AND tablename = 'class_score' 
  AND indexname = 'idx_enrollment_id';
