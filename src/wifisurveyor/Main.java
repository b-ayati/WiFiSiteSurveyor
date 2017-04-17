package wifisurveyor;

import wifisurveyor.core.DirectDbSiteSurveyor;
import wifisurveyor.gui.GUI;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("config.txt"))))
        {
            DirectDbSiteSurveyor.initialize(reader.readLine(), reader.readLine());
            ui.registerWiFiSurveyor(DirectDbSiteSurveyor.getInstance());
            DirectDbSiteSurveyor.getInstance().registerUi(ui);
            ui.showMainForm();
        }
        catch (SQLException e)
        {
            ui.showConnectionErrorMessage(e);
            System.exit(1);
        }
        catch (IOException e)
        {
            ui.showFatalErrorMessage(e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
