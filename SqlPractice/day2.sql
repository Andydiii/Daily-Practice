-- First exercise: calculate the total attendee count across all bookings, including every status. Name the result column total_attendees.
SELECT SUM(attendee_count) AS total_attendees
FROM bookings;

-- Next exercise: calculate the average and maximum attendee count for APPROVED bookings only, in one query.
SELECT avg(attendee_count) as avg_attendees, max(attendee_count) as max_attendees
from bookings
where status = 'APPROVED';

-- Next: calculate the total attendee count for each boardroom, including all statuses. return two columns boardroom and total_attenedess. sort by total_attendees
SELECT boardroom, sum(attendee_count) as total_attendees
FROM bookings
GROUP BY boardroom
ORDER BY total_attendees asc;

/*
Next is HAVING, which filters groups based on an aggregate result.

WHERE filters individual rows before grouping.
HAVING filters groups after aggregation.

Your exercise: show only boardrooms whose total attendee count is greater than 30, including all statuses.

Return boardroom and total_attendees, sorted from largest total to smallest.
*/
SELECT boardroom, sum(attendee_count) as total_attendees
FROM bookings
GROUP BY boardroom
HAVING total_attendees > 30
ORDER BY total_attendees DESC;

/*
Next, combine WHERE and HAVING:

For APPROVED bookings only, show boardrooms whose total attendee count exceeds 15.

Return boardroom and total_attendees, sorted from largest total to smallest.

Build on your current query:

Use WHERE before GROUP BY to filter bookings by status.
Use HAVING to filter the resulting totals.

Try it and share your query and result.
*/

SELECT boardroom, sum(attendee_count) as total_attendees
from bookings
where status = 'APPROVED'
GROUP BY boardroom
HAVING total_attendees > 15
ORDER BY total_attendees DESC;
