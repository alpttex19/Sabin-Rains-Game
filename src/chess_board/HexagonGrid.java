package chess_board;
import java.awt.*;
import java.awt.event.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


public class HexagonGrid extends JPanel{
    private int size = 20; // size of the small hexagons
    private int padding = 16; // space between hexagons
    public HexagonGrid(){
        super();
    }

    public Polygon generateHexagon(int centerX, int centerY, int size){
        // Create a new polygon
        int[] x = new int[6];
        int[] y = new int[6];

        for (int i = 0; i < 6; i++){
            x[i] = (int) (centerX + size * Math.cos(i * 2 * Math.PI / 6 + Math.PI / 6));
            y[i] = (int) (centerY + size * Math.sin(i * 2 * Math.PI / 6 + Math.PI / 6));
        }

        return new Polygon(x, y, x.length);
    }

    public List<Point> returnAllgrid(int centerX, int centerY){                
        int[] num = {5,6,7,8,9,8,7,6,5};
        List<Point> points = new ArrayList<>();
        for (var i = 0; i < num.length; i++){
            int wid = num[i];
            for (int j = 0; j <= wid; j ++) {
                // Generate the small hexagon
                int x = centerX + (int)(((j)-(wid/2.0)) * (size + padding));
                int y = centerY + (int)((i-4) * (size + padding - 5.6));
                // collect the coordinates of the small hexagons
                points.add(new Point(x, y));
            }
        }
        return points;
    }
    
    public void paintPolygon(Graphics g, Polygon p, Color c){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(c);
        g2.setStroke(new BasicStroke(3)); // Set the line thickness to 3
        g.drawPolygon(p);
    }

    public void fillPolygon(Graphics g, Polygon p, Color c){
        super.paintComponent(g);
        g.setColor(c);
        g.fillPolygon(p);
    }

    public void paintAllgrid(Graphics g, List<Point> points){
        for (Point point : points){
            Polygon hexagon = generateHexagon(point.x, point.y, size);
            paintPolygon(g, hexagon, Color.BLACK);
        }
    }

}
