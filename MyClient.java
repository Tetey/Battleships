import java.io.*;
import java.util.*;
import java.net.*;

public class MyClient{
	
	public static void main(String[] args){
		try{
			Socket s = new Socket("192.168.1.116", 8888);
			Board board = new Board();
			BoardUI boardUI = new BoardUI(board);
			MyConnection m = new MyConnection(s);		
			SendingThread st = new SendingThread(m, board, boardUI);
			ReceivingThread rt = new ReceivingThread(m, board, boardUI);
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