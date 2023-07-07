import java.awt.Color;
import java.util.Random;

public class SceneManager {
	public static double startTime;
	public static double endTime;
	public static int sceneNumber = 6;
	public static double timer;
	private static boolean done = false;
	private static Random rand;

	private static final Color LIGHT_PURPLE = new Color(229, 135, 255);
	private static final Color LIGHT_BLUE = new Color(138, 163, 255);
	private static final Color LIGHT_YELLOW = new Color(247, 250, 175);
	private static final Color LIGHT_ORANGE = new Color(247, 218, 151);
	private static final Color LIGHT_VIOLET = new Color(147, 134, 247);

	private static Body obj;

	public static void initSimulation() {

		timer = 0;
		switchScene();

		// Simulation.objects.add(new Obj(new Vec(w / 2, h - 100), Obj.rectangle(w, 50),
		// LIGHT_ORANGE, 30, true));
		// Simulation.objects.add(new Obj(new Vec(50, 0), Obj.rectangle(100, 100),
		// LIGHT_PURPLE, 60, false));
		// Simulation.objects.add(new Obj(new Vec(Simulation.SCREEN_WIDTH / 2-100,
		// 0),Obj.rectangle(100, 100), LIGHT_PURPLE, 60, false));
		// Simulation.objects.add(new Obj(new Vec(Simulation.SCREEN_WIDTH / 2-100,
		// 0),Obj.rectangle(100, 100), LIGHT_PURPLE, 60, false));
		// Simulation.objects.get(0).rotate(2);
		// Simulation.objects.get(1).vel = new Vec(1, 0);
		// Simulation.objects.get(2).vel = new Vec(-3, 0);
		// startTime = System.currentTimeMillis() / 1000;
		// System.out.println(startTime);
		rand = new Random();
	}

	// private static boolean done = false;
	//

	public static void update() {
		int w = Simulation.screenWidth;
		int h = Simulation.screenHeight;

		timer++;
		if (timer % 500 == 0 && timer < 50000) {
			Body b = (new ConvexPolygon(new Vec(rand.nextInt(0,w), -100), ConvexPolygon.regularPolygon(100, 4),//rand.nextInt(5) + 3),
					LIGHT_VIOLET, 1, false, ""));
			Simulation.objects.add(b);
			b.setAngle(Math.PI/4);
			Simulation.objects.add(new Circle(new Vec(rand.nextInt(0,w), 0), rand.nextInt(50,150), LIGHT_ORANGE, 1, false, ""));

			// Simulation.objects
			// .add(new Obj(new Vec(w/2, h / 2), Obj.rectangle(100, 100), LIGHT_PURPLE, 30,
			// false, "m1"));

		}

	}

	public static void switchScene() {
		int w = Simulation.screenWidth;
		int h = Simulation.screenHeight;
		reset();
		Simulation.objects.add(new ConvexPolygon(new Vec(500, 200), ConvexPolygon.rectangle(100, 100),//rand.nextInt(5) + 3),
				LIGHT_VIOLET, 1, false, ""));
		//Simulation.objects.add(new ConvexPolygon(new Vec(150, 0), ConvexPolygon.regularPolygon(100, 4),//rand.nextInt(5) + 3),
				//LIGHT_VIOLET, 1, false, ""));
		Simulation.objects.add(new Circle(new Vec(1000, 500), 100, LIGHT_ORANGE, 1, false, ""));
		switch (sceneNumber) {
			
		}
	}

	private static void reset() {
		startTime = 0;
		endTime = 0;
		done = false;
		UI.flagPos = new Vec(-1000, -1000);
		UI.text = "";
		Body.setRunningSimulation(true);
		UI.changeButtonText("Start");
		Simulation.objects.clear();
		Simulation.objects.add(new ConvexPolygon(new Vec(Simulation.screenWidth / 2, Simulation.screenHeight - 100),
				ConvexPolygon.rectangle(Simulation.screenWidth, 50), LIGHT_ORANGE, 30, true, ""));
	}

	public static void start() {
		if (Body.isRunningSimulation()) {
			Body.setRunningSimulation(false);
			UI.changeButtonText("Reset");
			startTime = System.currentTimeMillis() / 1000.0;

		} else {
			switchScene();
		}
	}
}
