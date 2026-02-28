-- V3__add_date_to_enrollment.sql

ALTER TABLE enrollment
    ADD COLUMN enrollment_date DATE NOT NULL DEFAULT CURRENT_DATE;
