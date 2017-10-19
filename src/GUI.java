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
import wheelsunh.users.TextBox;

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

	Rectangle _r1, _r2, _r3, _r4;
	TextBox _t1, _t2, _t3, _t4;

	static int _MAXPARTICLES = 500;
	static int _EMITERRATE = 10;

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
		_t1 = new TextBox();
		_t1.setLocation(0, 0);
		_t2 = new TextBox();
		_t2.setLocation(Frame.getWidth() - _t2.getWidth(), 0);
		_t3 = new TextBox();
		_t3.setLocation(0, Frame.getHeight() - _t2.getHeight());
		_t4 = new TextBox();
		_t4.setLocation(Frame.getWidth() - _t2.getWidth(), Frame.getHeight() - _t2.getHeight());
		_f1.addKeyListener(this);
		// this.run();
		_r1 = new Rectangle();
		_r1.setLocation(0, 0);
		_r1.setSize(Frame.getWidth() / 2, Frame.getHeight() / 2);
		_r1.setFillColor(new Color(0, 0, 0, 0));
		_r1.setFrameColor(Color.BLACK);
		_r2 = new Rectangle();
		_r2.setLocation(Frame.getWidth() / 2, 0);
		_r2.setSize(Frame.getWidth() / 2, Frame.getHeight() / 2);
		_r2.setFillColor(new Color(0, 0, 0, 0));
		_r2.setFrameColor(Color.RED);
		_r3 = new Rectangle();
		_r3.setLocation(0, Frame.getHeight() / 2);
		_r3.setSize(Frame.getWidth() / 2, Frame.getHeight() / 2);
		_r3.setFillColor(new Color(0, 0, 0, 0));
		_r3.setFrameColor(Color.GREEN);
		_r4 = new Rectangle();
		_r4.setLocation(Frame.getWidth() / 2, Frame.getHeight() / 2);
		_r4.setSize(Frame.getWidth() / 2, Frame.getHeight() / 2);
		_r4.setFillColor(new Color(0, 0, 0, 0));
		_r4.setFrameColor(Color.BLUE);
		Thread t1, t2, t3, t4, t5;
		t1 = new Thread(new ParticleWorker(1));
		t2 = new Thread(new ParticleWorker(2));
		t3 = new Thread(new ParticleWorker(3));
		t4 = new Thread(new ParticleWorker(4));
		t5 = new Thread(new BallCounter());
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		// run(); // Uncomment for Single Threaded operation
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
				for (int i = 0; i < _EMITERRATE; i++) {
					int mouseX = MouseInfo.getPointerInfo().getLocation().x - 20;
					int mouseY = MouseInfo.getPointerInfo().getLocation().y - 40;
					Point mouse = new Point(mouseX, mouseY);
					double velx = _rand.nextInt(18) - 9;
					double vely = _rand.nextInt(15) - 15;
					synchronized (_particles1) {
						if (_r1.contains(mouse)) {
							_particles1.add(new Particle((int) velx, (int) vely, new Point(mouseX, mouseY)));
							continue;
						}
					}
					synchronized (_particles2) {
						if (_r2.contains(mouse)) {
							_particles2.add(new Particle((int) velx, (int) vely, new Point(mouseX, mouseY)));
							continue;
						}
					}
					synchronized (_particles3) {
						if (_r3.contains(mouse)) {
							_particles3.add(new Particle((int) velx, (int) vely, new Point(mouseX, mouseY)));
							continue;
						}
					}
					synchronized (_particles4) {
						if (_r4.contains(mouse)) {
							_particles4.add(new Particle((int) velx, (int) vely, new Point(mouseX, mouseY)));
							continue;
						}
					}

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

	public synchronized void assignParticleGroups(int ListNum) {
		List particles = null;
		switch (ListNum) {
		case 1:
			particles = _particles1;
			break;
		case 2:
			particles = _particles2;
			break;
		case 3:
			particles = _particles3;
			break;
		case 4:
			particles = _particles4;
			break;
		default:
			return;
		}
		// Group 1 (0,0)-(ScreenWidth/2,ScreenHeight/2)
		// Group 2 (ScreenWidth/2, 0)-(screeenWidth, screenHeight/2)
		// Group 3 (0,ScreenHeight/2)-(screenWidth/2,screenHeight)
		// Group 4 (ScreenWidth/2,ScreenHeight/2)-(screenWidth, ScreenHeight)

		try {
			synchronized (particles) {
				Iterator i = particles.iterator();
				while (i.hasNext()) {
					Particle p = (Particle) i.next();
					Point nextPos = p.nextLocation();
					if (_r1.contains(nextPos) && ListNum != 1) {
						_particles1.add(p);
						i.remove();
						return;
					}
					if (_r2.contains(nextPos) && ListNum != 2) {
						_particles2.add(p);
						i.remove();
						return;
					}
					if (_r3.contains(nextPos) && ListNum != 3) {
						_particles3.add(p);
						i.remove();
						return;
					}
					if (_r4.contains(nextPos) && ListNum != 4) {
						_particles4.add(p);
						i.remove();
						return;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public synchronized void moveParticles(int num) {
		List particles = null;
		switch (num) {
		case 1:
			particles = _particles1;
			break;
		case 2:
			particles = _particles2;
			break;
		case 3:
			particles = _particles3;
			break;
		case 4:
			particles = _particles4;
			break;
		default:
			return;
		}
		synchronized (particles) {
			Iterator i = particles.iterator();
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
				if (p.getYLocation() >= Frame.getHeight() + 20) {
					p = null;
					i.remove();
					// p.setOnGround(true);
				}
			}
		}
	}

	/**
	 * Runing method for the thread
	 */
	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(16);
				//
				// assignParticleGroups(1);
				// assignParticleGroups(2);
				// assignParticleGroups(3);
				// assignParticleGroups(4);
				// moveParticles(1);
				// moveParticles(2);
				// moveParticles(3);
				// moveParticles(4);
				displayBallCount();

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	/**
	 * Displays the count of the number of particles in the boxes.
	 */
	public void displayBallCount() {

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

	// Worker thread for particle multi threading
	public class ParticleWorker implements Runnable {
		int listNum;

		public ParticleWorker(Object parameter) {
			listNum = (int) parameter;
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(16);
					assignParticleGroups(listNum);
					moveParticles(listNum);
				} catch (Exception e) {
					// TODO Auto-generated catch blocks
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @author Brian
	 *
	 */
	public class BallCounter implements Runnable {
		public BallCounter() {

		}

		public void run() {
			try {
				while (true) {
					_t1.setText(String.valueOf(_particles1.size()));
					_t2.setText(String.valueOf(_particles2.size()));
					_t3.setText(String.valueOf(_particles3.size()));
					_t4.setText(String.valueOf(_particles4.size()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
