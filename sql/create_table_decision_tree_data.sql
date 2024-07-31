
CREATE TABLE `decision_tree_data` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT,
  `file_id` VARCHAR(255), -- 假设fileId是一个字符串，长度限制为255
   `data` TEXT NOT NULL, -- 长度可能很长，使用TEXT类型
  `create_time` DATETIME NULL, -- 添加字段时，字段名和类型之间需要逗号，null前不要空格
  `update_time` DATETIME NULL, -- 同上
  PRIMARY KEY (`id`),
-- 当 user 表的记录被删除时，将 user_id 设置为 NULL
  CONSTRAINT `fk_decision_tree_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL)
