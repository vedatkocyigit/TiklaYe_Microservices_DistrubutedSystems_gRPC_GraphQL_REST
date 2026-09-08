CREATE TABLE user_profile (
                              id UUID PRIMARY KEY,
                              user_id UUID NOT NULL UNIQUE,
                              first_name VARCHAR(100),
                              last_name VARCHAR(100),
                              phone_number VARCHAR(20),
                              avatar_url VARCHAR(500),
                              created_at TIMESTAMP NOT NULL DEFAULT now(),
                              updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_user_profile_user_id ON user_profile (user_id);

CREATE TABLE address (
                         id UUID PRIMARY KEY,
                         user_id UUID NOT NULL,
                         label VARCHAR(50) NOT NULL,
                         city VARCHAR(100) NOT NULL,
                         district VARCHAR(100),
                         full_address VARCHAR(500) NOT NULL,
                         latitude DECIMAL(9,6),
                         longitude DECIMAL(9,6),
                         is_default BOOLEAN NOT NULL DEFAULT false,
                         created_at TIMESTAMP NOT NULL DEFAULT now(),
                         updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_address_user_id ON address (user_id);