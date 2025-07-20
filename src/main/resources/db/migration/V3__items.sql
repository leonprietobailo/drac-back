CREATE TABLE ITEM
(
    cod           BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    item_position BIGINT        NOT NULL,
    title         VARCHAR2(100) NOT NULL,
    price         NUMERIC(6, 2) NOT NULL
);

CREATE TABLE ITEM_COLOR
(
    cod      BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    item_cod BIGINT REFERENCES ITEM NOT NULL,
    color    VARCHAR2(7)            NOT NULL,
    url      VARCHAR2(200)          NOT NULL
);
