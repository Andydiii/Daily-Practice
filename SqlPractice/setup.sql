DROP TABLE IF EXISTS bookings;

CREATE TABLE bookings (
    id INTEGER PRIMARY KEY,
    customer_name TEXT NOT NULL,
    boardroom TEXT NOT NULL,
    booking_date TEXT NOT NULL,
    status TEXT NOT NULL,
    attendee_count INTEGER NOT NULL
);

INSERT INTO bookings (id, customer_name, boardroom, booking_date, status, attendee_count) VALUES
    (1, 'Alice', 'Maple', '2026-09-05', 'APPROVED', 8),
    (2, 'Bob', 'Oak', '2026-09-05', 'PENDING', 14),
    (3, 'Carol', 'Maple', '2026-09-06', 'APPROVED', 5),
    (4, 'David', 'Pine', '2026-09-06', 'DECLINED', 20),
    (5, 'Emma', 'Oak', '2026-09-07', 'PENDING', 12),
    (6, 'Frank', 'Maple', '2026-09-07', 'APPROVED', 18),
    (7, 'Grace', 'Pine', '2026-09-07', 'PENDING', 6),
    (8, 'Henry', 'Oak', '2026-09-08', 'APPROVED', 10);
