// Nichole Maldonado
// CS331 - Lab 5, QueenTestSuite Class

/*
 * QueentTestSuite Class is the test suite for the QueenTest junit test.
 */

// changelog
// [5/03/20] [Nichole Maldonado] added test suite for QueenTest.

package utep.cs3331.tests;

import utep.cs3331.tests.QueenTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 * QueentTestSuite Class is the test suite for the QueenTest junit test.
 */
@RunWith(Suite.class)
@SuiteClasses({QueenTest.class})
public class QueenTestSuite {}