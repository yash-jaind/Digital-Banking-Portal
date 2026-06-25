-- Digital Banking Portal - Database Schema
-- Run this in MySQL before starting the application

CREATE DATABASE IF NOT EXISTS digital_banking;
USE digital_banking;

-- Users table (customers)
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Accounts table
CREATE TABLE accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_type ENUM('SAVINGS', 'CURRENT') DEFAULT 'SAVINGS',
    balance DECIMAL(15, 2) DEFAULT 0.00,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Transactions table
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    from_account VARCHAR(20),
    to_account VARCHAR(20),
    transaction_type ENUM('CREDIT', 'DEBIT', 'TRANSFER') NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    description VARCHAR(255),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Beneficiaries table
CREATE TABLE beneficiaries (
    beneficiary_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    beneficiary_name VARCHAR(100) NOT NULL,
    account_number VARCHAR(20) NOT NULL,
    bank_name VARCHAR(100) NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Sample data
INSERT INTO users (username, password, full_name, email, phone) VALUES
('john_doe', 'john123', 'John Doe', 'john@example.com', '9876543210'),
('jane_smith', 'jane123', 'Jane Smith', 'jane@example.com', '9876543211');

INSERT INTO accounts (user_id, account_number, account_type, balance) VALUES
(1, 'ACC1000000001', 'SAVINGS', 50000.00),
(2, 'ACC1000000002', 'SAVINGS', 75000.00);

INSERT INTO transactions (from_account, to_account, transaction_type, amount, description) VALUES
(NULL, 'ACC1000000001', 'CREDIT', 50000.00, 'Initial Deposit'),
(NULL, 'ACC1000000002', 'CREDIT', 75000.00, 'Initial Deposit');
