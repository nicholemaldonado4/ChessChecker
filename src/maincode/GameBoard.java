// Nichole Maldonado
// CS331 - Lab 4, GameController Class

/*
 * The GameBoard class contains the chess board
 * on a 2D array and provides the functionality
 * to move the chess pieces as well as create
 * a game board with pieces in their initial positions.
 */

// changelog
// [2/28/20] [Nichole Maldonado] added getters and setters for 
//                               attributes that include board,
//                               chessBoard and pieceConfigurations.
// [2/29/20] [Nichole Maldonado] created function to move the chess pieces
// [2/29/20] [Nichole Maldonado] in createNewGameBoard, added the ability to
//                               simultanelously write the new pieces to the file
//                               resulting in more efficient code.
// [2/29/20] [Nichole Maldonado] removed pieceConfiguration class and added
//                               numRows and numColumns attribute.
// [3/01/20] [Nichole Maldonado] Reorganized import statements
// [3/01/20] [Nichole Maldonado] Removed createNewGameBoard method
// [4/25/20] [Nichole Maldonado] Changed package to lab5.
// [4/25/20] [Nichole Maldonado] Removed game board draw and moved it to GameView to
//                               implement MVC.

package utep.cs3331.lab5.chess;

import utep.cs3331.lab5.chess.chesspieces.ChessPiece;
import utep.cs3331.lab5.chess.chesspieces.ChessPieceTypes;
import utep.cs3331.lab5.chess.chesspieces.Bishop;
import utep.cs3331.lab5.chess.chesspieces.King;
import utep.cs3331.lab5.chess.chesspieces.Knight;
import utep.cs3331.lab5.chess.chesspieces.Pawn;
import utep.cs3331.lab5.chess.chesspieces.Queen;
import utep.cs3331.lab5.chess.chesspieces.Rook;
import utep.cs3331.lab5.chess.MinimumBoardDimensions;

/*
 * The GameBoard class contains the chess board
 * on a 2D array and provides the functionality
 * to move the chess pieces as well as create
 * a game board with pieces in their initial positions.
 */
public class GameBoard implements MinimumBoardDimensions {
    private ChessPiece[][] chessBoard;
    private int numRows;
    private int numColumns;
    
    /*
     * Default constructor for the Game board which
     * creates a new game board based on the number of 
     * rows and columns.
     * Note: This is the standard game board size and 
     * all other xml files will be checked to ensure game board
     * attribute matches.
     * @param: None
     * @return: The gameBoard attribute.
     */
    public GameBoard(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.chessBoard = new ChessPiece[this.numRows][this.numColumns];
    }
    
    /*
     * Getter for the chessBoard attribute.
     * @param: None
     * @return: The chessBoard attribute.
     */
    public ChessPiece[][] getChessBoard() {
        return this.chessBoard;
    }
    
    /*
     * Getter for the numRows attribute.
     * @param: None
     * @return: The numRows attribute.
     */
    public int getNumRows() {
        return this.numRows;
    }
    
    /*
     * Getter for the numColumns attribute.
     * @param: None
     * @return: The numColumns attribute.
     */
    public int getNumColumns() {
        return this.numColumns;
    }
    
    // No setters, once a chess board is made, it cannot be
    // changed.
    
    /*
     * Method that add a chess piece to the board.
     * @param: the type of chess piece, x position, y position, and whether
     *        the chess piece is white or black.
     * @return: None.
     */
    public void addChessPiece(ChessPieceTypes pieceType, char xPosition, int yPosition, 
            boolean isWhite, boolean play) {
        ChessPiece piece;
        
        // Creates a specific ChessPiece based on the pieceType.
        switch (pieceType) {
            case PAWN:
                piece = new Pawn(pieceType.formatName(), xPosition, yPosition, isWhite, play);
                break;
            case ROOK:
                piece =  new Rook(pieceType.formatName(), xPosition, yPosition, isWhite, play);
                break;
            case KNIGHT:
                piece = new Knight(pieceType.formatName(), xPosition, yPosition, isWhite, play);
                break;
            case QUEEN:
                piece = new Queen(pieceType.formatName(), xPosition, yPosition, isWhite, play);
                break;
            case BISHOP:
                piece = new Bishop(pieceType.formatName(), xPosition, yPosition, isWhite, play);
                break;
            default:
                piece =new King(pieceType.formatName(), xPosition, yPosition, isWhite, play);
                break;
        }
        
        // Board starts at 0,0.
        this.chessBoard[yPosition - 1][xPosition - MIN_X_POSITION] = piece;
    }
    
    /*
     * Method that moves the current chess piece at the x -y position to the
     * new position.
     * @param: the current position of the piece and the new position of the
     *        piece.
     * @return: None.
     */
    public void movePieces(int xPosition, int yPosition, int newXPosition, int newYPosition){
        this.chessBoard[newYPosition][newXPosition] = this.chessBoard[yPosition][xPosition];
        this.chessBoard[yPosition][xPosition] = null;
    }
}