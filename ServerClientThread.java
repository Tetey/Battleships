import java.util.*;

public class ServerClientThread extends Thread{
	MyConnection m;
	String clientName;
	int clientIndex = 0;
	
	
	public ServerClientThread(MyConnection m, String clientName){
		this.m = m;
		this.clientName = clientName;		
	}
	
	public void run(){		
		try{
			while(true){
				String msg = m.getMessage();
				//all server processing message from client stuff put here
			}
			
		}
		catch(Exception e){
			System.out.println("Server: Something bad happened :(");
			e.printStackTrace();
		}
	}
}