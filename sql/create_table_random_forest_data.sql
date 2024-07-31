CREATE TABLE `random_forest_data` (
                                      `id` INT NOT NULL AUTO_INCREMENT,
                                      `tree_index` INT NOT NULL,
                                      `features` TEXT NOT NULL,
                                      `tree` TEXT NOT NULL,
                                      `user_id` INT NULL, -- user_id 可以为 NULL
                                      `file_id` VARCHAR(255),
                                      `create_time` DATETIME NULL,
                                      `update_time` DATETIME NULL,
                                      PRIMARY KEY (`id`),
    -- 当 user 表的记录被删除时，将 user_id 设置为 NULL
                                      CONSTRAINT `fk_random_forest_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
)