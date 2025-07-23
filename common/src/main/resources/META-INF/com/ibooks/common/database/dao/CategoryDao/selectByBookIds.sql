select
  c.category_id,
  c.category_code,
  c.category_name
from
  category c
  inner join book b on c.category_id = b.category_id_1
  inner join book b on c.category_id = b.category_id_2
  inner join book b on c.category_id = b.category_id_3
    and b.deleted_at is null
where
  b.book_id in /* bookIds */(1,2)
  and
order by c.category_id asc