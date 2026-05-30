SELECT
  book_id,
  title,
  sub_title,
  author1,
  author2,
  translator,
  publisher,
  pic_file_name,
  level,
  category_id_1,
  category_id_2,
  category_id_3,
  created_at,
  created_id
FROM
  book

WHERE
  deleted_at IS NULL

  /*%if categoryId != null */
  AND (
    category_id_1 = /* categoryId */1
    OR
    category_id_2 = /* categoryId */1
    OR
    category_id_3 = /* categoryId */1
  )
  /*%end*/

  /*%if level != null */
    AND level LIKE /* level */'%level%'
  /*%end*/
/*# orderBy */