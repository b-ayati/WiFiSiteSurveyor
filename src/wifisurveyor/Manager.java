package wifisurveyor;

import wifisurveyor.core.DirectDbSiteSurveyor;

/**
 * Created by Behrouz on 14/03/2017.
 */
public class Manager
{
    private static WifiSiteSurveyor wifiSiteSurveyorInstance = new DirectDbSiteSurveyor();
    public static WifiSiteSurveyor getSurveyor()
    {
        return wifiSiteSurveyorInstance;
    }

    private Manager()
    {
    }


}
