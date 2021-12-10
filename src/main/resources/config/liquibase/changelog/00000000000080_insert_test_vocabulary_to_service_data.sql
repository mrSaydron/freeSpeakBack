-- liquibase formatted sql

-- changeset karmanov:00000000000080_insert_test_vocabulary_to_service_data runOnChange:true
delete from service_data where key_data in (
    'TEST_VOCABULARY_FIRST_STEP_SIZE',
    'TEST_VOCABULARY_BASE_STEP_SIZE',
    'TEST_VOCABULARY_MAX_FAIL_COUNT',
    'TEST_VOCABULARY_RANDOM_SIZE'
    );
insert into service_data (key_data, value) values
('TEST_VOCABULARY_FIRST_STEP_SIZE', 50),
('TEST_VOCABULARY_BASE_STEP_SIZE', 100),
('TEST_VOCABULARY_MAX_FAIL_COUNT', 5),
('TEST_VOCABULARY_RANDOM_SIZE', 25);
