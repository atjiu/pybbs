UPDATE `system_config`
SET `key` = 'upload_image_size_limit'
WHERE `id` = '20';

INSERT INTO `system_config` (`id`, `key`, `value`, `description`, `pid`, `type`, `option`, `reboot`)
VALUES (59, 'upload_video_size_limit', '20', '上传视频文件大小，单位MB，默认20MB', '25', 'number', NULL, '0');

