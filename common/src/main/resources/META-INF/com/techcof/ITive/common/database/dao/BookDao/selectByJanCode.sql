SELECT
  /*%expand*/*
FROM
  book
WHERE
  jan_code = /* janCode */'4900000000000'
  AND
  deleted_at IS NULL