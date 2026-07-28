SELECT
  /*%expand*/*
FROM
  maintenance
WHERE
  type = /* maintenanceType */'DATETIME'
  AND
  site_type = /* siteType */'GENERAL'
  AND
  deleted_at IS NULL