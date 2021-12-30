// Nichole Maldonado
// CS331 - Lab 4, Pawn Class

/*
 * This class file contains the Pawn class which is a derived class
 * of the ChessPiece class and thus inherits all
 * the fields and methods. The class also overrides the
 * validMove function but still calls the super's method
 * to continue the parameter checks.
 */

// changelog
// [2/07/20] [Nichole Maldonado] made Pawn class extend the ChessPiece class.
// [2/07/20] [Nichole Maldonado] created validInitialPosition method to
//                               check the x and y positions before creating the
//                               pawn.
// [2/07/20] [Nichole Maldonado] added createPawn method to take care of Pawn
//                               creation and verify the initial positions.
// [2/07/20] [Nichole Maldonado] overrode the super's validMove method by
//                               checking if the pawn can move vertically
//                               but still calls the super's to make sure the
//                               x and y position are correct.
// [2/07/20] [Nichole Maldonado] added class to a package to organize all the classes.
// [2/08/20] [Nichole Maldonado] removed validInitialPosition to allow for the chess
//                               piece to initially start anywhere.
// [2/29/20] [Nichole Maldonado] allowed pawn to move a maximum of two spaces initially.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.
// [4/25/20] [Nichole Maldonado] Removed call to super for validateMove method since implementing
//                               template method.

package utep.cs3331.lab5.chess.chesspieces;

import utep.cs3331.lab5.chess.chesspieces.ChessPiece;

/*
 * The Pawn Class inherits all the methods and fields
 * from the ChessPiece class which include name, 
 * xPosition, yPosition, and isWhite. The class
 * paritially overrides the validMove method.
 */
public class Pawn extends ChessPiece {
    
    /*
     * Constructor for Pawn that uses the super's constructor
     * to initialize the object's fields and play attribute.
     * @param: a string denoted as "Pawn", the x position, y position, and
     *         a boolean that is true if the piece is white and boolean play.
     * @return: None.
     * Assume the xPosition and yPosition are valid positions within the board.
     */
    public Pawn(String name, char xPosition, int yPosition, boolean isWhite, boolean play) {
        super(name, xPosition, yPosition, isWhite, play);
    }
    
    /*
     * Method that determines if the Pawn can move to the new positions.
     * @param: the new x and y positions that the piece could potentially
     *         move to.
     * @return: true if the Pawn can move to the new position, false otherwise.
     */
    @Override
    public boolean validMove(char xPosition, int yPosition) {
        int moveAmount = ((this.getIsWhite() && this.getYPosition() == 2) || 
                          (!this.getIsWhite() && this.getYPosition() == 7)) ? 2 : 1;

        return (xPosition == this.getXPosition()) && (
                (this.getIsWhite() && yPosition - this.getYPosition() <= moveAmount && 
                yPosition - this.getYPosition() >= 0) || 
                (!this.getIsWhite() && this.getYPosition() - yPosition <= moveAmount &&
                this.getYPosition() - yPosition >= 0));
        
    }
}