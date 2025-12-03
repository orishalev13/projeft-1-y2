import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;

public class TestCollisionDetection {

    public static void main(String[] args) {
        // 1. יצירת GUI
        GUI gui = new GUI("Collision Test", 800, 600);
        Sleeper sleeper = new Sleeper();

        // 2. יצירת GameEnvironment
        GameEnvironment environment = new GameEnvironment();

        // 3. יצירת בלוקים בגבולות המסך (רחבים/גבוהים)
        // בלוק עליון
        Block topBorder = new Block(new Rectangle(new Point(0, 0), 800, 20), Color.GRAY);

        // בלוק תחתון
        Block bottomBorder = new Block(new Rectangle(new Point(0, 580), 800, 20), Color.GRAY);

        // בלוק שמאלי
        Block leftBorder = new Block(new Rectangle(new Point(0, 0), 20, 600), Color.GRAY);

        // בלוק ימני
        Block rightBorder = new Block(new Rectangle(new Point(780, 0), 20, 600), Color.GRAY);

        // הוספה ל-GameEnvironment
        environment.addCollidable(topBorder);
        environment.addCollidable(bottomBorder);
        environment.addCollidable(leftBorder);
        environment.addCollidable(rightBorder);

        // 4. יצירת כמה בלוקים באמצע המסך
        Block block1 = new Block(new Rectangle(new Point(100, 100), 80, 30), Color.RED);
        Block block2 = new Block(new Rectangle(new Point(300, 200), 60, 40), Color.BLUE);
        Block block3 = new Block(new Rectangle(new Point(500, 150), 100, 25), Color.GREEN);
        Block block4 = new Block(new Rectangle(new Point(200, 400), 70, 50), Color.YELLOW);

        environment.addCollidable(block1);
        environment.addCollidable(block2);
        environment.addCollidable(block3);
        environment.addCollidable(block4);

        // 5. יצירת כדור
        Point ballCenter = new Point(400, 300);
        Ball ball = new Ball(ballCenter, 5, Color.WHITE);
        ball.setVelocity(4, -3);  // זז ימינה ומעלה
        ball.setGameEnvironment(environment);

        // 6. לולאת אנימציה
        while (true) {
            long startTime = System.currentTimeMillis();

            // קבלת משטח ציור
            DrawSurface d = gui.getDrawSurface();

            // רקע שחור
            d.setColor(Color.BLACK);
            d.fillRectangle(0, 0, 800, 600);

            // ציור כל הבלוקים
            topBorder.drawOn(d);
            bottomBorder.drawOn(d);
            leftBorder.drawOn(d);
            rightBorder.drawOn(d);

            block1.drawOn(d);
            block2.drawOn(d);
            block3.drawOn(d);
            block4.drawOn(d);

            // ציור הכדור
            ball.drawOn(d);

            // הצגה על המסך
            gui.show(d);

            // הזזת הכדור
            ball.moveOneStep();

            // שינה קצרה (60 FPS)
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = 16 - usedTime;  // ~60 FPS
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}