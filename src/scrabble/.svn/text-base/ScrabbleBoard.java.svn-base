package scrabble;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * @author stephaap
 * 
 */
class ScrabbleBoard extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private static JLabel[][] boardLabels;
	private static Color[][] boardColor;
	private int boardSize;
	private static ScrabbleTile[][] tileList;
	/**
	 * ArrayList of tiles place on board for the current turn.
	 */
	public static ArrayList<ScrabbleTile> wordList;

	/**
	 * Creates a default Scrabble board
	 */
	public ScrabbleBoard() {
		this(Scrabble.DEFAULT_BOARD_LAYOUT, Scrabble.DEFAULT_BOARD_SIZE);
	}

	/**
	 * Sets the tile on the board to the given word.
	 * 
	 * @param word
	 */
	public void setScrabbleBoard(ScrabbleWord word) {
		for (ScrabbleTile tile : word.getWordTiles()) {
//			if (Character.isLetter(tile.getLetter())) {
				ScrabbleBoard.boardLabels[tile.getRow()][tile.getColumn()]
						.setForeground(Color.white);
				ScrabbleBoard.boardLabels[tile.getRow()][tile.getColumn()]
						.setText(tile.getLetter() + "");
				ScrabbleBoard.boardLabels[tile.getRow()][tile.getColumn()]
						.setBackground(new Color(160, 32, 240));
				ScrabbleBoard.boardColor[tile.getRow()][tile.getColumn()] = new Color(
						160, 32, 240);
				tileList[tile.getRow()][tile.getColumn()] = tile;
				ScrabbleFrame.sc.getBoardChars()[tile.getRow()][tile
						.getColumn()] = tile.getLetter();
//			}
		}
		ScrabbleFrame.mainWord.add(word.toString());
		ScrabbleFrame.subWords.add(word.getSubWords());
		ScrabbleFrame.points.add(word.getPoints());
		ScrabbleFrame.totalPoints.add(ScrabbleFrame.sc.getTotalScore());
	}

	/**
	 * Put here a description of what this method does.
	 * 
	 * @return board color
	 */
	public static Color[][] getBoardColor() {
		return boardColor;
	}

	private static char[][] boardLayout;
	private static int boardSiz;

	/**
	 * Creates a new ScrabbleBoard dependent on the size and layout passed in.
	 * 
	 * @param boardLayout
	 *            - the layout of the special spaces on the board (Tripe Word
	 *            Score, Double Letter Score, etc).
	 * @param boardSize
	 *            - size of the board (must be odd)
	 */
	public ScrabbleBoard(char[][] boardLayout, int boardSize) {
		super();
		this.boardSize = boardSize;
		boardSiz = boardSize;
		ScrabbleBoard.boardLayout = boardLayout;
		tileList = new ScrabbleTile[boardSize][boardSize];
		wordList = new ArrayList<ScrabbleTile>();
		this.setSize(300, 300);
		this.setLocation(30, 15);
		GridLayout layout = new GridLayout(boardSize, boardSize);
		this.setLayout(layout);
		LineBorder border = new LineBorder(Color.gray, 1);
		ScrabbleBoard.boardLabels = new JLabel[boardSize][boardSize];
		ScrabbleBoard.boardColor = new Color[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				boardLabels[i][j] = new JLabel();
				boardLabels[i][j].addMouseListener(this);
				boardLabels[i][j].setOpaque(true);
				boardLabels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				boardLabels[i][j].setBorder(border);
				if (boardLayout == null) {
					// do nothing
				} else if (boardLayout[i][j] == 'T') {
					boardLabels[i][j]
							.setBackground(ScrabbleFrame.tripleWordScoreColor);
					ScrabbleBoard.boardColor[i][j] = ScrabbleFrame.tripleWordScoreColor;
				} else if (boardLayout[i][j] == 't') {
					boardLabels[i][j]
							.setBackground(ScrabbleFrame.tripleLetterScoreColor);
					ScrabbleBoard.boardColor[i][j] = ScrabbleFrame.tripleLetterScoreColor;
				} else if (boardLayout[i][j] == 'D' || boardLayout[i][j] == '*') {
					boardLabels[i][j]
							.setBackground(ScrabbleFrame.doubleWordScoreColor);
					ScrabbleBoard.boardColor[i][j] = ScrabbleFrame.doubleWordScoreColor;
				} else if (boardLayout[i][j] == 'd') {
					boardLabels[i][j]
							.setBackground(ScrabbleFrame.doubleLetterScoreColor);
					ScrabbleBoard.boardColor[i][j] = ScrabbleFrame.doubleLetterScoreColor;
				} else {
					// changes the background color for labels where tiles were
					// already present.
					if (boardLabels[i][j].getBackground().equals(
							new Color(160, 32, 240))) {
						boardLabels[i][j]
								.setBackground(new Color(160, 32, 240));
						ScrabbleBoard.boardColor[i][j] = new Color(160, 32, 240);
					} else {
						// sets the empty labels on the board to black
						// background color.
						boardLabels[i][j].setBackground(Color.black);
						ScrabbleBoard.boardColor[i][j] = Color.black;
					}
				}
				this.add(boardLabels[i][j]);
			}
		}
	}

	/**
	 * Resets the board to a fresh board with no tiles on the board.
	 * 
	 */
	public static void reset() {
		for (int i = 0; i < boardSiz; i++) {
			for (int j = 0; j < boardSiz; j++) {
				if (boardLayout == null) {
					// do nothing
				} else if (boardLayout[i][j] == 'T') {
					boardLabels[i][j]
							.setBackground(ScrabbleFrame.tripleWordScoreColor);
					ScrabbleBoard.boardColor[i][j] = ScrabbleFrame.tripleWordScoreColor;
				} else if (boardLayout[i][j] == 't') {
					boardLabels[i][j]
							.setBackground(ScrabbleFrame.tripleLetterScoreColor);
					ScrabbleBoard.boardColor[i][j] = ScrabbleFrame.tripleLetterScoreColor;
				} else if (boardLayout[i][j] == 'D' || boardLayout[i][j] == '*') {
					boardLabels[i][j]
							.setBackground(ScrabbleFrame.doubleWordScoreColor);
					ScrabbleBoard.boardColor[i][j] = ScrabbleFrame.doubleWordScoreColor;
				} else if (boardLayout[i][j] == 'd') {
					boardLabels[i][j]
							.setBackground(ScrabbleFrame.doubleLetterScoreColor);
					ScrabbleBoard.boardColor[i][j] = ScrabbleFrame.doubleLetterScoreColor;
				} else {
					boardLabels[i][j].setBackground(Color.black);
					ScrabbleBoard.boardColor[i][j] = Color.black;
				}
			}
		}
	}

	/**
	 * Return the board labels.
	 * 
	 * @return the board labels
	 */
	public static JLabel[][] getBoardLabel() {
		return boardLabels;
	}

	/**
	 * Get the 2d array of tiles
	 * 
	 * @return tile list.
	 */
	public ScrabbleTile[][] getTileList() {
		return tileList;
	}

	/**
	 * Returns board size.
	 * 
	 * @return board size
	 */
	public int getBoardSize() {
		return this.boardSize;
	}

	/**
	 * Updates the JLabels that contain the characters to be displayed.
	 * 
	 * @param boardChars
	 *            - character arrangement on the board
	 */
	public void changeCharactersOnBoard(char[][] boardChars) {
		for (int i = 0; i < boardChars.length; i++) {
			if (boardChars.length != boardChars[i].length) {
				try {
					throw new Exception("Jagged Array");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				for (int j = 0; j < boardChars[i].length; j++) {
					ScrabbleBoard.boardLabels[i][j].setForeground(Color.white);
					// if the letter is not a special character, set the add the
					// characters on board to the board label, and tile list.
					if (boardChars[i][j] != 'b') {
						ScrabbleBoard.boardLabels[i][j]
								.setText(boardChars[i][j] + "");
						if (ScrabbleBoard.contains(boardChars[i][j] + "")) {
							ScrabbleBoard.boardLabels[i][j]
									.setBackground(new Color(160, 32, 240));
							ScrabbleBoard.boardColor[i][j] = new Color(160, 32,
									240);
							ScrabbleBoard.boardLabels[i][j]
									.setText(boardChars[i][j] + "");
							ScrabbleBoard.tileList[i][j] = new ScrabbleTile(
									boardChars[i][j], (byte) i, (byte) j, false);
						} else {
							ScrabbleBoard.tileList[i][j] = null;
						}

					} else {
						// set the board label differently if it is a blank
						// tile.
						ScrabbleBoard.boardLabels[i][j].setText("b");
						if (ScrabbleBoard.contains(boardChars[i][j] + "")) {
							ScrabbleBoard.boardLabels[i][j]
									.setBackground(new Color(160, 32, 240));
							ScrabbleBoard.boardColor[i][j] = new Color(160, 32,
									240);
							ScrabbleBoard.tileList[i][j] = new ScrabbleTile(
									boardChars[i][j], (byte) i, (byte) j, true);
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		for (int i = 0; i < this.boardSize; i++) {
			for (int j = 0; j < this.boardSize; j++) {
				// set the label backgrounds to how they were before the click.
				ScrabbleBoard.boardLabels[i][j]
						.setBackground(ScrabbleBoard.boardColor[i][j]);
			}
		}
		for (int i = 0; i < this.boardSize; i++) {
			for (int j = 0; j < this.boardSize; j++) {
				// if this is the label that the user clicked.
				if (arg0.getSource().equals(ScrabbleBoard.boardLabels[i][j])) {
					// if the user clicks on the tile that he had previously
					// selected, it unselects the tile.
					if (ScrabbleBoard.boardLabels[i][j].getBackground().equals(
							Color.GRAY)) {
						ScrabbleBoard.boardLabels[i][j]
								.setBackground(ScrabbleBoard.boardColor[i][j]);
					} else {
						// if the tile is clicked for the first time.
						if (ScrabbleFrame.clicked) {
							// set the font color to white.
							ScrabbleBoard.boardLabels[i][j]
									.setForeground(Color.white);
							// temp variable storing the test of the label
							String s = ScrabbleBoard.boardLabels[i][j]
									.getText();
							// does not allow to swap a non-empty hand tile with
							// a tile on the board.
							if (contains(s) && contains(ScrabbleFrame.text)) {
								ScrabbleFrame.clicked = false;
								return;
							}
							// does not allow to swap tiles that were already
							// placed on the board before the move.
							if (ScrabbleBoard.boardLabels[i][j].getBackground()
									.equals(new Color(160, 32, 240))) {
								ScrabbleFrame.clicked = false;
								return;
							}
							// changes the label to the letter of the hand tile
							// that was selected.
							ScrabbleBoard.boardLabels[i][j]
									.setText(ScrabbleFrame.text);
							// if the tile was a blank tile, it sets the
							// scrabble tile to true indicating it is the blank
							// tile.
							if (UserHandPanel.userHandLabels[UserHandPanel.pos]
									.getText().equals("b")) {
								tileList[i][j] = new ScrabbleTile(
										ScrabbleFrame.text.charAt(0), (byte) i,
										(byte) j, true);
								wordList.add(new ScrabbleTile(
										ScrabbleFrame.text.charAt(0), (byte) i,
										(byte) j, true));
							} else {
								tileList[i][j] = new ScrabbleTile(
										ScrabbleFrame.text.charAt(0), (byte) i,
										(byte) j, false);
								// removes the tile from the word list if the
								// user puts the tile from the scrabble board
								// back to his hand.
								for (int k = 0; k < wordList.size(); k++) {
									if (wordList.get(k).equals(tileList[i][j])) {
										wordList.remove(k);
									}
								}
								wordList.add(new ScrabbleTile(
										ScrabbleFrame.text.charAt(0), (byte) i,
										(byte) j, false));
							}
							// sets the click to be false.
							ScrabbleFrame.clicked = false;
							ScrabbleFrame.text = "";
							// sets the user hand tile that was selected to null
							// or to the letter that was selected.
							if (UserHandPanel.pos >= 0) {
								UserHandPanel.userHandLabels[UserHandPanel.pos]
										.setText(s);
							}
							// sets the blank tile back to b.
							if (Character
									.isLowerCase(UserHandPanel.userHandLabels[UserHandPanel.pos]
											.getText().charAt(0))) {
								UserHandPanel.userHandLabels[UserHandPanel.pos]
										.setText("b");
							}
							UserHandPanel.pos = -1;
						}
						// sets the tile list and the board label to the same
						// text if the user keeps clicking on the board without
						// placing or removing anyother tiles.
						String temp = ScrabbleBoard.boardLabels[i][j].getText();
						ScrabbleBoard.boardLabels[i][j]
								.setBackground(Color.GRAY);
						ScrabbleBoard.boardLabels[i][j].setText(temp);
						if (Character.isLetter(temp.charAt(0))) {
							tileList[i][j] = new ScrabbleTile(temp.charAt(0),
									(byte) i, (byte) j, false);
						} else {
							tileList[i][j] = null;
						}
					}
				}
			}
		}
	}

	/**
	 * See if letter is an alphabet.
	 * 
	 * @param s
	 * @return
	 */
	private static boolean contains(String s) {
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
	 * Refreshes the tiles in hand after computer player finishes its move.
	 * 
	 * @param compLetters
	 */
	public void setTilesInHand(ArrayList<Character> compLetters) {
		for (int i = 0; i < compLetters.size(); i++) {
			UserHandPanel.userHandLabels[i].setText(compLetters.get(i) + "");
			if (Character.isLowerCase(compLetters.get(i))) {
				UserHandPanel.userHandLabels[i].setText("b");
			}
		}
		for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
			if (ScrabbleFrame.getScrabble().getTileBag().size() > 0) {
				if (!Character.isLetter((UserHandPanel.userHandLabels[i]
						.getText().charAt(0)))) {
					Character c = ScrabbleFrame
							.getScrabble()
							.getTileBag()
							.remove(ScrabbleFrame.getScrabble().getTileBag()
									.size() - 1);
					UserHandPanel.userHandLabels[i].setText(c + "");
					if (ScrabbleFrame.getScrabble().getGameType() == "2")
						ScrabbleFrame.getScrabble().getTilesInHand(1).set(i, c);
					else
						ScrabbleFrame.getScrabble().getTilesInHand(0).set(i, c);
				}
			} else {
				if (ScrabbleFrame.getScrabble().getGameType() == "2")
					ScrabbleFrame
							.getScrabble()
							.getTilesInHand(1)
							.set(i,
									UserHandPanel.userHandLabels[i].getText()
											.charAt(0));
				else
					ScrabbleFrame
							.getScrabble()
							.getTilesInHand(0)
							.set(1,
									UserHandPanel.userHandLabels[i].getText()
											.charAt(0));
			}
		}

	}

}