select
  /*%expand*/*
from
  upload_hr
where
  /*%if registerId != null */
  register_id = /* registerId */'registerId'
  and
  /*%end*/
  /*%if siteType != null */
  site_type = /* siteType */'ADMIN'
  and
  /*%end*/
  /*%if uploadType != null */
  upload_type = /* uploadType */'uploadType'
  and
  /*%end*/
  /*%if uploadStatus != null */
  upload_status = /* uploadStatus */'uploadStatus'
  and
  /*%end*/
  deleted_at is null
/*# orderBy */