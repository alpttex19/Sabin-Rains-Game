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
    private HexagonPanel hexPanel;
    private Color nextColor = Color.RED;
    private int redPoints;
    private int bluePoints;

    public PointPanel(List<Point> flagPoints, HexagonPanel hexPanel) {
        setOpaque(false); // 保证面板透明
        this.flagPoints = flagPoints;
        this.hexPanel = hexPanel;
        this.pointStatusMap = new HashMap<>();
        //empty, red, blue || can_be_reach,can_not_reach, locked
        for (Point p : flagPoints) {
            this.pointStatusMap.put(p, new StringPair("empty", "can_be_reach"));
        }
    }
    

    // Paint the flags
    public void paintPoints(Graphics g) {
        for (Point p : this.flagPoints) {
            StringPair status = this.pointStatusMap.get(p);
            if (status.matches2nd("locked")){
                Color semiTransparentGray = new Color(128, 128, 128, 170);
                g.setColor(semiTransparentGray);
                Polygon hexagon = hexPanel.generateHexagon(p.x, p.y);
                g.fillPolygon(hexagon);
            }

            if (status.matches("empty","can_be_reach")) {
                g.setColor(Color.GREEN);
                g.fillOval(p.x - 2, p.y - 2, 4, 4);
            }

            else if (status.matches1st("red")) {
                g.setColor(Color.RED);
                g.fillOval(p.x - 13, p.y - 13, 26, 26);
            }
            else if (status.matches1st("blue")) {
                g.setColor(Color.BLUE);
                g.fillOval(p.x - 13, p.y - 13, 26, 26);
            } 
            else { }
        }
        // on the right top corner, show the next color
        g.setColor(nextColor);
        g.fillOval(400, 80, 26, 26);

        // on the right top show the number of red and blue points
        g.setColor(Color.BLACK);
        g.drawString("Red: " + redPoints, 300, 80);
        g.drawString("Blue: " + bluePoints, 300, 120);
    }

    // Set the status of the point
    public void setPointStatus(Point centerPoint, StringPair status, List<Point> invertedPoints) {
        List<Point> lockedPoints = new ArrayList<>(this.flagPoints);
        List<Point> neiNeighbourPoints = new ArrayList<>();
        this.pointStatusMap.put(centerPoint, status);
        // check if the game is over
        boolean gameOver = true;
        this.redPoints = 0;
        this.bluePoints = 0;
        // invert the points
        for (Point p : invertedPoints) {
            this.pointStatusMap.put(p, status);
        }
        // show the next color on the right top corner
        this.nextColor = status.getFirst().equals("red") ? Color.BLUE : Color.RED;
        // find the reachable points
        for (Point p : this.flagPoints) {
            if (isReachable(p)) {
                StringPair currentStatus = new StringPair("empty", "can_be_reach");
                this.pointStatusMap.put(p, currentStatus);
                gameOver = false;
            }
            else {
                StringPair currentStatus = this.pointStatusMap.get(p);
                if (currentStatus.matches1st("empty")) {
                    StringPair newStatus = new StringPair(currentStatus.getFirst(), "can_not_reach");
                    this.pointStatusMap.put(p, newStatus);
                }
            }
            if ( this.pointStatusMap.get(p).getFirst() == "red"){
                this.redPoints += 1;
            }
            else if (this.pointStatusMap.get(p).getFirst() == "blue"){
                this.bluePoints += 1;
            }
        }
        if (gameOver) {
            // pop up a new gui to show the result
            JOptionPane.showMessageDialog(null, "WINNER:"+ (this.redPoints > this.bluePoints ? "RED" : "BLUE") + " WIN!", "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
        }

        // find the locked points
        for (Point p : this.flagPoints) {
            if (isReachable(p)) {
                List<Point> neighbours = findNeighbors(p);
                for (Point n : neighbours) {
                    List<Point> nei = findNeighbors(n);
                    for (Point nn : nei) {
                        if (!neiNeighbourPoints.contains(nn)) {
                            neiNeighbourPoints.add(nn);
                        }
                    }
                }
            }
        }
        lockedPoints.removeAll(neiNeighbourPoints);
        for (Point p : lockedPoints) {
            StringPair currentStatus = this.pointStatusMap.get(p);
            StringPair newStatus = new StringPair(currentStatus.getFirst(), "locked");
            this.pointStatusMap.put(p, newStatus);
        }

        // check if the game is over
        

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
        List<Point> real_neighbors = new ArrayList<>( );
        for (Point p : this.flagPoints) {
            if (Math.hypot(center.x - p.x, center.y - p.y) < 40) {
                real_neighbors.add(p);
            }
        }    
        real_neighbors.remove(center); 
        return real_neighbors;
    }

    public boolean isReachable(Point center) {
        List<Point> neighbors = findNeighbors(center);
        if (!this.pointStatusMap.get(center).matches("empty", "can_be_reach")) {
            return false;
        }
        for (Point p : neighbors) {
            if (this.pointStatusMap.get(p).matches("empty", "can_be_reach")) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintPoints(g);
        //paintReachableFlags(g);
    }
        
}
