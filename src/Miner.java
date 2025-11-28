import java.awt.Rectangle;

public class Miner extends SpriteMiner{

	public Miner() {
		super();
	}

	public Miner(int x, int y, int width, int height, String image) {
		super(x, y, width, height, image);
		
	}
	
	@Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
}
