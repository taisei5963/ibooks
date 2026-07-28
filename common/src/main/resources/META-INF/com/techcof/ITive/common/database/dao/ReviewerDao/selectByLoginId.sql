SELECT
  /*%expand*/*
FROM
  reviewer
WHERE
  login_id = /* loginId */1
  AND
  deleted_at IS NULL