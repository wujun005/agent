CREATE TABLE IF NOT EXISTS `tenant` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `tenant_code` VARCHAR(50) NOT NULL,
  `tenant_name` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(20) DEFAULT NULL,
  `email` VARCHAR(100) DEFAULT NULL,
  `valid_start_time` DATETIME DEFAULT NULL,
  `valid_end_time` DATETIME DEFAULT NULL,
  `remark` VARCHAR(500) DEFAULT NULL,
  `status` TINYINT NOT NULL DEFAULT 1,
  UNIQUE KEY `uk_tenant_code` (`tenant_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
