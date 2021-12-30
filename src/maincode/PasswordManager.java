// Nichole Maldonado
// CS331 - Lab 5, PasswordManager Class

/*
 * The PasswordManager class accepts a password as a field,
 * and verifies if the user is able to correctly input their
 * password. Also provides an interface to retrieve a 
 * new password from the user.
 */

// changelog
// [4/30/20] [Nichole Maldonado] created class to verify passwords and collect a new
//                               password if necessary.

package utep.cs3331.lab5.files;

import java.util.Scanner;

/*
 * The PasswordManager class accepts a password as a field,
 * and verifies if the user is able to correctly input their
 * password. Also provides an interface to retrieve a 
 * new password from the user.
 */
public class PasswordManager {
    private String password;
    
   
    /*
     * Getter for the password.
     * @param: None
     * @return: the password
     */
    public String getPassword() {
        return this.password;
    }
    
    /*
     * Setter for the password.
     * @param: the new password
     * @return: None
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /*
     * Method that determines if the user input matches their password
     * @param: Scanner for input
     * @return: True if the user was able to correctly enter their password,
     *          false otherwise.
     */
    protected boolean verifyPassword(Scanner input) {
        int numAttempts = 5;
        boolean matchFound = false;
        System.out.print("\nEnter your password: ");
        
        while (!matchFound && numAttempts > 0) {
            String trialPassword = input.nextLine();
            if (password.equals(trialPassword)) {
                matchFound = true;
            }
            else {
                numAttempts--;
                System.out.println("Invalid password. Number of attempts left: " + numAttempts);
                System.out.print("\nPassword: ");
            }
        }
        if (!matchFound) {
            System.out.println("\nMaximum password tries were exceeded. Try again later");
        }
        else {
            System.out.println("Valid password. Logging on.\n");
        }
        return matchFound;
    }
    
    /*
     * Creates new password if an existing user's password could not be found.
     * @param: Scanner for input
     * @return: None
     */
    protected void createNewPasswordForExistingUser(Scanner input){
        System.out.println("Your password could not be located.");
        createNewPassword(input);
    }
    
    /*
     * Creates a new password.
     * @param: Scanner for input
     * @return: None
     */
    protected void createNewPassword(Scanner input) {
        System.out.println("Please enter a new password.");
        System.out.println("All passwords will have their white space stripped if you include them.");
        System.out.print("Password: ");
   
        while ((this.password = input.nextLine().replaceAll("\\s","")).length() == 0) {
            System.out.println("Your password needs to include at least one character");
            System.out.println("Please try again. All white spaces will be stripped");
            System.out.print("Password: ");
        }
        System.out.println();
    }
}