import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class UI {
//	private static final Font LARGE_FONT = new Font("serif", Font.BOLD, 50);
//	private static final Font BUTTON_FONT = new Font("arial", Font.BOLD, 30);
//	private static final Font TITLE_FONT = new Font("arial", Font.BOLD, 60);
//	private static Map<String, JButton> buttons = new HashMap<String, JButton>();
//	public static String text = "";
//	public static Vec flagPos = new Vec(-1000, -1000);
//
//	public static void initUI(Simulation s) {
//		Color textColor = new Color(0, 67, 87);
//		Color borderColor = new Color(0, 196, 255);
//		Color darkBorderColor = new Color(0, 149, 255);
//
//		Border border1 = new LineBorder(darkBorderColor, 6, true);
//		Border border2 = new LineBorder(borderColor, 6, true);
//
//		buttons.put("next", new JButton("Next"));
//		buttons.get("next").setBounds(Simulation.screenWidth - 300, 50, 200, 75);
//		buttons.get("next").setFont(BUTTON_FONT);
//
//		buttons.put("back", new JButton("Back"));
//		buttons.get("back").setBounds(100, 50, 200, 75);
//		buttons.get("back").setFont(BUTTON_FONT);
//
//		buttons.put("start", new JButton("Start"));
//		buttons.get("start").setBounds(Simulation.screenWidth / 2 - 200, 50, 200, 75);
//		buttons.get("start").setFont(BUTTON_FONT);
//
//		for (Entry<String, JButton> button : buttons.entrySet()) {
//			String name = button.getKey();
//			JButton b = button.getValue();
//			b.setActionCommand(name);
//			b.setForeground(textColor);
//			b.setBackground(Color.WHITE);
//			b.setFocusable(false);
//			b.setBorder(border2);
//			b.addMouseListener(new java.awt.event.MouseAdapter() {
//				public void mouseEntered(java.awt.event.MouseEvent evt) {
//					b.setBorder(border1);
//				}
//
//				public void mouseExited(java.awt.event.MouseEvent evt) {
//					b.setBorder(border2);
//				}
//			});
//
//			s.add(b);
//			b.setVisible(true);
//			b.addActionListener(s);
//		}
//	}
//
//	/*public static void showStats(Graphics g) {
//		int len = Simulation.objects.size();
//		for (int i = 0; i < len; i++) {
//			Body obj = Simulation.objects.get(i);
//			if (!obj.isStatic) {
//				g.setColor(Color.BLACK);
//				g.setFont(LARGE_FONT);
//				String str = obj.getMass() + "kg";
//				if (obj.name != null) {
//					str = obj.name;
//				}
//				//g.drawString(str, (int) obj.pos.x - g.getFontMetrics().stringWidth(str) / 2,
//				//		(int) (obj.pos.y + g.getFontMetrics().getHeight() / 4));
//
//				// int scale = 100;
//				Vec vel = new Vec(obj.vel);
//				vel.normalize();
//				g.setColor(Color.BLUE);
//
//				// g.drawLine((int) obj.pos.x, (int)obj.pos.y, (int) (obj.pos.x + vel.x*scale),
//				// (int) (obj.pos.y + vel.y*scale));
//
//			}
//		}
//	}*/
//
//	public static void drawText(Graphics g) {
//		g.setColor(Color.BLACK);
//		g.setFont(LARGE_FONT);
//		if (text.equals("Rigidbody Physics Engine")) {
//			g.setFont(TITLE_FONT);
//			g.setColor(Color.PINK);
//		}
//		int increment = g.getFontMetrics().getHeight();
//		String[] arr = text.split("\n");
//		for (int i = 0; i < arr.length; i++) {
//			g.drawString(arr[i], (int) Simulation.screenWidth / 2 - g.getFontMetrics().stringWidth(arr[i]) / 2,
//					(int) (200 + g.getFontMetrics().getHeight() / 4 + increment * i));
//		}
//	}
//
//	public static void drawFlag(Graphics g) {
//		g.drawImage(Simulation.images[0], (int) flagPos.x - 50, (int) flagPos.y - 50, 100, 100, Color.WHITE, null);
//	}
//
//	public static void drawTimer(Graphics g) {
//		g.setColor(Color.BLACK);
//		g.setFont(LARGE_FONT);
//		g.drawString("Time: " + ((int) ((SceneManager.endTime - SceneManager.startTime) * 1000) / 1000.0) + "s",
//				Simulation.screenWidth / 2 + 100, 100);
//	}
//
//	public static void changeButtonText(String txt) {
//		if (buttons.get("start") != null) {
//			buttons.get("start").setText(txt);
//		}
//	}
}
