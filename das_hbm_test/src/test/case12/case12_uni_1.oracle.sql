DROP TABLE
    CASE12_UNI_1_PERSON CASCADE constraints purge;
CREATE TABLE
    CASE12_UNI_1_PERSON
    (     
        PERSON_NAME VARCHAR2(20) NOT NULL,
        PRIMARY KEY (PERSON_NAME)
    );

DROP TABLE
    CASE12_UNI_1_ADDRESS CASCADE constraints purge;
CREATE TABLE
    CASE12_UNI_1_ADDRESS
    (
        PERSON_NAME VARCHAR2(20) NOT NULL,
        ADDRESS_TYPE VARCHAR2(20),
        STREET VARCHAR2(20),
        PRIMARY KEY (PERSON_NAME,ADDRESS_TYPE)
    );