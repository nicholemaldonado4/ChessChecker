// Nichole Maldonado
// CS331 - Lab 5, GameDeleterTestSuite Class

/*
 * GameDeleterTestSuite Class is the test suite for the GameDeleterTest junit test.
 */

// changelog
// [5/03/20] [Nichole Maldonado] added test suite for GameDelterTest.

package utep.cs3331.tests;

import utep.cs3331.tests.GameDeleterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 * GameDeleterTestSuite Class is the test suite for the GameDeleterTest junit test.
 */
@RunWith(Suite.class)
@SuiteClasses({GameDeleterTest.class})
public class GameDeleterTestSuite {}