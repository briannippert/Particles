import java.awt.Point;

import wheelsunh.users.Frame;
import wheelsunh.users.Rectangle;

public class Cube extends Rectangle implements RidgedBody {

	public Cube(int xLocation, int yLocation, int angle) {
		this.setSize(20, 20);
		this.setLocation(xLocation, yLocation);
		this.setRotation(angle);
	}

	/**
	 * Checks to see if the shape contains a point.
	 * @param point Point to check.
	 */
	public synchronized boolean contains(Point point) {
		if (super.contains(point) == true) {
			return true;
		} else {
			return false;
		}

	}
	
	public static void main(String[] args)
	{
		new Frame();
		Cube c11 = new Cube (100,100,45);
		c11.setSize(500,500);
		System.out.println();
		System.out.println("Should be FALSE: " + c11.contains(new Point(50,50)));
		System.out.println("Should be TRUE: " + c11.contains(new Point(600,500)));
		System.out.println("Should be TRUE: " + c11.contains(new Point(1000,1000)));
		System.out.println( "Should be FALSE: " + c11.contains(new Point(0,0)));
	}

}
