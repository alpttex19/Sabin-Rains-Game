package chess_board;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.Map;


// Create a new chess board
class ChessBoard extends JFrame{
    private int width = 600;
    private int height = 600;
    private int click_mouseX = -1;
    private int click_mouseY = -1;
    //private JLabel countTxt;
    private LayeredPane layeredPane;
    private HexagonPanel preDrawnHexPanel;
    private PointPanel pointsPanel;
    private ImagePanel imagePanel;
    private ActivePanel activePanel;

    private List<Point> points;
    private Map<Point, String> pointStatusMap;

    public ChessBoard(){
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
            pointsPanel = new PointPanel(points);
            pointsPanel.setBounds(0, 0, width, height);
            this.add(pointsPanel, JLayeredPane.MODAL_LAYER);

            pointStatusMap = pointsPanel.getPointStatusMap();
            activePanel = new ActivePanel(pointStatusMap);
            activePanel.setBounds(0, 0, width, height);
            this.add(activePanel, JLayeredPane.POPUP_LAYER);

            addMouseListener(new MouseAdapter() {
                String pointColor = "red";
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) { // Check if left button clicked
                        click_mouseX = e.getX();
                        click_mouseY = e.getY();
                        for (Point p : points) {
                            double distance = Math.hypot(click_mouseX - p.x, click_mouseY - p.y);
                            if (distance < 15 && (pointsPanel.getPointStatus(p).equals("can_be_reach"))) {
                                pointsPanel.setPointStatus(p, pointColor);
                                pointColor = pointColor.equals("red") ? "blue" : "red";
                                break;
                            }
                        }
                        pointsPanel.repaint();
                    }
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                int lastX = -1;
                int lastY = -1;
                public void mouseMoved(MouseEvent e) {
                    click_mouseX = e.getX();
                    click_mouseY = e.getY();
                    for (Point p : points) {
                        double distance = Math.hypot(click_mouseX - p.x, click_mouseY - p.y);
                        if (distance < 15 && (p.x != lastX || lastY != p.y)) {
                            // preDrawnHexPanel.paintOnegrid(p.x, p.y);
                            lastX = p.x;
                            lastY = p.y;
                            break;
                        }
                    }
                }
            });
            
        }
    }
    /* 
    public void paint(Graphics g) {
        super.paint(g);
        if (mouseX != -1 && mouseY != -1) {
            Polygon polygonA = hexagonGrid.generateHexagon(mouseX, mouseY, 20);
            hexagonGrid.paintPolygon(g, polygonA, Color.RED);
        }
    }
    
    private void drawHexagon(Graphics g, int x, int y, int size) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            hexagon.addPoint((int) (x + size * Math.cos(i * Math.PI / 3)),
                             (int) (y + size * Math.sin(i * Math.PI / 3)));
        }
        g.drawPolygon(hexagon);
    }
    */

    public void GenerateGrid(){
        
    }
    // listen to the mouse click event
    
    public void create(){
        System.out.println("began to create chess board");

    }
    // Create a new chess board
    public static void main(String[] args) {
        // Create a new chess board
        new ChessBoard();
    }
}