package chess_board;
import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


class ImagePanel extends JPanel {
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
