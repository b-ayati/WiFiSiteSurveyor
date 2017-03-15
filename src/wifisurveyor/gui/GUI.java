package wifisurveyor.gui;

import wifisurveyor.UI;

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
public class GUI implements UI
{
    private SurveyingDialog currSurveyingDialog;
    public void reportStatus(String status)
    {
        currSurveyingDialog.showStatus(status);
    }

    public void setCurrSurveyingDialog(SurveyingDialog currSurveyingDialog)
    {
        this.currSurveyingDialog = currSurveyingDialog;
    }


}
