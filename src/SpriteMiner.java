import java.awt.Rectangle;

public class SpriteMiner {

	protected int x, y;
	protected int width, height;
	protected String image;
	protected Rectangle r;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		this.r.x = this.x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		this.r.y = this.y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
		this.r.width = this.width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
		this.r.height = this.height;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public Rectangle getRectangle() {
		return this.r;
	}
	
	public SpriteMiner() {
		super();
		this.x = -1;
		this.y = -1;
		this.width = 0;
		this.height = 0;
		this.image = "";
		r = new Rectangle(0,0,0,0);
	}
	
	public SpriteMiner(int x, int y, int width, int height, String image) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		r = new Rectangle(x, y, width, height);
	}
	
}
