-- 管理者
INSERT INTO `admin`
  (`login_id`,`password`,`name`,`account_status`,`login_failure_count`,`created_at`,`updated_at`,`created_id`,`ver`)
VALUES
  ('admin','4194d1706ed1f408d5e02d672777019f4d5385c766a8c6ca8acba3167d36a7b9','システム管理者','ACTIVE',0,now(),now(),'SYSTEM',1);

--　レビュワー
INSERT INTO `reviewer`
  (`login_id`,`password`,`name`,`account_status`,`login_failure_count`,`created_at`,`updated_at`,`created_id`,`ver`)
VALUES
  ('reviewerA','fa66ed652b77f7a4bbc9e07201ea3e37cdef4e8e130890b137aa5f55a65af1d0','レビュワーA','ACTIVE',0,now(),now(),'SYSTEM',1);

-- カテゴリ
INSERT INTO `category`
  (`category_code`,`category_name`,`created_at`,`updated_at`,`created_id`,`ver`)
VALUES
  ('38492015','WEBデザイン',now(),now(),'SYSTEM',1),
  ('59213084','データベース',now(),now(),'SYSTEM',1),
  ('84930217','フレームワーク',now(),now(),'SYSTEM',1),
  ('72819346','コマンド',now(),now(),'SYSTEM',1),
  ('10928374','資格',now(),now(),'SYSTEM',1),
  ('93284751','マネジメント',now(),now(),'SYSTEM',1),
  ('21839475','Android',now(),now(),'SYSTEM',1),
  ('48392016','iOS',now(),now(),'SYSTEM',1),
  ('70129483','プログラミングスキル',now(),now(),'SYSTEM',1),
  ('99999999','その他',now(),now(),'SYSTEM',1);