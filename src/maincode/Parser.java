// Nichole Maldonado
// CS331 - Lab 4, Parser Class

/*
 * The parser class contains two objects, parserReader and parserWriter,
 * These objects are called when an XML file either needs to be written
 * to based on the objects created or read from. The Parser class uses
 * dependency injection because both the parserReader and parserWriter
 * class share the same FilePaths which allows both objects to stay updated
 * with the latest path that the user selects.
 */

// changelog
// [3/03/20] [Nichole Maldonado] added getters for parserReader and parserWriter.
//                               The only setters for the objects is one single set
//                               parser reader and writer to maintain the dependency injection.
// [3/03/20] [Nichole Maldonado] moved code from main program and added tasks for
//                               parser to dictate whether file reader or writer is needed.
// [3/03/20] [Nichole Maldonado] Added a save function based on whether a new game needs to
//                               be created or an old game needs to be updated.
// [3/03/20] [Nichole Maldonado] Threw exceptions for certain methods due to the other methods
//                               called. After hand throwing exceptions in the program, the
//                               methods below were determined to need to throw exceptions to
//                               logically maintain the controll flow of the program.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.

package utep.cs3331.lab5.files;

import java.io.IOException;
import java.util.Scanner;

import utep.cs3331.lab5.chess.Controller;
import utep.cs3331.lab5.files.FilePaths;
import utep.cs3331.lab5.files.FileType;
import utep.cs3331.lab5.files.ParserReader;
import utep.cs3331.lab5.files.ParserWriter;
import utep.cs3331.lab5.players.ChessPlayer;

import org.jdom2.JDOMException;

/*
 * The parser class contains two objects, parserReader and parserWriter,
 * These objects are called when an XML file either needs to be written
 * to based on the objects created or read from. The Parser class uses
 * dependency injection because both the parserReader and parserWriter
 * class share the same FilePaths which allows both objects to stay updated
 * with the latest path that the user selects.
 */
public class Parser {
    private ParserReader parserReader;
    private ParserWriter parserWriter;
    
    /*
     * Getter for the parserReader attribute.
     * @param: None.
     * @return: The parserReader.
     */
    public ParserReader getParserReader() {
        return this.parserReader;
    }
    
    /*
     * Getter for the parserWriter attribute.
     * @param: None.
     * @return: The parserWriter.
     */
    public ParserWriter getParserWriter() {
        return this.parserWriter;
    }
    
    /*
     * Setter for the parserWriter and parserReader attribute.
     * @param: None.
     * @return: The parserWriter.
     */
    public boolean setParserReaderWriter(Scanner input) {
        FilePaths filePaths = new FilePaths();
        filePaths.collectFilePath(input, FileType.USERS);
        filePaths.collectFilePath(input, FileType.CONFIGS);
        filePaths.collectFilePath(input, FileType.CHESS_TEMPLATE);
        
        // Use dependency injection to share same file paths.
        this.parserReader = new ParserReader(filePaths);
        this.parserWriter = new ParserWriter(filePaths);
        
        return filePaths.getFilePath(FileType.USERS) != null && filePaths.getFilePath(FileType.CONFIGS) != null &&
                filePaths.getFilePath(FileType.CHESS_TEMPLATE) != null;
    }
    
    /*
     * Method that determines whether the user is a new or existing
     * user and builds the player based on the response.
     * @param: The scanner for user input.
     * @return: False if the user decided to quit the program, true otherwise.
     */
    public boolean startPlayerCreation(Scanner input) {
        
        // User signaled that they are an existing user.
        if (UserCreator.determineUserExistence(input)){

            // If the user was never found, leave program.
             return parserReader.findExistingUser(input, parserWriter);     
        }

        // User signaled that they are a new user.
        return parserReader.createPlayerFromXML(input);
    }
    
    /*
     * Method that returns the player created from the parserReader.
     * @param: None.
     * Output: The player created.
     */
    public ChessPlayer retrievePlayerCreation() {
        return parserReader.getPlayer();
    }
    
    /*
     * Method that returns the player created from the parserReader.
     * @param: None.
     * @return: false if the user decided to quit the game.
     */
    public boolean foundUserExists() {
        return parserReader.getFoundExistingUser();
    }
    
    /*
     * Method that adds a user id for existing users and stores players.
     * @param: None.
     * @return: false if the user decided to quit the game.
     */
    public boolean retrieveUserId(Scanner input) throws IOException {
        if (this.parserReader.getFoundExistingUser()) {
            this.parserReader.getPlayer().getIdQueue().add(this.parserWriter.createId());
            return true;
        }
        return this.parserWriter.storePlayer(this.parserReader.getPlayer(), input);
    }
    
    /*
     * Method that signals the creation a new game to write to xml.
     * @param: a scanner for input, parserWriter, and the game controller.
     * @return: false to if the user decided to quit the game, true otherwise.
     */
    public boolean signalGameCreation(Controller controller, Scanner input) 
            throws IOException, NullPointerException {
        
        if (controller.getIsNewGame()) {
            System.out.println("Creating a new game\n");
        }
    
        // Model game from xml.
        return this.parserReader.readConfigurations(controller, input) && 
                this.parserReader.fileToBoard(controller, input);
    }
    
    /*
     * Method that signals the creation a new game to write to xml.
     * @param: a scanner for input, parserWriter, and the game controller.
     * @return: false to if the user decided to quit the game, true otherwise.
     */
    public boolean saveGame(Controller controller, Scanner input) throws IOException, JDOMException {
        if (controller.getIsNewGame()) {
            
            // Write chess pieces.
            this.parserWriter.writeNewChessPieces(controller, input);
            
            // Write new id to user, Write to configs.
            return this.parserWriter.updateUserGameKey(controller.getPlayer(), input) &&               
                    this.parserWriter.updateConfig(controller, input); 
        }
        
        // Update chess pieces
        this.parserWriter.updateChessPieces(controller, input);
        return true;
    }
}