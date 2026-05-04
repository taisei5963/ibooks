SELECT
    b.book_id,
    b.title,
    b.sub_title,
    b.author1,
    b.author2,
    b.translator,
    b.publisher,
    b.pic_file_name,
    b.total_rating,
    b.category_id_1,
    b.category_id_2,
    b.category_id_3,
    c.category_name
FROM
  book b
  LEFT JOIN category c ON b.category_id_1 = c.category_id
  LEFT JOIN category c2 ON b.category_id_2 = c2.category_id
  LEFT JOIN category c3 ON b.category_id_3 = c3.category_id
WHERE
  book_id = /* bookId */1
  AND
  b.deleted_at IS NULL
  AND
  c.deleted_at IS NULL