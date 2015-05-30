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
				if(msg != null){
					if(msg.equals("Still Alone")){
						board.setMessageForSetupDone();
					}
					else if(msg.indexOf("Initialize OpponentBoard") != -1){
						msg = msg.replace("Initialize OpponentBoard", "");
						board.setOpponentShips(msg);
					}
					else if(msg.equals("Your Turn")){
						board.setMyTurn(true);
					}
					else if(msg.indexOf("Evaluate Opponent Turn") != -1){
						msg = msg.replace("Evaluate Opponent Turn", "");
						String[] temp = msg.split(" ");
						int x = Integer.parseInt(temp[0]);
						int y = Integer.parseInt(temp[1]);
						board.evaluateOpponentTurn(x, y);
						boardUI.update();
					}
					else if(msg.equals("You win")){
						board.setGameStatusToWin();
						board.setMyTurn(false);
					}
					else if(msg.equals("You lose")){
						board.setGameStatusToLose();
						board.setMyTurn(false);
					}
					else if(msg.equals("Draw")){
						board.setGameStatusToDraw();
						board.setMyTurn(false); //change to variable like, end game?
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