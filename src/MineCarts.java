public class MineCarts implements Runnable {

    private int x, y;
    private int width, height;
    private int speed;
    private int direction;
    private boolean moving;

    public MineCarts(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = 200;
        this.direction = 1;
        this.moving = false;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setSpeed(int speed) { this.speed = speed; }
    public void setDirection(int direction) { this.direction = direction; }

    @Override
    public void run() {
        while (moving) {
            x += direction;

            if (direction > 0 && x >= 600) {
                x = -width;
            } else if (direction < 0 && x + width <= 0) {
                x = 600;
            }

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void start() {
        if (!moving) {
            moving = true;
            new Thread(this).start();
        }
    }

    public void stop() {
        moving = false;
    }
}

