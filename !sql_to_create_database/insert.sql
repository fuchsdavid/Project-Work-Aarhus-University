INSERT INTO User (UserName, Name, Surname, Age, Email, Password) VALUES
('john_doe', 'John', 'Doe', 30, 'john_doe@gmail.com', '1234'),
('jane_smith', 'Jane', 'Smith', 28, 'jane_smith@gmail.com', '3333'),
('alice_johnson', 'Alice', 'Johnson', 35, 'alice_johnson@gmail.com', 'oooo');

INSERT INTO Habit (HabitID, CurrentStreak, LongestStreak, HabitName, UserName) VALUES
(1, 0, 0, "Running", 'john_doe');


INSERT INTO Entry (EntryID, Date, Value, HabitID) VALUES
(1, '2024-10-31', 1, 1);
