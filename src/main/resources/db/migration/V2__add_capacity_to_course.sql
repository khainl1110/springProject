-- V2__add_capacity_to_course.sql

ALTER TABLE course
    ADD COLUMN capacity INT NOT NULL DEFAULT 0,
    ADD COLUMN available_seats INT NOT NULL DEFAULT 0;

-- Optional: initialize existing rows
UPDATE course
SET capacity = 30,
    available_seats = 30
WHERE capacity = 0;
