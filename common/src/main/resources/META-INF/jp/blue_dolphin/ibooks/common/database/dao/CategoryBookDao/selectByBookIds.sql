SELECT
  c.category_code,
  c.category_name,
  b.book_id,
  b.category_id_1,
  b.category_id_2,
  b.category_id_3
FROM
  category c
  INNER JOIN book b ON c.category_id = b.category_id_1
    OR c.category_id = b.category_id_2
    OR c.category_id = b.category_id_3
    AND b.deleted_at IS NULL
WHERE
  b.book_id IN /* bookIds */(1,2)
ORDER BY category_id ASC;