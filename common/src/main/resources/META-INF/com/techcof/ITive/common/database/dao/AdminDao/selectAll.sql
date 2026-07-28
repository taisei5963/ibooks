SELECT
  /*%expand*/*
FROM
  admin
WHERE
  deleted_at IS NULL
  /*# orderBy */