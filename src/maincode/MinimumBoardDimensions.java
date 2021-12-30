// Nichole Maldonado
// CS331 - Lab5, MimimumBoardDimensions Interface

/*
 * This interface defines the minimum values
 * for the boards x and y dimensions.
 */

// changelog
// [2/13/20] [Nichole Maldonado] created BoardDimensions interface which defines
//                               the minimum and maximum x and y positions on
//                               the board.
// [3/04/20] [Nichole Mladonado] changed BoardDimension to only have minimum dimensions.
// [4/25/20] [Nichole Maldonado] Changed package to lab5.

package utep.cs3331.lab5.chess;

/*
 * BoardDimensions interface declares the
 * minimum x and y positions on the
 * alloted chess board.
 */
public interface MinimumBoardDimensions {
    public static final char MIN_X_POSITION = 'A';
    public static final int MIN_Y_POSITION = 1;
}