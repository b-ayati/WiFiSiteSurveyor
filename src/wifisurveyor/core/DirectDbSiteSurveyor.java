package wifisurveyor.core;

import wifisurveyor.PlainTextTable;
import wifisurveyor.UI;
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
    //constants:
    private static final int SCAN_COUNT = 4;
    private static final int RETRY_COUNT = 4;
    private static final int RESTART_DELAY_SECS = 5;
    private static final int WIFI_RECONNECT_DELAY_SECS = 5;


    //private String user;
    //private String password;
    private UI ui;
    private final String[] floorPlans = new String[]{"floor-00", "floor-02", "floor-03", "floor-04", "floor-05", "floor-06", "floor-07", "floor-08"};
    private String currentFloorPlan = null;
    private String currentSurveyName = null;
    private DBManager manager = null;
    private String userName;
    private String wifiProfileName;

    public DirectDbSiteSurveyor(String userName, String wifiProfileName) throws SQLException, ClassNotFoundException
    {
        this.userName = userName;
        this.wifiProfileName = wifiProfileName;
        this.manager = new DBManager();
    }

    private void restart() throws SQLException, ClassNotFoundException, InterruptedException
    {
        for (int tries = 1;; tries++)
        {
            try
            {
                DirectDbSiteSurveyor.getInstance().getUi().reportStatus("Reconnecting to database (attempt #" + tries + ")...");
                Thread.sleep(RESTART_DELAY_SECS * 1000);
                this.manager = new DBManager();
                break;
            }
            catch (Exception e)
            {
                if (tries == RETRY_COUNT)
                    throw e;
            }
        }

    }

    private String format(String format, Point2D p)
    {
        return String.format(format.replaceAll("%p", "[%.3f,%.3f]"), p.getX(), p.getY());
    }

    @Override

    public void setContext(String floorPlan, String surveyName)
    {
        this.currentFloorPlan = floorPlan;
        this.currentSurveyName = surveyName;
    }


    @Override
    public Image getCurrentFloorPlanImg() throws IOException
    {
        System.out.println("resources/plans/" + currentFloorPlan + ".png");
        return ImageIO.read(getClass().getResource("resources/floor_plans/" + currentFloorPlan + ".png"));
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
    public Point2D[] getCurrentPoints() throws SQLException, InterruptedException, ClassNotFoundException
    {
        try
        {
            return manager.getPoints(this.currentFloorPlan, this.userName, this.currentSurveyName);
        }
        catch (SQLException e)
        {
            this.restart();
            throw e;
        }
    }

    @Override
    public void scan(Point2D currentLocation) throws SQLException, InterruptedException, ClassNotFoundException
    {
        try
        {
            for (int k = 1; k <= SCAN_COUNT; k++)
            {
                //String commandOutput = Command.mock();
                DirectDbSiteSurveyor.getInstance().getUi().reportStatus("Refreshing wifi interface...");
                System.out.println(Command.execute("netsh wlan disconnect"));
                Thread.sleep(WIFI_RECONNECT_DELAY_SECS * 1000);
                System.out.println(Command.execute("netsh wlan connect " + wifiProfileName));
                this.restart();
                DirectDbSiteSurveyor.getInstance().getUi().reportStatus("Gathering data for sample #" + k + "...");
                String commandOutput = Command.execute("netsh wlan show networks mode=bssid");
                Parser parser = new Parser(commandOutput);
                ArrayList<AP> aps = parser.getAPs();
                System.out.println(aps);
                int i = 0;
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                Date now = new Date();
                String strDate = sdfDate.format(now);
                DirectDbSiteSurveyor.getInstance().getUi().reportStatus("Sending sample #" + k + " to database...");
                for (AP ap : aps)
                {
                    int completed = (int) ((float) i / aps.size() * 100);
                    manager.insert(currentLocation, strDate, this.currentFloorPlan, this.userName, currentSurveyName, ap.mac, Integer.parseInt(ap.channel.trim()), ap.ssid, Float.toString(ap.power));
                    DirectDbSiteSurveyor.getInstance().getUi().reportStatus("Sending sample #" + k + " to database... " + completed + "%");
                    i++;
                }
            }
            DirectDbSiteSurveyor.getInstance().getUi().reportStatus(format("Scan completed successfully for location %p.", currentLocation));
        }
        catch (Exception e)
        {
            this.restart();
            throw e;
        }
    }

    @Override
    public void remove(Point2D location) throws SQLException, ClassNotFoundException, InterruptedException
    {
        try
        {
            DirectDbSiteSurveyor.getInstance().getUi().reportStatus("Removing point...");
            manager.delete(location, this.currentFloorPlan, this.userName, this.currentSurveyName);
            DirectDbSiteSurveyor.getInstance().getUi().reportStatus(format("Point %p removed successfully.", location));
        }
        catch (SQLException e)
        {
            this.restart();
            throw e;
        }
    }


    @Override
    public PlainTextTable getData(Point2D location) throws SQLException, ClassNotFoundException, InterruptedException
    {
        try
        {
            System.out.println(location.getX() + "-" + location.getY());
            DirectDbSiteSurveyor.getInstance().getUi().reportStatus("Reading data from database...");
            String[][] data = manager.getPointData(location, this.currentFloorPlan, this.userName, this.currentSurveyName);
            String[] columns = {"mac", "channel", "ssid", "readings"};
            DirectDbSiteSurveyor.getInstance().getUi().reportStatus(format("Data for %p retrieved successfully.", location));
            return new PlainTextTable(columns, data);
        }
        catch (SQLException e)
        {
            this.restart();
            throw e;
        }
    }

    public UI getUi()
    {
        return ui;
    }

    public void registerUi(UI ui)
    {
        this.ui = ui;
    }

//singleton methods:

    private static DirectDbSiteSurveyor instance;

    public static void initialize(String userName, String wifiProfileName) throws SQLException, ClassNotFoundException
    {
        if(instance != null)
            throw new IllegalStateException();
        instance = new DirectDbSiteSurveyor(userName, wifiProfileName);
    }

    public static DirectDbSiteSurveyor getInstance()
    {
        return instance;
    }



}
