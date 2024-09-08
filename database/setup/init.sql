CREATE TABLE IF NOT EXISTS accounts (
    account_id BIGSERIAL PRIMARY KEY,
    document_number VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id BIGSERIAL PRIMARY KEY,
    event_date TIMESTAMP NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    operation_type VARCHAR(50) NOT NULL,
    account_id BIGINT NOT NULL,
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
        REFERENCES accounts(account_id)
        ON DELETE CASCADE
);
