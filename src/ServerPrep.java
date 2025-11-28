import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ServerPrep extends JFrame implements KeyListener, ActionListener {
    
	// Character / objects declarations
    private Miner miner;
    private EndChest endChest;
    private movingStones stone_1;
    private movingStones stone_2;
    private movingStones stone_3;
    private movingStones stone_4;
    
    private MineCarts minecart_1;
    private MineCarts minecart_2;
    private MineCarts minecart_3;
    private MineCarts minecart_4;
    
    // Miner Character GUI
    private JLabel lblMiner;
    private ImageIcon imgMiner;

    // Chest Object GUI
    private JLabel lblEndChest;
    private ImageIcon imgEndChest;

    // Mine cart Objects GUI
    private JLabel lblMinecart_1;
    private ImageIcon imgMinecart_1;
    private JLabel lblMinecart_2;
    private ImageIcon imgMinecart_2;
    private JLabel lblMinecart_3;
    private ImageIcon imgMinecart_3;
    private JLabel lblMinecart_4;
    private ImageIcon imgMinecart_4;

    // Stone Object GUI
    private JLabel lblStone_1;
    private ImageIcon imgStone_1;
    private JLabel lblStone_2;
    private ImageIcon imgStone_2;
    private JLabel lblStone_3;
    private ImageIcon imgStone_3;
    private JLabel lblStone_4;
    private ImageIcon imgStone_4;

    // Background GUI
    private JLabel lblBackground;
    private ImageIcon imgBackground;
    
    // Menu GUI
    private JLabel lblStartMenuBackground;
    private JLabel lblGameOverMenuBackground;
    private ImageIcon imgMenuBackground;
    private JPanel startMenu;
    private JLabel lblStartMenuTitle;
    private JLabel lblGameOverMenuTitle;
    private JPanel gameOverMenu;
    private JPanel contentPane;
    
    private JLabel lblStartMenuScore;
    private JLabel lblGameOverMenuScore;
    private JLabel lblInGameScore;
    int intScore = 0;
    String playerName = "Jordan";

    // Buttons
    private JButton btnStart;
    private JButton btnExitMainMenu;
    private JButton btnRestart;
    private JButton btnExitGameOverMenu;
    
    // Collision checkers
    private Thread collisionCheckThread;
    private boolean gameRunning = false;
    

    public ServerPrep() {
        super("Miner Game");
       
        //Background GUI Setup
        int width = MinerGameProperties.SCREEN_WIDTH;
        int height = MinerGameProperties.SCREEN_HEIGHT;

        ImageIcon originalIconBackground = new ImageIcon(getClass().getResource("/imageAssets/froggerProjectMapRevised.png"));
        Image originalImageBackground = originalIconBackground.getImage();
        Image scaledImageBackground = originalImageBackground.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imgBackground = new ImageIcon(scaledImageBackground);

        lblBackground = new JLabel(imgBackground);
        lblBackground.setBounds(0, 0, width, height);

        contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(width, height));
        contentPane.add(lblBackground);

        setContentPane(contentPane);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
          
        //Character / Object initialization
        miner = new Miner(100, 250, 100, 200, "minerFacingFront.png");
        endChest = new EndChest(100, 250, 100, 200,"chest.png");
        
        //Mine Carts
		minecart_1 = new MineCarts(0, 0, 120, 200, "minecartEmpty.png", false);
		minecart_2 = new MineCarts(0, 0, 120, 120, "minecartCoal.png", false);
		minecart_3 = new MineCarts(0, 0, 120, 120, "minecartEmpty.png", false);
		minecart_4 = new MineCarts(0, 0, 120, 120, "minecartCoal.png", false);
		
		//Stones
        stone_1 = new movingStones(0, 0, 120, 120, "stone.png", false);
        stone_2 = new movingStones(0, 0, 120, 120, "stone.png", false);
        stone_3 = new movingStones(0, 0, 120, 120, "stone.png", false);
        stone_4 = new movingStones(0, 0, 120, 120, "stone.png", false);
        
	    // Initialization Buttons
	    btnStart = new JButton("Start Game");
	    btnRestart = new JButton("Restart Game");
	    btnExitMainMenu = new JButton("Exit Game");
	    btnExitGameOverMenu = new JButton("Exit Game");

	    Color colorBlack = new Color(0, 0, 0);
	
	    // Start Menu GUI Setup
	    int menuWidth = MinerGameProperties.MENU_WIDTH;
	    int menuHeight = MinerGameProperties.MENU_HEIGHT;
	    int x = (MinerGameProperties.SCREEN_WIDTH - menuWidth) / 2;
	    int y = (MinerGameProperties.SCREEN_HEIGHT - menuHeight) / 2;
	
	    // Background Image
	    ImageIcon originalIconStartMenuBackground = new ImageIcon(getClass().getResource("/imageAssets/Menu.png"));
	    Image originalImageStartMenuBackground = originalIconStartMenuBackground.getImage();
	    Image scaledImageStartMenuBackground = originalImageStartMenuBackground.getScaledInstance(menuWidth, menuHeight, Image.SCALE_SMOOTH);
	    imgMenuBackground = new ImageIcon(scaledImageStartMenuBackground);
	
	    lblStartMenuBackground = new JLabel(imgMenuBackground);
	    lblGameOverMenuBackground = new JLabel(imgMenuBackground);
	    lblStartMenuBackground.setBounds(0, 0, menuWidth, menuHeight);
	    lblGameOverMenuBackground.setBounds(0, 0, menuWidth, menuHeight);
	    
	    // Game Over Menu Panel
	    gameOverMenu = new JPanel(null);
	    gameOverMenu.setBounds(x, y, menuWidth, menuHeight);
	
	    // Start Menu Panel
	    startMenu = new JPanel(null);
	    startMenu.setBounds(x, y, menuWidth, menuHeight);
	    
	    // Menu Buttons Image Scaling
	    int menuButtonWidth = 200;
	    int menuButtonHeight = 40;
	    ImageIcon imgMenuButtonIcon = new ImageIcon(getClass().getResource("/imageAssets/buttonMenu.png"));
	    Image originalMenuButtonImage = imgMenuButtonIcon.getImage();
	    Image scaledMenuButtonImage = originalMenuButtonImage.getScaledInstance(menuButtonWidth, menuButtonHeight, Image.SCALE_SMOOTH);
	    btnStart.setIcon(new ImageIcon(scaledMenuButtonImage));
	    btnRestart.setIcon(new ImageIcon(scaledMenuButtonImage));
	    btnExitMainMenu.setIcon(new ImageIcon(scaledMenuButtonImage));
	    btnExitGameOverMenu.setIcon(new ImageIcon(scaledMenuButtonImage));
	    btnStart.setBounds(100, 140, menuButtonWidth, menuButtonHeight);
	    btnRestart.setBounds(100, 140, menuButtonWidth, menuButtonHeight);
	    btnExitMainMenu.setBounds(100, 190, menuButtonWidth, menuButtonHeight);
	    btnExitGameOverMenu.setBounds(100, 190, menuButtonWidth, menuButtonHeight);
	    btnStart.setForeground(colorBlack);
	    btnRestart.setForeground(colorBlack);
	    btnExitMainMenu.setForeground(colorBlack);
	    btnExitGameOverMenu.setForeground(colorBlack);
	    
	    // Score Label
	    
	    //In game score
        lblInGameScore = new JLabel("Score: " + intScore);
        lblInGameScore.setForeground(colorBlack);
	    lblInGameScore.setFont(new Font("Arial", Font.BOLD, 24));
        lblInGameScore.setBounds(10, 10, menuButtonWidth, menuButtonHeight);
       
        // Start Menu Score
        lblStartMenuScore = new JLabel(playerName + "'s score: " + intScore);
        lblStartMenuScore.setForeground(colorBlack);
        lblStartMenuScore.setFont(new Font("Arial", Font.BOLD, 20));
        lblStartMenuScore.setBounds(115, 90, menuButtonWidth, menuButtonHeight);
        
        // Game Over Menu Score
        lblGameOverMenuScore = new JLabel(playerName + "'s score: " + intScore);
        lblGameOverMenuScore.setForeground(colorBlack);
        lblGameOverMenuScore.setFont(new Font("Arial", Font.BOLD, 20));
        lblGameOverMenuScore.setBounds(115, 90, menuButtonWidth, menuButtonHeight);
	    
        // Removed default button attributes
	    btnStart.setBorderPainted(false);
	    btnStart.setContentAreaFilled(false);
	    btnStart.setFocusPainted(false);
	    btnStart.setOpaque(false);
	    btnRestart.setBorderPainted(false);
	    btnRestart.setContentAreaFilled(false);
	    btnRestart.setFocusPainted(false);
	    btnRestart.setOpaque(false);

	    btnExitMainMenu.setBorderPainted(false);
	    btnExitMainMenu.setContentAreaFilled(false);
	    btnExitMainMenu.setFocusPainted(false);
	    btnExitMainMenu.setOpaque(false);
	    
	    btnExitGameOverMenu.setBorderPainted(false);
	    btnExitGameOverMenu.setContentAreaFilled(false);
	    btnExitGameOverMenu.setFocusPainted(false);
	    btnExitGameOverMenu.setOpaque(false);
	    
	    // Menu Button text attributes
	    btnStart.setFont(new Font("Arial", Font.BOLD, 18));
	    btnExitMainMenu.setFont(new Font("Arial", Font.BOLD, 18));
	    btnStart.setHorizontalTextPosition(SwingConstants.CENTER);
	    btnStart.setVerticalTextPosition(SwingConstants.CENTER);
	    btnExitMainMenu.setHorizontalTextPosition(SwingConstants.CENTER);
	    btnExitMainMenu.setVerticalTextPosition(SwingConstants.CENTER);
	    
	    btnRestart.setFont(new Font("Arial", Font.BOLD, 18));
	    btnExitGameOverMenu.setFont(new Font("Arial", Font.BOLD, 18));
	    btnRestart.setHorizontalTextPosition(SwingConstants.CENTER);
	    btnRestart.setVerticalTextPosition(SwingConstants.CENTER);
	    btnExitGameOverMenu.setHorizontalTextPosition(SwingConstants.CENTER);
	    btnExitMainMenu.setVerticalTextPosition(SwingConstants.CENTER);

	    // Menu Title Image scaling
	    int titleWidth = 300;
	    int titleHeight = 75;
	    lblStartMenuTitle = new JLabel();
	    lblGameOverMenuTitle = new JLabel();
	    ImageIcon imgMenuTitleIcon = new ImageIcon(getClass().getResource("/imageAssets/Title.png"));
	    Image originalTitleImage = imgMenuTitleIcon.getImage();
	    Image scaledTitleImage = originalTitleImage.getScaledInstance(titleWidth, titleHeight, Image.SCALE_SMOOTH);
	    lblStartMenuTitle.setIcon(new ImageIcon(scaledTitleImage));
	    lblStartMenuTitle.setBounds(50, 10, titleWidth, titleHeight);
	    lblStartMenuTitle.setText("Miner Game");
	    lblStartMenuTitle.setForeground(colorBlack);
	    lblStartMenuTitle.setHorizontalTextPosition(JLabel.CENTER);
	    lblStartMenuTitle.setVerticalTextPosition(JLabel.CENTER);
	    lblStartMenuTitle.setFont(new Font("Arial", Font.BOLD, 24));
	    
	    lblGameOverMenuTitle.setIcon(new ImageIcon(scaledTitleImage));
	    lblGameOverMenuTitle.setBounds(50, 10, titleWidth, titleHeight);
	    lblGameOverMenuTitle.setText("Game Over!");
	    lblGameOverMenuTitle.setForeground(colorBlack);
	    lblGameOverMenuTitle.setHorizontalTextPosition(JLabel.CENTER);
	    lblGameOverMenuTitle.setVerticalTextPosition(JLabel.CENTER);
	    lblGameOverMenuTitle.setFont(new Font("Arial", Font.BOLD, 24));
	    
	   	// Game Over Menu
	   	gameOverMenu.add(lblGameOverMenuTitle);
	   	gameOverMenu.add(btnRestart);
	   	gameOverMenu.add(btnExitGameOverMenu);
	   	gameOverMenu.add(lblGameOverMenuScore);
	   	gameOverMenu.add(lblGameOverMenuBackground);


	    // Add Start Menu Components
	    startMenu.add(lblStartMenuTitle);
	    startMenu.add(btnStart);
	    startMenu.add(btnExitMainMenu);
	    startMenu.add(lblStartMenuScore);
	    startMenu.add(lblStartMenuBackground);
	        
	
	    // Action Listeners for Buttons
	    btnStart.addActionListener(e -> startGame());
	    btnRestart.addActionListener(e -> resetGame());
	    btnExitMainMenu.addActionListener(e -> System.exit(0));
	    btnExitGameOverMenu.addActionListener(e -> System.exit(0));
	     
        // Miner Character GUI Setup
		miner.setX(212);
		miner.setY(550); 
		miner.setWidth(150);
		miner.setHeight(150);
		miner.setImage("imageAssets//minerFacingFront.png");
		
	    lblMiner = new JLabel();
	    // Image scaling
	    ImageIcon originalIconMiner = new ImageIcon(getClass().getResource("/" + miner.getImage()));
	    Image originalImageMiner = originalIconMiner.getImage();
	    Image scaledImageMiner = originalImageMiner.getScaledInstance(miner.getWidth(), miner.getHeight(), Image.SCALE_SMOOTH);
	    imgMiner = new ImageIcon(scaledImageMiner);
	    lblMiner.setIcon(imgMiner);
	    lblMiner.setSize(miner.getWidth(), miner.getHeight());
	    lblMiner.setLocation(miner.getX(), miner.getY());
	    
	    // Chest GUI Setup
	    endChest.setX(250);
	    endChest.setY(0); 
	    endChest.setWidth(75);
	    endChest.setHeight(75);
	    endChest.setImage("imageAssets//chestClosed.png");
		
	    lblEndChest = new JLabel();
	    // Image scaling
	    ImageIcon originalIconChest = new ImageIcon(getClass().getResource("/" + endChest.getImage()));
	    Image originalImageChest = originalIconChest.getImage();
	    Image scaledImageChest = originalImageChest.getScaledInstance(endChest.getWidth(), endChest.getHeight(), Image.SCALE_SMOOTH);
	    imgEndChest = new ImageIcon(scaledImageChest);
	    lblEndChest.setIcon(imgEndChest);
	    lblEndChest.setSize(endChest.getWidth(), endChest.getHeight());
	    lblEndChest.setLocation(endChest.getX(), endChest.getY());
	    
		
		//MineCart GUI Setup
	    
	    // Mine cart 1
		minecart_1.setX(375);
		minecart_1.setY(515);
		minecart_1.setWidth(80);
		minecart_1.setHeight(80);
		minecart_1.setImage("imageAssets//minecartCoal.png");
		minecart_1.setMoving(false);
		minecart_1.setGame(this);
		minecart_1.setSpeed(250);
		minecart_1.setDirection(-1);
		
	    lblMinecart_1 = new JLabel();
	    // Image scaling
	    ImageIcon originalIconMinecart = new ImageIcon(getClass().getResource("/" + minecart_1.getImage()));
	    Image originalImageMinecart = originalIconMinecart.getImage();
	    Image scaledImageMinecart = originalImageMinecart.getScaledInstance(minecart_1.getWidth(), minecart_1.getHeight(), Image.SCALE_SMOOTH);
	    imgMinecart_1 = new ImageIcon(scaledImageMinecart);
	    lblMinecart_1.setIcon(imgMinecart_1);
	    lblMinecart_1.setSize(minecart_1.getWidth(), minecart_1.getHeight());
	    lblMinecart_1.setLocation(minecart_1.getX(), minecart_1.getY());
	    minecart_1.setMineCartLabel(lblMinecart_1);
	    
	    // Mine cart 2
		minecart_2.setX(480);
		minecart_2.setY(415);
		minecart_2.setWidth(80);
		minecart_2.setHeight(80);
		minecart_2.setImage("imageAssets//minecartEmpty.png");
		minecart_2.setMoving(false);
		minecart_2.setGame(this);
		minecart_2.setSpeed(300);
		minecart_2.setDirection(1);
		
	    lblMinecart_2 = new JLabel();
	    // Image scaling
	    ImageIcon originalIconMinecart2 = new ImageIcon(getClass().getResource("/" + minecart_2.getImage()));
	    Image originalImageMinecart2 = originalIconMinecart2.getImage();
	    Image scaledImageMinecart2 = originalImageMinecart2.getScaledInstance(minecart_2.getWidth(), minecart_2.getHeight(), Image.SCALE_SMOOTH);
	    imgMinecart_2 = new ImageIcon(scaledImageMinecart2);
	    lblMinecart_2.setIcon(imgMinecart_2);
	    lblMinecart_2.setSize(minecart_2.getWidth(), minecart_2.getHeight());
	    lblMinecart_2.setLocation(minecart_2.getX(), minecart_2.getY());
	    minecart_2.setMineCartLabel(lblMinecart_2);
	    
	    // Mine cart 3
 		minecart_3.setX(175);
 		minecart_3.setY(315);
 		minecart_3.setWidth(80);
 		minecart_3.setHeight(80);
 		minecart_3.setImage("imageAssets//minecartCoal.png");
 		minecart_3.setMoving(false);
 		minecart_3.setGame(this);
 		minecart_3.setSpeed(200);
		minecart_3.setDirection(-1);
 		
 	    lblMinecart_3 = new JLabel();
 	    // Image scaling
 	    ImageIcon originalIconMinecart3 = new ImageIcon(getClass().getResource("/" + minecart_3.getImage()));
 	    Image originalImageMinecart3 = originalIconMinecart3.getImage();
 	    Image scaledImageMinecart3 = originalImageMinecart3.getScaledInstance(minecart_3.getWidth(), minecart_3.getHeight(), Image.SCALE_SMOOTH);
 	    imgMinecart_3 = new ImageIcon(scaledImageMinecart3);
 	    lblMinecart_3.setIcon(imgMinecart_3);
 	    lblMinecart_3.setSize(minecart_3.getWidth(), minecart_3.getHeight());
 	    lblMinecart_3.setLocation(minecart_3.getX(), minecart_3.getY());
 	    minecart_3.setMineCartLabel(lblMinecart_3);
 	    
 	    // Mine Cart 4
 	    minecart_4.setX(75);
		minecart_4.setY(415);
		minecart_4.setWidth(80);
		minecart_4.setHeight(80);
		minecart_4.setImage("imageAssets//minecartEmpty.png");
		minecart_4.setMoving(false);
		minecart_4.setGame(this);
		minecart_4.setSpeed(300);
		minecart_4.setDirection(1);
		
	    lblMinecart_4 = new JLabel();
	    // Image scaling
	    ImageIcon originalIconMinecart4 = new ImageIcon(getClass().getResource("/" + minecart_4.getImage()));
	    Image originalImageMinecart4 = originalIconMinecart4.getImage();
	    Image scaledImageMinecart4 = originalImageMinecart4.getScaledInstance(minecart_4.getWidth(), minecart_4.getHeight(), Image.SCALE_SMOOTH);
	    imgMinecart_4 = new ImageIcon(scaledImageMinecart4);
	    lblMinecart_4.setIcon(imgMinecart_4);
	    lblMinecart_4.setSize(minecart_4.getWidth(), minecart_4.getHeight());
	    lblMinecart_4.setLocation(minecart_4.getX(), minecart_4.getY());
	    minecart_4.setMineCartLabel(lblMinecart_4);
	    
 	    // Stones GUI Setup
 	    
 	    // Stone 1
 	    stone_1.setX(360);
		stone_1.setY(160);
		stone_1.setWidth(50);
		stone_1.setHeight(50);
		stone_1.setImage("imageAssets//stone.png");
		stone_1.setMoving(false);
		stone_1.setSpeed(400);
		stone_1.setDirection(1);
		
	    lblStone_1 = new JLabel();
	    // Image scaling
	    ImageIcon originalIconStone = new ImageIcon(getClass().getResource("/" + stone_1.getImage()));
	    Image originalImageStone = originalIconStone.getImage();
	    Image scaledImageStone = originalImageStone.getScaledInstance(stone_1.getWidth(), stone_1.getHeight(), Image.SCALE_SMOOTH);
	    imgStone_1 = new ImageIcon(scaledImageStone);
	    lblStone_1.setIcon(imgStone_1);
	    lblStone_1.setSize(stone_1.getWidth(), stone_1.getHeight());
	    lblStone_1.setLocation(stone_1.getX(), stone_1.getY());
	    stone_1.setStoneLabel(lblStone_1);
 	    
 	    // Stone 2
	    stone_2.setX(0);
		stone_2.setY(210);
		stone_2.setWidth(50);
		stone_2.setHeight(50);
		stone_2.setImage("imageAssets//stone.png");
		stone_2.setMoving(false);
		stone_2.setSpeed(350);
		stone_2.setDirection(-1);
		
	    lblStone_2 = new JLabel();
	    // Image scaling
	    ImageIcon originalIconStone2 = new ImageIcon(getClass().getResource("/" + stone_2.getImage()));
	    Image originalImageStone2 = originalIconStone2.getImage();
	    Image scaledImageStone2 = originalImageStone2.getScaledInstance(stone_2.getWidth(), stone_2.getHeight(), Image.SCALE_SMOOTH);
	    imgStone_2 = new ImageIcon(scaledImageStone2);
	    lblStone_2.setIcon(imgStone_2);
	    lblStone_2.setSize(stone_2.getWidth(), stone_2.getHeight());
	    lblStone_2.setLocation(stone_2.getX(), stone_2.getY());
	    stone_2.setStoneLabel(lblStone_2);
 	    
 	    // Stone 3
	    stone_3.setX(300);
		stone_3.setY(210);
		stone_3.setWidth(50);
		stone_3.setHeight(50);
		stone_3.setImage("imageAssets//stone.png");
		stone_3.setMoving(false);
		stone_3.setSpeed(350);
		stone_3.setDirection(-1);
		
	    lblStone_3 = new JLabel();
	    // Image scaling
	    ImageIcon originalIconStone3 = new ImageIcon(getClass().getResource("/" + stone_3.getImage()));
	    Image originalImageStone3 = originalIconStone3.getImage();
	    Image scaledImageStone3 = originalImageStone3.getScaledInstance(stone_3.getWidth(), stone_3.getHeight(), Image.SCALE_SMOOTH);
	    imgStone_3 = new ImageIcon(scaledImageStone3);
	    lblStone_3.setIcon(imgStone_3);
	    lblStone_3.setSize(stone_3.getWidth(), stone_3.getHeight());
	    lblStone_3.setLocation(stone_3.getX(), stone_3.getY());
	    stone_3.setStoneLabel(lblStone_3);
	    
	    // Stone 4
	    stone_4.setX(210);
		stone_4.setY(260);
		stone_4.setWidth(50);
		stone_4.setHeight(50);
		stone_4.setImage("imageAssets//stone.png");
		stone_4.setMoving(false);
		stone_4.setSpeed(500);
		stone_4.setDirection(1);
		
	    lblStone_4 = new JLabel();
	    // Image scaling
	    ImageIcon originalIconStone4 = new ImageIcon(getClass().getResource("/" + stone_4.getImage()));
	    Image originalImageStone4 = originalIconStone4.getImage();
	    Image scaledImageStone4 = originalImageStone4.getScaledInstance(stone_4.getWidth(), stone_4.getHeight(), Image.SCALE_SMOOTH);
	    imgStone_4 = new ImageIcon(scaledImageStone4);
	    lblStone_4.setIcon(imgStone_4);
	    lblStone_4.setSize(stone_4.getWidth(), stone_4.getHeight());
	    lblStone_4.setLocation(stone_4.getX(), stone_4.getY());
	    stone_4.setStoneLabel(lblStone_4);
	   
		//Populate screen
	    
	    // Miner + Chest
		contentPane.add(lblMiner);
		contentPane.add(lblEndChest);
		
		//Mine Carts
		contentPane.add(lblMinecart_1);
		contentPane.add(lblMinecart_2);
		contentPane.add(lblMinecart_3);
		contentPane.add(lblMinecart_4);
		
		//Score Labels
		contentPane.add(lblInGameScore);
		
		//Stones
		contentPane.add(lblStone_1);
		contentPane.add(lblStone_2);
		contentPane.add(lblStone_3);
		contentPane.add(lblStone_4);
		
		// Menus
		contentPane.add(gameOverMenu);
		contentPane.add(startMenu);
		
		
		// Z Order
		contentPane.setComponentZOrder(lblBackground, contentPane.getComponentCount() - 1);
		contentPane.setComponentZOrder(lblStone_1, 0);
		contentPane.setComponentZOrder(lblStone_2, 0);
		contentPane.setComponentZOrder(lblStone_3, 0);
		contentPane.setComponentZOrder(lblStone_4, 0);
		contentPane.setComponentZOrder(lblEndChest, 0);
		contentPane.setComponentZOrder(lblMiner, 0);
		contentPane.setComponentZOrder(lblMinecart_1, 0);
		contentPane.setComponentZOrder(lblMinecart_2, 0);
		contentPane.setComponentZOrder(lblMinecart_3, 0);
		contentPane.setComponentZOrder(lblMinecart_4, 0);
		contentPane.setComponentZOrder(gameOverMenu, 0);		
		contentPane.setComponentZOrder(startMenu, 0);
		
		contentPane.addKeyListener(this);
		contentPane.setFocusable(true);
		gameOverMenu.setVisible(false);
		
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        ServerPrep game = new ServerPrep();
        game.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	
    }

    @Override
    public void keyTyped(KeyEvent e) {
    	
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	
    	if (startMenu.isVisible() || gameOverMenu.isVisible()) {
            return;
        }
    	
		int x = miner.getX();
		int y = miner.getY();
		
		if ( e.getKeyCode() == KeyEvent.VK_UP ) {
			
			y -= MinerGameProperties.CHARACTER_STEP;
			
			miner.setImage("imageAssets/minerFacingAway.png");
			updateMinerImage();
			
			if (y + miner.getHeight() <= 0) {
				y = MinerGameProperties.SCREEN_HEIGHT;
			}
			
		} else if ( e.getKeyCode() == KeyEvent.VK_DOWN ) {
			
			y += MinerGameProperties.CHARACTER_STEP;
			
			miner.setImage("imageAssets/minerFacingFront.png");
			updateMinerImage();
			
			if (y > MinerGameProperties.SCREEN_HEIGHT) {
				y = -1 * miner.getHeight();
			}
			
		} else if ( e.getKeyCode() == KeyEvent.VK_LEFT ) {
			
			x -= MinerGameProperties.CHARACTER_STEP;
			
			miner.setImage("imageAssets/minerFacingLeft.png");
			updateMinerImage();
			
			if (x + miner.getWidth() <= 0) {
				x = MinerGameProperties.SCREEN_WIDTH;
			}
			
		} else if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) {
			
			x += MinerGameProperties.CHARACTER_STEP;
			
			miner.setImage("imageAssets/minerFacingRight.png");
			updateMinerImage();
			
			if (x >= MinerGameProperties.SCREEN_WIDTH) {
				x = -1 * miner.getWidth();
			}
			
		} else {
			
			System.out.println("unrecognized key press");
			
		}
		
		miner.setX(x);
		miner.setY(y);
		
		lblMiner.setLocation(miner.getX(), miner.getY() );
		
		if (miner.getY() <= -10) {
			gameWon();
		}
		
	}
    
    @Override
    public void keyReleased(KeyEvent e) {
    	
    }
    
    // Update miner Image Function - Updates the miner characters image when called upon
    private void updateMinerImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/" + miner.getImage()));
        Image scaledImage = icon.getImage().getScaledInstance(miner.getWidth(), miner.getHeight(), Image.SCALE_SMOOTH);
        lblMiner.setIcon(new ImageIcon(scaledImage));
    }
    
    // Start Game Function - Starts threads + Collision checker / disables menu
    private void startGame() {
    		
        // Mine Cart Array
        MineCarts[] mineCartsArray = {minecart_1, minecart_2, minecart_3, minecart_4};
        
		// Moving Stones Array
	    movingStones[] movingStonesArray = {stone_1, stone_2, stone_3, stone_4};
    	
    	for (int i=0; i < mineCartsArray.length; i++) {
    		mineCartsArray[i].setMiner(miner);
    		mineCartsArray[i].setMinerLabel(lblMiner);
    		mineCartsArray[i].startThread();
    	}
    	
    	for (int i=0; i < movingStonesArray.length; i++) {
    		movingStonesArray[i].setMiner(miner);
    		movingStonesArray[i].setMinerLabel(lblMiner);
    		movingStonesArray[i].startThread();
    	}
    	startCollisionChecker();
    	
    	startMenu.setVisible(false);
    	btnStart.setVisible(false);
    	btnExitMainMenu.setVisible(false);

    }
    
    // Game Won function - When Miner reaches end, awards points and prompts menu
    protected void gameWon() {
    	
    	stopCollisionChecker();
    	gameOverMenu.setVisible(true);
    	
    	intScore += 50;
    	lblGameOverMenuScore.setText(playerName + "'s score: " + intScore);
        lblInGameScore.setText("Score: " + intScore);
        
        // Update Database
        MinerGameDB.updateScore(playerName, intScore);
    	
    	// Mine Cart Array
        MineCarts[] mineCartsArray = {minecart_1, minecart_2, minecart_3, minecart_4};
        
		// Moving Stones Array
	    movingStones[] movingStonesArray = {stone_1, stone_2, stone_3, stone_4};
    	
    	for (int i=0; i < mineCartsArray.length; i++) {
    		mineCartsArray[i].setMiner(miner);
    		mineCartsArray[i].setMinerLabel(lblMiner);
    		mineCartsArray[i].stopThread();
    	}
    	
    	for (int i=0; i < movingStonesArray.length; i++) {
    		movingStonesArray[i].setMiner(miner);
    		movingStonesArray[i].setMinerLabel(lblMiner);
    		movingStonesArray[i].stopThread();
    	}
    }
    
    // isOnAnyStone Function - checks if miner is on any of the stones while in the lava area
    private boolean isOnAnyStone() {
        int minerHorizontalPadding = 40;
        int minerBottomPadding = 60;
        
        Rectangle minerFeetRect = new Rectangle(
            miner.getX() + minerHorizontalPadding,
            miner.getY() + miner.getHeight() - minerBottomPadding,
            miner.getWidth() - (minerHorizontalPadding * 2), 20);
        
        // Check collision with each stone
        movingStones[] stones = {stone_1, stone_2, stone_3, stone_4};
        
        for (movingStones stone : stones) {
            int stoneHorizontalPadding = 5;
            int stoneTopPadding = 5;
            
            Rectangle stoneRect = new Rectangle(
                stone.getX() + stoneHorizontalPadding,
                stone.getY() + stoneTopPadding,
                stone.getWidth() - (stoneHorizontalPadding * 2),
                stone.getHeight() - stoneTopPadding
            );
            
            if (minerFeetRect.intersects(stoneRect)) {
                return true;
            }
        }
        
        return false;
    }
    
    // Collision checker function - Checks if miner is within lava area + Calls the isOnAnyStone function to check if he is on a stone (safe)
    private void startCollisionChecker() {
        gameRunning = true;
        collisionCheckThread = new Thread(() -> {
            while (gameRunning) {
            	
                // Check if miner is in the lava
                if (miner.getY() >= 100 && miner.getY() <= 200) {
                	
                    // Check if miner is on stones
                    boolean onStone = isOnAnyStone();
                    
                    if (!onStone) {
                        // Immediately stop game
                        gameRunning = false;
                        
                        // Stop all mine carts
                        MineCarts[] mineCartsArray = {minecart_1, minecart_2, minecart_3, minecart_4};
                        for (MineCarts cart : mineCartsArray) {
                            cart.stopThread();
                        }
                        
                        // Stop all stones
                        movingStones[] movingStonesArray = {stone_1, stone_2, stone_3, stone_4};
                        for (movingStones stone : movingStonesArray) {
                            stone.stopThread();
                        }
                        
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        
                        // If not on a stone and is within lava area = Game over
                        gameOver();
                        break;
                    }
                }
                
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        collisionCheckThread.start();
    }
    
    private void stopCollisionChecker() {
        gameRunning = false;
        if (collisionCheckThread != null) {
            collisionCheckThread.interrupt();
        }
    }
    
    
    // Game over Function - Miner collides with mine cart of lava, stops game and subtracts score
    protected void gameOver() {
    	
    	stopCollisionChecker();
    	gameOverMenu.setVisible(true);
    	
    	intScore -= 50;
    	lblGameOverMenuScore.setText(playerName + "'s score: " + intScore);
        lblInGameScore.setText("Score: " + intScore);
        
        // Update Database
        MinerGameDB.updateScore(playerName, intScore);
    	
    	// Mine Cart Array
        MineCarts[] mineCartsArray = {minecart_1, minecart_2, minecart_3, minecart_4};
        
		// Moving Stones Array
	    movingStones[] movingStonesArray = {stone_1, stone_2, stone_3, stone_4};
    	
    	for (int i=0; i < mineCartsArray.length; i++) {
    		mineCartsArray[i].setMiner(miner);
    		mineCartsArray[i].setMinerLabel(lblMiner);
    		mineCartsArray[i].stopThread();
    	}
    	
    	for (int i=0; i < movingStonesArray.length; i++) {
    		movingStonesArray[i].setMiner(miner);
    		movingStonesArray[i].setMinerLabel(lblMiner);
    		movingStonesArray[i].stopThread();
    	}
    }
    
    // Reset Game Function - Resets all object coordinates and restarts the game
    protected void resetGame() {
	    
    	stopCollisionChecker();
	    gameOverMenu.setVisible(false);
	    miner.setX(212);
		miner.setY(550);
		
		miner.setImage("imageAssets//minerFacingFront.png");
		lblMiner.setLocation(miner.getX(), miner.getY());
		updateMinerImage();
		
		minecart_1.setX(375);
	    minecart_1.setY(515);
	    lblMinecart_1.setLocation(minecart_1.getX(), minecart_1.getY());
	    
	    minecart_2.setX(480);
	    minecart_2.setY(415);
	    lblMinecart_2.setLocation(minecart_2.getX(), minecart_2.getY());
	    
	    minecart_3.setX(175);
	    minecart_3.setY(315);
	    lblMinecart_3.setLocation(minecart_3.getX(), minecart_3.getY());
	    
	    minecart_4.setX(75);
	    minecart_4.setY(415);
	    lblMinecart_4.setLocation(minecart_4.getX(), minecart_4.getY());
	    
	    stone_1.setX(360);
	    stone_1.setY(160);
	    lblStone_1.setLocation(stone_1.getX(), stone_1.getY());
	    
	    stone_2.setX(0);
	    stone_2.setY(210);
	    lblStone_2.setLocation(stone_2.getX(), stone_2.getY());
	    
	    stone_3.setX(300);
	    stone_3.setY(210);
	    lblStone_3.setLocation(stone_3.getX(), stone_3.getY());
	    
	    stone_4.setX(210);
	    stone_4.setY(260);
	    lblStone_4.setLocation(stone_4.getX(), stone_4.getY());
		
	    startGame();
	}
}

