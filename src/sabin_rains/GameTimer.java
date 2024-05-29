package sabin_rains;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * GameTimer类，用于处理游戏计时器
 * 主要功能：
 * 1. 开始计时
 * 2. 停止计时
 * 3. 重置计时
 * 4. 获取剩余时间
 * 5. 设置超时监听器
 * 
 */


public class GameTimer {
    private static final int INITIAL_TIME = 30;
    private int timeLeft = INITIAL_TIME;
    private Timer timer;
    private TimeoutListener timeoutListener;
    // 构造函数
    public GameTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                } else {
                    timer.stop();
                    if (timeoutListener != null) {
                        timeoutListener.onTimeout();
                    }
                }
            }
        });
    }
    // 开始计时
    public void startTimer() {
        timeLeft = INITIAL_TIME;
        timer.start();
    }
    // 停止计时
    public void stopTimer() {
        timer.stop();
    }
    // 获取剩余时间
    public int getTimeLeft() {
        return timeLeft;
    }
    // 重置计时
    public void resetTimer() {
        timeLeft = INITIAL_TIME;
        timer.restart();
    }
    // 设置超时监听器
    public void setTimeoutListener(TimeoutListener listener) {
        this.timeoutListener = listener;
    }
    // 超时监听器接口
    public interface TimeoutListener {
        void onTimeout();
    }
}
