// Nichole Maldonado
// CS331 - Lab 5, King Class

/*
 * This class file contains the King class which is a derived class
 * of the Queen class, which inherits from the ChessPiece class
 * and thus inherits all the fields and methods. The class also overrides
 * the validMove function but still calls the super's method
 * to continue the parameter checks.
 */

// changelog
// [2/07/20] [Nichole Maldonado] made King class extend the Queen class.
// [2/07/20] [Nichole Maldonado] created validInitialPosition method to
//                               check the x and y positions before creating the
//                               pawn.
// [2/07/20] [Nichole Maldonado] added createKing method to take care of King
//                               creation and verify the initial positions.
// [2/07/20] [Nichole Maldonado] overrode the super's validMove method by
//                               checking if the King moves by one and calls the
//                               super's method to see if the King can move
//                               vertically, horizontally, or diagonally.
// [2/07/20] [Nichole Maldonado] added class to a package to organize all the classes.
// [2/08/20] [Nichole Maldonado] removed validInitialPosition to allow for the chess
//                               piece to initially start anywhere.
// [2/28/20] [Nichole Maldonado] overrode pieceInitial to create a string of the piece's intial
//                               to be used when drawing the board.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.

package utep.cs3331.lab5.chess.chesspieces;

import utep.cs3331.lab5.chess.chesspieces.Queen;

/*
 * The King Class inherits all the methods and fields
 * from the Queen clas, which inherits from the
 * ChessPiece class, which includes name, 
 * xPosition, yPosition, and isWhite. The class
 * paritially overrides the validMove method.
 */
public class King extends Queen {
    
    /*
     * Constructor for King that uses the super's constructor
     * to initialize the object's fields and play attribute.
     * @param: a string denoted as "King", the x position, y position, and
     *         a boolean that is true if the piece is white and play boolean. 
     * @return: None.
     * Assume the xPosition and yPosition are valid positions within the board.
     */
    public King(String name, char xPosition, int yPosition, boolean isWhite, boolean play) {
        super(name, xPosition, yPosition, isWhite, play);
    }
    
    /*
     * Method that determines if the King can move to the new positions.
     * @param: the new x and y positions that the piece could potentially
     *         move to.
     * @return: true if the King can move to the new position, false otherwise.
     */
    @Override
    public boolean validMove(char xPosition, int yPosition) {
        return this.xDifference(xPosition) <= 1 && 
                this.yDifference(yPosition) <= 1 &&
                super.validMove(xPosition, yPosition);
    }
    
    /*
     * Method that creates the initial of the chess piece starting
     * with "Ki" and then the x position and y position of the piece.
     * @param: None.
     * @return: A string of "Ki" + xPosition + yPosition.
     */
    @Override
    public String pieceInitial() {
        
        // Remove whitespace for alignment when displaying board.
        return (super.pieceInitial().substring(0,2) + this.getName().substring(1,2));
    }
}