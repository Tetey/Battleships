import java.util.*;

public class ServerClientThread extends Thread{
	MyConnection m;
	int playerNum;
	int clientIndex = 0;
	ArrayList<ServerClientThread> aSCT;
	public boolean start = false;
	
	public ServerClientThread(MyConnection m, int playerNum, ArrayList<ServerClientThread> aSCT){
		this.m = m;
		this.playerNum = playerNum;
		this.aSCT = aSCT;
		aSCT.add(this);	
	}
	
	public void run(){		
		try{
			ServerClientThread tempSCT;
			while(true){
				String msg = m.getMessage();
				if(msg.indexOf("Setup Done") != -1){
					if(aSCT.size() == 1){
						m.sendMessage("Still Alone");
					}
					else{
						for(int i = 0; i < aSCT.size(); i++){
							tempSCT = aSCT.get(i); 
							if(tempSCT != this){
								msg = msg.replace("Setup Done ", "Initialize OpponentBoard");
								tempSCT.m.sendMessage(msg);
							}
						}
					}
				}
				if(msg.indexOf("I am ready!") != -1){
					
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