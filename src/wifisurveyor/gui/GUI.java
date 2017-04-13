package wifisurveyor.gui;

import wifisurveyor.UI;
import wifisurveyor.WifiSiteSurveyor;

import javax.swing.*;

/**
 * Created by ayati on 3/5/2017.
 */
public class GUI implements UI
{
    private WifiSiteSurveyor surveyor;
    private SurveyingDialog currSurveyingDialog = null;

    private GUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException
    {
        // Set cross-platform Java L&F (also called "Metal")
        //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

        //Set System L&F
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

    @Override
    public synchronized void reportStatus(String status)
    {
        if(currSurveyingDialog != null)
            currSurveyingDialog.showStatus(status);
    }

    void setCurrSurveyingDialog(SurveyingDialog currSurveyingDialog)
    {
        this.currSurveyingDialog = currSurveyingDialog;
    }

    @Override
    public void showGeneralErrorMessage(Exception e)
    {
        if (currSurveyingDialog != null)
            reportStatus("Error!!!");
        JOptionPane.showMessageDialog(currSurveyingDialog, "This operation couldn't be completed. the following error has occurred:\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    @Override
    public void showConnectionErrorMessage(Exception e)
    {
        JOptionPane.showMessageDialog(currSurveyingDialog, e.getMessage() + "\nCheck your network connection and make sure you can ping ce.sharif.edu.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    @Override
    public void showFatalErrorMessage(Exception e)
    {
        JOptionPane.showMessageDialog(currSurveyingDialog, "The following error has occurred and WiFiSiteSurvey needs to close.\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    public void showMainForm()
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(StartingForm::createAndShow);
    }

    public void registerWiFiSurveyor(WifiSiteSurveyor surveyor)
    {
        this.surveyor = surveyor;
    }

    public WifiSiteSurveyor getWiFiSurveyor()
    {
        return surveyor;
    }

//singleton methods
    private static GUI instance = null;

    public static void initialize() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException
    {
        if(instance != null)
            throw new IllegalStateException();
        instance = new GUI();
    }

    public static GUI getInstance()
    {
        return instance;
    }



}
