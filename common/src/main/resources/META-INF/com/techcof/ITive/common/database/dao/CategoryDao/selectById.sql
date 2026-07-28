SELECT
    /*%expand*/*
FROM
    category
WHERE
    category_id = /* categoryId */1
    AND
    deleted_at IS NULL