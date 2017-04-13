package wifisurveyor.gui;


import javax.swing.*;
import java.awt.*;

public class SurveyingDialog extends JDialog
{
    public static final double SCREEN_FILL_HEIGHT = 0.9;
    public static final double SCREEN_FILL_WIDTH = 0.7;
    private JPanel contentPane;
    private JButton closeButton;
    private ImprintableImage imprintableImg;
    private JLabel statusLabel;

    public SurveyingDialog()
    {
        setContentPane(contentPane);
        setModal(true);
        int height = (int) Math.min(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * SCREEN_FILL_HEIGHT, this.getPreferredSize().getHeight());
        int width = (int) Math.min(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * SCREEN_FILL_WIDTH, this.getPreferredSize().getWidth());
        setPreferredSize(new Dimension(width, height));
        GUI.getInstance().setCurrSurveyingDialog(this);
        closeButton.addActionListener(e -> dispose());
    }

    private void createUIComponents() throws Exception
    {
        imprintableImg = new ImprintableImage(
                this,
                new ImprintableImage.Configuration(),
                new FloorPlanSurveyor(this),
                GUI.getInstance().getWiFiSurveyor().getCurrentFloorPlanImg());
    }

    public void showStatus(String status)
    {
        //statusLabel.setIcon(new ImageIcon(getClass().getResource("resources/icons/green.gif")));
        statusLabel.setText("• " + status);
    }

}
