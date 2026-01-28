DROP TABLE IF EXISTS monthly_summary;
DROP TABLE IF EXISTS daily_summary;
DROP TABLE IF EXISTS health_activity;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS raw_health_data;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,

    UNIQUE KEY uk_user_nickname (nickname),
    UNIQUE KEY uk_user_email (email)
);

CREATE TABLE health_activity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recordkey VARCHAR(255) NOT NULL,
    steps INT NOT NULL,
    calories DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    distance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    period_from DATETIME(6) NOT NULL,
    period_to DATETIME(6) NOT NULL,
    source_type ENUM('SAMSUNG', 'APPLE') NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,

    UNIQUE KEY uk_health_activity_record (recordkey, period_from, period_to)
);

CREATE TABLE daily_summary (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recordkey VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    total_steps INT NOT NULL DEFAULT 0,
    total_calories DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    total_distance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    source_type ENUM('SAMSUNG', 'APPLE') NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,

    KEY idx_daily_summary_recordkey (recordkey),
    KEY idx_daily_summary_date (date),
    CONSTRAINT uk_daily_summary_record_date UNIQUE (recordkey, date)
);


CREATE TABLE monthly_summary (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recordkey VARCHAR(255) NOT NULL,
    summary_month VARCHAR(255) NOT NULL,
    total_steps INT NOT NULL DEFAULT 0,
    total_calories DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    total_distance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    source_type ENUM('SAMSUNG', 'APPLE') NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,

    KEY idx_monthly_summary_recordkey (recordkey),
    KEY idx_monthly_summary_month (summary_month),
    CONSTRAINT uk_monthly_summary_record_month UNIQUE (recordkey, summary_month)
);

CREATE TABLE raw_health_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recordkey VARCHAR(255) NOT NULL,
    source_type ENUM('SAMSUNG', 'APPLE') NOT NULL,
    raw_data LONGTEXT NOT NULL,
    created_at DATETIME(6) NOT NULL
);