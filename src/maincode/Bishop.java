// Nichole Maldonado
// CS331 - Lab 5, Bishop Class

/*
 * This class file contains the Bishop class which is a derived class
 * of the ChessPiece class and thus inherits all
 * the fields and methods. The class also overrides the
 * validMove function but still calls the super's method
 * to continue the parameter checks.
 */

// changelog
// [2/07/20] [Nichole Maldonado] made Bishop class extend the ChessPiece class.
// [2/07/20] [Nichole Maldonado] created validInitialPosition method to
//                               check the x and y positions before creating the
//                               pawn.
// [2/07/20] [Nichole Maldonado] added createBishop method to take care of Pawn
//                               creation and verify the initial positions.
// [2/07/20] [Nichole Maldonado] overrode the super's validMove method by
//                               checking if the Bishop can move diagonally
//                               but still calls the super's to make sure the
//                               x and y position are correct.
// [2/07/20] [Nichole Maldonado] added class to a package to organize all the classes.
// [2/08/20] [Nichole Maldonado] removed validInitialPosition to allow for the chess
//                               piece to initially start anywhere.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.
// [4/25/20] [Nichole Maldonado] Removed call to super for validateMove method since implementing
//                               template method.

package utep.cs3331.lab5.chess.chesspieces;

import utep.cs3331.lab5.chess.chesspieces.ChessPiece;
import utep.cs3331.lab5.chess.chesspieces.DiagonalMoveValidator;

/*
 * The Bishop Class inherits all the methods and fields
 * from the ChessPiece class which include name, 
 * xPosition, yPosition, and isWhite. The class
 * paritially overrides the validMove method.
 */
public class Bishop extends ChessPiece implements DiagonalMoveValidator {
    
    /*
     * Constructor for Bishop that uses the super's constructor
     * to initialize the object's fields and play attribute.
     * @param: a string denoted as "Bishop", the x position, y position,
     *         a boolean that is true if the piece is white and play boolean.
     * @return: None.
     * Assume the xPosition and yPosition are valid positions within the board.
     */
    public Bishop (String name, char xPosition, int yPosition, boolean isWhite, boolean play) {
        super(name, xPosition, yPosition, isWhite, play);
    }
    
    /*
     * Method that determines if the Bishop can move to the new positions.
     * @param: the new x and y positions that the piece could potentially
     *         move to.
     * @return: true if the Bishop can move to the new position, false otherwise.
     */
    @Override
    public boolean validMove(char xPosition, int yPosition) {
        
        // Move diagonally checks that the xDifference is greater
        // than 1, so no need to call the super's validMove.
        return DiagonalMoveValidator.moveDiagonally(this.getXPosition(), 
                this.getYPosition(), xPosition, yPosition);
    }
}