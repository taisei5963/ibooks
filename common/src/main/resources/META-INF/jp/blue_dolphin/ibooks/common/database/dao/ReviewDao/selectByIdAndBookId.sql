SELECT
  r.review_id,
  r.book_id,
  r.reviewer_id,
  r.rating,
  r.comment,
  r.review_date,
  rr.name
FROM
  review AS r
LEFT JOIN
  reviewer AS rr ON rr.reviewer_id = r.reviewer_id
  AND rr.deleted_at IS NULL
LEFT JOIN
  book AS b ON b.book_id = r.book_id
  AND b.deleted_at IS NULL
WHERE
    rr.account_status = 'ACTIVE'
    AND
    r.review_view_flag = 'ON'
    /*%if reviewId != null */
    AND
    r.review_id = /* reviewId */10
    /*%end*/
    /*%if bookId != null */
    AND
    r.book_id = /* bookId */20
    /*%end*/
    AND r.deleted_at IS NULL