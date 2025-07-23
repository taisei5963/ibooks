select
  category_id as id,
  category_name as name
from
  category
where
  deleted_at is null
order by category_id asc