SELECT
    /*%expand*/*
FROM
    category
WHERE
    category_code = /* categoryCode */'999999999'
    AND
    deleted_at IS NULL