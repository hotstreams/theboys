--begin transaction
insert into theboys.users values (1,'login1','password','HERO');
-- No hero with id=5
insert into theboys.user_hero_rates values (5,1,4);