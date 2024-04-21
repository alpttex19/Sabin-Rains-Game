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
        // Set the background color of the chess board
        setBackground(Color.WHITE);
        // Set the visibility of the chess board
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // JPanel gridPanel = new JPanel();

        GridPanel gridPanel = new GridPanel();
        gridPanel.setLayout(null);
        // gridPanel.setLayout(null);
        // gridPanel.setLayout(null);
        // gridPanel.setSize(width, height);
        // JPanel imagePanel = new JPanel();
        // imagePanel.setLayout(null);
        // imagePanel.setSize(width, height);

        // ImageIcon imageBackground = new ImageIcon("src/sources/backgroud.jpg");
        // JLabel imageLabel = new JLabel(imageBackground, SwingConstants.CENTER);
        // imageLabel.setBounds(100, 100, boardSize, boardSize);
        // imagePanel.add(imageLabel);
        countTxt = new JLabel("welcome to the chess board");
        countTxt.setFont(new Font("Arial", Font.BOLD, 20));
        countTxt.setBounds(100, 0, 300, 50);
        gridPanel.add(countTxt);

        // hexagonGrid = new HexagonGrid();
        // hexagonGrid.paintAllgrid(getGraphics(),300, 350);
        // gridPanel.add(hexagonGrid);
        gridPanel.add(new HexagonGrid());
        add(gridPanel);
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