// Nichole Maldonado
// CS331 - Lab 4, ExpertiseLevel enum

/*
 * This enum file contains the FileType enum 
 * which defines the 3 different types of xml files
 * and the method format name which returns a String of
 * enum value in lower case.
 */

// changelog
// [4/30/20] [Nichole Maldonado] created fileType enum for FilePaths

package utep.cs3331.lab5.files;

/*
 * Enum which defines the 3 different types of xml files
 * and the method format name which returns a String of
 * enum value in lower case.
 */
public enum FileType {
    USERS, CONFIGS, CHESS_TEMPLATE;
    
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