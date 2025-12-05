import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;

public class ServerPrep {
    
	final static int SERVER_PORT = 5556;
	final static int CLIENT_PORT = 5656;

    private static MineCarts minecart_1;
    private static MineCarts minecart_2;
    private static MineCarts minecart_3;
    private static MineCarts minecart_4;

	public static void main(String[] args) throws IOException {
		
		Miner miner = new Miner(212, 550, 150, 150);
        MineCarts[] mineCartsArray = {minecart_1, minecart_2, minecart_3, minecart_4};
		
		
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
							
							MinerGameServerService myService = new MinerGameServerService (s, miner, mineCartsArray);
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

