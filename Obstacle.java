package game;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Obstacles are square blocks that appear randomly in the game along with gems.
 * You lose a life if you hit a block.
 * You will have a maximum of 10 obstacles in the game.
 *
 * @author Deeksha
 */
public class Obstacle extends Polygon {

    private Color color;
    
    /**
     * Calls the super constructor in the Polygon class to create the square,
     * then sets the color of the obstacle block to gray
     *
     * @param shape - stores the square coordinates of obstacles
     * @param position - position of the obstacle on a random block of the grid
     * @param rotation - required by Polygon class
     */
    public Obstacle(Point[] shape, Point position, double rotation) {
        super(shape, position, rotation);
        this.color = Color.GRAY;
    }
    
    /**
     * Required in SnakeGemGame to prevent multiple objects from spawning 
     * on the same tiles
     *
     * @param tileCenter - center coordinates of the board tile that the obstacle is on
     * @return boolean - tells whether the current obstacle is on the given tile
     */
    public boolean isOnTile(Point tileCenter) {
    	return (int) position.x == (int) tileCenter.x &&
    			(int) position.y == (int) tileCenter.y;
    }

    /**
     * Paints the area inside the obstacle edges (square).
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