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

    private static movingStones stone_1;
    private static movingStones stone_2;
    private static movingStones stone_3;
    private static movingStones stone_4;

	public static void main(String[] args) throws IOException {
		
		Miner miner = new Miner(212, 550, 150, 150);
        MineCarts[] mineCartsArray = {minecart_1, minecart_2, minecart_3, minecart_4};
        mineCartsArray[0] = new MineCarts(375,515,80,80);
        mineCartsArray[1] = new MineCarts(480,415,80,80);
        mineCartsArray[2] = new MineCarts(175,315,80,80);
        mineCartsArray[3] = new MineCarts(75,415,80,80);

        movingStones[] stonesArray = {stone_1, stone_2, stone_3, stone_4};
        stonesArray[0] = new movingStones(360,160,50,50);
        stonesArray[1] = new movingStones(0,210,50,50);
        stonesArray[2] = new movingStones(300,210,50,50);
        stonesArray[3] = new movingStones(210,260,50,50);
		
		
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
							
							MinerGameServerService myService = new MinerGameServerService (s, miner, mineCartsArray, stonesArray);
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

