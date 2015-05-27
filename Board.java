import java.util.*;

public class Board{
	protected int[][] myBoard; 
	protected int[][] opponentBoard;
	//int[] opponentShips; // in order ung ships[ie. ung 0, 1, 2, puro oneblock ships]; if 0, not yet killed, if 1, killed
	public ArrayList<Ship> myShips = new ArrayList<Ship>();
	public ArrayList<Ship> opponentShips = new ArrayList<>();
	
	public String gameStatus;

	public String message = null;
	
	//PLAYER BOARD CONSTANTS

	protected static final int INITIAL = 0; //ito lang kailangan ni tetey
	protected static final int ONEBLOCKSHIP = 1;
	protected static final int TWOBLOCKSHIP = 2;
	protected static final int THREEBLOCKSHIP = 3;
	protected static final int FOURBLOCKSHIP = 4;
	protected static final int TILEDEAD = 5; //ito lang kailangan ni tetey
	protected static final int BOATDEAD = 7;
	protected static final int BOMBED = 6; //ito lang kailangan ni tetey

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
		
		myBoard[0][2]=TILEDEAD;
		myBoard[6][7]=BOMBED;
	}

	//call when done with setup
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
			}
			else if(temp.size == THREEBLOCKSHIP){
				myBoard[temp.XPosition][temp.YPosition] = THREEBLOCKSHIP;
			}
			else if(temp.size == FOURBLOCKSHIP){
				myBoard[temp.XPosition][temp.YPosition] = FOURBLOCKSHIP;
			}  
		}
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