package chess_board;
import java.awt.*;
import java.util.Map;

import javax.swing.*;

public class ActivePanel extends JPanel{
    private Map<Point, StringPair> pointStatusMap;
    private HexagonPanel hexPanel;

    public ActivePanel(Map<Point, StringPair> pointStatusMap) {
        setOpaque(false);
        this.pointStatusMap = pointStatusMap;
    }

    public void setStatusMap(Map<Point, StringPair> pointStatusMap) {
        this.pointStatusMap = pointStatusMap;
    }

    public void paintReaction(Graphics g){
        for (Point key : this.pointStatusMap.keySet()) {
            // 使用 key
            StringPair status = pointStatusMap.get(key);
            if (status.matches("empty", "ready_to_reach")) {
                Polygon hexagon = hexPanel.generateHexagon(key.x, key.y);
                g.setColor(Color.RED);
                g.drawPolygon(hexagon);
            }
            else if (status.matches("red", "ready_to_invert") || status.matches("blue", "ready_to_invert")) {
                Polygon hexagon = hexPanel.generateHexagon(key.x, key.y);
                g.setColor(Color.WHITE);
                g.drawPolygon(hexagon);
            }
        }
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintReaction(g);
    }
    
}
