import java.awt.Color;

/**
 * Represents a celestial body (like a planet or star) in the Massive Motion
 * simulation.
 * Each body has a position, velocity, size, and color.
 */
public class CelestialBody {
    private int x, y, vx, vy, size;
    private Color color;

    public CelestialBody(int x, int y, int vx, int vy, int size, Color color) {
        this.x = x; // The x-coordinate of the celestial body.
        this.y = y; // The y-coordinate of the celestial body.
        this.vx = vx; // Velocity of x
        this.vy = vy; // Velocity of y
        this.size = size;
        this.color = color;
    }

    public void move() {
        x += vx;
        y += vy;
    }

    public boolean isOffScreen(int width, int height) {
        return x < 0 || x > width || y < 0 || y > height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int c) {
        x = c;
    }

    public void setY(int p) {
        y = p;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }
}
