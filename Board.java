import java.util.*;

public class Board{
	protected String[][] myBoard; 
	protected String[][] opponentBoard;
	//int[] opponentShips; // in order ung ships[ie. ung 0, 1, 2, puro oneblock ships]; if 0, not yet killed, if 1, killed
	public ArrayList<Ship> myShips = new ArrayList<Ship>();
	public ArrayList<Ship> opponentShips = new ArrayList<>();
	
	public String gameStatus;
	public String message = null;

	protected boolean isMyTurn = false;
	protected boolean isSetUpDone = false;

	//PLAYER BOARD CONSTANTS

	protected static final int INITIAL = 0; //ito lang kailangan ni tetey
	protected static final int ONEBLOCKSHIP = 1;
	protected static final int TWOBLOCKSHIP = 2;
	protected static final int THREEBLOCKSHIP = 3;
	protected static final int FOURBLOCKSHIP = 4;
	/*protected static final int TILEDEAD = 5; //ito lang kailangan ni tetey
	protected static final int BOMBED = 6; //ito lang kailangan ni tetey
	
	protected static final int BOATDEAD1 = 7;
	protected static final int BOATDEAD2 = 8;
	protected static final int BOATDEAD3 = 9;
	protected static final int BOATDEAD4 = 10;
	*/

	protected static final String TILEDEAD = "TILEDEAD";
	protected static final String BOMBED = "BOMBED";



	public Board(){
		myBoard = new String[10][10]; //change to size of grid
		opponentBoard = new String[10][10]; //change to size of grid
		gameStatus = "init";
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				myBoard[i][j] = "null";
				opponentBoard[i][j] = "null";
			}
		}


		Ship ship1 = new Ship(ONEBLOCKSHIP, 1, "First");
		Ship ship2 = new Ship(ONEBLOCKSHIP, 2, "Second");
		Ship ship3 = new Ship(ONEBLOCKSHIP, 3, "Third");
		Ship ship4 = new Ship(TWOBLOCKSHIP, 4, "Fourth");
		Ship ship5 = new Ship(TWOBLOCKSHIP, 5, "Fifth");
		Ship ship6 = new Ship(THREEBLOCKSHIP, 6, "Sixth");
		Ship ship7 = new Ship(THREEBLOCKSHIP, 7, "Seventh");
		Ship ship8 = new Ship(FOURBLOCKSHIP, 8, "Eighth");

		myShips.add(ship1);
		myShips.add(ship2);
		myShips.add(ship3);
		myShips.add(ship4);
		myShips.add(ship5);
		myShips.add(ship6);
		myShips.add(ship7);
		myShips.add(ship8);

		for(int i = 0; i < 8; i++){
			Ship ship = new Ship();
			ship.sunk = 1;
			opponentShips.add(ship);
		}

	}

	//call as soon as done with setup
	public void setupDone(){
		setGameStatusToOngoing();
		initMyBoard();
		setMessageForSetupDone();
		//System.out.println(gameStatus);
		//System.out.println(message);
	}

	public void initMyBoard(){		
		Ship temp;
		for(int i = 0; i < myShips.size(); i++){
			temp = myShips.get(i);
			if(temp.size == ONEBLOCKSHIP){
				myBoard[temp.XPosition][temp.YPosition] = temp.shipName;
			}
			else if(temp.size == TWOBLOCKSHIP){
				myBoard[temp.XPosition][temp.YPosition] = temp.shipName;
				if(temp.isHorizontal){
					myBoard[temp.XPosition+1][temp.YPosition] = temp.shipName;
				}
				else{
					myBoard[temp.XPosition][temp.YPosition+1] = temp.shipName;
				}
			}
			else if(temp.size == THREEBLOCKSHIP){
				myBoard[temp.XPosition][temp.YPosition] = temp.shipName;
				if(temp.isHorizontal){
					myBoard[temp.XPosition+1][temp.YPosition] = temp.shipName;
					myBoard[temp.XPosition+2][temp.YPosition] = temp.shipName;
				}
				else{
					myBoard[temp.XPosition][temp.YPosition+1] = temp.shipName;
					myBoard[temp.XPosition][temp.YPosition+2] = temp.shipName;
				}
			}
			else if(temp.size == FOURBLOCKSHIP){
				myBoard[temp.XPosition][temp.YPosition] = temp.shipName;
				if(temp.isHorizontal){
					myBoard[temp.XPosition+1][temp.YPosition] = temp.shipName;
					myBoard[temp.XPosition+2][temp.YPosition] = temp.shipName;
					myBoard[temp.XPosition+3][temp.YPosition] = temp.shipName;
				}
				else{
					myBoard[temp.XPosition][temp.YPosition+1] = temp.shipName;
					myBoard[temp.XPosition][temp.YPosition+2] = temp.shipName;
					myBoard[temp.XPosition][temp.YPosition+3] = temp.shipName;
				}
			}  

		}
	}

	public void initOpponentBoard(){
		Ship temp;
		for(int i = 0; i < opponentShips.size(); i++){
			temp = opponentShips.get(i);
			if(temp.size == ONEBLOCKSHIP){
				opponentBoard[temp.XPosition][temp.YPosition] = temp.shipName;
			}
			else if(temp.size == TWOBLOCKSHIP){
				opponentBoard[temp.XPosition][temp.YPosition] = temp.shipName;
				if(temp.isHorizontal){
					opponentBoard[temp.XPosition+1][temp.YPosition] = temp.shipName;
				}
				else{
					opponentBoard[temp.XPosition][temp.YPosition+1] = temp.shipName;
				}
			}
			else if(temp.size == THREEBLOCKSHIP){
				opponentBoard[temp.XPosition][temp.YPosition] = temp.shipName;
				if(temp.isHorizontal){
					opponentBoard[temp.XPosition+1][temp.YPosition] = temp.shipName;
					opponentBoard[temp.XPosition+2][temp.YPosition] = temp.shipName;
				}
				else{
					opponentBoard[temp.XPosition][temp.YPosition+1] = temp.shipName;
					opponentBoard[temp.XPosition][temp.YPosition+2] = temp.shipName;
				}
			}
			else if(temp.size == FOURBLOCKSHIP){
				opponentBoard[temp.XPosition][temp.YPosition] = temp.shipName;
				if(temp.isHorizontal){
					opponentBoard[temp.XPosition+1][temp.YPosition] = temp.shipName;
					opponentBoard[temp.XPosition+2][temp.YPosition] = temp.shipName;
					opponentBoard[temp.XPosition+3][temp.YPosition] = temp.shipName;
				}
				else{
					opponentBoard[temp.XPosition][temp.YPosition+1] = temp.shipName;
					opponentBoard[temp.XPosition][temp.YPosition+2] = temp.shipName;
					opponentBoard[temp.XPosition][temp.YPosition+3] = temp.shipName;
				}
			}  
		}
	}

	public void setMessageForSetupDone(){
		String messageToSend = "Setup Done";
		Ship temp;
		for(int i = 0; i < myShips.size(); i++){
			temp = myShips.get(i);
			messageToSend = messageToSend + " " + temp.shipName + " " + Integer.toString(temp.size) + " " + temp.isHorizontal + " " + Integer.toString(temp.XPosition) + " " + Integer.toString(temp.YPosition); 
		}
		message = "" + messageToSend;
	}

	public void setOpponentShips(String str){
		String[] ships = str.split(" ");
		int index = 0;

		opponentShips.clear();
		for(int i = 0; i < 8; i++){
			index = i*5;
			Ship ship = new Ship();
			ship.shipName = ships[index];
			ship.size = Integer.parseInt(ships[index+1]);
			ship.sunk = ship.size;
			if(ships[index+2].equals("true")){
				ship.isHorizontal = true;
			}
			else{
				ship.isHorizontal = false;
			}
			ship.XPosition = Integer.parseInt(ships[index+3]);
			ship.YPosition = Integer.parseInt(ships[index+4]);
			opponentShips.add(ship);
		}
		initOpponentBoard();
	}

	public void sendMoveToOpponent(int XPosition, int YPosition){
		message = "My Move" + Integer.toString(XPosition) + " " + Integer.toString(YPosition);
	}


	public void evaluateMyTurn(int x, int y){
		if(opponentBoard[x][y].equals("null")){
			//set to bombed
			opponentBoard[x][y] = BOMBED;
			setMyTurn(false);
			System.out.println("Not my turn.");
			System.out.println("Naset ko na ba turn ko? " + isMyTurn);
		}
		else{
			String tempShipName = opponentBoard[x][y];
			Ship tempShip;
			System.out.println("SIZE!!!! of opponentShips KO : " + opponentShips.size());
			for(int i = 0; i < opponentShips.size(); i++){
				tempShip = opponentShips.get(i);
				if(tempShip.shipName.equals(tempShipName)){
					tempShip.sunk--;
					//set opponentboard to tileDead
					opponentBoard[x][y] = TILEDEAD;
					System.out.println("Still my turn.");
					System.out.println("Naset ko na ba turn ko? " + isMyTurn);
					break;
				}
			}
		}
		sendMoveToOpponent(x, y);
		//automatically check for win or lose
	}

	public void evaluateOpponentTurn(int x, int y){
		if(myBoard[x][y].equals("null")){
			//set to bombed
			myBoard[x][y] = BOMBED;
			System.out.println("My turn!");
			setMyTurn(true);		
			System.out.println("Naset ko na ba turn ko? " + isMyTurn);
		}
		else{
			String tempShipName = myBoard[x][y];
			Ship tempShip;
			for(int i = 0; i < myShips.size(); i++){
				tempShip = myShips.get(i);
				System.out.println(tempShip.shipName + " " + tempShipName + " " + tempShip.sunk);
				if(tempShip.shipName.equals(tempShipName)){
					tempShip.sunk--;
					//set myBoard to tileDead
					System.out.println("Still not my turn.");
					System.out.println("Naset ko na ba turn ko? " + isMyTurn);
					myBoard[x][y] = TILEDEAD;
					break;
				}
			}
		}
		//automatically check for win or lose
		checkIfWinLoseDraw();
	}

	//condition for draw?, pag ubos na tiles
	public void checkIfWinLoseDraw(){
		Ship temp;
		boolean iWin = true;
		boolean	opponentWin = true;
		for(int i = 0; i < myShips.size(); i++){
			temp = myShips.get(i);
			if(temp.sunk > 0){
				opponentWin = false;
				break;
			}
		}
		if(opponentWin){
			setGameStatusToLose();
			iWin = false;
			message = "I lose";
			setMyTurn(false);
			//send message na end game na, win lose
		}
		else{
			for(int i = 0; i < opponentShips.size(); i++){
				temp = opponentShips.get(i);
				if(temp.sunk != 0){
					iWin = false;
					//System.out.println(temp.sunk);
				}
			}
			if(iWin){
				setGameStatusToWin();
				opponentWin = false;
				message = "I win";
				setMyTurn(false);
				//send message na end game na, win lose
			}
			/*else{
				boolean draw = true;

				for(int i = 0; i < 10; i++){
					for(int j = 0; j < 10; j++){
						if(myBoard[i][j].equals("null")){
							draw = false;
						}
					}
				}

				for(int i = 0; i < 10; i++){
					for(int j = 0; j < 10; j++){
						if(opponentBoard[i][j].equals("null")){
							draw = false;
						}
					}
				}

				if(draw){
					setGameStatusToDraw();
					message = "Draw";
					setMyTurn(false);
					//send message an end game na, draw
				}
			}*/
		}

		

	}

	public void setMyTurn(boolean b){
		isMyTurn = b;
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

	public void setGameStatusToDraw(){
		gameStatus = "draw";
	}

}