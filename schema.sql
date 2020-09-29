--JUSTIN LONG--
--JJL90--
--schema.sql--

-- This file is the schema for the final project for CS1555 --

DROP TABLE USER_ROLE CASCADE CONSTRAINTS;
DROP TABLE USER_ACCOUNT CASCADE CONSTRAINTS;
DROP TABLE OLYMPICS CASCADE CONSTRAINTS;
DROP TABLE SPORT CASCADE CONSTRAINTS;
DROP TABLE PARTICIPANT CASCADE CONSTRAINTS;
DROP TABLE COUNTRY CASCADE CONSTRAINTS;
DROP TABLE TEAM CASCADE CONSTRAINTS;
DROP TABLE TEAM_MEMBER CASCADE CONSTRAINTS;
DROP TABLE VENUE CASCADE CONSTRAINTS;
DROP TABLE MEDAL CASCADE CONSTRAINTS;
DROP TABLE EVENT CASCADE CONSTRAINTS;
DROP TABLE SCOREBOARD CASCADE CONSTRAINTS;
DROP TABLE EVENT_PARTICIPATION CASCADE CONSTRAINTS;

CREATE TABLE USER_ROLE(
    role_id    number not null,
    role_name  varchar2(20) not null,
    CONSTRAINT USER_ROLE_PK PRIMARY KEY (role_id) ,
    CONSTRAINT USER_ROLE_CHECK CHECK(role_name in ('Organizer', 'Coach', 'Guest'))
);

CREATE TABLE USER_ACCOUNT(
    user_id     integer not null,
    username    varchar2(20) not null,
    passkey     varchar2(20) not null,
    role_id integer not null,
    last_login date not null,   
    CONSTRAINT USER_ACCOUNT_PK PRIMARY KEY (user_id),
    CONSTRAINT USER_ROLE_FK FOREIGN KEY (role_id) REFERENCES USER_ROLE(role_id),
    CONSTRAINT USER_ACCOUNT_UN UNIQUE (username)
);

CREATE TABLE OLYMPICS(
    olympic_id      integer not null,
    olympic_num     varchar2(30) not null,
    host_city       varchar2(30) not null,
    opening_date    date not null,
    closing_date    date not null,
    official_website varchar2(50),
    CONSTRAINT OLYMPICS_PK PRIMARY KEY (olympic_id),
    CONSTRAINT OLYMPICS_UN UNIQUE (olympic_num)
);

CREATE TABLE SPORT(
   sport_id     integer not null,
   sport_name   varchar2(30) not null,
   description  varchar2(200),
   dob date not null,
   team_size    integer not null,
   CONSTRAINT SPORT_PK PRIMARY KEY (sport_id)
);

CREATE TABLE PARTICIPANT(
    participant_id  integer not null,
    fname varchar2(30) not null,
    lname varchar2(30) not null,
    nationality varchar2(30) not null,
    birth_place varchar2(30) not null,
    dob date not null,
    CONSTRAINT PARTICIPANT_PK PRIMARY KEY (participant_id)
);

CREATE TABLE COUNTRY(
    country_id integer not null,
    country varchar2(20) not null,
    country_code varchar2(3) not null,
    CONSTRAINT COUNTRY_PK PRIMARY KEY (country_id),
    CONSTRAINT COUNTRY_UN UNIQUE (country_code)
);

CREATE TABLE TEAM(
    team_id integer not null,
    olympic_id integer not null,
    team_name varchar2(50) not null,
    country_id integer not null,
    sport_id integer not null,
    coach_id integer not null,
    CONSTRAINT TEAM_PK PRIMARY KEY(team_id),
    CONSTRAINT OLYMPICS_FK FOREIGN KEY (olympic_id) REFERENCES OLYMPICS(olympic_id) ,
    CONSTRAINT COUNTRY_FK FOREIGN KEY (country_id) REFERENCES COUNTRY(country_id) ,
    CONSTRAINT SPORT_FK FOREIGN KEY (sport_id) REFERENCES SPORT(sport_id) ,
    CONSTRAINT COACH_FK FOREIGN KEY (coach_id) REFERENCES PARTICIPANT(participant_id)
);

CREATE TABLE TEAM_MEMBER(
    team_id integer not null,
    participant_id integer not null,
    CONSTRAINT TEAM_MEMBER_PK PRIMARY KEY (team_id, participant_id),
    CONSTRAINT TEAM_MEMBER_FK FOREIGN KEY (team_id) REFERENCES TEAM(team_id) ,
    CONSTRAINT PARTICIPANT_FK FOREIGN KEY (participant_id) REFERENCES PARTICIPANT(participant_id)
);

CREATE TABLE VENUE(
    venue_id integer not null,
    olympic_id integer not null,
    venue_name varchar2(30) not null,
    capacity integer not null,
    CONSTRAINT VENUE_PK PRIMARY KEY (venue_id),
    CONSTRAINT OLYMPIC_VENUE_FK FOREIGN KEY (olympic_id) REFERENCES OLYMPICS(olympic_id) 
);

CREATE TABLE MEDAL(
    medal_id integer not null,
    medal_title varchar2(6) not null,
    points integer not null,
    CONSTRAINT MEDAL_PK PRIMARY KEY (medal_id)
);

CREATE TABLE EVENT(
    event_id integer not null,
    sport_id integer not null,
    venue_id integer not null,
    gender varchar2(2) not null,
    event_time date not null,
    CONSTRAINT EVENT_PK PRIMARY KEY (event_id),
    CONSTRAINT SPORT_EVENT_FK FOREIGN KEY (sport_id) REFERENCES SPORT(sport_id) ,
    CONSTRAINT VENUE_EVENT_FK FOREIGN KEY (event_id) REFERENCES EVENT(event_id) ,
    CONSTRAINT EVENT_CONSTRAINT_CHECK CHECK(gender in ('M', 'F'))
);

CREATE TABLE SCOREBOARD(
    olympic_id integer not null,
    event_id integer not null,
    team_id integer not null,
    participant_id integer not null,
    position integer,
    medal_id integer,
    CONSTRAINT SCOREBOARD_PK PRIMARY KEY (olympic_id, event_id, participant_id, team_id),
    CONSTRAINT OLYMPIC_SCOREBOARD_FK FOREIGN KEY (olympic_id) REFERENCES OLYMPICS(olympic_id) ,
    CONSTRAINT EVENT_SSCOREBOARD_FK FOREIGN KEY (event_id) REFERENCES EVENT(event_id),
    CONSTRAINT TEAM_SCOREBOARD_FK FOREIGN KEY (team_id) REFERENCES TEAM(team_id) ,
    CONSTRAINT PARTICIPANT_SCOREBOARD_FK FOREIGN KEY (participant_id) REFERENCES PARTICIPANT(participant_id),
    CONSTRAINT MEDAL_SCOREBOARD_FK FOREIGN KEY (medal_id) REFERENCES MEDAL(medal_id) 
);

CREATE TABLE EVENT_PARTICIPATION(
    event_id integer not null,
    team_id integer not null,
    status char not null,
    CONSTRAINT EVENT_PARTICIPATION_PK PRIMARY KEY (event_id, team_id),
    CONSTRAINT EVENT_PARTICIPATION_FK FOREIGN KEY (event_id) REFERENCES EVENT(event_id) ,
    CONSTRAINT EVENT_TEAM_FK FOREIGN KEY (team_id) REFERENCES TEAM(team_id),
    CONSTRAINT EVENT_PARTICIPATION_CHECK CHECK(status in ('e', 'n'))
    
);

DROP SEQUENCE USER_ROLE_SEQUENCE;
CREATE SEQUENCE USER_ROLE_SEQUENCE start with 1 INCREMENT BY 1;
DROP SEQUENCE EVENT_SEQUENCE;
CREATE SEQUENCE EVENT_SEQUENCE start with 1 INCREMENT BY 1;
DROP SEQUENCE TEAM_SEQUENCE;
CREATE SEQUENCE TEAM_SEQUENCE start with 1 INCREMENT BY 1;
DROP SEQUENCE PARTICIPANT_SEQUENCE;
CREATE SEQUENCE PARTICIPANT_SEQUENCE start with 1 INCREMENT BY 1;



