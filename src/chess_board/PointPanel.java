package chess_board;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;  

public class PointPanel extends JPanel{
    private List<Point> flagPoints;
    // Map the status of each point
    private Map<Point, StringPair> pointStatusMap;

    public PointPanel(List<Point> flagPoints) {
        setOpaque(false); // 保证面板透明
        this.flagPoints = flagPoints;
        this.pointStatusMap = new HashMap<>();
        //empty, red, blue || can_be_reach, locked, can_be_invert, ready_to_reach
        for (Point p : flagPoints) {
            this.pointStatusMap.put(p, new StringPair("empty", "can_be_reach"));
        }
    }
    
    public void paintOnpanel(){
        repaint();
    }

    // Paint the flags
    public void paintPoints(Graphics g) {
        for (Point p : this.flagPoints) {
            StringPair status = this.pointStatusMap.get(p);          
            if (status.matches("empty","can_be_reach")) {
                g.setColor(Color.GREEN);
                g.fillOval(p.x - 2, p.y - 2, 4, 4);
            }
            else if (status.matches("empty","locked")) {
            }  
            else { }

            if (status.matches("red", "can_be_invert")) {
                g.setColor(Color.RED);
                g.fillOval(p.x - 13, p.y - 13, 26, 26);
            }
            else if (status.matches("blue", "can_be_invert")) {
                g.setColor(Color.BLUE);
                g.fillOval(p.x - 13, p.y - 13, 26, 26);
            } 
            else { }
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
    public void setPointStatus(Point point, StringPair status) {
        System.out.print(point);
        System.out.println(status);
        this.pointStatusMap.put(point, status);
        // for (Point p : this.flagPoints) {
        //     if (!isReachable(p)) {
        //         this.pointStatusMap.put(p, "can_be_reach");
        //     }
        // }
    }

    // Get the status of the point
    public StringPair getPointStatus(Point point) {
        return this.pointStatusMap.get(point);
    }

    public Map<Point, StringPair> getPointStatusMap() {
        return this.pointStatusMap;
    }

    // find the neighbors of the point
    public List<Point> findNeighbors(Point center) {
        List<Point> poss_neighbors = new ArrayList<>( );
        poss_neighbors.add(new Point(center.x - 18, center.y - 30));
        poss_neighbors.add(new Point(center.x + 18, center.y - 30));
        poss_neighbors.add(new Point(center.x - 36, center.y));
        poss_neighbors.add(new Point(center.x + 36, center.y));
        poss_neighbors.add(new Point(center.x - 18, center.y + 30));
        poss_neighbors.add(new Point(center.x + 18, center.y + 30));
        List<Point> real_neighbors = new ArrayList<>( );
        for (Point p : this.flagPoints) {
            if (poss_neighbors.contains(p)) {
                real_neighbors.add(p);
            }
        }     
        return real_neighbors;
    }

    public boolean isReachable(Point center) {
        List<Point> neighbors = findNeighbors(center);
        if (!this.pointStatusMap.get(center).matches1st("empty")) {
            return false;
        }
        for (Point p : neighbors) {
            if (this.pointStatusMap.get(p).matches1st("empty")) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("paintComponent");
        super.paintComponent(g);
        paintPoints(g);
        //paintReachableFlags(g);
    }
        
}
