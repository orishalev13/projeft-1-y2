public class Velocity {
    private double dx;
    private double dy;

    // Constructor
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }
    // Create velocity from angle and speed
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // Convert angle to radians (assuming angle 0 is up)
        double angleInRadians = Math.toRadians(angle - 90);
        double dx = speed * Math.cos(angleInRadians);
        double dy = speed * Math.sin(angleInRadians);
        return new Velocity(dx, dy);
    }

    // Getters
    public double getDx() {
        return this.dx;
    }
    public double getDy() {
        return this.dy;
    }
}