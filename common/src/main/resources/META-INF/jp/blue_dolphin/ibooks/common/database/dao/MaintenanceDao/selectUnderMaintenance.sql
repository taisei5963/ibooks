SELECT
  /*%expand*/*
FROM
  maintenance
WHERE
  (
    type = 'IMMEDIATE'
    OR
    (
      type = 'DATETIME'
      AND
      status = 'ENABLED'
      AND
      effective_from <= /* now */'2025-10-20 00:00:00'
      AND
      effective_to >= /* now */'2025-10-20 00:00:00'
    )
  )
  AND
  site_type = /* siteType */'GENERAL'
  AND
  deleted_at IS NULL
ORDER BY maintenance_id DESC