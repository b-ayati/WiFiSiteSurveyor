package wifisurveyor;

/**
 * Created by Behrouz on 14/03/2017.
 */
public class Manager
{
    private static WifiSiteSurveyor wifiSiteSurveyorInstance = null;

    private static UI uiInstance = null;

    public static WifiSiteSurveyor getSurveyor()
    {
        return wifiSiteSurveyorInstance;
    }

    public static UI getUI()
    {
        return uiInstance;
    }

    public static void initialize(WifiSiteSurveyor surveyor, UI ui)
    {
        if (uiInstance == null && wifiSiteSurveyorInstance == null)
        {
            wifiSiteSurveyorInstance = surveyor;
            uiInstance = ui;
        }
        else
            throw new IllegalStateException("manager already has been initialized.");
    }

    private Manager()
    {
    }
}
