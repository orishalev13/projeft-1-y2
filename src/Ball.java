import java.awt.*;
import java.awt.Color;
import biuoop.DrawSurface;

public class Ball {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;

    // Constructors
    public Ball(Point center, int r, Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(0, 0); // Default velocity is 0
    }


    public Ball(int x, int y, int r, Color color) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
    }

    // Accessors
    public int getX() {
        return (int) this.center.getX();
    }

    public int getY() {
        return (int) this.center.getY();
    }

    public int getSize() {
        return this.radius;
    }

    public Color getColor() {
        return this.color;
    }

    // Velocity methods
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    public Velocity getVelocity() {
        return this.velocity;
    }

    // Draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
    }

    // Move one step
    public void moveOneStep() {
        this.center = this.velocity.applyToPoint(this.center);
    }
}
