DROP TABLE
    CASE2 CASCADE constraints purge;
CREATE TABLE
    CASE2
    (
        CASE2_ID VARCHAR2(20) NOT NULL,
        CASE2_NAME VARCHAR2(20),
        CASE2_SUB_TYPE VARCHAR2(20),
        CASE2_SUB1_NAME VARCHAR2(20),
        CASE2_SUB2_NAME VARCHAR2(20),
        PRIMARY KEY (CASE2_ID)
    );