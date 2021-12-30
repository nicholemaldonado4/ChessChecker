// Nichole Maldonado
// CS331 - Lab4, DiagonalMoveValidator Interface

/*
 * This interface determines if the current x and y position
 * can be reached diagonally from the new x and y positions.
 */

// changelog
// [3/04/20] [Nichole Mladonado] moved method from ChessPieces class and created an interface
//                               to reduce overhead.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.

package utep.cs3331.lab5.chess.chesspieces;

/*
 * BoardDimensions interface declares the
 * minimum and maximum x and y positions on the
 * alloted chess board.
 */
public interface DiagonalMoveValidator {
    
    /*
     * Method that determines if x and y positions can be reached diagonally
     * from new x and y position.
     * @param: the new x and y position to be validated, and the current x and positions.
     * @return: a boolean if the new x and y positions can be reached
     *          diagonally from the current position.
     */
    public static boolean moveDiagonally(char xPosition, int yPosition, char newXPosition, int newYPosition) {
         return (Math.abs(xPosition - newXPosition) != 0) && 
                (Math.abs(xPosition - newXPosition) == Math.abs(yPosition - newYPosition));
    }
}