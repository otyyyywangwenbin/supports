DROP TABLE
    CASE11_BI_1_ONE CASCADE constraints purge;
CREATE TABLE
    CASE11_BI_1_ONE
    (     
        CASE11_BI_1_ONE_ID VARCHAR2(20) NOT NULL,
        CASE11_BI_1_ONE_NAME VARCHAR2(20),
        PRIMARY KEY (CASE11_BI_1_ONE_ID)
    );

DROP TABLE
    CASE11_BI_1_MANY CASCADE constraints purge;
CREATE TABLE
    CASE11_BI_1_MANY
    (
        CASE11_BI_1_MANY_ID VARCHAR2(20) NOT NULL,
        CASE11_BI_1_MANY_NAME VARCHAR2(20),
        CASE11_BI_1_ONE_ID VARCHAR2(20),
        PRIMARY KEY (CASE11_BI_1_MANY_ID)
    );
