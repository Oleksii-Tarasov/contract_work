DROP TABLE IF EXISTS estimate;
DROP TABLE IF EXISTS purchases;
DROP TABLE IF EXISTS dk_code_inf;
DROP TABLE IF EXISTS users;

-- Таблиця Користувачів
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       last_name VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       middle_name VARCHAR(255) NOT NULL,
                       short_name VARCHAR(100),            -- Прізвище та ініціали
                       position VARCHAR(150),              -- Посада
                       login VARCHAR(100) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL
);

-- Таблиця Кошторису
CREATE TABLE estimate
(
    id              SERIAL PRIMARY KEY,      -- Унікальний ідентифікатор запису
    kekv            VARCHAR(10)    NOT NULL, -- Код КЕКВ
    dk_code         VARCHAR(10)    NOT NULL, -- Код ДК
    project_name    TEXT           NOT NULL, -- Назва предмета закупівлі
    unit_of_measure VARCHAR(20)    NOT NULL, -- Одиниця виміру
    quantity        NUMERIC(10, 2) NOT NULL, -- Кількість
    price           NUMERIC(12, 2) NOT NULL, -- Ціна за одиницю
    total_price     NUMERIC(12, 2) NOT NULL, -- Загальна сума
    special_fund    NUMERIC(12, 2) NOT NULL, -- Кошти зі спеціального фонду
    general_fund    NUMERIC(12, 2) NOT NULL, -- Кошти із загального фонду
    justification   TEXT           NOT NULL, -- Обґрунтування закупівлі
    informatization BOOLEAN DEFAULT FALSE    -- Належність проєкту до інформатизації
);

-- Таблиця Закупівель
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
    contract_price    NUMERIC(12, 2) DEFAULT 0,         -- Сума Договору
    remaining_balance NUMERIC(12, 2) NOT NULL,          -- Залишок коштів
    payment_to        DATE,                             -- Гранична дата оплати по Договору
    special_fund      NUMERIC(12, 2) NOT NULL,
    general_fund      NUMERIC(12, 2) NOT NULL,
    justification     TEXT           NOT NULL,
    informatization   BOOLEAN DEFAULT FALSE,
    responsible_executor_id INTEGER REFERENCES users(id) ON DELETE SET NULL,
    status            SMALLINT DEFAULT 0                -- Статус закупівлі
);

-- Таблиця Кодів ДК інформатизації
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
    -- Проходимо по всіх еталонних кодах довідника (без суфікса)
    FOR ref_code IN SELECT SUBSTRING(dk_code FROM '^[0-9]+') FROM dk_code_inf
        LOOP
        -- ref_code тепер містить тільки префікс, наприклад, '30200000' або '71200000'

        -- Перетворюємо еталонний код на шаблон регулярного виразу
        -- '30200000' -> '^302\d{5}'

        -- Замінюємо 0 на \d (будь-яка цифра)
        -- '30200000' стає '302\d\d\d\d\d'
            prefix_part := REPLACE(ref_code, '0', '\d');

            -- Формуємо повний регулярний вираз:
            -- Початок (^) + наш шаблон префікса + дефіс (-) + будь-яка цифра (\d) + кінець ($)
            regex_pattern := '^' || prefix_part || '-\d$';

            -- Перевіряємо, чи вхідний код відповідає цьому регулярному виразу,
            -- Наприклад, '302256000-9' ~ '^302\d{5}-\d$'
            IF NEW.dk_code ~ regex_pattern THEN
                code_exists := TRUE;
                EXIT; -- Знайшли збіг, виходимо з циклу
            END IF;

        END LOOP;

    -- Встановлюємо фінальне значення
    IF code_exists THEN
        NEW.informatization = TRUE;
    ELSE
        NEW.informatization = FALSE;
    END IF;

    RETURN NEW;

END;
$$ LANGUAGE plpgsql;

-- Створення тригерів
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
