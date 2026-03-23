-- Generate 30.000.000 student accounts with realistic data
-- WARNING: This will take several hours to execute

DO $$
DECLARE
    student_role_id UUID;
    counter INT := 0;
    batch_size INT := 10000;
    total_records INT := 3000000;
    user_ids UUID[];
BEGIN
    -- Get STUDENT role ID
    SELECT id INTO student_role_id FROM role WHERE role_name = 'STUDENT';
    
    -- Generate users in batches for better performance
    WHILE counter < total_records LOOP
        -- Insert users and collect their IDs
        WITH inserted AS (
            INSERT INTO users (id, email, username, password, full_name, phone, created_at, updated_at)
            SELECT 
                gen_random_uuid(),
                'student' || (counter + gs) || '@learnify.com',
                'student' || (counter + gs),
                '$2a$12$2cvOxFV/8EtbsCw4EJ4OTelVmN4itVeYn0tfllDqn4fBqg4AJdl2u', -- Password: S123456@
                'Student User ' || (counter + gs),
                CASE 
                    WHEN (counter + gs) % 3 = 0 THEN NULL  -- 33% without phone
                    ELSE '09' || LPAD((((counter + gs)::BIGINT * 123456789) % 100000000)::TEXT, 8, '0')  -- Generate unique phone
                END,
                NOW() - (random() * INTERVAL '365 days'), -- Random created date within last year
                NOW()
            FROM generate_series(1, batch_size) AS gs
            RETURNING id
        )
        SELECT ARRAY_AGG(id) INTO user_ids FROM inserted;
        
        -- Assign STUDENT role to all users in this batch
        INSERT INTO user_roles (user_id, role_id)
        SELECT unnest(user_ids), student_role_id;
        
        counter := counter + batch_size;
        
        -- Progress logging every 100k records
        IF counter % 100000 = 0 THEN
            RAISE NOTICE 'Generated % records...', counter;
        END IF;
    END LOOP;
    
    RAISE NOTICE 'Successfully generated 1,000,000 student accounts';
END $$;
