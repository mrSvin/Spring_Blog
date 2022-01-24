-- INSERT INTO `skillbox_blog`.`users` (`code`, `email`, `is_moderator`, `name`, `password`, `photo`, `reg_time`) VALUES ('1111', 'aaa@mail.ru', '1', 'oksana', '123', 'https://go.skillbox.ru/media/user/photo/110dcfdb-a407-42e7-b2ae-b5dff52ed400.jpg', '14.09.21');
-- INSERT INTO `skillbox_blog`.`users` (`code`, `email`, `is_moderator`, `name`, `password`, `photo`, `reg_time`) VALUES ('222', 'bbb@mail.ru', '1', 'artem', '123', 'https://go.skillbox.ru/media/users/39444ceb-1136-4d68-acfb-2b005fdc9ad3/ava.png', '12.01.22');
-- INSERT INTO `skillbox_blog`.`users` (`code`, `email`, `is_moderator`, `name`, `password`, `photo`, `reg_time`) VALUES ('3333', 'ccc@mail.ru', '0', 'kesha', '123', 'http://s1.iconbird.com/ico/0612/practika/w256h2561339698323user.png', '12.01.22');
-- INSERT INTO `skillbox_blog`.`users` (`code`, `email`, `is_moderator`, `name`, `password`, `photo`, `reg_time`) VALUES ('4444', 'ddd@mail.ru', '0', 'petya', '123', 'http://s1.iconbird.com/ico/0612/practika/w256h2561339698323user.png', '12.01.22');
-- INSERT INTO `skillbox_blog`.`users` (`code`, `email`, `is_moderator`, `name`, `password`, `photo`, `reg_time`) VALUES ('5555', 'eee@mail.ru', '0', 'valya', '123', 'http://s1.iconbird.com/ico/0612/practika/w256h2561339698323user.png', '12.01.22');

-- INSERT INTO `skillbox_blog`.`tags` (`name`) VALUES ('fun');
-- INSERT INTO `skillbox_blog`.`tags` (`name`) VALUES ('trash');
-- INSERT INTO `skillbox_blog`.`tags` (`name`) VALUES ('news');
-- INSERT INTO `skillbox_blog`.`tags` (`name`) VALUES ('work');

-- INSERT INTO `skillbox_blog`.`tag2post` (`post_id`, `tag_id`) VALUES ('1', '1');
-- INSERT INTO `skillbox_blog`.`tag2post` (`post_id`, `tag_id`) VALUES ('2', '2');
-- INSERT INTO `skillbox_blog`.`tag2post` (`post_id`, `tag_id`) VALUES ('3', '3');
-- INSERT INTO `skillbox_blog`.`tag2post` (`post_id`, `tag_id`) VALUES ('4', '4');
-- INSERT INTO `skillbox_blog`.`tag2post` (`post_id`, `tag_id`) VALUES ('5', '5');

-- INSERT INTO `skillbox_blog`.`posts` (`is_active`, `moderation_status`, `moderator_id`, `text`, `time`, `title`, `view_count`, `user_id`) VALUES ('1', 'NEW', '1', 'Hello World!', '14.09.21', 'Test', '1', '1');
-- INSERT INTO `skillbox_blog`.`posts` (`is_active`, `moderation_status`, `moderator_id`, `text`, `time`, `title`, `view_count`, `user_id`) VALUES ('0', 'ACCEPTED', '2', 'Test 2!', '12.01.22', 'Test 2', '2', '2');
-- INSERT INTO `skillbox_blog`.`posts` (`is_active`, `moderation_status`, `moderator_id`, `text`, `time`, `title`, `view_count`, `user_id`) VALUES ('0', 'ACCEPTED', '2', 'Test 3!', '12.01.22', 'Test 3', '10', '3');
-- INSERT INTO `skillbox_blog`.`posts` (`is_active`, `moderation_status`, `moderator_id`, `text`, `time`, `title`, `view_count`, `user_id`) VALUES ('0', 'DECLINED', '2', 'Test 4!', '12.01.22', 'Test 4', '321', '4');
-- INSERT INTO `skillbox_blog`.`posts` (`is_active`, `moderation_status`, `moderator_id`, `text`, `time`, `title`, `view_count`, `user_id`) VALUES ('0', 'ACCEPTED', '2', 'Test 5!', '12.01.22', 'Test 5', '9', '5');

-- INSERT INTO `skillbox_blog`.`post_votes` (`time`, `value`, `post_id`, `user_id`) VALUES ('14.09.21', '1', '1', '1');
-- INSERT INTO `skillbox_blog`.`post_votes` (`time`, `value`, `post_id`, `user_id`) VALUES ('12.01.22', '24', '2', '2');
-- INSERT INTO `skillbox_blog`.`post_votes` (`time`, `value`, `post_id`, `user_id`) VALUES ('12.01.22', '34', '3', '4');
-- INSERT INTO `skillbox_blog`.`post_votes` (`time`, `value`, `post_id`, `user_id`) VALUES ('12.01.22', '45', '4', '4');
-- INSERT INTO `skillbox_blog`.`post_votes` (`time`, `value`, `post_id`, `user_id`) VALUES ('12.01.22', '7', '5', '4');

-- INSERT INTO `skillbox_blog`.`post_comments` (`parent_id`, `text`, `time`, `user_id`, `post_id`) VALUES ('1', 'test comment!', '14.09.21', '1', '1');
-- INSERT INTO `skillbox_blog`.`post_comments` (`parent_id`, `text`, `time`, `user_id`, `post_id`) VALUES ('2', 'test comment2!', '12.01.22', '3', '2');
-- INSERT INTO `skillbox_blog`.`post_comments` (`parent_id`, `text`, `time`, `user_id`, `post_id`) VALUES ('2', 'test comment3!', '12.01.22', '3', '3');
-- INSERT INTO `skillbox_blog`.`post_comments` (`parent_id`, `text`, `time`, `user_id`, `post_id`) VALUES ('2', 'test comment4!', '12.01.22', '3', '4');
-- INSERT INTO `skillbox_blog`.`post_comments` (`parent_id`, `text`, `time`, `user_id`, `post_id`) VALUES ('2', 'test comment5!', '12.01.22', '3', '4');

-- INSERT INTO `skillbox_blog`.`global_settings` (`code`, `name`, `value`) VALUES ('1111', 'test', '1');
-- INSERT INTO `skillbox_blog`.`global_settings` (`code`, `name`, `value`) VALUES ('2222', 'test2', '2');
-- INSERT INTO `skillbox_blog`.`global_settings` (`code`, `name`, `value`) VALUES ('3333', 'test3', '3');
-- INSERT INTO `skillbox_blog`.`global_settings` (`code`, `name`, `value`) VALUES ('4444', 'test4', '4');
-- INSERT INTO `skillbox_blog`.`global_settings` (`code`, `name`, `value`) VALUES ('5555', 'test5', '5');

-- INSERT INTO `skillbox_blog`.`captcha_codes` (`code`, `secret_code`, `time`) VALUES ('321', '1111', '2014.09.21');
-- INSERT INTO `skillbox_blog`.`captcha_codes` (`code`, `secret_code`, `time`) VALUES ('222', '2222', '2012.01.22');
-- INSERT INTO `skillbox_blog`.`captcha_codes` (`code`, `secret_code`, `time`) VALUES ('333', '3333', '2012.01.22');
-- INSERT INTO `skillbox_blog`.`captcha_codes` (`code`, `secret_code`, `time`) VALUES ('444', '4444', '2012.01.22');
-- INSERT INTO `skillbox_blog`.`captcha_codes` (`code`, `secret_code`, `time`) VALUES ('555', '5555', '2012.01.22');
