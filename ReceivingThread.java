import java.io.*;

public class ReceivingThread extends Thread{
	MyConnection m;
	
	public ReceivingThread(MyConnection m){
		this.m = m;
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