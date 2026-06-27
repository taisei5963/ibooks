INSERT INTO `privilege` (`name`, `description`, `created_at`, `updated_at`, `created_id`, `ver`) VALUES
('システム管理者', '管理者権限', now(), now(), 'system', 1),
('レビュワー', 'レビュワー権限', now(), now(), 'system', 1);

INSERT INTO `admin` (`login_id`, `password`, `name`, `privilege_id`, `account_status`, `created_at`, `updated_at`, `created_id`, `ver`) VALUES
('admin', '$2y$10$NBnNnZPrL6O9yXbL6tP/HebaLi7akEe7J8s5YQ0/sDPzeRK5VvKcK', 'システム管理者', 1, 'ACTIVE', now(), now(), 'SYSTEM', 1);