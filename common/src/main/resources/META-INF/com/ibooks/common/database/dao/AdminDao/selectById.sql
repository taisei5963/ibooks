select
  /*%expand*/*
from
  admin
where
  admin_id = /* adminId */1
  and
  deleted_at is null