// Nichole Maldonado
// CS331 - Lab 5, Rook Class

/*
 * This class file contains the Rook class which is a derived class
 * of the ChessPiece class and thus inherits all
 * the fields and methods. The class also overrides the
 * validMove function but still calls the super's method
 * to continue the parameter checks.
 */

// changelog
// [2/07/20] [Nichole Maldonado] made Rook class extend the ChessPiece class.
// [2/07/20] [Nichole Maldonado] created validInitialPosition method to
//                               check the x and y positions before creating the
//                               pawn.
// [2/07/20] [Nichole Maldonado] added createRook method to take care of Rook
//                               creation and verify the initial positions.
// [2/07/20] [Nichole Maldonado] overrode the super's validMove method by
//                               checking if the Rook can move vertically or
//                               horizontally and still calls the super's method
//                               to ensure the x and y positions are valid.
// [2/07/20] [Nichole Maldonado] added class to a package to organize all the classes.
// [2/08/20] [Nichole Maldonado] removed validInitialPosition to allow for the chess
//                               piece to initially start anywhere.
// [2/10/20] [Nichole Maldonado] fixed bug for rook that was not recognizing a move
//                               horizontally.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.
// [4/25/20] [Nichole Maldonado] Removed call to super for validateMove method since implementing
//                               template method.

package utep.cs3331.lab5.chess.chesspieces;

import utep.cs3331.lab5.chess.chesspieces.ChessPiece;

/*
 * The Rook Class inherits all the methods and fields
 * from the ChessPiece class which include name, 
 * xPosition, yPosition, and isWhite. The class
 * paritially overrides the validMove method.
 */
public class Rook extends ChessPiece {

    /*
     * Constructor for Rook that uses the super's constructor
     * to initialize the object's fields with play attribute.
     * @param: a string denoting the piece's type, the x position, y position, 
     *         a boolean that is true if the piece is white, and play boolean. 
     * @return: None.
     * Assume the xPosition and yPosition are valid positions within the board.
     */
    public Rook(String name, char xPosition, int yPosition, boolean isWhite, boolean play){
        super(name, xPosition, yPosition, isWhite, play);
    }
    
    /*
     * Method that determines if the Rook can move to the new positions.
     * @param: the new x and y positions that the piece could potentially
     *         move to.
     * @return: true if the Rook can move to the new position, false otherwise.
     */
    public boolean validMove(char xPosition, int yPosition) {
        int xDifference = this.xDifference(xPosition);
        int yDifference = this.yDifference(yPosition);
        return (xDifference == 0 && yDifference > 0) || (yDifference == 0 && xDifference > 0);
    }
}