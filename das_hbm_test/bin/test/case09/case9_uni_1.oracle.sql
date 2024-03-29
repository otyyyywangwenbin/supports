DROP TABLE
    CASE9_UNI_1_ONE CASCADE constraints purge;
CREATE TABLE
    CASE9_UNI_1_ONE
    (     
        CASE9_UNI_1_ONE_ID VARCHAR2(20) NOT NULL,
        CASE9_UNI_1_ONE_NAME VARCHAR2(20),
        PRIMARY KEY (CASE9_UNI_1_ONE_ID)
    );

DROP TABLE
    CASE9_UNI_1_MANY CASCADE constraints purge;
CREATE TABLE
    CASE9_UNI_1_MANY
    (
        CASE9_UNI_1_MANY_ID VARCHAR2(20) NOT NULL,
        CASE9_UNI_1_MANY_NAME VARCHAR2(20),
        CASE9_UNI_1_ONE_ID VARCHAR2(20),
        PRIMARY KEY (CASE9_UNI_1_MANY_ID)
    );
