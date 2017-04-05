package wifisurveyor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javax.swing.SwingUtilities.*;

/**
 * Created by ayati on 3/5/2017.
 */
public class ImprintableImage extends JComponent implements MouseListener
{
    public interface Handler
    {
        void setParent(ImprintableImage parent);

        boolean addPoint(Point2D p);

        boolean removePoint(Point2D p) throws SQLException;

        boolean selectPoint(Point2D p) throws SQLException;
    }

    public static class Configuration
    {
        private final double distancePrecision = 0.015;
        private final ImageIcon pointAddedIcon = new ImageIcon(getClass().getResource("resources/icons/checked.png"));
        private final ImageIcon pointSavingIcon  = new ImageIcon(getClass().getResource("resources/icons/ripple.gif"));
        private final ImageIcon pointRemovingIcon  = new ImageIcon(getClass().getResource("resources/icons/default.gif"));
        private final ImageIcon pointSelectedIcon  = new ImageIcon(getClass().getResource("resources/icons/magnify.gif"));
    }

    private class Point
    {
        private Point2D.Float normalizedPoint;
        private int x;
        private int y;
        private ImageIcon icon = null;

        Point(int x, int y)
        {
            this.x = x;
            this.y = y;
            float normalized_x = (float) x / (float) backgroundImage.getWidth(null);
            float normalized_y = (float) y / (float) backgroundImage.getHeight(null);
            this.normalizedPoint = new Point2D.Float(normalized_x, normalized_y);
        }

        Point(float normalized_x, float normalized_y)
        {
            this.normalizedPoint = new Point2D.Float(normalized_x, normalized_y);
            this.x = (int) (normalized_x * backgroundImage.getWidth(null));
            this.y = (int) (normalized_y * backgroundImage.getHeight(null));
        }

        public boolean isInside()
        {
            return (normalizedPoint.getX() >= 0 && normalizedPoint.getX() <= 1 &&
                    normalizedPoint.getY() >= 0 && normalizedPoint.getY() <= 1);
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point point = (Point) o;
            return this.normalizedPoint.distance(point.normalizedPoint) <= config.distancePrecision;
        }

        @Override
        public int hashCode()
        {
            throw new UnsupportedOperationException("hash code is not supported!");
        }

        void setIcon(ImageIcon icon)
        {
            this.icon = icon;
        }

        void paint(Component observer, Graphics g)
        {
            icon.paintIcon(observer, g, this.x - icon.getIconWidth() / 2, this.y - icon.getIconHeight() / 2);
        }
    }

    private Configuration config;
    private Handler handler;
    private Image backgroundImage;
    private List<ImprintableImage.Point> markedPoints = Collections.synchronizedList(new ArrayList<>());
    private boolean isReady = true;

    public ImprintableImage(Configuration config, Handler handler, Image backgroundImage, Point2D[] initialPoints)
    {
        this.handler = handler;
        handler.setParent(this);
        this.backgroundImage = backgroundImage;
        addMouseListener(this);
        this.config = config;
        for (Point2D initPt : initialPoints)
        {
            Point tempPt = new Point((float) initPt.getX(), (float) initPt.getY());
            tempPt.setIcon(config.pointAddedIcon);
            this.markedPoints.add(tempPt);
        }
        this.setPreferredSize(new Dimension(backgroundImage.getWidth(this), backgroundImage.getHeight(this)));
        this.setMaximumSize(this.getPreferredSize());
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(backgroundImage, 0, 0, this);
        for (Point marked_point : markedPoints)
            marked_point.paint(this, g);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        ImprintableImage.Point p = new ImprintableImage.Point(e.getX(), e.getY());
        if (isReady && p.isInside())
        {
            isReady = false;
             int index = markedPoints.lastIndexOf(p);
            if (index == -1)
            {
                if (isLeftMouseButton(e))
                {
                    markedPoints.add(p);
                    p.setIcon(config.pointSavingIcon);
                    new Thread(() ->
                    {
                        if (handler.addPoint(p.normalizedPoint))
                            p.setIcon(config.pointAddedIcon);
                        else
                            markedPoints.remove(markedPoints.size() - 1);
                        isReady = true;
                    }).start();
                }
                else
                    isReady = true;
            }
            else
            {
                Point selected = markedPoints.get(index);
                if (isRightMouseButton(e))
                {
                    selected.setIcon(config.pointRemovingIcon);
                    new Thread(() ->
                    {
                        try {
                            if (handler.removePoint(p.normalizedPoint))
                                markedPoints.remove(index);
                            else
                                selected.setIcon(config.pointAddedIcon);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        isReady = true;
                    }).start();
                }
                else
                {
                    selected.setIcon(config.pointSelectedIcon);
                    new Thread(() ->
                    {
                        try {
                            handler.selectPoint(p.normalizedPoint);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        selected.setIcon(config.pointAddedIcon);
                        isReady = true;
                    }).start();
                }
            }
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }


}
