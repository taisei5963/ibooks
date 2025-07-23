select
  admin_id as id,
  login_id as code,
  name
from
  admin
where
  deleted_at is null