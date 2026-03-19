package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * The class represents a collectible Gem in the game
 * 
 * They have 3 major properties: their rarity, the value and color 
 * of each gem based on its rarity
 * 
 * @author Deeksha
 */
public class Gem extends Polygon implements Collectible {

    private boolean rarity;  
    private int pointValue;
    private Color color;
    
    /**
     * Calls the super constructor in the Polygon class to create the polygon,
     * then sets a rarity to determine the color and point value of the gem.
     *
     * @param shape - shape of the gem (pentagon for normal gems
     * and hexagon for rare gems)
     * @param position - position of the gem on a random block of the grid
     * @param rotation - needed by Polygon Class
     * @param rarity - boolean that decided if the gem is rare or not
     */
    public Gem(Point[] shape, Point position, double rotation, boolean rarity) {
        super(shape, position, rotation);
        this.rarity = rarity;

        if (rarity) {
            pointValue = 3;
            color = Color.PINK;
        } 
        else {
            pointValue = 1;
            color = Color.BLUE; 
        }
    }
    
    /**
     * Returns the point value of the gem.
     *
     * @return pointValue
     */
    @Override
    public int getPointsValue() {
        return pointValue;
    }
    
    /**
     * Returns a boolean that represents whether the gem is rare or not.
     *
     * @return rarity
     */
    @Override
    public boolean isGemRare() {
        return rarity;
    }

    //============================================================================================================
    
    /**
     * Required by the SnakeGemGame class to prevent multiple objects from 
     * spawning on the same tiles.
     *
     * @param tileCenter - center coordinates of the board tile the gem is on
     * @return boolean - tells whether the current gem is on the given tile
     */
    public boolean isOnTile(Point tileCenter) {
    	return (int) position.x == (int) tileCenter.x &&
    			(int) position.y == (int) tileCenter.y;
    }
    
    /**
     * Paints the area inside the gem edges (pentagon or hexagon).
     * 
     * @param brush
     */
    public void paint(Graphics brush) {
    	
        Point[] points = this.getPoints();
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xPoints[i] = (int) points[i].x;
            yPoints[i] = (int) points[i].y;
        }

        brush.setColor(color);
        brush.fillPolygon(xPoints, yPoints, points.length);
    }
}