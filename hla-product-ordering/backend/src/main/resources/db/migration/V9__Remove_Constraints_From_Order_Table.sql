-- This script removes the NOT NULL constraints from email_id and received fields

-- Remove NOT NULL constraint from email_id
ALTER TABLE order_entity
    ALTER COLUMN email_id DROP NOT NULL;

-- Remove NOT NULL constraint from received
ALTER TABLE order_entity
    ALTER COLUMN received DROP NOT NULL;