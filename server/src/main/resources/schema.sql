CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(512) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS item_requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description VARCHAR(512),
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    requestor_id BIGINT NOT NULL REFERENCES "users" (id),
    CONSTRAINT pk_item_request PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS items  (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(512),
    is_available BOOLEAN DEFAULT TRUE NOT NULL,
    owner_id BIGINT NOT NULL REFERENCES "users" (id),
    item_request_id BIGINT REFERENCES "item_requests" (id),
    CONSTRAINT pk_item PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status VARCHAR(50) DEFAULT 'WAITING' NOT NULL,
    item_id BIGINT NOT NULL REFERENCES "items" (id),
    booker_id BIGINT NOT NULL REFERENCES "users" (id),
    CONSTRAINT pk_booking PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS comments (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text VARCHAR(512),
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    item_id BIGINT NOT NULL REFERENCES "items" (id),
    author_id BIGINT NOT NULL REFERENCES "users" (id),
    CONSTRAINT pk_comment PRIMARY KEY (id)
);