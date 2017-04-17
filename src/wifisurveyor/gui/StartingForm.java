package wifisurveyor.gui;

import javax.swing.*;


/**
 * Created by Behrouz on 13/03/2017.
 */
public class StartingForm
{
    private JPanel rootPanel;
    private JButton okButton;
    private JComboBox planComboBox;
    private JComboBox prjNameComboBox;


    public StartingForm()
    {
        okButton.addActionListener(e -> onStart());
    }

    private void onStart()
    {
        String floor_plan = (String) planComboBox.getSelectedItem();
        String prj = (String) prjNameComboBox.getSelectedItem();
        if (prj == null || prj.equals(""))
            JOptionPane.showMessageDialog(null, "Please enter a valid name for your project.");
        else
        {
            GUI.getInstance().getWiFiSurveyor().setContext(floor_plan, prj);
            SurveyingDialog dialog = new SurveyingDialog();
            dialog.pack();
            dialog.setTitle(prj + ": " + floor_plan);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            if (prjNameComboBox.getSelectedIndex() == -1)        //an ugly way to keep the combo box updated!
                prjNameComboBox.addItem(prj);
        }
    }

    private void createUIComponents()
    {
        try
        {
            planComboBox = new JComboBox(GUI.getInstance().getWiFiSurveyor().getFloorPlanNames());

            prjNameComboBox = new JComboBox(GUI.getInstance().getWiFiSurveyor().getSurveyNames());
            planComboBox.setPrototypeDisplayValue("aaaaaaaaaaaaaaaaa");
            prjNameComboBox.setPrototypeDisplayValue("aaaaaaaaaaaaaaaaa");
        }
        catch (Exception e)
        {
            GUI.getInstance().showFatalErrorMessage(e);
        }
    }

    public static void createAndShow()
    {
        //Create and set up the window.
        JFrame main_frame = new JFrame("WiFi Site Surveyor");
        main_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        main_frame.setContentPane(new StartingForm().rootPanel);

        //Display the window.
        main_frame.pack();
        main_frame.setLocationRelativeTo(null);
        main_frame.setVisible(true);
    }
}
