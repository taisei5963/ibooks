INSERT INTO `privilege` (`name`, `description`, `created_at`, `updated_at`, `created_id`, `ver`) VALUES
('システム管理者', '管理者権限', now(), now(), 'system', 1),
('レビュワー', 'レビュワー権限', now(), now(), 'system', 1);

INSERT INTO `admin` (`login_id`, `password`, `name`, `privilege_id`, `account_status`, `created_at`, `updated_at`, `created_id`, `ver`) VALUES
('admin', '$2y$10$NBnNnZPrL6O9yXbL6tP/HebaLi7akEe7J8s5YQ0/sDPzeRK5VvKcK', 'システム管理者', 1, 'ACTIVE', now(), now(), 'SYSTEM', 1);

INSERT INTO `category` (`category_code`, `category_name`, `created_at`, `updated_at`, `created_id`, `ver`) VALUES
('10842937', 'ITの基礎・資格', now(), now(), 'system', 1),
('39281745', 'Webサイト作成', now(), now(), 'system', 1),
('82710496', 'プログラミング', now(), now(), 'system', 1),
('55102938', 'スマホアプリ開発', now(), now(), 'system', 1),
('29384751', 'データの保存と管理', now(), now(), 'system', 1),
('77492013', '開発を支える道具', now(), now(), 'system', 1),
('46281057', '仕事の進め方', now(), now(), 'system', 1),
('91028374', 'セキュリティ・法規', now(), now(), 'system', 1),
('62839410', 'AI・最新トレンド', now(), now(), 'system', 1),
('31928475', 'クラウド・インフラ', now(), now(), 'system', 1),
('84751029', '業界知識・キャリア', now(), now(), 'system', 1),
('50293847', 'その他・読み物', now(), now(), 'system', 1);