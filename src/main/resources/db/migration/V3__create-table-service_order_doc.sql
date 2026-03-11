CREATE TABLE service_order_doc (
    id BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    file_path TEXT NOT NULL,
    service_order_id BIGINT NOT NULL,
    FOREIGN KEY (service_order_id) REFERENCES service_order(id)
    ON DELETE CASCADE

);