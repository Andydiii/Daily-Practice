-- Write today's SQL answers below.
-- 1. 1. 查询所有 booking 显示全部 columns 和全部 records。
select *
from bookings;

/*
2. 查询所有 Pending booking
要求：
- 只返回 customer_name、booking_date、boardroom
- 只查询 PENDING
- 按 booking_date 从早到晚排列
*/
select customer_name, booking_date, boardroom
from bookings
where status = 'PENDING'
ORDER BY booking_date asc;

/*
3. 查询符合条件的 Maple booking
查询：boardroom是 Maple
并且 attendee_count >= 8
*/
SELECT customer_name, attendee_count
FROM bookings
where boardroom = 'Maple' and attendee_count >= 8;

/*
4. 统计 booking总数
返回总共有多少条 booking。
预期：8。
*/
select count(*) as 'Number of Bookings'
from bookings;

/*
5. 按 status统计数量
结果类似：
*/
SELECT status, count(*) as 'booking_count'
FROM bookings
GROUP BY status
ORDER BY booking_count DESC;

