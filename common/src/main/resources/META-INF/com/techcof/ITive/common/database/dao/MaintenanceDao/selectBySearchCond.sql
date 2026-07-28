SELECT
  /*%expand*/*
FROM
  maintenance
WHERE
  type = /* maintenanceType */'DATETIME'
  AND
  deleted_at IS NULL
  /*# orderBy */