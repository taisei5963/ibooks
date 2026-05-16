SELECT
    /*%expand*/*
FROM
    book_chapter
WHERE
    book_id = /* bookId */1
  AND
    deleted_at IS NULL