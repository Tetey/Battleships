import java.io.*;

public class ReceivingThread extends Thread{
	MyConnection m;
	Board board;
	
	public ReceivingThread(MyConnection m, Board board){
		this.m = m;
		this.board = board;
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