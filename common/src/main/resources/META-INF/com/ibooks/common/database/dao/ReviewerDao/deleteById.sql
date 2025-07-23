update
  reviewer
set
  deleted_at = /* now */'2025-06-24 00:00:00',
  updated_at = /* now */'2025-06-24 00:00:00',
  created_id = /* registerId */'1'
where
  reviewer_id = /* reviewerId */1