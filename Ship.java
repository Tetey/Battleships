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
			XPosition = 0;
			YPosition = 1;
			break;
		case 2:
			XPosition = 0;
			YPosition = 4;
			break;
		case 3:
			XPosition = 5;
			YPosition = 6;
			break;
		case 4:
			XPosition = 4;
			YPosition = 2;
			break;
		case 5:
			XPosition = 7;
			YPosition = 6;
			isHorizontal = false;
			break;
		case 6:
			XPosition = 1;
			YPosition = 7;
			break;
		case 7:
			XPosition = 8;
			YPosition = 0;
			isHorizontal = false;
			break;
		case 8:
			XPosition = 3;
			YPosition = 4;
			break;
		}
	}
}