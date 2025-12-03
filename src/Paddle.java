import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;

/**
 * Paddle class - the player-controlled object in the game.
 * The paddle is controlled by keyboard (left/right arrows).
 * It implements both Sprite and Collidable.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rectangle;
    private Color color;
    private int speed;           // pixels to move per frame
    private int leftBoundary;    // left boundary (can't go past this)
    private int rightBoundary;   // right boundary (can't go past this)

    /**
     * Constructor.
     * @param keyboard the keyboard sensor for reading key presses
     * @param rectangle the paddle's shape and position
     * @param color the paddle's color
     * @param speed movement speed in pixels per frame
     */
    public Paddle(biuoop.KeyboardSensor keyboard, Rectangle rectangle,
                  Color color, int speed) {
        this.keyboard = keyboard;
        this.rectangle = rectangle;
        this.color = color;
        this.speed = speed;

        // Default boundaries (can be changed with setBoundaries)
        this.leftBoundary = 20;      // accounting for left border block
        this.rightBoundary = 780;    // accounting for right border block
    }

    /**
     * Set the movement boundaries for the paddle.
     * The paddle cannot move past these boundaries.
     * @param left left boundary x-coordinate
     * @param right right boundary x-coordinate
     */
    public void setBoundaries(int left, int right) {
        this.leftBoundary = left;
        this.rightBoundary = right;
    }

    /**
     * Move the paddle left.
     * Stops at the left boundary.
     */
    public void moveLeft() {
        Point upperLeft = this.rectangle.getUpperLeft();
        double newX = upperLeft.getX() - this.speed;

        // Check if we would go past the left boundary
        if (newX < this.leftBoundary) {
            newX = this.leftBoundary;
        }

        // Create new rectangle with updated position
        this.rectangle = new Rectangle(
                new Point(newX, upperLeft.getY()),
                this.rectangle.getWidth(),
                this.rectangle.getHeight()
        );
    }

    /**
     * Move the paddle right.
     * Stops at the right boundary.
     */
    public void moveRight() {
        Point upperLeft = this.rectangle.getUpperLeft();
        double newX = upperLeft.getX() + this.speed;

        // Check if we would go past the right boundary
        // (considering the paddle's width)
        if (newX + this.rectangle.getWidth() > this.rightBoundary) {
            newX = this.rightBoundary - this.rectangle.getWidth();
        }

        // Create new rectangle with updated position
        this.rectangle = new Rectangle(
                new Point(newX, upperLeft.getY()),
                this.rectangle.getWidth(),
                this.rectangle.getHeight()
        );
    }

    // ==========================================
    // Sprite interface implementation
    // ==========================================

    /**
     * Time passed - check keyboard and move accordingly.
     */
    @Override
    public void timePassed() {
        // Check if left arrow is pressed
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }

        // Check if right arrow is pressed
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draw the paddle on the given surface.
     * @param d the drawing surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        Point upperLeft = this.rectangle.getUpperLeft();
        int x = (int) upperLeft.getX();
        int y = (int) upperLeft.getY();
        int width = (int) this.rectangle.getWidth();
        int height = (int) this.rectangle.getHeight();

        // Fill the paddle with its color
        d.setColor(this.color);
        d.fillRectangle(x, y, width, height);

        // Draw black border
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, width, height);
    }

    // ==========================================
    // Collidable interface implementation
    // ==========================================

    /**
     * Get the collision rectangle.
     * @return the rectangle shape of this paddle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Handle a hit on the paddle.
     * The paddle is divided into 5 regions, each affecting the ball differently.
     * @param collisionPoint the point where the collision occurred
     * @param currentVelocity the current velocity of the ball
     * @return the new velocity after the hit
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        // Divide paddle into 5 equal regions
        Point upperLeft = this.rectangle.getUpperLeft();
        double paddleLeft = upperLeft.getX();
        double paddleWidth = this.rectangle.getWidth();
        double regionWidth = paddleWidth / 5.0;

        // Determine which region (1-5) was hit
        double hitX = collisionPoint.getX();
        int region = (int) ((hitX - paddleLeft) / regionWidth) + 1;

        // Clamp region to valid range [1, 5]
        if (region < 1) {
            region = 1;
        }
        if (region > 5) {
            region = 5;
        }

        // Calculate current speed (magnitude of velocity)
        double currentSpeed = Math.sqrt(
                currentVelocity.getDx() * currentVelocity.getDx() +
                        currentVelocity.getDy() * currentVelocity.getDy()
        );

        // Determine new velocity based on region
        Velocity newVelocity;

        switch (region) {
            case 1:
                // Left-most region: bounce at 300 degrees (-60 from vertical)
                newVelocity = Velocity.fromAngleAndSpeed(300, currentSpeed);
                break;

            case 2:
                // Second region: bounce at 330 degrees (-30 from vertical)
                newVelocity = Velocity.fromAngleAndSpeed(330, currentSpeed);
                break;

            case 3:
                // Middle region: straight up (only reverse vertical direction)
                newVelocity = new Velocity(
                        currentVelocity.getDx(),
                        -Math.abs(currentVelocity.getDy())  // ensure upward
                );
                break;

            case 4:
                // Fourth region: bounce at 30 degrees
                newVelocity = Velocity.fromAngleAndSpeed(30, currentSpeed);
                break;

            case 5:
                // Right-most region: bounce at 60 degrees
                newVelocity = Velocity.fromAngleAndSpeed(60, currentSpeed);
                break;

            default:
                // Shouldn't happen, but default to straight up
                newVelocity = new Velocity(
                        currentVelocity.getDx(),
                        -Math.abs(currentVelocity.getDy())
                );
        }

        return newVelocity;
    }

    // ==========================================
    // Additional methods
    // ==========================================

    /**
     * Add this paddle to the game.
     * @param g the game
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}