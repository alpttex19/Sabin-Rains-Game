package chess_board;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
//import javax.swing.*;
import java.io.*;

import java.awt.*;
import javax.swing.*;

public class HexagonGrid extends JPanel{
    private int boardSize = 600;
    public HexagonGrid(){
        super();
    }

    public Polygon generateHexagon(int centerX, int centerY, int size){
        // Create a new polygon
        int[] x = new int[6];
        int[] y = new int[6];

        for (int i = 0; i < 6; i++){
            x[i] = (int) (centerX + size * Math.cos(i * 2 * Math.PI / 6 + Math.PI / 6));
            y[i] = (int) (centerY + size * Math.sin(i * 2 * Math.PI / 6 + Math.PI / 6));
        }

        return new Polygon(x, y, x.length);
    }
    
    public void paintPolygon(Graphics g, Polygon p, Color c){
        super.paintComponent(g);
        g.setColor(c);
        g.drawPolygon(p);
    }

    public void fillPolygon(Graphics g, Polygon p, Color c){
        super.paintComponent(g);
        g.setColor(c);
        g.fillPolygon(p);
    }

    public void paintAllgrid(Graphics g,int centerX, int centerY){
        super.paintComponent(g);
        try{
            Image im = ImageIO.read(new File("src/sources/backgroud.jpg"));
            g.drawImage(im, 0, 0, boardSize, boardSize, null);
        } catch(IOException e1){}                  

        int size = 20; // size of the small hexagons
        int padding = 16; // space between hexagons

        int[] num = {5,6,7,8,9,8,7,6,5};

        for (var i = 0; i < num.length; i++){
            int wid = num[i];
            for (int j = 0; j <= wid; j ++) {
                // Generate the small hexagon
                int x = centerX + (int)(((j)-(wid/2.0)) * (size + padding));
                int y = centerY + (int)((i-4) * (size + padding - 5.6));
                Polygon hexagon = generateHexagon(x, y, size);

                // Draw the small hexagon
                // g.setColor(Color.BLACK);
                // g.drawPolygon(hexagon);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(3)); // Set the line thickness to 3
                g2.drawPolygon(hexagon);
            }
        }
    }
}
