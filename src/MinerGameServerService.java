import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MinerGameServerService implements Runnable {
	
	final static int CLIENT_PORT = 5656;
	
	private Socket s;
	private Scanner in;
	private Miner miner;
	
	public MinerGameServerService(Socket s, Miner miner) {
		this.s = s;
		this.miner = miner;
	}

	public void run() {
		try {
			in = new Scanner(s.getInputStream());
			processRequest( );
			
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
			
			try (Socket s2 = new Socket("localhost", CLIENT_PORT);
		         PrintWriter out = new PrintWriter(s2.getOutputStream(), true)) {

		        String commandOut = "MINER " + miner.getX() + " " + miner.getY();
		        System.out.println("Sending: " + commandOut);
		        out.println(commandOut);

		    } catch (IOException e) {
		        e.printStackTrace();
		        
		    }

		}
			
	}
		
}

