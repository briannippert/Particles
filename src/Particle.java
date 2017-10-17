import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import wheelsunh.users.Ellipse;

/**
 * Particle Class
 * 
 * @author Brian
 *
 */
public class Particle extends Ellipse {
	int _xVelocity;
	int _yVelocity;
	boolean _onGround = false;

	/**
	 * Creates new particle class. Extends Ellipse
	 * 
	 * @param xVelocity
	 *            integer
	 * @param yVelocity
	 *            integer
	 * @param location
	 *            point
	 */
	public Particle(int xVelocity, int yVelocity, Point location) {
		_xVelocity = xVelocity;
		_yVelocity = yVelocity;
		Random r1 = new Random();
		this.setSize(10, 10);
		this.setLocation(location);
		this.setColor(new Color(r1.nextInt(255), r1.nextInt(255), r1.nextInt(255), r1.nextInt(255)));
	}

	/**
	 * Changes X velocity by x.
	 * 
	 * @param x
	 *            integer
	 */
	public void changeXVelocity(int x) {
		_xVelocity += x;

	}

	/**
	 * Changes Y velocity by y.
	 * 
	 * @param y
	 *            integer
	 */
	public void changeYVelocity(int y) {
		_yVelocity += y;
	}

	/**
	 * Sets X velocity.
	 * 
	 * @param x
	 *            integer
	 */
	public void setXVelocity(int x) {
		_xVelocity = x;
	}

	/**
	 * Sets the Y velocity.
	 * 
	 * @param y
	 *            integer
	 */
	public void setYVelocity(int y) {
		_yVelocity = y;
	}

	/**
	 * Returns velocity of the particle.
	 * 
	 * @return point velocity
	 */
	public Point getVelocity() {

		return new Point(_xVelocity, _yVelocity);

	}

	/**
	 * returns the next location of the particle.
	 * 
	 * @return
	 */
	public Point nextLocation() {
		return new Point(this.getXLocation() + _xVelocity, this.getYLocation() + _yVelocity);
	}

	public void setOnGround(boolean onGround) {
		_onGround = onGround;
	}

	/**
	 * Moves the particle by its velocity.
	 */
	public void move() {
		if(_onGround == false)
		{
			this.setLocation(this.getXLocation() + _xVelocity, this.getYLocation() + _yVelocity);	
		}
		else
		{
			this.setLocation(this.getXLocation() + _xVelocity, this.getYLocation());	
		}
		
	}

}
