import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;
import java.util.Scanner;

public class MultipleFramesBouncingBallsAnimation {
    public static void main(String[] args){
        System.out.print("\nEnter sizes: ");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        args = input.trim().split("\\s+");
        int numBalls = args.length;
        int half = numBalls / 2;
        int frame1MinX = 50, frame1MinY = 50;
        int frame1MaxX = 500, frame1MaxY = 500;
        Color frame1Color = Color.GRAY;
        int frame2MinX = 450, frame2MinY = 450;
        int frame2MaxX = 600, frame2MaxY = 600;
        Color frame2Color = Color.YELLOW;
        int windowWidth = 600;
        int windowHeight = 600;
        GUI gui = new GUI("Multiple Frames Bouncing Balls", windowWidth, windowHeight);
        Random random = new Random();
        Ball[] balls = new Ball[numBalls];
        System.out.println("Creating " + numBalls + " balls:");
        System.out.println("First " + half + " balls in gray frame");
        System.out.println("Last " + (numBalls - half) + " balls in yellow frame");
        for (int i = 0; i < numBalls; i++) {
            int size = Integer.parseInt(args[i]);
            int minX, minY, maxX, maxY; String frameName;
            if (i < half) {  // חצי ראשון - מסגרת 1 (אפורה)
                minX = frame1MinX;
                minY = frame1MinY;
                maxX = frame1MaxX;
                maxY = frame1MaxY;
                frameName = "gray";
            } else {
                // חצי שני - מסגרת 2 (צהובה)
                minX = frame2MinX;
                minY = frame2MinY;
                maxX = frame2MaxX;
                maxY = frame2MaxY;
               frameName = "yellow";
            }

            // מיקום אקראי בתוך המסגרת
            int frameWidth = maxX - minX;
            int frameHeight = maxY - minY;

            // וודא שהכדור לא גדול מדי למסגרת
            if (size * 2 >= frameWidth || size * 2 >= frameHeight) {
                System.out.println("Ball " + (i+1) + " (size " + size + ") is too big for " +
                        frameName + " frame! Reducing to size 10.");
                size = 10;
            }

            int x = random.nextInt(frameWidth - size * 2) + minX + size;
            int y = random.nextInt(frameHeight - size * 2) + minY + size;

            // צבע אקראי
            Color color = new Color(random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256));

            // יצירת הכדור
            balls[i] = new Ball(x, y, size, color);

            // מהירות לפי גודל
            double speed;
            if (size >= 50) {
                speed = 2;
            } else if (size >= 30) {
                speed = 4;
            } else if (size >= 20) {
                speed = 6;
            } else if (size >= 10) {
                speed = 8;
            } else {
                speed = 10;
            }

            double angle = random.nextDouble() * 360;
            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
            balls[i].setVelocity(v);

            System.out.println("  Ball " + (i+1) + ": size=" + size +
                    ", frame=" + frameName +
                    ", pos=(" + x + "," + y + ")");
        }

        Sleeper sleeper = new Sleeper();
        System.out.println("\nStarting animation...");

        // לולאת אנימציה
        while (true) {
            DrawSurface d = gui.getDrawSurface();

            // 1. רקע לבן
            d.setColor(Color.WHITE);
            d.fillRectangle(0, 0, windowWidth, windowHeight);

            // 2. צייר מסגרת 1 (אפורה)
            d.setColor(frame1Color);
            d.fillRectangle(frame1MinX, frame1MinY,
                    frame1MaxX - frame1MinX,
                    frame1MaxY - frame1MinY);

            // 3. צייר מסגרת 2 (צהובה)
            d.setColor(frame2Color);
            d.fillRectangle(frame2MinX, frame2MinY,
                    frame2MaxX - frame2MinX,
                    frame2MaxY - frame2MinY);

            // 4. זוז וצייר את כל הכדורים
            for (int i = 0; i < balls.length; i++) {
                // קבע את גבולות המסגרת של הכדור
                int minX, minY, maxX, maxY;

                if (i < half) {
                    // מסגרת 1
                    minX = frame1MinX;
                    minY = frame1MinY;
                    maxX = frame1MaxX;
                    maxY = frame1MaxY;
                } else {
                    // מסגרת 2
                    minX = frame2MinX;
                    minY = frame2MinY;
                    maxX = frame2MaxX;
                    maxY = frame2MaxY;
                }

                // זוז את הכדור עם גבולות המסגרת שלו
                balls[i].moveOneStepInFrame(minX, minY, maxX, maxY);

                // צייר את הכדור
                balls[i].drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}
