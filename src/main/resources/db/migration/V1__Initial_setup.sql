CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL ,
    password TEXT NOT NULL,
    email_id VARCHAR (200) NOT NULL ,
    client_secret TEXT NOT NULL,
    confirmed BOOLEAN DEFAULT FALSE NOT NULL,
    active BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP  NOT NULL
);
