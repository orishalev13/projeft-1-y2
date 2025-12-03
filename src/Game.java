import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;

/**
 * Game class - manages the game.
 * Holds the sprites and collidables, and runs the animation loop.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;

    /**
     * Constructor.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.sleeper = new Sleeper();
    }

    /**
     * Add a collidable to the game environment.
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add a sprite to the game.
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initialize a new game.
     * Creates the blocks, ball, and paddle, and adds them to the game.
     */
    public void initialize() {
        // Create GUI
        this.gui = new GUI("Arkanoid", 800, 600);

        // Create border blocks (so ball doesn't fall off screen)
        createBorders();

        // Create the blocks pattern
        createBlocks();

        // Create paddle
        createPaddle();

        // Create balls
        createBalls();
    }

    /**
     * Create the border blocks around the screen.
     */
    private void createBorders() {
        // Top border
        Block topBorder = new Block(
                new Rectangle(new Point(0, 20), 800, 20),
                Color.GRAY
        );
        topBorder.addToGame(this);

        // Bottom border
        Block bottomBorder = new Block(
                new Rectangle(new Point(0, 580), 800, 20),
                Color.GRAY
        );
        bottomBorder.addToGame(this);

        // Left border
        Block leftBorder = new Block(
                new Rectangle(new Point(0, 20), 20, 580),
                Color.GRAY
        );
        leftBorder.addToGame(this);

        // Right border
        Block rightBorder = new Block(
                new Rectangle(new Point(780, 20), 20, 580),
                Color.GRAY
        );
        rightBorder.addToGame(this);
    }

    /**
     * Create the pattern of blocks.
     */
    private void createBlocks() {
        // Colors for each row
        Color[] colors = {
                Color.RED,
                Color.ORANGE,
                Color.YELLOW,
                Color.GREEN,
                new Color(0, 150, 255),  // Light blue
                Color.PINK
        };

        int blockWidth = 50;
        int blockHeight = 25;
        int startX = 275;  // Center the blocks
        int startY = 100;

        // Create 6 rows of blocks
        for (int row = 0; row < 6; row++) {
            int numBlocksInRow = 12 - row;  // Each row has one less block
            int rowStartX = startX + (row * blockWidth / 2);

            for (int col = 0; col < numBlocksInRow; col++) {
                Rectangle rect = new Rectangle(
                        new Point(rowStartX + col * blockWidth, startY + row * blockHeight),
                        blockWidth,
                        blockHeight
                );

                Block block = new Block(rect, colors[row]);
                block.addToGame(this);
            }
        }
    }

    /**
     * Create the paddle.
     */
    private void createPaddle() {
        biuoop.KeyboardSensor keyboard = this.gui.getKeyboardSensor();

        Rectangle paddleRect = new Rectangle(
                new Point(350, 550),  // Centered, near bottom
                100,                   // Width
                20                     // Height
        );

        Paddle paddle = new Paddle(keyboard, paddleRect, Color.ORANGE, 7);
        paddle.setBoundaries(20, 780);  // Can't go past borders
        paddle.addToGame(this);
    }

    /**
     * Create the balls.
     */
    private void createBalls() {
        // First ball
        Ball ball1 = new Ball(400, 400, 5, Color.WHITE);
        ball1.setVelocity(4, -4);
        ball1.setGameEnvironment(this.environment);
        ball1.addToGame(this);

        // Second ball
        Ball ball2 = new Ball(450, 350, 5, Color.CYAN);
        ball2.setVelocity(-3, -5);
        ball2.setGameEnvironment(this.environment);
        ball2.addToGame(this);
    }

    /**
     * Run the game - start the animation loop.
     */
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis();  // timing

            DrawSurface d = this.gui.getDrawSurface();

            // Draw background
            d.setColor(Color.BLACK);
            d.fillRectangle(0, 0, 800, 600);

            // Draw all sprites
            this.sprites.drawAllOn(d);
            this.gui.show(d);

            // Notify all sprites that time passed
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}