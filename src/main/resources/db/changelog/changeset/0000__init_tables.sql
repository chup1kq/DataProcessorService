CREATE TABLE IF NOT EXISTS request_log (
    id SERIAL PRIMARY KEY,
    json_payload TEXT,
    processing_time_ms BIGINT NOT NULL,
    xml_tag_count INTEGER,
    json_key_count INTEGER
);