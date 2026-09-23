-- Q1: 使用已有的 bookings 表，只统计 APPROVED 预订，按 boardroom 分组，显示预订数量至少为2的会议室。
SELECT boardroom, count(*) as booking_count
FROM bookings
WHERE status = 'APPROVED'
GROUP BY boardroom
HAVING booking_count >= 2
ORDER BY booking_count DESC;

-- Q2: 包含所有状态，按 boardroom 分组，返回：boardroom, 预订数量 booking_count, 总参会人数 total_attendees. 只保留预订数量至少2条，并且总参会人数大于30的会议室，按总参会人数降序排列。提示：在 HAVING 中用 AND 连接两个条件。
SELECT boardroom, count(*) as booking_count, sum(attendee_count) as total_attendees
FROM bookings
GROUP BY boardroom
HAVING booking_count >= 2 and total_attendees > 30
ORDER BY total_attendees DESC;