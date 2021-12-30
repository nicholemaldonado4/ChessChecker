// Nichole Maldonado
// CS331 - Lab 5, ExceptionHandler Interface

/*
 * Interface that provides a static method to
 * handle exceptions throwns. The method
 * prints the error and provides the next actions
 * that the user can take.
 */

// changelog
// [2/29/20] [Nichole Maldonado] refactored handleException method
//                               from FileWriter and create an interface
//                               that can be used for all exceptions.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.

package utep.cs3331.lab5.files.exceptions;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Interface that provides a static method to
 * handle exceptions throwns. The method
 * prints the error and provides the next actions
 * that the user can take.
 */
public interface ExceptionHandler {
    
    /*
     * Method that allows user to determine whether they
     * want to quit the program or perform some action in the
     * event of an exception or error.
     * Input: The error message of what went wrong, the propsed action, and
     *        a scanner for user input.
     * Output: true if the user wants to follow the proposed action or
     *         false to quit.
     */
    public static boolean handleException(String error, String proposedAction, Scanner input) {
        System.out.printf("An error occured when %s.\n", error);
        System.out.println("Would you like to");
        System.out.printf("1. %s\n", proposedAction);
        System.out.println("2. Quit");
        System.out.print("Select 1 or 2: ");
        int menuNum = 2;
        try {
            menuNum = input.nextInt();
            if (menuNum != 1 && menuNum != 2) {
                System.out.println("Invalid selection. Program terminating");
                menuNum = 2;
            }
        }
        catch(InputMismatchException o){
            System.out.println("Invalid selection. Program terminating.");
        }
        finally {
            input.nextLine();
            System.out.println();
        }
        return menuNum == 1;
    }
}