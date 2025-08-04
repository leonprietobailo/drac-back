CREATE TABLE ITEM
(
    cod           BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    item_position BIGINT        NOT NULL,
    title         VARCHAR2(100) NOT NULL,
    price         NUMERIC(6, 2) NOT NULL,
    description   TEXT          NOT NULL
);

CREATE TABLE ITEM_COLOR
(
    cod   BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    color VARCHAR2(7) NOT NULL
);

CREATE TABLE ITEM_SIZE
(
    cod  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    size VARCHAR2(3) NOT NULL
);

CREATE TABLE ITEM_ATTRIBUTE
(
    cod            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    item_cod       BIGINT REFERENCES ITEM NOT NULL,
    item_color_cod BIGINT REFERENCES ITEM_COLOR,
    item_size_cod  BIGINT REFERENCES ITEM_SIZE,
    UNIQUE (item_cod, item_color_cod, item_size_cod)
);

CREATE TABLE ITEM_IMAGE
(
    cod                BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    url                TEXT                             NOT NULL,
    item_attribute_cod BIGINT REFERENCES ITEM_ATTRIBUTE NOT NULL
)
