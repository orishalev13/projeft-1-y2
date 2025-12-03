import java.util.List;
import java.util.ArrayList;

public class GameEnvironment {
    private ArrayList<Collidable> collidables;
    // add the given collidable to the environment.

    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    // add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    public CollisionInfo getClosestCollision(Line trajectory) {

        Point closestPoint = null;
        Collidable closestCollidable = null;
        double minDistance = Double.POSITIVE_INFINITY;

        // Go over all collidables
        for (Collidable c : collidables) {
            Rectangle rect = c.getCollisionRectangle();

            // Get all intersection points with this rectangle
            List<Point> points = rect.intersectionPoints(trajectory);

            // Check each intersection point
            for (Point p : points) {
                double distance = p.distance(trajectory.start());

                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = p;
                    closestCollidable = c;
                }
            }
        }

        // If nothing was found
        if (closestPoint == null) {
            return null;
        }

        return new CollisionInfo(closestPoint, closestCollidable);
    }
}

