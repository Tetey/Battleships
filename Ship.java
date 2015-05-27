public class Ship{
	int size; //1, 2, 3, 4
	String orientation; //horizontal, vertical
	int XPosition;
	int YPosition;

	public Ship(int size){
		this.size = size;
		orientation = "horizontal"; //change to default orientation of ship
	}
}