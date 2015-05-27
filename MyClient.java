import java.io.*;
import java.util.*;
import java.net.*;

public class MyClient{
	
	public static void main(String[] args){
		try{
			Socket s = new Socket("127.0.0.1", 8888);
			Board board = new Board();
			BoardUI myBoard = new BoardUI();
			MyConnection m = new MyConnection(s);		
			SendingThread st = new SendingThread(m, board);
			ReceivingThread rt = new ReceivingThread(m, board);
			st.start();
			rt.start();
		}
		catch(Exception e){
			System.out.println("Client: Something bad happened :(");
			e.printStackTrace();
		}
	}
	
	public static void endClient(){
		System.exit(0);
	}
}