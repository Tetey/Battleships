import java.util.*;

public class Board{
	protected int[][] myBoard; 
	protected int[][] opponentBoard;
	//int[] opponentShips; // in order ung ships[ie. ung 0, 1, 2, puro oneblock ships]; if 0, not yet killed, if 1, killed
	public ArrayList<Ship> myShips = new ArrayList<Ship>();
	//public ArrayList<Ship> opponentShips = new ArrayList<>();
	
	public String gameStatus;
	public String message = null;

	public boolean isMyTurn = false;
	public boolean isSetUpDone = false;

	//PLAYER BOARD CONSTANTS

	protected static final int INITIAL = 0; //ito lang kailangan ni tetey
	protected static final int ONEBLOCKSHIP = 1;
	protected static final int TWOBLOCKSHIP = 2;
	protected static final int THREEBLOCKSHIP = 3;
	protected static final int FOURBLOCKSHIP = 4;
	protected static final int TILEDEAD = 5; //ito lang kailangan ni tetey
	protected static final int BOMBED = 6; //ito lang kailangan ni tetey
	
	protected static final int BOATDEAD1 = 7;
	protected static final int BOATDEAD2 = 8;
	protected static final int BOATDEAD3 = 9;
	protected static final int BOATDEAD4 = 10;

	public Board(){
		myBoard = new int[10][10]; //change to size of grid
		opponentBoard = new int[10][10]; //change to size of grid
		gameStatus = "init";
		
		Ship ship1 = new Ship(ONEBLOCKSHIP, 1);
		Ship ship2 = new Ship(ONEBLOCKSHIP, 2);
		Ship ship3 = new Ship(ONEBLOCKSHIP, 3) ;
		Ship ship4 = new Ship(TWOBLOCKSHIP, 4);
		Ship ship5 = new Ship(TWOBLOCKSHIP, 5);
		Ship ship6 = new Ship(THREEBLOCKSHIP, 6);
		Ship ship7 = new Ship(THREEBLOCKSHIP, 7);
		Ship ship8 = new Ship(FOURBLOCKSHIP, 8);

		myShips.add(ship1);
		myShips.add(ship2);
		myShips.add(ship3);
		myShips.add(ship4);
		myShips.add(ship5);
		myShips.add(ship6);
		myShips.add(ship7);
		myShips.add(ship8);

	}

	//call as soon as done with setup
	public void setupDone(){
		setGameStatusToOngoing();

		Ship temp;
		for(int i = 0; i < myShips.size(); i++){
			temp = myShips.get(i);
			if(temp.size == ONEBLOCKSHIP){
				myBoard[temp.XPosition][temp.YPosition] = ONEBLOCKSHIP;
			}
			else if(temp.size == TWOBLOCKSHIP){
				myBoard[temp.XPosition][temp.YPosition] = TWOBLOCKSHIP;
				if(temp.isHorizontal){
					myBoard[temp.XPosition+1][temp.YPosition] = TWOBLOCKSHIP;
				}
				else{
					myBoard[temp.XPosition][temp.YPosition+1] = TWOBLOCKSHIP;
				}
			}
			else if(temp.size == THREEBLOCKSHIP){
				myBoard[temp.XPosition][temp.YPosition] = THREEBLOCKSHIP;
				if(temp.isHorizontal){
					myBoard[temp.XPosition+1][temp.YPosition] = THREEBLOCKSHIP;
					myBoard[temp.XPosition+2][temp.YPosition] = THREEBLOCKSHIP;
				}
				else{
					myBoard[temp.XPosition][temp.YPosition+1] = THREEBLOCKSHIP;
					myBoard[temp.XPosition][temp.YPosition+2] = THREEBLOCKSHIP;
				}
			}
			else if(temp.size == FOURBLOCKSHIP){
				myBoard[temp.XPosition][temp.YPosition] = FOURBLOCKSHIP;
				if(temp.isHorizontal){
					myBoard[temp.XPosition+1][temp.YPosition] = FOURBLOCKSHIP;
					myBoard[temp.XPosition+2][temp.YPosition] = FOURBLOCKSHIP;
					myBoard[temp.XPosition+3][temp.YPosition] = FOURBLOCKSHIP;
				}
				else{
					myBoard[temp.XPosition][temp.YPosition+1] = FOURBLOCKSHIP;
					myBoard[temp.XPosition][temp.YPosition+2] = FOURBLOCKSHIP;
					myBoard[temp.XPosition][temp.YPosition+3] = FOURBLOCKSHIP;
				}
			}  
		}
		setMessageForSetupDone();
	}

	public void setMessageForSetupDone(){
		message = "Setup Done";
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				message = message + " " + Integer.toString(myBoard[i][j]);
			}
		}
	}

	public void signalStart(){
		message = "I'm ready!";
	}

	public void setGameStatusToOngoing(){
		gameStatus = "ongoing";
	}

	public void setGameStatusToWin(){
		gameStatus = "win";
	}

	public void setGameStatusToLose(){
		gameStatus = "lose";
	}

}