package sabin_rains;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainClass extends JFrame {
    private GameTimer gameTimer;
    private JLabel timerLabel;

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
        setTitle("Sabin Rains");
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layeredPane = new LayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));
        add(layeredPane);
        setVisible(true);
    }
    
    private class LayeredPane extends JLayeredPane implements GameTimer.TimeoutListener {
        public LayeredPane() {
            // 初始化游戏计时器
            gameTimer = new GameTimer();
            gameTimer.setTimeoutListener(this);

            // 开始计时
            gameTimer.startTimer();

            imagePanel = new ImagePanel();
            imagePanel.setBounds(0, 0, width, height);
            this.add(imagePanel, JLayeredPane.DEFAULT_LAYER);

            preDrawnHexPanel = new HexagonPanel();
            preDrawnHexPanel.setBounds(0, 0, width, height);
            this.add(preDrawnHexPanel, JLayeredPane.PALETTE_LAYER);

            points = preDrawnHexPanel.getAllpoints(300, 300);
            pointsPanel = new PointPanel(points, preDrawnHexPanel);
            pointsPanel.setBounds(0, 0, width, height);
            this.add(pointsPanel, JLayeredPane.MODAL_LAYER);

            pointStatusMap = pointsPanel.getPointStatusMap();
            activePanel = new ActivePanel(pointStatusMap, points);
            activePanel.setBounds(0, 0, width, height);

            timerLabel = new JLabel("Time left: " + gameTimer.getTimeLeft(), SwingConstants.CENTER);
            timerLabel.setFont(new Font("Serif", Font.BOLD, 32));
            activePanel.add(timerLabel, BorderLayout.CENTER);
            this.add(activePanel, JLayeredPane.POPUP_LAYER);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    handleMouseClick(e);
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    handleMouseMovement(e);
                }
            });

            // 启动一个计时器来更新显示剩余时间
            new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timerLabel.setText("Time left: " + gameTimer.getTimeLeft());
                }
            }).start();
        }

        private void handleMouseClick(MouseEvent e) {
            int click_mouseX = e.getX();
            int click_mouseY = e.getY();
            for (Point p : points) {
                double distance = Math.hypot(click_mouseX - p.x, click_mouseY - p.y);
                if (distance < 17 && pointsPanel.getPointStatus(p).matches2nd("can_be_reach")) {
                    makeMove(p);
                    break;
                }
            }
        }

        private void handleMouseMovement(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            for (Point p : points) {
                double distance = Math.hypot(mouseX - p.x, mouseY - p.y);
                if (distance < 15) {
                    activePanel.paintHexagons(preDrawnHexPanel, pointsPanel, p.x, p.y, pointColor);
                    invertedPoints = activePanel.canInvertpoint();
                    activePanel.repaint();
                    break;
                }
            }
        }

        private void makeMove(Point p) {
            StringPair status = new StringPair(pointColor, "can_not_reach");
            pointsPanel.setPointStatus(p, status, invertedPoints);
            invertedPoints = null;
            activePanel.setStatusMap(pointsPanel.getPointStatusMap());
            pointColor = pointColor.equals("red") ? "blue" : "red";
            pointsPanel.repaint();
            gameTimer.resetTimer(); // 重置计时器
        }

        @Override
        public void onTimeout() {
            // 模拟随机落子操作
            Random rand = new Random();
            while (true) {
                Point p = points.get(rand.nextInt(points.size()));
                if (pointsPanel.getPointStatus(p).matches2nd("can_be_reach")) {
                    makeMove(p);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new MainClass();
    }
}
