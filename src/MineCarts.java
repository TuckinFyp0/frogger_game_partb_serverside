import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MineCarts extends SpriteMiner implements Runnable{

	private Boolean moving;
	private Thread t;
	private JLabel mineCartLabel;
	private JButton startButton;
	
	private Miner miner;
	private JLabel minerLabel;
	private ServerPrep game;
	
	// Movement variables
	private int speed;
	private int direction;
	
	public MineCarts() {
		super();
		this.moving = false;
		this.mineCartLabel = null;
		this.startButton = null;
		
		this.miner = null;
		this.minerLabel = null;
		this.speed = 200;
		this.direction = 1;
		
	}
	
	public MineCarts(int x, int y, int width, int height, String image, Boolean moving) {
		super(x, y, width, height, image);
		
		this.moving = moving;
		this.mineCartLabel = null;
		this.startButton = null;
		this.miner= null;
		this.minerLabel= null;
		this.speed = 200;
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

	public JLabel getMineCartLabel() {
		return mineCartLabel;
	}

	public void setMineCartLabel(JLabel mineCartLabel) {
		this.mineCartLabel = mineCartLabel;
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
	
	public void setGame(ServerPrep game) {
        this.game = game;
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
			
			mineCartLabel.setLocation(this.x, this.y);
			
			this.detectCollision();
			
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
		
	}
	
	// Start thread function - Starts the mine cart movement thread
	public void startThread() {
		if ( !this.moving ) {
			this.moving = true;
			t = new Thread(this, "MineCart movement thread");
			t.start();
		}
	}
	
	// Stop thread Function - Stops the mine cart movement thread
	public void stopThread() {
		if ( t != null ) {
			this.moving = false;
			t.interrupt();
		}
	}
	
	// Detects collision Function - Detects collision with miner and starts gameOver() function if checks are met. Padding used to 
	// fix the offset due to image scaling issues.
	private void detectCollision() {
		
		if (mineCartLabel == null || minerLabel == null || miner == null) {
			return;
		}
		
		int cartHorizontalPadding = 8;
        int cartTopPadding = 55;
        int cartBottomPadding = 10;

        int minerHorizontalPadding = 40;
        int minerTopPadding = 45;
        int minerBottomPadding = 35;

	    Rectangle mineCartRect = new Rectangle(
	        mineCartLabel.getX() + cartHorizontalPadding,
	        mineCartLabel.getY() + cartTopPadding,
	        mineCartLabel.getWidth() - (cartHorizontalPadding * 2),
	        mineCartLabel.getHeight() - cartTopPadding - cartBottomPadding
	    );

	    Rectangle minerRect = new Rectangle(
	        miner.getX() + minerHorizontalPadding,
	        miner.getY() + minerTopPadding,
	        miner.getWidth() - (minerHorizontalPadding * 2),
	        miner.getHeight() - minerTopPadding - minerBottomPadding
	    );
		
		if (mineCartRect.intersects(minerRect)) {
			this.moving = false;
			game.gameOver();
		}
		
	}
}
