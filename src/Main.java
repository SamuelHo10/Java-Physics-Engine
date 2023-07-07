import javax.swing.*;

@SuppressWarnings("serial")
public class Main extends JFrame{

	public static Simulation simulation;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		simulation = new Simulation();
		this.add(simulation);
		this.setTitle("Physics");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
