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
    private static final int RETRY_COUNT = 5;
    private static final int RETRY_DELAY_SECS = 4;
    private static final int WIFI_COOLDOWN_TIME_SECS = 4;


    //private String user;
    //private String password;
    private UI ui;
    private final String[] floorPlans = new String[]{"floor-00", "floor-01", "floor-02", "floor-03", "floor-04", "floor-05", "floor-06", "floor-07", "floor-08"};
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

    public void connect() throws SQLException
    {
        manager.connect();
    }

    private String format(String format, Point2D p)
    {
        return String.format(format.replaceAll("%p", "[%.3f,%.3f]"), p.getX(), p.getY());
    }

    @Override
    public synchronized void setContext(String floorPlan, String surveyName)
    {
        this.currentFloorPlan = floorPlan;
        this.currentSurveyName = surveyName;
    }

    @Override
    public String getContext()
    {
        return String.format("[%s] - %s:%s", userName, currentSurveyName, currentFloorPlan);
    }

    @Override
    public synchronized void closeCurrentContext() throws InterruptedException, SQLException, ClassNotFoundException
    {
        ui.reportStatus("Closing surveyor context...");
        for (int tries = 1; ; tries++)
        {
            try
            {
                manager.flushBuffer();
                break;
            }
            catch (Exception e)
            {
                if (tries == RETRY_COUNT)
                    throw e;
                ui.reportStatus("Error while flushing local buffer. retrying (attempt #" + tries + ")...");
                Thread.sleep(RETRY_DELAY_SECS * 1000);
            }
        }
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
        return manager.getPoints(this.currentFloorPlan, this.userName, this.currentSurveyName);
    }

    @Override
    public synchronized void scan(Point2D currentLocation) throws InterruptedException
    {
        for (int k = 1; k <= SCAN_COUNT; k++)
        {
            //String commandOutput = Command.mock();
            ui.reportStatus("Refreshing wifi interface...");
            System.out.println(Command.execute("netsh wlan disconnect"));
            Thread.sleep(WIFI_COOLDOWN_TIME_SECS * 1000);
            System.out.println(Command.execute("netsh wlan connect " + wifiProfileName));
            ui.reportStatus("Gathering data for sample #" + k + "...");
            Thread.sleep(WIFI_COOLDOWN_TIME_SECS * 1000);
            String commandOutput = Command.execute("netsh wlan show networks mode=bssid");
            Parser parser = new Parser(commandOutput);
            ArrayList<AP> aps = parser.getAPs();
            System.out.println(aps);
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
            Date now = new Date();
            String strDate = sdfDate.format(now);
            for (AP ap : aps)
                manager.insert(currentLocation, strDate, this.currentFloorPlan, this.userName, currentSurveyName, ap.mac, Integer.parseInt(ap.channel.trim()), ap.ssid, Float.toString(ap.power));
        }
        ui.reportStatus(format("Scan completed successfully for location %p.", currentLocation));
    }

    @Override
    public void remove(Point2D location) throws SQLException, ClassNotFoundException, InterruptedException
    {
        ui.reportStatus("Removing point...");
        manager.delete(location, this.currentFloorPlan, this.userName, this.currentSurveyName);
        ui.reportStatus(format("Point %p removed successfully.", location));
    }


    @Override
    public PlainTextTable getData(Point2D location) throws SQLException, ClassNotFoundException, InterruptedException
    {
        System.out.println(location.getX() + "-" + location.getY());
        ui.reportStatus("Reading data from database...");
        String[][] data = manager.getPointData(location, this.currentFloorPlan, this.userName, this.currentSurveyName);
        String[] columns = {"mac", "channel", "ssid", "readings"};
        ui.reportStatus(format("Data for %p retrieved successfully.", location));
        return new PlainTextTable(columns, data);
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
        if (instance != null)
            throw new IllegalStateException();
        instance = new DirectDbSiteSurveyor(userName, wifiProfileName);
    }

    public static DirectDbSiteSurveyor getInstance()
    {
        return instance;
    }


}
