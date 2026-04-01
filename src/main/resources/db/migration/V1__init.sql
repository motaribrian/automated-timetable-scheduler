-- 1. Base Tables (No Foreign Keys)
CREATE TABLE USERS (
                       ID BIGINT NOT NULL,
                       EMAIL VARCHAR(255),
                       NAME VARCHAR(255),
                       PASSWORD VARCHAR(255),
                       PRIMARY KEY (ID)
);

CREATE TABLE FACULTY (
                         ID BIGINT NOT NULL,
                         IS_AVAILABLE BOOLEAN NOT NULL,
                         MAX_HOURS_PER_DAY INTEGER NOT NULL,
                         PRIMARY KEY (ID)
);

CREATE TABLE COURSES (
                         ID BIGINT NOT NULL,
                         COURSE_CODE VARCHAR(255),
                         COURSE_TYPE VARCHAR(255),
                         CREDITS INTEGER NOT NULL,
                         HOURS_PER_WEEK INTEGER NOT NULL,
                         IS_MINOR BOOLEAN NOT NULL,
                         LECTURE_HOURS INTEGER NOT NULL,
                         NAME VARCHAR(255),
                         PRACTICAL_HOURS INTEGER NOT NULL,
                         THEORY_HOURS INTEGER NOT NULL,
                         PRIMARY KEY (ID)
);

CREATE TABLE ROOMS (
                       ID BIGINT NOT NULL,
                       CAPACITY INTEGER NOT NULL,
                       IS_AVAILABLE BOOLEAN NOT NULL,
                       ROOM_NUMBER VARCHAR(255),
                       ROOM_TYPE VARCHAR(255),
                       PRIMARY KEY (ID)
);

CREATE TABLE STUDENT_BATCH (
                               ID BIGINT NOT NULL,
                               BATCH_NAME VARCHAR(255),
                               STRENGTH INTEGER NOT NULL,
                               STUDENT_BATCH_YEAR INTEGER NOT NULL,
                               PRIMARY KEY (ID)
);

CREATE TABLE TIME_SLOT (
                           TIMESLOT_ID BIGINT NOT NULL,
                           DAY_OF_WEEK VARCHAR(255),
                           START_TIME TIME,
                           END_TIME TIME,
                           SLOT_TYPE VARCHAR(255),
                           PRIMARY KEY (TIMESLOT_ID)
);

-- 2. Join and Relationship Tables
CREATE TABLE COURSE_LECTURE_ROOMIDS (
                                        COURSE_ID BIGINT NOT NULL,
                                        LECTURE_ROOMIDS BIGINT
);

CREATE TABLE COURSES_ELIGIBLE_FACULTY (
                                          COURSE_ID BIGINT NOT NULL,
                                          ELIGIBLE_FACULTY_ID BIGINT NOT NULL
);

CREATE TABLE FACULTY_PREFERRED_SLOTS (
                                         FACULTY_ID BIGINT NOT NULL,
                                         TIMESLOT_ID BIGINT NOT NULL
);

CREATE TABLE FACULTY_SUBJECTS (
                                  FACULTY_ID BIGINT NOT NULL,
                                  SUBJECT_NAME VARCHAR(255)
);

CREATE TABLE STUDENTBATCH_COURSE (
                                     STUDENT_BATCH_ID BIGINT NOT NULL,
                                     COURSES_ID BIGINT NOT NULL
);

CREATE TABLE STUDENT_BATCH_LECTURE_ROOMIDS (
                                               STUDENT_BATCH_ID BIGINT NOT NULL,
                                               LECTURE_ROOMIDS BIGINT
);

CREATE TABLE STUDENT_BATCH_PRACTICAL_ROOMIDS (
                                                 STUDENT_BATCH_ID BIGINT NOT NULL,
                                                 PRACTICAL_ROOMIDS BIGINT
);

CREATE TABLE TIMETABLE_BATCH_IDS (
                                     TIMETABLE_ID BIGINT NOT NULL,
                                     BATCH_ID INTEGER
);

-- 3. The Central Transaction Table
CREATE TABLE LESSON (
                        LESSON_ID BIGINT NOT NULL,
                        LESSON_TYPE VARCHAR(255),
                        COURSE_ID BIGINT,
                        MINORTIMESLOT_ID BIGINT,
                        ROOM_ID BIGINT,
                        STUDENT_BATCH_ID BIGINT,
                        FACULTY_ID BIGINT,
                        TIMESLOT_ID BIGINT,
                        PRIMARY KEY (LESSON_ID)
);

-- 4. Foreign Key Constraints
ALTER TABLE FACULTY ADD CONSTRAINT FK_FACULTY_USER FOREIGN KEY (ID) REFERENCES USERS(ID);
ALTER TABLE FACULTY_SUBJECTS ADD CONSTRAINT FK_FACULTY_SUBJ FOREIGN KEY (FACULTY_ID) REFERENCES FACULTY(ID);
ALTER TABLE COURSES_ELIGIBLE_FACULTY ADD CONSTRAINT FK_CEF_FACULTY FOREIGN KEY (ELIGIBLE_FACULTY_ID) REFERENCES FACULTY(ID);
ALTER TABLE COURSES_ELIGIBLE_FACULTY ADD CONSTRAINT FK_CEF_COURSE FOREIGN KEY (COURSE_ID) REFERENCES COURSES(ID);
ALTER TABLE LESSON ADD CONSTRAINT FK_LESSON_ROOM FOREIGN KEY (ROOM_ID) REFERENCES ROOMS(ID);
ALTER TABLE LESSON ADD CONSTRAINT FK_LESSON_COURSE FOREIGN KEY (COURSE_ID) REFERENCES COURSES(ID);
ALTER TABLE LESSON ADD CONSTRAINT FK_LESSON_SLOT FOREIGN KEY (TIMESLOT_ID) REFERENCES TIME_SLOT(TIMESLOT_ID);
ALTER TABLE LESSON ADD CONSTRAINT FK_LESSON_BATCH FOREIGN KEY (STUDENT_BATCH_ID) REFERENCES STUDENT_BATCH(ID);
ALTER TABLE LESSON ADD CONSTRAINT FK_LESSON_FACULTY FOREIGN KEY (FACULTY_ID) REFERENCES FACULTY(ID);
ALTER TABLE STUDENTBATCH_COURSE ADD CONSTRAINT FK_SBC_BATCH FOREIGN KEY (STUDENT_BATCH_ID) REFERENCES STUDENT_BATCH(ID);
ALTER TABLE STUDENTBATCH_COURSE ADD CONSTRAINT FK_SBC_COURSE FOREIGN KEY (COURSES_ID) REFERENCES COURSES(ID);