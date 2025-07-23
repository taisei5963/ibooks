select
  /*%expand*/*
from
  upload_hr
where
  upload_type = /* uploadType */'uploadType'
  and
  deleted_at is null
 order by upload_hr_id desc limit 1