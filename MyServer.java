import java.net.*;
import java.util.*;

public class MyServer{
	
	public static void main(String args[]) {
		try{
			System.out.println("S: Starting server...");
			ServerSocket ssocket = new ServerSocket(8888);
			System.out.println("S: Waiting for connections...");
			ArrayList<ServerClientThread> aSCT = new ArrayList<ServerClientThread>();
			
			int counter = 1;
			
			while(true){
				Socket s = ssocket.accept();
				MyConnection m = new MyConnection(s);
				int playerNum = counter;
				ServerClientThread sct = new ServerClientThread(m, playerNum, aSCT);
				sct.start();
				counter++;
				if(counter > 2){
					System.out.println("Max players connected! Can't accept any more players.");
					break;
				}
			}
		}
		catch (Exception e){
			System.out.println("Server: Something bad happened :(");
			//e.printStackTrace();
		}
	}
}