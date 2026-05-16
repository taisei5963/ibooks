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
  category_id_3,
  created_at,
  created_id
FROM
  book

WHERE
  deleted_at IS NULL

  /*%if title != null */
  AND title LIKE /* title */'%title%'
  /*%end*/

  /*%if author != null */
  AND (
    author1 LIKE /* author */'%author%'
    OR
    author2 LIKE /* author */'%author%'
  )
  /*%end*/

  /*%if publisher != null */
  AND publisher LIKE /* publisher */'%publisher%'
  /*%end*/

  /*%if categoryId != null */
  AND (
    category_id_1 = /* categoryId */1
    OR
    category_id_2 = /* categoryId */1
    OR
    category_id_3 = /* categoryId */1
  )
  /*%end*/
/*# orderBy */