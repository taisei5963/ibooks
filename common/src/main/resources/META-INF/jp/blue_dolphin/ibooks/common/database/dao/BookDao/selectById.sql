SELECT
    book_id,
    title,
    sub_title,
    author1,
    author2,
    translator,
    publisher,
    pic_file_name,
    total_rating,
    category_id_1,
    category_id_2,
    category_id_3
FROM
  book
WHERE
  book_id = /* bookId */1
  AND
  deleted_at IS NULL