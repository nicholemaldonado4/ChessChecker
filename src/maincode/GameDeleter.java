// Nichole Maldonado
// CS331 - Lab 5, GameDeleter Class

/*
 * GameDeleter class removes games based on the user's selection,
 * updates the queue, removes the game file, and removes the game
 * from the config game files.
 */

// changelog
// [4/26/20] [Nichole Maldonado] added class to remove games when GameSelector saw
//                               that the games would exceed the max capacity.
// [4/26/20] [Nichole Maldonado] fixed bug in deleteGameConfigs that was not properly
//                               deleting the game configs.

package utep.cs3331.lab5.chess;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import utep.cs3331.lab5.files.FileType;
import utep.cs3331.lab5.files.ParserWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

/*
 * GameDeleter class removes games based on the user's selection,
 * updates the queue, removes the game file, and removes the game
 * from the config game files.
 */
public class GameDeleter {
    private Set<String> gamesToDelete;

    /*
     * Default Constructor that creates the hashset.
     * @param: None.
     * @return: None.
     */
    public GameDeleter() {
        gamesToDelete = new HashSet<String>();
    }
    
    /*
     * Getter for gamesToDelete (used for testing purposes).
     * @param: None.
     * @return: None.
     */
    public Set<String> getGamesToDelete() {
        return this.gamesToDelete;
    }
    
    /*
     * Gets games to delete from user and deltes from config and game files.
     * @param: None.
     * @return: None.
     */
    protected void deleteGames(GameSelector gameSelector, Scanner input) throws IOException, JDOMException {
        
        // Get games to delete
        this.deleteUserSelectedGames(gameSelector, input);

        // Delete games from configs and the .xml file.
        this.deleteGameFiles(gameSelector.getFilePaths().getFilePath(FileType.CHESS_TEMPLATE));
        this.deleteGameConfigs(gameSelector.getFilePaths().getFilePath(FileType.CONFIGS), input);
    }

    /*
     * Method that determines which games the user wants to delete and deletes them.
     * @param: Scanner for input and the gameSelector.
     * @return: None.
     */
    public void deleteUserSelectedGames(GameSelector gameSelector, Scanner input)  {
        
        boolean deletedGame = false;
        Iterator<String> iter = gameSelector.getIdQueue().iterator();
        System.out.println("Select");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("To delete a game file.\n");
        while (iter.hasNext()) {
            String id = iter.next();
            try {
            
                String idString = gameSelector.timestampToDay(id);
                System.out.println("Would you like to delete: " + idString);
                System.out.print("Select 1 (for yes) or 2 (for no): ");
                int selection = 0;

                while (selection != 1 && selection != 2) {
                    try {
                        selection = input.nextInt();
                        input.nextLine();
                    }
                    catch (InputMismatchException e) {
                        input.nextLine();
                        selection = 0;
                    }

                    if (selection != 1 && selection != 2) {
                        System.out.println("\nInvalid input. Please try");
                        System.out.println();
                        System.out.println("Would you like to delete: " + idString);
                        System.out.print("Select 1 (for yes) or 2 (for no): ");
                    }
                }
                
                // Remove selected games.
                if (selection == 1) {
                    deletedGame = true;
                    this.gamesToDelete.add(id);
                    iter.remove();
                }
                System.out.println();
            }
            //Remove selected games if a problem with the id occured.
            catch (ParseException e) {
                System.out.println("One of your game ids was corrupted. The game id will be removed.");
                iter.remove();
            }
        }
        
        // Remove a game if user never selected any games.
        if (!deletedGame){
            System.out.println("You did not select any games to delete. Your oldest game will be deleted");
            gamesToDelete.add(gameSelector.getIdQueue().removeFirst());
        }
    }
    
    /*
     * Method that deletes game files that have the
     * gameid appended to the end of "chessTemplate"
     * @param: The file path of the chess template.
     * @return: None.
     */
    private void deleteGameFiles(String filePathChessTemplate) {
        for (String id: this.gamesToDelete) {
            File gameFile = new File(filePathChessTemplate.substring(0, filePathChessTemplate.length() - 4) + id + ".xml");
            gameFile.delete();
        }
    }
    
    /*
     * Method that deletes games from the game config file if they
     * are in the gamesToDelete hashset.
     * @param: The file path of the config file.
     * @return: None. 
     * NOTE: Throws IOException. Crucial to throw back to controller, the game keys were there
     * but and the file accessed but now it can no longer be accessed.
     */
    private void deleteGameConfigs(String gameConfigFilePath, Scanner input) throws IOException, JDOMException {
        
        // Get loaded game xml file.
        File inputFile = new File(gameConfigFilePath);
        
        FileInputStream fileStream = new FileInputStream(inputFile);
        SAXBuilder saxBuilder = new SAXBuilder();
        Document configFile = saxBuilder.build(inputFile);
        
        Element chessConfigs = configFile.getRootElement();
        List<Element> gameList = chessConfigs.getChildren("Chess");

        if (gameList != null) {
            int numToRemove = this.gamesToDelete.size();
            List<Element> removeElements = new ArrayList<Element>();
            
            // Not necessary to use the iterator, but
            // using for this lab assignment.
            for (Iterator<Element> iterator = gameList.iterator(); iterator.hasNext() && removeElements.size() < numToRemove;) {
                Element game = iterator.next();
                
                // Add to remove if in the set.
                if (game.getChild("game") != null && game.getChild("game").getChild("id") != null && this.gamesToDelete.contains(game.getChild("game").getChild("id").getText())) {
                    removeElements.add(game);
                }
            }
            
            // Remove uneeded games.
            for (Element game: removeElements) {
                chessConfigs.removeContent(game);
            }
            
        }
        
        ParserWriter.sendToFile(configFile, inputFile, input);
        fileStream.close();
    }
}