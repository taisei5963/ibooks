SELECT
  /*%expand*/*
FROM
  upload_hist_detail
WHERE
  upload_hist_id = /* uploadHistId */1
  and
  deleted_at IS NULL
/*# orderBy */