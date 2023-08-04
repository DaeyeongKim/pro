create table member (id varchar(50) primary key, pw varchar(100) not null, name varchar(30) not null, phone number(30) null, email varchar(30) null, address varchar(50) null, signUpDay date)

desc member;

drop table member;
drop table board;
drop table save_study;

insert into member (id, pw , name, phone, email, address, signUpDay) values('123','1637056d5f4326d2371ff0e8597dd2772d8ed004dec9d80ded9005fcd3d35c07','테스트',010332123,'ekiek234','도쿄도',sysdate);

select * from board;
select * from member;
select * from note_trans;
select * from save_study;

truncate table save_study;

select id, pw from member where id = 'tkj';



create table board (no number (10), title varchar(50), content varchar(200), id varchar (50) constraint b_number_fk references member, loadday  date);

create sequence board_sequence increment by 1 start with 1;

create table note_trans (id varchar (50) constraint n_id_fk references member, songtitle varchar(30), singer varchar(100),lyrics varchar(100) );

delete sequence seq_board;


nasu0210@naver.com
update board set title = '이강인', content = '축구 잘함' where no = 3;

create table save_study (id varchar (50) constraint s_id_fk references member, savenum number(20), songtitle varchar(30));
SELECT board_sequence.CURRVAL FROM dual;

SELECT * FROM USER_SEQUENCES;  

SELECT board_sequence FROM dual;
SELECT board_sequence.CURRVAL FROM dual;
select savenum, songtitle from save_study where id='tkj';
