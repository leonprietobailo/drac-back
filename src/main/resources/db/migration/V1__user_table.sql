CREATE TABLE USERS(
    cod BIGINT AUTO_INCREMENT PRIMARY KEY,
    email varchar2(100) UNIQUE,
    password varchar2(100),
    first_name varchar2(50),
    last_name varchar2(50),
    birthdate timestamp,
    telephone varchar2(20)
)
