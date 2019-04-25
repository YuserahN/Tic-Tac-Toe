import java.util.Random;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Player {
    private String symbol;
    private int score;
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String sym) {
        symbol = sym;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public Player(String symbol, int score) {
        this.symbol = symbol;
        this.score = score;
    }
    @Override 
    public String toString() {
        return String.format("Symbol = %s, Score = %d", symbol, score);
    }
}

class DrawingFrame extends JFrame implements ActionListener {
    Player you;
    public void actionPerformed(ActionEvent e) {
        JButton cell = (JButton)(e.getSource());
        if(cell.getText() == "_") {
            cell.setText(you.getSymbol());
        } else {
            cell.setText("Clicked Again");
        }
    }
    public void setupUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100,100,300,300);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(3,3));
        for (int i = 0; i < 9; i++) {
            JButton eachCell = new JButton("_");
            eachCell.addActionListener(this);
            gameBoard.add(eachCell);
        }
        c.add(gameBoard,BorderLayout.CENTER);
    }
    public DrawingFrame(Player you) {
        this.you = you;
        setupUI();
    }
}

public class DinTicTacToe {
    public static void main (String[] args) {
        Player you = new Player("X", 0);
        DrawingFrame frm = new DrawingFrame(you);
        frm.setVisible(true);
    }
}