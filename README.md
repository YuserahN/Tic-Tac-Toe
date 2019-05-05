# Tic-Tac-Toe
Game of Tic Tac Toe in Java

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

