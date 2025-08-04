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


INSERT INTO ITEM_COLOR (color) VALUES ('#FF5733');
INSERT INTO ITEM_COLOR (color) VALUES ('#33FF57');
INSERT INTO ITEM_COLOR (color) VALUES ('#3357FF');
INSERT INTO ITEM_COLOR (color) VALUES ('#FFFF00');
INSERT INTO ITEM_COLOR (color) VALUES ('#FF33FF');
INSERT INTO ITEM_SIZE (size) VALUES ('XXS');
INSERT INTO ITEM_SIZE (size) VALUES ('XS');
INSERT INTO ITEM_SIZE (size) VALUES ('S');
INSERT INTO ITEM_SIZE (size) VALUES ('M');
INSERT INTO ITEM_SIZE (size) VALUES ('L');
INSERT INTO ITEM_SIZE (size) VALUES ('XL');
INSERT INTO ITEM_SIZE (size) VALUES ('XXL');

-- ITEM 1 has 3 colors (3, 4, 5)
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (1, 3, NULL);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (1, 4, NULL);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (1, 5, NULL);

-- ITEM 2 has 2 colors (1, 2)
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (2, NULL, NULL);

-- ITEM 3 already had 3 colors (3, 4, 5) – preserved
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (3, 3, 2);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (3, 3, 3);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (3, 3, 4);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (3, 4, 2);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (3, 4, 3);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (3, 4, 4);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (3, 5, 2);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (3, 5, 3);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (3, 5, 4);

-- ITEM 4 now has 4 colors (1 to 4)
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (4, 1, NULL);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (4, 2, NULL);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (4, 3, NULL);
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (4, 4, NULL);

-- ITEM 5 remains single-color (1) → NULL
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (5, NULL, NULL);

-- ITEM 6 now has 5 colors (1 to 5)
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (6, NULL, NULL);

-- ITEM 7 now has 2 colors (2, 3)
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (7, NULL, NULL);

-- ITEM 8 now has 3 colors (3, 4, 5)
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (8, NULL, NULL);

-- ITEM 9 remains without color → NULL
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (9, NULL, NULL);

-- ITEM 10 now has 2 colors (4, 5) and no sizes
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (10, NULL, NULL);

-- ITEM 11 remains without color/size → NULL
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (11, NULL, NULL);

-- ITEM 12 now has 2 colors and size
INSERT INTO ITEM_ATTRIBUTE (item_cod, item_color_cod, item_size_cod) VALUES (12, NULL, NULL);

INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/3357FF/ffffff', 1);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/FFFF00/ffffff', 2);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/FF33FF/ffffff', 3);

INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/3357FF/ffffff', 5);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/3357FF/ffffff', 6);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/3357FF/ffffff', 7);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/FFFF00/ffffff', 8);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/FFFF00/ffffff', 9);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/FFFF00/ffffff', 10);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/FF33FF/ffffff', 11);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/FF33FF/ffffff', 12);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/FF33FF/ffffff', 13);

INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/FF5733/ffffff', 14);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/33FF57/ffffff', 15);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/3357FF/ffffff', 16);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/FFFF00/ffffff', 17);


INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', 4);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', 18);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', 19);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', 20);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', 21);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', 22);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', 23);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', 24);
INSERT INTO ITEM_IMAGE (url, item_attribute_cod) VALUES ('https://dummyimage.com/195x195/000/ffffff', 25);
