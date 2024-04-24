package chess_board;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;


// Create a new chess board
class chess_board extends JFrame{
    private int width = 600;
    private int height = 600;
    private int mouseX = -1;
    private int mouseY = -1;
    private JLabel countTxt;
    private HexagonGrid hexagonGrid;
    private List<Point> points;

    public chess_board(){
        super();
        // Set the size of the chess board
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridPanel backgdPanel = new GridPanel();
        backgdPanel.setLayout(null);
        countTxt = new JLabel();
        countTxt.setFont(new Font("Arial", Font.BOLD, 20));
        countTxt.setText("Chess Board");
        countTxt.setBounds(250, 100, 300, 50);
        backgdPanel.add(countTxt);

        hexagonGrid = new HexagonGrid();
        backgdPanel.add(hexagonGrid);
        add(backgdPanel);
        // remember the all coordinates of the small hexagons
        points = hexagonGrid.returnAllgrid(300, 300);

        setVisible(true);
        
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                for (Point p : points) {
                    if (mouseX - p.x < 10 && mouseY - p.y < 10) {
                        mouseX = p.x;
                        mouseY = p.y;
                        break;
                    }
                repaint();
                }
            }
        });
    }

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

    public void GenerateGrid(){
        
    }
    // 监听鼠标位置，在对应位置画出六边形
    
    public void create(){
        System.out.println("began to create chess board");

    }
    // Create a new chess board
    public static void main(String[] args) {
        // Create a new chess board
        chess_board app = new chess_board();
    }
}