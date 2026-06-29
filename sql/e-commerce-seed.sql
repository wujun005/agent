USE `e-commerce`;

INSERT INTO `user` (`id`, `username`, `password`, `nickname`, `phone`, `email`, `status`)
VALUES
  (1, 'zhangsan', '123456', '张三', '13800000001', 'zhangsan@example.com', 1),
  (2, 'lisi', '123456', '李四', '13800000002', 'lisi@example.com', 1),
  (3, 'wangwu', '123456', '王五', '13800000003', 'wangwu@example.com', 1),
  (4, 'zhaoliu', '123456', '赵六', '13800000004', 'zhaoliu@example.com', 1),
  (5, 'sunqi', '123456', '孙七', '13800000005', 'sunqi@example.com', 0)
ON DUPLICATE KEY UPDATE
  `username` = VALUES(`username`),
  `password` = VALUES(`password`),
  `nickname` = VALUES(`nickname`),
  `phone` = VALUES(`phone`),
  `email` = VALUES(`email`),
  `status` = VALUES(`status`);

INSERT INTO `category` (`id`, `name`)
VALUES
  (1, '手机数码'),
  (2, '电脑办公'),
  (3, '智能配件'),
  (4, '办公外设'),
  (5, '存储设备')
ON DUPLICATE KEY UPDATE
  `name` = VALUES(`name`);

INSERT INTO `product` (`id`, `product_name`, `description`, `category_id`, `price`, `stock`, `status`)
VALUES
  (1, 'iPhone 15 128G', '苹果智能手机，黑色，国行版本', 1, 4999.00, 36, 1),
  (2, '华为 MateBook 14', '14 英寸轻薄笔记本，适合办公和学习', 2, 6299.00, 18, 1),
  (3, '小米手环 8', '运动健康监测手环，支持心率和睡眠记录', 3, 249.00, 120, 1),
  (4, '罗技 MX Master 3S', '无线办公鼠标，静音按键', 4, 699.00, 42, 1),
  (5, '机械键盘 K87', '87 键机械键盘，茶轴，白色背光', 4, 329.00, 55, 1),
  (6, 'AirPods Pro 2', '主动降噪无线耳机，Type-C 充电盒', 1, 1899.00, 27, 1),
  (7, '显示器 27 英寸 2K', '27 英寸 IPS 显示器，分辨率 2560x1440', 2, 1399.00, 23, 1),
  (8, '移动硬盘 1TB', '高速便携固态移动硬盘，USB-C 接口', 5, 459.00, 64, 1)
ON DUPLICATE KEY UPDATE
  `product_name` = VALUES(`product_name`),
  `description` = VALUES(`description`),
  `category_id` = VALUES(`category_id`),
  `price` = VALUES(`price`),
  `stock` = VALUES(`stock`),
  `status` = VALUES(`status`);

INSERT INTO `sensitive_word` (`id`, `word`, `category`, `replace_text`, `status`, `remark`)
VALUES
  (1, '刷单', '交易风险', '**', 1, '涉及虚假交易行为'),
  (2, '假货', '商品违规', '**', 1, '涉及商品质量和售假风险'),
  (3, '返现私聊', '导流违规', '**', 1, '引导用户脱离平台交易'),
  (4, '外挂', '平台安全', '**', 1, '涉及作弊或非法工具'),
  (5, '银行卡代付', '资金风险', '**', 1, '存在异常支付和洗钱风险')
ON DUPLICATE KEY UPDATE
  `word` = VALUES(`word`),
  `category` = VALUES(`category`),
  `replace_text` = VALUES(`replace_text`),
  `status` = VALUES(`status`),
  `remark` = VALUES(`remark`);

INSERT INTO `order_info` (`id`, `order_no`, `user_id`, `product_id`, `quantity`, `total_amount`, `status`)
VALUES
  (1, 'EC202605200001', 1, 1, 1, 4999.00, 'PAID'),
  (2, 'EC202605200002', 1, 3, 2, 498.00, 'COMPLETED'),
  (3, 'EC202605200003', 2, 2, 1, 6299.00, 'SHIPPED'),
  (4, 'EC202605200004', 2, 4, 1, 699.00, 'PENDING'),
  (5, 'EC202605200005', 3, 6, 1, 1899.00, 'PAID'),
  (6, 'EC202605200006', 3, 8, 2, 918.00, 'COMPLETED'),
  (7, 'EC202605200007', 4, 7, 1, 1399.00, 'CANCELLED'),
  (8, 'EC202605200008', 4, 5, 1, 329.00, 'SHIPPED'),
  (9, 'EC202605200009', 5, 3, 1, 249.00, 'PENDING')
ON DUPLICATE KEY UPDATE
  `order_no` = VALUES(`order_no`),
  `user_id` = VALUES(`user_id`),
  `product_id` = VALUES(`product_id`),
  `quantity` = VALUES(`quantity`),
  `total_amount` = VALUES(`total_amount`),
  `status` = VALUES(`status`);
