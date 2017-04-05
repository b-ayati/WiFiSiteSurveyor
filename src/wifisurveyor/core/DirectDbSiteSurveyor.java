package wifisurveyor.core;

import wifisurveyor.Manager;
import wifisurveyor.PlainTextTable;
import wifisurveyor.WifiSiteSurveyor;
import wifisurveyor.core.Database.DBManager;
import wifisurveyor.core.wifiScanner.AP;
import wifisurveyor.core.wifiScanner.Command;
import wifisurveyor.core.wifiScanner.Parser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ayati on 3/12/2017.
 */
public class DirectDbSiteSurveyor implements WifiSiteSurveyor
{
    //private String user;
    //private String password;
    private final String[] floorPlans = new String[]{"floor-3","floor-5"};
    private String currentFloorPlan = null;
    private String currentSurveyName = null;
    private DBManager manager = null;
    private String username = "ali";
/*
    public DirectDbSiteSurveyor(String user, String password)
    {
        this.user = user;
        this.password = password;
    }
*/
    public DirectDbSiteSurveyor()  {
        try {
            this.manager = new DBManager();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setContext(String floorPlan, String surveyName)
    {
        this.currentFloorPlan = floorPlan;
        this.currentSurveyName = surveyName;
    }

    @Override
    public Image getCurrentFloorPlanImg()
    {
        Image temp = null;
        try
        {
            temp = ImageIO.read(getClass().getResource("resources/plans/" + currentFloorPlan + ".bmp"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return temp;
    }

    private void delay()
    {
        try
        {
            Thread.sleep(4000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getFloorPlanNames()
    {
        return floorPlans.clone();
    }

    @Override
    public String[] getSurveyNames() throws SQLException {
        return manager.getUserProjects(username);
    }

    @Override
    public Point2D[] getCurrentPoints() throws SQLException {
        return manager.getPoints(this.currentFloorPlan, this.username, this.currentSurveyName);
    }

    @Override
    public void scan(Point2D currentLocation) throws SQLException {
        Manager.getUI().reportStatus("Start Scanning ...");
        String commandOutput = Command.mock();
        Parser parser = new Parser(commandOutput);
        ArrayList<AP> aps = parser.getAPs();
        int i = 0;
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        Manager.getUI().reportStatus("Sending data to DataBase");
        for (AP ap : aps) {
            float compeleted = (float)i/aps.size();
            Manager.getUI().reportStatus("compeleted: " + compeleted);
            manager.insert(currentLocation,strDate, this.currentFloorPlan, this.username, currentSurveyName, ap.mac,Integer.parseInt(ap.channel.trim()),ap.ssid, Float.toString(ap.power));
            i++;
        }
        Manager.getUI().reportStatus("Scan finished ...");

    }

    @Override
    public void remove(Point2D location) throws SQLException {
        Manager.getUI().reportStatus("removing point...");
        manager.delete(location,this.currentFloorPlan, this.username, this.currentSurveyName);
        Manager.getUI().reportStatus(null);
    }


    @Override
    public PlainTextTable getData(Point2D location) throws SQLException {
        System.out.println(location.getX() + "-" + location.getY());
        Manager.getUI().reportStatus("reading point data...");
        String[][] data = manager.getPointData(location, this.currentFloorPlan, this.username, this.currentSurveyName);
        System.out.println(data.length);
        System.out.println(data[0].length);
        String[] columns = {"mac", "channel", "ssid" , "readings"};
        Manager.getUI().reportStatus("data fetched.");
        return new PlainTextTable(columns, data);
    }







}
