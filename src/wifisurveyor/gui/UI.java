package wifisurveyor.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by ayati on 3/5/2017.
 */
public class UI
{
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI()
    {

        //Create and set up the window.
        JFrame main_frame = new JFrame("HelloWorldSwing");
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //.

        //JComponent newContentPane = MainForm;
        //ewContentPane.setOpaque(true); //content panes must be opaque
        main_frame.setContentPane(new StartingForm().rootPanel);
        //main_frame.setContentPane(new TestUI().root);

        //Display the window.
        main_frame.pack();
        main_frame.setVisible(true);
    }

}
