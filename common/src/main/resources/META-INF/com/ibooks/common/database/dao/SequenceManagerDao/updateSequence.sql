update
  sequence_manager
set
  sequence = /* nextSequence */3, updated_at = /* now */'2022-01-01 00:00:0'
where
  sequence_manager_id = /* sequenceManagerId */1
  and
  sequence = /* nowSequence */0