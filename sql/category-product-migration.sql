CREATE TABLE IF NOT EXISTS `category` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_category_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `category` (`id`, `name`)
VALUES
  (1, '手机数码'),
  (2, '电脑办公'),
  (3, '智能配件'),
  (4, '办公外设'),
  (5, '存储设备')
ON DUPLICATE KEY UPDATE
  `name` = VALUES(`name`);

SET @category_id_column_exists = (
  SELECT COUNT(*)
  FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'product'
    AND COLUMN_NAME = 'category_id'
);
SET @add_category_id_sql = IF(
  @category_id_column_exists = 0,
  'ALTER TABLE `product` ADD COLUMN `category_id` BIGINT DEFAULT NULL AFTER `description`',
  'SELECT 1'
);
PREPARE add_category_id_stmt FROM @add_category_id_sql;
EXECUTE add_category_id_stmt;
DEALLOCATE PREPARE add_category_id_stmt;

SET @category_index_exists = (
  SELECT COUNT(*)
  FROM information_schema.STATISTICS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'product'
    AND INDEX_NAME = 'idx_product_category_id'
);
SET @add_category_index_sql = IF(
  @category_index_exists = 0,
  'ALTER TABLE `product` ADD KEY `idx_product_category_id` (`category_id`)',
  'SELECT 1'
);
PREPARE add_category_index_stmt FROM @add_category_index_sql;
EXECUTE add_category_index_stmt;
DEALLOCATE PREPARE add_category_index_stmt;

SET @category_fk_exists = (
  SELECT COUNT(*)
  FROM information_schema.TABLE_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA = DATABASE()
    AND TABLE_NAME = 'product'
    AND CONSTRAINT_NAME = 'fk_product_category'
);
SET @add_category_fk_sql = IF(
  @category_fk_exists = 0,
  'ALTER TABLE `product` ADD CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL',
  'SELECT 1'
);
PREPARE add_category_fk_stmt FROM @add_category_fk_sql;
EXECUTE add_category_fk_stmt;
DEALLOCATE PREPARE add_category_fk_stmt;

UPDATE `product`
SET `category_id` = CASE
  WHEN `product_name` LIKE '%iPhone%' OR `product_name` LIKE '%AirPods%' THEN 1
  WHEN `product_name` LIKE '%MateBook%' OR `product_name` LIKE '%显示器%' THEN 2
  WHEN `product_name` LIKE '%手环%' THEN 3
  WHEN `product_name` LIKE '%鼠标%' OR `product_name` LIKE '%键盘%' THEN 4
  WHEN `product_name` LIKE '%硬盘%' THEN 5
  ELSE 1
END
WHERE `category_id` IS NULL;
