package wifisurveyor.gui;

import wifisurveyor.UI;

import javax.swing.*;

/**
 * Created by ayati on 3/5/2017.
 */
public class GUI implements UI
{
    private SurveyingDialog currSurveyingDialog = null;

    public synchronized void reportStatus(String status)
    {
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
        JOptionPane.showMessageDialog(currSurveyingDialog, "This operation can't be done. the following error has occurred:\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    @Override
    public void showConnectionErrorMessage(Exception e)
    {
        JOptionPane.showMessageDialog(currSurveyingDialog, e.getMessage() + "\ncheck your network connection and make sure you can ping ce.sharif.edu.", "Connection Error", JOptionPane.ERROR_MESSAGE);
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


}
