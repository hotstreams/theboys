--begin transaction
insert into s265062.users values (1,'login1','password','HERO');
-- No hero with id=5
insert into s265062.user_hero_rates values (5,1,4);