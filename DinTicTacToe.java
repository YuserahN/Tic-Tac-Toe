import java.util.Random;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser; 

/*
AUTHOR:     Yuserah Din
DUE-DATE:   05/07/2019
CLASS:      OOP 
ASSIGNMENT: Final Project (DinTicTacToe)
DESCR:      I've created a game of Tic Tac Toe. 

    0 | 1 | 2 
    ---------
    3 | 4 | 5 
    ---------
    6 | 7 | 8

When you run the game, a dialog box will pop up, asking you to choose 
a player mode (Multiplayer or Single Player). The former means two people will be playing. 
Player 1 will have to click on a button on the game board (add a symbol), then Player 2 
will do the same. Single Player mode is when you play with the computer. 
So the human player adds a symbol, and the computer automatically gets a turn. 

After choosing player mode, another dialog box will ask you to choose a symbol (X or O). 

Once these options are chosen, the drawing frame will pop up with the "gameboard". To represent
this, I've used an array of JButtons called eachCell. As mentioned before, when clicked, each button/cell 
will change it's text (either to X or O). Then the button will automatically be disabled. 

Each time a user clicks on a cell/button on the gameboard, the index of that cell/button is concatentated to 
the current player's chosenCombo (as a string). This data is contained in the Player class as well. 
p1.setChosenCombo(index of the cell as a string). 

After each turn, the static function called checkWins() is called from the Game class. (Controller Class) 
This function contains a String array of the winning combinations (based on the indices of the buttons). 
For example, "012" is a winning combination because it represents the first row. CheckWin() essentially 
loops through the array of winning combinations and breaks down each string element into 3 chars. In the case 
of "012", c1 = "0", c2 = "1", c3 = "2". Then it checks if the chosenCombo for the current player contains 
all 3 of these characters. If yes, then it sets boolean won = true and displaying the win message and 
prompting the player to choose between exiting the game or playing again. If the former, then it exits. 
If the latter, the current game is erased and the game restarts. GameBoard.initBoard(). 

Each time checkWin is called, if the player has not won, the function proceeds to check if there are any more 
empty cells. If not, then gameOver = true.  

I have a menu at the top of the gameboard that allows a user to restart, exit, save (to file), 
or open (read from file). 

1. Player class is the model class. It stores the data for each player, 
    which includes the symbol that the player chose (X or O), the combination
    of the buttons that were clicked, and win status. 
    The constructor takes in the string symbol, an empty string for the chosen combo, and false 
    for win status. 

2. Controller classes include Game, GameBoard, and Computer. 
    I also have GameStatWriter, GameStatReader, writeToFile, and readFromFile in order to read/write to .txt and .bin
    files.
    Game, as mentioned previously, handles checking win status and whether the game is over. 
    GameBoard, as mentioned previously, initializes the game board and store the data for the 
    player mode. If there are two players, boolean multiplayer = true. 
    It also takes the chosen symbol and passes it to the player objects that it creates. 
    Ex: If player 1 chose 'X', 
    then Player p1 = new Player("X", "", false) and Player p2 = new Player("O", "", false); 
    Computer recursively generates a random number (0 - 8) for each index and adds a symbol in 
    the given cell if it's empty. 

3. DrawingFrame is the View class. It displays the buttons and responds to all the click events. 

Future Enhancements: I'd like to improve the design of the gameboard. For exameple, I'd like to have 
different colors for each symbol. (It was a bit difficult to change the text of the button once it's been disabled.) 
I'd also like to the option of choosing a symbol and player mode through the game board, as opposed to a dialog box. 
Also, currently, the only way to read/write to file is using the 'Save' and 'Open' menu options. I'd like to have it 
write game stats to a file automatically each time the a player wins or game is over. Right now, if you want to write 
the stats to a file after game is over and someone has won, you'd have to click x on the dialog boxes and then 
access the menu item 'Save'. 

*/

//Model class stores data for each player.
class Player implements Serializable {
    private String symbol;
    private String chosenCombo = "";
    private boolean winner;
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String sym) {
        symbol = sym;
    }
    public String getChosenCombo() {
        return chosenCombo;
    }
    public void setChosenCombo(String chosenCombo) {
        this.chosenCombo += chosenCombo;
    }
    public boolean getWinner() {
        return winner;
    }
    public void setWinner(boolean winner) {
        this.winner = winner;
    }
    public Player(String symbol, String chosenCombo, boolean winner) {
        setSymbol(symbol);
        setChosenCombo(chosenCombo);
        setWinner(winner);
    }
    @Override 
    public String toString() {
        return String.format("Symbol = %s, Chosen Combo = %s, Winner = %b", symbol, chosenCombo, winner);
    }
}

//Controller class manages wins and game overs. 
class Game {
    //Game is over if nobody has won and there are no more empty cells left. 
    public static boolean gameOver(JButton[] eachCell) {
        for (JButton c: eachCell) {
           if(c.getText().equals("_")) {
               return false;
           } 
       }
       return true;
    }
    public static void checkWin(Player pl, JButton[] eachCell) {
        //These are the winning combinations
        String[] winCombos = {
        "012", "048", "036", 
        "102", "147", 
        "201", "246", "258",
        "345", "306",
        "435", "408", "426", "417",
        "534", "528", 
        "678", "624", "603",
        "768", "714", 
        "867", "804", "825"};

        boolean won = false;
        //For the JOptionPane
        String[] options = {"Play Again", "Exit Game"}; 
        //Loops through winning combos and checks if all three winning indices are in the
        //player's chosenCombo. 
        for( int i = 0; i < winCombos.length; i++){
            char c1 = winCombos[i].charAt(0);
            char c2 = winCombos[i].charAt(1);
            char c3 = winCombos[i].charAt(2);
            //player has won if combination exists 
            if(pl.getChosenCombo().indexOf(c1) != -1 && pl.getChosenCombo().indexOf(c2) != -1 && pl.getChosenCombo().indexOf(c3) != -1){
                won = true;
                pl.setWinner(true);
            }
        } 

        String winMessage = "";
        if(won) {
            if(pl.getSymbol().equals("X")) {
                winMessage = "X Wins! Game Over!";
            } else {
                winMessage = "O Wins! Game Over!";
            }
        } else {
            winMessage = "It's a draw.";
        }

        if(won || gameOver(eachCell)) {
            int playAgain = JOptionPane.showOptionDialog(null, winMessage,
                "Click a button",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if(playAgain == 0) {
               GameBoard.initBoard(); 
            } else if (playAgain == 1) {
                System.exit(0);
            } 
        }
    }
} 

//Write Game Stats to a text file. 
//This includes the symbol chosen, the chosen combo, and win status. 
class GameStatsWriter {
    public static boolean writeGameStatsToTextFile(String fname, ArrayList<Player> players) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(fname))));
            for(Player player: players) {
                pw.println(player.getSymbol() + ": " + player.getChosenCombo() + " " + player.getWinner());
            }
            pw.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}

//Write to a .txt or .bin file by selecting a file from JFileChooser. 
class writeToFile {
    public static void write(ArrayList<Player> pl) {
        String fname;
        File f;
        JFileChooser jfc = new JFileChooser();
        if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            f = jfc.getSelectedFile();
            JOptionPane.showMessageDialog(null,"saving " + f.getName());
            if(f.toString().lastIndexOf(".txt") > 0) {
                System.out.println("Writing Game Stats To Text File...");
                GameStatsWriter.writeGameStatsToTextFile(f.toString(), pl);
                System.out.println("Success");
            } else if(f.toString().lastIndexOf(".bin") > 0) {
                System.out.println("Writing To Binary...");
                try {
                    FileOutputStream fos = new FileOutputStream(f.toString());
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(pl);
                    oos.close();
                    System.out.println("Success");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("File Extention Not Recognized.");
            }
        }
    }
} 

//Reads Game Stats (symbol, chosen combo, and win status). 
class GameStatsReader {
    public static ArrayList<Player> readGameStatsFromTextFile(String fname) {
        ArrayList<Player> players = new ArrayList<Player>();
        try {
            System.out.println(fname);
            File f = new File(fname);
            Scanner fsc = new Scanner(f);
            String symbol, chosenCombo, line = ""; 
            boolean winStat;
            String[] parts;
            Player pl;
            while(fsc.hasNextLine()) {
                line = fsc.nextLine();
                parts = line.split(" ");
                symbol = parts[0];
                chosenCombo = parts[1];
                winStat = Boolean.valueOf(parts[2]);
                pl = new Player(symbol, chosenCombo, winStat);
                players.add(pl);
            }
            fsc.close();
            return players;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

//Use JFileChooser to select a file to read from. These stats are displayed to the console. 
class readFromFile {
    public static void read(ArrayList<Player> players) {
        String fname;
        File f;
        JFileChooser jfc = new JFileChooser();
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            f = jfc.getSelectedFile();
            JOptionPane.showMessageDialog(null,"opening " + f.getName());
            if(f.toString().lastIndexOf(".txt") > 0) {
                players = GameStatsReader.readGameStatsFromTextFile(f.toString());
                if(players == null) {
                    System.out.println("No game stats were read.");
                } else {
                    System.out.println("Reading From Text File...");
                    for(Player pl : players) {
                        System.out.println(pl);
                    }
                    System.out.println("Success...");
                }
            } else if(f.toString().lastIndexOf(".bin") > 0) {
                System.out.println("Reading From Binary...");
                try {
                    FileInputStream fis = new FileInputStream(f.toString());
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    players = (ArrayList<Player>)(ois.readObject());
                    for(Player pl : players) {
                        System.out.println(pl);
                    }
                    System.out.println("Success");
                    ois.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("File Extention Not Recognized.");
            }
        }
    }
}

//This is the computer's turn. It generates a random number between 0-8, each 
//representing the index of a button/cell. If the cell is empty, the computer 
//can add/draw it's chosen symbol to that button. If not, the function repeats
//recursively until it finds an empty cell. 
class Computer {
    public static void addGamePiece(JButton[] eachCell, Player p2) {
        Random rnd = new Random();
        int randomCell = rnd.nextInt(9);
        if (eachCell[randomCell].getText() != "_") {
            addGamePiece(eachCell, p2);
        } else {
            eachCell[randomCell].setFont(new Font("Serif", Font.BOLD, 48));
            eachCell[randomCell].setText(p2.getSymbol());
            eachCell[randomCell].setEnabled(false);
            p2.setChosenCombo(Integer.toString(Arrays.asList(eachCell).indexOf(eachCell[randomCell])));
            Game.checkWin(p2, eachCell);
        }
    }
}

class DrawingFrame extends JFrame implements ActionListener {
    Player p1, p2;
    boolean xSym = true;
    boolean oSym = false;
    boolean multiPlayer = false;
    ArrayList<Player> players = new ArrayList<Player>();
    
    JButton[] eachCell = new JButton[9];
    public void actionPerformed(ActionEvent e) {
        JButton cell = (JButton)(e.getSource());
        cell.setFont(new Font("Serif", Font.BOLD, 48));
        //If multiplayer is true, then it determines who's turn it is by 
        //setting xSym and oSym to true/false. For example, if player 1 is 'X', 
        //xSym = true and player 1 gets a turn, after which xSym is set to false 
        //and oSym is set to true. And player 2 gets a turn. 
        if(multiPlayer == true) {
            if(xSym) {
                cell.setText(p1.getSymbol());
                cell.setForeground(Color.GREEN);
                p1.setChosenCombo(Integer.toString(Arrays.asList(eachCell).indexOf(cell)));
                Game.checkWin(p1, eachCell);
            } else {
                cell.setText(p2.getSymbol());
                cell.setForeground(Color.GREEN);
                p2.setChosenCombo(Integer.toString(Arrays.asList(eachCell).indexOf(cell)));
                Game.checkWin(p2, eachCell);
            }
            xSym = !xSym;
            oSym = !oSym;
        } else {
            //If singe player mode is on, the human gets a turn. 
            //Then it checks to see if there are any empty cells left. 
            //If so, computer gets a turn. 
            cell.setText(p1.getSymbol());
            p1.setChosenCombo(Integer.toString(Arrays.asList(eachCell).indexOf(cell)));
            Game.checkWin(p1, eachCell);
            boolean computerTurn = false;
            for (JButton button: eachCell) {
                if(button.getText().equals("_")) {
                   computerTurn = true;
                }
            }
            if(computerTurn) {
                Computer.addGamePiece(eachCell, p2);
            }
        } 
        cell.setEnabled(false);        
    }
    public void setupMenu() {
        JMenuBar mbar = new JMenuBar();
        JMenu mnuFile = new JMenu("File");
        JMenuItem miRestart = new JMenuItem("Restart");
        miRestart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameBoard.initBoard();
            }
        });
        mnuFile.add(miRestart);
        JMenuItem miExit = new JMenuItem("Exit");
        miExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mnuFile.add(miExit);

        //Write to File       
        JMenuItem miSave = new JMenuItem("Save");
        miSave.addActionListener(new ActionListener() {
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent e) {
                players.add(p1);
                players.add(p2);
                writeToFile.write(players);
            }
        });
        mnuFile.add(miSave);
        
        //Read from File
        JMenuItem miOpen = new JMenuItem("Open");
        miOpen.addActionListener(new ActionListener() {
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent e) {
                readFromFile.read(players);
            }
        });
        mnuFile.add(miOpen);

        mbar.add(mnuFile);

        setJMenuBar(mbar);
    }
    public void setupUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100,100,500,500);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        
        //Draws the gameboard using a JButton array
        JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(3,3));
        for (int i = 0; i < 9; i++) {
            eachCell[i] = new JButton("_");
            eachCell[i].addActionListener(this);
            gameBoard.add(eachCell[i]);
        }
        c.add(gameBoard,BorderLayout.CENTER);
        setupMenu();
    }
    public DrawingFrame(Player p1, Player p2, boolean multiPlayer) {
        this.p1 = p1;
        this.p2 = p2;
        this.multiPlayer = multiPlayer;
        setupUI();
    }
}

class GameBoard {
    //Gets player mode and creates Player objects based on chosen symbol. 
    //DrawingFrame frm is created and passed the Player objects, as well as the playermode. 
    public static void initBoard() {
        boolean multiPlayer = false;
        Player p1, p2;
        String[] playerOptions = {"Multi Player", "Single Player"};
        int playerMode = JOptionPane.showOptionDialog(null, "Choose Player Mode",
                "Click a button",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, playerOptions, playerOptions[0]);
        String[] symbolOptions = {"X", "O"};
        int chosenSymbol = JOptionPane.showOptionDialog(null, "Choose A Symbol For Player 1",
                "Click a button",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, symbolOptions, symbolOptions[0]);
        if(chosenSymbol == 0) {
            p1 = new Player("X", "", false);
            p2 = new Player("O", "", false);
        } else {
            p1 = new Player("O", "", false);
            p2 = new Player("X", "", false);
        }
        
        if(playerMode == 0) {
            multiPlayer = true;
        } else {
            multiPlayer = false;
        }
        DrawingFrame frm = new DrawingFrame(p1, p2, multiPlayer);
        frm.setVisible(true);
    }
}

public class DinTicTacToe {
    public static void main (String[] args) {
        GameBoard.initBoard();
    }
}