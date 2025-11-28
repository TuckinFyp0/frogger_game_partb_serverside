import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JLabel;

public class movingStones extends SpriteMiner implements Runnable{

	private Boolean moving;
	private Thread t;
	private JLabel stoneLabel;
	private JButton startButton;
	
	private Miner miner;
	private JLabel minerLabel;
	private int speed;
	private int direction;
	
	public movingStones() {
		super();
		this.moving = false;
		this.stoneLabel = null;
		this.startButton = null;
		
		this.miner = null;
		this.minerLabel = null;
		this.speed = 300;
		this.direction = 1;
		
	}
	
	public movingStones(int x, int y, int width, int height, String image, Boolean moving) {
		super(x, y, width, height, image);
		
		this.moving = moving;
		this.stoneLabel = null;
		this.startButton = null;
		this.miner= null;
		this.minerLabel= null;
		this.speed = 300;
		this.direction = 1;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	
	public Boolean getMoving() {
		return moving;
	}

	public void setMoving(Boolean moving) {
		this.moving = moving;
	}

	public Thread getT() {
		return t;
	}

	public void setT(Thread t) {
		this.t = t;
	}

	public JLabel getStoneLabel() {
		return stoneLabel;
	}

	public void setStoneLabel(JLabel stoneLabel) {
		this.stoneLabel = stoneLabel;
	}

	public JButton getStartButton() {
		return startButton;
	}

	public void setStartButton(JButton startButton) {
		this.startButton = startButton;
	}

	public Miner getMiner() {
		return miner;
	}

	public void setMiner(Miner miner) {
		this.miner = miner;
	}

	public JLabel getMinerLabel() {
		return minerLabel;
	}

	public void setMinerLabel(JLabel minerLabel) {
		this.minerLabel = minerLabel;
	}

	@Override
	public void run() {
		
		while(moving) {
			
			int x = this.x;
			
			x += MinerGameProperties.CHARACTER_STEP * direction;
			
			this.setX(x);
			
			if (direction > 0) { 
				if (x >= MinerGameProperties.SCREEN_WIDTH) {
					this.setX(-1 * this.width);
				}
			} else { 
				if (x + this.width <= 0) {
					this.setX(MinerGameProperties.SCREEN_WIDTH);
				}
			}
			
			stoneLabel.setLocation(this.x, this.y);
			
			this.detectCollisionAndCenter();
			
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
		
	}
	
	public void startThread() {
		if ( !this.moving ) {
			this.moving = true;
			t = new Thread(this, "Stone movement thread");
			t.start();
		}
	}
	
	public void stopThread() {
		if ( t != null ) {
			this.moving = false;
			t.interrupt();
		}
	}
	
	// Detect Collision and Center Function - Used to detect collision with miner and stone, because of 	
	// image scaling needed to create this method to center miner over the stone... Image scaling caused
	// issues with hit boxes.
	private boolean detectCollisionAndCenter() {
		if (stoneLabel == null || minerLabel == null || miner == null) {
			return false;
		}
		
		int stoneHorizontalPadding = 5;
		int stoneTopPadding = 5;
		int stoneBottomPadding = 5;

        int minerHorizontalPadding = 40;
        int minerTopPadding = 80;
        int minerBottomPadding = 60;

	    Rectangle stoneRect = new Rectangle(
	    	stoneLabel.getX() + stoneHorizontalPadding,
	        stoneLabel.getY() + stoneTopPadding,
	        stoneLabel.getWidth() - (stoneHorizontalPadding * 2),
	        stoneLabel.getHeight() - stoneTopPadding - stoneBottomPadding
	    );

	    Rectangle minerRect = new Rectangle(
	        miner.getX() + minerHorizontalPadding,
	        miner.getY() + minerTopPadding,
	        miner.getWidth() - (minerHorizontalPadding * 2),
	        miner.getHeight() - minerTopPadding - minerBottomPadding
	    );
		
	    if (stoneRect.intersects(minerRect)) {
	    	int centered = this.x + (this.width / 2) - (miner.getWidth() / 2);
	        miner.setX(centered);
	        minerLabel.setLocation(miner.getX(), miner.getY());
	        return true;
	    }
	    return false;
	}
}