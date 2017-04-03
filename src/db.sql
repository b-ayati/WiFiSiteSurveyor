DROP VIEW survey_data;
DROP TABLE measurements, access_points, survey_contexts;

CREATE TABLE access_points (
    id      serial PRIMARY KEY,
    mac     macaddr,
    channel smallint CHECK (channel > 0 AND channel < 200),
    ssid    varchar(100),
    UNIQUE (mac)
);

CREATE TABLE survey_contexts (
    id          serial PRIMARY KEY,
    floor_plan  varchar(50),
    user_name   varchar(50),
    survey_name varchar(50),
    UNIQUE (floor_plan, user_name, survey_name)
);

CREATE TABLE measurements (
    id             serial PRIMARY KEY,
    coordinate     point CHECK (coordinate <@ box '((0,0),(1,1))'),
    log_time       timestamp DEFAULT now(),
    survey_context int REFERENCES survey_contexts,
    ap_info        int REFERENCES access_points,
    readings       real []
);

CREATE INDEX ON measurements (survey_context);

CREATE VIEW survey_data AS
    SELECT measurements.id, coordinate, log_time, floor_plan, user_name, survey_name, mac, channel, ssid, readings
    FROM measurements, survey_contexts, access_points
    WHERE measurements.survey_context = survey_contexts.id AND measurements.ap_info = access_points.id;


CREATE OR REPLACE FUNCTION survey_data_insert_row()
    RETURNS trigger AS
$$
DECLARE
    ap_id      int;
    context_id int;
BEGIN
    --Notice: functions run in a transaction so there is no need for a transaction here!
    SELECT id INTO ap_id FROM access_points WHERE NEW.mac = mac;
    IF NOT FOUND
    THEN
        INSERT INTO access_points (mac, channel, ssid)
        VALUES (NEW.mac, NEW.channel, NEW.ssid)
        RETURNING id INTO ap_id;
    END IF;

    SELECT id INTO context_id
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
    RETURNS trigger AS
$$
BEGIN
    DELETE FROM measurements WHERE OLD.id = measurements.id;
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

INSERT INTO survey_data (coordinate, log_time, floor_plan, user_name, survey_name, mac, channel, ssid, readings)
VALUES (point(0.12, 0.15), '2017-03-12 13:56:42.75', 'floor-8', 'group-2', 'exp#1', macaddr('00-14-22-01-23-45'), 6,
        'CE_WLAN', '{-25.5}');


SELECT * FROM survey_data WHERE coordinate <-> POINT(0.22130,0.1378881) <= 0.0001

