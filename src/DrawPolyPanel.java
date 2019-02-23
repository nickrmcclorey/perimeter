import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.List;
import javax.swing.JPanel;
import com.sun.javafx.geom.Point2D;
import javafx.scene.shape.Circle;

public class DrawPolyPanel extends JPanel {

    public List<Point> points;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int k = 0; k < points.size(); k++) {
            g.fillOval(points.get(k).x, points.get(k).y, 8, 8);
        }

        Polygon perimeter = Perimeter.findPerimeter(points);
        g.drawPolygon(perimeter);
    }
}