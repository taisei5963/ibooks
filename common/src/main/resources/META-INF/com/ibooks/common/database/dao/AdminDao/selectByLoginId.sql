select
  /*%expand*/*
from
  admin
where
  login_id = /* loginId */'loginId'
  and
  deleted_at is null