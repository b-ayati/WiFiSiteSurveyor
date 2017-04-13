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
        GUI.initialize();
        GUI ui = GUI.getInstance();
        try
        {
            DirectDbSiteSurveyor.initialize();
            ui.registerWiFiSurveyor(DirectDbSiteSurveyor.getInstance());
            DirectDbSiteSurveyor.getInstance().registerUi(ui);
            ui.showMainForm();
        }
        catch (SQLException e)
        {
            ui.showConnectionErrorMessage(e);
            System.exit(1);
        }
    }
}
