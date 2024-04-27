package chess_board;
import java.awt.*;
import java.util.Map;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

public class ActivePanel extends JPanel{
    private Map<Point, StringPair> pointStatusMap;
    private HexagonPanel hexPanel;
    private PointPanel pointPanel;
    private String pointColor;
    private int mouseOnX = -1;
    private int mouseOnY = -1;

    public ActivePanel(Map<Point, StringPair> pointStatusMap, List<Point> flagPoints) {
        setOpaque(false);
        this.pointStatusMap = pointStatusMap;
    }

    public void setStatusMap(Map<Point, StringPair> pointStatusMap) {
        this.pointStatusMap = pointStatusMap;
    }

    public void paintHexagons(HexagonPanel hexPanel, PointPanel pointPanel, int x, int y, String pointColor){
        this.hexPanel = hexPanel;
        this.pointPanel = pointPanel;
        this.pointColor = pointColor;
        this.mouseOnX = x;
        this.mouseOnY = y;
        repaint();
    }

    // Function to sort neighbors clockwise from a starting point
    public List<Point> sortNeighborsClockwise(Point center, List<Point> neighbors, Point starPoint) {
        neighbors.sort(new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                // Calculate angles relative to the center point
                double angle1 = Math.atan2(p1.y - center.y, p1.x - center.x);
                double angle2 = Math.atan2(p2.y - center.y, p2.x - center.x);
                
                // Compare angles to sort in clockwise order
                return Double.compare(angle1, angle2);
            }
        });
        int startIndex = neighbors.indexOf(starPoint);
        for (int i = 0; i < startIndex; i++) {
            Point temp = neighbors.get(0);
            neighbors.remove(0);
            neighbors.add(temp);
        }
        return neighbors;
    }

    public List<Point> canInvertpoint(){
        List<Point> poss_to_invert = new ArrayList<>();
        Point centerPoint = new Point(this.mouseOnX, this.mouseOnY);
        if (!this.pointStatusMap.get(centerPoint).matches("empty", "can_be_reach")) {
            return poss_to_invert;
        }
        String anothorColor = this.pointColor.equals("red") ? "blue" : "red";
        // find the points that can be inverted
        List<Point> pointsNeighbour = pointPanel.findNeighbors(centerPoint);
        for (Point pN : pointsNeighbour) {
            List<Point> pNneighbours = this.sortNeighborsClockwise(pN, pointPanel.findNeighbors(pN), centerPoint);
            pNneighbours.remove(centerPoint);
            List<Point> clockList = new ArrayList<>();
            for (Point pNn : pNneighbours) {
                StringPair status = this.pointStatusMap.get(pNn);
                if (status.matches1st(anothorColor)) {
                    clockList.add(pNn);
                }
                else if (status.matches1st("empty")) {
                    clockList.clear();
                    break;
                }
                else {
                    break;
                }
            }
            poss_to_invert.addAll(clockList);
            List<Point> interclockList = new ArrayList<>();
            for (int i = pNneighbours.size() - 1; i >= 0; i--) {
                Point pNn = pNneighbours.get(i);
                StringPair status = this.pointStatusMap.get(pNn);
                if (status.matches1st(anothorColor)) {
                    interclockList.add(pNn);
                }
                else if (status.matches1st("empty")) {
                    interclockList.clear();
                    break;
                }
                else {
                    break;
                }
            }
            poss_to_invert.addAll(interclockList); 
        }
        return poss_to_invert; 
    }

    public void paintReaction(Graphics g2){
        Graphics2D g = (Graphics2D) g2;
        g.setStroke(new BasicStroke(3)); // Set the line thickness to 3
        if (this.mouseOnX == -1 || this.mouseOnY == -1) {
            return;
        }
        StringPair mouseOnStatus = this.pointStatusMap.get(new Point(this.mouseOnX, this.mouseOnY));
        if (mouseOnStatus.matches("empty", "can_be_reach")) {
            Polygon hexagon = this.hexPanel.generateHexagon(this.mouseOnX, this.mouseOnY);
            g.setColor(Color.RED);
            g.drawPolygon(hexagon);
        }
        for (Point p : canInvertpoint()) {
            Polygon hexagon = hexPanel.generateHexagon(p.x, p.y);
            g.setColor(Color.WHITE);
            g.drawPolygon(hexagon);
        }
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintReaction(g);
    }
    
}
