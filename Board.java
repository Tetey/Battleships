import java.util.*;

public class Board{
	int[][] myBoard; 
	int[][] opponentBoard;
	ArrayList<Ship> myShips = new ArrayList<Ship>();
	String gameStatus;
	
	//PLAYER BOARD CONSTANTS
	protected static final int INITIAL = 0;
	protected static final int TILEDEAD = 1;
	protected static final int BOATDEAD = 2;
	protected static final int BOMBED = 3;

	public Board(){
		myBoard = new int[9][9]; //change to size of grid
		opponentBoard = new int[9][9]; //change to size of grid
		gameStatus = "init";
		
		Ship ship1 = new Ship(1);
		Ship ship2 = new Ship(1);
		Ship ship3 = new Ship(1);
		Ship ship4 = new Ship(2);
		Ship ship5 = new Ship(2);
		Ship ship6 = new Ship(3);
		Ship ship7 = new Ship(3);
		Ship ship8 = new Ship(4);

		myShips.add(ship1);
		myShips.add(ship2);
		myShips.add(ship3);
		myShips.add(ship4);
		myShips.add(ship5);
		myShips.add(ship6);
		myShips.add(ship7);
		myShips.add(ship8);
	}
}