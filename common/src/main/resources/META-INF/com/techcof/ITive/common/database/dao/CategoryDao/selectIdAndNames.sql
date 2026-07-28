SELECT
  category_id as ID,
  category_name as NAME
FROM
  category
WHERE
  deleted_at IS NULL