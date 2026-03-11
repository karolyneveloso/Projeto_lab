CREATE TABLE service_order (
    id BIGSERIAL PRIMARY KEY,
    patient_name  VARCHAR(200) NOT NULL,
    color  VARCHAR(100)  NOT NULL,
    material  VARCHAR(100)  NOT NULL,
    size  VARCHAR(100)  NOT NULL,
    description TEXT,
    status  VARCHAR(80)  NOT NULL DEFAULT 'SENT',
    dentist_id BIGINT NOT NULL,
    FOREIGN KEY (dentist_id) REFERENCES users(id),

    CONSTRAINT check_status
        CHECK (status IN ('SENT','RECEIVED','IN_PRODUCTION','ADJUSTMENT','FINISHED','CANCELED'))
);
CREATE INDEX idx_dentist_id ON service_order(dentist_id);
