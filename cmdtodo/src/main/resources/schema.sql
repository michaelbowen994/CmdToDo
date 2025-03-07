DROP TABLE IF EXISTS Todo;

CREATE TABLE IF NOT EXISTS Todo (
    id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    created_on timestamp NOT NULL,
    last_updated timestamp NOT NULL,
    due_date timestamp,
    description VARCHAR(255),
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    updates VARCHAR(255)
);

-- CREATE TABLE IF NOT EXISTS updates (
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     update_text VARCHAR(512) NOT NULL,
-- )

-- CREATE TABLE IF NOT EXISTS users (
--     id INT PRIMARY KEY NOT NULL,
--     first_name VARCHAR(255) NOT NULL,
--     last_name VARCHAR(255) NOT NULL,
--     password VARCHAR(255) NOT NULL,
--     email VARCHAR(255) NOT NULL,
--     todo_ids VARCHAR(255) NOT NULL
-- )