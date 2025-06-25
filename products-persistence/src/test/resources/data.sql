INSERT INTO currency (id, code, created_at, updated_at)
VALUES (gen_random_uuid(), 'USD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'EUR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'GBP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'JPY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'CHF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'CAD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'AUD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'NZD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'CNY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'SEK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'NOK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'DKK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'INR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'BRL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, created_at, updated_at)
VALUES (gen_random_uuid(), 'PHYSICAL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (gen_random_uuid(), 'DIGITAL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);