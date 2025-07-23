select
  /*%expand*/*
from
  category
where
  deleted_at is null
order by category_id asc