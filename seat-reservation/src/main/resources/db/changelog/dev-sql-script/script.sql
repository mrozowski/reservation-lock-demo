-- Insert test data to trip table
INSERT INTO trip (id, departure, destination, date, price)
VALUES ('WL120323A', 'Warsaw', 'London', '2024-03-12 10:00', 115),
       ('NYP230423B', 'New York', 'Paris', '2024-04-23 19:45', 150),
       ('SFT150523C', 'San Francisco', 'Tokyo', '2024-05-15 07:55', 400),
       ('LD270623D', 'London', 'Dubai', '2024-06-27 15:00', 280),
       ('BS090723E', 'Beijing', 'Sydney', '2024-11-18 9:50', 220),
       ('WJ311823F', 'Warsaw', 'Jakarta', '2024-05-07 9:30', 250),
       ('TO140123G', 'Toronto', 'Osaka', '2025-01-14: 22:25', 490),
       ('BR041223H', 'Berlin', 'Rio de Janeiro', '2024-12-04 1:00', 370),
       ('MXM220323I', 'Mexico City', 'Mumbai', '2024-03-22 16:10', 360),
       ('NYA010423J', 'New York', 'Athens', '2024-04-01 20:25', 340);

-- Insert test data to seat table. Each trip has 66 available seats from 10A till 20F
INSERT INTO seat (trip_id, seat_number, status, lock_expiration_time, lock_session_token)
SELECT trip.id         AS trip_id,
       CONCAT(10 + (seq - 1) % 11,
              CASE
                  WHEN seq <= 11 THEN 'A'
                  WHEN seq <= 22 THEN 'B'
                  WHEN seq <= 33 THEN 'C'
                  WHEN seq <= 44 THEN 'D'
                  WHEN seq <= 55 THEN 'E'
                  ELSE 'F'
                  END) AS seat_number,
       'AVAILABLE'     AS status,
       NULL            AS lock_expiration_time,
       NULL            AS lock_session_token
FROM trip, generate_series(1, 66) seq;


-- Insert test data to reservation table.
INSERT INTO reservation (reference, trip_id, seat_id, customer_name, created_at, payment_status)
VALUES ('ATJT09', 'NYA010423J', 10, 'John Doe', CURRENT_TIMESTAMP, 'CONFIRMED');

-- Update seat to have status RESERVED
UPDATE seat
SET status = 'RESERVED'
WHERE trip_id = 'NYA010423J'
  AND id = 10;