SELECT
 /*%expand*/*
FROM
 action_role
WHERE
 privilege_id = /* privilegeId */1
 AND
 deleted_at IS NULL