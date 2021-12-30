// Nichole Maldonado
// CS331 - Lab 4, Queen Class

/*
 * This class file contains the Queen class which is a derived class
 * of the Rook class, which inherits from the ChessPiece class
 * and thus inherits all the fields and methods. The class also overrides
 * the validMove function but still calls the super's method
 * to continue the parameter checks.
 */

// changelog
// [2/07/20] [Nichole Maldonado] made Queen class extend the Rook class.
// [2/07/20] [Nichole Maldonado] created validInitialPosition method to
//                               check the x and y positions before creating the
//                               pawn.
// [2/07/20] [Nichole Maldonado] added createQueen method to take care of Queen
//                               creation and verify the initial positions.
// [2/07/20] [Nichole Maldonado] overrode the super's validMove method by
//                               checking if the Queen can move diagonally but still calls
//                               the super's method to see if the x and y positions are
//                               valid.
// [2/07/20] [Nichole Maldonado] added class to a package to organize all the classes.
// [2/08/20] [Nichole Maldonado] removed validInitialPosition to allow for the chess
//                               piece to initially start anywhere.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.

package utep.cs3331.lab5.chess.chesspieces;

import utep.cs3331.lab5.chess.chesspieces.Rook;
import utep.cs3331.lab5.chess.chesspieces.DiagonalMoveValidator;

/*
 * The Queen Class inherits all the methods and fields
 * from the Rook class, which is derived from the
 * the ChessPiece class which includes name, 
 * xPosition, yPosition, and isWhite. The class
 * paritially ovverrides the validMove method.
 */
public class Queen extends Rook implements DiagonalMoveValidator {
    
    /*
     * Constructor for Queen that uses the super's constructor
     * to initialize the object's fields and play attribute.
     * @param: a string denoted of the piece's type, the x position, y position, and
     *         a boolean that is true if the piece is white. 
     * @return: None.
     * Assume the xPosition and yPosition are valid positions within the board.
     */
    public Queen(String name, char xPosition, int yPosition, boolean isWhite, boolean play) {
        super(name, xPosition, yPosition, isWhite, play);
    }
    
    /*
     * Method that determines if the Queen can move to the new positions.
     * @param: the new x and y positions that the piece could potentially
     *         move to.
     * @return: true if the Queen can move to the new position, false otherwise.
     */
    @Override
    public boolean validMove(char xPosition, int yPosition) {
        return super.validMove(xPosition, yPosition) || 
                DiagonalMoveValidator.moveDiagonally(this.getXPosition(), 
                this.getYPosition(), xPosition, yPosition);
    }
}