package GamePack;


/*The class defines a pair of (x, y) which defines the place(cell) of an object in a matrix   */
public class Pair {
	private int x;
	private int y;
	/*constructor*/
	public Pair(int x, int y){
		this.x= x;
		this.y = y;
	}
	
	/*getters*/
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y; 
	}
	
	/*setters*/
	public int setX(int x) {
		return this.x= x;
	}
	
	public int setY(int y) {
		return this.y = y;
	}
	/*adds x to the x field*/
	public int sumSetX(int x) {
		return this.x= this.x + x;
	}
	/*adds y to the y field*/
	public int sumSetY(int y) {
		return this.y = this.y + y;
	}
	@Override
	public boolean equals (Object other) {
		if(!(other instanceof Pair))
			return false;
		if(this.x != ((Pair) other).getX())
			return false;
		if(this.y != ((Pair) other).getY())
			return false;
		return true;
	}
}
