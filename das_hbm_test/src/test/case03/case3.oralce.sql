DROP TABLE
    CASE3_SUB1 CASCADE constraints purge;
CREATE TABLE
    CASE3_SUB1
    (     
        CASE3_PARENT_ID VARCHAR2(20) NOT NULL,
        CASE3_PARENT_NAME VARCHAR2(20),
        CASE3_SUB1_NAME VARCHAR2(20),
        PRIMARY KEY (CASE3_PARENT_ID)
    );

DROP TABLE
    CASE3_SUB2 CASCADE constraints purge;
CREATE TABLE
    CASE3_SUB2
    (
        CASE3_PARENT_ID VARCHAR2(20) NOT NULL,
        CASE3_PARENT_NAME VARCHAR2(20),
        CASE3_SUB2_NAME VARCHAR2(20),
        PRIMARY KEY (CASE3_PARENT_ID)
    );
