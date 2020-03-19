select * from user;
select substring_index(join_date, ' ', 1) from user;
select * from blog;
select * from category where no=3 and user_id='mickey' and no != (select min(no) from category where user_id = 'mickey');


## user
select id, name from user where id = 'ellisjoe' and pw = password('ellisjoe');
insert into user values('ellisjoe', '조종혁', password('ellisjoe'), now());
update user set name = '관리자', pw = password('admin') where id = 'admin';

## blog
insert into blog values('admin', 'admin 님의 블로그', 'default.jpg');
update blog set title = 'admin 님의 블로그', logo = 'default.jpg' where user_id = 'admin';
delete from blog where user_id = 'ellisjoe';
select user_id, title, logo from blog where user_id = 'admin';

## category
insert into category values(4, '카테고리1', 'admin의 카테고리', now(), 'admin');
delete from category where no = 4 and user_id = 'admin';
select no, name, (select count(*) from post where category_no = C.no) as posted, description, reg_date, user_id from category C where C.user_id = 'admin' order by no asc;
select no, name from category where user_id = 'admin' order by no asc;
select min(no) from category where user_id = 'admin';

## post
insert into post values(1, 'post from admin', 'bla bla bla...', now(), 1);
insert into post values(2, 'post from admin', 'bla bla bla...', now(), 1);

# 포스트 제거 전에 계정 인증하기 (계정명)
select user_id from category where no = 1;

# 단일 포스트를 제거하는 경우
delete from post where no = 3;

# 같은 그룹의 여러 포스트를 제거하는 경우 ()
delete from post where category_no = 2;

select no, title, substring_index(reg_date, ' ', 1) as reg_date, category_no from post where category_no = 1 order by no asc;
select no, title, contents, category_no from post where no = 1 and category_no = 1;