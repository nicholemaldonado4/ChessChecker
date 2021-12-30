// Nichole Maldonado
// CS331 - Lab 5, QueenTest Class

/*
 * Junit tests for the Queen's valid move method. Also includes tests
 * for GameView's selectX and retrieveYPosition which is used to pass values
 * to the valid move method. These methods protect invalid Board ranges to be
 * passed to the validMove method.
 */

// changelog
// [5/01/20] [Nichole Maldonado] added junit tests for validMove.
// [5/01/20] [Nichole Maldonado] added junit tests for rselectX and retrieveYPostiion
//                               to test edge cases.

// FOR JUNIT 5

import utep.cs3331.lab5.chess.chesspieces.ChessPiece;
import utep.cs3331.lab5.chess.chesspieces.ChessPieceTypes;
import utep.cs3331.lab5.chess.chesspieces.Queen;
import utep.cs3331.lab5.chess.GameView;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/*
 * Junit tests for the Queen's valid move method. Also includes tests
 * for GameView's selectX and retrieveYPosition which is used to pass values
 * to the valid move method. These methods protect invalid Board ranges to be
 * passed to the validMove method.
 */
class QueenTestJunit5 {
    private ChessPiece queen;

    /*
     * Method that creates a Queen.
     * diagonal position.
     * @param: None.
     * @return: None.
     */
    @BeforeEach
    void setup() {
        queen = new Queen(ChessPieceTypes.QUEEN.formatName(), 'D', 5, true, true);
    }

    /*
     * Method that checks if all diagonal moves are valid.
     * @param: None.
     * @return: None.
     */
    @Test
    void validMoveDiagonal() {
        // Up and to the left.
        assertTrue(queen.validMove('A', 8));

        // Up and to the right.
        assertTrue(queen.validMove('F', 7));

        // Down and to the left.
        assertTrue(queen.validMove('B', 3));

        // Down and to the right.
        assertTrue(queen.validMove('F', 3));

        queen.setIsWhite(false);

        // Up and to the left.
        assertTrue(queen.validMove('A', 8));

        // Up and to the right.
        assertTrue(queen.validMove('F', 7));

        // Down and to the left.
        assertTrue(queen.validMove('B', 3));

        // Down and to the right.
        assertTrue(queen.validMove('F', 3));
    }

    /*
     * Method that verifies that the Queen can move horizontally.
     * @param: None.
     * @return: None.
     */
    @Test
    void validMoveHorizontal() {
        queen.setYPosition(1);
        queen.setXPosition('D');

        // Left
        assertTrue(queen.validMove('A', 1));

        // Right
        assertTrue(queen.validMove('E', 1));

        queen.setIsWhite(false);
        queen.setYPosition(7);
        queen.setXPosition('F');

        // Left
        assertTrue(queen.validMove('C', 7));

        // Right
        assertTrue(queen.validMove('H', 7));

    }

    /*
     * Method that verifies that the Queen can move vertically.
     * @param: None.
     * @return: None.
     */
    @Test
    void validMoveVertical() {

        // Top
        assertTrue(queen.validMove('D', 8));

        // Bottom
        assertTrue(queen.validMove('D', 1));

        queen.setIsWhite(false);
        queen.setYPosition(6);
        queen.setXPosition('B');

        // Top
        assertTrue(queen.validMove('B', 7));

        // Bottom
        assertTrue(queen.validMove('B', 2));
    }

    /*
     * Method that ensures that a Queen cannot move in a l shape.
     * @param: None.
     * @return: None.
     */
    @Test
    void invalidMoveLShape() {

        // Top and to right
        assertFalse(queen.validMove('E', 7));

        // Top and to the left
        assertFalse(queen.validMove('C', 7));

        // Bottom and to the right
        assertFalse(queen.validMove('E', 3));

        // Bottom and to the left.
        assertFalse(queen.validMove('C', 3));
    }

    /*
     * Method that ensures that a queen cannot move in a nearly
     * diagonal position.
     * @param: None.
     * @return: None.
     */
    @Test
    void invalidMoveSlantedDiagonal() {
        queen.setXPosition('C');
        queen.setYPosition(2);
        assertFalse(queen.validMove('B', 5));
        assertFalse(queen.validMove('E', 8));

        queen.setIsWhite(false);
        queen.setXPosition('F');
        queen.setYPosition(1);
        assertFalse(queen.validMove('D', 8));
        assertFalse(queen.validMove('H', 4));
    }

    /*
     * Method that check out of bounds for the x position.
     * selectX gets the x value that is eventually given
     * to valid move for the queen.
     * diagonal position.
     * @param: None.
     * @return: None.
     */
    @Test
    void outOfBoundsXPosition() {
        GameView gameView = new GameView();
        Scanner input = new Scanner("i\nK\n2\ns\nq\na\n");
        assertEquals('A', gameView.selectX(input, 8));
    }

    /*
     * Method that check out of bounds for the y position.
     * retrieveYPosition gets the y value that is eventually given
     * to valid move for the queen.
     * diagonal position.
     * @param: None.
     * @return: None.
     */
    @Test
    void outOfBoundsYPosition() {
        GameView gameView = new GameView();
        Scanner input = new Scanner("i\n20\n9\n-1\n!\nA\n8\n");
        assertEquals(8, gameView.retrieveYPosition(input, 8));
    }
}