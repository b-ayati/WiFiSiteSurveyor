DROP VIEW survey_data;
DROP TABLE measurements, access_points, survey_contexts;

CREATE TABLE access_points (
    id      SERIAL PRIMARY KEY,
    mac     MACADDR,
    channel SMALLINT CHECK (channel > 0 AND channel < 200),
    ssid    VARCHAR(100),
    UNIQUE (mac)
);

CREATE TABLE survey_contexts (
    id          SERIAL PRIMARY KEY,
    floor_plan  VARCHAR(50),
    user_name   VARCHAR(50),
    survey_name VARCHAR(50),
    UNIQUE (floor_plan, user_name, survey_name)
);

CREATE TABLE measurements (
    id             SERIAL PRIMARY KEY,
    coordinate     POINT CHECK (coordinate <@ BOX '((0,0),(1,1))'),
    log_time       TIMESTAMP DEFAULT now(),
    survey_context INT REFERENCES survey_contexts,
    ap_info        INT REFERENCES access_points,
    readings       REAL []
);

CREATE INDEX ON measurements (survey_context);

CREATE VIEW survey_data AS
    SELECT measurements.id, coordinate, log_time, floor_plan, user_name, survey_name, mac, channel, ssid, readings
    FROM measurements, survey_contexts, access_points
    WHERE measurements.survey_context = survey_contexts.id AND measurements.ap_info = access_points.id;

CREATE OR REPLACE FUNCTION survey_data_insert_row()
    RETURNS TRIGGER AS
$$
DECLARE
    ap_id      INT;
    context_id INT;
BEGIN
    --Notice: functions run in a transaction so there is no need for a transaction here!
    SELECT id
    INTO ap_id
    FROM access_points
    WHERE NEW.mac = mac;
    IF NOT FOUND
    THEN
        INSERT INTO access_points (mac, channel, ssid)
        VALUES (NEW.mac, NEW.channel, NEW.ssid)
        RETURNING id INTO ap_id;
    END IF;

    SELECT id
    INTO context_id
    FROM survey_contexts
    WHERE floor_plan = NEW.floor_plan AND user_name = NEW.user_name AND survey_name = NEW.survey_name;
    IF NOT FOUND
    THEN
        INSERT INTO survey_contexts (floor_plan, user_name, survey_name)
        VALUES (NEW.floor_plan, NEW.user_name, NEW.survey_name)
        RETURNING id INTO context_id;
    END IF;

    INSERT INTO measurements (coordinate, log_time, survey_context, ap_info, readings)
    VALUES (NEW.coordinate, NEW.log_time, context_id, ap_id, NEW.readings);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION survey_data_delete_row()
    RETURNS TRIGGER AS
$$
BEGIN
    DELETE FROM measurements
    WHERE OLD.id = measurements.id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER survey_data_insert
INSTEAD OF INSERT ON survey_data
FOR EACH ROW
EXECUTE PROCEDURE survey_data_insert_row();

CREATE TRIGGER survey_data_delete
INSTEAD OF DELETE ON survey_data
FOR EACH ROW
EXECUTE PROCEDURE survey_data_delete_row();

/*
SELECT mac,channel,ssid,avg(readings[1]) as signal_power FROM survey_data WHERE floor_plan = 'floor-03' and survey_name= '456465' and user_name='test' and coordinate <-> point(0.270860,0.770714) <= 1.0E-4 GROUP BY mac, channel, ssid ORDER BY signal_power DESC;
INSERT INTO survey_data (coordinate, log_time, floor_plan, user_name, survey_name, mac, channel, ssid, readings)
VALUES (point(0.12, 0.15), '2017-03-12 13:56:42.75', 'floor-8', 'group-2', 'exp#1', macaddr('00-14-22-01-23-45'), 6,
        'CE_WLAN', '{-25.5}');


SELECT *
FROM survey_data
WHERE coordinate <-> POINT(0.22130, 0.1378881) <= 0.0001

SELECT DISTINCT (coordinate[0], coordinate[1]) from survey_data WHERE floor_plan = 'floor-3' and user_name = 'ali' and survey_name='temp'
*/