SELECT
    COUNT(book_id)
FROM
    book
WHERE
    deleted_at IS NULL