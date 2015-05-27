public class SendingThread extends Thread{
	String input;
	MyConnection m;
	Board board;

	public SendingThread(MyConnection m, Board board){
		this.m = m;
		this.board = board;
	}
	
	public void run(){
		try{
			while(true){
				input = board.message;
				if(input != null){	
					boolean sent = m.sendMessage(input);
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