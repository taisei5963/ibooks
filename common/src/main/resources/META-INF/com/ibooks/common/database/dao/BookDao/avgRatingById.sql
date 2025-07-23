update
  book
set
  total_rating = (
    select
      round(coalesce(avg(rating), 0.0), 1)
    from
      review
    where book_id = /* bookId */1
    and
    deleted_at is null
  )
where book_id = /* bookId */1;
