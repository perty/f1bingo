-- Add column last_read to fan
ALTER TABLE fan ADD COLUMN last_read TIMESTAMP not null DEFAULT now();
