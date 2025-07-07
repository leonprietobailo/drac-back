CREATE TABLE USERS
(
    cod        BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    email      varchar2(100) UNIQUE              NOT NULL,
    password   varchar2(100)                     NOT NULL,
    first_name varchar2(50)                      NOT NULL,
    last_name  varchar2(50)                      NOT NULL,
    birthdate  timestamp                         NOT NULL,
    telephone  varchar2(20),
    newsletter boolean                           NOT NULL
)
