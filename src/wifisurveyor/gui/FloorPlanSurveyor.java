package wifisurveyor.gui;

import wifisurveyor.PlainTextTable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Behrouz Ayati on 3/7/2017.
 */

public class FloorPlanSurveyor implements ImprintableImage.Handler
{
    private Component parent;

    public FloorPlanSurveyor(Component parent)
    {
        this.parent = parent;
    }

    @Override
    public boolean addPoint(Point2D p)
    {
        try
        {
            GUI.getInstance().getWiFiSurveyor().scan(p);
            return true;
        }
        catch (Exception e)
        {
            parent.setEnabled(true); //to prevent parent dialog from minimizing
            GUI.getInstance().showGeneralErrorMessage(e);
            return false;
        }
    }

    @Override
    public boolean removePoint(Point2D p)
    {
        try
        {
            parent.setEnabled(true);
            int option = JOptionPane.showOptionDialog(
                    parent,
                    "Are you sure you want to permanently delete this point and its data?",
                    "Delete Point",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null);
            if (option == JOptionPane.YES_OPTION)
            {
                GUI.getInstance().getWiFiSurveyor().remove(p);
                return true;
            }
            else
                return false;
        }
        catch (Exception e)
        {
            GUI.getInstance().showGeneralErrorMessage(e);
            return false;
        }
    }

    @Override
    public boolean selectPoint(Point2D p)
    {
        try
        {
            PlainTextTable textTable = GUI.getInstance().getWiFiSurveyor().getData(p);
            JTable table = new JTable(textTable.getData(), textTable.getColumnNames());
            table.setEnabled(false);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            JOptionPane.showMessageDialog(parent, scrollPane, String.format("Data Collected at [%.3f,%.3f]", p.getX(), p.getY()), JOptionPane.PLAIN_MESSAGE);
            return true;
        }
        catch (Exception e)
        {
            GUI.getInstance().showGeneralErrorMessage(e);
            return false;
        }
    }

    @Override
    public Point2D[] getInitialPoints()
    {
        try
        {
            return GUI.getInstance().getWiFiSurveyor().getCurrentPoints();
        }
        catch (Exception e)
        {
            GUI.getInstance().showGeneralErrorMessage(e);
            return null;
        }
    }


}
