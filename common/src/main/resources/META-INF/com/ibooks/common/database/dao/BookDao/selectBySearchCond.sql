SELECT
    b.book_id,
    b.book_code,
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
    c.category_name,
    EXISTS (
        SELECT 1
        FROM review r
        WHERE r.book_id = b.book_id
          AND r.user_id = /* userId */1
    ) AS is_reviewed
FROM
    book b
LEFT JOIN category c ON b.category_id_1 = c.category_id
LEFT JOIN category c2 ON b.category_id_2 = c2.category_id
LEFT JOIN category c3 ON b.category_id_3 = c3.category_id
WHERE
    c.deleted_at IS NULL
/*%if bookCode != null */
AND b.book_code = /* bookCode */'bookCode'
/*%end*/
/*%if title != null */
AND b.title LIKE /* title */'%title%'
/*%end*/
/*%if author != null */
AND
(b.author1 LIKE /* author */'%author%' OR b.author2 LIKE /* author */'%author%')
/*%end*/
/*%if publisher != null */
AND b.publisher LIKE /* publisher */'%publisher%'
/*%end*/
/*%if categoryId != null */
AND (b.category_id_1 = /* categoryId */1 OR b.category_id_2 = /* categoryId */2 OR b.category_id_3 = /* categoryId */3)
/*%end */
    b.deleted_at IS NULL
/*# orderBy */