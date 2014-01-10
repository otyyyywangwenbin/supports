DROP TABLE
    CASE1_PARENT CASCADE constraints purge;
CREATE TABLE
    CASE1_PARENT
    (
        CASE1_PARENT_ID VARCHAR2(20) NOT NULL,
        CASE1_PARENT_NAME VARCHAR2(20),
        PRIMARY KEY (CASE1_PARENT_ID)
    );
    
DROP TABLE
    CASE1_SUB1 CASCADE constraints purge;
CREATE TABLE
    CASE1_SUB1
    (
        CASE1_PARENT_ID VARCHAR2(20),
        CASE1_SUB1_NAME VARCHAR2(20)
    );

DROP TABLE
    CASE1_SUB2 CASCADE constraints purge;
CREATE TABLE
    CASE1_SUB2
    (
        CASE1_PARENT_ID VARCHAR2(20),
        CASE1_SUB2_NAME VARCHAR2(20)
    );
