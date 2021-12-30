// Nichole Maldonado
// CS331 - Lab 5, TerminalFontColor Class

/*
 * Interface that provides different terminal font colors.
 */

// changelog
// [5/01/20] [Nichole Maldonado] added static colors for terminal fonts.

// NOTE: These colors were found from https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
// Thanks to the link above for provding the fields to set the terminal colors!

package utep.cs3331.tests.testutils;

/*
 * Interface that provides different terminal font colors.
 */
public interface TerminalFontColor {
    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m"; 
    public static final String GREEN = "\033[0;32m"; 
    public static final String BLUE = "\033[0;34m"; 
    public static final String BLUE_BOLD = "\033[1;34m"; 
}