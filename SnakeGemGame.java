package game;

import java.util.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

/**
 * Main class for the Snake Gem game.
 * 
 * @author Deeksha
 */
public class SnakeGemGame extends Game {

	private Snake snake;
    private ArrayList<Gem> gems;
    private ArrayList<Obstacle> obstacles;
    private GameBoard board;
    private Timer timer;

    private int score;
    private int lives;
    private int gemsCollected;
    private boolean gameOver;
    private static final int MAX_OBSTACLES = 10;

    private Random random;

    /**
     * Calls the super constructor in the Game class and initializes all
     * instance variables, including setting score to 0 and lives to 3
     */
    public SnakeGemGame() {
        super("Snake Gem", 800, 800);

        random = new Random();
        gems = new ArrayList<Gem>();
        obstacles = new ArrayList<Obstacle>();
        board = new GameBoard(Color.lightGray, Color.orange, Color.yellow);
        
        score = 0;
        lives = 3;
        gemsCollected = 0;
        gameOver = false;

        createSnake();
        spawnNewGem(); 

        this.addKeyListener(snake);
        setFocusable(true);

        //anonymous inner class that restarts the game when R key is pressed
        this.addKeyListener(new KeyListener() {

        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
        			restartGame();
        		}
        	}

        	@Override
        	public void keyReleased(KeyEvent e) {
        		// required by KeyListener
        	}

        	@Override
        	public void keyTyped(KeyEvent e) { 
        		// required by KeyListener
        	}
        	
        });
        
        //lambda expression
        timer = new Timer(250, e -> {
        	updateGame();
        	repaint();
        });
 
        timer.start();
        this.requestFocus();
    }    

    /**
     * Creates the snake shape and prepares it for use in the game.
     */
	private void createSnake() {
		
        Point[] snakeShape = { 
        		new Point(5, 15),
        		new Point(45, 15),
        		new Point(45, 35),
        		new Point(5, 35)
        };
        snake = new Snake(snakeShape, getSnakePosition(14, 0), 0);
    }
	
	/**
	 * Spawns a new gem on the game board, only when the previous gem is
	 * collected by the snake.
	 */
	private void spawnNewGem() {
		
    	int row;
    	int col;

    	do {
    		row = 1 + random.nextInt(14);
    		col = random.nextInt(14);
    	} while (!isTileValidToSpawn(row, col));

    	boolean rare = ((gemsCollected + 1) % 5 == 0);

    	Point[] gemShape; 
    	if (rare) {
    		gemShape = makeHexagonPoints();
    		gems.add(new Gem(gemShape, getHexagonGemPosition(row, col), 0, rare));
    	} else {
    		gemShape = makePentagonPoints();
    		gems.add(new Gem(gemShape, getPentagonGemPosition(row, col), 0, rare));
    	}

    	spawnObstacle();
    }

	/**
	 * Spawns a new obstacle on the game board, only when the number of
	 * obstacles does not exceed 10.
	 */
    private void spawnObstacle() { 
    	
    	if (obstacles.size() >= MAX_OBSTACLES) {
    		return;
    	}
    	
    	int row;
    	int col;

    	do {
    		row = 1 + (random.nextInt(14));
    		col = (random.nextInt(14));
    	} while (!isTileValidToSpawn(row, col));

    	Point[] rockShape = {
    			new Point(5, 5),
    			new Point(45, 5),
    			new Point(45, 45),
    			new Point(5, 45)
    	};

    	obstacles.add(new Obstacle(rockShape, getObstaclePosition(row, col), 0));
    }
    
    //======================================= GEMS COORDINATES LOGIC =======================================
    // The following methods make the point corners of regular and rare gems
    
    /**
     * Creates pentagon points for non-rare gems. 
     * @return a Point array of pentagon vertices.
     */
    private Point[] makePentagonPoints() {
    	
    	return new Point[] {
    			new Point(25, 5),
    			new Point(40, 18),
    			new Point(34, 38),
    			new Point(16, 38),
    			new Point(10, 18)
    	};
    }

    /**
     * Creates hexagon points for rare gems.
     * @return a Point array of hexagon vertices. 
     */
    private Point[] makeHexagonPoints() {
    	
    	return new Point[] {
    			new Point(18, 5),
    			new Point(32, 5),
    			new Point(42, 25),
    			new Point(32, 45),
    			new Point(18, 45),
    			new Point(8, 25)
    	};
    }

    //=========================================== CORNER LOGIC ===========================================
    // The following methods let you know the top-left corner coordinate of any tile, 
    // and the top left coordinates of snake, gem and obstacle objects
    
    /**
     * Returns the coordinate of the top left corner of a given tile.
     * @param row of the grid box
     * @param col of the grid box
     * @return a Point that represents the top left corner of the given grid box.
     */
    private Point getTileTopLeft(int row, int col) {
    	
    	int x = 50 + col * 50;
    	int y = row * 50;
    	return new Point(x, y);
    }
    
    /**
     * Returns the coordinate of the top left corner of the snake.
     * @param row of the grid box
     * @param col of the grid box
     * @return a Point that represents the top left corner of the snake.
     */
    private Point getSnakePosition(int row, int col) {
    	
    	Point tile = getTileTopLeft(row, col);
    	return new Point(tile.x + 5, tile.y + 15);
    }
    
    /**
     * Returns the coordinate of the top left corner of the obstacle.
     * @param row of the grid box
     * @param col of the grid box
     * @return a Point that represents the top left corner of the obstacle.
     */
    private Point getObstaclePosition(int row, int col) {
    	
    	Point tile = getTileTopLeft(row, col);
    	return new Point(tile.x + 5, tile.y + 5);
    }
    
    /**
     * Returns the coordinate of the top left corner of the non-rare gem.
     * @param row of the grid box
     * @param col of the grid box
     * @return a Point that represents the top left corner of the gem.
     */
    private Point getPentagonGemPosition(int row, int col) {
    	
    	Point tile = getTileTopLeft(row, col);
    	return new Point(tile.x + 10, tile.y + 8);
    }
    
    /**
     * Returns the coordinate of the top left corner of the rare gem.
     * @param row of the grid box
     * @param col of the grid box
     * @return a Point that represents the top left corner of the gem.
     */
    private Point getHexagonGemPosition(int row, int col) {
    	
    	Point tile = getTileTopLeft(row, col);
    	return new Point(tile.x + 8, tile.y + 5);
    }
    
    //=========================================== POSITION LOGIC ===========================================
    // the following methods let you know if any particular object - gem, snake or obstacle is on a particular
    // tile or not. And the last method check weather something (like a gem or obstacle) spawn on a tile or not
    // based on the fact that something already exists there or not.
    
    /**
     * Checks if the snake is on a specific tile, given by the row and column.
     * @param row of the tile
     * @param col of the tile
     * @return a boolean that indicates whether the snake is on the given tile.
     */
    private boolean isSnakeOnTile(int row, int col) {
    	
    	Point p = getSnakePosition(row, col);
    	return snake.isOnTile(p);
    }
    
    /**
     * Checks if the gem is on a specific tile, given by the row and column.
     * @param row of the tile
     * @param col of the tile
     * @return a boolean that indicates whether the gem is on the given tile.
     */
    private boolean isGemOnTile(int row, int col) {
    	
    	Point pentagonPos = getPentagonGemPosition(row, col);
    	Point hexagonPos = getHexagonGemPosition(row, col);

    	for (int i = 0; i < gems.size(); i++) {
    		if (gems.get(i).isOnTile(pentagonPos) || gems.get(i).isOnTile(hexagonPos)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Checks if the obstacle is on a specific tile, given by the row and column.
     * @param row of the tile
     * @param col of the tile
     * @return a boolean that indicates whether the obstacle is on the given tile.
     */
    private boolean isObstacleOnTile(int row, int col) {
    	
    	Point p = getObstaclePosition(row, col);

    	for (int i = 0; i < obstacles.size(); i++) {
    		if (obstacles.get(i).isOnTile(p)) {
    			return true;
    		}
    	}

    	return false;
    }
      
    /**
     * Based on the three methods above, this method checks whether a tile
     * is empty to spawn a gem or obstacle.
     * @param row of the tile
     * @param col of the tile
     * @return a boolean that indicates whether a tile is currently empty.
     */
    private boolean isTileValidToSpawn(int row, int col) {
    	
    	return !isSnakeOnTile(row, col)
    			&& !isGemOnTile(row, col)
    			&& !isObstacleOnTile(row, col);
    }

    //=========================================== COLLISION LOGIC ===========================================
    // The following methods if the snake collides with any gem (points collected), wall (live lost), 
    // obstacle (live lost). And the last method updates the game frames every 250 milliseconds.

    /**
     * Checks if the snake collides with the wall.
     * @return a boolean that indicates whether the snake has collided with
     * the wall.
     */
    private boolean checkWallCollision() {
    	
    	double x = snake.position.x;
    	double y = snake.position.y;

    	if (x < 55 || x > 705 || y < 65 || y > 715) {
    		lives--;
    		resetSnakePosition();

    		if (lives <= 0) {
    			gameOver = true;
    		}
    		return true;
    	}
    	return false;
    }

    /**
     * Checks if the snake collides with a gem.
     * @return a boolean that indicates whether the snake has collided with
     * a gem.
     */
    private void checkGemCollisions() {
    	
        for (int i = 0; i < gems.size(); i++) {
            Gem gem = gems.get(i);

            if (snake.collidesWith(gem)) {
                score += gem.getPointsValue();
                gemsCollected++;
                gems.remove(i);
                spawnNewGem();
                return;
            }
        }
    }
    
    /**
     * Checks if the snake collides with an obstacle.
     * @return a boolean that indicates whether the snake has collided with
     * an obstacle.
     */
    private boolean checkObstacleCollisions() {
    	
        for (int i = 0; i < obstacles.size(); i++) {
            if (snake.collidesWith(obstacles.get(i))) {
                lives--;
                resetSnakePosition();

                if (lives <= 0) {
                    gameOver = true;
                }
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Uses the three methods above to check snake collision altogether. If
     * the game is over, nothing happens. The snake will move and then check
     * all three possible collisions to determine the next course of action.
     */
    private void updateGame() {
    	
    	if (gameOver) {
    		return;
    	}

    	snake.move();

    	if (checkWallCollision()) {
    		return;
    	}
    	checkGemCollisions();

    	if (checkObstacleCollisions()) {
    		return;
    	}
    }
    
    //=========================================== PAINT LOGIC ===========================================
    // The next methods paint the background board, Statistics board, End Screen and every other element
    
    /**
     * Inner class that represents the game board, including the wall
     * and tiles.
     */
	private class GameBoard {
		
		private Color wall;
		private Color square1;
		private Color square2;
		
		/**
		 * Initializes the instance variables of the inner class.
		 * @param wall - color of the wall
		 * @param square1 - first tile color
		 * @param square2 - second tile color
		 */
		public GameBoard(Color wall, Color square1, Color square2) {
			this.wall = wall;
			this.square1 = square1;
			this.square2 = square2;
		}
		
		/**
		 * Creates the wall and alternating grid pattern, using the 
		 * color instance variables.
		 * @param brush
		 */
		public void paint(Graphics brush) {
			
			brush.setColor(wall);
	    	brush.fillRect(0, 0, width, height); 
	    	
	    	brush.setColor(square1);
	    	for(int row = 1; row <= 14; row++) {
	    		if(row % 2 == 1) {
	    			for(int col = 0; col < 14; col += 2) {
	    				brush.fillRect(50 + col * 50, row * 50, 50, 50);
	    			}
	    		}
	    		if(row % 2 == 0) {
	    			for(int col = 1; col < 14; col += 2) {
	    				brush.fillRect(50 + col * 50, row * 50, 50, 50);
	    			}
	    		}
	    	}
	    	brush.setColor(square2);
	    	
	    	for(int row = 1; row <= 14; row++) {
	    		if(row % 2 == 0) {
	    			for(int col = 0; col < 14; col += 2) {
	    				brush.fillRect(50 + col * 50, row * 50, 50, 50);
	    			}
	    		}
	    		if(row % 2 == 1) {
	    			for(int col = 1; col < 14; col += 2) {
	    				brush.fillRect(50 + col * 50, row * 50, 50, 50);
	    			}
	    		}
	    	}
		}
	}
	
	/**
	 * Creates all game elements, including the wall, tiles, score, lives,
	 * obstacles, gems, and snake.
	 */
	@Override
    public void paint(Graphics brush) {
    	
		board.paint(brush);
    	
    	if (gameOver) {
            drawEndScreen(brush);
            return;
        }

        drawStats(brush);
        //lambda expressions
        obstacles.forEach(o -> o.paint(brush));
        gems.forEach(g -> g.paint(brush));
        snake.paint(brush);
    }

	/**
	 * Creates game statistics, including the number of lives (indicated by 
	 * hearts) and score (indicated by number of gems collected).
	 * @param brush
	 */
    private void drawStats(Graphics brush) {
    	
        ScoreDisplay scoreDisplay = new ScoreDisplay();
        HeartDisplay heartDisplay = new HeartDisplay();

        scoreDisplay.paint(brush);
        heartDisplay.paint(brush);
    }
    
    //=========================================== STATS LOGIC ===========================================
    // The next methods display the stats (coins earned and lives left) at all times. 
    
   /**
    * Inner class that represents the score display, which is the point value
    * of gems collected (rare gems are 3 times as valuable).
    */
    private class ScoreDisplay {
    	
    	/**
    	 * Draws the score display, which says "Score: " and the value in white font.
    	 * @param brush
    	 */
        public void paint(Graphics brush) {
            brush.setColor(Color.WHITE);
            brush.setFont(new Font("Arial", Font.BOLD, 20));
            brush.drawString("Score: " + score, 20, 35);
        }
    } 

    /**
     * Inner class that represents the heart display, which is the number
     * of lives left.
     */
    private class HeartDisplay {
    	
    	/**
    	 * Draws the heart display, which shows hearts according to the 
    	 * number of lives left.
    	 * @param brush
    	 */
        public void paint(Graphics brush) {
        	
            for (int i = 0; i < lives; i++) {
                drawHeart(brush, 650 + i * 40, 20);
            }
        }
        
        /**
         * Draws each individual heart, using two circles and a triangle.
         * @param brush
         * @param x coordinate of the center of the first circle.
         * @param y coordinate of the center of the first circle.
         */
        private void drawHeart(Graphics brush, int x, int y) {
        	
            brush.setColor(Color.RED);
            brush.fillOval(x, y, 15, 15);
            brush.fillOval(x + 10, y, 15, 15);

            int[] xPoints = {x - 2, x + 27, x + 12};
            int[] yPoints = {y + 10, y + 10, y + 30};

            brush.fillPolygon(xPoints, yPoints, 3);
        }
    }
    
    //=========================================== END GAME LOGIC ===========================================
    
    /**
     * Creates the black end screen when the snake loses all its lives, 
     * adding text to show that the game is over, the score, and how to restart
     * @param brush
     */
    private void drawEndScreen(Graphics brush) {
    	
        brush.setColor(Color.BLACK);
        brush.fillRect(0, 0, width, height);

        brush.setColor(Color.WHITE);
        brush.setFont(new Font("Arial", Font.BOLD, 32));
        brush.drawString("Game Over", 300, 220);

        brush.setFont(new Font("Arial", Font.PLAIN, 24));
        brush.drawString("Final Score: " + score, 310, 280);
        brush.drawString("Press R to Restart", 285, 330);
    }
    
    /**
     * When the game restarts, this method ensures that the snake starts
     * on the bottom left corner tile. 
     */
    private void resetSnakePosition() {
    	
    	Point start = getSnakePosition(14, 0);
    	snake.position.x = start.x;
    	snake.position.y = start.y;
    	snake.rotation = 0;
    	snake.direction = Direction.NONE;
    }
    
    /**
     * This method resets all the game statistics when the user chooses to
     * restart, clearing gems and obstacles, setting score to 0, lives to 3, 
     * calling the method above to reset the snake position, and spawning a 
     * new gem to start the game.
     */
    protected void restartGame() {
    	
    	score = 0;
    	lives = 3;
    	gemsCollected = 0;
    	gameOver = false;

    	gems.clear();
    	obstacles.clear();

    	resetSnakePosition();
    	spawnNewGem();
    }
    
    //=========================================== MAIN METHOD ===========================================
    
    public static void main(String[] args) {
        new SnakeGemGame();
    }
}