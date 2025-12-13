SELECT
  /*%expand*/*
FROM
  book
WHERE
  title = /* title */'title'
  AND
  publisher = /* publisher */'publisher'
  AND
  deleted_at IS NULL