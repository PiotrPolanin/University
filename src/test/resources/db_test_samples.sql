INSERT INTO teachers(firstName, lastName, age, email, subject)
VALUES ('John', 'Butler', '46', 'john_butler@university.com', 'Math'),
('Ann', 'Forest', '32', 'ann_forest@university.com', 'Chemistry'),
('George', 'Frank', '56', 'george_frank@university.com', 'Physics'),
('Ann', 'Grey', '64', 'ann_grey@university.com', 'French Lecturer'),
('Mary', 'McDonald', '28', 'mary_mcdonald@university.com', 'Biologist'),
('Steven', 'Grey', '38', 'steven_grey@university.com', 'English Lecturer');

INSERT INTO students(firstName, lastName, age, email, studyField)
VALUES ('Ann', 'Smith', '18', 'a.smith@gmail.com', 'Economics'),
('Mark', 'Washington', '19', 'mark_washington@yahoo.com', 'Engineering'),
('Josh', 'Bowl', '22', 'joshb@gmail.com', 'Statistics'),
('Cathy', 'Summer', '21', 'csummer@yahoo.com', 'Pharmacy'),
('Mark', 'Stuart', '18', 'mark.stuard@yahoo.com', 'Anthropology'),
('Lisa', 'Smith', '18', 'smithl@gmail.com', 'Chemistry');

INSERT INTO students_teachers(student_id, teacher_id)
VALUES ('1', '1'),
('1', '2'),
('1', '3'),
('1', '4'),
('1', '6'),
('2', '4'),
('2', '5'),
('2', '6'),
('3', '2'),
('3', '3'),
('3', '6'),
('4', '1'),
('4', '5'),
('4', '6'),
('5', '4'),
('5', '5'),
('5', '6'),
('6', '1'),
('6', '2'),
('6', '3'),
('6', '4'),
('6', '5'),
('6', '6');
