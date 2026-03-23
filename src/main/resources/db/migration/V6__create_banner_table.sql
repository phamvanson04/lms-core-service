-- V6__create_banner_table.sql
-- Create banner table to match Banner entity mapping

CREATE TABLE IF NOT EXISTS banner (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    image_url TEXT,
    active BOOLEAN NOT NULL,
    priority INTEGER,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    course_id UUID REFERENCES courses(id) ON DELETE SET NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_banner_active ON banner(active);
CREATE INDEX IF NOT EXISTS idx_banner_priority ON banner(priority);
CREATE INDEX IF NOT EXISTS idx_banner_course_id ON banner(course_id);
