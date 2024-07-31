CREATE TABLE `linear_regression_data` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `file_id` VARCHAR(255) NULL,
  `epoch` INT NULL,
  `weights` TEXT NULL, -- 假设 weights 是一个 JSON 字符串或以某种方式序列化的字符串
  `bias` TEXT NULL, -- 假设 bias 是一个 JSON 字符串或以某种方式序列化的字符串
  `loss` DOUBLE PRECISION NULL,
    `create_time` DATETIME NULL, -- 添加字段时，字段名和类型之间需要逗号，null前不要空格
  `update_time` DATETIME NULL, -- 同上
  PRIMARY KEY (`id`),
    -- 当 user 表的记录被删除时，将 user_id 设置为 NULL
  CONSTRAINT `fk_linear_regression_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) 