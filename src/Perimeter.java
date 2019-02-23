import java.awt.Container;
import java.awt.Point;
import java.awt.Polygon;
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

    public static double angle(Point source, Point destination, double currentAngle) {
        double angle = Math.atan2((destination.y - source.y), (destination.x - source.x));
        while (angle > 0) {
            angle -= (Math.PI * 2);
        }
        if (angle == 0.0) {
            System.out.println("okay");
            System.out.println((destination.y - source.y));
            System.out.println((destination.x - source.x));
            System.out.println((destination.y - source.y) / (destination.x - source.x));
        }
        return angle; 
    }

    public static Point findNextPoint(List<Point> points, Point current, double currentAngle) {
        Point next = points.get(0);
        double maxAngle = angle(current, points.get(0), currentAngle);

        for (int k = 1; k < points.size(); k++) {
            if (points.get(k).x == current.x) {
                continue;
            }
            double ang = angle(current, points.get(k), currentAngle);
            System.out.println("ang: " + ang);
            if (ang > maxAngle) {
               next = points.get(k);
               maxAngle = ang;
            }
        }
        System.out.println();

        return next;
    }

    public static Polygon findPerimeter(List<Point> points) {
        Polygon perimeter = new Polygon();
        Point currentPoint = highestPoint(points);
        double currentAngle = 0;
        perimeter.addPoint(currentPoint.x, currentPoint.y);

        Point nextPoint = findNextPoint(points, currentPoint, currentAngle);
        perimeter.addPoint(nextPoint.x, nextPoint.y);
        perimeter.addPoint(950, 950);

        return perimeter;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Austin");
        frame.setSize(1000, 1000);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            public void windowDeactivated(WindowEvent e) {
                // System.exit(0);
            }
        });

        Container contentPane = frame.getContentPane();
        DrawPolyPanel panel = new DrawPolyPanel();
        panel.points = getPoints();
        contentPane.add(panel);

        frame.setVisible(true);
    }
}