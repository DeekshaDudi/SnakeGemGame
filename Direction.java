package game;

/**
 * Direction Enum represents the four possible directions for Snake
 * element and its parameters define the direction in X and Y in terms of integers
 * 
 * NONE is the direction at the start of each round when no key is pressed
 * and the snake does not move
 * 
 * @author Deeksha
 */

public enum Direction {
	NONE(0, 0), RIGHT(1, 0), LEFT(-1, 0), UP(0, -1), DOWN(0, 1);
	
	private final int directionX;
	private final int directionY;
	
	/**
	 * Initializes the directionX and directionY instance variables 
	 * depending on the chosen enum constant.
	 * 
	 * @param directionX
	 * @param directionY
	 */
	private Direction(int directionX, int directionY) {
		
		this.directionX = directionX;
		this.directionY = directionY;
	}
	
	/**
	 * Returns the value of the enum constant in the x direction.
	 * 
	 * @return the integer value of the enum constant in the x direction. 
	 */
	public int getDirectionX() {
		return directionX;
	}
	
	/**
	 * Returns the value of the enum constant in the y direction.
	 * 
	 * @return the integer value of the enum constant in the y direction. 
	 */
	public int getDirectionY() {
		return directionY;
	}
}


