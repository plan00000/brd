-- 查询语句
select r.user_id,sum(r.have_brokerage),r.relate from record_brokerage r where r.user_id = 6 and r.relate = 1 group by r.user_id;

-- 联合查询
select u.id,u.username, s.user_id ,s.brokerage , s.relate from user u ,(select r.user_id user_id ,sum(r.have_brokerage) brokerage ,r.relate relate from record_brokerage r where r.user_id =6 and r.relate = 1 group by r.user_id) s where u.id = s.user_id;

select u.id,u.username,s.user_id,s.brokerage,s.relate from user u left join user_info_both ub on u.user_info_both_id = ub.id  left join (select r.user_id user_id,sum(r.have_brokerage) brokerage ,r.relate relate from record_brokerage r where r.user_id = 6 and r.relate =1 group by r.gain_user_id) s on u.id = s.user_id where u.state = 1 and ub.parentid = 6;