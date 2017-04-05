package wifisurveyor.gui;

import wifisurveyor.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ayati on 3/5/2017.
 */
public class GUI implements UI
{
    private SurveyingDialog currSurveyingDialog;

    public synchronized void reportStatus(String status)
    {
        currSurveyingDialog.showStatus(status);
    }

    void setCurrSurveyingDialog(SurveyingDialog currSurveyingDialog)
    {
        this.currSurveyingDialog = currSurveyingDialog;
    }

    public static void showErrorMessage(Component parent, Exception e)
    {
        JOptionPane.showMessageDialog(parent, "This operation can't be done. the following error has occurred:\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showFatalErrorMessage(Component parent, Exception e)
    {
        JOptionPane.showMessageDialog(parent, "The following error has occurred and WiFiSiteSurvey needs to close.\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    public void showMainForm()
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(StartingForm::createAndShow);
    }


}
