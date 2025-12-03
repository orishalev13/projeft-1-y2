import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * SpriteCollection holds a collection of sprites.
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Constructor.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * Add a sprite to the collection.
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        // Make a copy of the sprites list to avoid concurrent modification
        List<Sprite> spritesCopy = new ArrayList<Sprite>(this.sprites);

        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }

    /**
     * Call drawOn(d) on all sprites.
     * @param d the drawing surface
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}