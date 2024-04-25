package chess_board;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class HexagonWindow {
    private JFrame frame;
    private LayeredPane layeredPane;
    private ImagePanel imagePanel;
    private HexagonPanel preDrawnHexPanel;
    private HexagonPanel clickDrawnHexPanel;

    public HexagonWindow() {
        // 创建主窗口
        frame = new JFrame("Hexagon Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        // 创建三层面板
        layeredPane = new LayeredPane();
        layeredPane.setPreferredSize(new Dimension(500, 500));
        
        // 添加层面板到窗口
        frame.add(layeredPane);
        frame.pack();
        frame.setVisible(true);
    }

    private class LayeredPane extends JLayeredPane {
        public LayeredPane() {
            // 创建图像面板
            imagePanel = new ImagePanel();
            imagePanel.setBounds(0, 0, 500, 500);
            this.add(imagePanel, JLayeredPane.DEFAULT_LAYER);

            // 创建预绘制的六边形面板
            preDrawnHexPanel = new HexagonPanel(Color.BLUE);
            preDrawnHexPanel.setBounds(0, 0, 500, 500);
            preDrawnHexPanel.addPreDrawnHexagon();
            this.add(preDrawnHexPanel, JLayeredPane.PALETTE_LAYER);

            // 创建单击绘制的六边形面板
            clickDrawnHexPanel = new HexagonPanel(Color.RED);
            clickDrawnHexPanel.setBounds(0, 0, 500, 500);
            this.add(clickDrawnHexPanel, JLayeredPane.MODAL_LAYER);

            // 添加鼠标监听器
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // 在点击位置绘制六边形
                    clickDrawnHexPanel.drawHexagon(e.getX(), e.getY());
                }
            });
        }
    }

    private class ImagePanel extends JPanel {
        private Image image;

        public ImagePanel() {
            // 加载图像
            image = new ImageIcon("src/sources/backgroud.jpg").getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, this);
            }
        }
    }

    private class HexagonPanel extends JPanel {
        private Color hexColor;
        private List<Path2D> hexagons = new ArrayList<>();

        public HexagonPanel(Color color) {
            hexColor = color;
            setOpaque(false); // 保证面板透明
        }

        public void drawHexagon(int x, int y) {
            // 计算六边形的顶点
            Path2D hexagon = new Path2D.Double();
            double angle = Math.toRadians(60);
            int size = 30; // 六边形的大小
            hexagon.moveTo(x + size * Math.cos(0), y + size * Math.sin(0));
            for (int i = 1; i < 6; i++) {
                hexagon.lineTo(x + size * Math.cos(i * angle), y + size * Math.sin(i * angle));
            }
            hexagon.closePath();
            hexagons.add(hexagon);
            repaint();
        }

        public void addPreDrawnHexagon() {
            // 在第二层面板预绘制六边形的位置
            drawHexagon(250, 250); // 在窗口中心绘制一个六边形
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(hexColor);
            for (Path2D hexagon : hexagons) {
                g2d.fill(hexagon);
            }
        }
    }

    public static void main(String[] args) {
        new HexagonWindow();
    }
}
