import java.io.*;

public class ReceivingThread extends Thread{
	MyConnection m;
	Board board;
	BoardUI boardUI;

	public ReceivingThread(MyConnection m, Board board, BoardUI boardUI){
		this.m = m;
		this.board = board;
		this.boardUI = boardUI;
	}
	
	public void run(){
		try{
			while(true){
				String msg = m.getMessage();
				if(msg.equals("Still Alone")){
					board.setupDone();
				}
				else if(msg.indexOf("Initialize OpponentBoard") != -1){
					msg = msg.replace("Initialize OpponentBoard", "");
					String[] tempStringArray = msg.split(" ");
					for(int i = 0; i < 10; i++){
						for(int j = 0; j < 10; j++){
							int index = (i*10)+j;
							board.opponentBoard[i][j] = Integer.parseInt(tempStringArray[index]);
						}
					}
				}
			}
		}
		catch(Exception e){
			System.out.println("Client: Something bad happened... :(");
			e.printStackTrace();
		}
	}
}