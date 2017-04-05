package wifisurveyor;

import java.awt.*;
import java.awt.geom.Point2D;
import java.sql.SQLException;

/**
 * Created by Behrouz on 14/03/2017.
 */
public interface WifiSiteSurveyor
{
    public void setContext(String floorPlan, String surveyName);

    public Image getCurrentFloorPlanImg();

    public String[] getFloorPlanNames();

    public String[] getSurveyNames() throws SQLException;

    public Point2D[] getCurrentPoints() throws SQLException;

    public void scan(Point2D currentLocation) throws Exception;

    public void remove(Point2D location) throws SQLException;

    public PlainTextTable getData(Point2D location) throws SQLException;
}
