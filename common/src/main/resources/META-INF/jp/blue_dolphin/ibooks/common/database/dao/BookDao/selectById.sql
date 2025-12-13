SELECT
  /*%expand*/*
FROM
  book
WHERE
  book_id = /* bookId */1
  AND
  deleted_at IS NULL