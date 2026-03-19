package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * This class represents the snake in the game. It can collect gems, 
 * rotate, and move up, down, left, or right. Movements are determined by 
 * user input.
 * 
 * @author Deeksha
 */
public class Snake extends Polygon implements Movable, KeyListener {

	/**
     * Enum that represents what direction the snake is facing 
     * based on what key was pressed last.
     */
    Direction direction;
    
    /**
     * Represents how much a snake moves in one step.
     * It ensures the snake moves according to the grid boxes.
     */
    private int stepSize; 
  

    /**
     * Calls the super constructor in the Polygon class to create the square,
     * then sets a step size and direction (initialized to none so the snake
     * doesn't move at the start of the game).
     * 
     * @param shape - shape of the snake (rectangle)
     * @param position - position of the snake on a random block of the grid
     * @param rotation - required by Polygon Class
     */
    public Snake(Point[] shape, Point position, double rotation) {
        super(shape, position, rotation);
        
        stepSize = 50;
        direction = Direction.NONE;
    }

    @Override
    public void move() { 
        position.x += direction.getDirectionX() * stepSize;
        position.y += direction.getDirectionY() * stepSize;
    }

    /**
     * Checks if the snake collides with any other polygon, like a 
     * gem or an obstacle
     * 
     * @param other polygon (gem or obstacle)
     * @return boolean - whether they collided or not
     */
    public boolean collidesWith(Polygon other) {
    	
        Point[] otherPoints = other.getPoints();
        Point[] myPoints = this.getPoints();

        for (int i = 0; i < otherPoints.length; i++) {
            if (this.contains(otherPoints[i])) {
                return true;
            }
        }

        for (int i = 0; i < myPoints.length; i++) {
            if (other.contains(myPoints[i])) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Required in SnakeGemGame to prevent multiple objects from spawning 
     * on the same tiles
     *
     * @param tileCenter - center coordinates of the board tile
     * @return boolean - tells whether the snake is on the given tile
     */ 
    public boolean isOnTile(Point tileCenter) {
    	return (int) position.x == (int) tileCenter.x &&
    			(int) position.y == (int) tileCenter.y;
    }
    
    //=========================================== KEYBOARD LOGIC ===========================================
    // These methods together control the keyboard inputs and their results

    /**
     * Checks what key is pressed and changes the direction and degree of rotation 
     * based on the input.
     * 
     * @param KeyBoard Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
        	direction = Direction.UP;
        	rotation = 270;
        } 
        else if (key == KeyEvent.VK_DOWN) {
        	direction = Direction.DOWN;
        	rotation = 90;
        } 
        else if (key == KeyEvent.VK_LEFT) {
        	direction = Direction.LEFT;
        	rotation = 180;
        } 
        else if (key == KeyEvent.VK_RIGHT) {
        	direction = Direction.RIGHT;
        	rotation = 0;
        }
    }

    /**
     * The next two methods have to be overridden based on KeyListener requirements
     * but they don't have any real use for us.
     */
    
    @Override
    public void keyReleased(KeyEvent e) {
    	// required by KeyListener
    }

    @Override
    public void keyTyped(KeyEvent e) { 
        // required by KeyListener
    }
    
    //=========================================== PAINT LOGIC ===========================================
    
    /**
     * Paints the area inside the edges of the snake (rectangle)
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

        brush.setColor(Color.GREEN);
        brush.fillPolygon(xPoints, yPoints, points.length);
    }
}