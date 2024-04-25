package chess_board;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;


// Create a new chess board
class ChessBoard extends JFrame{
    private int width = 600;
    private int height = 600;
    private int mouseX = -1;
    private int mouseY = -1;
    private JLabel countTxt;
    private HexagonGrid hexagonGrid;
    private List<Point> points;
    private Flags flags;

    public ChessBoard(){
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
        flags = new Flags(points);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // Check if left button clicked
                    mouseX = e.getX();
                    mouseY = e.getY();
                    System.out.print("the first point is: ");
                    System.out.println(points.get(0));
                    for (Point p : points) {
                        double distance = Math.hypot(mouseX - p.x, mouseY - p.y);
                        if (distance < 8) {
                            System.out.print("the clicked point is: ");
                            System.out.println(p);
                            flags.setPointStatus(p, "red");
                            repaint();
                            break;
                        }
                    }
                }
            }
        });

        /* 
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
        */
        setVisible(true);
    }
    

    public void paint(Graphics g) {
        super.paint(g);
        flags.paintPoints(g);
        // if (mouseX != -1 && mouseY != -1) {
        //     Polygon polygonA = hexagonGrid.generateHexagon(mouseX, mouseY, 20);
        //     hexagonGrid.paintPolygon(g, polygonA, Color.RED);
        // }
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
        ChessBoard app = new ChessBoard();
    }
}