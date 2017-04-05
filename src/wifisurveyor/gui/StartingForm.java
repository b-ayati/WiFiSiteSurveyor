package wifisurveyor.gui;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;
import wifisurveyor.Manager;

import javax.swing.*;


/**
 * Created by Behrouz on 13/03/2017.
 */
public class StartingForm
{
    JPanel rootPanel;
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
        if(prj.equals(""))
            JOptionPane.showMessageDialog(null, "please enter a valid name for your project.");
        else
        {
            Manager.getSurveyor().setContext(floor_plan, prj);
            SurveyingDialog dialog = new SurveyingDialog();
            dialog.pack();
            dialog.setTitle(prj + ": " + floor_plan);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            if(prjNameComboBox.getSelectedIndex() == -1)        //an ugly way to keep the combo box updated!
                prjNameComboBox.addItem(prj);
        }
    }

    private void createUIComponents() throws SQLException {
        planComboBox = new JComboBox(Manager.getSurveyor().getFloorPlanNames());
        prjNameComboBox = new JComboBox(Manager.getSurveyor().getSurveyNames());
        planComboBox.setPrototypeDisplayValue("aaaaaaaaaaaaaaaaa");
        prjNameComboBox.setPrototypeDisplayValue("aaaaaaaaaaaaaaaaa");
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
