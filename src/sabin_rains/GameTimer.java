package sabin_rains;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer {
    private static final int INITIAL_TIME = 30;
    private int timeLeft = INITIAL_TIME;
    private Timer timer;
    private TimeoutListener timeoutListener;

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

    public void startTimer() {
        timeLeft = INITIAL_TIME;
        timer.start();
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void resetTimer() {
        timeLeft = INITIAL_TIME;
        timer.restart();
    }

    public void setTimeoutListener(TimeoutListener listener) {
        this.timeoutListener = listener;
    }

    public interface TimeoutListener {
        void onTimeout();
    }
}
