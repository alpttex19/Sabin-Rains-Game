package chess_board;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;  

public class Flags {
    private List<Point> flagPoints;
    private int padding = 16;
    // Map the status of each point
    private Map<Point, String> pointStatusMap;

    public Flags(List<Point> flagPoints) {
        this.flagPoints = flagPoints;
        this.pointStatusMap = new HashMap<>();
        for (Point p : flagPoints) {
            this.pointStatusMap.put(p, "empty");
        }
    }
    // Paint the flags
    public void paintPoints(Graphics g) {
        for (Point p : this.flagPoints) {
            String status = this.pointStatusMap.get(p);
            if (status.equals("empty")) {
                continue;
            }
            else if (status.equals("red")) {
                g.setColor(Color.RED);
            }
            else if (status.equals("blue")) {
                g.setColor(Color.BLUE);
            }
            g.fillOval(p.x - 5, p.y - 5, 10, 10);
        }
    }
    // Paint the reachable flags
    public void paintReachableFlags(Graphics g, List<Point> points) {
        for (Point p : points) {
            if (isReachable(p, points)) {
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

    // given the center point, return the six points of the hexagon
    public List<Point> returnHexagonPoints(Point center, List<Point> points) {
        int x = center.x;
        int y = center.y;
        points.add(new Point(x, y - padding));
        points.add(new Point(x + (int)(padding * Math.sqrt(3) / 2), y - padding / 2));
        points.add(new Point(x + (int)(padding * Math.sqrt(3) / 2), y + padding / 2));
        points.add(new Point(x, y + padding));
        points.add(new Point(x - (int)(padding * Math.sqrt(3) / 2), y + padding / 2));
        points.add(new Point(x - (int)(padding * Math.sqrt(3) / 2), y - padding / 2));

        return points;
    }

    public boolean isReachable(Point p, List<Point> points) {
        List<Point> pointsNearby = returnHexagonPoints(p, points);

        if (!this.pointStatusMap.get(p).equals("empty")) {
            return false;
        }
        for (Point point : pointsNearby) {
            if (this.pointStatusMap.get(point).equals("empty")) {
                return true;
            }
        }
        return false;
    }
        
}
