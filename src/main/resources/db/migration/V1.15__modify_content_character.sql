ALTER TABLE topic
    MODIFY COLUMN title varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT null;
ALTER TABLE topic
    MODIFY COLUMN content longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT null;
ALTER TABLE comment
    MODIFY COLUMN content longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT null;