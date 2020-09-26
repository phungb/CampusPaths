package hw8;

public class Building {
	/**
	 * Abstract Function: A building that has abreviated name , shortName,
	 * 										  full name of longName
	 * 										  and that is located in coordinates x, y	
	 * 
	 */
	
	
	private String shortName;
	private String longName;
	private double x;
	private double y;
	
	/**
	 * constructs a new building
	 * 
	 * @param shortName 
	 * 		abbreviation name of building
	 * @param longName 
	 * 		full name of the building
	 * @param x
	 * 		x coordinate of the building
	 * @param y 
 	 *		y coordinate of the building
 	 * @requires shortName != null & longName != null
	 */
	public Building(String shortName, String longName, double x, double y) {
		this.shortName = shortName;
		this.longName = longName;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @return shortName of building
	 */
	public String getShortName() {
		return this.shortName;
	}
	
	/**
	 * 
	 * @return longName of building
	 */
	public String getLongName() {
		return this.longName;
	}
	
	/**
	 * 
	 * @return x coordinate of building
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * 
	 * @return y coordinate of building
	 */
	public double getY() {
		return this.y;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Building)) {
			return false;
		}
		Building temp = (Building) o;
		return (temp.x == this.x) && (temp.y == this.y) &&
			   (this.shortName.equals(temp.shortName)) &&
			   (this.longName.equals(temp.longName));
	}
	
	public int hashCode() {
		return (int) ((int) 37 * (shortName.hashCode() + 37 * longName.hashCode() * 37 * (x + 37 * y)));
	}
	
	public String toString() {
		return Double.toString(x) + " " + Double.toString(y);
	}
}
