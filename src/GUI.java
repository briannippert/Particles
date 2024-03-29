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
public class GUI implements KeyListener {
	Frame _f1;
	public List<RoundParticle> _particles1, _particles2, _particles3, _particles4;
	public List<RigidBody> bodies;
	double _gravity = 2;
	double _windResistance = 3;
	Random _rand;
	int _groupCounter = 1;

	Rectangle _r1, _r2, _r3, _r4;
	TextBox _t1, _t2, _t3, _t4;
	Cube c1;

	static int _MAXPARTICLES = 1000;
	static int _EMITERRATE = 5;

	/**
	 * Constructor for GUI Class
	 */
	@SuppressWarnings("unchecked")
	public GUI() {
		_f1 = new Frame(800, 800);
		_rand = new Random();
		_particles1 = Collections.synchronizedList(new ArrayList());
		_particles2 = Collections.synchronizedList(new ArrayList());
		_particles3 = Collections.synchronizedList(new ArrayList());
		_particles4 = Collections.synchronizedList(new ArrayList());
		_f1.addKeyListener(this);
		// this.run();
		c1 = new Cube(300, 300, 45);
		c1.setSize(20, 200);
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
			if (_particles1.size() + _particles2.size() + _particles3.size() + _particles4.size()>= _MAXPARTICLES) {
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
					double velx = _rand.nextInt(36) - 18;
					double vely = _rand.nextInt(30) - 30;
					switch (_groupCounter) {
					case 1:
						synchronized (_particles1) {
							_particles1.add(new RoundParticle((int) velx, (int) vely, new Point(mouseX, mouseY)));
							_groupCounter = 2;
							continue;
						}

					case 2:
						synchronized (_particles2) {
							_particles2.add(new RoundParticle((int) velx, (int) vely, new Point(mouseX, mouseY)));
							_groupCounter = 3;
							continue;
						}

					case 3:
						synchronized (_particles3) {
							_particles3.add(new RoundParticle((int) velx, (int) vely, new Point(mouseX, mouseY)));
							_groupCounter = 4;
							continue;
						}

					case 4:
						synchronized (_particles4) {
							_particles4.add(new RoundParticle((int) velx, (int) vely, new Point(mouseX, mouseY)));
							_groupCounter = 1;
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
					RoundParticle p = (RoundParticle) i.next();
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

	/**
	 * Moves all particles in list num.
	 * 
	 * @param num
	 */
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
				RoundParticle p = (RoundParticle) i.next();
				if (c1.contains(p.getLocation()) == true) {
					p.setXVelocity((int)(-p.getVelocity().x*.9));
					p.setYVelocity((int)(-p.getVelocity().y*.9));
					p.move();
					continue;
				}
				if (p.getXLocation() <= 5 || p.getXLocation() >= Frame.getWidth() - 5) {
					p.setXVelocity(-p.getVelocity().x);
				}
				if (p.getYLocation() <= 0) {
					p.setYVelocity(-p.getVelocity().y);
				}
				p.changeYVelocity((int) (_gravity));
				p.move();
				if (p.getYLocation() >= Frame.getHeight() + 20) {
					p = null;
					i.remove();
				}
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

	// Worker thread for particle multi threading
	public class ParticleWorker implements Runnable {
		int listNum;
		long prevTime =System.currentTimeMillis() ;
		public ParticleWorker(Object parameter) {
			listNum = (int) parameter;
		}

		public void run() {
			while (true) {
				try {
					if(System.currentTimeMillis() - prevTime >= 16){
						moveParticles(listNum);
						prevTime = System.currentTimeMillis();
					}
				} catch (Exception e) {
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
//					Thread.sleep(30);
//					_t1.setText(String.valueOf(_particles1.size()));
//					_t2.setText(String.valueOf(_particles2.size()));
//					_t3.setText(String.valueOf(_particles3.size()));
//					_t4.setText(String.valueOf(_particles4.size()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
