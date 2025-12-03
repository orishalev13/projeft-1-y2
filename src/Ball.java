import java.awt.*;
import java.awt.Color;
import biuoop.DrawSurface;

public class Ball  implements Sprite{
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;

    // Constructors
    public Ball(Point center, int r, Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        //this.velocity = new Velocity(0, 0); // Default velocity is 0
        //this.gameEnvironment=gameEnvironment;
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
    public void setGameEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment;
    }
    // Draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    // Move one step
    public void moveOneStep(int width, int height) {
        this.center = this.velocity.applyToPoint(this.center);

        double x = this.center.getX();
        double y = this.center.getY();
        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();

        if (x - this.radius <= 0 || x + this.radius >= width) {
            this.velocity = new Velocity(-dx, dy);
        }

        if (y - this.radius <= 0 || y + this.radius >= height) {
            this.velocity = new Velocity(dx, -dy);
        }
    }
    private Point moveToAlmostHitPoint(Point collisionPoint) {
        // נחזור מעט אחורה בכיוון ההפוך למהירות
        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();

        // נזוז קצת אחורה (למשל 0.01 פיקסלים)
        double epsilon = 0.01;

        // חישוב הכיוון ההפוך
        double length = Math.sqrt(dx * dx + dy * dy);
        double backDx = -(dx / length) * epsilon;
        double backDy = -(dy / length) * epsilon;

        return new Point(
                collisionPoint.getX() + backDx,
                collisionPoint.getY() + backDy
        );
    }
    public void moveOneStep() {

            Point start = this.center;
            Point end = this.velocity.applyToPoint(start);
            Line trajectory = new Line(start, end);

            // שלב 2: בדיקה אם יש התנגשות במסלול
            CollisionInfo collision = this.gameEnvironment.getClosestCollision(trajectory);
            if (collision == null)
                    this.center = end;
         else {
            // יש התנגשות!

            // שלב 3.1: זוז כמעט עד נקודת ההתנגשות
            Point collisionPoint = collision.collisionPoint();

            // נזוז לנקודה קצת לפני נקודת ההתנגשות
            // כדי שהכדור לא "ייתקע" בתוך העצם
            Point almostCollisionPoint = moveToAlmostHitPoint(collisionPoint);
            this.center = almostCollisionPoint;

            // שלב 3.2: הודע לעצם שנפגע
            Collidable hitObject = collision.collisionObject();

            // שלב 3.3: קבל מהירות חדשה מהעצם שנפגע
            this.velocity = hitObject.hit(collisionPoint, this.velocity);
        }

    }
    public void moveOneStepInFrame(int minX, int minY, int maxX, int maxY) {

        this.center = this.velocity.applyToPoint(this.center);

        double x = this.center.getX();
        double y = this.center.getY();
        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();


        if (x - this.radius <= minX || x + this.radius >= maxX) {
            this.velocity = new Velocity(-dx, dy);
        }


        if (y - this.radius <= minY || y + this.radius >= maxY) {
            this.velocity = new Velocity(dx, -dy);
        }


        x = Math.max(minX + this.radius, Math.min(maxX - this.radius, x));
        y = Math.max(minY + this.radius, Math.min(maxY - this.radius, y));
        this.center = new Point(x, y);
    }
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}
