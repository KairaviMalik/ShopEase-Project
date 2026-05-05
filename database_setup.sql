-- ═══════════════════════════════════════════════════
--   ShopEase Database Setup Script
--   Run this in MySQL Workbench or MySQL CLI first
-- ═══════════════════════════════════════════════════

-- 1. Create database
CREATE DATABASE IF NOT EXISTS shopease_db;
USE shopease_db;

-- ═══════════════════════════════════════════════════
-- 2. USERS TABLE (stores both Admin and Customer)
-- ═══════════════════════════════════════════════════
CREATE TABLE IF NOT EXISTS users (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100)  NOT NULL,
    email         VARCHAR(100)  NOT NULL UNIQUE,
    password      VARCHAR(100)  NOT NULL,
    phone         VARCHAR(15),
    city          VARCHAR(50),
    role          ENUM('Admin','Customer') DEFAULT 'Customer',
    risk_level    ENUM('low','medium','high') DEFAULT 'low',
    total_orders  INT  DEFAULT 0,
    total_returns INT  DEFAULT 0,
    blocked       BOOLEAN DEFAULT FALSE,
    joined_date   DATE
);

-- ═══════════════════════════════════════════════════
-- 3. PRODUCTS TABLE
-- ═══════════════════════════════════════════════════
CREATE TABLE IF NOT EXISTS products (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(150) NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    stock       INT DEFAULT 0,
    category    VARCHAR(50),
    description TEXT,
    rating      DECIMAL(2,1) DEFAULT 4.0
);

-- ═══════════════════════════════════════════════════
-- 4. ORDERS TABLE
-- ═══════════════════════════════════════════════════
CREATE TABLE IF NOT EXISTS orders (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    customer_id    INT NOT NULL,
    customer_name  VARCHAR(100),
    product_id     INT NOT NULL,
    product_name   VARCHAR(150),
    quantity       INT DEFAULT 1,
    total_amount   DECIMAL(10,2),
    payment_method VARCHAR(30),
    status         ENUM('Confirmed','Cancelled','Returned') DEFAULT 'Confirmed',
    order_date     DATE,
    FOREIGN KEY (customer_id) REFERENCES users(id),
    FOREIGN KEY (product_id)  REFERENCES products(id)
);

-- ═══════════════════════════════════════════════════
-- 5. RETURN REQUESTS TABLE
-- ═══════════════════════════════════════════════════
CREATE TABLE IF NOT EXISTS return_requests (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    order_id      INT NOT NULL,
    customer_id   INT NOT NULL,
    customer_name VARCHAR(100),
    product_name  VARCHAR(150),
    reason        VARCHAR(200),
    status        ENUM('Pending','Approved','Rejected') DEFAULT 'Pending',
    request_date  DATE,
    FOREIGN KEY (order_id)    REFERENCES orders(id),
    FOREIGN KEY (customer_id) REFERENCES users(id)
);

-- ═══════════════════════════════════════════════════
-- 6. SEED DATA — Demo users
-- ═══════════════════════════════════════════════════
INSERT INTO users (name, email, password, phone, city, role, risk_level, total_orders, total_returns, blocked, joined_date) VALUES
('Admin User',    'admin@shopease.com', 'admin123', '9999999999', 'Mumbai',    'Admin',    'low',    0,  0, FALSE, '2023-01-01'),
('Rahul Sharma',  'rahul@shopease.com', 'pass123',  '9876543210', 'Dehradun',  'Customer', 'low',    3,  1, FALSE, '2024-01-15'),
('Priya Patel',   'priya@shopease.com', 'pass123',  '9876540001', 'Pune',      'Customer', 'medium', 12, 4, FALSE, '2024-02-20'),
('Amit Singh',    'amit@shopease.com',  'pass123',  '9876540002', 'Delhi',     'Customer', 'high',   8,  6, FALSE, '2024-03-10'),
('Kavya Reddy',   'kavya@shopease.com', 'pass123',  '9876540003', 'Hyderabad', 'Customer', 'high',   15, 8, TRUE,  '2024-01-28');

-- ═══════════════════════════════════════════════════
-- 7. SEED DATA — Products
-- ═══════════════════════════════════════════════════
INSERT INTO products (name, price, stock, category, description, rating) VALUES
-- Electronics
('Samsung Galaxy S23',      79999, 15, 'Electronics', 'Snapdragon 8 Gen 2, 50MP camera, Dynamic AMOLED', 4.6),
('Apple MacBook Air M2',    99999,  8, 'Electronics', 'M2 chip, 13.6 Liquid Retina, 18-hour battery',    4.8),
('Sony WH-1000XM5',         24999, 30, 'Electronics', 'Industry-leading ANC, 30-hour battery',           4.7),
('OnePlus Nord CE 3',       26999, 22, 'Electronics', 'AMOLED, 50MP, 80W SuperVOOC charging',            4.4),
('Mi Smart TV 43 inch',     28999, 12, 'Electronics', '4K UHD Android TV, Dolby Vision',                 4.3),
('Gaming Keyboard RGB',      3999, 45, 'Electronics', 'Mechanical, blue switches, anti-ghosting',        4.5),
-- Clothing
('Mens Casual T-Shirt',       599, 50, 'Clothing',    '100% cotton, relaxed fit',                        4.3),
('Womens Designer Kurti',    1299, 40, 'Clothing',    'Embroidered ethnic wear, cotton blend',            4.5),
('Mens Formal Shirt',         999, 60, 'Clothing',    'Slim fit, wrinkle-resistant',                     4.2),
('Running Shoes Men',        2499, 35, 'Clothing',    'Lightweight mesh, extra cushioning',               4.6),
('Winter Jacket Unisex',     2999, 25, 'Clothing',    'Waterproof, fleece-lined',                        4.4),
-- Books
('Java OOP Mastery',          449,100, 'Books',       'Complete OOP in Java, real-world examples',       4.7),
('Clean Code',                599, 75, 'Books',       'Write clean, maintainable code professionally',   4.8),
('The Psychology of Money',   349,120, 'Books',       'Timeless lessons on wealth and happiness',        4.9),
('Atomic Habits',             399, 90, 'Books',       'Build good habits, break bad ones',               4.8),
-- Home
('Air Fryer 4L',             3999, 28, 'Home',        'Healthy oil-free cooking, 8 preset modes',        4.5),
('Robot Vacuum Cleaner',    12999, 14, 'Home',        'Smart mapping, auto-return',                      4.4),
('LED Desk Lamp',             799, 80, 'Home',        '5 brightness modes, USB charging port',           4.4),
-- Sports
('Yoga Mat Extra Thick',     1299, 60, 'Sports',      '6mm non-slip TPE mat with carry strap',           4.6),
('Dumbbells Set 5-25kg',     4999, 18, 'Sports',      'Rubber-coated hex dumbbells, 5 pairs',            4.7),
('Cricket Bat Kashmir',      1799, 40, 'Sports',      'Kashmir willow, professional grade',              4.5),
-- Toys
('LEGO Creator City Set',    2999, 22, 'Toys',        '580-piece city modular set',                      4.8),
('Remote Control Car',       1499, 35, 'Toys',        '1:18 scale, 2.4GHz, 25 km/h',                    4.4);

-- ═══════════════════════════════════════════════════
-- Verify setup
-- ═══════════════════════════════════════════════════
SELECT 'Setup complete!' AS status;
SELECT COUNT(*) AS total_users     FROM users;
SELECT COUNT(*) AS total_products  FROM products;
