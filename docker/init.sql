DROP TABLE IF EXISTS estimate;
DROP TABLE IF EXISTS purchases;
DROP TABLE IF EXISTS dk_code_inf;
DROP TABLE IF EXISTS users;

-- Users Table
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       last_name VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       middle_name VARCHAR(255) NOT NULL,
                       short_name VARCHAR(100),            -- Surname and initials
                       position VARCHAR(150),              -- Position
                       login VARCHAR(100) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL
);

-- Estimate Table
CREATE TABLE estimate
(
    id              SERIAL PRIMARY KEY,      -- Unique record identifier
    kekv            VARCHAR(10)    NOT NULL, -- KEKV Code
    dk_code         VARCHAR(10)    NOT NULL, -- DK Code
    project_name    TEXT           NOT NULL, -- Item name
    unit_of_measure VARCHAR(20)    NOT NULL, -- Unit of measure
    quantity        NUMERIC(10, 2) NOT NULL, -- Quantity
    price           NUMERIC(12, 2) NOT NULL, -- Unit price
    total_price     NUMERIC(12, 2) NOT NULL, -- Total amount
    special_fund    NUMERIC(12, 2) NOT NULL, -- Special fund funds
    general_fund    NUMERIC(12, 2) NOT NULL, -- General fund funds
    justification   TEXT           NOT NULL, -- Purchase justification
    informatization BOOLEAN DEFAULT FALSE    -- Is informatization project
);

-- Purchases Table
CREATE TABLE purchases
(
    id                SERIAL PRIMARY KEY,
    kekv              VARCHAR(10)    NOT NULL,
    dk_code           VARCHAR(10),
    project_name      TEXT           NOT NULL,
    unit_of_measure   VARCHAR(20)    NOT NULL,
    quantity          NUMERIC(10, 2) NOT NULL,
    price             NUMERIC(12, 2) NOT NULL,
    total_price       NUMERIC(12, 2) NOT NULL,
    contract_price    NUMERIC(12, 2) DEFAULT 0,         -- Contract Amount
    remaining_balance NUMERIC(12, 2) NOT NULL,          -- Remaining Balance
    payment_to        DATE,                             -- Payment deadline per Contract
    special_fund      NUMERIC(12, 2) NOT NULL,
    general_fund      NUMERIC(12, 2) NOT NULL,
    justification     TEXT           NOT NULL,
    informatization   BOOLEAN DEFAULT FALSE,
    responsible_executor_id INTEGER REFERENCES users(id) ON DELETE SET NULL,
    status            SMALLINT DEFAULT 0                -- Purchase status
);

-- Informatization DK Codes Table
CREATE TABLE dk_code_inf
(
    id           SERIAL PRIMARY KEY,
    dk_code      VARCHAR(10) NOT NULL,
    project_name TEXT        NOT NULL
);

CREATE OR REPLACE FUNCTION set_informatization_status()
    RETURNS TRIGGER AS $$
DECLARE
    code_exists   BOOLEAN DEFAULT FALSE;
    ref_code      TEXT;
    regex_pattern TEXT;
    prefix_part   TEXT;
BEGIN
    -- Loop through all reference codes from the directory (without suffix)
    FOR ref_code IN SELECT SUBSTRING(dk_code FROM '^[0-9]+') FROM dk_code_inf
        LOOP
        -- ref_code now contains only the prefix, e.g., '30200000' or '71200000'

        -- Convert reference code to a regex pattern
        -- '30200000' -> '^302\d{5}'

        -- Replace 0 with \d (any digit)
        -- '30200000' becomes '302\d\d\d\d\d'
            prefix_part := REPLACE(ref_code, '0', '\d');

            -- Form the full regular expression:
            -- Start (^) + our prefix pattern + hyphen (-) + any digit (\d) + end ($)
            regex_pattern := '^' || prefix_part || '-\d$';

            -- Check if the input code matches this regex pattern,
            -- For example, '302256000-9' ~ '^302\d{5}-\d$'
            IF NEW.dk_code ~ regex_pattern THEN
                code_exists := TRUE;
                EXIT; -- Match found, exit loop
            END IF;

        END LOOP;

    -- Set final value
    IF code_exists THEN
        NEW.informatization = TRUE;
    ELSE
        NEW.informatization = FALSE;
    END IF;

    RETURN NEW;

END;
$$ LANGUAGE plpgsql;

-- Create triggers
DROP TRIGGER IF EXISTS trigger_set_informatization_estimate ON estimate;
DROP TRIGGER IF EXISTS trigger_set_informatization_purchases ON purchases;

CREATE TRIGGER trigger_set_informatization_estimate
    BEFORE INSERT OR UPDATE OF dk_code
    ON estimate
    FOR EACH ROW
EXECUTE FUNCTION set_informatization_status();

CREATE TRIGGER trigger_set_informatization_purchases
    BEFORE INSERT OR UPDATE OF dk_code
    ON purchases
    FOR EACH ROW
EXECUTE FUNCTION set_informatization_status();
