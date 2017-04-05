package wifisurveyor;

import wifisurveyor.core.DirectDbSiteSurveyor;
import wifisurveyor.gui.GUI;

/**
 * Created by Behrouz on 14/03/2017.
 */
public class Manager
{
    private static WifiSiteSurveyor wifiSiteSurveyorInstance = new DirectDbSiteSurveyor();

    private static UI uiInstance = new GUI();

    public static WifiSiteSurveyor getSurveyor()
    {
        return wifiSiteSurveyorInstance;
    }

    public static UI getUI()
    {
        return uiInstance;
    }

    private Manager()
    {
    }


}
