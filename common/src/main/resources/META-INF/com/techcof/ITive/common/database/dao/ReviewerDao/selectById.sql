SELECT
  /*%expand*/*
FROM
  reviewer
WHERE
  reviewer_id = /* reviewerId */1
  AND
  deleted_at IS NULL