SELECT
  /*%expand*/*
FROM
  category
WHERE
  deleted_at IS NULL
ORDER BY category_id ASC