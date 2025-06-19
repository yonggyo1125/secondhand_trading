CREATE TABLE SURVEY_DIABETES (
    seq BIGINT AUTO_INCREMENT,
    memberSeq BIGINT,
    gender ENUM('FEMALE', 'MALE', 'OTHER') DEFAULT 'FEMALE',
    age INT,
    hypertension TINYINT(1),
    heartDisease TINYINT(1),
    smokingHistory ENUM('NO_INFO', 'CURRENT', 'EVER', 'FORMER', 'NEVER', 'NOT_CURRENT') DEFAULT 'NO_INFO',
    FOREIGN KEY(memberSeq) REFERENCES MEMBER(seq),
    PRIMARY KEY(seq)
);