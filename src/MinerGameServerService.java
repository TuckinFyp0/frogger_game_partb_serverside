import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JLabel;

public class MinerGameServerService implements Runnable {
	
	final static int CLIENT_PORT = 5656;
	
	private Socket s;
	private Scanner in;
	private Miner miner;
    private MineCarts[] mineCartsArray;
    private movingStones[] stonesArray;
	
	public MinerGameServerService(Socket s, Miner miner, MineCarts[] mineCartsArray, movingStones[] stonesArray) {
		this.s = s;
		this.miner = miner;
        this.mineCartsArray = mineCartsArray;
        this.stonesArray = stonesArray;

        startStonesThread();
        startMinecartThread();
	}

	public void run() {
		try {
			in = new Scanner(s.getInputStream());

            updateMinerPosition();
			processRequest();
			
		} catch (IOException e){
			e.printStackTrace();
			
		} finally {
			try {
				s.close();
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
	}

	private void processRequest() {
		while(true) {
			if(!in.hasNext( )){
				return;
			}
			String command = in.next();
			if (command.equals("Quit")) {
				return;
			} else {
				executeCommand(command);
			}
		}
	}

	private void executeCommand(String command) {
		
		if (command.equals("MOVEMINER")) {
			String minerAction = in.next();
			
			if (minerAction.equals("UP")) {
				miner.setY(miner.getY() - 50);
				
			} else if (minerAction.equals("DOWN")) {
				miner.setY(miner.getY() + 50);
				
			} else if (minerAction.equals("LEFT")) {
				miner.setX(miner.getX() - 50);
				
			} else if (minerAction.equals("RIGHT")) {
				miner.setX(miner.getX() + 50);
				
			}

            updateMinerPosition();

		}

        if (command.equals("RESETGAME")) {

            miner.setY(550);
            miner.setX(212);

            mineCartsArray[0].setX(375);
            mineCartsArray[0].setY(515);
            mineCartsArray[1].setX(480);
            mineCartsArray[1].setY(415);
            mineCartsArray[2].setX(175);
            mineCartsArray[2].setY(315);
            mineCartsArray[3].setX(75);
            mineCartsArray[3].setY(415);

            updateMinerPosition();
            updateMinerPosition();
        }
			
	}

    private void updateMinerPosition() {
        try (Socket s2 = new Socket("localhost", CLIENT_PORT);
             PrintWriter out = new PrintWriter(s2.getOutputStream(), true)) {

            String commandOut = "MINER " + miner.getX() + " " + miner.getY();
            System.out.println("Sending: " + commandOut);
            out.println(commandOut);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void updateMinecarts() {
        try (Socket s2 = new Socket("localhost", CLIENT_PORT);
             PrintWriter out = new PrintWriter(s2.getOutputStream(), true)) {

            for (int i = 0; i < mineCartsArray.length; i++) {
                MineCarts cart = mineCartsArray[i];
                String msg = "MINECART " + i + " " + cart.getX() + " " + cart.getY();
                System.out.println("Sending: " + msg);
                out.println(msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveMinecarts() {
        for (MineCarts cart : mineCartsArray) {
            int newX = cart.getX() + cart.getDirection() * cart.getSpeed() / 10;
            cart.setX(newX);

            if (cart.getDirection() > 0 && cart.getX() >= 600) {
                cart.setX(-cart.getWidth());
            } else if (cart.getDirection() < 0 && cart.getX() + cart.getWidth() <= 0) {
                cart.setX(600);
            }
        }
    }

    private void startMinecartThread() {
        Thread t = new Thread(() -> {
            try {
                while (true) {
                    moveMinecarts();
                    updateMinecarts();
                    Thread.sleep(50);

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        t.start();
    }

    private void updateStones() {
        try (Socket s2 = new Socket("localhost", CLIENT_PORT);
             PrintWriter out = new PrintWriter(s2.getOutputStream(), true)) {

            for (int i = 0; i < stonesArray.length; i++) {
                movingStones stone = stonesArray[i];
                String msg = "STONE " + i + " " + stone.getX() + " " + stone.getY();
                System.out.println("Sending: " + msg);
                out.println(msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveStones() {
        for (movingStones stone : stonesArray) {
            int newX = stone.getX() + stone.getDirection() * stone.getSpeed() / 10;
            stone.setX(newX);

            if (stone.getDirection() > 0 && stone.getX() >= 600) {
                stone.setX(-stone.getWidth());
            } else if (stone.getDirection() < 0 && stone.getX() + stone.getWidth() <= 0) {
                stone.setX(600);
            }
        }
    }

    private void startStonesThread() {
        Thread t = new Thread(() -> {
            try {
                while (true) {
                    moveStones();
                    updateStones();
                    Thread.sleep(50);

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        t.start();
    }


		
}

