drop table ourusers;
ALTER TABLE fan
    ADD COLUMN password varchar(255);
ALTER TABLE fan
    ADD COLUMN roles varchar(255);
ALTER TABLE fan
    ADD CONSTRAINT fan_name_unique UNIQUE (name);

-- Create the admin user with the password 'admin' and the role 'ADMIN'
INSERT INTO fan (name, password, roles)
VALUES ('admin', '$2a$10$WUtB65K/5F5qifFDM929Cebz6akXnpm8GoiLpOyhTrvdE/Cv1Ubbq', 'ADMIN')
on conflict (name) do nothing;
