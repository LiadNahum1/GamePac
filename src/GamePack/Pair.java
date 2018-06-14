package GamePack;


/*The class defines a pair */
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
	public int sumSetX(int x) {
		return this.x= this.x + x;
	}
	
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
