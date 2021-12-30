// Nichole Maldonado
// CS331 - Lab 5, GameDeleterTestSuite Class

/*
 * GameSelectorTestSuite Class is the test suite for the GameSelectorTest junit test.
 */

// changelog
// [5/03/20] [Nichole Maldonado] added test suite for GameDelterTest.

package utep.cs3331.tests;

import utep.cs3331.tests.GameSelectorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.Suite;

/*
 * GameSelectorTestSuite Class is the test suite for the GameSelectorTest junit test.
 */
@RunWith(Suite.class)
@SuiteClasses({GameSelectorTest.class})
public class GameSelectorTestSuite {}