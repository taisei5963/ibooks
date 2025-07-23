select
  /*%expand*/*
from
  reviewer
where
  reviewer_id = /* reviewerId */1
  and
  deleted_at is null