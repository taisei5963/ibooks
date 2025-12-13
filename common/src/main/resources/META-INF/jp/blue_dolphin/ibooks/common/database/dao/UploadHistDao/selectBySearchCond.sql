SELECT
  /*%expand*/*
FROM
  upload_hist
WHERE
  /*%if createdId != null */
  created_id = /* createdId */'createdId'
  AND
  /*%end*/
  /*%if !createdIds.isEmpty() */
  created_id in /* createdIds */('createdId1', 'createdId2')
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
  deleted_at IS NULL
/*# orderBy */