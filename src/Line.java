public class Line {private Point start;
    private Point end;

    // Constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    // Return the length of the line
    public double length() {
        return this.start.distance(this.end);
    }

    // Returns the middle point of the line
    public Point middle() {
        double midX = (this.start.getX() + this.end.getX()) / 2;
        double midY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(midX, midY);
    }

    // Returns the start point of the line
    public Point start() {
        return this.start;
    }

    // Returns the end point of the line
    public Point end() {
        return this.end;
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
        return (this.intersectionWith(other) != null);
    }

    // Returns the intersection point if the lines intersect, and null otherwise
    public Point intersectionWith(Line other) {
        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.end.getX();
        double y2 = this.end.getY();

        double x3 = other.start.getX();
        double y3 = other.start.getY();
        double x4 = other.end.getX();
        double y4 = other.end.getY();

        // Calculate the denominator
        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        // If denominator is 0, lines are parallel
        if (denominator == 0) {
            return null;

        }

        // Calculate intersection point using parametric equations
        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
        double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denominator;

        // Check if intersection point is within both line segments
        if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
            double intersectX = x1 + t * (x2 - x1);
            double intersectY = y1 + t * (y2 - y1);
            return new Point(intersectX, intersectY);
        }

        return null;
    }

    // equals -- return true if the lines are equal, false otherwise
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start));
    }
}
