public class Ship{
	public int size; //1, 2, 3, 4
	public String orientation; //horizontal, vertical
	public int sunk; //1 if sunk 

	public int gridRowPos;
	public int gridColPos;

	public Ship(int size){
		this.size = size;
		orientation = "horizontal"; //change to default orientation of ship
		sunk = 0;
	}
}