-- Triggers, Functions, Procedures --
-- JJL90 --

--Phase 1--
ALTER SESSION SET PLSCOPE_SETTINGS = 'IDENTIFIERS:NONE';
SET SERVEROUTPUT ON
-- 1 --
CREATE OR REPLACE TRIGGER ASSIGN_MEDAL
BEFORE INSERT OR UPDATE
ON SCOREBOARD
FOR EACH ROW
BEGIN
    IF :new.position = 3 or :new.position = 2 or :new.position = 1 then :new.medal_id := :new.position;
    ELSE :new.medal_id := null;
    END IF;
END;
/

 --2--
CREATE OR REPLACE TRIGGER ATHLETE_DISMISSAL
BEFORE DELETE
ON PARTICIPANT
FOR EACH ROW
DECLARE
    team_id_val integer;
    sport_id_val integer;
    team_size_val integer;
BEGIN
    SELECT team_id into team_id_val FROM TEAM_MEMBER WHERE participant_id = :old.participant_id;
    SELECT sport_id into sport_id_val FROM TEAM WHERE team_id = team_id_val;
    SELECT team_size into team_size_val FROM SPORT WHERE sport_id = sport_id_val;
    IF team_size_val = 1 THEN 
        DELETE FROM SCOREBOARD WHERE team_id = team_id_val;
    ELSE UPDATE EVENT_PARTICIPATION SET status='n' WHERE team_id = team_id_val;
    END IF;
        DELETE FROM TEAM_MEMBER WHERE participant_id = :old.participant_id;
        DELETE FROM SCOREBOARD WHERE participant_id = :old.participant_id;
END;
/

-- 3 --
CREATE OR REPLACE VIEW EVENT_CAPACITY AS
    SELECT venue_id, count(*) as ven_count
        FROM EVENT
        GROUP BY venue_id;
        
CREATE OR REPLACE TRIGGER ENFORCE_CAPACITY
BEFORE INSERT OR UPDATE
ON EVENT
FOR EACH ROW
DECLARE
    total_events integer;
    maximum_capacity integer;
    venue_exception EXCEPTION;
    vc integer;
BEGIN
    SELECT count(*) into vc FROM EVENT_CAPACITY WHERE venue_id = :new.venue_id;
 
    IF vc != 0 THEN
        SELECT capacity into maximum_capacity FROM VENUE WHERE venue_id = :new.venue_id;
        SELECT nvl(ven_count,0) into total_events FROM EVENT_CAPACITY WHERE venue_id = :new.venue_id;
        IF total_events >= maximum_capacity THEN RAISE venue_exception; END IF;
    END IF;

        
END;
/

COMMIT;

--Phase 2--








    