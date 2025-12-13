SELECT
  /*%expand*/*
FROM
  maintenance
WHERE
  maintenance_id = /* maintenanceId */1
  AND
  ver = /* ver */1
  AND
  deleted_at IS NULL