# Instructions

This project was made using a text editor and the terminal, please first set the 3 jar file path locally using:

export CLASSPATH=$CLASSPATH:[your directory]/Maldonado_Nichole_Lab5/junit-4.13.jar:[your directory]/Maldonado_Nichole_Lab5/jdom-2.0.6.jar:[your directory]/Maldonado_Nichole_Lab5/hamcrest-core-1.3.jar

Example:
export CLASSPATH=$CLASSPATH:/Users/nichole_maldonado/Desktop/Lab5/junit-4.13.jar:/Users/nichole_maldonado/Desktop/Lab5/jdom-2.0.6.jar:/Users/nichole_maldonado/Desktop/Lab5/hamcrest-core-1.3.jar

The three jars needed are:
hamcrest-core-1.3.jar
jdom-2.0.6.jar
junit-4.13.jar

The main program is located in [your directory]/Maldonado_Nichole_Lab5/utep/cs3331/lab5/chess/Maldonado_Nichole_Lab5.class

To run the program, simply enter (while the state of your terminal is located in the folder Maldonado_Nichole_Lab5):

java utep/cs3331/lab5/chess/Maldonado_Nichole_Lab5

The program starts by asking the user to enter the path for three files:

1. user xml file:
	[your directory]Maldonado_Nichole_Lab5/utep/cs3331/lab5/files/users.xml

2. configs xml file:
	[your director]Maldonado_Nichole_Lab5/utep/cs3331/lab5/files/gameConfigs.xml

3. chess template xml file:
	[your director]Maldonado_Nichole_Lab5/utep/cs3331/lab5/files/chessTemplate.xml

Please include the above paths to allow the program to run and have access to its resources.

# Testing

The main test is located in [your directory]/Maldonado_Nichole_Lab5/utep/cs3331/tests/TestSuiteRunner.class

To run the tests, simply enter (while the state of your terminal is located in the folder Maldonado_Nichole_Lab5):

java utep/cs3331/tests/TestSuiteRunner

All tests will be run. The tests are subdivided into suites that run the corresponding tests. For example, UserCreatorTestSuite.java runs UserCreatorTest.java

All code is located in src. The main code is located in src/maincode. The tests are located in src/tests. These tests are made with Junit 4. I also made the tests in Junit 5, however this code is just strictly for reference since I created a test suite to run all the test code.

# Notes for Main Program

After typing the file paths, the program allows the user to select if they are a new or existing user. If you are an existing user, you have the capability to load one of three of your most recent game (all of your older games, however, are still stored) or create a new game.

If you are a new user, you can only create a new game.

After setting up your user information, such as piece color, and board information, such as allow chat, you can move any of the pieces how you wish on the game board. After a move, the program will ask you if you are done. If so, select 2. Otherwise, you can continue to move the pieces across the board.

Lastly, if you do not select autosave, the game will ask you at the end if you want to save your game. If you do, make sure to select 1. Otherwise your game will not be saved.

All games are saved in the directory director]Maldonado_Nichole_Lab5/utep/cs3331/lab5/files/

The file name is chessTemplate and the id attached to the game.

As for the file exceptions, there are some instances in which the program will allow you to just re-enter a file path and continue. However, in some instances, the program will notify you and ask if you want to restart the game or quit.  Look in the ParserReader and ParserWriter Class file comments to learn more about why these specific exception handling  implementations were made.

Additionally, the program stores your password, so please remember it to log in again. You can also store
a maximum of 7 games. After that, the program will ask you to delete at least one game. 

