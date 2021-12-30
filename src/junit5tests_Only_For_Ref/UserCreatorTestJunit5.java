// Nichole Maldonado
// CS331 - Lab 5, UserCreator Class

/*
 * Junit tests for UserCreator's arrangePasswordValidPassword method.
 * Test cases covers the correct password entered, incorrect passwords,
 * and existing users with no passwords.
 */

// changelog
// [5/01/20] [Nichole Maldonado] added junit tests for arrangePasswordValidPassword.
// [5/01/20] [Nichole Maldonado] added junit test cases for possible edge cases
//                               such as an existing user not having a
//                               password element.

// FOR JUNIT 5 ONLY

import java.util.Scanner;

import utep.cs3331.lab5.files.PasswordManager;
import utep.cs3331.lab5.files.UserCreator;

import org.jdom2.Element;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/*
 * Junit tests for UserCreator's arrangePasswordValidPassword method.
 * Test cases covers the correct password entered, incorrect passwords,
 * and existing users with no passwords.
 */
public class UserCreatorTestJunit5 {
    PasswordManager passwordManager;
    Element user;

    /*
     * Creates a user element and password element for every
     * test case.
     * @param: None.
     * @return: None.
     */
    @Before
    public void setup() {
        this.passwordManager = new PasswordManager();
        this.user = new Element("user");
        this.user.addContent(new Element("password"));
    }

    /*
     * Method that tests if a valid password is seen as valid.
     * @param: None.
     * @return: None.
     */
    @Test
    public void arrangePasswordValidPassword() {
        this.user.getChild("password").setText("userPassword");
        Scanner input = new Scanner("userPassword");
        boolean[] addedAndValid = UserCreator.arrangePassword(this.passwordManager, this.user, input);
        assertFalse(addedAndValid[0]);
        assertTrue(addedAndValid[1]);
    }

    /*
     * Method that tests if an invalid password is seen as invalid.
     * @param: None.
     * @return: None.
     */
    @Test
    public void arrangePasswordInitialInValidPassword() {
        this.user.getChild("password").setText("userPassword");
        Scanner input = new Scanner("userpassword\nuserPassword");
        boolean[] addedAndValid = UserCreator.arrangePassword(this.passwordManager, this.user, input);
        assertFalse(addedAndValid[0]);
        assertTrue(addedAndValid[1]);
    }

    /*
     * Method that tests if a repeated invalid password will lead
     * to a time out after 5 tries
     * @param: None.
     * @return: None.
     */
    @Test
    public void arrangePasswordInValidPasswordCompletely() {
        this.user.getChild("password").setText("userPassword");
        Scanner input = new Scanner("userpassword\nuserpassword\nuserpassword\nuserpassword\nuserpassword\n");
        boolean[] addedAndValid = UserCreator.arrangePassword(this.passwordManager, this.user, input);
        assertFalse(addedAndValid[0]);
        assertFalse(addedAndValid[1]);
    }

    /*
     * Method that tests if a user element with a password element but not
     * text will lead to a new password creation.
     * @param: None.
     * @return: None.
     */
    @Test
    public void arrangePasswordNoPassword() {
        Scanner input = new Scanner("userPassword\n");
        boolean[] addedAndValid = UserCreator.arrangePassword(this.passwordManager, this.user, input);
        assertTrue(addedAndValid[1]);
        assertTrue(addedAndValid[1]);
        assertEquals("userPassword", this.passwordManager.getPassword());
    }

    /*
     * Method that tests if a password creation will remove all
     * whitespaces.
     * @param: None.
     * @return: None.
     */
    @Test
    public void arrangePasswordNoPasswordAndSpaces() {
        Scanner input = new Scanner("user       Password\n");
        boolean[] addedAndValid = UserCreator.arrangePassword(this.passwordManager, this.user, input);
        assertTrue(addedAndValid[1]);
        assertTrue(addedAndValid[1]);
        assertEquals("userPassword", this.passwordManager.getPassword());
    }

    /*
     * Method that tests if a user element lacks a password element,
     * a new password element will be created and inserted.
     * @param: None.
     * @return: None.
     */
    @Test
    public void arrangePasswordEmptyPassword() {
        this.user.getChild("password").setText("");
        Scanner input = new Scanner("userPassword\n");
        boolean[] addedAndValid = UserCreator.arrangePassword(this.passwordManager, this.user, input);
        assertTrue(addedAndValid[1]);
        assertTrue(addedAndValid[1]);
        assertEquals("userPassword", this.passwordManager.getPassword());
    }
}