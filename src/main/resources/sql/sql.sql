-- CREATE SCHEMA `skillbox_blog` ;

-- CREATE TABLE `skillbox_blog`.`users` (
--   `id` INT NOT NULL AUTO_INCREMENT ,
--   `is_moderator` TINYINT(255) NOT NULL,
--   `reg_time` DATETIME(6) NOT NULL,
--   `name` VARCHAR(255) NOT NULL,
--   `email` VARCHAR(255) NOT NULL,
--   `password` VARCHAR(255) NOT NULL,
--   `code` VARCHAR(255) NULL,
--   `photo` TEXT(255) NULL,
--   PRIMARY KEY (`id`));

-- CREATE TABLE `skillbox_blog`.`posts` (
--   `id` INT NOT NULL AUTO_INCREMENT,
--   `is_active` TINYINT(1) NOT NULL,
--   `moderation_status` ENUM("NEW", "ACCEPTED", "DECLINED") NOT NULL,
--   `moderator_id` INT NULL,
--   `user_id` INT NOT NULL,
--   `time` DATETIME NOT NULL,
--   `title` VARCHAR(255) NOT NULL,
--   `text` TEXT(255) NOT NULL,
--   `view_count` INT NOT NULL,
--   PRIMARY KEY (`id`));

-- CREATE TABLE `skillbox_blog`.`post_votes` (
--   `id` INT NOT NULL AUTO_INCREMENT,
--   `user_id` INT NOT NULL,
--   `post_id` INT NOT NULL,
--   `time` DATETIME NOT NULL,
--   `value` TINYINT(5) NOT NULL,
--   PRIMARY KEY (`id`));

-- CREATE TABLE `skillbox_blog`.`tags` (
--   `id` INT NOT NULL AUTO_INCREMENT,
--   `name` VARCHAR(255) NOT NULL,
--   PRIMARY KEY (`id`));

-- CREATE TABLE `skillbox_blog`.`tag2post` (
--   `id` INT NOT NULL AUTO_INCREMENT,
--   `post_id` INT NOT NULL,
--   `tag_id` INT NOT NULL,
--   PRIMARY KEY (`id`));

-- CREATE TABLE `skillbox_blog`.`post_comments` (
--   `id` INT NOT NULL AUTO_INCREMENT,
--   `parent_id` INT NULL,
--   `post_id` INT NOT NULL,
--   `user_id` INT NOT NULL,
--   `time` DATETIME NOT NULL,
--   `text` TEXT(255) NOT NULL,
--   PRIMARY KEY (`id`));

-- CREATE TABLE `skillbox_blog`.`captcha_codes` (
--   `id` INT NOT NULL AUTO_INCREMENT,
--   `time` DATETIME NOT NULL,
--   `code` TINYTEXT NOT NULL,
--   `secret_code` TINYTEXT NOT NULL,
--   PRIMARY KEY (`id`));

-- CREATE TABLE `skillbox_blog`.`global_settings` (
--   `id` INT NOT NULL AUTO_INCREMENT,
--   `code` VARCHAR(255) NOT NULL,
--   `name` VARCHAR(255) NOT NULL,
--   `value` VARCHAR(255) NOT NULL,
--   PRIMARY KEY (`id`));





