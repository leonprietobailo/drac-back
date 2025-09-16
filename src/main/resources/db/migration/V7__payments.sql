CREATE TABLE PAYMENT_TRANSACTION
(
    COD            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    IDENTIFIER     CHAR(36)                          NOT NULL UNIQUE,
    CREATED_AT     TIMESTAMP                         NOT NULL,
    STATUS         VARCHAR(23)                       NOT NULL,
    USER_COD       BIGINT REFERENCES USERS           NOT NULL,
    payment_id     CHAR(36)                          NULL,
    payment_method VARCHAR(19)                       NULL
);
