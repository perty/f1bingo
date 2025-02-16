update fan set roles = 'USER' where roles is null;
ALTER TABLE fan
    ALTER COLUMN roles SET NOT NULL,
    ALTER COLUMN roles SET DEFAULT 'USER';
