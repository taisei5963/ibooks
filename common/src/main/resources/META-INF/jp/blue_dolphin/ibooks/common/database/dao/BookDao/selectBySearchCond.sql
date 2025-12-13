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
WHERE
  b.deleted_at IS NULL
  AND c.deleted_at IS NULL

  /*%if title != null */
  AND b.title LIKE /* title */'%title%'
  /*%end*/

  /*%if author != null */
  AND (
    b.author1 LIKE /* author */'%author%'
    OR
    b.author2 LIKE /* author */'%author%'
  )
  /*%end*/

  /*%if publisher != null */
  AND b.publisher LIKE /* publisher */'%publisher%'
  /*%end*/

  /*%if categoryId != null */
  AND (
    b.category_id_1 = /* categoryId */1
    OR
    b.category_id_2 = /* categoryId */1
    OR
    b.category_id_3 = /* categoryId */1
  )
  /*%end*/
/*# orderBy */