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
//                               class from Game since the MVC pattern is being incorporated.
//                               describe the current game.
// [2/29/20] [Nichole Maldonado] moved getters and setters for 
//                               max time, id, chat, and auto save from Game.
// [3/30/20] [Nichole Maldonado] refactored verifyAndInitiateMove, removing print statements
//                               and game board printing. Only kept game logic

package utep.cs3331.lab5.chess;

import utep.cs3331.lab5.chess.exceptions.PieceInPlaceException;
import utep.cs3331.lab5.chess.GameBoard;
import utep.cs3331.lab5.chess.MinimumBoardDimensions;

public class GameModel implements MinimumBoardDimensions {
    private String id;
    private int maxTime;
    private boolean useAutoSave;
    private boolean hasChat;
    private GameBoard board;
    
    public GameModel(String id){
        this.id = id;
        
        // Default time to 1.
        // Will be updated if user selects another time.
        this.maxTime = 1;
    }
    
    /*
     * Getter method for the field id.
     * @param: None.
     * @return: the id field.
     */
    public String getId() {
        return this.id;
    }
    
    /*
     * Getter for the gameBoard attribute.
     * @param: None
     * @return: The gameBoard attribute.
     */
    public GameBoard getBoard() {
        return this.board;
    }
    
    /*
     * Getter method for the field maxTime.
     * @param: None.
     * @return: the maxTime field.
     */
    public int getMaxTime() {
        return this.maxTime;
    }
    
    /*
     * Getter method for the field useAutoSave.
     * @param: None.
     * @return: the useAutoSave field.
     */
    public boolean getUseAutoSave() {
        return this.useAutoSave;
    }
    
    /*
     * Getter method for the field hasChat.
     * @param: None.
     * @return: the hasChat field.
     */
    public boolean getHasChat() {
        return this.hasChat;
    }
    
    /*
     * Setter method for the field id.
     * @param: the id to set the corresponding field to.
     * @return: None.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /*
     * Setter method for the field maxTime.
     * @param: the maxTime to set the corresponding field to.
     * @return: None.
     */
    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }
    
    /*
     * Setter method for the field useAutoSave.
     * @param: the useAutoSave to set the corresponding field to.
     * @return: None.
     */
    public void setUseAutoSave(boolean useAutoSave) {
        this.useAutoSave = useAutoSave;
    }
    
    /*
     * Setter method for the field hasChat.
     * @param: the hasChat to set the corresponding field to.
     * @return: None.
     */
    public void setHasChat(boolean hasChat) {
        this.hasChat = hasChat;
    }
    
    /*
     * Getter for the gameBoard attribute.
     * @param: None
     * @return: The gameBoard attribute.
     */
    public void setBoard(GameBoard game) {
        this.board = board;
    }
    
    /*
     * Method that creates a new GameBoard based on the number of
     * rows and columns.
     * @param: the number of rows and columns.
     * @return: None.
     */
    public void createBoard(int numRows, int numColumns) {
        this.board = new GameBoard(numRows, numColumns);
    }
    
    /*
     * Method that verifies move from the initial positions to the
     * new positions and moves the piece if valid.
     * @param: a scanner for user input.
     * @return: None.
     */
    public String verifyAndInitiateMove(int xPosition, int yPosition, char newXPosition, int newYPosition) throws NullPointerException, PieceInPlaceException {
        
        String canMoveNotifier = "";
        boolean canMove = false;
        
        int newXPositionValue = Integer.parseInt(String.valueOf(newXPosition - MinimumBoardDimensions.MIN_X_POSITION));

        // If a chess piece does not exist at the new position, then validate the move.
        if (this.board.getChessBoard()[newYPosition - 1][newXPositionValue] == null) {
            canMove = this.board.getChessBoard()[yPosition][xPosition].move(newXPosition, newYPosition); 
        }

        // Otherwise, you cannot move the piece.
        else {
            throw new PieceInPlaceException("PieceInPlaceException");
        }
        if (!canMove) {
            canMoveNotifier = "not";
        }
        
        // Update the game board and pieces with the move.
        if(canMove) {
            this.board.movePieces(xPosition, yPosition, newXPositionValue, newYPosition - 1);
        }
        return canMoveNotifier;
    }
}