import java.util.ArrayList;
import java.util.List;

public class Rectangle {
    private Point  upperLeft;
    double width; double height;

    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;

    }

    public List<Point> intersectionPoints(Line line){
        List<Point> intersections = new ArrayList<>();

        // Get the four edges of the rectangle
        Line[] edges = getRectangleEdges();

        // Check intersection with each edge
        for (Line edge : edges) {
            Point intersection = edge.intersectionWith(line);
            if (intersection != null) {
                intersections.add(intersection);
            }
        }

        return intersections;
    }
    private Line[] getRectangleEdges() {
        // Top-left corner
        double x = upperLeft.getX();
        double y = upperLeft.getY();
        // Four corners of the rectangle
        Point topLeft = new Point(x, y);
        Point topRight = new Point(x + width, y);
        Point bottomLeft = new Point(x, y + height);
        Point bottomRight = new Point(x + width, y + height);

        // Four edges
        Line topEdge = new Line(topLeft, topRight);
        Line bottomEdge = new Line(bottomLeft, bottomRight);
        Line leftEdge = new Line(topLeft, bottomLeft);
        Line rightEdge = new Line(topRight, bottomRight);

        return new Line[]{topEdge, bottomEdge, leftEdge, rightEdge};
    }
        public double getWidth(){
        return this.width;
    }
    public double getHeight(){
      return this.height;
    }
    public Point getUpperLeft(){
        return this.upperLeft;
    }
}
