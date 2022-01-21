DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS students_teachers;

CREATE TABLE students (
id bigint(20) NOT NULL AUTO_INCREMENT,
firstName varchar(255) NOT NULL,
lastName varchar(255) NOT NULL,
age int(11) NOT NULL,
email varchar(255) NOT NULL,
studyField varchar(255) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE teachers (
id bigint(20) NOT NULL AUTO_INCREMENT,
firstName varchar(255) NOT NULL,
lastName varchar(255) NOT NULL,
age int(11) NOT NULL,
email varchar(255) NOT NULL,
subject varchar(255) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE students_teachers (
student_id bigint(20) NOT NULL,
teacher_id bigint(20) NOT NULL,
PRIMARY KEY (student_id,teacher_id)
);

