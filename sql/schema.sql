USE master;

CREATE DATABASE neptunpro;

USE neptunpro;

CREATE TABLE _bag (
    id BIGINT IDENTITY(1,1) NOT NULL,
    bkey VARCHAR(255) CHECK (bkey IN ('TOKEN_SECRET')),
    bvalue VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE users (
    id BIGINT IDENTITY(1,1) NOT NULL,
    email VARCHAR(255),
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) CHECK (role IN ('USER','ADMIN')),
    PRIMARY KEY (id)
);

CREATE TABLE course (
    id BIGINT IDENTITY(1,1) NOT NULL,
    teacher_id BIGINT NOT NULL,
    name VARCHAR(255),
    type VARCHAR(255) CHECK (type IN ('LECTURE','LAB')),
    PRIMARY KEY (id),
    FOREIGN KEY (teacher_id) REFERENCES users(id)
);

CREATE TABLE event_source_grade (
    created_at DATETIME2(6),
    created_by BIGINT,
    grade_id BIGINT,
    id BIGINT IDENTITY(1,1) NOT NULL,
    creator VARCHAR(255),
    payload VARCHAR(MAX),
    source VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE event_source_student (
    created_at DATETIME2(6),
    created_by BIGINT,
    id BIGINT IDENTITY(1,1) NOT NULL,
    student_id BIGINT,
    creator VARCHAR(255),
    payload VARCHAR(MAX),
    source VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE program (
    id BIGINT IDENTITY(1,1) NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE student (
    gpa FLOAT(53),
    version SMALLINT,
    created_at DATETIME2(6),
    created_by BIGINT,
    id BIGINT IDENTITY(1,1) NOT NULL,
    modified_at DATETIME2(6),
    modified_by BIGINT,
    program_id BIGINT NOT NULL,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (modified_by) REFERENCES users(id),
    FOREIGN KEY (program_id) REFERENCES program(id)
);


CREATE TABLE grade (
    grade FLOAT(53) NOT NULL,
    version SMALLINT,
    course_id BIGINT NOT NULL,
    created_at DATETIME2(6),
    created_by BIGINT,
    id BIGINT IDENTITY(1,1) NOT NULL,
    modified_at DATETIME2(6),
    modified_by BIGINT,
    student_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (course_id) REFERENCES course(id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (modified_by) REFERENCES users(id),
    FOREIGN KEY (student_id) REFERENCES student(id)
);