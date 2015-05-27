public class Ship{
	public int size; //1, 2, 3, 4
	public boolean isHorizontal; //horizontal, vertical
	public int sunk; //1 if sunk 

	public int XPosition;
	public int YPosition;

	public Ship(int size, int posInit){
		
		this.size = size;
		isHorizontal = true; //change to default orientation of ship
		sunk = 0;
		
		switch(posInit){
		case 1:
			XPosition = 2;
			YPosition = 5;
			break;
		case 2:
			XPosition = 2;
			YPosition = 7;
			break;
		case 3:
			XPosition = 6;
			YPosition = 9;
			break;
		case 4:
			XPosition = 9;
			YPosition = 3;
			break;
		case 5:
			XPosition = 8;
			YPosition = 0;
			break;
		case 6:
			XPosition = 2;
			YPosition = 9;
			break;
		case 7:
			XPosition = 2;
			YPosition = 2;
			isHorizontal = false;
			break;
		case 8:
			XPosition = 7;
			YPosition = 4;
			isHorizontal = false;
			break;
		
		}
	}
}