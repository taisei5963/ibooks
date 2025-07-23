select
  /*%expand*/*
from
  admin
where
  deleted_at is null
/*# orderBy */