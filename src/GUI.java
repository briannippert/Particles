import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import wheelsunh.users.Frame;
import wheelsunh.users.Line;
import wheelsunh.users.Rectangle;

/**
 * GUI implements a particle fountain.
 * 
 * @author Brian
 *
 */
public class GUI implements KeyListener, Runnable {
	Frame _f1;
	public List<Particle> _particles1, _particles2, _particles3, _particles4;
	double _gravity = 1;
	double _windResistance = 1;
	Random _rand;

	/**
	 * Constructor for GUI Class
	 */
	@SuppressWarnings("unchecked")
	public GUI() {
		_f1 = new Frame();
		_rand = new Random();
		_particles1 = Collections.synchronizedList(new ArrayList());
		_particles2 = Collections.synchronizedList(new ArrayList());
		_particles3 = Collections.synchronizedList(new ArrayList());
		_particles4 = Collections.synchronizedList(new ArrayList());
		_f1.addKeyListener(this);
		// Line vertLine = new Line(Frame.getWidth() / 2, 0, Frame.getWidth() /
		// 2, Frame.getHeight());
		// Line horzLine = new Line(0, Frame.getHeight() / 2, Frame.getWidth(),
		// Frame.getHeight() / 2);
		// vertLine.setColor(Color.BLACK);
		// horzLine.setColor(Color.BLACK);
		this.run();
	}

	/**
	 * Unimplemented Key Typed method. Needed for implementing KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	/**
	 * Key Pressed Event. Triggers particle fountain at mouse pointer location
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == ' ') {
			if (_particles1.size() >= _MAXPARTICLES) {
				return;
			}
			try {
				if (MouseInfo.getPointerInfo().getLocation().x > _f1.getWidth()
						|| MouseInfo.getPointerInfo().getLocation().y > _f1.getHeight()
						|| MouseInfo.getPointerInfo().getLocation().x < 0
						|| MouseInfo.getPointerInfo().getLocation().y < 0) {
					return;
				}
					double velx = _rand.nextInt(18) - 9;
					double vely = _rand.nextInt(15) - 15;
					int mouseX = MouseInfo.getPointerInfo().getLocation().x - 15;
					int mouseY = MouseInfo.getPointerInfo().getLocation().y - 40;
					_particles1.add(new Particle((int) velx, (int) vely, new Point(mouseX, mouseY)));
				}

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}

	}

	/**
	 * Unimplemented Key Typed method. Needed for implementing KeyListener
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void assignParticleGroups(List Particles) {
		// Group 1 (0,0)-(ScreenWidth/2,ScreenHeight/2)
		// Group 2 (ScreenWidth/2, 0)-(screeenWidth, screenHeight/2)
		// Group 3 (0,ScreenHeight/2)-(screenWidth/2,screenHeight)
		// Group 4 (ScreenWidth/2,ScreenHeight/2)-(screenWidth, ScreenHeight)
		Rectangle r1, r2, r3, r4;
		r1 = new Rectangle();
		r1.setLocation(0, 0);
		r1.setSize(Frame.getWidth() / 2, Frame.getHeight() / 2);
		r1.setFillColor(new Color(0, 0, 0, 0));
		r1.setFrameColor(Color.BLACK);
		r2 = new Rectangle();
		r2.setLocation(Frame.getWidth() / 2, 0);
		r2.setSize(Frame.getWidth() / 2, Frame.getHeight() / 2);
		r2.setFillColor(new Color(0, 0, 0, 0));
		r2.setFrameColor(Color.RED);
		r3 = new Rectangle();
		r3.setLocation(0, Frame.getHeight() / 2);
		r3.setSize(Frame.getWidth() / 2, Frame.getHeight() / 2);
		r3.setFillColor(new Color(0, 0, 0, 0));
		r3.setFrameColor(Color.GREEN);
		r4 = new Rectangle();
		r4.setLocation(Frame.getWidth() / 2, Frame.getHeight() / 2);
		r4.setSize(Frame.getWidth() / 2, Frame.getHeight() / 2);
		r4.setFillColor(new Color(0, 0, 0, 0));
		r4.setFrameColor(Color.BLUE);
		synchronized (Particles) {
			Iterator i = _particles1.iterator();
			while (i.hasNext()) {
				Particle p = (Particle) i.next();
				Point nextPos = p.nextLocation();
				//// if()
			}
		}
	}

	/**
	 * Runing method for the thread
	 */
	@Override
	public void run() {

		while (true) {
			assignParticleGroups(_particles1);
			try {
				Thread.sleep(25);
				synchronized (_particles1) {
					Iterator i = _particles1.iterator();
					while (i.hasNext()) {
						Particle p = (Particle) i.next();
						if (p.getXLocation() <= 5 || p.getXLocation() >= Frame.getWidth() - 5) {
							p.setXVelocity(-p.getVelocity().x);
						}
						if (p.getYLocation() <= 0) {
							p.setYVelocity(-p.getVelocity().y);
						}
						p.changeYVelocity((int) _gravity);
						p.move();
							p = null;
							// p.setOnGround(true);
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	/**
	 * Main Method
	 * 
	 * @param args
	 *            Input Arguments
	 */
	public static void main(String args[]) {
		new GUI();
	}
}
