用户表：
create table user(
	id varchar(36) primary key,
	name varchar(20) unique not null,
	password varchar(18) not null,
	age varchar(3),
	sex varchar(2),
	location varchar(50),
	mail varchar(20),
	tags varchar(100),
	portrait varchar(100)
)character set utf8 collate utf8_general_ci;

豆瓣用户表：
create table douban_user(
	m_id varchar(36) primary key,
	m_url varchar(20) not null,
	username varchar(100) not null,
	location varchar(50),
	tags varchar(100),
	score varchar(5),
	portrait varchar(100),
	visithistory varchar(100),
	timestamp varchar(10)
)character set utf8 collate utf8_general_ci;

用户映射表：
create table usermapping(
	id int(11) primary key auto_increment,
	user_id varchar(36) unique not null
)character set utf8 collate utf8_general_ci;

电影表:
create table movie(
	id varchar(36) primary key,
	url varchar(50) unique not null,
	date varchar(50),
	country varchar(50),
	directors varchar(100),
	type varchar(30),
	rate varchar(5),
	name varchar(150),
	actors varchar(400),
	language varchar(100),
	intro text,
	length varchar(100),
	picture varchar(100)
)character set utf8 collate utf8_general_ci;

电影映射表：
create table moviemapping(
	id int(11) primary key auto_increment,
	movie_id varchar(36) unique not null
)character set utf8 collate utf8_general_ci;

历史记录表：
create table history(
	id varchar(36) primary key,
	rate varchar(5) not null,
	date datetime not null,
	comment text,
	user_id varchar(32),
	movie_id varchar(32),
	constraint foreign key(user_id) references user(id),
	constraint foreign key(movie_id) references movie(id)
)character set utf8 collate utf8_general_ci;

推荐表：
create table recommand(
	id varchar(36) primary key,
	rate varchar(10),
	user_id varchar(32),
	movie_id varchar(32),
	constraint foreign key(user_id) references user(id),
	constraint foreign key(movie_id) references movie(id)
)character set utf8 collate utf8_general_ci;