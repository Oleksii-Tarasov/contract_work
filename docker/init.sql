DROP TABLE IF EXISTS estimate;
DROP TABLE IF EXISTS dk_code_inf;

-- Таблиця Кошторису
CREATE TABLE estimate
(
    id              SERIAL PRIMARY KEY,      -- Унікальний ідентифікатор запису
    kekv            VARCHAR(10)    NOT NULL, -- Код КЕКВ
    dk_code         VARCHAR(10)    NOT NULL, -- Код ДК
    name_project    TEXT           NOT NULL, -- Назва предмета закупівлі
    unit_of_measure VARCHAR(20)    NOT NULL, -- Одиниця виміру
    quantity        NUMERIC(10, 2) NOT NULL, -- Кількість
    price           NUMERIC(12, 2) NOT NULL, -- Ціна за одиницю
    total_price     NUMERIC(12, 2) NOT NULL, -- Загальна сума
    special_fund    NUMERIC(12, 2) NOT NULL, -- Кошти зі спеціального фонду
    general_fund    NUMERIC(12, 2) NOT NULL, -- Кошти із загального фонду
    justification   TEXT           NOT NULL, -- Обґрунтування закупівлі
    informatization BOOLEAN DEFAULT FALSE    -- Належність проєкту до інформатизації
);

-- Таблиця Кодів ДК інформатизації
CREATE TABLE dk_code_inf
(
    id           SERIAL PRIMARY KEY,
    dk_code      VARCHAR(10) NOT NULL,
    name_project TEXT        NOT NULL
);

CREATE OR REPLACE FUNCTION set_informatization_status()
    RETURNS TRIGGER AS
$$
DECLARE
    code_exists   BOOLEAN DEFAULT FALSE;
    ref_code      TEXT;
    regex_pattern TEXT;
BEGIN
    -- Проходимо по всіх еталонних кодах довідника
    FOR ref_code IN SELECT dk_code FROM dk_code_inf
        LOOP
        -- Перетворюємо еталонний код на шаблон регулярного виразу
        -- '30200000-1' перетворюється на '^302\d{5}-\d$'

        -- 1. Замінюємо всі цифри на '\d' (будь-яка цифра)
            regex_pattern := REGEXP_REPLACE(ref_code, '[0-9]', '\d', 'g');

            -- 2. Замінимо нулі на '\d', а інші цифри залишимо як є.
            -- Замінюємо 0 на \d (будь-яка цифра)
            -- '30200000-1' стає '302\d\d\d\d\d-\d'
            regex_pattern := REPLACE(ref_code, '0', '\d');
            -- Додаємо маркери початку та кінця рядка для точної відповідності
            regex_pattern := '^' || regex_pattern || '$';

            -- Перевіряємо, чи вхідний код відповідає цьому регулярному виразу
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

-- Створення тригера
DROP TRIGGER IF EXISTS trigger_set_informatization ON estimate;

CREATE TRIGGER trigger_set_informatization
    BEFORE INSERT OR UPDATE OF dk_code
    ON estimate
    FOR EACH ROW
EXECUTE FUNCTION set_informatization_status();
