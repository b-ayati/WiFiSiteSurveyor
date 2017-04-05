package wifisurveyor;

import wifisurveyor.core.DirectDbSiteSurveyor;
import wifisurveyor.gui.GUI;

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
        GUI ui = new GUI();
        try
        {
            Manager.initialize(new DirectDbSiteSurveyor(), ui);
        }
        catch (Exception e)
        {
            GUI.showFatalErrorMessage(null, e);
        }
        ui.showMainForm();

    }
}
