alter table if exists s265062.assessments drop constraint if exists FKao52jab8qxsufr4xj8ryrsao8;
alter table if exists s265062.candidate_requests drop constraint if exists FK6p18ycejl4iqnp7me3neqw3xo;
alter table if exists s265062.hero_creation_events drop constraint if exists FKp96ch72xasoo0dep874c2y5cx;
alter table if exists s265062.hero_creation_events drop constraint if exists FKnq8b6sy1w3bavuh1cr3beeer9;
alter table if exists s265062.hero_creation_orders drop constraint if exists FK3a2563eo2bu85j2gy7ncsve48;
alter table if exists s265062.heroes_skills drop constraint if exists FKfb86n50ehxp2jpxbw56w95ap0;
alter table if exists s265062.heroes_skills drop constraint if exists FKr2toc6rbynlu9wwbvy889rvmi;
alter table if exists s265062.orders drop constraint if exists FKpxtb8awmi0dk6smoh2vp1litg;
alter table if exists s265062.orders drop constraint if exists FKp69uui117r5ncrp0phuo05vwk;
alter table if exists s265062.orders_skills drop constraint if exists FKqm4qlmra4c1xj15llg1qmwtg8;
alter table if exists s265062.orders_skills drop constraint if exists FK9c5ybtl5jgnlf5hv2u4pjvrnq;
alter table if exists s265062.researches drop constraint if exists FKcqly4u6kr1upmxlecf9yuhp1c;
alter table if exists s265062.researches drop constraint if exists FKej18i7rsj9hndfy5efpobcgyn;
alter table if exists s265062.researches drop constraint if exists FKree188qn5e0xgjitvgc5qq9em;
alter table if exists s265062.tests drop constraint if exists FKg05yiq1me9mpxkh3pvclrhnok;
alter table if exists s265062.user_hero_subscriptions drop constraint if exists FKf6ijummhnbjdijc3pw3m10b18;
alter table if exists s265062.user_hero_subscriptions drop constraint if exists FKpe600ijjtefi75jxbicpjtxpr;
drop table if exists s265062.assessments cascade;
drop table if exists s265062.candidate_requests cascade;
drop table if exists s265062.candidates cascade;
drop table if exists s265062.customers cascade;
drop table if exists s265062.hero_creation_events cascade;
drop table if exists s265062.hero_creation_orders cascade;
drop table if exists s265062.heroes cascade;
drop table if exists s265062.heroes_skills cascade;
drop table if exists s265062.managers cascade;
drop table if exists s265062.medicine cascade;
drop table if exists s265062.orders cascade;
drop table if exists s265062.orders_skills cascade;
drop table if exists s265062.posts cascade;
drop table if exists s265062.researches cascade;
drop table if exists s265062.scientists cascade;
drop table if exists s265062.skills cascade;
drop table if exists s265062.tests cascade;
drop table if exists s265062.user_hero_subscriptions cascade;
drop table if exists s265062.user_hero_rates cascade;
drop table if exists s265062.users cascade;
create table s265062.assessments (event_date date, passed boolean, request_date date not null, research_id integer not null, test_id serial not null, comment varchar(255), name varchar(255) not null, primary key (test_id));
create table s265062.candidate_requests (birthday date not null, candidate_order_id serial not null, height integer not null, scientist_id integer, weight integer not null, description varchar(255), race varchar(255), sex varchar(255), primary key (candidate_order_id));
create table s265062.candidates (birthday date not null, candidate_id integer not null, height integer not null, weight integer not null, address varchar(255), description varchar(255), first_name varchar(255) not null, last_name varchar(255) not null, phone varchar(255), race varchar(255), sex varchar(255), status varchar(255) check (status in ('IN_PROGRESS','WAITING','BECAME_HERO','DIED')), medical_doc bytea, photo bytea, primary key (candidate_id));
create table s265062.customers (customer_id integer not null, primary key (customer_id));
create table s265062.hero_creation_events (candidate_id integer, date date not null, hero_creation_event_id serial not null, scientist_id integer, result varchar(255) not null, primary key (hero_creation_event_id));
create table s265062.hero_creation_orders (hero_creation_order_id serial not null, manager_id integer, primary key (hero_creation_order_id));
create table s265062.heroes (hero_id integer not null, description varchar(255), name varchar(255) not null unique, rating double precision, primary key (hero_id));
create table s265062.heroes_skills (hero_id integer not null, skill_id integer not null, primary key (hero_id, skill_id));
create table s265062.managers (manager_id integer not null, primary key (manager_id));
create table s265062.medicine (medicine_id serial not null, name varchar(255) not null unique, primary key (medicine_id));
create table s265062.orders (customer_id integer not null, date date, start_date date, end_date date, request_description varchar(255), hero_id integer, order_id serial not null, hero_description varchar(255), status varchar(255) not null check (status in ('PENDING','IN_PROGRESS','WAITING_FOR_CUSTOMER_APPROVAL','FULFILLED','DECLINED')), primary key (order_id));
create table s265062.orders_skills (order_id integer not null, skill_id integer not null, primary key (order_id, skill_id));
create table s265062.posts (hero_id integer not null, post_id serial not null, description varchar(255), title varchar(255) not null, creation_time timestamp with time zone not null, primary key (post_id));
create table s265062.researches (candidate_id integer unique, doze_count integer not null, doze_ml integer not null, exam_passed boolean, medicine_id integer, research_id serial not null, scientist_id integer not null, result varchar(255), status varchar(255) not null check (status in ('INITIALIZED','CANDIDATE_FOUND','WAITING_FOR_INJECTION','WAITING_FOR_TESTS','WAITING_FOR_ASSESSMENT','WAITING_FOR_EXAM','CANDIDATE_BECAME_HERO','CANDIDATE_DIED_AT_INJECTION','CANDIDATE_DIED_AT_ASSESSMENT','CANDIDATE_DIED_AT_EXAM')), primary key (research_id));
create table s265062.scientists (scientist_id integer not null, primary key (scientist_id));
create table s265062.skills (skill_id serial not null, description varchar(255), name varchar(255) not null unique, primary key (skill_id));
create table s265062.tests (event_date date, request_date date not null, research_id integer not null, test_id serial not null, name varchar(255) not null, result varchar(255), primary key (test_id));
create table s265062.user_hero_subscriptions (hero_id integer not null, user_id integer not null);
create table s265062.user_hero_rates (hero_id integer not null, user_id integer not null, rate integer not null check (rate < 6 and rate > 0), primary key (hero_id, user_id));
create table s265062.users (id serial, login varchar(255) not null unique, password varchar(255) not null, role varchar(255) not null check (role in ('VISITOR','SCIENTIST','MANAGER','HERO','CANDIDATE','CUSTOMER')), primary key (id));
alter table if exists s265062.assessments add constraint FKao52jab8qxsufr4xj8ryrsao8 foreign key (research_id) references s265062.researches;
alter table if exists s265062.candidate_requests add constraint FK6p18ycejl4iqnp7me3neqw3xo foreign key (scientist_id) references s265062.scientists;
alter table if exists s265062.hero_creation_events add constraint FKp96ch72xasoo0dep874c2y5cx foreign key (candidate_id) references s265062.candidates;
alter table if exists s265062.hero_creation_events add constraint FKnq8b6sy1w3bavuh1cr3beeer9 foreign key (scientist_id) references s265062.scientists;
alter table if exists s265062.hero_creation_orders add constraint FK3a2563eo2bu85j2gy7ncsve48 foreign key (manager_id) references s265062.managers;
alter table if exists s265062.heroes_skills add constraint FKfb86n50ehxp2jpxbw56w95ap0 foreign key (skill_id) references s265062.skills;
alter table if exists s265062.heroes_skills add constraint FKr2toc6rbynlu9wwbvy889rvmi foreign key (hero_id) references s265062.heroes;
alter table if exists s265062.orders add constraint FKpxtb8awmi0dk6smoh2vp1litg foreign key (customer_id) references s265062.customers;
alter table if exists s265062.orders add constraint FKp69uui117r5ncrp0phuo05vwk foreign key (hero_id) references s265062.heroes;
alter table if exists s265062.orders_skills add constraint FKqm4qlmra4c1xj15llg1qmwtg8 foreign key (skill_id) references s265062.skills;
alter table if exists s265062.orders_skills add constraint FK9c5ybtl5jgnlf5hv2u4pjvrnq foreign key (order_id) references s265062.hero_creation_orders;
alter table if exists s265062.researches add constraint FKcqly4u6kr1upmxlecf9yuhp1c foreign key (candidate_id) references s265062.candidates;
alter table if exists s265062.researches add constraint FKej18i7rsj9hndfy5efpobcgyn foreign key (medicine_id) references s265062.medicine;
alter table if exists s265062.researches add constraint FKree188qn5e0xgjitvgc5qq9em foreign key (scientist_id) references s265062.scientists;
alter table if exists s265062.tests add constraint FKg05yiq1me9mpxkh3pvclrhnok foreign key (research_id) references s265062.researches;
alter table if exists s265062.user_hero_subscriptions add constraint FKf6ijummhnbjdijc3pw3m10b18 foreign key (hero_id) references s265062.heroes;
alter table if exists s265062.user_hero_subscriptions add constraint FKpe600ijjtefi75jxbicpjtxpr foreign key (user_id) references s265062.users;
alter table if exists s265062.user_hero_rates add constraint FKf6ijummhnbjdijc3p3rt10b18 foreign key (hero_id) references s265062.heroes;
alter table if exists s265062.user_hero_rates add constraint FKpe600ijjtefi75jxb3rtjtxpr foreign key (user_id) references s265062.users;

insert into users (login,password,role) values ('login','{bcrypt}$2a$10$gME7VLRHiw4dbjAJEJ9EM.1KA4/5jQwg6gQ6r9fCNJ2n3uPawF/OG', 'VISITOR')
                                                                                                          ,('scientist1','{bcrypt}$2a$10$B5iA7OUD4P9BrLfpjnatEemyGb.IDxUoEK25d48zCjCDYhzB9uwEe', 'SCIENTIST')
                                                                                                          ,('scientist2','{bcrypt}$2a$10$X44JqjGlX0hRAtqzr3ZBM.tTPUXb2KmLu8UaZNZjzdtAEr/R/MTsK', 'SCIENTIST')
                                                                                                          ,('manager1','{bcrypt}$2a$10$ppjYRaZVTPQhlspcI7f6uu7YX2S8HaPFFSVyS8W3.Y0jfpje/m8ie', 'MANAGER')
                                                                                                          ,('manager2','{bcrypt}$2a$10$WHJNOA9.XmARvzF4imWyoumSk4aRQIZXzVbDmPuGTgmKRZPMpPCuC', 'MANAGER')
                                                                                                          ,('manager3','{bcrypt}$2a$10$R3qpKGPYfunTzKItCJmqYOv8AaeXWCUyx0yBzmzxwxnYgMWv.VAC', 'MANAGER')
                                                                                                          ,('hero1','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'HERO')
                                                                                                          ,('hero2','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'HERO')
                                                                                                          ,('hero3','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'HERO')
                                                                                                          ,('hero4','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'HERO')
                                                                                                          ,('hero5','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'HERO')
                                                                                                          ,('hero6','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'HERO')
                                                                                                          ,('hero7','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'HERO')
                                                                                                          ,('hero8','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'HERO')
                                                                                                          ,('hero9','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'HERO')
                                                                                                          ,('hero10','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'HERO')
                                                                                                          ,('candidate','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'CANDIDATE')
                                                                                                          ,('customer','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'CUSTOMER')
                                                                                                          ;

insert into s265062.scientists values (2)
, (3)
;

insert into s265062.managers values (4)
, (5)
, (6)
;

insert into s265062.candidates values ('2000-03-04'::date, 17, 180, 75, 'SPB', 'Some candidate', 'John', 'Doe', '+19483728494', 'white', 'male', 'WAITING', null, null)
;

insert into s265062.medicine values (1, 'The V serum')
, (2, 'The V serum II')
, (3, 'The V serum III')
;

insert into s265062.skills values (1, 'Controls fire and has the ability to manipulate flames.', 'Fire bending')
,(2, 'Ability to move objects with the power of mind.', 'Telekinesis')
,(3, 'Can turn invisible and pass through solid objects.', 'Phantomness')
,(4, 'Controls electricity and can discharge powerful electrical blasts.', 'Electricity')
,(5, 'Moves at incredible speeds, allowing for lightning-fast reflexes.', 'Speedforce')
,(6, 'Harnesses cosmic energy and possesses superhuman strength and flight.', 'Space energy')
,(7, 'Controls ice and can create freezing temperatures.', 'Ice bending')
,(8, 'Possesses mind control and telepathic abilities.', 'Mind bending')
,(9, 'Can fly and has the power of healing and purification.', 'Holiness')
,(10, 'Has the ability to bind and manipulate souls.', 'Soul bending')
;

insert into s265062.heroes values (7, 'Controls fire and has the ability to manipulate flames.', 'Blaze', 3.94)
,(8, 'Ability to move objects with the power of mind.', 'Kinetic', 1.22)
,(9, 'Can turn invisible and pass through solid objects.', 'Phantom', 2.55)
,(10, 'Controls electricity and can discharge powerful electrical blasts.', 'Volt', 4.69)
,(11, 'Moves at incredible speeds, allowing for lightning-fast reflexes.', 'Quicksilver', 4.85)
,(12, 'Harnesses cosmic energy and possesses superhuman strength and flight.', 'Nova', 2.02)
,(13, 'Controls ice and can create freezing temperatures.', 'Frostbite', 2.56)
,(14, 'Possesses mind control and telepathic abilities.', 'Mind Bender', 4.32)
,(15, 'Can fly and has the power of healing and purification.', 'Seraph', 4.63)
,(16, 'Has the ability to bind and manipulate souls.', 'Warlock', 4.72)
;

insert into s265062.heroes_skills values (7, 1)
,(8, 2)
,(9, 3)
,(10, 4)
,(11, 5)
,(12, 6)
,(13, 7)
,(14, 8)
,(15, 9)
,(16, 10)
;

insert into s265062.posts values (7, 1, 'Last night a massive battle happened in the center of the New York between aliens and Blaze. As we know for now, there`re 70 of murdered and 137 of injured.', 'Blaze saves the city again!!!', '2004-10-18 10:23:54+02')
, (7, 2, 'Last night a massive battle happened in the center of the New York between aliens and Blaze. As we know for now, there`re 70 of murdered and 137 of injured.', 'Blaze saves the city again!!!', '2004-10-19 10:23:54+02')
, (7, 3, 'Last night a massive battle happened in the center of the New York between aliens and Blaze. As we know for now, there`re 70 of murdered and 137 of injured.', 'Blaze saves the city again!!!', '2004-10-20 10:23:54+02')
;

insert into s265062.user_hero_subscriptions values (7, 1)
, ( 8, 1)
, ( 9, 1)
, (10, 1)
;

insert into s265062.customers values (18);
--insert into s265062.orders values (500000, 18, '2023-08-08'::date, '2023-08-09'::date, '2023-08-10'::date, 7, 1, null, 'PENDING')
--, (500000, 18, '2023-08-09'::date,'2023-08-10'::date,'2023-08-12'::date, 8, 2, null, 'PENDING')
--;

insert into s265062.user_hero_rates values (7, 1, 5)
,(7, 2, 4)
,(7, 3, 5)
,(7, 4, 2)
;