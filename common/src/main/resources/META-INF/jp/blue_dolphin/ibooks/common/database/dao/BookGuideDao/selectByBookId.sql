SELECT
    /*%expand*/*
FROM
    book_guide
WHERE
    book_id = /* bookId */1
  AND
    deleted_at IS NULL