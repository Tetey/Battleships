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
			}
		}
		catch(Exception e){
			System.out.println("Client: Something bad happened... :(");
			e.printStackTrace();
		}
	}
}