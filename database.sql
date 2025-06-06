CREATE TABLE `category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `is_active` TINYINT(1) DEFAULT 1,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `unit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `is_active` TINYINT(1) DEFAULT 1,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100),
  `code` VARCHAR(50),
  `is_active` TINYINT(1) DEFAULT 1,
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
  FOREIGN KEY (`category_id`) REFERENCES `category`(`id`),
  FOREIGN KEY (`unit_id`) REFERENCES `unit`(`id`)
);

CREATE TABLE product_stock (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `quantity` DOUBLE,
    `cost_price` DOUBLE,
    `selling_price` DOUBLE,
    `mrp` DOUBLE,
    `product_id` INT,
  FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
);

CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(100) NOT NULL,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `gender` VARCHAR(10),
  `contact` VARCHAR(20),
  `image_url` VARCHAR(255),
  `is_active` TINYINT(1) DEFAULT 1,
  `account_non_locked` TINYINT(1) DEFAULT 1,
  `failed_attempt` INT DEFAULT 0,
  `lock_time` DATETIME DEFAULT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `party` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `registration_number` VARCHAR(100),
  `is_active` TINYINT(1) DEFAULT 1,
  `type` VARCHAR(50) NOT NULL,
  `contact` VARCHAR(20),
  `address` VARCHAR(255),
  `email` VARCHAR(100),
  `remarks` TEXT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `account_master` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `account_code` VARCHAR(50),
  `account_name` VARCHAR(100) NOT NULL,
  `account_type` VARCHAR(100) NOT NULL,
  `is_active` TINYINT(1) DEFAULT 1,
  `deletable` TINYINT(1) DEFAULT 1,
  `parent_account_name` VARCHAR(100),
  `parent_id` INT DEFAULT 0,
  `remarks` TEXT,
  `party_id` INT DEFAULT NULL UNIQUE,
  `party_type` VARCHAR(50),
   `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`party_id`) REFERENCES `party`(`id`)
);

CREATE TABLE `document_number` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `module` VARCHAR(50) NOT NULL,          
    `prefix` VARCHAR(20),                   
    `start_number` INT NOT NULL DEFAULT 1,    
    `end_number` INT NOT NULL DEFAULT 999999,  
    `length` INT NOT NULL DEFAULT 6,          
    `description` VARCHAR(255)              
);

CREATE TABLE `master_purchase_entry` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` VARCHAR(255),
  `system_entry_no` VARCHAR(25) UNIQUE NOT NULL,
  `bill_no` VARCHAR(255),
  `transaction_type` VARCHAR(255),
  `sub_total` DOUBLE DEFAULT 0 NOT NULL,
  `discount` DOUBLE DEFAULT 0 NOT NULL,
  `non_taxable_amount` DOUBLE DEFAULT 0 NOT NULL,
  `taxable_amount` DOUBLE DEFAULT 0 NOT NULL,
  `total_tax` DOUBLE DEFAULT 0 NOT NULL,
  `rounded` BOOLEAN DEFAULT FALSE,
  `rounding` DOUBLE DEFAULT 0 NOT NULL,
  `grand_total` DOUBLE DEFAULT 0 NOT NULL,
  `discount_type` VARCHAR(255),
  `remarks` TEXT,
  `party_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`party_id`) REFERENCES `party`(`id`),
  UNIQUE KEY `unique_party_bill_no` (`party_id`, `bill_no`)
);

CREATE TABLE `purchase_entry` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `master_purchase_entry_id` INT,
    `quantity` DOUBLE,
    `cost_price` DOUBLE,
    `selling_price` DOUBLE,
    `mrp` DOUBLE,
    `product_id` INT,
    FOREIGN KEY (`master_purchase_entry_id`) REFERENCES `master_purchase_entry`(`id`),
  FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
);

CREATE TABLE `master_sales_entry` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` VARCHAR(255),
  `system_entry_no` VARCHAR(25) UNIQUE NOT NULL,
  `transaction_type` VARCHAR(255),
  `sub_total` DOUBLE DEFAULT 0 NOT NULL,
  `discount` DOUBLE DEFAULT 0 NOT NULL,
  `non_taxable_amount` DOUBLE DEFAULT 0 NOT NULL,
  `taxable_amount` DOUBLE DEFAULT 0 NOT NULL,
  `total_tax` DOUBLE DEFAULT 0 NOT NULL,
  `rounded` BOOLEAN DEFAULT FALSE,
  `is_cancelled` BOOLEAN DEFAULT FALSE,
  `rounding` DOUBLE DEFAULT 0 NOT NULL,
  `grand_total` DOUBLE DEFAULT 0 NOT NULL,
  `discount_type` VARCHAR(255),
  `remarks` TEXT,
  `cancel_remarks` TEXT,
  `party_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`party_id`) REFERENCES `party`(`id`)
);

CREATE TABLE `sales_entry` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `master_sales_entry_id` INT,
    `quantity` DOUBLE,
    `cost_price` DOUBLE,
    `selling_price` DOUBLE,
    `mrp` DOUBLE,
    `product_id` INT,
    FOREIGN KEY (`master_sales_entry_id`) REFERENCES `master_sales_entry`(`id`),
  FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
);

INSERT INTO `category` (`name`, `is_active`) VALUES 
('Smartphones', 1),
('Laptops', 1),
('Accessories', 1),
('Tablets', 1),
('Smartwatches', 1);

INSERT INTO `unit` (`name`, `is_active`) VALUES 
('Piece', 1),
('Box', 1),
('Packet', 1),
('Set', 1),
('Bundle', 1);

INSERT INTO `product` 
(`name`, `code`, `is_active`, `is_service_item`, `is_purchasable`, `is_sellable`, `cost_price`, `selling_price`, `mrp`, `category_id`, `unit_id`) 
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

INSERT INTO `party` (`name`, `registration_number`, `is_active`, `type`, `contact`, `address`, `email`, `remarks`)
VALUES 
('Tech Distributors Inc.', 'REG12345', 1, 'Vendor', '9876543210', 'Kathmandu, Nepal', 'vendor1@techdist.com', 'Bulk electronics supplier'),
('Gadget Retailers', 'REG12346', 1, 'Customer', '9801234567', 'Lalitpur, Nepal', 'customer1@gadgetretail.com', 'Regular mobile retailer'),
('Digital Nepal', 'REG12347', 1, 'Vendor', '9811122233', 'Pokhara, Nepal', 'vendor2@digitalnepal.com', 'Laptop and accessory wholesaler'),
('Smart Solutions', 'REG12348', 1, 'Customer', '9808765432', 'Biratnagar, Nepal', 'customer2@smartsolutions.com', 'Corporate client'),
('Everest Traders', 'REG12349', 1, 'Vendor', '9822334455', 'Chitwan, Nepal', 'vendor3@everesttraders.com', 'Tablet and watch supplier'),
('Valley Mobiles', 'REG12350', 1, 'Customer', '9841122334', 'Bhaktapur, Nepal', 'customer3@valleymobiles.com', 'Mobile shop chain'),
('Quick Supplies', 'REG12351', 1, 'Vendor', '9855566778', 'Butwal, Nepal', 'vendor4@quicksupplies.com', 'General electronics vendor'),
('GreenTech Enterprises', 'REG12352', 1, 'Customer', '9809988776', 'Dharan, Nepal', 'customer4@greentech.com', 'Eco-tech solutions firm'),
('Ecom Vendor House', 'REG12353', 1, 'Vendor', '9866677885', 'Nepalgunj, Nepal', 'vendor5@ecomvendor.com', 'Online platform supplier'),
('City Electronics', 'REG12354', 1, 'Customer', '9812345678', 'Hetauda, Nepal', 'customer5@cityelectronics.com', 'Retail electronics chain');

INSERT INTO `account_master` (`id`, `account_code`, `account_name`, `account_type`, `is_active`, `deletable`, `parent_account_name`, `parent_id`, `remarks`, `party_id`) VALUES
(1, 'C-000', 'Cash In Hand', 'Cash & Cash Equivalents', 1, 0, NULL, 0, NULL, NULL),
(2, 'S-000', 'Sales', 'Direct Income', 1, 0, NULL, 0, NULL, NULL),
(3, 'C-001', 'Cash', 'Cash & Cash Equivalents', 1, 0, 'Cash In Hand', 1, NULL, NULL),
(4, 'P-001', 'Purchase', 'Cost of Goods Sold', 1, 0, NULL, 0, NULL, NULL),
(5, 'S-001', 'VAT Sales', 'Direct Income', 1, 0, 'Sales', 2, NULL, NULL),
(6, 'C-002', 'Petty Cash', 'Cash & Cash Equivalents', 1, 0, 'Cash In Hand', 1, NULL, NULL),
(7, 'VP-001', 'VAT Purchase', 'Cost of Goods Sold', 1, 0, 'Purchase', 4, NULL, NULL),
(8, 'E-002', 'Printing and Stationary', 'Administrative Expenses', 1, 0, NULL, 0, NULL, NULL),
(9, 'E-003', 'Fuel Expenses', 'Administrative Expenses', 1, 0, NULL, 0, NULL, NULL),
(10, 'VFP-001', 'VAT Free Purchase', 'Cost of Goods Sold', 1, 0, 'Purchase', 4, NULL, NULL),
(11, 'FA-001', 'Fixed Assets', 'Non-Current Assets', 1, 0, NULL, 0, NULL, NULL),
(12, NULL, 'Plant and Machinery', 'Non-Current Assets', 1, 0, 'Fixed Assets', 11, NULL, NULL),
(13, NULL, 'Debtors', 'Receivables', 1, 0, NULL, 0, NULL, NULL),
(14, 'TP-001', 'Tax Payable', 'Other Payables', 1, 0, NULL, 0, NULL, NULL),
(15, 'T-001', 'Tax', 'Other Payables', 1, 0, 'Tax Payable', 14, NULL, NULL),
(16, 'OE-001', 'Other Expenses', 'Administrative Expenses', 1, 0, NULL, 0, NULL, NULL),
(17, 'A-001', 'Adjustment', 'Administrative Expenses', 1, 0, 'Other Expenses', 16, NULL, NULL),
(18, 'TP-002', 'Trader Payable', 'Payables', 1, 0, NULL, 0, NULL, NULL),
(19, NULL, 'In. Direct', 'Indirect Income', 1, 0, NULL, 0, NULL, NULL),
(20, NULL, 'Interest', 'Indirect Income', 1, 0, 'In. Direct', 19, NULL, NULL),
(21, NULL, 'In. Expenses', 'Other Indirect Expenses', 1, 0, NULL, 0, NULL, NULL),
(22, NULL, 'Bank Charge', 'Other Indirect Expenses', 1, 0, 'In. Expenses', 21, NULL, NULL),
(23, NULL, 'Trade Receivables', 'Receivables', 1, 0, NULL, 0, NULL, NULL),
(24, NULL, 'VAT Free Sales', 'Direct Income', 1, 0, 'Sales', 2, NULL, NULL),
(25, NULL, 'Tech Distributors Inc.', 'Payables', 1, 1, 'Trader Payable', 18, 'Bulk electronics supplier', 1),
(26, NULL, 'Digital Nepal', 'Payables', 1, 1, 'Trader Payable', 18, 'Laptop and accessory wholesaler', 3),
(27, NULL, 'Everest Traders', 'Payables', 1, 1, 'Trader Payable', 18, 'Tablet and watch supplier', 5),
(28, NULL, 'Quick Supplies', 'Payables', 1, 1, 'Trader Payable', 18, 'General electronics vendor', 7),
(29, NULL, 'Ecom Vendor House', 'Payables', 1, 1, 'Trader Payable', 18, 'Online platform supplier', 9),
(30, NULL, 'Gadget Retailers', 'Receivables', 1, 1, 'Trade Receivables', 23, 'Regular mobile retailer', 2),
(31, NULL, 'Smart Solutions', 'Receivables', 1, 1, 'Trade Receivables', 23, 'Corporate client', 4),
(32, NULL, 'Valley Mobiles', 'Receivables', 1, 1, 'Trade Receivables', 23, 'Mobile shop chain', 6),
(33, NULL, 'GreenTech Enterprises', 'Receivables', 1, 1, 'Trade Receivables', 23, 'Eco-tech solutions firm', 8),
(34, NULL, 'City Electronics', 'Receivables', 1, 1, 'Trade Receivables', 23, 'Retail electronics chain', 10);

INSERT INTO `document_number` (`module`, `prefix`, `start_number`, `end_number`, `length`, `description`) VALUES 
('Purchase', 'PE-', 1, 999999, 6, 'Purchase Entry'),
('Sales', 'SI-', 1, 999999, 6, 'Sales Entry');
('Journal', 'J-', 1, 999999, 6, 'Journal Entry');





