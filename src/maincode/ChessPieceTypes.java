// Nichole Maldonado
// CS331 - Lab 4, ChessPieceTypes enum

/*
 * This enum file contains the ChessPieceTypes enum which defines
 * the 6 types of chess pieces. It also includes the function format
 * name which returns a String of the enum value in lower case.
 */

// changelog
// [1/30/20] [Nichole Maldonado] created enum with the 6 different types of 
//                               chess pieces.
// [1/30/20] [Nichole Maldonado] created a formatName method to allow the
//                               chess pieces' classes' name fields to
//                               be assigned to a proper string.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.
// [4/25/20] [Nichole Maldonado] Removed call to super for validateMove method since implementing
//                               template method.

package utep.cs3331.lab5.chess.chesspieces;

/*
 * Enum which defines the 6 different chess pieces
 * and the method format name which returns s String of
 * the first letter capitalized followed by lower case letters.
 */
public enum ChessPieceTypes {
    PAWN, ROOK, BISHOP, KNIGHT, KING, QUEEN;
    
    /*
     * Method that returns a String of the current instances first
     * letter capitalized followed by lower case letters.
     * @param: None.
     * @return: a String of the current instance's first letter capitalized
     *          followed by lower case letters.
     */
    public String formatName() {
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
}