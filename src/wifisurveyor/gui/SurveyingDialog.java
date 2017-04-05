package wifisurveyor.gui;

import wifisurveyor.Manager;

import javax.swing.*;
import java.awt.*;

public class SurveyingDialog extends JDialog
{
    private JPanel contentPane;
    private JButton closeButton;
    private ImprintableImage imprintableImg;
    private JLabel statusLabel;

    public SurveyingDialog()
    {
        setContentPane(contentPane);
        setModal(true);
        int height = (int) Math.min(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.85, this.getPreferredSize().getHeight());
        int width = (int) Math.min(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.85, this.getPreferredSize().getWidth());
        setPreferredSize(new Dimension(width, height));
        ((GUI) Manager.getUI()).setCurrSurveyingDialog(this);
        closeButton.addActionListener(e -> dispose());
    }

    private void createUIComponents()
    {
        imprintableImg = new ImprintableImage(
                new ImprintableImage.Configuration(),
                new FloorPlanSurveyor(),
                Manager.getSurveyor().getCurrentFloorPlanImg());
    }


    public void showStatus(String status)
    {
        //statusLabel.setIcon(new ImageIcon(getClass().getResource("resources/icons/green.gif")));
        statusLabel.setText("• " + status);
    }

}
