package rpg;

import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CombatObject extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private GameEngine GE;
	protected Entity entity;

	// gui parts
	protected JProgressBar healthBar;
	protected JProgressBar manaBar;
	private JPanel barPanel;
	private JLabel name;
	private JPanel imagePanel;
	protected ImageIcon backgroundImage; // grass, dirt, floor, etc...
	protected JLabel background; // container to hold terrainID image
	protected JLabel foreground; // container to hold entityID image
	protected boolean currentTurn;
	protected boolean currentTarget;

	/**
	 * Constructor for CombatObject
	 */
	public CombatObject(GameEngine tempEngine, Entity entity, ImageIcon bg) {
		GE = tempEngine;
		this.entity = entity;

		// build the GUI
		this.setLayout(new BorderLayout());
		this.setOpaque(false); // transparent background

		// name tag
		name = new JLabel(entity.getName(), JLabel.CENTER);

		// health bar for the player
		healthBar = new JProgressBar(0);
		healthBar.setForeground(Color.RED);
		healthBar.setBackground(Color.WHITE);
		healthBar.setBorderPainted(true);
		//healthBar.setMaximumSize(new Dimension(GE.C_WIDTH, 15));
		healthBar.setStringPainted(true);
		setHealthBar();

		// mana bar for the player
		manaBar = new JProgressBar(0);
		manaBar.setForeground(Color.BLUE);
		manaBar.setBackground(Color.WHITE);
		manaBar.setBorderPainted(true);
		//manaBar.setMaximumSize(new Dimension(GE.C_WIDTH, 15));
		manaBar.setStringPainted(true);
		setManaBar();
		
		//panel to hold bars
		barPanel = new JPanel();
		//barPanel.setPreferredSize(new Dimension(GE.C_WIDTH, 30));
		barPanel.setLayout(new GridLayout(2, 1));
		barPanel.add(healthBar);
		barPanel.add(manaBar);

		// Lay a background, foreground, and highground JLabel
		background = new JLabel();
		background.setSize(GE.C_WIDTH, GE.C_HEIGHT);
		background.setLocation(0, 0);
		foreground = new JLabel();
		foreground.setSize(GE.C_WIDTH, GE.C_HEIGHT);
		foreground.setLocation(0, 0);

		// image with layers
		JLayeredPane layer = new JLayeredPane();
		layer.add(background, JLayeredPane.DEFAULT_LAYER); // 0
		layer.add(foreground, JLayeredPane.PALETTE_LAYER); // 100 (above 0)
		layer.setLayout(new BorderLayout());
		layer.setOpaque(false); // non-transparent
		layer.setSize(GE.C_WIDTH, GE.C_HEIGHT);

		imagePanel = new JPanel();
		imagePanel.setLayout(new GridLayout(0,4));
		imagePanel.setOpaque(false); // transparent background
		imagePanel.setPreferredSize(new Dimension(GE.C_WIDTH, GE.C_HEIGHT + 15));
		imagePanel.add(new JLabel(""));
		imagePanel.add(layer);
		imagePanel.add(barPanel);
		imagePanel.add(new JLabel(""));

		setForeground();
		setBackground(bg);

		// pack to main panel
		this.add(name, BorderLayout.NORTH);
		this.add(imagePanel, BorderLayout.CENTER);
		this.add(Box.createGlue(), BorderLayout.EAST);
		setCurrentTurn(false);
	}

	/**
	 * Highlights the Object with a GREEN box when flag is true.
	 * 
	 * @param flag
	 */
	public void setCurrentTurn(boolean flag) {
		if (flag) {
			currentTurn = true;
			this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
		} else {
			this.setBorder(null);
			currentTurn = false;
		}
	}

	/**
	 * Highlights the Object with a Red box or Cyan box, or nothing
	 * 
	 * @param flag
	 */
	public void setCurrentTarget(boolean flag) {
		if (flag) {
			currentTarget = true;
			this.setBorder(BorderFactory.createLineBorder(Color.CYAN, 4));
		} else {
			currentTarget = false;
			this.setBorder(null);
		}
	}

	public void setBackground(ImageIcon bg) {
		backgroundImage = bg;
		background.setIcon(backgroundImage);
	}

	public void setForeground() {
		foreground.setIcon(entity.getImage());
	}

	/**
	 * Updates the CombatObject's health bar to the current % health of the
	 * entity it holds.
	 */
	public void setHealthBar() {
		int healthPercent = (int) (((double) entity.getCurrentHealth() / (double) entity
				.getMaxHealth()) * 100);
		healthBar.setValue(healthPercent);
		if(healthBar.getValue() == 0){
			healthBar.setString("DEAD");
		}else{
		healthBar.setString("" + entity.getCurrentHealth() + "/"
				+ entity.getMaxHealth());
		}
	}

	public void setManaBar() {
		int manaPercent = (int) (((double) entity.getCurrentMana() / (double) entity
				.getMaxMana()) * 100);
		manaBar.setValue(manaPercent);
		manaBar.setString("" + entity.getCurrentMana() + "/"
				+ entity.getMaxMana());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Auto-generated method stub
	}

} // end of CombatObject

