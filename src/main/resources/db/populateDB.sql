DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);


INSERT INTO meals(date_time, user_id, description, calories)
VALUES ('2020-01-30 8:00', 100000, 'Завтрак', 500),
    ('2020-01-30 12:00', 100000, 'Обед', 1000),
    ('2020-01-30 20:00', 100000, 'Ужин', 300),
    ('2020-01-31 8:00', 100000, 'Завтрак', 700),
    ('2020-01-31 12:00', 100000, 'Обед', 1000);


