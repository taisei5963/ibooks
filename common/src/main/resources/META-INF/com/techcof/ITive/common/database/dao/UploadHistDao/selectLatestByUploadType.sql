SELECT
  /*%expand*/*
FROM
  upload_hist
WHERE
  upload_type = /* uploadType */'uploadType'
  AND
  deleted_at IS NULL
ORDER BY upload_hist_id DESC limit 1