SELECT
  /*%expand*/*
FROM
  admin
WHERE
  admin_id = /* adminId */1
  AND
  deleted_at IS NULL