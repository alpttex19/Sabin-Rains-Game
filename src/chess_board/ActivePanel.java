package chess_board;
import java.awt.*;
import java.util.Map;

import javax.swing.*;

public class ActivePanel extends JPanel{
    private Map<Point, String> pointStatusMap;
    private HexagonPanel hexPanel;

    public ActivePanel(Map<Point, String> pointStatusMap) {
        setOpaque(false);
        this.pointStatusMap = pointStatusMap;
    }

    public void setStatusMap(Map<Point, String> pointStatusMap) {
        this.pointStatusMap = pointStatusMap;
    }

    public void paintReaction(Graphics g){
        for (Point key : this.pointStatusMap.keySet()) {
            // 使用 key
            String status = pointStatusMap.get(key);
            if (status.equals("ready_to_reach")) {
                Polygon hexagon = hexPanel.generateHexagon(key.x, key.y);
                g.setColor(Color.RED);
                g.drawPolygon(hexagon);
            }
            else if (status.equals("ready_to_invert")) {
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
