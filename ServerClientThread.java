import java.util.*;

public class ServerClientThread extends Thread{
	MyConnection m;
	int playerNum;
	int clientIndex = 0;
	ArrayList<ServerClientThread> aSCT;
	public boolean start = false;
	boolean gameEnd = false;

	public ServerClientThread(MyConnection m, int playerNum, ArrayList<ServerClientThread> aSCT){
		this.m = m;
		this.playerNum = playerNum;
		this.aSCT = aSCT;
		aSCT.add(this);	
	}
	
	public void run(){		
		try{
			ServerClientThread tempSCT;
			boolean gameStart = false;
			while(!gameEnd){
				String msg = m.getMessage();
				if(msg != null){
					if(msg.indexOf("Setup Done") != -1){
						//System.out.println("" + playerNum + " done setting up.");
						if(aSCT.size() == 1){
							m.sendMessage("Still Alone");
						}
						else{
							for(int i = 0; i < aSCT.size(); i++){
								tempSCT = aSCT.get(i); 
								if(tempSCT != this){
									msg = msg.replace("Setup Done ", "Initialize OpponentBoard");
									tempSCT.m.sendMessage(msg);
									//System.out.println("Sent " + playerNum + "'s config.");
								}
							}
							start = true;
							for(int i = 0; i < aSCT.size(); i++){
								tempSCT = aSCT.get(i);
								if(tempSCT != this){
									//means tapos na magset up both players
									if(tempSCT.start){
										gameStart = true;
										//System.out.println("Start Game!");
									}
								}
							}
							if(gameStart){
								for(int i = 0; i < aSCT.size(); i++){
									tempSCT = aSCT.get(i);
									if(tempSCT.playerNum == 1){
										tempSCT.m.sendMessage("Your Turn");
										//System.out.println("" + tempSCT.playerNum + "'s turn.");
									}
								}
							}
						}
					}			

					if(msg.indexOf("My Move") != -1){
						for(int i = 0; i < aSCT.size(); i++){
							tempSCT = aSCT.get(i);
							if(tempSCT != this){
								msg = msg.replace("My Move", "Evaluate Opponent Turn");
								tempSCT.m.sendMessage(msg);
							}
						}
					}

					if(msg.equals("/quit")){
						for(int i = 0; i < aSCT.size(); i++){
							tempSCT = aSCT.get(i);
							if(tempSCT != this){
								msg = "Ayoko na.";
								tempSCT.m.sendMessage(msg);
								tempSCT.gameEnd = true;
							}
							else{
								msg = "Ayaw mo na.";
								tempSCT.m.sendMessage(msg);
							}
						}
						gameEnd = true;
					}

					if(msg.equals("I win")){
						for(int i = 0; i < aSCT.size(); i++){
							tempSCT = aSCT.get(i);
							if(tempSCT != this){
								tempSCT.m.sendMessage("You lose");
								//gameEnd = true;
							}
						}
					}	
					else if(msg.equals("I lose")){
						for(int i = 0; i < aSCT.size(); i++){
							tempSCT = aSCT.get(i);
							if(tempSCT != this){
								tempSCT.m.sendMessage("You win");
								//gameEnd = true;
							}
						}

					}	/*
					else if(msg.equals("Draw")){
						for(int i = 0; i < aSCT.size(); i++){
							tempSCT = aSCT.get(i);
							if(tempSCT != this){
								tempSCT.m.sendMessage("Draw");
								break;
							}
						}
					}	*/		


					//all server processing message from client stuff put here
				}
			}
			//System.out.println("Tapos na eh");
		}
		catch(Exception e){
			System.out.println("Server: Something bad happened :(");
			//e.printStackTrace();
		}
	}
}