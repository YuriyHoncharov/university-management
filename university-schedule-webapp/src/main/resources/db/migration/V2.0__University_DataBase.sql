CREATE TABLE IF NOT EXISTS Houses (
id SERIAL PRIMARY KEY,
house VARCHAR(15) NOT NULL
);

CREATE TABLE IF NOT EXISTS Roles (
id SERIAL PRIMARY KEY, 
roleName TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Wizards (
id SERIAL PRIMARY KEY,
login TEXT NOT NULL,
password TEXT NOT NULL,
name TEXT NOT NULL,
lastName TEXT NOT NULL,
roleId INT,
houseId INT,
FOREIGN KEY (houseId) REFERENCES Houses (id) ON DELETE CASCADE,
FOREIGN KEY (roleId) REFERENCES Roles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Subjects (
id SERIAL PRIMARY KEY,
name TEXT NOT NULL,
description TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Auditoriums (
id INT PRIMARY KEY,
name VARCHAR(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS Enrollments (
userId INT,
FOREIGN KEY (userId) REFERENCES Wizards (id) ON DELETE CASCADE,
subjectId INT,
FOREIGN KEY(subjectId) REFERENCES Subjects (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Lessons (
id SERIAL PRIMARY KEY,
subjectId INT NOT NULL,
FOREIGN KEY (subjectId) REFERENCES Subjects (id),
teacherId INT NOT NULL,
FOREIGN KEY (teacherId) REFERENCES Wizards (id),
time TIMESTAMP NOT NULL,
auditoriumId INT NOT NULL,
FOREIGN KEY (auditoriumId) REFERENCES Auditoriums (id),
houseId INT NOT NULL,
FOREIGN KEY (houseId) REFERENCES Houses (id) ON DELETE CASCADE
);
