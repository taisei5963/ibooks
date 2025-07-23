select
  /*%expand*/*
from
  upload_hr_detail
where
  upload_hr_detail_id = /* uploadHrDetailId */1
  and
  deleted_at is null