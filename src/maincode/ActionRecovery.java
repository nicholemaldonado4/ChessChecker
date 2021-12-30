// Nichole Maldonado
// CS331 - Lab 5, ChessPieceTypes enum

/*
 * This enum file contains the ActionRecovery enum which contains
 * two enums, piece dne and piece already exists, that are used to
 * determine the action to be taken by the game view.
 */

// changelog
// [4/25/20] [Nichole Maldonado] created enum with the 2 different types of 
//                               action recoveries for the GameView to evaluate.

package utep.cs3331.lab5.chess.exceptions;

/*
 * Enum which defines the two different type of action recoveries
 * to be evaluated by the GameView.
 */
public enum ActionRecovery {
    PIECE_DNE, PIECE_ALREADY_EXISTS;
    
    /*
     * Method that returns a String of the current instances in lowercase.
     * @param: None.
     * @return: a String of the current instance in lower case.
     */
    public String formatName() {
        
        String formattedName = this.name().toLowerCase().replace('_', ' ');
        
        // If the enum ends with dne, extend to "does not exist".
        if (formattedName.length() >= 3) {
            if (formattedName.substring(this.name().length() - 3, this.name().length()).equals("dne")) {
                formattedName = formattedName.substring(0, this.name().length() - 3) + "does not exist";
            }
        }
        
        return formattedName;
    }
}