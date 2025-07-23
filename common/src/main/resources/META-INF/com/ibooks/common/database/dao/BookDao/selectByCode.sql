select
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
from
  book b
  left join category c on b.category_id_1 = c.category_id
  left join category c2 on b.category_id_2 = c2.category_id
  left join category c3 on b.category_id_3 = c3.category_id
where
  b.book_code = /* bookCode */'1234567890123'
  and b.deleted_at is null
  and c.deleted_at is null
order by b.book_id asc