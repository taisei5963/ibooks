select
  /*%expand*/*
from
  category
where
  category_id = /* categoryId */1
  and
  deleted_at is null