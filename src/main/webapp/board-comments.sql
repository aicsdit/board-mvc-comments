-- 데이터베이스 생성 : jinsookdb는 데이터베이스명
CREATE DATABASE jinsookdb;

-- 사용자 생성
CREATE USER jinsook@localhost IDENTIFIED BY '1111';

-- 사용자의 db에 대한 권한 부여
GRANT ALL privileges ON jinsookdb.* TO jinsook@localhost WITH GRANT OPTION;

-- 권한 갱신
FLUSH PRIVILEGES;

-- 데이터베이스 조회
SHOW DATABASES;

-- board 테이블
CREATE TABLE BOARD(
    BCODE INT AUTO_INCREMENT PRIMARY KEY,
    SUBJECT VARCHAR(100),
    CONTENT TEXT,
    WRITER VARCHAR(50),
    REGDATE DATE
);

COMMIT;

ALTER TABLE board
  ADD FILENAME VARCHAR(50)
    AFTER REGDATE;

-- 한줄 댓글 저장 테이블
CREATE TABLE comments(
    num INT AUTO_INCREMENT PRIMARY KEY,
    BCODE INT,
	  CCODE INT,
    CONTENT VARCHAR(100),
    REGDATE DATE
);

DROP TABLE comments;

INSERT INTO comments(BCODE, CCODE, CONTENT, REGDATE) VALUES(90, 1, '안녕1', NOW());
INSERT INTO comments(BCODE, CCODE, CONTENT, REGDATE) VALUES(90, 2, '안녕2', NOW());
INSERT INTO comments(BCODE, CCODE, CONTENT, REGDATE) VALUES(90, 3, '안녕3', NOW());

    

-- 저장 프로시저 정의

DELIMITER $$ 
CREATE OR REPLACE PROCEDURE proc_comments(
	IN _bcode INT,
	IN _content VARCHAR(100)
)
BEGIN 
	DECLARE max_ccode INT;
	
	SELECT MAX(ccode) into max_ccode FROM comments WHERE bcode = _bcode;
	IF max_ccode IS NULL THEN 
		INSERT INTO comments(bcode, ccode, content, regdate) VALUES(_bcode,  1, _content, NOW());
	ELSE 
		INSERT INTO comments(bcode, ccode, content, regdate) VALUES(_bcode,  max_ccode+1, _content, NOW());
	END IF;
END $$
DELIMITER; -- 구분기호를 다시 세미콜론으로 변경하기 위해 사용 proc_comments

-- 저장 프로시저 호출
CALL proc_comments(107, '반갑습니다!!!');
   
-- 저장 프로시저 상태 확인
SHOW  PROCEDURE  STATUS;