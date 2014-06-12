package scrabble;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

/**
 * @author stephaap
 * 
 */
class UserHandPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	/**
	 * Holds the hand tiles.
	 */
	public static JLabel[] userHandLabels;

	public static JLabel[] toExchange;
	/**
	 * Holds the position of the hand tile.
	 */
	public static MouseListener mouse;
	public static int pos = -1;
	private Color[] userHandColor;
	public static boolean isExchange = false;

	/**
	 * This is the default constructor
	 */
	public UserHandPanel() {
		this(null, Scrabble.DEFAULT_MAX_HAND_SIZE);
	}

	/**
	 * Creates a new JPanel that contains the JLabels to be displayed to
	 * represent the user's hand.
	 * 
	 * @param tiles
	 *            - tiles held by user
	 * @param maxTilesInHand
	 *            - maximum number of tiles user can hold at a time
	 */
	public UserHandPanel(ArrayList<Character> tiles, int maxTilesInHand) {
		super();
		mouse = this;
		this.setSize(new Dimension(475, 60)); // CWA: If this is to be at a
												// fixed location,
		this.setLocation(new Point(30, 325)); // probably should be above the
												// board instead of
		GridLayout layout = new GridLayout(); // below it, to allow for
												// different board sizes.
		layout.setColumns(maxTilesInHand); // if below the board, it would be
											// better to calculate
		layout.setRows(1); // the location.
		layout.setHgap(20); // Most of the numbers here should be variables or
		layout.setVgap(15); // named constants, instead of magic numbers.
		this.setLayout(layout);

		UserHandPanel.userHandLabels = new JLabel[maxTilesInHand];
		this.userHandColor = new Color[maxTilesInHand];
		for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
			if (tiles != null && i < tiles.size()) {
				if (tiles.get(i) == 'b') {
					UserHandPanel.userHandLabels[i] = new JLabel(" ");
				} else {
					UserHandPanel.userHandLabels[i] = new JLabel(tiles.get(i)
							+ "");
				}
				UserHandPanel.userHandLabels[i].setVisible(true);
			} else {
				UserHandPanel.userHandLabels[i] = new JLabel();
				UserHandPanel.userHandLabels[i].setVisible(false);
			}
			UserHandPanel.userHandLabels[i]
					.setBackground(ScrabbleFrame.tileColor);
			this.userHandColor[i] = ScrabbleFrame.tileColor;
			UserHandPanel.userHandLabels[i].setOpaque(true);
			UserHandPanel.userHandLabels[i].addMouseListener(this);
			UserHandPanel.userHandLabels[i]
					.setHorizontalAlignment(SwingConstants.CENTER);
			UserHandPanel.userHandLabels[i].setFont(new Font("Dialog",
					Font.BOLD, 24));
			this.add(UserHandPanel.userHandLabels[i]);
		}
	}

	/**
	 * See if letter is an alphabet.
	 * 
	 * @param s
	 * @return
	 */
	static boolean contains(String s) {
		String[] letters = new String[26];
		int x = 'A';
		for (int i = 0; i < 26; i++) {
			char c = (char) x;
			letters[i] = (c + "");
			x++;
		}
		for (int i = 0; i < 26; i++) {
			if (letters[i].equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Updates the JLabels that display the tiles in the user's hand.
	 * 
	 * @param tiles
	 *            - tiles currently held by user
	 */
	public void changeTilesInHand(ArrayList<Character> tiles) {
		for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
			if (i < tiles.size()) {
				UserHandPanel.userHandLabels[i].setText(tiles.get(i) + "");
				UserHandPanel.userHandLabels[i].setVisible(true);
			} else {
				UserHandPanel.userHandLabels[i].setText("");
				UserHandPanel.userHandLabels[i].setVisible(false);
			}
		}
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (isExchange) {
			for (int i = 0; i < toExchange.length; i++) {
				if (arg0.getSource().equals(toExchange[i])) {
					if (!Character.isLetter(toExchange[i].getText().charAt(0))) {
						return;
					}
					if (toExchange[i].getBackground().equals(Color.yellow)) {
						toExchange[i].setBackground(Color.cyan);
					} else {
						toExchange[i].setBackground(Color.yellow);
					}
				}
			}
		}
		for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
			UserHandPanel.userHandLabels[i]
					.setBackground(this.userHandColor[i]);
		}
		for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
			final int x2 = i;
			if (arg0.getSource().equals(UserHandPanel.userHandLabels[i])) {
				// checks if the hand tile was already selected.
				if (UserHandPanel.userHandLabels[i].getBackground().equals(
						Color.CYAN)) {
					ScrabbleFrame.text = "";
					ScrabbleFrame.clicked = false;
					UserHandPanel.userHandLabels[i]
							.setBackground((this.userHandColor[i]));
				} else {
					// sets the click to true
					ScrabbleFrame.clicked = true;
					if (UserHandPanel.userHandLabels[i].getText().equals("b")) {
						// if it is a blank tile then it pops up a frame asking
						// the user to select the letter the user wants.
						final ArrayList<JButton> list = new ArrayList<JButton>();
						final JFrame letters = new JFrame();
						JPanel pane = new JPanel();
						letters.setTitle("Choose the Letter");
						letters.setSize(new Dimension(1550, 100));
						pane.setLayout(new GridLayout(1, 27));
						Color[] choices = { Color.green, Color.YELLOW };
						int pos = 0;
						int x = 'A';
						for (int i1 = 0; i1 < 26; i1++) {
							char c = (char) x;
							JButton temp = new JButton(c + "");
							if (pos == 0) {
								temp.setBackground(choices[pos]);
								temp.setForeground(Color.blue);
								pos = 1;
							} else {
								temp.setBackground(choices[1]);
								temp.setForeground(Color.black);
								pos = 0;
							}
							list.add(temp);
							x++;
						}
						for (int i2 = 0; i2 < list.size(); i2++) {
							final int x1 = i2;
							list.get(i2).addActionListener(
									new java.awt.event.ActionListener() {
										@Override
										public void actionPerformed(
												java.awt.event.ActionEvent e) {
											ScrabbleFrame.text = list.get(x1)
													.getText().toLowerCase();
											UserHandPanel.userHandLabels[x2]
													.setText(ScrabbleFrame.text);
											letters.dispose();
										}
									});
							Font f = new Font("Times New Roman", Font.PLAIN, 12);
							list.get(i2).setFont(f);
							pane.add(list.get(i2));
						}
						letters.add(pane);
						letters.setVisible(true);
					} else {
						// stores the text of the user hand tile to a static
						// variable so it can be used to place the tile on the
						// board.
						ScrabbleFrame.text = UserHandPanel.userHandLabels[i]
								.getText();
					}
					pos = i;
					UserHandPanel.userHandLabels[i].setBackground(Color.CYAN);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// do nothing.
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// do nothing.
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// do nothing.
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// do nothing.
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 */
	public static void exchangeTiles() {
		isExchange = true;
		final JFrame frame = new JFrame("Exchange Tiles");
		frame.setLayout(new GridLayout(2, 1));
		frame.setSize(new Dimension(300, 100));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, UserHandPanel.userHandLabels.length));
		LineBorder border = new LineBorder(Color.black, 3);
		JButton ok = new JButton("OK");
		UserHandPanel.toExchange = new JLabel[UserHandPanel.userHandLabels.length];
		for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
			JLabel temp = new JLabel(UserHandPanel.userHandLabels[i].getText());
			temp.setFont(new Font("Dialog", Font.BOLD, 24));
			temp.setOpaque(true);
			temp.setBackground(Color.yellow);
			temp.setHorizontalAlignment(SwingConstants.CENTER);
			temp.setBorder(border);
			temp.addMouseListener(mouse);
			panel.add(temp);
			toExchange[i] = temp;
		}
		ok.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Integer> exchange = new ArrayList<Integer>();
				for (int i = 0; i < toExchange.length; i++) {
					if (toExchange[i].getBackground().equals(Color.cyan)) {
						exchange.add(i);
					}
				}
				System.out.println(exchange);
				ScrabbleFrame.exchange(exchange);
				isExchange = false;
				frame.dispose();
			}

		});
		frame.add(panel);
		frame.add(ok);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
