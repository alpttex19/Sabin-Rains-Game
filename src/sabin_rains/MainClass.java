package sabin_rains;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import sabin_rains.*;

import java.util.List;
import java.util.Map;


// Create a new chess board
class MainClass extends JFrame{
    private int width = 600;
    private int height = 600;
    private LayeredPane layeredPane;
    private HexagonPanel preDrawnHexPanel;
    private PointPanel pointsPanel;
    private ImagePanel imagePanel;
    private ActivePanel activePanel;

    private List<Point> points;
    private Map<Point, StringPair> pointStatusMap;
    private List<Point> invertedPoints;

    String pointColor = "red";

    public MainClass(){
        super();
        // Set the size of the chess board
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layeredPane = new LayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));
        add(layeredPane);
        setVisible(true);
    }
    
    private class LayeredPane extends JLayeredPane {
        public LayeredPane() {
            imagePanel = new ImagePanel();
            imagePanel.setBounds(0, 0, width, height);
            this.add(imagePanel, JLayeredPane.DEFAULT_LAYER);

            preDrawnHexPanel = new HexagonPanel();
            preDrawnHexPanel.setBounds(0, 0, width, height);
            // preDrawnHexPanel.repaint();
            this.add(preDrawnHexPanel, JLayeredPane.PALETTE_LAYER);


            points = preDrawnHexPanel.getAllpoints(300, 300);
            pointsPanel = new PointPanel(points, preDrawnHexPanel);
            pointsPanel.setBounds(0, 0, width, height);
            this.add(pointsPanel, JLayeredPane.MODAL_LAYER);

            pointStatusMap = pointsPanel.getPointStatusMap();
            activePanel = new ActivePanel(pointStatusMap, points);
            activePanel.setBounds(0, 0, width, height);
            this.add(activePanel, JLayeredPane.POPUP_LAYER);

            addMouseListener(new MouseAdapter() {
                int click_mouseX;
                int click_mouseY;
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) { // Check if left button clicked
                        click_mouseX = e.getX();
                        click_mouseY = e.getY();
                        for (Point p : points) {
                            double distance = Math.hypot(click_mouseX - p.x, click_mouseY - p.y);
                            if (distance < 17 && (pointsPanel.getPointStatus(p).matches2nd("can_be_reach"))) {
                                StringPair status = new StringPair(pointColor, "can_not_reach");
                                pointsPanel.setPointStatus(p, status, invertedPoints);
                                // for (Point point : invertedPoints) {
                                //     pointsPanel.setPointStatus(point, status);
                                // }
                                activePanel.setStatusMap(pointsPanel.getPointStatusMap());
                                pointColor = pointColor.equals("red") ? "blue" : "red";      
                                pointsPanel.repaint();
                                break;
                            }
                        }
                    }
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                int lastX = -1;
                int lastY = -1;
                int mouseX;
                int mouseY;
                public void mouseMoved(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    for (Point p : points) {
                        double distance = Math.hypot(mouseX - p.x, mouseY - p.y);
                        if (distance < 15 && (p.x != lastX || lastY != p.y)) {
                            activePanel.paintHexagons(preDrawnHexPanel, pointsPanel, p.x, p.y, pointColor);
                            lastX = p.x;
                            lastY = p.y;
                            invertedPoints = activePanel.canInvertpoint();
                            activePanel.repaint();
                            break;
                        }
                    }
                }
            });
            
        }
    }

    // Create a new chess board
    public static void main(String[] args) {
        // Create a new chess board
        new MainClass();
    }
}