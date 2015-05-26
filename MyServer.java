import java.io.*;
import java.net.*;
import java.util.*;

public class MyServer{
	
	public static void main(String args[]) {
		try{
			System.out.println("S: Starting server...");
			ServerSocket ssocket = new ServerSocket(8888);
			System.out.println("S: Waiting for connections...");
			//ArrayList<ServerClientThread> aSCT = new ArrayList<ServerClientThread>();
			
			int counter = 1;
			
			while(true){
				Socket s = ssocket.accept();
				MyConnection m = new MyConnection(s);
				String clientName = "Player" + counter;
				ServerClientThread sct = new ServerClientThread(m, clientName);
				sct.start();
				counter++;
			}
		}
		catch (Exception e){
			System.out.println("Server: Something bad happened :(");
			e.printStackTrace();
		}
	}
}