CREATE TABLE ADDRESS
(
    cod         BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_cod    BIGINT REFERENCES USERS           NOT NULL,
    city        varchar2(100)                     NOT NULL,
    province    varchar2(100)                     NOT NULL,
    street      varchar2(100)                     NOT NULL,
    flat        varchar2(100),
    postal_code varchar2(5)                       NOT NULL
);

CREATE TABLE TOTP
(
    cod          BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    email        VARCHAR2(100)                     NOT NULL,
    otp          NUMERIC(4, 0)                     NOT NULL,
    request_date TIMESTAMP                         NOT NULL
);
