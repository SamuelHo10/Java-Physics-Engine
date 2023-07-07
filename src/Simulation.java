import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.*;

@SuppressWarnings("serial")
public class Simulation extends JPanel implements Runnable, ActionListener {

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenWidth = 0;
	public static int screenHeight = 0;

	public static final int DELAY = 2;
	//public static final double TIME_FACTOR = 1;
	public static double prevTime,curTime,dt;
	public static final double GRAVITY = 9.8;
	private static String[] imageNames = {"flag.png"};
	public static Image[] images = new Image[imageNames.length];
	// private Image star;

	public static ArrayList<Body> objects = new ArrayList<Body>();
	private Thread simulator;
	// private int x, y;

	public Simulation() {

		initSimulation();
	}

	 private void loadImage() {
		 for(int i=0;i<imageNames.length;i++) {
			 ImageIcon ii = new ImageIcon("src/images/"+imageNames[i]);
			 images[i] = ii.getImage();
		 }
	 }

	private void initSimulation() {
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(screenWidth, screenHeight));

		SceneManager.initSimulation();
		UI.initUI(this);
		prevTime = System.currentTimeMillis();
		this.setLayout(null);
		loadImage();
		// x = INITIAL_X;
		// y = INITIAL_Y;
	}

	@Override
	public void addNotify() {
		super.addNotify();

		simulator = new Thread(this);
		simulator.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		UI.drawFlag(g);
		for(int i=0;i<objects.size();i++) {
			objects.get(i).draw(g);
		}
		//UI.showStats(g);
		UI.drawText(g);
		UI.drawTimer(g);
		// drawStar(g);
	}

	/*
	 * private void drawStar(Graphics g) {
	 * 
	 * g.drawImage(star, x, y, this); Toolkit.getDefaultToolkit().sync(); }
	 */

	private void loop() {
		 //Point point = MouseInfo.getPointerInfo().getLocation();
		 //Vec vec = new Vec(point.getX(),point.getY());
		 //objects.get(0).pos = vec;
		double curTime = System.currentTimeMillis();
		dt = (curTime-Simulation.prevTime)/10.0;
		Simulation.prevTime = curTime;
		if (Body.isRunningSimulation()) {
			Body.updateAll(objects);
			SceneManager.update();
		}
	}

	@Override
	public void run() {

		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (true) {

			loop();
			repaint();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0) {
				sleep = 2;
			}

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {

				String msg = String.format("Thread interrupted: %s", e.getMessage());

				JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
			}

			beforeTime = System.currentTimeMillis();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == null)
			action = "";
		switch (action) {
		case "next":
			SceneManager.sceneNumber++;
			SceneManager.switchScene();
			break;
		case "back":
			SceneManager.sceneNumber--;
			SceneManager.switchScene();
			break;
		case "start":
			SceneManager.start();
		}

	}
}
