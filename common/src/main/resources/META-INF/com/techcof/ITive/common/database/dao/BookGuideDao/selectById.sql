SELECT
    /*%expand*/*
FROM
    book_guide
WHERE
    book_guide_id = /* bookGuideId */1
  AND
    deleted_at IS NULL