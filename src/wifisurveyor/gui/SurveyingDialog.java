package wifisurveyor.gui;

import wifisurveyor.Manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

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
        ((GUI) Manager.getUI()).setCurrSurveyingDialog(this);
        closeButton.addActionListener(e -> dispose());
    }

    private void createUIComponents()
    {
        try {
            imprintableImg = new ImprintableImage(
                    new ImprintableImage.Configuration(),
                    new FloorPlanSurveyor(),
                    Manager.getSurveyor().getCurrentFloorPlanImg(),
                    Manager.getSurveyor().getCurrentPoints());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showStatus(String status)
    {
        //statusLabel.setIcon(new ImageIcon(getClass().getResource("resources/icons/green.gif")));
        statusLabel.setText("• " + status);
    }

}
