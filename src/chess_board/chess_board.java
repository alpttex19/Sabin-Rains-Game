package chess_board;
import java.awt.*;
import java.awt.event.*;
import chess_board.HexagonGrid;
import chess_board.GridPanel;
import javax.swing.*;


// Create a new chess board
class chess_board extends JFrame{
    private int width = 600;
    private int height = 600;
    private JLabel countTxt;
    HexagonGrid hexagonGrid = new HexagonGrid();

    public chess_board(){
        super();
        // Set the size of the chess board
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridPanel backgdPanel = new GridPanel();
        backgdPanel.setLayout(null);
        countTxt = new JLabel();
        countTxt.setFont(new Font("Arial", Font.BOLD, 20));
        countTxt.setText("Chess Board");
        countTxt.setBounds(250, 100, 300, 50);
        backgdPanel.add(countTxt);

        backgdPanel.add(new HexagonGrid());
        add(backgdPanel);
        setVisible(true);
    }

    public void GenerateGrid(){
        
    }

    public void create(){
        System.out.println("began to create chess board");

    }
    // Create a new chess board
    public static void main(String[] args) {
        // Create a new chess board
        chess_board app = new chess_board();
    }
}