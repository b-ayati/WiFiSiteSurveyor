package wifisurveyor.core;

import wifisurveyor.Manager;
import wifisurveyor.PlainTextTable;
import wifisurveyor.WifiSiteSurveyor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

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
/*
    public DirectDbSiteSurveyor(String user, String password)
    {
        this.user = user;
        this.password = password;
    }
*/
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
    public String[] getSurveyNames()
    {
        //TODO: implement by qing the db
        return new String[]{"khafan", "prj1", "my survey"};
    }

    @Override
    public Point2D[] getCurrentPoints()
    {
        return new Point2D[] {new Point2D.Float(0.25f,0.4f)};
    }

    @Override
    public void scan(Point2D currentLocation)
    {
        delay();
    }

    @Override
    public void remove(Point2D location)
    {
        Manager.getUI().reportStatus("removing point...");
        delay();
        Manager.getUI().reportStatus(null);
    }


    @Override
    public PlainTextTable getData(Point2D location)
    {
        Manager.getUI().reportStatus("reading point data...");
        delay();
        Manager.getUI().reportStatus("data fetched.");
        return new PlainTextTable();
    }







}
