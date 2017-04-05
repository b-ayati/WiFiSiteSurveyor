package wifisurveyor.gui;

import wifisurveyor.Manager;
import wifisurveyor.PlainTextTable;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.sql.SQLException;

/**
 * Created by ayati on 3/7/2017.
 */
public class FloorPlanSurveyor implements ImprintableImage.Handler
{
    ImprintableImage parent;

    @Override
    public void setParent(ImprintableImage parent)
    {
        this.parent = parent;
    }

    @Override
    public boolean addPoint(Point2D p)
    {
        try
        {
            Manager.getSurveyor().scan(p);
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean removePoint(Point2D p) throws SQLException {
        int option = JOptionPane.showOptionDialog(
                parent,
                "Are you sure you want to permanently delete this point?",
                "Delete Point",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);
        if(option == JOptionPane.YES_OPTION)
        {
            Manager.getSurveyor().remove(p);
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean selectPoint(Point2D p) throws SQLException {
        PlainTextTable textTable = Manager.getSurveyor().getData(p);
        JTable table = new JTable(textTable.getData(), textTable.getColumnNames());
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        JOptionPane.showMessageDialog(parent, scrollPane, String.format("Data saved at [%3f,%3f]", p.getX(), p.getY()) , JOptionPane.PLAIN_MESSAGE);
        return true;
    }
}
