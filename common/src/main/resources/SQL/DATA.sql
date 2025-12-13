INSERT INTO `admin` (`login_id`, `password`, `name`, `privilege_id`, `account_status`, `created_at`, `updated_at`, `created_id`, `ver`) VALUES
('admin', '4194d1706ed1f408d5e02d672777019f4d5385c766a8c6ca8acba3167d36a7b9', 'システム管理者', 1, 'ACTIVE', now(), now(), 'SYSTEM', 1);

INSERT INTO `category` (`category_code`, `category_name`, `created_at`, `updated_at`, `created_id`, `ver`) VALUES
('38492015', 'WEBデザイン', now(), now(), 'SYSTEM', 1),
('59213084', 'データベース', now(), now(), 'SYSTEM', 1),
('84930217', 'フレームワーク', now(), now(), 'SYSTEM', 1),
('72819346', 'コマンド', now(), now(), 'SYSTEM', 1),
('10928374', '資格', now(), now(), 'SYSTEM', 1),
('93284751', 'マネジメント', now(), now(), 'SYSTEM', 1),
('21839475', 'Android', now(), now(), 'SYSTEM', 1),
('48392016', 'iOS', now(), now(), 'SYSTEM', 1),
('70129483', 'プログラミングスキル', now(), now(), 'SYSTEM', 1),
('99999999', 'その他', now(), now(), 'SYSTEM', 1);