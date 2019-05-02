import java.util.Random;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

class Player {
    private String symbol;
    private int score;
    private int[] gameScore;
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
        setSymbol(symbol);
        setScore(score);
    }
    @Override 
    public String toString() {
        return String.format("Symbol = %s, Score = %d", symbol, score);
    }
}

class Wins {
    public static void checkWin(String pl) {
        String[] winLines = {
        "012", "048", "036", 
        "102", "147", 
        "201", "246", "258",
        "345", "306",
        "435", "408", "426", "417",
        "534", "528", 
        "678", "624", "603",
        "768", "714", 
        "867", "804", "825"};

        boolean gameOver = false;
        String[] options = {"Play Again", "Exit Game"};
        for( int i = 0; i < winLines.length; i++){
            char c1 = winLines[i].charAt(0);
            char c2 = winLines[i].charAt(1);
            char c3 = winLines[i].charAt(2);
            if(pl.indexOf(c1) != -1 && pl.indexOf(c2) != -1 && pl.indexOf(c3) != -1){
                // currentPlayer has won if he has all the 3 positions of a winning combo
                //System.out.println("YOU WON!");
                gameOver = true;
            }
        } 

        if(gameOver) {
            int playAgain = JOptionPane.showOptionDialog(null, "YOU WIN! Game Over!",
                "Click a button",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        }
    }
}


class DrawingFrame extends JFrame implements ActionListener {
    Player p1, p2;
    boolean xSym = true;
    boolean oSym = false;
    boolean multiPlayer = false;
    String pl1 = "", pl2 = "";
    
    JButton[] eachCell = new JButton[9];
    public void actionPerformed(ActionEvent e) {
        JButton cell = (JButton)(e.getSource());
        cell.setFont(new Font("Serif", Font.BOLD, 48));
        if(multiPlayer == true) {
            if(xSym) {
                cell.setText(p1.getSymbol());
                cell.setForeground(Color.GREEN);
                pl1 += Arrays.asList(eachCell).indexOf(cell);
                //cell.setText("<html><font color=green>" + p1.getSymbol() + "</font></html>");
                Wins.checkWin(pl1);
            } else {
                cell.setText(p2.getSymbol());
                pl2 += Arrays.asList(eachCell).indexOf(cell);
                Wins.checkWin(pl2);
            }
            xSym = !xSym;
            oSym = !oSym;
        } else {
            Random rnd = new Random();
            if(cell.getText() == "_") {
                cell.setText(p1.getSymbol());
                int randomCell = rnd.nextInt(9);
                int i = 8;
                if (eachCell[randomCell].getText() != "_") {
                    randomCell = rnd.nextInt(9);
                    i--; 
                } else {
                    eachCell[randomCell].setText("O");
                }
            }
        }
        cell.setEnabled(false);        
    }
    public void setupUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100,100,500,500);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(3,3));
        for (int i = 0; i < 9; i++) {
            eachCell[i] = new JButton("_");
            eachCell[i].addActionListener(this);
            gameBoard.add(eachCell[i]);
        }
        c.add(gameBoard,BorderLayout.CENTER);
    }
    public DrawingFrame(Player p1, Player p2, boolean multiPlayer) {
        this.p1 = p1;
        this.p2 = p2;
        this.multiPlayer = multiPlayer;
        setupUI();
    }
}

public class DinTicTacToe {
    public static void main (String[] args) {
        boolean multiPlayer = false;
        String[] options = {"Multi Player", "Single Player"};
        int gameMode = JOptionPane.showOptionDialog(null, "Choose Player Mode",
                "Click a button",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        Player p1 = new Player("X", 0);
        Player p2 = new Player("O", 0);
        if(gameMode == 0) {
            multiPlayer = true;
        } else {
            multiPlayer = false;
        }
        DrawingFrame frm = new DrawingFrame(p1, p2, multiPlayer);
        frm.setVisible(true);
    }
}