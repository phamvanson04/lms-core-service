-- Add address column to users table
ALTER TABLE users ADD COLUMN address VARCHAR(255);

CREATE INDEX idx_users_address ON users(address);
