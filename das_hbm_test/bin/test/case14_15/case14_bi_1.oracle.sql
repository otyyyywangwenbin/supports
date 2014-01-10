DROP TABLE
    CASE14_BI_1_ONE CASCADE constraints purge;
CREATE TABLE
    CASE14_BI_1_ONE
    (     
        CASE14_BI_1_ONE_ID VARCHAR2(20) NOT NULL,
        CASE14_BI_1_ONE_NAME VARCHAR2(20),
        PRIMARY KEY (CASE14_BI_1_ONE_ID)
    );

DROP TABLE
    CASE14_BI_1_MANY CASCADE constraints purge;
CREATE TABLE
    CASE14_BI_1_MANY
    (
        CASE14_BI_1_MANY_ID VARCHAR2(20) NOT NULL,
        CASE14_BI_1_MANY_NAME VARCHAR2(20),
        CASE14_BI_1_ONE_ID VARCHAR2(20),
        PRIMARY KEY (CASE14_BI_1_MANY_ID)
    );