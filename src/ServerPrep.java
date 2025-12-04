import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;

public class ServerPrep {
    
	final static int SERVER_PORT = 5556;
	final static int CLIENT_PORT = 5656;

	public static void main(String[] args) throws IOException {
		
		Miner miner = new Miner(212, 550, 150, 150);
		
		
		Thread t1 = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized(this) {

					ServerSocket server;
					try {
						
						server = new ServerSocket(SERVER_PORT);
						System.out.println("Waiting for clients to connect...");
						while(true) {
							Socket s = server.accept();
							System.out.println("client connected");
							
							MinerGameServerService myService = new MinerGameServerService (s, miner);
							Thread t2 = new Thread(myService);
							t2.start();
						}
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
		
				}
			}
		});
		t1.start( );
	}
    
}

