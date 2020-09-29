--JUSTIN LONG--
--JJL90--

-- Data for the last four olympics:
--     Beijing, Rio, Paris, London
--     6 events (3 atomic and 3 team events)
--     6 countries (which have won medals)
--     Total of 24 events
--USER ROLE (role id, role name)
INSERT INTO USER_ROLE values(1, 'Organizer');
--USER ACCOUNT (user id, username, passkey, role id, last login)
INSERT INTO USER_ACCOUNT values(USER_ROLE_SEQUENCE.NEXTVAL, 'testusername', 'password', 1, '1-Jan-1964');

--OLYMPIC (olympic id, olympic num, host city, opening date, closing date, official website)
INSERT INTO OLYMPICS  values(1, 'XXV', 'Athens', '13-Aug-2004', '29-Aug-2004', 'https://www.olympic.org/athens-2004');
INSERT INTO OLYMPICS  values(2, 'XXVI', 'Beijing', '8-Aug-2008', '8-Aug-2008', 'https://www.olympic.org/beijing-2008');
INSERT INTO OLYMPICS  values (3,'XXVII','LONDON', '27-Jul-2012', '12-Jul-2012', 'https://www.olympic.org/london-2012');
INSERT INTO OLYMPICS  values (4,'XXVIII', 'RIO', '5-Aug-2016', '21-Aug-2016', 'https://www.olympic.org/rio-2016');
--
----MEDAL (medal id, medal title, points)
INSERT INTO MEDAL values(1, 'Gold', 5);
INSERT INTO MEDAL values(2, 'Silver', 3);
INSERT INTO MEDAL values(3, 'Bronze', 1);
--
--SPORT (sport id, sport name, description, dob, team size)
INSERT INTO SPORT values(1000, 'Test Sport', 'Test Description', '1-Jan-1964', 1);--Test Individual Sport
INSERT INTO SPORT values(1001, 'Test Sport', 'Test Description', '1-Jan-1964', 20); --Test Team Sport
INSERT INTO SPORT values(1, 'Volleyball', 'A team sport in which two teams compete with the primary objective of grounding a ball on the other teams court', '1-Jan-1964', 6);
INSERT INTO SPORT values(2, 'Sport 2', 'two sports', '1-Jan-1964', 6);
INSERT INTO SPORT values(3, 'Sport 3', 'two sports', '1-Jan-1964', 6);
--
--VENUE (venue id, olympic id, venue name, capacity)
INSERT INTO VENUE values(1, 1, 'Pace and Friendship Stadium', 2);
INSERT INTO VENUE values(2, 1, 'Pace and Friendship Stadium', 2000
);
--
--EVENT (event id, sport id, venue id, gender, event time)
INSERT INTO EVENT values(EVENT_SEQUENCE.NEXTVAL, 1, 1, 'F', '14-Aug-2004');
INSERT INTO EVENT values(EVENT_SEQUENCE.NEXTVAL, 2, 1, 'F', '14-Aug-2004');
INSERT INTO EVENT values(EVENT_SEQUENCE.NEXTVAL, 3, 1, 'F', '14-Aug-2004');

----COUNTRY (country id, country, country code)
INSERT INTO COUNTRY values(1, 'China', 'CHN');
INSERT INTO COUNTRY values(2, 'Russia', 'RUS');
INSERT INTO COUNTRY values(1000, 'Cuba', 'CUB');
--
--
----PARTICIPANT (participant id, fname, lname, nationality, birth place, dob)
INSERT INTO PARTICIPANT values(PARTICIPANT_SEQUENCE.NEXTVAL, 'Test Coach', 'Test', 'Test', 'Test', '1-Jan-1991'); --Test Value
INSERT INTO PARTICIPANT values(PARTICIPANT_SEQUENCE.NEXTVAL, 'Test Coach', 'China', 'Test', 'Test', '1-Jan-1999'); 
INSERT INTO PARTICIPANT values(PARTICIPANT_SEQUENCE.NEXTVAL, 'Test Coach', 'Russia', 'Test', 'Test', '1-Jan-1991'); 
INSERT INTO PARTICIPANT values(PARTICIPANT_SEQUENCE.NEXTVAL, 'Test Player', 'Russia', 'Test', 'Test', '1-Jan-1991'); 
INSERT INTO PARTICIPANT values(1000, 'Test trig', 'Russia', 'Test', 'Test', '1-Jan-1991'); 
INSERT INTO PARTICIPANT values(1001, 'Test trig', 'Russia', 'Test', 'Test', '1-Jan-1991'); 
INSERT INTO PARTICIPANT values(30, 'Test trig coach', 'Russia', 'Test', 'Test', '1-Jan-1991'); 

--
--TEAM (team id, olympic id, team name, country id, sport id, coach id)
INSERT INTO TEAM values(1000, 1, 'Test Team', 1000, 1000, 30); --Test Value
INSERT INTO TEAM values(1001, 1, 'Test Team', 1000, 1001, 30); --Test Value
INSERT INTO TEAM values(TEAM_SEQUENCE.NEXTVAL, 1, 'Test Team', 1, 1, 1); --Test Value
INSERT INTO TEAM values(TEAM_SEQUENCE.NEXTVAL, 1, 'China Womens Volleyball', 1, 1, 2);
INSERT INTO TEAM values(TEAM_SEQUENCE.NEXTVAL, 1, 'Russia Womens Volleyball', 2, 1, 3);
INSERT INTO TEAM values(TEAM_SEQUENCE.NEXTVAL, 1, 'Test', 2, 1, 4);
--INSERT INTO TEAM values(3, 1, 'Cuba Womens Vollyeball', 3, 1, 67);
--
----TEAM MEMBER (team id, participant id)
INSERT INTO TEAM_MEMBER values(1000, 1000);
INSERT INTO TEAM_MEMBER values(1001, 1001);
--
----EVENT_PARTICIPATION (event id, team id, status)
INSERT INTO EVENT_PARTICIPATION values(1, 1000, 'e'); --Test Individual Sport
INSERT INTO EVENT_PARTICIPATION values(1, 1001, 'e'); --Test Individual Sport
--
----SCOREBOARD (olympic id, event id, team id, participant id, position, medal id)
INSERT INTO SCOREBOARD values(1, 1, 1000, 1000, 1, Null); --Test Val
INSERT INTO SCOREBOARD values(1, 1, 1, 4, 2, 2);
INSERT INTO SCOREBOARD values(1, 1, 2, 2, 4, 2);
INSERT INTO SCOREBOARD values(1, 1, 3, 3, 3, 3);
INSERT INTO SCOREBOARD values(1, 2, 1, 4, 1, 1);
--
----PROOF OF TRIGGER 1
SELECT * FROM SCOREBOARD;
--
----PROOF OF TRIGGER 2
SELECT * FROM EVENT_PARTICIPATION;
DELETE FROM PARTICIPANT WHERE participant_id=1000;
SELECT * FROM EVENT_PARTICIPATION;
--
--SELECT * FROM PARTICIPANT;
DELETE FROM PARTICIPANT WHERE participant_id=1001;
SELECT * FROM PARTICIPANT;
--
----PROOF OF TRIGGER 3 ALONGSIDE TEXT
SELECT * FROM EVENT;

--
COMMIT;
