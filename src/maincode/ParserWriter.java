// Nichole Maldonado
// CS331 - Lab 5, ParserWriter Class

/*
 * The ParserWriter class solely writes changes to the
 * xml file. These changes can include updating the
 * user game key, creating a new game segment, 
 * removing an old game, or updating a current game.
 */

// changelog
// [2/28/20] [Nichole Maldonado] added getters and setters for filePathChess and filePathUsers
// [2/29/20] [Nichole Maldonado] created writeNewGame, updateGame, and removeOldGame
//                               to modfiy the relationship between the xml files and the game.
// [2/29/20] [Nichole Maldonado] refactored redundant code into a method that
//                               creates an chess template without pieces.
// [2/29/20] [Nichole Maldonado] created sendToFile which saves changes to the xml file.
// [3/01/20] [Nichole Maldonado] fixed possible null pointer exceptions in the
//                               writeNewGame, updateGame, removeOldGame functions.
// [3/01/20] [Nichole Maldonado] orderer import statements.
// [3/04/20] [Nichole Maldonado] changed name to ParserWriter.
// [3/04/20] [Nichole Maldonado] fixed catch statements and ensured that if an exception was thrown,
//                               the catch statements would excute correctly through testing.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.
// [4/25/20] [Nichole Maldonado] Changed Game access of fields to GameModel's access of fields.
// [4/26/20] [Nichole Maldonado] Added method to update the order and amount of user game keys.


package utep.cs3331.lab5.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import utep.cs3331.lab5.chess.chesspieces.ChessPiece;
import utep.cs3331.lab5.chess.Controller;
import utep.cs3331.lab5.files.exceptions.ExceptionHandler;
import utep.cs3331.lab5.files.FilePaths;
import utep.cs3331.lab5.files.FileType;
import utep.cs3331.lab5.players.ChessPlayer;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/*
 * The FileWritter class solely writes changes to the
 * xml file. These changes can include updating the
 * user game key, creating a new game segment, 
 * removing an old game, or updating a current game.
 */
public class ParserWriter implements ExceptionHandler {    
    private FilePaths filePaths;
    
    /*
     * Default constructor that assigns filePaths
     * to its field.
     * Input: The filePaths object.
     * Output: None.
     */
    public ParserWriter(FilePaths filePaths){
        this.filePaths = filePaths;
    }
    
    /*
     * Getter for the filePaths attribute.
     * Input: None.
     * Output: The filePaths.
     */
    public FilePaths getFilePaths() {
        return this.filePaths;
    }
    
    /*
     * Setter for the filePaths attribute.
     * Input: The new filePaths to be assigned to the current field.
     * Output: None.
     */
    public void setFilePaths(FilePaths filePaths) {
        this.filePaths = filePaths;
    }
    
    /*
     * Method that stores the user game keys in the chess player's
     * id list.
     * @param: the chess player and the element that represents the
     *         chess player.
     * @return: None.
     */
    private void storeIds(ChessPlayer player, Element user) {
        for (String id : player.getIdQueue()) {
            user.getChild("user_game_key").addContent(new Element("id").setText(id));
        }
    }
    
    /*
     * Method that creates a user element based off the player's attributes.
     * @param: the chess player and the parent element that will have a child
     *         that represents the chess player.
     * @return: None.
     */
    private void addPlayerElements(ChessPlayer player, Element userInfo) {
        Element user = new Element("user");
        userInfo.addContent(user);

        // Creates xml elements and copies information from player.
        user.addContent(new Element("name"));
        user.addContent(new Element("password"));
        user.addContent(new Element("expertise_level"));
        user.addContent(new Element("user_color"));
        user.addContent(new Element("user_game_key"));

        user.getChild("name").setText(player.getName());
        user.getChild("password").setText(player.getPassword());
        user.getChild("expertise_level").setText(player.getExpertiseLevel().formatName());
        user.getChild("user_color").setText(player.getUsesWhitePieces() ? "white" : "black");

        this.storeIds(player, user);
    }
    
    /*
     * Method that writes a player to an xml file.
     * @param: The player whose attributes will be written to the file.
     * @return: false if the user decides to quit the game.
     * An IOException may be thrown. The program should terminate
     * if so since it is unknown whether then user will re-input the file
     * that has already been searched for the player.
     */
    protected boolean storePlayer(ChessPlayer player, Scanner input) throws IOException {
        
        // Get the XML file for users.
        File inputFile = new File(this.filePaths.getFilePath(FileType.USERS));
        
        try (FileInputStream fileStream = new FileInputStream(inputFile)) {
            
            SAXBuilder saxBuilder = new SAXBuilder();
            Document configFile = saxBuilder.build(inputFile);

            Element userInfo = configFile.getRootElement();
            
            // Add player.
            this.addPlayerElements(player, userInfo);
            player.getIdQueue().addLast(this.createId());
            
            this.sendToFile(configFile, inputFile, input);
        }
        
        // Allow users to recall the the method in the event an exception occured.
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return storePlayer(player, input);
            }
            return false;    
        }
        return true;
    }
    
    /*
     * Creates an id based on the current time stamp.
     * @param: None.
     * @return: a unique id.
     */
    public static String createId(){
        return new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
    }
    
    /*
     * Method that creates a game template with board and game elements only.
     * @param: The chess element that will house the new elements
     *        and the controller which will be used to populate the fields.
     * @return: None.
     */
    private void createChessConfigs(Element chess, Controller controller) {
        chess.addContent(new Element("board"));
        chess.addContent(new Element("game"));
        
        // Make the board
        chess.getChild("board").addContent(new Element("rows"));
        chess.getChild("board").addContent(new Element("columns"));
        chess.getChild("board").getChild("rows").setText(Integer.toString(
                controller.getGame().getGameModel().getBoard().getNumRows()));
        chess.getChild("board").getChild("columns").setText(Integer.toString(
                controller.getGame().getGameModel().getBoard().getNumColumns()));
        
        // Make the game.
        chess.getChild("game").addContent(new Element("id"));
        chess.getChild("game").addContent(new Element("max_time"));
        chess.getChild("game").addContent(new Element("chat"));
        chess.getChild("game").addContent(new Element("auto_save"));
        chess.getChild("game").getChild("id").setText(controller.getGame().getGameModel().getId());
        chess.getChild("game").getChild("max_time").setText(Integer.toString(
                controller.getGame().getGameModel().getMaxTime()));
        chess.getChild("game").getChild("chat").setText(
                (controller.getGame().getGameModel().getHasChat()) ? "enable" : "disable");
        chess.getChild("game").getChild("auto_save").setText(
                (controller.getGame().getGameModel().getUseAutoSave()) ? "active" : "inactive");
    }
       
    /*
     * Function that finds a user with the old game key and
     * changes the game key to the new user game key.
     * @param: the new game key, the player, and a list
     *         of user elements.
     * @return: None.
     */
    private boolean findUser(String playerName, String newUserGameKey, List<Element> users) {
        for (Element user : users) {
            try {
                if (user.getChild("name") != null && user.getChild("name").getText().equals(playerName)) {
                    
                    if (user.getChild("user_game_key") == null) {
                        user.addContent(new Element("user_game_key"));
                    }
                    Element newId = new Element("id").setText(newUserGameKey);
                    user.getChild("user_game_key").addContent(newId);
    
                    return true;
                }
            } 
            // In event user game key does not exist, invalid so remove game entry.
            catch (NullPointerException e) {
                Element root = user.getParentElement();
                root.removeContent(user);
            }
        }
        return false;
    }
    
    /*
     * Method that updates the user game keys of the player.
     * @param: The player whose keys will be used to update
     *         the user file and a scanner for user input.
     * @return: false if the user decided to quit the game in the
     *         event of an exception, or true otherwise.
     */
    protected boolean updateUserGameKey(ChessPlayer player, Scanner input) {
        
        // update the XML file for users.
        File inputFile = new File(this.filePaths.getFilePath(FileType.USERS));
        
        try (FileInputStream fileStream = new FileInputStream(inputFile)) {
            
            SAXBuilder saxBuilder = new SAXBuilder();
            Document configFile = saxBuilder.build(inputFile);
            
            Element userInfo = configFile.getRootElement();
            List<Element> users = userInfo.getChildren();
            
            // Add unique user game key to xml file.
            if (!findUser(player.getName(), player.getIdQueue().peekLast(), users)) {
                
                // If user was not found in xml file, then add entire user.
                // Note that this could occur if user changes the address of the user xml file.
                this.addPlayerElements(player, userInfo);
            }
            this.sendToFile(configFile, inputFile, input);
        }
        
        // Retries in the event of an error.
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return this.updateUserGameKey(player, input);
            }
            return false;
        } 
        catch (IOException e) {
            if (ExceptionHandler.handleException("trying to find the user xml file", "Re-enter the file", input)) {
                this.filePaths.collectFilePath(input, FileType.USERS);
                return this.updateUserGameKey(player, input);
            }
            return false;
        }
        return true;
    }
    
    /*
     * Method that updates config file with the new game attributes
     * based on the new game.
     * @param: The controller whose board and game will be used
     * .      and a scanner for user input.
     * @return: false if the user decided to quit the game in the
     *         event of an exception, or true otherwise.
     */
    protected boolean updateConfig(Controller controller, Scanner input) {

        // Read the XML file
        File inputFile = new File(this.filePaths.getFilePath(FileType.CONFIGS));
        
        try (FileInputStream fileStream = new FileInputStream(inputFile)) {

            SAXBuilder saxBuilder = new SAXBuilder();
            Document configFile = saxBuilder.build(inputFile);
            
            Element chessConfigs = configFile.getRootElement();
            Element chess = new Element("Chess");
            chessConfigs.addContent(chess);
            
            
            this.createChessConfigs(chess, controller);
            this.sendToFile(configFile, inputFile, input);
        }
        
        // Retries in the event of an error.
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return this.updateConfig(controller, input);
            }
            return false;
        } 
        catch (IOException e) {
            if (ExceptionHandler.handleException("trying to find the config xml file", "Re-enter the file", input)) {
                this.filePaths.collectFilePath(input, FileType.CONFIGS);
                return this.updateConfig(controller, input);
            }
            return false;
        }
        return true;
    }
    
    /*
     * Method that creates a new xml file with the chess pieces where
     * the file is located in the same directory as the template chess file
     * and has the id appended at the end.
     * @param: The controller whose board and game will be used
     * .      and a scanner for user input.
     * @return: None.
     */
    protected void writeNewChessPieces(Controller controller, Scanner input) {
        
        // Create new chess piece xml file
        File inputFile = new File(this.filePaths.getFilePath(FileType.CHESS_TEMPLATE).substring(0,
                this.filePaths.getFilePath(FileType.CHESS_TEMPLATE).length() - 4) 
                + controller.getGame().getGameModel().getId() + ".xml");
  
        Element chessGame = new Element("ChessGame");
        Document configFile = new Document(chessGame);
        
        Element chess = new Element("Chess");
        chessGame.addContent(chess);
        
        Element pieces = new Element("pieces");
        chess.addContent(pieces);
        
        // Adds pieces
        this.boardToFile(controller.getGame().getGameModel().getBoard().getChessBoard(), pieces);

        this.sendToFile(configFile, inputFile, input);
    }
    
    /*
     * Method that updates the xml chess piece file for a loaded game.
     * @param: The controller whose board and game will be used
     * .      and a scanner for user input.
     * @return: None.
     * NOTE: an IOException of JDOME exception may occur.
     * Originally the user was given the option to re-enter the file path
     * however testing showed that this would lead to duplication in the 
     * file stream. In order to avoid duplication, and thus many bugs,
     * the exceptions are thrown.
     */
    protected void updateChessPieces(Controller controller, Scanner input) throws IOException, JDOMException {
      
        // Get loaded game xml file.
        File inputFile = new File(this.filePaths.getFilePath(FileType.CHESS_TEMPLATE).substring(0,
                this.filePaths.getFilePath(FileType.CHESS_TEMPLATE).length() - 4) + 
                controller.getGame().getGameModel().getId() + ".xml");
        
        FileInputStream fileStream = new FileInputStream(inputFile);
        SAXBuilder saxBuilder = new SAXBuilder();
        Document configFile = saxBuilder.build(inputFile);
        
        Element chessPieces = configFile.getRootElement();
        
        if (chessPieces.getChild("Chess") == null) {
            chessPieces.addContent(new Element("Chess"));  
        }
        
        // Remove old pieces
        if (chessPieces.getChild("Chess").getChild("pieces") != null) {
            chessPieces.getChild("Chess").getChild("pieces").removeContent();
      
        }
        else {
            chessPieces.getChild("Chess").addContent(new Element("pieces"));
        }
        
        // add new pieces
        boardToFile(controller.getGame().getGameModel().getBoard().getChessBoard(), chessPieces.getChild("Chess").getChild("pieces"));

        this.sendToFile(configFile, inputFile, input);
        fileStream.close();
    }
    
    /*
     * Method that updates the xml user file with the game keys that may have changed
     * after an existing player is done player.
     * @param: The chess player whose ids will be re-ordered.
     * @return: None.
     * NOTE: an IOException of JDOME exception may occur.
     * Originally the user was given the option to re-enter the file path
     * however testing showed that this would lead to duplication in the 
     * file stream. In order to avoid duplication, and thus many bugs,
     * the exceptions are thrown.
     */
    public void updateExistingGameKeys(ChessPlayer player, boolean saveGame, boolean isNewGame, Scanner input) throws IOException, JDOMException {
      
        // Get loaded game xml file.
        File inputFile = new File(this.filePaths.getFilePath(FileType.USERS));
        
        FileInputStream fileStream = new FileInputStream(inputFile);
        SAXBuilder saxBuilder = new SAXBuilder();
        Document configFile = saxBuilder.build(inputFile);
        
        Element userInfo = configFile.getRootElement();
        List<Element> users = userInfo.getChildren();

        // Finds user if they exist.
        Element user = ParserReader.retrieveUserByName(player.getName(), users);
        
        // If user does not exist, write to file.
        if (user == null){
            this.addPlayerElements(player, userInfo);
        }
        
        // If user exists, remove old game keys and add the new game keys.
        // Note, all keys do not need to be removed, but will be restructured to
        // fit the users most recently used game keys.
        else {
            if (user.getChild("user_game_key") != null) {
                user.getChild("user_game_key").removeContent();
            }
            else {
                user.addContent(new Element("user_game_key"));
            }
            
            if (!saveGame && isNewGame && player.getIdQueue().size() > 0) {
                player.getIdQueue().removeLast();
            }
   
            this.storeIds(player, user);
        }

        this.sendToFile(configFile, inputFile, input);
        fileStream.close();
    }
    
    /*
     * Method that creates a piece element parsing the
     * color, x and y location, piece type, and whether it can play
     * from a string in the format of "name + xPosition + yPosition".
     * Input: a string in the format of "name + xPosition + yPosition"
     *        and a boolean that denotes the piece color.
     * Output: an Element with the specified attributes.
     */
    private static Element createPiece(String chessPieceName, boolean isWhite, boolean play) {
        Element piece = new Element("piece");
        piece.setAttribute("color", (isWhite) ? "white" : "black");
        int length = chessPieceName.length();
        piece.setAttribute("locationX", chessPieceName.substring(length - 2, length - 1));
        piece.setAttribute("locationY", chessPieceName.substring(length - 1, length));
        piece.setAttribute("play", (play) ? "true" : "false");
        piece.setText(chessPieceName.substring(0, length - 2).toLowerCase());
        return piece;
    }
    
    /*
     * Method that writes the changes made to the
     * document to the actual file.
     * Input: the document and the file to be written to.
     * Output: None.
     */
    public static void sendToFile(Document configFile, File inputFile, Scanner input) {
        try {
            FileWriter fileWriter = new FileWriter(inputFile);
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(configFile, fileWriter);
        }
        catch (IOException e) {
            if (ExceptionHandler.handleException("the file's input/output", "Try again", input)) {
                sendToFile(configFile, inputFile, input);
            }
        }
    }
    
    
    /*
     * Method that writes chess pieces stored in chess board to 
     * pieces, the parent elements.
     * Input: the chessBoard with all the chess pieces and
     *        the parent, pieces.
     * Output: None.
     */
    private void boardToFile(ChessPiece[][] chessBoard, Element pieces) {
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                if (chessBoard[i][j] != null) {
                    Element piece = createPiece(chessBoard[i][j].getName(), chessBoard[i][j].getIsWhite(),
                            chessBoard[i][j].getPlay());
                    pieces.addContent(piece);
                }
            }
        }
    }
}