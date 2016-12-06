/**
 * Object representing the information of each player.
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

public class UserPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8005126132273764537L;
	
	// Preferred width of panel
	public static final int WIDTH = 400;
	// Preferred height of panel
	public static final int HEIGHT = 400;
	
	// ID of a "null" ball
	public static final int NONE_ID = 0;
	// ID of a striped ball
	public static final int STRIPED_ID = 1;
	// ID of a solid ball
	public static final int SOLID_ID = 2;
	// ID of the eight ball
	public static final int EIGHT_ID = 3;

	// Name of the player
	private String name;
	// Ball object associated with the player.
	private Ball ballType;
	// Type of the ball associated with the player.
	private int type;
	// Whether or not this Player is the next to play.
	private boolean isPlaying;
	// A list of all the balls sunk of this Player.
	private ArrayList<Ball> sunkBalls;
	
	UserPanel (String name, boolean isPlaying) {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.name = name;
		this.isPlaying = isPlaying;
		this.sunkBalls = new ArrayList<Ball>();
		this.ballType = new Ball(-1, 0, 0, 0, GamePanel.BLACK, GamePanel.BLACK);
	}

	@Override
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		
	    Graphics2D g2 = (Graphics2D)g;
	    RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
		
		// Painting the name of this Player
		Font f = new Font("Arial", Font.BOLD, 48);
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setRenderingHints(rh);
		g2d.setFont(f);
		FontMetrics fm = g2d.getFontMetrics();
		
		int x = (getWidth() - fm.stringWidth(this.name)) / 2;
		int y = (fm.getHeight()) / 2 + fm.getAscent();
		
		g2d.setColor(GamePanel.BLACK);
		g2d.drawString(this.name, x, y);
		
		// Painting the ball associated with this Player
		ballType.radius = fm.getHeight() / 2;
		ballType.pos.x = getWidth() - ballType.radius;
		ballType.pos.y = y - fm.getAscent() + ballType.radius;
		
		if (type == STRIPED_ID) {
			ballType.primary = GamePanel.RED;
			ballType.secondary = GamePanel.WHITE;
			ballType.draw(g);
		} else if (type == SOLID_ID) {
			ballType.primary = GamePanel.RED;
			ballType.secondary = GamePanel.RED;
			ballType.draw(g);
		} else if (type == EIGHT_ID) {
			ballType.primary = GamePanel.BLACK;
			ballType.secondary = GamePanel.BLACK;
			ballType.draw(g);
		}
		
		// Painting the balls sunk by this Player
		for (int i = 0; i < sunkBalls.size(); i++) {
			sunkBalls.get(i).radius = 15;
			sunkBalls.get(i).pos = new Vector(120 + (i % 6) * 35, 115 + (i / 6) * 35);
			sunkBalls.get(i).draw(g);
		}
		
		// If this Player is the next to play, paint a green triangle beside their name.
		if (isPlaying) {
			g2.setColor(GamePanel.GREEN);
			g2.fillPolygon(new Polygon(new int[]{25, 25, 60}, new int[]{30, 80, 55}, 3));
		}
		
		g2d.dispose();
	}
	
	/**
	 * Resets this Player.
	 */
	public void reset () {
		type = 0;
		sunkBalls.clear();
	}
	
	/**
	 * 
	 * @param type type to set
	 */
	public void setType (int type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @return true if this Player is playing
	 */
	public boolean isPlaying () {
		return isPlaying;
	}
	
	/**
	 * Toggles isPlaying.
	 */
	public void toggleIsPlaying () {
		this.isPlaying = !this.isPlaying;
	}
	
	/**
	 * 
	 * @param isPlaying isPlaying to set
	 */
	public void setIsPlaying (boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	
	/**
	 * 
	 * @param b ball to add to sunkBalls
	 */
	public void addBall (Ball b) {
		this.sunkBalls.add(b);
	}
	
	/**
	 * 
	 * @return type associated with this Player
	 */
	public int getType () {
		return this.type;
	}
}
