SELECT
    /*%expand*/*
FROM
    book_chapter
WHERE
    book_chapter_id = /* bookChapterId */1
    AND
    deleted_at IS NULL
ORDER BY
    sort_order ASC,
    book_chapter_id ASC