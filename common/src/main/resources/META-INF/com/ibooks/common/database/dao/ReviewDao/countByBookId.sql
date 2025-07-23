select
  count(*) as counter
from
  review
where
  book_id = /* bookId */1
  /*%if kind != 0*/
    and rating = /* kind */1
  /*%end*/
  and deleted_at is null
