select
  /*%expand*/*
from
  category
where
  category_code = /* categoryCode */'categoryCode'
  and
  deleted_at is null