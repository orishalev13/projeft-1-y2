import biuoop.GUI;
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.Random;

public class AbstractArtDrawing {

    public void drawRandomLines() {
        Random rand = new Random();

        // creat the window
        GUI gui = new GUI("Abstract Art", 400, 300);
        DrawSurface d = gui.getDrawSurface();

        // array for the line
        Line[] lines = new Line[10];

        //creat new 10 line
        for (int i = 0; i < 10; i++) {
            int x1 = rand.nextInt(400);
            int y1 = rand.nextInt(300);
            int x2 = rand.nextInt(400);
            int y2 = rand.nextInt(300);

            lines[i] = new Line(x1, y1, x2, y2);
        }

        //drow the line black
        d.setColor(Color.BLACK);
        for (Line line : lines) {
            d.drawLine((int)line.start().getX(),
                    (int)line.start().getY(),
                    (int)line.end().getX(),
                    (int)line.end().getY());
        }

        //deow the point blue
        d.setColor(Color.BLUE);
        for (Line line : lines) {
            Point middle = line.middle();
            d.fillCircle((int)middle.getX(), (int)middle.getY(), 3);
        }

        // drow the point chituch red
        d.setColor(Color.RED);
        for (int i = 0; i < lines.length; i++) {
            for (int j = i + 1; j < lines.length; j++) {
                Point intersection = lines[i].intersectionWith(lines[j]);
                if (intersection != null) {
                    d.fillCircle((int)intersection.getX(),
                            (int)intersection.getY(), 3);
                }
            }
        }

        gui.show(d);
    }

    public static void main(String[] args) {
        AbstractArtDrawing art = new AbstractArtDrawing();
        art.drawRandomLines();
    }
}