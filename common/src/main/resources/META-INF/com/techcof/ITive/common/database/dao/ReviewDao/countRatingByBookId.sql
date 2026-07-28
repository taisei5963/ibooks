SELECT
  COUNT(*)
FROM
  review
WHERE
  book_id = /* bookId */1
  AND
  /*%if kind != 0 */
  rating = /* kind */5
  AND
  /*%end*/
  deleted_at IS NULL