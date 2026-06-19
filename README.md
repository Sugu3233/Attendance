Initial Project Creation.


-- ==========================================
-- USERS TABLE
-- ==========================================
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    mobile_no VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    address TEXT,
    city VARCHAR(100),
    district VARCHAR(100),
    dob DATE,
    role VARCHAR(50) NOT NULL,
    oprtnl_flag BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- ATTENDANCE TABLE
-- ==========================================
CREATE TABLE attendance (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('PRESENT', 'ABSENT')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_attendance_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_attendance_user_date
        UNIQUE (user_id, attendance_date)
);

-- ==========================================
-- FEE STRUCTURE TABLE
-- ==========================================
CREATE TABLE fee_structure (
    id BIGSERIAL PRIMARY KEY,
    structure_name VARCHAR(150) NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    effective_from DATE NOT NULL,
    oprtnl_flag BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- FEE PAYMENT TABLE
-- One record per user per month
-- ==========================================
CREATE TABLE fee_payment (
    id BIGSERIAL PRIMARY KEY,
    structure_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    payment_month DATE NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    remarks TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_fee_payment_structure
        FOREIGN KEY (structure_id)
        REFERENCES fee_structure(id),

    CONSTRAINT fk_fee_payment_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT uk_fee_payment_user_month
        UNIQUE (user_id, payment_month)
);

-- ==========================================
-- FEE PAYMENT HISTORY TABLE
-- Stores all payment transactions
-- ==========================================
CREATE TABLE fee_payment_history (
    id BIGSERIAL PRIMARY KEY,
    fee_payment_id BIGINT NOT NULL,
    paid_amount NUMERIC(10,2) NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_mode VARCHAR(30),
    transaction_ref VARCHAR(100),
    remarks TEXT,

    CONSTRAINT fk_fee_payment_history_payment
        FOREIGN KEY (fee_payment_id)
        REFERENCES fee_payment(id)
        ON DELETE CASCADE
);

-- ==========================================
-- INDEXES
-- ==========================================
CREATE INDEX idx_users_mobile
ON users(mobile_no);

CREATE INDEX idx_attendance_user
ON attendance(user_id);

CREATE INDEX idx_fee_payment_user
ON fee_payment(user_id);

CREATE INDEX idx_fee_payment_month
ON fee_payment(payment_month);
