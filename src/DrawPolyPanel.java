import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.List;
import javax.swing.JPanel;

public class DrawPolyPanel extends JPanel {

    public List<Point> points;
    public List<Polygon> polygons;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int k = 0; k < points.size(); k++) {
            g.fillOval(points.get(k).x, points.get(k).y, 8, 8);
        }

        for (int k = 0; k < polygons.size(); k++) {
            g.drawPolygon(polygons.get(k));
        }
    }
}