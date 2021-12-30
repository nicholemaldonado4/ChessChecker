// Nichole Maldonado
// CS331 - Lab 4, ExpertiseLevel enum

/*
 * This enum file contains the ExpertiseLevel enum which defines
 * the 4 types of expert chess levels. It also includes the function format
 * name which returns a String of the first letter capitalized
 * followed by lower case letters.
 */

// changelog
// [2/28/20] [Nichole Maldonado] created enum with the 4 different types of 
//                               user expertise levels
// [2/28/20] [Nichole Maldonado] created a formatName method to allow the
//                               colors to be displayed in lower case.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.

package utep.cs3331.lab5.players;

/*
 * Enum which defines the 4 different user expertise levels
 * and the method format name which returns s String of
 * enum value in lower case.
 */
public enum ExpertiseLevel {
    NOVICE, MEDIUM, ADVANCED, MASTER;
    
    /*
     * Method that returns a String of the current instances
     * in lower case.
     * @param: None.
     * @return: a String of the current instance in lower case.
     */
    public String formatName() {
        return this.name().toLowerCase();
    }
}