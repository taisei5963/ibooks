SELECT
  /*%expand*/*
FROM
  upload_hist
WHERE
  created_at <= /* storageTerm */'2024-01-01 23:59:59'
  AND
  deleted_at IS NULL