public class Point {  private double x;
    private double y;

    // Constructor
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }


    // Distance -- return the distance of this point to the other point
    public double distance(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    // equals -- return true if the points are equal, false otherwise
    public boolean equals(Point other) {
        return (this.x == other.x) && (this.y == other.y);
    }

    // Return the x value of this point
    public double getX() {
        return this.x;
    }

    // Return the y value of this point
    public double getY() {
        return this.y;
    }
}
