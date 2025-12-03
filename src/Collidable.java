public interface Collidable {
    Rectangle getCollisionRectangle();
    Velocity hit(Point collisionPoint, Velocity currentVelocity);
}
