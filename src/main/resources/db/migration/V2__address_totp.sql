CREATE TABLE ADDRESS
(
    cod         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_cod    BIGINT REFERENCES USERS,
    city        varchar2(100),
    province    varchar2(100),
    street      varchar2(100),
    flat        varchar2(100),
    postal_code NUMERIC(5, 0)
);

CREATE TABLE TOTP
(
    cod          BIGINT AUTO_INCREMENT PRIMARY KEY,
    email        VARCHAR2(100) UNIQUE,
    otp          NUMERIC(4, 0),
    request_date TIMESTAMP
);
