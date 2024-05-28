package sabin_rains;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameTimer extends JFrame {
    private static final int INITIAL_TIME = 30;
    private int timeLeft = INITIAL_TIME;
    private JLabel timerLabel;
    private Timer timer;
    private JButton randomMoveButton;

    public GameTimer() {
        // 初始化窗口
        setTitle("Sabin Rains Timer");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 初始化计时器标签
        timerLabel = new JLabel("Time left: " + timeLeft, SwingConstants.CENTER);
        timerLabel.setFont(new Font("Serif", Font.BOLD, 32));
        add(timerLabel, BorderLayout.CENTER);

        // 初始化随机落子按钮
        randomMoveButton = new JButton("Random Move");
        randomMoveButton.setEnabled(false);
        randomMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRandomMove();
            }
        });
        add(randomMoveButton, BorderLayout.SOUTH);

        // 初始化计时器
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerLabel.setText("Time left: " + timeLeft);
                } else {
                    timer.stop();
                    performRandomMove();
                }
            }
        });

        setVisible(true);
        startTimer();
    }

    private void startTimer() {
        timeLeft = INITIAL_TIME;
        timerLabel.setText("Time left: " + timeLeft);
        randomMoveButton.setEnabled(false);
        timer.start();
    }

    private void performRandomMove() {
        // 模拟随机落子操作
        Random rand = new Random();
        int randomPosition = rand.nextInt(25); // 假设有25个可落子位置
        JOptionPane.showMessageDialog(this, "Random move at position: " + randomPosition);
        
        // 重启计时器并启用随机落子按钮
        randomMoveButton.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameTimer();
            }
        });
    }
}
