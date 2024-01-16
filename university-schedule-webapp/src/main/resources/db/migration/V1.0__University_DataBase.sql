CREATE TABLE IF NOT EXISTS Grades (
id SERIAL PRIMARY KEY,
grade VARCHAR(5) NOT NULL
);

CREATE TABLE IF NOT EXISTS Roles (
id SERIAL PRIMARY KEY, 
roleName TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Users (
id SERIAL PRIMARY KEY,
login TEXT NOT NULL,
password TEXT NOT NULL,
name TEXT NOT NULL,
lastName TEXT NOT NULL,
roleId INT,
gradeId INT,
FOREIGN KEY (gradeId) REFERENCES Grades (id),
FOREIGN KEY (roleId) REFERENCES Roles(id)
);

CREATE TABLE IF NOT EXISTS Subjects (
id INT PRIMARY KEY,
name TEXT NOT NULL,
description TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Auditoriums (
id INT PRIMARY KEY,
name VARCHAR(5) NOT NULL
);

CREATE TABLE IF NOT EXISTS Enrollments (
userId INT,
FOREIGN KEY (userId) REFERENCES Users (id) ON DELETE CASCADE,
subjectId INT,
FOREIGN KEY(subjectId) REFERENCES Subjects (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Lessons (
id SERIAL PRIMARY KEY,
subjectId INT NOT NULL,
FOREIGN KEY (subjectId) REFERENCES Subjects (id),
teacherId INT NOT NULL,
FOREIGN KEY (teacherId) REFERENCES Users (id),
time TIMESTAMP NOT NULL,
auditoriumId INT NOT NULL,
FOREIGN KEY (auditoriumId) REFERENCES Auditoriums (id),
gradeId INT NOT NULL,
FOREIGN KEY (gradeId) REFERENCES Grades (id)
);