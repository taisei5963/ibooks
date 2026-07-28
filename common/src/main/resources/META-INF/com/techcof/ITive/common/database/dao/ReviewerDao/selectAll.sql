SELECT
  /*%expand*/*
FROM
  reviewer
WHERE
  deleted_at IS NULL
  /*# orderBy */