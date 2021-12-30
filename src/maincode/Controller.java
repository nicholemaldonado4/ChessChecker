// Nichole Maldonado
// CS331 - Lab 5, Controller Class

/*
 * GameController class controls the activity between the
 * player, game board, and game. It has functions 
 * used to facilliate the initialization of the board
 * and starting the game. It also causes the move verification
 * to occur for every chess move.
 */

// changelog
// [2/28/20] [Nichole Maldonado] added getters and setters for 
//                               attributes that include board,
//                               player, max time, id, and chat.
// [2/29/20] [Nichole Maldonado] moved max time, id, chat, and autosave to new
//                               class Game and added attribute of Game.
// [2/29/20] [Nichole Maldonado] Created function to start the game.
// [2/29/20] [Nichole Maldonado] Reused code from Lab3 to collect x and y positions.
// [3/01/20] [Nichole Maldonado] Reorganized import statements
// [3/04/20] [Nichole Maldonado] Moved start game to Game file
// [3/04/05] [Nichole Maldonado] Added game board to Game class instead.
// [3/04/05] [Nichole Maldonado] Added isNewGame attribute to the controller
//                               so the controller will always know at any time what
//                               the status is of the system.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.
// [4/25/20] [Nichole Maldonado] Renamed controller for MVC game controller 
//                               to be called GameController.

package utep.cs3331.lab5.chess;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import utep.cs3331.lab5.chess.Game;
import utep.cs3331.lab5.chess.GameSelector;
import utep.cs3331.lab5.files.exceptions.ExceptionHandler;
import utep.cs3331.lab5.files.ParserWriter;
import utep.cs3331.lab5.files.Parser;
import utep.cs3331.lab5.players.ChessPlayer;

import org.jdom2.JDOMException;

/*
 * GameController class controls the activity between the
 * player, game board, and game. It has functions 
 * used to facilliate the initialization of the board
 * and starting the game.
 */
public class Controller implements ExceptionHandler {
    private ChessPlayer player;
    private Game game;
    private Parser parser;
    private boolean isNewGame;
    
    /*
     * Default constructor that creates a new game board.
     * @param: the ChessPlayer and unique id.
     * @return: None.
     */
    public Controller() {}  
    
    /*
     * Getter for the game attribute.
     * @param: None
     * @return: The game attribute.
     */
    public Game getGame() {
        return this.game;
    }
    
    /*
     * Getter for the isNewGame attribute.
     * @param: None
     * @return: The isNewGame attribute.
     */
    public boolean getIsNewGame() {
        return this.isNewGame;
    }
    
    /*
     * Getter for the chessPlayer attribute.
     * @param: None
     * @return: The chessPlayer attribute.
     */
    public ChessPlayer getPlayer() {
        return this.player;
    }
    
    /*
     * Getter for the parser attribute.
     * @param: None
     * @return: The parser attribute.
     */
    public Parser getParser() {
        return this.parser;
    }
    
    /*
     * Setter for the isNewGame attribute.
     * @param: The value isNewGame attribute will be assigned to.
     * @return: None.
     */
    public void setIsNewGame(boolean isNewGame) {
        this.isNewGame = isNewGame;
    }
    
    // Setters for all other attrubutes do not exist since
    // changing a player or the game could affect the other 
    // attrubutes.
    
    /*
     * Method that determines if the user wants to save the game.
     * @param: a scanner for user input.
     * @return: true to save, false to not save.
     */
    public boolean saveGame(Scanner input) {
        System.out.println("Would you like to save your game?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Select 1 or 2: ");
        int selection;
        try {
            selection = input.nextInt();
            
            System.out.println();
            if (selection != 1 && selection != 2) {
                System.out.println("Invalid menu selection. Your game will be saved.");
                selection = 1;
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input. Your game will be saved.");
            selection = 1;
        }
        
        input.nextLine();
        
        return (selection == 1) ? true : false;
    }
    
    /*
     * Method that initiates the file collection from the users.
     * @param: the scanner for user input.
     * @return: false if files were never properly set.
     */
    private boolean retrieveFiles(Scanner input) {
        this.parser = new Parser();
        return this.parser.setParserReaderWriter(input);
    }
  
    /*
     * Method that signals either load an existing game or
     * create a new game for an existing user.
     * @param: a scanner for input.
     * @return: false to if the user decided to quit the game, true otherwise.
     */
    private boolean existingLoadGameOrNewGame(Scanner input) throws IOException, JDOMException, NullPointerException {
        GameSelector gameSelector = new GameSelector(this.player.getIdQueue(), this.parser.getParserWriter().getFilePaths(), this.player.getName());
        
        // Determine new or load game for existing user.
        this.isNewGame = gameSelector.selectGameType(input);
            
        // Existing user wants to start new game.
        if (this.isNewGame) {
            
            // Set id for game and add to player's list of ids.
            this.game.getGameModel().setId(ParserWriter.createId());
            this.player.getIdQueue().add(this.game.getGameModel().getId());
        }
        
        this.game.getGameModel().setId(this.player.getIdQueue().peekLast());
        
        // Creates the board from the file if the user
        // chooses to load a game.
        return this.parser.signalGameCreation(this, input);
    }
    
    /*
     * Method that determines whether a new game needs to be created,
     * or determines if an existing user still needs to decide.
     * @param: a scanner for input.
     * @return: false to if the user decided to quit the game, true otherwise.
     */
    private boolean signalGameCreation(Scanner input) throws IOException, JDOMException, NullPointerException {
        
        // Creates new game.
        if (this.isNewGame) {
            return this.parser.signalGameCreation(this, input);
        }

        // Or determines whether to create or load a new game from an
        // existing user.
        return existingLoadGameOrNewGame(input);
    }
    
    /*
     * Method that runs the program and starts the file
     * reading writing and game start.
     * @param: the FileReader and scanner for input.
     * @return: None.
     */
    public void runChessInterface(Scanner input) {
        boolean restart = true;
        do {
            try {
                if (this.retrieveFiles(input)) {
              
                    // User was never found or user decided to quit game.
                    if (!parser.startPlayerCreation(input)) {
                        return;
                    }
                    
                    this.player = this.parser.retrievePlayerCreation();
                    
                    // Now that player is created, move on to game construction.
                    // Player could be null if a player was never found.
                    if (this.player != null) {
                        
                        // If the user does not have an existing game, create a new game key
                        // and write the new user with the game key to the user xml file.
                        if (this.player.getIdQueue().peek() == null) {
                            this.isNewGame = true;
                  
                            if(!parser.retrieveUserId(input)){
                                return;
                            }
                        }
                        
                        // Create game using id from last game which is really the newest
                        // game stored.
                        this.game = new Game(player.getIdQueue().peekLast());
                        
                        // Model game after xml files.
                        if (!this.signalGameCreation(input)) {
                            return;
                        }
 
                        // Start game.
                        this.game.startGame(input);
                        
                        boolean savedGame = false;
                        if (this.game.getGameModel().getUseAutoSave() || this.saveGame(input)) {
                            this.parser.saveGame(this, input);
                            savedGame = true;
                        }
               
                        this.parser.getParserWriter().updateExistingGameKeys(this.player, savedGame, this.isNewGame, input);
                   
                    }
                    restart = false;
               }
            }
            catch (NullPointerException | IOException | JDOMException e) {
                restart = ExceptionHandler.handleException("trying to access the xml files", "Restart program", input);
            }
        } while (restart);
    }
}