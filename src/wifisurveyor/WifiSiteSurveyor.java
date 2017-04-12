package wifisurveyor;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Behrouz on 14/03/2017.
 */
public interface WifiSiteSurveyor
{
    public void setContext(String floorPlan, String surveyName);

    public Image getCurrentFloorPlanImg() throws Exception;

    public String[] getFloorPlanNames();

    public String[] getSurveyNames() throws Exception;

    public Point2D[] getCurrentPoints() throws Exception;

    public void scan(Point2D currentLocation) throws Exception;

    public void remove(Point2D location) throws Exception;

    public PlainTextTable getData(Point2D location) throws Exception;
}
