// Nichole Maldonado
// CS331 - Lab 4, ParserReader Class

/*
 * This class file contains the ParserReader class
 * with the field filePaths. This class is solely
 * used to read from the xml files, find existing user,
 * create a player based off the xml file, and 
 * create a game off the xml file.
 */

// changelog
// [2/28/20] [Nichole Maldonado] added getters and setters for filePathChess and filePathUsers
// [2/29/20] [Nichole Maldonado] created createPlayerFromXML, findExistingUsers, populateBoard,
//                               and fileUpdateController to change or create objects based on
//                               xml files provided.
// [2/29/20] [Nichole Maldonado] removed attributes and instead grouped them into a single
//                               FilePaths object.
// [2/29/20] [Nichole Maldonado] fixed bug that was not checking to make sure that the
//                               board and game contained its neccessary attributes.
// [2/29/20] [Nichole Maldonado] Changed previous exception handling to use method provided
//                               by exception handling interface.
// [3/01/20] [Nichole Maldonado] fixed possible null pointer exceptions in findChessPiecesParent
//                               and populateBoard methods.
// [3/01/20] [Nichole Maldonado] ordered import statements.
// [3/04/20] [Nichole Maldonado] changed name to ParserReader.
// [3/04/20] [Nichole Maldonado] fixed catch statements and ensured that if an exception was thrown,
//                               the catch statements would excute correctly through testing.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.
// [4/25/20] [Nichole Maldonado] Changed Game access of fields to GameModel's access of fields.
// [4/30/20] [Nichole Maldonado] Added streams and lambdas. Also made changes to hashmap to store passwords.

package utep.cs3331.lab5.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import utep.cs3331.lab5.chess.chesspieces.ChessPieceTypes;
import utep.cs3331.lab5.chess.Controller;
import utep.cs3331.lab5.chess.GameBoard;
import utep.cs3331.lab5.files.exceptions.ExceptionHandler;
import utep.cs3331.lab5.files.FilePaths;
import utep.cs3331.lab5.files.FileType;
import utep.cs3331.lab5.files.ParserWriter;
import utep.cs3331.lab5.files.UserCreator;
import utep.cs3331.lab5.players.ChessPlayer;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;

/*
 * This class file contains the ParserReader class
 * with the field filePaths. This class is solely
 * used to read from the xml files, find existing user,
 * create a player based off the xml file, and 
 * create a game off the xml file.
 */
public class ParserReader implements ExceptionHandler {    
    private FilePaths filePaths;
    private ChessPlayer player;
    private boolean foundExistingUser;
    
    /*
     * Default constructor that creates a new FilePath object.
     * @param: None.
     * @return: None.
     */
    public ParserReader(FilePaths filePaths) {
        this.filePaths = filePaths;
    }
    
    /*
     * Getter function that returns the player
     * attribute.
     * Input: None.
     * Output: the chess player.
     */
    public ChessPlayer getPlayer() {
        return player;
    }
    
    /*
     * Getter method for the field filePath
     * @param: None.
     * @return: a string of a filePath.
     */
    public FilePaths getFilePaths() {
        return this.filePaths;
    }
    
    /*
     * Getter method for the field foundExistingUser.
     * @param: None.
     * @return: a boolean of a whether the player is new or existing.
     */
    public boolean getFoundExistingUser() {
        return this.foundExistingUser;
    }
    
    /*
     * Setter method for the field foundExistingUser.
     * @param: the boolean value to be assigned to the field foundExistingUser.
     * @return: None.
     */
    public void setFoundExistingUser(boolean foundExistingUser) {
        this.foundExistingUser = foundExistingUser;
    }
    
    /*
     * Setter method for the field player.
     * @param: the ChessPlayer object to be assigned to the field player.
     * @return: None.
     */
    public void setPlayer(ChessPlayer player) {
        this.player = player;
    }
    
    // FilePaths can only be set once when the constructor is called.
    
    /*
     * Method that returns the user with the corresponding
     * userName.
     * @param: the user's name to find and a list of users.
     * @return: The user with the userName if found, or null otherwise.
     */
    protected static Element retrieveUserByName(String userName, List<Element> users) {
        Optional<Element> user = users.stream().filter(
            (Element userElement)->userElement.getChild("name") != null && userElement.getChild("name").getValue().equals(userName)).findFirst();
        
        return (user.isPresent()) ? user.get() : null;
    }
    
    
    /*
     * Method that creates a ChessPlayer based on the xml file.
     * @param: The config file, input file, and scanner are included,
     *        incase the user does not have a game id in which case the 
     *        file writer will remove the entry. The userName and element
     *        are used to create the player.
     * @return: true if the player was successfully created, or false otherwise.
     */
    private boolean createPlayerFromLoadedFile(Document configFile, File inputFile, 
            Scanner input, Element user, String userName, ParserWriter parserWriter, 
            FileInputStream fileStream) throws IOException {
        
        if (user.getChild("user_game_key") == null) {
            user.addContent(new Element("user_game_key"));
        }
        
        this.player = UserCreator.createExistingPlayer(userName, input, user, 
                user.getChild("user_game_key").getChildren("id"), configFile, inputFile, parserWriter);
        this.foundExistingUser = true;
        return true;
    }
    
    /* Method that finds an existing user based on the 
     * the user name collected.
     * @param: Scanner to retrieve user input.
     * @return: True if a user name was finally found.
     *         false is returned otherwise.
     */
    protected boolean findExistingUser(Scanner input, ParserWriter parserWriter) {
        String userName = UserCreator.getUserName(input);
        
        // Read the user XML file for the users.
        File inputFile = new File(this.filePaths.getFilePath(FileType.USERS));
        
        try (FileInputStream fileStream = new FileInputStream(inputFile)) {

            SAXBuilder saxBuilder = new SAXBuilder();
            Document configFile = saxBuilder.build(fileStream);
            
            // Get the root element
            Element root = configFile.getRootElement();
            List<Element> users = root.getChildren();

            // Finds user if they exist.
            Element user = this.retrieveUserByName(userName, users);
            
            // If the user was successfully found, create and store
            // the user and game key.
            if (user != null) {
                return this.createPlayerFromLoadedFile(configFile, inputFile, input, 
                        user, userName, parserWriter, fileStream);
            }
            
        } 
        
        // Allows user to redo method if an exception occured.
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return findExistingUser(input, parserWriter);
            }
            return false;
        } 
        catch (IOException e) {
            if (ExceptionHandler.handleException("trying to find the user xml file", "Enter new file", input)) {
                
                // Update user file path and recall the method.
                this.filePaths.collectFilePath(input, FileType.USERS);
                return findExistingUser(input, parserWriter);
            }
            return false;
        }
        
        // Re-enter user name in worst case.
        System.out.println("The specified username could not be found");
        if (UserCreator.reEnterUserName(input)){
            return findExistingUser(input, parserWriter);
        }
        return false;
    }

    /*
     * Method that populates a hash table with users and 
     * returns true if the userName was never found.
     * @param: the user's name to find, a list of users,
     *        and a potential hash table to store the users.
     * @return: The user with the userName if found, or null otherwise.
     */
    public boolean userDoesNotExist(String userName, List<Element> users, Map <String, String> userStorage) {
        boolean userNotFound = true;
   
        for (Element user : users) {
            Element userNameElement = user.getChild("name");
            if (userNameElement != null) {

                // Hashes users in case a user is found. In which case, the user names
                // will need to be compared with the new user names that the user selects.
                userStorage.put(userNameElement.getValue(), ((user.getChild("password") != null) ? user.getChild("password").getValue() : null));    
                
                if (userNameElement.getValue().equals(userName)) {
                    userNotFound = false;   
                }  
            }                   
        }
        return userNotFound;
    }
    
    /*
     * Method that reads an xml file to ensure that a user name
     * does not already exist before creating the player.
     * @param: The scanner to retrieve the user input.
     * @return: None.
     */
    public boolean createPlayerFromXML(Scanner input) {
        String userName = UserCreator.getUserName(input);
        Map<String, String> userHash = new HashMap<String, String>();
        
        // Read the XML file
        File inputFile = new File(this.filePaths.getFilePath(FileType.USERS));
        
        try (FileInputStream fileStream = new FileInputStream(inputFile)) {
    
            SAXBuilder saxBuilder = new SAXBuilder();
            Document configFile = saxBuilder.build(fileStream);

            // Get the root userInfo element
            Element userInfo = configFile.getRootElement();
            List<Element> userElements= userInfo.getChildren();
            boolean userNotFound = userDoesNotExist(userName, userElements, userHash);
            
            // If the user was found, then the discrepcies among the user needs
            // to be fixed.
            if (!userNotFound) {
                userName = UserCreator.resolveMatchingUserNames(userHash, input);
            }
            
            // If userName is not null, then a new user is ready to be created.
            if (userName != null) {
                this.player = UserCreator.createPlayer(userName, userHash.get(userName), input);
            }
            
            // If userName is null, then user wants to quit.
            if (userName == null || this.player == null) {
                return false;
            }
        } 
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return createPlayerFromXML(input);
            }
            return false;
        } 
        catch (IOException e) {
            if (ExceptionHandler.handleException("trying to find the user xml file", "Enter new file", input)) {
                this.filePaths.collectFilePath(input, FileType.USERS);
                return createPlayerFromXML(input);
            }
            return false;
        }
        return true;
    }
    
    /*
     * Method that reads an xml file and converts the pieces into piece
     * objects and adds them to the board.
     * @param: The game board and a list of piece elements.
     * @return: true if the board was correctly populated.
     * If NullPointerException is thrown, it symbolizes that the file
     * was missing an element tag.
     */
    private boolean populateBoard(GameBoard board, List<Element> chessPieces) 
            throws NullPointerException {
        
        for (Element piece : chessPieces) {
            boolean isWhite;
            char xPosition;
            int yPosition;
            boolean play;
            ChessPieceTypes pieceType;

            // The file is expected to have the type in the form of
            // pieceNameXY where X should correspond to the x - position and
            // Y should correspond to the y - position.
            String pieceTypeStr = piece.getText().toUpperCase();

            try {

                // Get piece type.
                pieceType = ChessPieceTypes.valueOf(pieceTypeStr);

                // Get the xPosition.
                if (piece.getAttribute("locationX").getValue().length() == 1) {
                    xPosition = piece.getAttribute("locationX").getValue().toUpperCase().charAt(0);
                    
                    if (xPosition - board.MIN_X_POSITION < 0 || xPosition - board.MIN_X_POSITION >= board.getNumRows()) {
                        return false;
                    }
                    
                    // Get the yPosition.
                    yPosition = Integer.parseInt(piece.getAttribute("locationY").getValue());

                    if (yPosition < 1 || yPosition > board.getNumColumns()) {
                        return false;
                    }

                    //Get isWhite.
                    if (piece.getAttribute("color").getValue().toLowerCase().equals("white")) {
                        isWhite = true;   
                    }
                    else if (piece.getAttribute("color").getValue().toLowerCase().equals("black")) {
                        isWhite = false;
                    }
                    else {
                        return false;
                    }

                    //Get play.
                    if (piece.getAttribute("play").getValue().toLowerCase().equals("true")) {
                        play = true;   
                    }
                    else if (piece.getAttribute("play").getValue().toLowerCase().equals("false")) {
                        play = false;
                    }
                    else {
                        return false;
                    }

                    // Pawn piece can only move forward. Thus, a white pawn can not
                    // exist at row 1 and a black pawn cannot exist at row 8.
                    if (pieceType == ChessPieceTypes.PAWN && isWhite && yPosition == 1) {
                        return false;
                    }
                    if(pieceType == ChessPieceTypes.PAWN && !isWhite && yPosition == board.getNumRows()) {
                        return false;
                    }

                    // Create the new, specific ChessPiece.
                    board.addChessPiece(pieceType, xPosition, yPosition, isWhite, play);
                } 
                else {
                    return false;
                }
            }

            // Catches exception but does not print error. If an exception is caught,
            // then it signals that the piece attribute should remain untouched.
            catch(NumberFormatException e){
                return false;
            }
            catch(IllegalArgumentException e){
                return false;
            }
        }
        return true;
    }
    
    /*
     * Helper method that updates the controller's game useAutoSave attribute based 
     * on the game element.
     * @param: The controller, whose game useAutoSave attribute will be updated,
     *        and the game element.
     * @return: None.
     */
    private void updateAutoSave(Element game, Controller controller) {

        // Check and update autosave.
        try {
            String autoSaveLabel = game.getChild("auto_save").getText().toLowerCase();
            
            // Auto-save can only be active or inactive.
            boolean useAutoSave = false;
            if (autoSaveLabel.equals("active")) {
                useAutoSave = true;
            }
            else if (!autoSaveLabel.equals("inactive")) {
                System.out.println("Auto save can only be active or inactive. Auto-save defaults to inactive");
                useAutoSave = false;
            }
            controller.getGame().getGameModel().setUseAutoSave(useAutoSave); 
        }
        catch (NullPointerException e) {
            System.out.println("A problem occured while loading your auto-save setting. Auto-save defaults to inactive.");
            controller.getGame().getGameModel().setUseAutoSave(false);
        }
    }
    
    /*
     * Helper method that updates the controller's game maxTime attribute based 
     * on the game element.
     * @param: The controller, whose game maxTime attribute will be updated,
     *        and the game element.
     * @return: true if the element did not contain any false values
     *         and was able to update the controller successfully, 
     *         or false is otherwise returned.
     */
    private void updateMaxTime(Element game, Controller controller) {
        
        // Check and update max time.
        try {
            int maxTime = Integer.parseInt(game.getChild("max_time").getText());
            
            // maxTime can only be in the range [1, 100].
            if (maxTime < 1 || maxTime > 100) {
                System.out.println("Max time can only be in the range from [1, 100], so max time will default to 30.");
                maxTime = 30;
            }
            controller.getGame().getGameModel().setMaxTime(maxTime); 
        }
        catch (NullPointerException e) {
            System.out.println("A problem occured while loading your max-time setting. Max-time defaults to 30 minutes.");
            controller.getGame().getGameModel().setMaxTime(30);
        }
    }
    
    /*
     * Helper method that updates the controller's game chat attribute based 
     * on the game element.
     * @param: The controller, whose game chat attribute will be updated,
     *        and the game element.
     * @return: true if the element did not contain any false values
     *         and was able to update the controller successfully, 
     *         or false is otherwise returned.
     */
    private void updateChat(Element game, Controller controller) {
        
        // Check and update chat.
        try {
            String hasChatLabel = game.getChild("chat").getText().toLowerCase();
            boolean hasChat = false;
            if (hasChatLabel.equals("enable")) {
                hasChat = true;
            }
            else if (!hasChatLabel.equals("disable")) {
                System.out.println("Chat can only be disable or enable, so has chat will be disabled.");
            }
            controller.getGame().getGameModel().setHasChat(hasChat); 
        }
        catch (NullPointerException e) {
            System.out.println("A problem occured while loading your chat setting. Chats defaults to disabled.");
            controller.getGame().getGameModel().setHasChat(false);
        }
    } 
    
    /*
     * Method that creates the game board based on an id. 
     * @param: The controller which contains the id and board,
     *        a scanner to retrieve input, and the file writer
     *        to make changes to the xml in the event that improper xml structure
     *        or exceptions occur.
     * @return: false if the user decided to quit the game due to an exception, or
     *         true otherwise.
     * NOTE: Testing revealed that if user was able to re-enter the file names, the
     * buffer could still contain information causing duplication of pieces on the board.
     * Thus the exceptions are thrown.
     */
    protected boolean fileToBoard(Controller controller, Scanner input) 
            throws IOException, NullPointerException {
        
        // Read the XML file based on whether the game is new or loaded.
        File inputFile = (controller.getIsNewGame()) ? 
                new File(this.filePaths.getFilePath(FileType.CHESS_TEMPLATE)) : 
                new File(this.filePaths.getFilePath(FileType.CHESS_TEMPLATE).substring(
                        0, this.filePaths.getFilePath(FileType.CHESS_TEMPLATE).length() - 4) + 
                        controller.getGame().getGameModel().getId() + ".xml");

        try (FileInputStream fileStream = new FileInputStream(inputFile);) {

            SAXBuilder saxBuilder = new SAXBuilder();
            Document configFile = saxBuilder.build(fileStream);
            Element chessGame = configFile.getRootElement();
            
            boolean populatedBoard = populateBoard(controller.getGame().getGameModel().getBoard(), 
                    chessGame.getChild("Chess").getChild("pieces").getChildren("piece"));
            
            // If the board contained improper syntax,the board will be erased.
            if (!populatedBoard) {
                System.out.println("The loaded game contained incorrect syntax.");
                System.out.println("The default chess template will be used instead.");
                
                controller.setIsNewGame(true);
                
                // If new game template was used, ask for a new one and restart.
                if (controller.getIsNewGame()) {
                    System.out.print("Since the default chess template was used and contained invalid syntax");
                    System.out.println("you will be prompted to re-enter the default chess template.\n");
                    this.filePaths.collectFilePath(input, FileType.CHESS_TEMPLATE);
                    
                    return fileToBoard(controller, input);
                }
                
                // Otherwise, recall method with default file.
                return fileToBoard(controller, input);
            }
        } 
        
        // Recalls the method if an exception occurs.
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return fileToBoard(controller, input);
            }
            return false;
        } 
        return true;
    }
    
    /*
     * Method that finds a config element with the given id.
     * @param: The id of the game and a list of config elements.
     * @return: The config element with the id if found.
     * NOTE: Cannot use stream since we can break out of the for each so
     *       stream would not be the best implementation.
     */
    private Element findConfigs(String id, List<Element> configs) {        
        for (Element configElement : configs) {
            Element game = configElement.getChild("game");
            if (game != null && game.getChild("id") != null && game.getChild("id").getText().equals(id)) {
                return configElement; 
            }                   
        }
        return null;
    }
    
    /*
     * Method that sets the game properties based on the file. 
     * @param: The controller which contains the id and board,
     *        a scanner to retrieve input, and the file writer
     *        to make changes to the xml in the event that improper xml structure
     *        or exceptions occur.
     * @return: false if the user decided to quit the game due to an exception, or
     *         true otherwise.
     * NOTE: Exceptions are thrown to allow the user to restart the porgram if wanted.
     * this is crucial since a game cannot be created without the necessary configs.
     */
    public boolean readConfigurations(Controller controller, Scanner input)  
            throws IOException, NullPointerException {
        
        // Read the XML file based on whether the game is new or loaded.
        File inputFile = (controller.getIsNewGame()) ? 
                new File(this.filePaths.getFilePath(FileType.CHESS_TEMPLATE)) : 
                new File(this.filePaths.getFilePath(FileType.CONFIGS));

        try (FileInputStream fileStream = new FileInputStream(inputFile);) {

            //Create a document builder
            SAXBuilder saxBuilder = new SAXBuilder();

            Document configFile = saxBuilder.build(fileStream);
            Element chessConfigs = configFile.getRootElement();
            Element configs;
            
            if (controller.getIsNewGame()) {
                configs = chessConfigs.getChild("Chess");
            }
            else {
                configs = this.findConfigs(controller.getGame().getGameModel().getId(), chessConfigs.getChildren("Chess"));
            }
            
            // Sets the board size.
            controller.getGame().getGameModel().createBoard(Integer.parseInt(configs.getChild("board").getChild("rows").getText()), 
                    Integer.parseInt(configs.getChild("board").getChild("columns").getText()));
            
            // Sets game values.
            if (!controller.getIsNewGame() || controller.getGame().getGameView().useDefaultConfigurations(input)) {
                this.updateAutoSave(configs.getChild("game"), controller);
                this.updateChat(configs.getChild("game"), controller);
                this.updateMaxTime(configs.getChild("game"), controller);
            }
            else {
                controller.getGame().setTimeChatSave(input);
            }
        } 
        
        // Recalls the method if an exception occurs.
        catch (JDOMException e) {
            if (ExceptionHandler.handleException("attempting to build the DOM tree", "Try again", input)) {
                return fileToBoard(controller, input);
            }
            return false;
        } 
        return true;
    }
}