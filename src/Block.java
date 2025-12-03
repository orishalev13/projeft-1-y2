import biuoop.DrawSurface;
import java.awt.Color;
public class  Block implements Collidable , Sprite  {
    private Rectangle rect;
    private Color color;

    public Block(Rectangle rect, Color color) {
        this.rect = rect;
        this.color=color;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // Check if the collision is on a vertical edge (left or right)
        boolean hitLeft = Math.abs(collisionPoint.getX() - rect.getUpperLeft().getX()) < 0.0001;
        boolean hitRight = Math.abs(collisionPoint.getX() - (rect.getUpperLeft().getX() + rect.getWidth())) < 0.0001;

        // Check if the collision is on a horizontal edge (top or bottom)
        boolean hitTop = Math.abs(collisionPoint.getY() - rect.getUpperLeft().getY()) < 0.0001;
        boolean hitBottom = Math.abs(collisionPoint.getY() - (rect.getUpperLeft().getY() + rect.getHeight())) < 0.0001;

        // If the collision occurs on a vertical edge → invert dx
        if (hitLeft || hitRight) {
            dx = -dx;
        }

        // If the collision occurs on a horizontal edge → invert dy
        if (hitTop || hitBottom) {
            dy = -dy;
        }

        return new Velocity(dx, dy);
    }
    public void drawOn(DrawSurface surface) {
        Point upperLeft = this.rect.getUpperLeft();
        int x = (int) upperLeft.getX();
        int y = (int) upperLeft.getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();

        // מילוי הבלוק בצבע
        surface.setColor(this.color);
        surface.fillRectangle(x, y, width, height);

        // ציור מסגרת שחורה סביב הבלוק (אופציונלי, אבל נראה טוב)
        surface.setColor(Color.BLACK);
        surface.drawRectangle(x, y, width, height);
    }

    @Override
    public void timePassed() {}
        public void addToGame(Game g) {
            g.addSprite(this);      // Add as Sprite (for drawing)
            g.addCollidable(this);  // Add as Collidable (for collisions)
        }


    }

