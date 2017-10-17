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

/**
 * GUI implements a particle fountain.
 * 
 * @author Brian
 *
 */
public class GUI implements KeyListener, Runnable {
	AnimationTimer _timer;
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
		Line vertLine = new Line(Frame.getWidth() / 2, 0, Frame.getWidth() / 2, Frame.getHeight());
		Line horzLine = new Line(0, Frame.getHeight() / 2, Frame.getWidth(), Frame.getHeight() / 2);
		vertLine.setColor(Color.BLACK);
		horzLine.setColor(Color.BLACK);
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
			try {
				Thread.sleep(5);
				if (MouseInfo.getPointerInfo().getLocation().x > _f1.getWidth()
						|| MouseInfo.getPointerInfo().getLocation().y > _f1.getHeight()
						|| MouseInfo.getPointerInfo().getLocation().x < 0
						|| MouseInfo.getPointerInfo().getLocation().y < 0) {
					return;
				}
				for (int i = 0; i < 50; i++) {
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

	/**
	 * Runing method for the thread
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(15);
				// System.out.println(_particles1.size());
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
						if (p.getYLocation() >= Frame.getHeight() - 10) {
							// p = null;
							p.setOnGround(true);
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
