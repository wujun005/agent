CREATE TABLE IF NOT EXISTS `category` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_category_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `product` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `product_name` VARCHAR(100) NOT NULL,
  `description` TEXT,
  `category_id` BIGINT DEFAULT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `stock` INT NOT NULL DEFAULT 0,
  `status` TINYINT NOT NULL DEFAULT 1,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `idx_product_category_id` (`category_id`),
  CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELETE FROM product WHERE product_name LIKE 'IT-P1-%';

INSERT INTO category (id, name)
VALUES
    (1, '手机数码'),
    (2, '电脑办公'),
    (3, '智能配件'),
    (4, '办公外设')
ON DUPLICATE KEY UPDATE
    name = VALUES(name);

INSERT INTO product
    (id, product_name, description, category_id, price, stock, status)
VALUES
    (900001, 'IT-P1-Keyboard', 'integration keyboard', 4, 329.00, 55, 1),
    (900002, 'IT-P1-Mouse', 'integration mouse', 4, 129.00, 80, 1);
