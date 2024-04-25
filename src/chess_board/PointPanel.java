package chess_board;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;  

public class PointPanel extends JPanel{
    private List<Point> flagPoints;
    private int padding = 16;
    // Map the status of each point
    private Map<Point, String> pointStatusMap;

    public PointPanel(List<Point> flagPoints) {
        setOpaque(false); // 保证面板透明
        this.flagPoints = flagPoints;
        this.pointStatusMap = new HashMap<>();
        for (Point p : flagPoints) {
            this.pointStatusMap.put(p, "empty");
        }
    }
    
    public void paintOnpanel(){
        repaint();
    }

    // Paint the flags
    public void paintPoints(Graphics g) {
        for (Point p : this.flagPoints) {
            String status = this.pointStatusMap.get(p);
            if (status.equals("empty")) {
                if (isReachable(p)) {
                    g.setColor(Color.GREEN);
                    g.fillOval(p.x - 5, p.y - 5, 10, 10);
                    continue;
                }    
            }
            else if (status.equals("red")) {
                g.setColor(Color.RED);
            }
            else if (status.equals("blue")) {
                g.setColor(Color.BLUE);
            }
            g.fillOval(p.x - 13, p.y - 13, 26, 26);
        }
    }
    // Paint the reachable flags
    public void paintReachableFlags(Graphics g) {
        for (Point p : this.flagPoints) {
            if (isReachable(p)) {
                g.setColor(Color.GREEN);
                g.fillOval(p.x - 2, p.y - 2, 4, 4);
            }
        }
    }

    // Set the status of the point
    public void setPointStatus(Point point, String status) {
        this.pointStatusMap.put(point, status);
    }

    // Get the status of the point
    public String getPointStatus(Point point) {
        return this.pointStatusMap.get(point);
    }

    // find the neighbors of the point
    public List<Point> findNeighbors(Point center) {
        List<Point> neighbors = new ArrayList<>( );
        neighbors.add(new Point(center.x - 18, center.y - 30));
        neighbors.add(new Point(center.x + 18, center.y - 30));
        neighbors.add(new Point(center.x - 36, center.y));
        neighbors.add(new Point(center.x + 36, center.y));
        neighbors.add(new Point(center.x - 18, center.y + 30));
        neighbors.add(new Point(center.x + 18, center.y + 30));
        return neighbors;
    }

    public boolean isReachable(Point center) {
        List<Point> neighbors = findNeighbors(center);
        if (!this.pointStatusMap.get(center).equals("empty")) {
            return false;
        }
        for (int i = 0; i < 6; i++) {
            if (this.flagPoints.contains(neighbors.get(i))) {
                if (this.pointStatusMap.get(neighbors.get(i)).equals("empty")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintPoints(g);
        paintReachableFlags(g);
    }
        
}
