--liquibase formatted sql
--changeset vholovetskyi:3

INSERT INTO customer(first_name, last_name, email, phone, street, city, zip_code)
VALUES
    ('Volodymyr', 'Holovetskyi', 'vholovetskyi@gmail.com', '0965643522', 'Ruska 1', 'Ternopil', '46-001'),
    ('Shawn', 'Dawson', 'Vova98313@gmail.com', '0987643272', 'Ruska 1', 'Odesa', '48-001'),
    ('Brandy', 'Zapata', 'bzapatagmailcom', '0975993222', 'Ruska 1', 'Kyiv', '50-001'),
    ('Ira', 'Moreau', 'imoreau@gmail.com', '0965643272', 'Ruska 1', 'Kharkiv', '44-001'),
    ('Abbey', 'Painter', 'apainter@gmail.com', '0962343277', 'Ruska 1', 'Lviv', '45-001');
