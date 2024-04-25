package chess_board;
import java.awt.*;
import java.awt.event.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


public class HexagonPanel extends JPanel{
    private int size = 20; // size of the small hexagons
    private int padding = 16; // space between hexagons
    private Color hexColor;
    public HexagonPanel(){
        hexColor = Color.BLACK;
        setOpaque(false);
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
    

    public void fillPolygon(Graphics g, Polygon p, Color c){
        super.paintComponent(g);
        g.setColor(c);
        g.fillPolygon(p);
    }

    public void paintAllgrid(int x , int y){
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        // paint the hexagons
        List<Point> points = returnAllgrid(300, 300);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(this.hexColor);
        g2.setStroke(new BasicStroke(3)); // Set the line thickness to 3
        for (Point point : points ) {
            Polygon hexagon = generateHexagon(point.x, point.y, size);
            g.drawPolygon(hexagon);
        }
    }

}
