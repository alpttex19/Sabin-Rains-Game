package sabin_rains;
import java.awt.*;
import javax.swing.*;

import sabin_rains.*;


class ImagePanel extends JPanel {
    private ImageIcon image;

    public ImagePanel() {
        // 加载图像
        java.net.URL imgURL = getClass().getResource("/sources/backgroud.jpg");
        image = new ImageIcon(imgURL);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image.getImage(), 0, 0, this);
        }
    }
}
