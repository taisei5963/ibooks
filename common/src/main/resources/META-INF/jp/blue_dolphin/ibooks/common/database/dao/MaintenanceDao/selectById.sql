SELECT
  /*%expand*/*
FROM
  maintenance
WHERE
  maintenance_id = /* maintenanceId */2
  AND
  deleted_at IS NULL