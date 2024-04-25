package chess_board;
import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


class GridPanel extends JPanel {
    public List<Point> points;
    public GridPanel() {
        super();
        points = new ArrayList<>();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 在这里调用 paintAllgrid 方法
        try{
            Image im = ImageIO.read(new File("src/sources/backgroud.jpg"));
            g.drawImage(im, 0, 0, getWidth(),getHeight(), null);
        } catch(IOException e1){}  
        HexagonGrid hexagonGrid = new HexagonGrid();
        points = hexagonGrid.returnAllgrid(300, 300);
        System.out.println(points.get(0));
        hexagonGrid.paintAllgrid(g, points);
    }
}
