CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    channel VARCHAR(20) NOT NULL,
    recipient VARCHAR(40) NOT NULL,
    template VARCHAR(50) NOT NULL,
    variables JSONB NOT NULL,
    status_msg VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE telemetry (
    id SERIAL PRIMARY KEY,
    truckId VARCHAR(50) NOT NULL,
    temperature NUMERIC NOT NULL,
    checkedAt TIMESTAMP NOT NULL
);