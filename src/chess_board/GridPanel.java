package chess_board;
import java.awt.*;
import java.awt.event.*;
import chess_board.HexagonGrid;
import chess_board.GridPanel;
import javax.swing.*;


class GridPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 在这里调用 paintAllgrid 方法
        HexagonGrid hexagonGrid = new HexagonGrid();
        hexagonGrid.paintAllgrid(g, 300, 350);
    }
}
