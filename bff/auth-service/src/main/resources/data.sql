INSERT INTO users (username, password)
values ('admin', '$2y$10$7FecnGqGcjaSXjH6UCx/le8SquD9ROO7gi6ZtIp7xRyNXq71mnqUm');

INSERT INTO user_authorities (user_id, authority)
values (1, 'admin');

INSERT INTO users (username, password)
values ('tester', '$2y$10$7FecnGqGcjaSXjH6UCx/le8SquD9ROO7gi6ZtIp7xRyNXq71mnqUm');

INSERT INTO user_authorities (user_id, authority)
values (2, 'admin');