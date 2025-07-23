select
  r.review_id,
  r.book_id,
  r.reviewer_id,
  r.rating,
  r.comment,
  r.review_date,
  rv.name
from
  review r
left join reviewer rv on rv.reviewer_id = r.reviewer_id
  and rv.deleted_at is null
left join book b on b.book_id = r.book_id
  and b.deleted_at is null
where
  rv.account_status = 'ACTIVE'
  and r.review_status = 'PUBLIC'
  /*%if reviewId != null*/
    and r.review_id = /* reviewId */1
  /*%end*/
  /*%if bookId != null*/
    and r.book_id = /* bookId */1
  /*%end*/
  and r.deleted_at is null