// Nichole Maldonado
// CS331 - Lab 5, Game Class

/*
 * Game class that represents the current
 * attributes of the game including whether
 * the game has chat, an id, and uses auto save.
 * Game class that contains setters and getters
 * to modify its fields and thus the state of the game.
 */

// changelog
// changelog
// [2/29/20] [Nichole Maldonado] moved max time, id, chat, and autosave to
//                               class from GameController since all the attributes
//                               describe the current game.
// [2/29/20] [Nichole Maldonado] added getters and setters for 
//                               max time, id, chat, and auto save to change the state of the game.
// [3/30/20] [Nichole Maldonado] moved max time, save method, and chat method from main
//                               program and added to class to set fields directly.
// [4/25/20] [Nichole Maldonado] changed package to lab5.
// [4/25/20] [Nichole Maldonado] changed name to game controller and refactored code into
//                               GameModel and GameView to support MVC design pattern.
// [4/25/20] [Nichole Maldonado] removed all attributes and replaced with a GameView and GameModel.

package utep.cs3331.lab5.chess;

import java.util.InputMismatchException;
import java.util.Scanner;

import utep.cs3331.lab5.chess.exceptions.PieceInPlaceException;
import utep.cs3331.lab5.chess.exceptions.ActionRecovery;
import utep.cs3331.lab5.chess.GameBoard;
import utep.cs3331.lab5.chess.GameModel;
import utep.cs3331.lab5.chess.GameView;
import utep.cs3331.lab5.chess.MinimumBoardDimensions;

/*
 * Game class that represents the current
 * attributes of the game including whether
 * the game has chat, an id, and uses auto save.
 * Game class that contains setters and getters
 * to modify its fields and thus the state of the game.
 */
public class Game implements MinimumBoardDimensions {
    private GameView gameView;
    private GameModel gameModel;
    
    /*
     * Constructor that creates a game and initializes
     * the id with the id provided.
     * @param: a string id.
     * @return: none.
     */
    public Game(String id) {
        this.gameView = new GameView();
        this.gameModel = new GameModel(id);
    }
    
    /*
     * Getter for returns gameView.
     * @param: None.
     * @return: the gameView.
     */
    public GameView getGameView() {
        return this.gameView;
    }
    
    /*
     * Getter for returns gameModel.
     * @param: None.
     * @return: the gameModel.
     */
    public GameModel getGameModel() {
        return this.gameModel;
    }
    
    /*
     * Method that intiates the retrieval of the chat, max time
     * limit, and save method from the user.
     * @param: the scanner for input.
     * @return: None.
     */  
    public void setTimeChatSave(Scanner input) {
        this.gameModel.setHasChat(this.gameView.retrieveHasChat(input));
        this.gameModel.setMaxTime(this.gameView.retrieveTimeLimit(input));
        this.gameModel.setUseAutoSave(this.gameView.retrieveSaveMethod(input));
    }
    
    /*
     * Method that tells gameView to print the game instructions and
     * recieves user input from view. Then passes the gameModel to determine
     * if user selected a valid chess piece move.
     * @param: a scanner for user input.
     * @return: None.
     */
    public void startGame(Scanner input) {
    
        this.gameView.drawGame(this.gameModel.getBoard().getChessBoard(), this.gameModel.getBoard().getNumColumns());
        boolean quitPlaying = false;
        
        // Continues to play as long as user wants to.
        while (!quitPlaying) {
           
            // Gets chess piece initial positions.
            char xPosition = this.gameView.retrieveXPosition(input, this.gameModel.getBoard().getNumColumns(), true);
            int yPosition = this.gameView.retrieveYPosition(input, this.gameModel.getBoard().getNumRows());
            
            int xPositionValue = Integer.parseInt(String.valueOf(xPosition - MinimumBoardDimensions.MIN_X_POSITION));
                                              
            if (this.gameModel.getBoard().getChessBoard()[yPosition - 1][xPositionValue] == null) {
                this.gameView.printErrorResolution(ActionRecovery.PIECE_DNE);
            }
            else {
                
                // Gets new chess piece position.
                char newXPosition = this.gameView.retrieveXPosition(input, this.gameModel.getBoard().getNumColumns(), false);
                int newYPosition = this.gameView.retrieveYPosition(input, this.gameModel.getBoard().getNumRows());
                
                // Verifies chess position.
                try {
                    String oldChessName = this.gameModel.getBoard().getChessBoard()[yPosition-1][xPositionValue].getName();
                    String moveNotifier = gameModel.verifyAndInitiateMove(xPositionValue, 
                            yPosition - 1, newXPosition, newYPosition);
                    
                    // Print result of move.
                    this.gameView.printPiecePosition(moveNotifier, oldChessName, newXPosition, newYPosition);
                }
                catch (NullPointerException e) {
                    this.gameView.printErrorResolution(ActionRecovery.PIECE_DNE);
                }
                catch (PieceInPlaceException e) {
                    this.gameView.printErrorResolution(ActionRecovery.PIECE_ALREADY_EXISTS);
                }
                this.gameView.drawGame(this.gameModel.getBoard().getChessBoard(), this.gameModel.getBoard().getNumColumns());
            }
            quitPlaying = this.gameView.quitGame(input);
            
        }
    }
}
