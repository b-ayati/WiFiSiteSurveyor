package wifisurveyor.gui;

import wifisurveyor.UI;
import wifisurveyor.WifiSiteSurveyor;

import javax.swing.*;
import java.awt.*;

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
        if (currSurveyingDialog != null)
            currSurveyingDialog.showStatus(status);
    }

    void setCurrSurveyingDialog(SurveyingDialog currSurveyingDialog)
    {
        this.currSurveyingDialog = currSurveyingDialog;
    }

    @Override
    public void showGeneralErrorMessage(Exception e)
    {
        reportStatus("Error!!!");
        showMessageDialog(currSurveyingDialog, "This operation couldn't be completed. the following error has occurred:\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    @Override
    public void showConnectionErrorMessage(Exception e)
    {
        reportStatus("Connection Error!!! fix your network connection to prevent data loss.");
        showMessageDialog(currSurveyingDialog, e.getMessage() + "\nCheck your network connection and make sure you can connect to your database.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    @Override
    public void showFatalErrorMessage(Exception e)
    {
        showMessageDialog(currSurveyingDialog, "The following error has occurred and WiFiSiteSurvey needs to close.\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    /**
     * it seems that there is a bug in swing JOptionPane class which causes the parent window minimizes
     * when the parent is disabled before calling the showDialog message.
     *
     * @param parent
     * @param message
     * @param title
     * @param messageType
     */
    static void showMessageDialog(Component parent, Object message, String title, int messageType)
    {
        if (parent != null && !parent.isEnabled())
        {
            parent.setEnabled(true);    //to prevent parent dialog from minimizing
            JOptionPane.showMessageDialog(parent, message, title, messageType);
            parent.setEnabled(false);
        }
        else
            JOptionPane.showMessageDialog(parent, message, title, messageType);
    }

    static int showOptionDialog(Component parent, Object message, String title, int optionType, int messageType)
    {
        int result;
        if (parent != null && !parent.isEnabled())
        {
            parent.setEnabled(true);    //to prevent parent dialog from minimizing
            result = JOptionPane.showOptionDialog(parent, message, title, optionType, messageType, null, null, null);
            parent.setEnabled(false);
        }
        else
            result = JOptionPane.showOptionDialog(parent, message, title, optionType, messageType, null, null, null);
        return result;
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
        if (instance != null)
            throw new IllegalStateException();
        instance = new GUI();
    }

    public static GUI getInstance()
    {
        return instance;
    }


}
