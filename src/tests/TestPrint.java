// Nichole Maldonado
// CS331 - Lab 5, TestPrint Class

/*
 * Interface that provides static methods to print the start and end of a test case.
 */

// changelog
// [5/01/20] [Nichole Maldonado] added static methods for start and end of
//                               test cases.

package utep.cs3331.tests.testutils;

import utep.cs3331.tests.testutils.TerminalFontColor;

import org.junit.rules.TestName;

/*
 * Interface that provides static methods to print the start and end of a test case.
 */
public interface TestPrint extends TerminalFontColor{
    
    /*
     * Static method that prints the current method running.
     * @param: None
     * @return: None
     */
    public static void printTestStart(TestName testName) {
        System.out.println(TerminalFontColor.GREEN + "\n[junit]" + TerminalFontColor.RESET + " Running "  + testName.getMethodName());
    }
    
    /*
     * Static method that prints the test completed.
     * @param: None
     * @return: None
     */
    public static void printTestEnd() {
        System.out.println(TerminalFontColor.GREEN + "\n[junit]"  + TerminalFontColor.RESET + " Test Complete");
    }
}