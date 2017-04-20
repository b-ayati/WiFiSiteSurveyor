package wifisurveyor.core.Database;

import wifisurveyor.core.DirectDbSiteSurveyor;

import java.awt.geom.Point2D;
import java.sql.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Queue;

/**
 * Created by alireza on 3/15/17.
 */
public class DBManager
{
    private static final double PRECISION = 0.0001;
    private static final int CONNECT_TIMEOUT_SECS = 8;
    private static final int SOCKET_TIMEOUT_SECS = 10;
    private Connection connection = null;
    private Statement stmt = null;
    private String user = "survey_app";
    private String password = "surveyMIGirim10";
    private Queue<String> buffer = new ArrayDeque<>();


    public DBManager() throws ClassNotFoundException
    {
        Class.forName("org.postgresql.Driver");
    }

    /**
     * creates a JDBC connection to database
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public synchronized void connect() throws SQLException
    {
        DirectDbSiteSurveyor.getInstance().getUi().reportStatus("Connecting to database...");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("loginTimeout", String.valueOf(CONNECT_TIMEOUT_SECS));
        props.setProperty("connectTimeout", String.valueOf(CONNECT_TIMEOUT_SECS));
        props.setProperty("socketTimeout", String.valueOf(SOCKET_TIMEOUT_SECS));
        this.connection = DriverManager.getConnection("jdbc:postgresql://git.ce.sharif.edu:5432/site_survey_db", props);
        this.connection.setAutoCommit(true);
        this.stmt = connection.createStatement();
        this.stmt.setQueryTimeout(SOCKET_TIMEOUT_SECS);
    }

    /**
     * inserts an AP data to database
     *
     * @param coordinate
     * @param log_time
     * @param plan
     * @param user_name
     * @param survey_name
     * @param mac
     * @param channel
     * @param ssid
     * @param signalPower
     * @throws SQLException
     */
    public synchronized void insert(Point2D coordinate, String log_time, String plan, String user_name, String survey_name, String mac, int channel, String ssid, String signalPower)
    {
        String query = String.format("INSERT INTO survey_data (coordinate, log_time, floor_plan, user_name, survey_name, mac, channel, ssid, readings) VALUES (%s, '%s', '%s', '%s', '%s', macaddr('%s'), %d, '%s', '{%s}');", "point(" + coordinate.getX() + "," + coordinate.getY() + ")", log_time, plan, user_name, survey_name, mac, channel, ssid.replace("'", ""), signalPower);
        buffer.add(query);
    }

    public synchronized void flushBuffer() throws SQLException
    {
        if (connection.isClosed())
            connect();
        int init_size = buffer.size();
        while (!buffer.isEmpty())
        {
            DirectDbSiteSurveyor.getInstance().getUi().reportStatus("Flushing local buffer to database... " + (init_size - buffer.size()) * 100 / init_size + "% completed");
            stmt.executeUpdate(buffer.peek());
            buffer.poll();
        }
    }

    /**
     * deletes a point from a plan
     *
     * @param coordinate
     * @param plan
     * @param username
     * @param survey_name
     */
    public synchronized void delete(Point2D coordinate, String plan, String username, String survey_name) throws SQLException
    {
        flushBuffer();
        String query = String.format("delete FROM survey_data WHERE coordinate <-> point(%f,%f) <= " + PRECISION + " and floor_plan = '%s' and survey_name='%s' and user_name='%s';", coordinate.getX(), coordinate.getY(), plan, survey_name, username);
        System.out.println(query);
        stmt.executeUpdate(query);
    }

    public Point2D[] getPoints(String floor_plan, String username, String survey_name) throws SQLException
    {
        flushBuffer();
        String query = String.format("SELECT DISTINCT (coordinate[0], coordinate[1]) from survey_data WHERE floor_plan = '%s' and user_name = '%s' and survey_name='%s';", floor_plan, username, survey_name);
        System.out.println(query);
        ResultSet resultSet = stmt.executeQuery(query);
        ArrayList<Point2D> point2DS = new ArrayList<>();
        while (resultSet.next())
        {
            String tmp = resultSet.getString(1);
            String pointString = tmp.substring(1, tmp.length() - 1);
            String[] parts = pointString.split(",");
            Point2D p = new Point2D.Float(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]));
            point2DS.add(p);
        }
        return point2DS.toArray(new Point2D[point2DS.size()]);
    }

    public String[] getUserProjects(String userName) throws SQLException
    {
        flushBuffer();
        String query = String.format("select DISTINCT survey_name from survey_data WHERE user_name='%s';", userName);
        ResultSet resultSet = stmt.executeQuery(query);
        ArrayList<String> projects = new ArrayList<>();
        while (resultSet.next())
        {
            String record = resultSet.getString(1);
            projects.add(record);
        }
        return projects.toArray(new String[projects.size()]);

    }

    public String[][] getPointData(Point2D coordinate, String plan, String username, String survey_name) throws SQLException
    {
        flushBuffer();
        String query = String.format("SELECT mac, channel, ssid, avg(readings[1]) as signal_power FROM survey_data WHERE floor_plan = '%s' and survey_name= '%s' and user_name='%s' and coordinate <-> point(%f,%f) <= " + PRECISION + " GROUP BY mac, channel, ssid ORDER BY signal_power DESC;", plan, survey_name, username, coordinate.getX(), coordinate.getY());
        System.out.println(query);
        ResultSet resultSet = stmt.executeQuery(query);
        ArrayList<String[]> data = new ArrayList<>();
        while (resultSet.next())
        {
            String mac = resultSet.getString(1);
            String channel = resultSet.getString(2);
            String ssid = resultSet.getString(3);
            String reading = resultSet.getString(4);
            data.add(new String[]{mac, channel, ssid, reading});
        }
        return data.toArray(new String[data.size()][]);
    }

}
