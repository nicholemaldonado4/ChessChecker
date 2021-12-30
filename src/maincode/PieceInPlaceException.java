// Nichole Maldonado
// CS331 - Lab 5, PieceInPlaceException

/*
 * The PieceInPlaceException is thrown when a piece is already 
 * located in the new position designated.
 */

// changelog
// [4/25/20] [Nichole Maldonado] created exception class since the GameModel cannot
//                               print this exception. Throw it to Game to deal with it.


package utep.cs3331.lab5.chess.exceptions;

/*
 * PieceInPlaceException is thrown when a piece is
 * already located in the new position that the piece
 * was attempted to move to.
 */
public class PieceInPlaceException extends Exception{
    
    /*
     * Hands the exception message to Exception.
     * @param: The exception message.
     * @return: None.
     */
    public PieceInPlaceException(String message) {
        super(message);
    }
}