package wifisurveyor;

import wifisurveyor.core.DirectDbSiteSurveyor;
import wifisurveyor.gui.GUI;

import javax.swing.*;
import java.sql.SQLException;

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
            DirectDbSiteSurveyor surveyor = new DirectDbSiteSurveyor();
            Manager.initialize(surveyor, ui);
            ui.showMainForm();
        }
        catch (SQLException e)
        {
            ui.showConnectionErrorMessage(e);
            System.exit(1);
        }
    }
}
