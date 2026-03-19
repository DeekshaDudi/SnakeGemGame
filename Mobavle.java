package game;

/**
 * The Movable Interface represents the contract of any
 * object that we can move in the game.
 * 
 * It is only extended by Snake object for now.
 * 
 * @author Deeksha
 */
public interface Movable {
	
	/**
	 * Allows objects to change their coordinates on the game screen 
	 * depending on user input. 
	 */
	void move();
}