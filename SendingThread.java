public class SendingThread extends Thread{
	String input;
	MyConnection m;
	Board board;
	BoardUI boardUI;

	public SendingThread(MyConnection m, Board board, BoardUI boardUI){
		this.m = m;
		this.board = board;
		this.boardUI = boardUI;
	}
	
	public void run(){
		try{
			while(true){
				Thread.sleep(1000);
				input = board.message;
				System.out.println(input);
				//System.out.println("Message: " + board.message);
				if(input != null){	
					m.sendMessage(input);
					board.message = null;
				}
			}
		}
		catch(Exception e){
			System.out.println("Client: Something went wrong... :(");
			e.printStackTrace();
		}	
	}
}