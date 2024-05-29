package sabin_rains;
import java.awt.*;
import javax.swing.*;

import sabin_rains.*;

/*
 * ImagePanel类，用于绘制背景图片
 * 主要功能：
 * 1. 绘制背景图片
 * 
 */

class ImagePanel extends JPanel {
    private ImageIcon image;
    // 构造函数, 加载背景图片
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
