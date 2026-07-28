UPDATE
  reviewer
SET
  deleted_at = /* now */'2022-01-01 00:00:00',
  updated_at = /* now */'2022-01-01 00:00:00',
  created_id = /* registerId */'0'
WHERE
  reviewer_id = /* reviewerId */1