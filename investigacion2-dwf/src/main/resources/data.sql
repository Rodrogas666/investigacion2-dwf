-- Crear tabla 'orders'
CREATE TABLE orders (
    id IDENTITY PRIMARY KEY,          -- autoincremental en H2
    customer_name VARCHAR(100) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO orders (customer_name, total, status, created_at, updated_at) VALUES
('Alice', 100.00, 'NEW', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Bob', 75.50, 'NEW', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
