import java.awt.Container;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

public class Perimeter {

    public static List<Point> getPoints() {
        Random prng = new Random();
        List<Point> allPoints = new ArrayList<Point>(20);

        for (int k = 0; k < 20; k++) {
            int x = prng.nextInt(800) + 100;
            int y = prng.nextInt(800) + 100;
            allPoints.add(new Point(x, y));
        }

        return allPoints;
    }

    public static Point highestPoint(List<Point> points) {
        Point highest = points.get(0);
        for (int k = 1; k < points.size(); k++) {
            if (points.get(k).y < highest.y) {
                highest = points.get(k);
            }
        }

        return highest;
    }

    public static double angleDifference(Point source, Point destination, double currentAngle) {
        double angle = Math.atan2((destination.y - source.y), (destination.x - source.x));
        while (angle > currentAngle) {
            angle -= (Math.PI * 2);
        }

        return currentAngle - angle; 
    }

    public static Point findNextPoint(List<Point> points, Point current, double currentAngle) {
        Point next = points.get(0);
        double maxAngle = angleDifference(current, points.get(0), currentAngle);

        for (int k = 1; k < points.size(); k++) {
            double ang = angleDifference(current, points.get(k), currentAngle);
            if (ang > maxAngle) {
               next = points.get(k);
               maxAngle = ang;
            }
        }

        return next;
    }

    public static Polygon findPerimeter(List<Point> points) {
        Polygon perimeter = new Polygon();
        Point currentPoint = highestPoint(points);
        Point highesPoint = currentPoint;
        double currentAngle = 0;
        perimeter.addPoint(currentPoint.x + 4, currentPoint.y + 4);

        Point nextPoint = findNextPoint(points, currentPoint, currentAngle);
        perimeter.addPoint(nextPoint.x + 4, nextPoint.y + 4);
        currentAngle = -1.0 * angleDifference(currentPoint, nextPoint, 0);
        currentPoint = nextPoint;

        int k = 0;
        while (currentPoint != highesPoint && k++ < 10) {
            nextPoint = findNextPoint(points, currentPoint, currentAngle);
            perimeter.addPoint(nextPoint.x + 4, nextPoint.y + 4);
            currentAngle = -1.0 * angleDifference(currentPoint, nextPoint, 0);
            currentPoint = nextPoint;
        }

        return perimeter;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Perimeter Finder");
        frame.setSize(1000, 1000);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            public void windowDeactivated(WindowEvent e) {
                // System.exit(0);
            }
        });
        MouseListener listener = new MouseListener(){    
            public void mouseClicked(MouseEvent e) {
                JFrame frame = (JFrame) e.getSource();
                DrawPolyPanel panel = (DrawPolyPanel) frame.getContentPane().getComponents()[0];
                panel.points.add(new Point(e.getX(), e.getY()));
                panel.polygons.set(0, Perimeter.findPerimeter(panel.points));
                panel.revalidate();
                panel.repaint();
            }

            public void mouseReleased(MouseEvent e) { }
            public void mousePressed(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
        };
        frame.addMouseListener(listener);

        

        Container contentPane = frame.getContentPane();
        DrawPolyPanel panel = new DrawPolyPanel();
        panel.points = getPoints();
        panel.polygons = new ArrayList<Polygon>();
        panel.polygons.add(Perimeter.findPerimeter(panel.points));
        contentPane.add(panel);

        frame.setVisible(true);
    }
}