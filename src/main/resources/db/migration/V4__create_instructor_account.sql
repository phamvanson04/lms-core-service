-- Create instructor user account
INSERT INTO users (id, email, username, password, full_name, phone, created_at, updated_at)
VALUES (
    gen_random_uuid(),
    'instructor@learnify.com',
    'INSTRUCTOR',
    '$2a$12$2cvOxFV/8EtbsCw4EJ4OTelVmN4itVeYn0tfllDqn4fBqg4AJdl2u', -- Password: S123456@
    'Instructor User',
    '0987654321',
    NOW(),
    NOW()
) ON CONFLICT (email) DO NOTHING;

-- Assign INSTRUCTOR role to the instructor user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, role r
WHERE u.email = 'instructor@learnify.com' AND r.role_name = 'INSTRUCTOR'
ON CONFLICT DO NOTHING;
