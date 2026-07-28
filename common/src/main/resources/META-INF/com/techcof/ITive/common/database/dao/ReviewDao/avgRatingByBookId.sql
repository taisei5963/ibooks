SELECT
  AVG(rating)
FROM
  review
WHERE
  book_id = /* bookId */1
  AND
  deleted_at IS NULL