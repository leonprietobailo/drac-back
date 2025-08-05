INSERT INTO USERS (EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, BIRTHDATE, TELEPHONE, NEWSLETTER)
VALUES ('john.doe@example.com', 'password123', 'John', 'Doe', DATE '1990-05-15', '555-1234', false),
       ('jane.smith@example.com', 'secret456', 'Jane', 'Smith', DATE '1985-10-30', '555-5678', false),
       ('alice.wong@example.com', 'qwerty789', 'Alice', 'Wong', DATE '1992-07-20', '555-9012', false),
       ('bob.johnson@example.com', 'passw0rd', 'Bob', 'Johnson', DATE '1988-02-25', '555-3456', false),
       ('carol.brown@example.com', 'letmein', 'Carol', 'Brown', DATE '1995-12-12', '555-7890', false);


INSERT INTO ITEM (ITEM_POSITION, TITLE, PRICE, DESCRIPTION)
VALUES (1, 'ITEM #1', 6.25, 'A basic entry-level item, perfect for starters.'),
       (2, 'ITEM #2', 7.5, 'An upgraded version of item #1 with slight improvements.'),
       (3, 'ITEM #3', 8.75, 'Popular among casual users for its balance of price and quality.'),
       (4, 'ITEM #4', 10.0, 'Offers enhanced performance and durability.'),
       (5, 'ITEM #5', 11.25, 'Ideal for users seeking more reliability and style.'),
       (6, 'ITEM #6', 12.5, 'Mid-range item offering excellent value for money.'),
       (7, 'ITEM #7', 13.75, 'Comes with premium features and better materials.'),
       (8, 'ITEM #8', 15.0, 'Designed for professionals needing consistent performance.'),
       (9, 'ITEM #9', 16.25, 'Advanced item with extended capabilities and support.'),
       (10, 'ITEM #10', 17.5, 'A refined product built for long-term use.'),
       (11, 'ITEM #11', 18.75, 'Top-tier item offering cutting-edge features.'),
       (12, 'ITEM #12', 20.0, 'The ultimate item in this series with maximum performance.');


INSERT INTO COLOR (color) VALUES ('#FF5733'); -- 1
INSERT INTO COLOR (color) VALUES ('#33FF57'); -- 2
INSERT INTO COLOR (color) VALUES ('#3357FF'); -- 3
INSERT INTO COLOR (color) VALUES ('#FFFF00'); -- 4
INSERT INTO COLOR (color) VALUES ('#FF33FF'); -- 5
INSERT INTO SIZE (size) VALUES ('XXS'); -- 1
INSERT INTO SIZE (size) VALUES ('XS'); -- 2
INSERT INTO SIZE (size) VALUES ('S'); -- 3
INSERT INTO SIZE (size) VALUES ('M'); -- 4
INSERT INTO SIZE (size) VALUES ('L'); -- 5
INSERT INTO SIZE (size) VALUES ('XL'); -- 6
INSERT INTO SIZE (size) VALUES ('XXL'); -- 7

-- ITEM 1 has 3 colors
INSERT INTO ITEM_COLOR(color_cod, item_cod) VALUES (1, 1);
INSERT INTO ITEM_COLOR(color_cod, item_cod) VALUES (2, 1);
INSERT INTO ITEM_COLOR(color_cod, item_cod) VALUES (3, 1);

-- ITEM 2 HAS 2 COLORS, 3 SIZES.
INSERT INTO ITEM_COLOR(color_cod, item_cod) VALUES (4, 2);
INSERT INTO ITEM_COLOR(color_cod, item_cod) VALUES (5, 2);
INSERT INTO ITEM_SIZE(size_cod, item_cod) VALUES (2, 2);
INSERT INTO ITEM_SIZE(size_cod, item_cod) VALUES (3, 2);
INSERT INTO ITEM_SIZE(size_cod, item_cod) VALUES (4, 2);
INSERT INTO ITEM_SIZE(size_cod, item_cod) VALUES (5, 2);

-- ITEM 3 HAS ALL SIZES.
INSERT INTO ITEM_SIZE(size_cod, item_cod) VALUES (1, 3);
INSERT INTO ITEM_SIZE(size_cod, item_cod) VALUES (2, 3);
INSERT INTO ITEM_SIZE(size_cod, item_cod) VALUES (3, 3);
INSERT INTO ITEM_SIZE(size_cod, item_cod) VALUES (4, 3);
INSERT INTO ITEM_SIZE(size_cod, item_cod) VALUES (5, 3);
INSERT INTO ITEM_SIZE(size_cod, item_cod) VALUES (6, 3);
INSERT INTO ITEM_SIZE(size_cod, item_cod) VALUES (7, 3);

-- ITEM 4-12 HAVE NO COLORS NOR SIZES.

-- ITEM 1, COLOR 1
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/FF5733/33FF57', 1, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/FF5733/3357FF', 1, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/FF5733/FFFF00', 1, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/FF5733/FF33FF', 1, 1);
-- ITEM 1, COLOR 2
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/33FF57/FF5733', 2, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/33FF57/3357FF', 2, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/33FF57/FFFF00', 2, 1);
-- ITEM 1, COLOR 3
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/3357FF/FF5733', 3, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/3357FF/1A2B3C', 3, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/3357FF/A1B2C3', 3, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/3357FF/DDEEFF', 3, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/3357FF/123456', 3, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/3357FF/ABCDEF', 3, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/3357FF/765432', 3, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/3357FF/DEAD55', 3, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/3357FF/C0FFEE', 3, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/3357FF/BADA55', 3, 1);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/3357FF/7A1CEB', 3, 1);


-- ITEM 2, COLOR 1
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/FFFF00/33FF57', 4, 2);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/FFFF00/3357FF', 4, 2);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/FFFF00/FF5733', 4, 2);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/FFFF00/FF33FF', 4, 2);
-- ITEM 2, COLOR 2
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/FF33FF/FF5733', 5, 2);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/FF33FF/3357FF', 5, 2);
INSERT INTO ITEM_IMAGE(url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/FF33FF/FFFF00', 5, 2);

INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', NULL, 3);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF33FF', NULL, 3);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FFFF00', NULL, 3);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF5733', NULL, 3);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/3357FF', NULL, 3);

INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', NULL, 4);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF33FF', NULL, 4);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FFFF00', NULL, 4);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF5733', NULL, 4);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/3357FF', NULL, 4);

INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', NULL, 5);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF33FF', NULL, 5);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FFFF00', NULL, 5);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF5733', NULL, 5);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/3357FF', NULL, 5);

INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', NULL, 6);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF33FF', NULL, 6);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FFFF00', NULL, 6);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF5733', NULL, 6);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/3357FF', NULL, 6);

INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', NULL, 7);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF33FF', NULL, 7);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FFFF00', NULL, 7);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF5733', NULL, 7);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/3357FF', NULL, 7);

INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', NULL, 8);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF33FF', NULL, 8);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FFFF00', NULL, 8);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF5733', NULL, 8);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/3357FF', NULL, 8);

INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', NULL, 9);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF33FF', NULL, 9);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FFFF00', NULL, 9);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF5733', NULL, 9);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/3357FF', NULL, 9);

INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', NULL, 10);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF33FF', NULL, 10);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FFFF00', NULL, 10);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF5733', NULL, 10);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/3357FF', NULL, 10);

INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', NULL, 11);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF33FF', NULL, 11);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FFFF00', NULL, 11);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF5733', NULL, 11);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/3357FF', NULL, 11);

INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', NULL, 12);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF33FF', NULL, 12);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FFFF00', NULL, 12);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/FF5733', NULL, 12);
INSERT INTO ITEM_IMAGE (url, color_cod, item_cod) VALUES ('https://dummyimage.com/195x195/000/3357FF', NULL, 12);
