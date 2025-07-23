select
  /*%expand*/*
from
  reviewer
where
  login_id = /* loginId */'loginId'
  and
  deleted_at is null