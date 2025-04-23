CREATE TABLE `Category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `status` TINYINT(1) DEFAULT 1,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `Unit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `status` TINYINT(1) DEFAULT 1,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `Product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100),
  `code` VARCHAR(50),
  `status` TINYINT(1) DEFAULT 1,
  `is_service_item` TINYINT(1) DEFAULT 0,
  `is_purchasable` TINYINT(1) DEFAULT 1,
  `is_sellable` TINYINT(1) DEFAULT 1,
  `cost_price` DECIMAL(12,2),
  `selling_price` DECIMAL(12,2),
  `mrp` DECIMAL(12,2),
  `category_id` INT,
  `unit_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `Category`(`id`),
  FOREIGN KEY (`unit_id`) REFERENCES `Unit`(`id`)
);

CREATE TABLE `User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(100) NOT NULL,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `gender` VARCHAR(10),
  `contact` VARCHAR(20),
  `status` TINYINT(1) DEFAULT 1,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

INSERT INTO `Category` (`name`, `status`) VALUES 
('Smartphones', 1),
('Laptops', 1),
('Accessories', 1),
('Tablets', 1),
('Smartwatches', 1);

INSERT INTO `Unit` (`name`, `status`) VALUES 
('Piece', 1),
('Box', 1),
('Packet', 1),
('Set', 1),
('Bundle', 1);

INSERT INTO `Product` 
(`name`, `code`, `status`, `is_service_item`, `is_purchasable`, `is_sellable`, `cost_price`, `selling_price`, `mrp`, `category_id`, `unit_id`) 
VALUES
('iPhone 14 Pro', 'IP14P', 1, 0, 1, 1, 950.00, 1099.00, 1199.00, 1, 1),
('Samsung Galaxy S23', 'SGS23', 1, 0, 1, 1, 800.00, 999.00, 1050.00, 1, 1),
('MacBook Air M2', 'MBAIR2', 1, 0, 1, 1, 1000.00, 1199.00, 1299.00, 2, 1),
('Dell XPS 13', 'DX13', 1, 0, 1, 1, 950.00, 1150.00, 1250.00, 2, 2),
('Logitech Mouse', 'LGM01', 1, 0, 1, 1, 15.00, 25.00, 30.00, 3, 3),
('iPad Pro', 'IPDP', 1, 0, 1, 1, 700.00, 899.00, 999.00, 4, 1),
('Apple Watch SE', 'AWSE', 1, 0, 1, 1, 200.00, 249.00, 299.00, 5, 1),
('Lenovo Tab M10', 'LTM10', 1, 0, 1, 1, 120.00, 160.00, 180.00, 4, 1),
('Galaxy Watch 5', 'GW5', 1, 0, 1, 1, 180.00, 229.00, 250.00, 5, 1),
('Asus Vivobook', 'AVBK', 1, 0, 1, 1, 600.00, 750.00, 800.00, 2, 2),
('HP Pavilion', 'HPPAV', 1, 0, 1, 1, 650.00, 799.00, 899.00, 2, 2),
('iPhone SE', 'IPSE', 1, 0, 1, 1, 350.00, 429.00, 499.00, 1, 1),
('Samsung Tab S6', 'STS6', 1, 0, 1, 1, 400.00, 499.00, 599.00, 4, 1),
('Noise Smartwatch', 'NSW01', 1, 0, 1, 1, 30.00, 49.00, 60.00, 5, 1),
('Boat Earbuds', 'BE01', 1, 0, 1, 1, 20.00, 29.00, 35.00, 3, 3),
('Dell Mouse', 'DM01', 1, 0, 1, 1, 10.00, 19.00, 25.00, 3, 3),
('iPad Mini', 'IPDM', 1, 0, 1, 1, 400.00, 499.00, 599.00, 4, 1),
('MacBook Pro', 'MBP', 1, 0, 1, 1, 1200.00, 1499.00, 1599.00, 2, 2),
('Lenovo Legion', 'LLGN', 1, 0, 1, 1, 1100.00, 1299.00, 1399.00, 2, 2),
('Samsung Galaxy Buds', 'SGB', 1, 0, 1, 1, 50.00, 69.00, 79.00, 3, 3),
('iPhone 13 Mini', 'IP13M', 1, 0, 1, 1, 700.00, 799.00, 899.00, 1, 1),
('Realme Watch', 'RW01', 1, 0, 1, 1, 40.00, 59.00, 69.00, 5, 1),
('Amazon Fire HD', 'AFHD', 1, 0, 1, 1, 80.00, 109.00, 120.00, 4, 4),
('Sony Headphones', 'SH01', 1, 0, 1, 1, 60.00, 79.00, 89.00, 3, 3),
('HP Envy', 'HPE', 1, 0, 1, 1, 700.00, 899.00, 999.00, 2, 2);

INSERT INTO `User` (full_name, username, email, password, gender, contact, status) VALUES
('Admin User', 'admin', 'admin@example.com', 'Admin@123', 'Male', '9800000001', 1),
('Sandip Shakya', 'sandip', 'sandip@example.com', 'User@123', 'Male', '9800000002', 1),
('Ram Bahadur', 'ram_bahadur', 'ram@example.com', 'User@123', 'Male', '9800000003', 1);

