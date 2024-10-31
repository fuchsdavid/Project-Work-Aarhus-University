INSERT INTO User (UserName, Name, Surname, Age, Email, Password) VALUES
('john_doe', 'John', 'Doe', 30, 'john_doe@gmail.com', '1234'),
('jane_smith', 'Jane', 'Smith', 28, 'jane_smith@gmail.com', '3333'),
('alice_johnson', 'Alice', 'Johnson', 35, 'alice_johnson@gmail.com', 'oooo');

INSERT INTO HabitCategory (Habit_Cat_Name, Description) VALUES
('Health', 'Habits related to physical health and fitness.'),
('Food', 'Habits to improve eating habits.'),
('Sport', 'Habits focused on sport.');

INSERT INTO Habit (Habit_ID, Name, Description, Habit_Cat_Name) VALUES
(1, 'Run', 'I like running', 'Sport'),
(2, 'Wake up early', 'I am lazy', 'Health'),
(3, 'Drink water', 'I like water', 'Food');

INSERT INTO User_Habit (UserName, Habit_ID) VALUES
('john_doe', 1),
('john_doe', 2),
('jane_smith', 3),
('jane_smith', 1),
('alice_johnson', 2);


INSERT INTO Goal (Goal_ID, Description, Value) VALUES
(1, 'Run 5km', '5km'),
(2, 'Drink 8 cups of water', '8'),
(3, 'Run for 20 minutes', '20min'),
(4, 'Got up early', '0');

INSERT INTO Journal(Journal_ID, UserName, Habit_ID, Goal_ID, Current_Streak, Start_Date, Activity_Type) VALUES
(1,     'john_doe', 1, 1, 0, '2024-10-31', 'km'),
(2,     'john_doe', 2, 4, 0, '2024-12-31', 'bool'),
(3,     'jane_smith', 3, 2, 0, '2024-10-10', 'amount'),
(4,     'jane_smith', 1, 3, 0, '2024-09-10', 'min'),
(5,     'alice_johnson', 2, 4, 0, '2024-09-10', 'bool');

INSERT INTO Entry(Entry_ID, Date, Value, Journal_ID) VALUES
(1, '2008-11-11', "asda", 1)