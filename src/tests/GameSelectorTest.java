// Nichole Maldonado
// CS331 - Lab 5, GameSelectorTest Class

/*
 * Junit tests for GameSelector's pickGameToLoadFirstElement method. 
 * Test cases covers the correct ordering of elements in the deque,
 * invalid ids, and invalid selections.
 */

// changelog
// [5/01/20] [Nichole Maldonado] added junit tests for pickGameToLoadFirstElement.
// [5/01/20] [Nichole Maldonado] added junit test cases for possible edge cases
//                               such as invalid ids and invalid selections.

package utep.cs3331.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import utep.cs3331.lab5.chess.GameSelector;
import utep.cs3331.lab5.files.FilePaths;
import utep.cs3331.tests.testutils.TestPrint;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/*
 * Junit tests for GameSelector's pickGameToLoadFirstElement method. 
 * Test cases covers the correct ordering of elements in the deque,
 * invalid ids, and invalid selections.
 */
public class GameSelectorTest implements TestPrint {
    private Deque<String> idQueue;
    private GameSelector gameSelector;
    
    // Used to print method names.
    @Rule 
    public TestName testName = new TestName();
    
    /*
     * Every deque starts with the same id at the head.
     * @param: None.
     * @return: None.
     */
    @Before
    public void setup(){
        TestPrint.printTestStart(testName);
        this.idQueue = new ArrayDeque<>();
        this.gameSelector = new GameSelector(idQueue, new FilePaths(), "nichole");
        idQueue.addLast("20200429205546624");
    }
    
    /*
     * Method that prints the end status after every test case.
     * @param: None.
     * @return: None.
     */
    @After
    public void tearDown() {
        TestPrint.printTestEnd();
    }

    /*
     * Checks that if the user selects the most recent game, the
     * order of the games will be preserved.
     * @param: None.
     * @return: None.
     */ 
    @Test
    public void pickGameToLoadFirstElement() {
        idQueue.addLast("20200429205500529");
        idQueue.addLast("20200430200439309");
        Scanner input = new Scanner("1\n");
        gameSelector.pickGameToLoad(input);

        assertEquals("20200430200439309", idQueue.peekLast());
        idQueue.removeLast();
        assertEquals("20200429205500529", idQueue.peekLast());
        idQueue.removeLast();
        assertEquals("20200429205546624", idQueue.peekLast());
    }
    
    /*
     * Checks that if the user selects the last game, the
     * last game will be moved to the tail of the deque.
     * @param: None.
     * @return: None.
     */ 
    @Test
    public void pickGameToLoadLastElement() {
        idQueue.addLast("20200429205500529");
        idQueue.addLast("20200430200439309");
        Scanner input = new Scanner("3\n");
        gameSelector.pickGameToLoad(input);

        assertEquals("20200429205546624", idQueue.peekLast());
        idQueue.removeLast();
        assertEquals("20200430200439309", idQueue.peekLast());
        idQueue.removeLast();
        assertEquals("20200429205500529", idQueue.peekLast());
    }

    /*
     * Checks that if the user selects the middle game, the
     * middle game will be moved to the tail of the deque.
     * @param: None.
     * @return: None.
     */ 
    @Test
    public void pickGameToLoadCenterElement() {
        idQueue.addLast("20200429205500529");
        idQueue.addLast("20200430200439309");
        Scanner input = new Scanner("2\n");
        gameSelector.pickGameToLoad(input);

        assertEquals("20200429205500529", idQueue.peekLast());
        idQueue.removeLast();
        assertEquals("20200430200439309", idQueue.peekLast());
        idQueue.removeLast();
        assertEquals("20200429205546624", idQueue.peekLast());
    }

    /*
     * Checks that if the user has only two game ids,
     * the program will still be able to use the queue and array list accordingly.
     * @param: None.
     * @return: None.
     */ 
    @Test
    public void pickGameToLoadLessThanThree() {

        idQueue.addLast("20200429205500529");
        Scanner input = new Scanner("2\n");

        gameSelector.pickGameToLoad(input);

        assertEquals("20200429205546624", idQueue.peekLast());
        idQueue.removeLast();
        assertEquals("20200429205500529", idQueue.peekLast());
        idQueue.removeLast();
    }

    /*
     * If id cannot be formatted a game key is corrupted. Tests if
     * method can ignore the corrupt id and print the other options.
     * @param: None.
     * @return: None.
     */ 
    @Test
    public void pickGameToLoadInvalidID() {

        idQueue.addLast("123a");
        idQueue.addLast("20200429205500529");
        Scanner input = new Scanner("2\n");

        gameSelector.pickGameToLoad(input);

        assertEquals("20200429205546624", idQueue.peekLast());
        idQueue.removeLast();
        assertEquals("20200429205500529", idQueue.peekLast());
        idQueue.removeLast();
    }

    /*
     * If user selects wrong number most recent game is loaded.
     * Checks to see that most recent game is loaded.
     * @param: None.
     * @return: None.
     */ 
    @Test
    public void pickGameToLoadInvalidSelection() {

        idQueue.addLast("20200429205500529");
        Scanner input = new Scanner("3\n");

        gameSelector.pickGameToLoad(input);

        assertEquals("20200429205500529", idQueue.peekLast());
        idQueue.removeLast();
        assertEquals("20200429205546624", idQueue.peekLast());
        idQueue.removeLast();
    }
}