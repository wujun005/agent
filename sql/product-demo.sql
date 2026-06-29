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

INSERT INTO `category` (`id`, `name`)
VALUES
  (1, '手机数码'),
  (2, '电脑办公'),
  (3, '智能配件')
ON DUPLICATE KEY UPDATE
  `name` = VALUES(`name`);

INSERT INTO `product` (`product_name`, `description`, `category_id`, `price`, `stock`, `status`)
VALUES
  ('iPhone 15 128G', '苹果智能手机，黑色，国行版本', 1, 4999.00, 36, 1),
  ('华为 MateBook 14', '14 英寸轻薄笔记本，适合办公和学习', 2, 6299.00, 18, 1),
  ('小米手环 8', '运动健康监测手环，支持心率和睡眠记录', 3, 249.00, 120, 1);
