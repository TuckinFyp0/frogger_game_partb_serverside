import java.awt.Rectangle;

public class Miner {
    private int x, y;
    private int width, height;

    public Miner() {
        this.x = 0;
        this.y = 0;
        this.width = 50;
        this.height = 50;
    }

    public Miner(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }
}
