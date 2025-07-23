select
  /*%expand*/*
from
  upload_hr
where
  upload_hr_id = /* uploadHrId */1
  and
  deleted_at is null