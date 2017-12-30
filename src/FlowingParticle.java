import java.awt.Paint;
import java.awt.Point;

public interface FlowingParticle {

	public void move();
	public void setXVelocity(int xVel);
	public void setYVelocity(int yVel);
	public Point getVelocity();
	public void setOnGround(boolean onGround);
	public void changeXVelocity(int xVel);
	public void changeYVelocity(int yVel);

}
