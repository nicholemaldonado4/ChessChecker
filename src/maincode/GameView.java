// Nichole Maldonado
// CS331 - Lab 5, Game Class

/*
 * Game class that represents the current
 * attributes of the game including whether
 * the game has chat, an id, and uses auto save.
 * Game class that contains setters and getters
 * to modify its fields and thus the state of the game.
 */

// changelog
// changelog
// [4/25/20] [Nichole Maldonado] moved UI for retrieving game settings from Game since
//                               implementing MVC pattern.
// [4/25/20] [Nichole Maldonado] moved UI for print game board from GameBoard.
// [4/25/20] [Nichole Maldonado] created methods for print statements for game recovery.

package utep.cs3331.lab5.chess;

import java.util.InputMismatchException;
import java.util.Scanner;

import utep.cs3331.lab5.chess.chesspieces.ChessPiece;
import utep.cs3331.lab5.chess.exceptions.ActionRecovery;
import utep.cs3331.lab5.chess.exceptions.PieceInPlaceException;
import utep.cs3331.lab5.chess.MinimumBoardDimensions;

public class GameView implements MinimumBoardDimensions {
    
    /*
     * Method that returns a valid xPosition from 'A' to 'H'
     * based on the user's input. 0 is returned otherwise.
     * @param: the scanner to retrieve the input from the user.
     * @return: a character denoting the xPosition.
     */ 
    public char selectX(Scanner input, int numColumns) {
        boolean foundX = false;
        char xPosition = 0;

        while (!foundX) {
            System.out.print("Enter the x - position (A - H): ");
            String xPositionStr = input.nextLine().replaceAll("\\s+", "");

            if (xPositionStr.length() != 1) {
                System.out.print("Invalid x-position. The x-position can only be one character.");
                System.out.println("Please try again.");
            }
            else {
                // Converts position to character.
                xPosition = xPositionStr.toUpperCase().charAt(0);

                // xPosition must be in the chess board's range.
                if (xPosition >= (MIN_X_POSITION + numColumns) || xPosition < MIN_X_POSITION) {
                    System.out.print("Invalid x-position. The x-position must be in the range A to H.");
                    System.out.println("Please try again.");
                }
                else {
                    foundX = true;
                }
            }
        }

        return xPosition;
    }
    
    
    /*
     * Method that returns a valid yPosition from 1 to 8.
     * based on the user's input. -1 is returned otherwise.
     * @param: the scanner to retrieve the input from the user.
     * @return: an integer denoting the yPosition.
     */
    public int retrieveYPosition(Scanner input, int numRows) {
        boolean foundY = false;
        int yPosition = 0;
        
        while (!foundY) {
            System.out.print("Enter the y - position (1 - 8): ");
            
            try {
                yPosition = input.nextInt();
                input.nextLine();
                System.out.println();

                // yPosition must be in the chess board's range.
                if (yPosition > numRows || yPosition < MIN_Y_POSITION) {

                    System.out.print("Invalid y-position. The y-position must in the range ");
                    System.out.println("of 1 to 8. Please try again.");
                }
                else {
                    foundY = true;
                }
            } catch(InputMismatchException e) {
                input.nextLine();
                System.out.println("Invalid input. The y-position can only be an integer. Please try again");
            }
        }
        
        return yPosition;
    }
    
    /*
     * Gets the current chess piece x position for user and tells them accordingly.
     * @param: a scanner for input, the number of columns in the game board, and
     * whether the x position will be for a new move or to select the current position.
     * @return: the x position selected.
     */
    protected char retrieveXPosition(Scanner input, int numCols, boolean getCurr){
        if (getCurr) {
            System.out.println("Select the current chess piece you want to move based on its coordinates");
        }
        else {
            System.out.println("Enter where you would like to move the chess piece based on the coordinates of the board game.");
        }
        return selectX(input, numCols);
    }
    
    /*
     * Method that draws the game board.
     * @param: None.
     * @return: None.
     */
    protected void drawGame(ChessPiece[][] chessBoard, int numColumns) {
        String col = "|";
        String row = "-----------------------------------------------------";
        System.out.println("Current Chess Game\n");
        
        System.out.println(row);
        for (int i = chessBoard.length - 1; i >= 0; i--) {
            System.out.print(" " + (i + 1) + " |");
            for (int j = 0; j < chessBoard[i].length; j++) {
                if (chessBoard[i][j] != null) {
                    System.out.print("| " + chessBoard[i][j].pieceInitial() + " ");
                }
                else {
                   System.out.print("|     "); 
                }
            }
            System.out.println("|\n" + row);
        }
        System.out.print(row + "\n   |");
        for (char i = MIN_X_POSITION; i < MIN_X_POSITION + numColumns; i++) {
            System.out.print("|  " + i + "  ");
        }
        System.out.println("|\n" + row);
    }
    
    /*
     * Method that asks user if they want to continue playing the game.
     * @param: a scanner for user input.
     * @return: true to quit, false to continue playing.
     */
    protected boolean quitGame(Scanner input) {
        int selection = -1;
        while (selection != 1 && selection != 2) {
            System.out.println("Would you like to continue playing");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Select 1 or 2: ");
            
            try {
                selection = input.nextInt();
                input.nextLine();
                System.out.println();
                
                if (selection != 1 && selection != 2) {
                    System.out.println("Only values 1 or 2 are allowed. Please try again.");
                }
            }
            catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Invalid input. Please try again");
            }
        }
        
        return (selection == 1) ? false : true;
    }
    
        /*
     * Method that retrieves save capability.
     * @param: the scanner for input.
     * @return: returns true or false for whether auto save will be used.
     */  
    public boolean retrieveSaveMethod(Scanner input) {
        System.out.println("Would you like your game to ");
        System.out.println("1. Auto-save after the game");
        System.out.println("2. Be prompted to save at the end of the game");
        System.out.print("Select 1 or 2: ");
        int selectionNum = 0;
        try {
            selectionNum = input.nextInt();
            input.nextLine();
            
            if (selectionNum != 1 && selectionNum != 2) {
                System.out.println("\nInvalid menu number. Autosave disabled.");
                selectionNum = 2;
            }
        } 
        catch (InputMismatchException e) {
            input.nextLine();
            System.out.println("\nInvalid input. Auto-save will be disabled");
            selectionNum = 2;
        }
        
        System.out.println();
        
        return (selectionNum == 1) ? true : false;
    }
    
    /*
     * Method that retrieves max time limit selection from the user and assigns
     * result ot maxTime.
     * @param: the scanner for input.
     * @return: returns the game time.
     */  
    public int retrieveTimeLimit(Scanner input) {
        System.out.println("Enter the time limit for your game in minutes from [1, 100]");
        System.out.println("Example: Game time: 20\n");
        System.out.print("Game time: ");
        int gameTime = 0;
        try {
            gameTime = input.nextInt();
            input.nextLine();
            
            if (gameTime < 1 || gameTime > 100) {
                System.out.println("\nInvalid input. The game time will be set to 30 minutes");
                gameTime = 30;
            }
        } 
        catch (InputMismatchException e) {
            input.nextLine();
            System.out.println("\nInvalid input. The game time will be set to 30 minutes");
            gameTime = 30;
        }
        System.out.println();
        return gameTime;
    }
    
    /*
     * Method that retrieves chat capability from user and assigns
     * result ot hasChat.
     * @param: the scanner for input.
     * @return: returns ture if the game will have chat or false otherwise.
     */  
    public boolean retrieveHasChat(Scanner input) {
        System.out.println("Do you want to allow chat?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Select 1 or 2: ");
        int selection = 0;
        try {
            selection = input.nextInt();
            input.nextLine();
            
            if (selection < 1 && selection > 2) {
                System.out.println("\nInvalid input. The chat will be disabled.");
                selection = 2;
            }
        } 
        catch (InputMismatchException e) {
            input.nextLine();
            System.out.println("\nInvalid input. The chat will be disabled.");
            selection = 2;
        }
        System.out.println();
        return (selection == 1) ? true : false;
    }
    
    /*
     * Method that prints the message that occurred due to an exception or error that 
     * occured and the action to be taken.
     * @param: The ActionRecovery.
     * @return: None.
     */
    protected void printErrorResolution(ActionRecovery recovery) {
        System.out.println("------------------------------------------------------\n");
        System.out.printf("A chess %s at the entered position.\n", recovery.formatName());
        System.out.println("No moves will be performed.");
        System.out.println("------------------------------------------------------\n");
    }
    
    /*
     * Method that prints whether the piece can be moved or not.
     * @param: The pieceName, new x and y position, and whether or not the piece can move
     *         to the new position.
     * @return: None.
     */
    protected void printPiecePosition(String moveNotifier, String pieceName, char newXPosition, int newYPosition) {
        System.out.printf("\n%s can%s move to %c, %d\n\n", pieceName, moveNotifier, newXPosition, newYPosition);
        System.out.println("------------------------------------------------------\n");
    }
    
        
    /*
     * Method that returns true if the user wants to use the game's default configurations
     * @param: the scanner for input.
     * @return: None.
     */  
    public boolean useDefaultConfigurations(Scanner input) {
        System.out.print("Do you want to use the game's default configurations");
        System.out.println(" or choose the configurations?");
        System.out.println("1. Use default configurations");
        System.out.println("2. Choose your own");
        System.out.print("Select 1 or 2: ");
        int selection = 0;
        try {
            selection = input.nextInt();
            input.nextLine();
            
            if (selection < 1 && selection > 2) {
                System.out.println("\nInvalid input. Default configurations will be chosen.");
                selection = 1;
            }
        } 
        catch (InputMismatchException e) {
            input.nextLine();
            System.out.println("\nInvalid input. Default configurations will be chosen.");
            selection = 1;
        }
        System.out.println();
        return (selection == 1) ? true : false;
    }
}