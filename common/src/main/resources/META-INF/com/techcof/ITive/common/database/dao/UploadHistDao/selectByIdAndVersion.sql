SELECT
  /*%expand*/*
FROM
  upload_hist
WHERE
  upload_hist_id = /* uploadHistId */1
  and
  ver = /* ver */1
  and
  deleted_at IS NULL