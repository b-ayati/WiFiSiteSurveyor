package wifisurveyor;

import wifisurveyor.gui.StartingForm;

import javax.swing.*;

/**
 * Created by ayati on 3/5/2017.
 */
public class Main
{
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException
    {

        // Set cross-platform Java L&F (also called "Metal")
        //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

        //Set System L&F
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(StartingForm::createAndShow);
    }
}
