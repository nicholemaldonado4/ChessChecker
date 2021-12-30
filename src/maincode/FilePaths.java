// Nichole Maldonado
// CS331 - Lab 4, FilePaths Class

/*
 * The FilePaths class contains a fielPathsMap which
 * hashes file paths based on the file type.
 * Available behaviours include getters
 * for the map and getter for a specific path
 * based on the file type key.
 */

// changelog
// [2/29/20] [Nichole Maldonado] refactored code by creating a single
//                               filePath that will store the file paths
//                               for the user and chess xml at any time.
// [2/29/20] [Nichole Maldonado] Created getters and setters for the attributes.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.
// [4/30/20] [Nichole Maldonado] Changed 3 fields to a single hash map which hashes
//                               the file paths based on their file type.

package utep.cs3331.lab5.files;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

import utep.cs3331.lab5.files.FileType;

/*
 * The FilePaths class contains a fielPathsMap which
 * hashes file paths based on the file type.
 * Available behaviours include getters
 * for the map and getter for a specific path
 * based on the file type key.
 */
public class FilePaths {
    private Map<FileType, String> filePathsMap;
    
    public FilePaths() {
        this.filePathsMap = new HashMap<FileType, String>();
    }
    
    /*
     * Gets a specific file path based on the key.
     * @param: The file type key.
     * @return: the file path associated with the file type key.
     */
    public String getFilePath(FileType key) {
        return this.filePathsMap.get(key);
    }
    
    /*
     * Getster for the filePathsMap attribute
     * @param: None.
     * @return: the filePathsMap.
     */
    public Map<FileType, String> getAllFilePaths() {
        return this.filePathsMap;
    }
    
    /* Ensures that filePath includes a .txt file at the end.
     * @param: a string of the file path that will be evaluated.
     * @return: Returns false if filePath does not end in .txt and returns
     *        true otherwise.  
     */              
    private boolean isXmlFile(String filePath) {
                
        // Returns false if the filePath does not have at least 5 letters since a
        // valid .txt file name can have a minimum of 5 letters.
        if (filePath.length() < 5) {
            System.out.print("\nInvalid file. The file must be a .xml file with at ");
            System.out.println("least a one character name.");
            return false;
        }
        
        // If the length of filePath is greater than or equal to 5, then true is
        // returned only if filePath ends in ".txt".
        if (filePath.substring(filePath.length() - 4).equals(".xml")) {
            return true;
        }
                
        System.out.println("\nInvalid file. The file must be a .xml file.");
        return false;
    }
    
    /*
     * Method that collects a file path from the user and
     * if it is valid hashes the file path based on the filetype
     * into the filePathsMap.
     * @param: the scanner to collect the user's input and the file type.
     * @return: None.
     */
    public void collectFilePath(Scanner input, FileType fileType) {
        boolean foundPath = false;
        
        while (!foundPath) {
            System.out.printf("Enter the path for the %s xml file: ", fileType.formatName());
            String filePath = input.nextLine();
            
            //foundPath = this.isXmlFile(filePath);
            foundPath = true;
            if(foundPath){
                this.filePathsMap.put(fileType, filePath);
            }
        }
    }
}