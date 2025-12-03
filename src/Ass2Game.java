/**
 * Ass2Game - Assignment 2 main class.
 * This class creates and runs the Arkanoid game.
 *
 * @author [Your Name]
 * @version 1.0
 */
public class Ass2Game {

    /**
     * Main method - entry point of the program.
     * Creates a new game, initializes it, and runs it.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
