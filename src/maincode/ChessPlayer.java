// Nichole Maldonado
// CS331 - Lab 4, ChessPlayer Class

/*
 * The ChessPlayer class that represents the current 
 * player. The class contains attributes for 
 * the player such as their name, expertise level, and
 * user color. Getters are provided for the method and not 
 * setters. Once the user is created, their expertise level,
 * user color, and name cannot change.
 */

// changelog
// [2/28/20] [Nichole Maldonado] added getters and setters for 
//                               attributes that include board,
//                               expertise level, and user color.
// [3/01/20] [Nichole Maldonado] Reorganized import statements.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.

package utep.cs3331.lab5.players;

import java.util.Deque;

import utep.cs3331.lab5.players.ExpertiseLevel;


/*
 * The ChessPlayer class that represents the current 
 * player. The class contains attributes for 
 * the player such as their name, expertise level, and
 * user color. Getters are provided for the method and not 
 * setters. Once the user is created, their expertise level,
 * user color, and name cannot change.
 */
public class ChessPlayer {
    private String name;
    private String password;
    private ExpertiseLevel expertiseLevel;
    private boolean usesWhitePieces;
    private Deque<String> idQueue;
    
    /*
     * Default constructor that initializes the name,
     * expertiseLevel, and userColor for the player.
     * @param: The name, expertiseLevel, and userColor for the player.
     * @return: None.
     */
    public ChessPlayer(String name, String password, ExpertiseLevel expertiseLevel, boolean usesWhitePieces, Deque<String> idQueue) {
        this.name = name;
        this.password = password;
        this.expertiseLevel = expertiseLevel;
        this.usesWhitePieces = usesWhitePieces;
        this.idQueue = idQueue;
    }
    
    /*
     * Getter for the player's name.
     * @param: None
     * @return: The player's name.
     */
    public String getName(){
        return this.name;
    }
    
    /*
     * Getter for the player's password.
     * @param: None
     * @return: The player's password.
     */
    public String getPassword(){
        return this.password;
    }
    
    /*
     * Getter for the player's name.
     * @param: None
     * @return: The player's name.
     */
    public Deque<String> getIdQueue(){
        return this.idQueue;
    }
    
    /*
     * Setter for the player's id queue.
     * @param: None
     * @return: The player's queue of ids.
     */
    public void setIdQueue(Deque<String> idQueue){
        this.idQueue = idQueue;
    }
    
    /*
     * Getter for the player's expertise level.
     * @param: None
     * @return: The player's name.
     */
    public ExpertiseLevel getExpertiseLevel(){
        return this.expertiseLevel;
    }
    
    /*
     * Getter for the player's color.
     * @param: None
     * @return: The player's color.
     */
    public boolean getUsesWhitePieces(){
        return this.usesWhitePieces;
    }
}