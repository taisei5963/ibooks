SELECT
  /*%expand*/*
FROM
  book
WHERE
  jan_code IN /* janCodes */('4900000000000','4900000000001')
  AND
  deleted_at IS NULL