// Nichole Maldonado
// CS331 - Lab 5, GameModelTest Class

/*
 * Junit tests for GameModels's verifyAndInitiateValidMove method.
 * Test cases cover possible exceptions thrown, edge cases of pieces
 * moved where pieces do not belong, and regular game plays.
 */

// changelog
// [5/01/20] [Nichole Maldonado] added junit tests for verifyAndInitiateValidMove.
// [5/01/20] [Nichole Maldonado] added junit test cases for possible exceptions

package utep.cs3331.tests;

import utep.cs3331.lab5.chess.GameBoard;
import utep.cs3331.lab5.chess.GameModel;
import utep.cs3331.lab5.chess.chesspieces.Bishop;
import utep.cs3331.lab5.chess.chesspieces.ChessPiece;
import utep.cs3331.lab5.chess.chesspieces.ChessPieceTypes;
import utep.cs3331.lab5.chess.exceptions.PieceInPlaceException;
import utep.cs3331.tests.testutils.TestPrint;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.Test;

/*
 * Junit tests for GameModels's verifyAndInitiateValidMove method.
 * Test cases cover possible exceptions thrown, edge cases of pieces
 * moved where pieces do not belong, and regular game plays.
 */
public class GameModelTest implements TestPrint{
    private GameModel gameModel;

    // Used to print method names.
    @Rule
    public TestName testName = new TestName();

    /*
     * Method that creates a GameModel for every test case
     * and creates an 8 x 8 Chessboard.
     * @param: None.
     * @return: None.
     */
    @Before
    public void setup() {
        TestPrint.printTestStart(testName);
        this.gameModel = new GameModel("1234567891234567");
        gameModel.createBoard(8,8);
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
     * Method that checks the base cases of valid moves.
     * Tests to see if the bishop at C,5 can move to A,8.
     * @param: None.
     * @return: None.
     */
    @Test
    public void verifyAndInitiateMoveValidMove() {
        this.gameModel.getBoard().addChessPiece(ChessPieceTypes.BISHOP, 'D', 5, true,true);
        String canMove = null;
        try {

            // Game passes in the integer value of D and the y position - 1.
            canMove = this.gameModel.verifyAndInitiateMove(3,4,'A',8);
        }
        catch (NullPointerException | PieceInPlaceException e) {
            canMove = null;
        }
        assertEquals("", canMove);
    }

    /*
     * Method that checks the base cases of invalid moves.
     * Tests if pawn at A,2 can move to A,5.
     * @param: None.
     * @return: None.
     */
    @Test
    public void verifyAndInitiateMoveInValidMove() {
        this.gameModel.getBoard().addChessPiece(ChessPieceTypes.PAWN, 'A', 2, true,true);
        String canMove = null;
        try {
            // Game passes in the integer value of D and the y position - 1.
            canMove = this.gameModel.verifyAndInitiateMove(0,1,'A',5);
        }
        catch (NullPointerException | PieceInPlaceException e) {
            canMove = null;
        }
        assertEquals("not", canMove);
    }

    /*
     * Method that tests to see if a Rook can move to a position where a
     * Pawn already exists. Edges case that ensures that the PieceInPlaceException * is thrown  since we attempt to move a piece to where a piece
     * already exists.
     * @param: None.
     * @return: None.
     */
    @Test
    public void verifyAndInitiateMovePieceAlreadyExists() {
        this.gameModel.getBoard().addChessPiece(ChessPieceTypes.PAWN, 'A', 2, true,true);
        this.gameModel.getBoard().addChessPiece(ChessPieceTypes.ROOK, 'A', 1, true,true);
        assertThrows(PieceInPlaceException.class, ()-> {this.gameModel.verifyAndInitiateMove(0,0,
                'A',2);});
    }

    /*
     * Method that tests to move a piece a piece that does not exist.
     * startGame will catch exception and have view display the results.
     * already exists.
     * @param: None.
     * @return: None.
     */
    @Test
    public void verifyAndInitiateMovePieceDNE() {
        assertThrows(NullPointerException.class, ()-> {this.gameModel.verifyAndInitiateMove(0,1,
                'A',2);});
    }

    /*
     * Test case to check that if just one piece moves,
     * all the other pieces will stay in their positions.
     * @param: None.
     * @return: None.
     */
    @Test
    public void verifyAndInitiateMovePieceMoved() {

        // Populate game board
        this.gameModel.getBoard().addChessPiece(ChessPieceTypes.PAWN, 'A', 2, true,true);
        this.gameModel.getBoard().addChessPiece(ChessPieceTypes.KNIGHT, 'B', 8, false,true);
        this.gameModel.getBoard().addChessPiece(ChessPieceTypes.QUEEN, 'D', 4, false,true);
        this.gameModel.getBoard().addChessPiece(ChessPieceTypes.BISHOP, 'B', 4, true,true);
        this.gameModel.getBoard().addChessPiece(ChessPieceTypes.KING, 'E', 2, false,true);
        this.gameModel.getBoard().addChessPiece(ChessPieceTypes.ROOK, 'E', 8, true,true);

        // Get reference to ChessPieces
        ChessPiece pawn = this.gameModel.getBoard().getChessBoard()[0][1];
        ChessPiece knight = this.gameModel.getBoard().getChessBoard()[1][7];
        ChessPiece queen = this.gameModel.getBoard().getChessBoard()[3][3];
        ChessPiece bishop = this.gameModel.getBoard().getChessBoard()[1][3];
        ChessPiece king = this.gameModel.getBoard().getChessBoard()[4][1];
        ChessPiece rook = this.gameModel.getBoard().getChessBoard()[4][7];

        try {

            // Move Knight
            this.gameModel.verifyAndInitiateMove(1,7,'C',6);
        } catch (NullPointerException | PieceInPlaceException e) {
            fail();
        }

        // Check to see if only Knight moved.
        assertSame(pawn, this.gameModel.getBoard().getChessBoard()[2][5]);
        assertSame(knight, this.gameModel.getBoard().getChessBoard()[1][7]);
        assertSame(queen, this.gameModel.getBoard().getChessBoard()[3][3]);
        assertSame(bishop, this.gameModel.getBoard().getChessBoard()[1][3]);
        assertSame(king, this.gameModel.getBoard().getChessBoard()[4][1]);
        assertSame(rook, this.gameModel.getBoard().getChessBoard()[4][7]);
    }
}