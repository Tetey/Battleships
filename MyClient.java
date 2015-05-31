
import java.io.*;
import java.util.*;
import java.net.*;

public class MyClient{
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		try{
			String serverIP;
			String portNumberString;
			int portNumber;

			System.out.print("Enter Server IP Address: ");
			serverIP = scanner.nextLine();
			System.out.print("Enter port number: ");
			portNumberString = scanner.nextLine();
			portNumber = Integer.parseInt(portNumberString);
			Socket s = new Socket(serverIP, portNumber);
			System.out.println("GAME START!");
			Board board = new Board();
			BoardUI boardUI = new BoardUI(board);
			MyConnection m = new MyConnection(s);		
			SendingThread st = new SendingThread(m, board, boardUI);
			ReceivingThread rt = new ReceivingThread(m, board, boardUI);
			st.start();
			rt.start();
			String quit = "";

			while(!quit.equals("/quit")){
				System.out.println("---Type /quit to end the game--- ");
				quit = scanner.nextLine();
			}

			m.sendMessage(quit);
		}
		catch(Exception e){
			System.out.println("Client: Something bad happened :(");
			e.printStackTrace();
		}
	}
	
	public static void endClient(boolean b){
		//System.out.println()
		if(b){
			System.out.println("You ended the game.");
		}
		else{
			System.out.println("Your opponent has ended the game.");
		}
		System.exit(0);
	}
}