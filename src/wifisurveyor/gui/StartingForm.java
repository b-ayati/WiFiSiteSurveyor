package wifisurveyor.gui;

import wifisurveyor.Manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Behrouz on 13/03/2017.
 */
public class StartingForm
{
    JPanel rootPanel;
    private JButton okButton;
    private JComboBox planComboBox;
    private JComboBox surveyNameComboBox;


    public StartingForm()
    {
        okButton.addActionListener(e -> onStart());
    }

    private void onStart()
    {
        Manager.getSurveyor().setContext((String) planComboBox.getSelectedItem(), (String) surveyNameComboBox.getSelectedItem());
        SurveyingDialog dialog = new SurveyingDialog();
        dialog.pack();
        dialog.setTitle("asdsads");
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void createUIComponents()
    {
        planComboBox = new JComboBox(Manager.getSurveyor().getFloorPlanNames());
        surveyNameComboBox = new JComboBox(Manager.getSurveyor().getSurveyNames());
        planComboBox.setPrototypeDisplayValue("aaaaaaaaaaaaaaaaa");
        surveyNameComboBox.setPrototypeDisplayValue("aaaaaaaaaaaaaaaaa");
    }

    public static void createAndShow()
    {
        //Create and set up the window.
        JFrame main_frame = new JFrame("WiFi Site Surveyor (beta)");
        main_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        main_frame.setContentPane(new StartingForm().rootPanel);

        //Display the window.
        main_frame.pack();
        main_frame.setLocationRelativeTo(null);
        main_frame.setVisible(true);
    }
}
