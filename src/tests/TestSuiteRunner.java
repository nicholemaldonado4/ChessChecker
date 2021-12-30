// Nichole Maldonado
// CS331 - Lab 5, TestSuiteRunner Class

/*
 * Main test runner that runs all 5 tests for UserCreatorTestSuite, 
 * GameSelectorTestSuite, GameDeleterTestSuite, GameModelTestSuite,
 * and QueenTestSuite.
 */

// changelog
// [5/01/20] [Nichole Maldonado] added test suite for UserCreator
// [5/01/20] [Nichole Maldonado] added test suite for other tests.
// [5/01/20] [Nichole Maldonado] added font color capabilities

// NOTE: Font colors were user from https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
// Thanks to the source above!

package utep.cs3331.tests;

import utep.cs3331.tests.GameDeleterTestSuite;
import utep.cs3331.tests.GameModelTestSuite;
import utep.cs3331.tests.GameSelectorTestSuite;
import utep.cs3331.tests.QueenTestSuite;
import utep.cs3331.tests.testutils.TerminalFontColor;
import utep.cs3331.tests.testutils.TestPrint;
import utep.cs3331.tests.UserCreatorTestSuite;

import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;
import org.junit.runner.Result;

/*
 * Main test runner that runs all 5 tests for UserCreatorTestSuite, 
 * GameSelectorTestSuite, GameDeleterTestSuite, GameModelTestSuite,
 * and QueenTestSuite.
 */
public class TestSuiteRunner implements TerminalFontColor {
    
    /*
     * Method that runs the tests and prints the results.
     * @param: None.
     * @return: None.
     */
    public static <T> void runTests(Class<T> classToTest) {
        Result result = JUnitCore.runClasses(classToTest);
 
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            failure.getException().printStackTrace();
        }
        System.out.println("\n--------------------------");
        System.out.println(TerminalFontColor.BLUE + "Number of Tests Run: " + TerminalFontColor.RESET + result.getRunCount());   
        System.out.println(TerminalFontColor.BLUE + "Number of Test Failures: " + TerminalFontColor.RESET + result.getFailureCount());
        System.out.print( TerminalFontColor.BLUE + "All tests passed: " + ((result.wasSuccessful()) ? TerminalFontColor.GREEN : TerminalFontColor.RED) + result.wasSuccessful() + TerminalFontColor.RESET);
        
        System.out.println("\n--------------------------\n");
    }
    
    /*
     * Main method that runs unit tests for all 5 test suites.
     * @param: None.
     * @return: None.
     */
    public static void main(String[] args) {
        
        System.out.println(TerminalFontColor.BLUE_BOLD + "UNIT TESTS FOR USER CREATOR\n"  + TerminalFontColor.RESET);
        runTests(UserCreatorTestSuite.class);
        
        System.out.println(TerminalFontColor.BLUE_BOLD + "UNIT TESTS FOR GAME SELECTOR\n" + TerminalFontColor.RESET);
        runTests(GameSelectorTestSuite.class);
        
        System.out.println(TerminalFontColor.BLUE_BOLD + "UNIT TESTS FOR GAME DELETER\n" + TerminalFontColor.RESET);
        runTests(GameDeleterTestSuite.class);
        
        System.out.println(TerminalFontColor.BLUE_BOLD + "UNIT TESTS FOR GAME MODEL\n" + TerminalFontColor.RESET);
        runTests(GameModelTestSuite.class);
        
        System.out.println(TerminalFontColor.BLUE_BOLD + "UNIT TESTS FOR QUEEN\n" + TerminalFontColor.RESET);
        runTests(QueenTestSuite.class);
    }
}