DROP TABLE
    CASE10_UNI_2_ONE_1 CASCADE constraints purge;
CREATE TABLE
    CASE10_UNI_2_ONE_1
    (     
        CASE10_UNI_2_ONE_1_ID VARCHAR2(20) NOT NULL,
        CASE10_UNI_2_ONE_1_NAME VARCHAR2(20),
        PRIMARY KEY (CASE10_UNI_2_ONE_1_ID)
    );

DROP TABLE
    CASE10_UNI_2_ONE_2 CASCADE constraints purge;
CREATE TABLE
    CASE10_UNI_2_ONE_2
    (
        CASE10_UNI_2_ONE_2_ID VARCHAR2(20) NOT NULL,
        CASE10_UNI_2_ONE_2_NAME VARCHAR2(20),
        PRIMARY KEY (CASE10_UNI_2_ONE_2_ID)
    );
