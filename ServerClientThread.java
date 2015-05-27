import java.util.*;

public class ServerClientThread extends Thread{
	MyConnection m;
	int playerNum;
	int clientIndex = 0;
	ArrayList<ServerClientThread> aSCT;
	
	public ServerClientThread(MyConnection m, int playerNum, ArrayList<ServerClientThread> aSCT){
		this.m = m;
		this.playerNum = playerNum;
		this.aSCT = aSCT;
		aSCT.add(this);	
	}
	
	public void run(){		
		try{
			while(true){
				String msg = m.getMessage();
				if(msg.equals("Setup Done")){
					if(aSCT.size() == 1){
						m.sendMessage("Still Alone");
					}
				}
				//all server processing message from client stuff put here
			}
			
		}
		catch(Exception e){
			System.out.println("Server: Something bad happened :(");
			e.printStackTrace();
		}
	}
}