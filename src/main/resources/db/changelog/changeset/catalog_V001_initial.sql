CREATE TABLE categories
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    parent_id  BIGINT REFERENCES categories (id) ON DELETE CASCADE,
    chat_id    BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


