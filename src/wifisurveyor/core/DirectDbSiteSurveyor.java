package wifisurveyor.core;

import wifisurveyor.Manager;
import wifisurveyor.PlainTextTable;
import wifisurveyor.WifiSiteSurveyor;
import wifisurveyor.core.Database.DBManager;
import wifisurveyor.core.wifiScanner.AP;
import wifisurveyor.core.wifiScanner.Command;
import wifisurveyor.core.wifiScanner.Parser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
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
    public static final int SCAN_COUNT = 3;
    //private String user;
    //private String password;
    private final String[] floorPlans = new String[]{"floor-3", "floor-5"};
    private String currentFloorPlan = null;
    private String currentSurveyName = null;
    private DBManager manager = null;
    private String userName = "ali";

    /*
        public DirectDbSiteSurveyor(String user, String password)
        {
            this.user = user;
            this.password = password;
        }
    */
    public DirectDbSiteSurveyor() throws SQLException, ClassNotFoundException
    {
        this.manager = new DBManager();
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
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public String[] getFloorPlanNames()
    {
        return floorPlans.clone();
    }

    @Override
    public String[] getSurveyNames() throws SQLException
    {
        return manager.getUserProjects(userName);
    }

    @Override
    public Point2D[] getCurrentPoints() throws SQLException
    {
        return manager.getPoints(this.currentFloorPlan, this.userName, this.currentSurveyName);
    }

    @Override
    public void scan(Point2D currentLocation) throws SQLException, InterruptedException
    {
        for (int k = 1; k <= SCAN_COUNT; k++)
        {
            String commandOutput = Command.mock();
            Manager.getUI().reportStatus("Refreshing wifi interface...");
            //System.out.println(Command.execute("netsh interface set interface name=Wi-Fi admin=disabled"));
            //System.out.println(Command.execute("netsh interface set interface name=Wi-Fi admin=enabled"));
            //Thread.sleep(10 * 1000);
            Manager.getUI().reportStatus("Gathering data for sample #" + k + "...");
            //String commandOutput = Command.execute("netsh wlan show networks mode=bssid");
            Parser parser = new Parser(commandOutput);
            ArrayList<AP> aps = parser.getAPs();
            int i = 0;
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
            Date now = new Date();
            String strDate = sdfDate.format(now);
            Manager.getUI().reportStatus("Sending data to database...");
            for (AP ap : aps)
            {
                int completed = (int) ((float) i / aps.size() * 100);
                manager.insert(currentLocation, strDate, this.currentFloorPlan, this.userName, currentSurveyName, ap.mac, Integer.parseInt(ap.channel.trim()), ap.ssid, Float.toString(ap.power));
                Manager.getUI().reportStatus("Sending data to database... " + completed + "%");
                i++;
            }
        }
        Manager.getUI().reportStatus(format("Scan completed successfully for location %p.", currentLocation));

    }

    @Override
    public void remove(Point2D location) throws SQLException
    {
        Manager.getUI().reportStatus("Removing point...");
        manager.delete(location, this.currentFloorPlan, this.userName, this.currentSurveyName);
        Manager.getUI().reportStatus(format("Point %p removed successfully.", location));
    }


    @Override
    public PlainTextTable getData(Point2D location) throws SQLException
    {
        System.out.println(location.getX() + "-" + location.getY());
        Manager.getUI().reportStatus("Reading data from DB...");
        String[][] data = manager.getPointData(location, this.currentFloorPlan, this.userName, this.currentSurveyName);
        String[] columns = {"mac", "channel", "ssid", "readings"};
        Manager.getUI().reportStatus(format("Data for %p retrieved successfully.", location));
        return new PlainTextTable(columns, data);
    }


    private String format(String format, Point2D p)
    {
        return String.format(format.replaceAll("%p", "[%.3f,%.3f]"), p.getX(), p.getY());
    }


}
