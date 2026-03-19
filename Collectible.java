package game;

/**
 * The Collectible Interface represents the contract of any
 * object that we collect in the game.
 * 
 * Right now, it is only extended by Gems but it's scope
 * can be expanded further in the future.
 * 
 * @author Deeksha
 */

public interface Collectible {

	/**
	 * Returns an integer that represents the point value of the object. 
	 * @return integer point value of the object.
	 */
	int getPointsValue();
	
	/**
	 * Returns a boolean that represents whether the Collectible object
	 * is rare or not, which means the object has a different point value.
	 * @return boolean that represents the rarity of the object.
	 */
    boolean isGemRare();  
}
