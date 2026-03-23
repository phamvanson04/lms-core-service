-- V2__seed_data.sql
-- Seed initial data for Learnify LMS

-- Insert default roles
INSERT INTO role (id, role_name) VALUES
    (gen_random_uuid(), 'ADMIN'),
    (gen_random_uuid(), 'INSTRUCTOR'),
    (gen_random_uuid(), 'STUDENT')
ON CONFLICT (role_name) DO NOTHING;
--S123456@
INSERT INTO users (id, email, username, password, full_name, phone, avatar_url, bio, created_at, updated_at)
VALUES (
    gen_random_uuid(),
    'vanson2004tkdh@gmail.com',
    'ADMIN',
    '$2a$12$2cvOxFV/8EtbsCw4EJ4OTelVmN4itVeYn0tfllDqn4fBqg4AJdl2u',
    'System Administrator',
    NULL,
    NULL,
    'System administrator account',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)
ON CONFLICT (email) DO NOTHING;

-- Assign ROLE_ADMIN to admin user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, role r
WHERE u.email = 'vanson2004tkdh@gmail.com' AND r.role_name = 'ADMIN'
ON CONFLICT DO NOTHING;

-- Insert default permission for ROLE_ADMIN
INSERT INTO permission (id, permission_code) VALUES
    (gen_random_uuid(), 'USER_READ'),
    (gen_random_uuid(), 'USER_WRITE'),
    (gen_random_uuid(), 'USER_DELETE'),
    (gen_random_uuid(), 'COURSE_READ'),
    (gen_random_uuid(), 'COURSE_WRITE'),
    (gen_random_uuid(), 'COURSE_DELETE'),
    (gen_random_uuid(), 'ENROLLMENT_READ'),
    (gen_random_uuid(), 'ENROLLMENT_WRITE'),
    (gen_random_uuid(), 'ORDER_READ'),
    (gen_random_uuid(), 'ORDER_WRITE'),
    (gen_random_uuid(), 'PAYMENT_READ'),
    (gen_random_uuid(), 'PAYMENT_WRITE'),
    (gen_random_uuid(), 'CATEGORY_READ'),
    (gen_random_uuid(), 'CATEGORY_WRITE'),
    (gen_random_uuid(), 'COUPON_READ'),
    (gen_random_uuid(), 'COUPON_WRITE')
ON CONFLICT (permission_code) DO NOTHING;

-- Assign all permission to ROLE_ADMIN
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM role r
CROSS JOIN permission p
WHERE r.role_name = 'ADMIN'
ON CONFLICT DO NOTHING;

-- Insert default categories
INSERT INTO categories (id, name, description, image_url, active, created_at, updated_at) VALUES
    (gen_random_uuid(), 'Programming', 'Learn programming languages and software development', NULL, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'Design', 'Graphic design, UI/UX, and creative tools', NULL, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'Business', 'Business management, marketing, and entrepreneurship', NULL, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'Data Science', 'Data analysis, machine learning, and AI', NULL, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'Personal Development', 'Self-improvement and productivity', NULL, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;
