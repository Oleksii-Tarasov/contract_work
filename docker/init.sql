DROP TABLE IF EXISTS estimate;

-- Таблиця Кошторису
CREATE TABLE estimate
(
    id              SERIAL PRIMARY KEY,                                -- Унікальний ідентифікатор запису
    kekv            VARCHAR(10)    NOT NULL,                           -- Код КЕКВ
    dk_code         VARCHAR(10)    NOT NULL,                           -- Код ДК
    name_project    TEXT           NOT NULL,                           -- Назва предмета закупівлі
    unit_of_measure VARCHAR(20)    NOT NULL,                           -- Одиниця виміру
    quantity        NUMERIC(10, 2) NOT NULL,                           -- Кількість
    price           NUMERIC(12, 2) NOT NULL,                           -- Ціна за одиницю
    total_price     NUMERIC(12, 2) NOT NULL,                           -- Загальна сума
    special_fund    NUMERIC(12, 2) NOT NULL,                           -- Кошти із спеціального фонду
    general_fund    NUMERIC(12, 2) NOT NULL,                           -- Кошти із загального фонду
    justification   TEXT           NOT NULL                           -- Обґрунтування закупівлі
);

COMMENT ON COLUMN procurement_projects.kekv IS 'Код економічної класифікації видатків';
COMMENT ON COLUMN procurement_projects.dk_code IS 'Код Державного класифікатора 021:2015';
COMMENT ON COLUMN procurement_projects.name_project IS 'Назва предмета закупівлі';
COMMENT ON COLUMN procurement_projects.unit_of_measure IS 'Одиниця виміру предмета закупівлі';
COMMENT ON COLUMN procurement_projects.quantity IS 'Кількість предмета закупівлі';
COMMENT ON COLUMN procurement_projects.price IS 'Ціна за одиницю предмета закупівлі';
COMMENT ON COLUMN procurement_projects.total_price IS 'Загальна сума за предметом закупівлі';
COMMENT ON COLUMN procurement_projects.special_fund IS 'Сума із спеціального фонду';
COMMENT ON COLUMN procurement_projects.general_fund IS 'Сума із загального фонду';
COMMENT ON COLUMN procurement_projects.justification IS 'Обґрунтування предмета закупівлі';