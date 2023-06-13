insert into users (id,login,password,role) values (1,'login','{bcrypt}$2a$10$gME7VLRHiw4dbjAJEJ9EM.1KA4/5jQwg6gQ6r9fCNJ2n3uPawF/OG', 'VISITOR')
,(2,'scientist1','{bcrypt}$2a$10$B5iA7OUD4P9BrLfpjnatEemyGb.IDxUoEK25d48zCjCDYhzB9uwEe', 'SCIENTIST')
,(3,'scientist2','{bcrypt}$2a$10$X44JqjGlX0hRAtqzr3ZBM.tTPUXb2KmLu8UaZNZjzdtAEr/R/MTsK', 'SCIENTIST')
,(4,'manager1','{bcrypt}$2a$10$ppjYRaZVTPQhlspcI7f6uu7YX2S8HaPFFSVyS8W3.Y0jfpje/m8ie', 'MANAGER')
,(5,'manager2','{bcrypt}$2a$10$WHJNOA9.XmARvzF4imWyoumSk4aRQIZXzVbDmPuGTgmKRZPMpPCuC', 'MANAGER')
,(6,'manager3','{bcrypt}$2a$10$R3qpKGPYfunTzKItCJmqYOv8AaeXWCUyx0yBzmzxwxnYgMWv.VAC', 'MANAGER')
,(7,'hero','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'HERO')
,(8,'candidate','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'CANDIDATE')
,(28,'customer','{bcrypt}$2a$10$g8jsE/oUtD5q99Zhml1m5u7/v0HtOqB/CBhK63Wv950J8WXmCXywW', 'CUSTOMER')
;

insert into scientists values (2)
, (3)
;

insert into managers values (4)
, (5)
, (6)
;

insert into candidates values ('2000-03-04'::date, 8, 180, 75, 'SPB', 'Some candidate', 'John', 'Doe', '+19483728494', 'white', 'male', 'WAITING', null, null)
;

insert into medicine values (1, 'The V serum')
, (2, 'The V serum II')
, (3, 'The V serum III')
;

insert into skills values (1, 'Controls fire and has the ability to manipulate flames.', 'Fire bending')
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

insert into heroes values (7, 'Controls fire and has the ability to manipulate flames.', 'Blaze', 3.94)
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

insert into heroes_skills values (7, 1)
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

insert into posts values (7, 1, 'Last night a massive battle happened in the center of the New York between aliens and Blaze. As we know for now, there`re 70 of murdered and 137 of injured.', 'Blaze saves the city again!!!')
, (7, 2, 'Last night a massive battle happened in the center of the New York between aliens and Blaze. As we know for now, there`re 70 of murdered and 137 of injured.', 'Blaze saves the city again!!!')
, (7, 3, 'Last night a massive battle happened in the center of the New York between aliens and Blaze. As we know for now, there`re 70 of murdered and 137 of injured.', 'Blaze saves the city again!!!')
;

insert into user_hero_subscriptions values (7, 1)
, ( 8, 1)
, ( 9, 1)
, (10, 1)
;

insert into customers values (28);
insert into orders values (28, '2023-08-08'::date, '2023-08-10'::date, '2023-08-10'::date, null ,7, nextval('orders_seq'), null, 'PENDING')
, (28, '2023-08-09'::date, '2023-08-12'::date, '2023-08-13'::date, null, 8, nextval('orders_seq'), null, 'PENDING')
;
