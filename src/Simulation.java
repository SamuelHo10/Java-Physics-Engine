import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.*;
import java.util.Random;
import java.awt.Color;

@SuppressWarnings("serial")
public class Simulation extends JPanel implements Runnable, ActionListener {

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenWidth = 0;
	public static int screenHeight = 0;

	public static final int DELAY = 2;
	public static double prevTime,curTime,dt;
	public static final double GRAVITY = 9.8;
	
	public static ArrayList<Body> objects = new ArrayList<Body>();
	private Thread simulator;
	
	private static int timer = 0;
	private static Random rand;
	
	private static final Color LIGHT_PURPLE = new Color(229, 135, 255);
	private static final Color LIGHT_BLUE = new Color(138, 163, 255);
	private static final Color LIGHT_YELLOW = new Color(247, 250, 175);
	private static final Color LIGHT_ORANGE = new Color(247, 218, 151);
	private static final Color LIGHT_VIOLET = new Color(147, 134, 247);

	public Simulation() {

		initSimulation();
	}


	private void initSimulation() {
		
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(screenWidth, screenHeight));

//		SceneManager.initSimulation();
//		UI.initUI(this);
		prevTime = System.currentTimeMillis();
		this.setLayout(null);
		
		rand = new Random();
		
		Simulation.objects.add(new ConvexPolygon(new Vec(Simulation.screenWidth / 2, Simulation.screenHeight - 100),
				ConvexPolygon.rectangle(Simulation.screenWidth, 50), LIGHT_ORANGE, 30, true, ""));
		
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
		
		for(int i=0;i<objects.size();i++) {
			objects.get(i).draw(g);
		}
	}

	private void update() {
		int w = Simulation.screenWidth;
		int h = Simulation.screenHeight;

		timer++;
		if (timer % 500 == 0 && timer < 50000) {
			Body b = (new ConvexPolygon(new Vec(rand.nextInt(0,w), -100), ConvexPolygon.regularPolygon(100, rand.nextInt(5) + 3),
					LIGHT_VIOLET, 1, false, ""));
			Simulation.objects.add(b);
			b.setAngle(Math.PI/4);
			Simulation.objects.add(new Circle(new Vec(rand.nextInt(0,w), 0), rand.nextInt(50,150), LIGHT_PURPLE, 1, false, ""));

			

		}
	}

	private void loop() {
		 //Point point = MouseInfo.getPointerInfo().getLocation();
		 //Vec vec = new Vec(point.getX(),point.getY());
		 //objects.get(0).pos = vec;
		double curTime = System.currentTimeMillis();
		dt = (curTime-Simulation.prevTime)/10.0;
		Simulation.prevTime = curTime;
		if (Body.isRunningSimulation()) {
			Body.updateAll(objects);
			update();
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
//		String action = e.getActionCommand();
//		if (action == null)
//			action = "";
//		switch (action) {
//		case "next":
//			SceneManager.sceneNumber++;
//			SceneManager.switchScene();
//			break;
//		case "back":
//			SceneManager.sceneNumber--;
//			SceneManager.switchScene();
//			break;
//		case "start":
//			SceneManager.start();
//		}
//
	}
}
