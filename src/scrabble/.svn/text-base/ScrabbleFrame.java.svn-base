package scrabble;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Provides a simple GUI for interacting with a Scrabble object
 * 
 * @author Amanda Stephan, modified by Claude Anderson. Major refactoring by
 *         Matt Boutell and Claude Anderson, April 2011.
 */

public class ScrabbleFrame extends JFrame {
	/**
	 * Field that tells whether the hand tile was clicked or not.
	 */
	public static boolean clicked = false;
	/**
	 * Text of the tile.
	 */
	public static String text = "";
	/**
	 * Stores the word played by the player.
	 */
	public static ArrayList<Character> word = new ArrayList<Character>();
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_DICTIONARY = "scrabbleDictionaries/dictionary02.sd";
	private static Scrabble currentScrabble;
	private static ScrabbleDictionary dictionary;
	@SuppressWarnings("unused")
	private static ScrabbleDictionary backwardsDictionary;
	private JPanel jContentPane = null;
	/**
	 * Stores the array list of moves.
	 */
	public static ArrayList<ArrayList<String>> subWords = new ArrayList<ArrayList<String>>();
	/** Color for a Triple Word Score space */
	public static final Color tripleWordScoreColor = new Color(255, 0, 0);
	/** Color for a Triple Letter Score space */
	public static final Color tripleLetterScoreColor = new Color(35, 35, 255); // @jve:decl-index=0:
	/** Color for a Double Word Score space */
	public static final Color doubleWordScoreColor = new Color(160, 82, 45); // @jve:decl-index=0:
	/** Color for a Double Letter Score space */
	public static final Color doubleLetterScoreColor = new Color(199, 21, 133);
	/** Color for a tile space in user's hand. */
	public static final Color tileColor = new Color(255, 204, 102);

	private UserHandPanel userHand = null;
	private static ScrabbleBoard scrabbleBoard = null;
	/**
	 * Stores the current game state.
	 */
	static GameState sc;
	/**
	 * Stores array list of points.
	 */
	protected static ArrayList<Integer> points = new ArrayList<Integer>();
	/**
	 * stores array list of total scores.
	 */
	public static ArrayList<Integer> totalPoints = new ArrayList<Integer>();

	private JLabel tripleWordScoreLabel = null;
	private JLabel doubleWordScoreLabel = null;
	private JLabel tripleLetterScoreLabel = null;
	private JLabel doubleLetterScoreLabel = null;
	private static JButton stepOnceButton = null;
	private static JButton playGameButton = null;
	private JButton quitButton = null;
	private JLabel scoreLabel = null;
	private JLabel playerScoreLabel = null;
	private JLabel computerScoreLabel = null;
	private JLabel curScoreLabel = null;
	private JLabel scoreTurnLabel = null;
	private JLabel scoreThisTurnLabel = null;
	private JLabel computerThisTurnLabel = null;
	private JMenuBar modeBar = null;
	private static JButton playWordButton = null;

	/**
	 * Create the scrabble GUI, loading a new standard game.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			new ScrabbleFrame().setVisible(true);
		} catch (Exception e) {
			System.out.println("Cannot create initial game");
			e.printStackTrace();
		}
	}

	/**
	 * A default constructor that will create an empty default board with a
	 * randomly-ordered tilebag (once you have implemented shuffling)
	 * 
	 * @throws Exception
	 *             If any method it calls threw an exception. Could make this
	 *             more robust.
	 */
	public ScrabbleFrame() throws Exception {
		super();
		Date start = new Date();
		ScrabbleFrame.dictionary = new ScrabbleDictionary(
				ScrabbleFrame.DEFAULT_DICTIONARY);
		ScrabbleFrame.backwardsDictionary = new ScrabbleDictionary(
				"scrabbleDictionaries/dictionary03.sd");
		Date end = new Date();
		long elapsedTime = end.getTime() - start.getTime();
		System.out.println(elapsedTime + " ms");
		ScrabbleFrame.currentScrabble = new Scrabble(ScrabbleFrame.dictionary);
		// InputStream in = new FileInputStream("music.wav");
		// Clip clip = AudioSystem.getClip();
		// AudioInputStream ais = AudioSystem
		// .getAudioInputStream(new BufferedInputStream(in));
		// clip.open(ais);
		// clip.loop(Clip.LOOP_CONTINUOUSLY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeGuiComponents();
		connectGUItoGsme(ScrabbleFrame.currentScrabble);
	}

	/**
	 * Returns dictionary.
	 * 
	 * @return dictionary.
	 */
	public static ScrabbleDictionary getDictionary() {
		return dictionary;
	}

	/**
	 * Returns backwards dictionary.
	 * 
	 * @return backwards dictionary.
	 */
	public static ScrabbleDictionary getBackwardsDictionary() {
		return dictionary;
	}

	/**
	 * Creates the GUI components, but does not give them any info about a
	 * particular Scrabble game.
	 */
	private void initializeGuiComponents() {
		this.setSize(529, 515);
		this.setTitle("Scrabble Game");
		this.setContentPane(getJContentPane());
		this.setJMenuBar(getModeMenu());
	}

	/**
	 * Adds information from the state of the Scrabble game to this GUI.
	 * 
	 * @param sc
	 *            the Scrabble game to connect.
	 */
	private void connectGUItoGsme(Scrabble sc) {
		this.jContentPane.remove(ScrabbleFrame.scrabbleBoard);
		ScrabbleFrame.scrabbleBoard = new ScrabbleBoard(
				sc.getBoardConfiguration(), sc.getBoardSize());
		this.jContentPane.add(ScrabbleFrame.scrabbleBoard, null);
		this.jContentPane.remove(this.userHand);
		System.out.println(sc.getTilesInHand(0));
		this.userHand = new UserHandPanel(sc.getTilesInHand(0),
				sc.getMaxHandSize());
		this.jContentPane.add(this.userHand, null);
		updateBoardFromGameState(sc.getCurrentGameState());
		ScrabbleFrame.sc = sc.getCurrentGameState();

	}

	public static void setDifficultyHard() {
		ScrabbleFrame.getScrabble().difficulty = 'C';
	}

	public static void setDifficultyNormal() {
		ScrabbleFrame.getScrabble().difficulty = 'F';
	}

	/**
	 * 
	 * Initializes the game from a saved.
	 * 
	 */
	private void initializeGUIFromFile() {
		JFileChooser chooser = new JFileChooser();
		String filePath;
		chooser.setCurrentDirectory(new java.io.File("scrabbleFiles/"));
		chooser.setDialogTitle("Please select a test file.");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { // the
																			// load
																			// game
																			// dialog
																			// box
			filePath = chooser.getSelectedFile().getPath();
			try {
				if (filePath.contains(".scrabble")) { // fails if you try to
														// open any other files
					ScrabbleFrame.currentScrabble = new Scrabble(
							ScrabbleFrame.dictionary, filePath);
					if (ScrabbleFrame.currentScrabble != null) {
						if (ScrabbleFrame.currentScrabble.getGameType() == "C")
							ScrabbleFrame.playGameButton.setEnabled(true);
						else
							ScrabbleFrame.playGameButton.setEnabled(false);
						ScrabbleFrame.stepOnceButton.setEnabled(true);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
			if (ScrabbleFrame.currentScrabble != null) {
				connectGUItoGsme(ScrabbleFrame.currentScrabble);
			}
		}
	}

	/**
	 * Returns the current scrabble.
	 * 
	 * @return scrabble.
	 */
	public static Scrabble getScrabble() {
		return ScrabbleFrame.currentScrabble;
	}

	/**
	 * 
	 * Saves the current scrabble game state to an file. holding the board state
	 * and game mode, as well as hands and score
	 * 
	 */
	private void initializeGUIToFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("scrabbleFiles/"));
		chooser.setDialogTitle("Please select a test file.");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = new File(chooser.getSelectedFile() + ".scrabble");
			try {
				PrintWriter writer = new PrintWriter(file);
				ArrayList<Character> temp = new ArrayList<Character>();
				// write the max size of user hand panel, the max board size and
				// the game type: human, computer or human vs. computer
				writer.println(ScrabbleFrame.currentScrabble.getMaxHandSize()
						+ " " + ScrabbleFrame.currentScrabble.getBoardSize()
						+ " " + ScrabbleFrame.currentScrabble.getGameType());
				// print all the tiles in hand.
				for (JLabel label : UserHandPanel.userHandLabels) {
					temp.add(label.getText().charAt(0));
				}
				for (Character c : temp)
					writer.print(c);
				// print player score.
				writer.println(" " + ScrabbleFrame.currentScrabble.getScore(0));
				// print computer score
				if (ScrabbleFrame.currentScrabble.getGameType().equals("2")) {
					temp = ScrabbleFrame.currentScrabble.getTilesInHand(1);
					for (Character c : temp)
						writer.print(c);
					writer.println(" "
							+ ScrabbleFrame.currentScrabble.getScore(1));
				}
				writer.println(ScrabbleFrame.currentScrabble.getPlayer());
				// print the board configurations.
				for (int r = 0; r < ScrabbleFrame.currentScrabble
						.getBoardSize(); r++) {
					for (int c = 0; c < ScrabbleFrame.currentScrabble
							.getBoardSize(); c++)
						writer.print(ScrabbleFrame.currentScrabble
								.getBoardConfiguration()[r][c]);
					writer.println();
				}
				// print the tiles on the board.
				for (int r = 0; r < ScrabbleFrame.currentScrabble
						.getBoardSize(); r++) {
					for (int c = 0; c < ScrabbleFrame.currentScrabble
							.getBoardSize(); c++)
						if (ScrabbleFrame.currentScrabble.getBoardChars()[r][c] == '\0')
							writer.print(" ");
						else
							writer.print(ScrabbleFrame.currentScrabble
									.getBoardChars()[r][c]);
					writer.println();
				}
				// write down the letters remaining in the tile bag.
				temp = ScrabbleFrame.currentScrabble.getTileBag();
				for (Character c : temp)
					writer.print(c);
				writer.println();
				writer.close();
			} catch (FileNotFoundException fnfException) {
				String msg = "Unable to save game: "
						+ fnfException.getMessage();
				JOptionPane.showMessageDialog(this, msg, "Save Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// Currently does not call gs.lastWordPlayed. You may want to fix that.
	private void updateBoardFromGameState(GameState gs) {
		if (gs != null) {
			ScrabbleFrame.scrabbleBoard.changeCharactersOnBoard(gs
					.getBoardChars());
			this.userHand.changeTilesInHand(gs.getTilesInHand());
			if (ScrabbleFrame.currentScrabble.getGameType().equals("2")) {
				this.jContentPane.remove(this.scoreLabel);
				this.jContentPane.remove(this.curScoreLabel);
				this.jContentPane.remove(this.scoreTurnLabel);
				this.jContentPane.remove(this.scoreThisTurnLabel);
				if (this.playerScoreLabel != null) {
					this.jContentPane.remove(this.playerScoreLabel);
				}
				if (this.computerScoreLabel != null) {
					this.jContentPane.remove(this.computerScoreLabel);
				}
				if (this.computerThisTurnLabel != null) {
					this.jContentPane.remove(this.computerThisTurnLabel);
				}

				this.scoreLabel = new JLabel();
				this.scoreLabel.setFont(new Font("Dialog", Font.BOLD, 18));
				this.scoreLabel.setSize(new Dimension(150, 25));
				this.scoreLabel.setLocation(new Point(345, 10));
				this.scoreLabel.setText("Score This Turn:");

				this.curScoreLabel = new JLabel();
				this.curScoreLabel
						.setHorizontalAlignment(SwingConstants.CENTER);
				this.curScoreLabel.setFont(new Font("Dialog", Font.BOLD, 30));
				this.curScoreLabel.setSize(new Dimension(150, 25));
				this.curScoreLabel.setLocation(new Point(345, 37));
				this.curScoreLabel.setText("");

				this.playerScoreLabel = new JLabel();
				this.playerScoreLabel
						.setFont(new Font("Dialog", Font.BOLD, 18));
				this.playerScoreLabel.setSize(new Dimension(150, 25));
				this.playerScoreLabel.setLocation(new Point(345, 62));
				this.playerScoreLabel.setText("Player Score:");

				this.scoreThisTurnLabel = new JLabel();
				this.scoreThisTurnLabel.setText("");
				this.scoreThisTurnLabel.setSize(new Dimension(150, 25));
				this.scoreThisTurnLabel
						.setHorizontalAlignment(SwingConstants.CENTER);
				this.scoreThisTurnLabel.setFont(new Font("Dialog", Font.BOLD,
						30));
				this.scoreThisTurnLabel.setLocation(new Point(345, 87));

				this.computerScoreLabel = new JLabel();
				this.computerScoreLabel.setFont(new Font("Dialog", Font.BOLD,
						18));
				this.computerScoreLabel.setSize(new Dimension(150, 25));
				this.computerScoreLabel.setLocation(new Point(345, 109));
				this.computerScoreLabel.setText("Computer Score:");

				this.computerThisTurnLabel = new JLabel();
				this.computerThisTurnLabel.setText("");
				this.computerThisTurnLabel.setSize(new Dimension(150, 25));
				this.computerThisTurnLabel
						.setHorizontalAlignment(SwingConstants.CENTER);
				this.computerThisTurnLabel.setFont(new Font("Dialog",
						Font.BOLD, 30));
				this.computerThisTurnLabel.setLocation(new Point(345, 134));

				this.getContentPane().add(this.scoreLabel, null);
				this.getContentPane().add(this.playerScoreLabel, null);
				this.getContentPane().add(this.computerScoreLabel, null);
				this.getContentPane().add(this.curScoreLabel, null);
				this.getContentPane().add(this.scoreThisTurnLabel, null);
				this.getContentPane().add(this.computerThisTurnLabel, null);

				this.curScoreLabel.setText(gs.getScoreThisPlay() + "");
				this.scoreThisTurnLabel.setText(gs.getPlayerScore() + "");
				this.computerThisTurnLabel.setText(gs.getComputerScore() + "");
			} else {
				this.curScoreLabel.setText(gs.getTotalScore() + "");
				this.scoreThisTurnLabel.setText(gs.getScoreThisPlay() + "");
			}
			this.repaint();
		} else {
			System.out.println("GameState to display is invalid");
		}
	}

	/**
	 * This method initializes stepOnceButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getStepOnceButton() {
		if (ScrabbleFrame.stepOnceButton == null) {
			ScrabbleFrame.stepOnceButton = new JButton();
			ScrabbleFrame.stepOnceButton.setText("Play Once");
			ScrabbleFrame.stepOnceButton.setSize(new Dimension(100, 50));
			ScrabbleFrame.stepOnceButton.setMnemonic(KeyEvent.VK_UNDEFINED);
			ScrabbleFrame.stepOnceButton.setLocation(new Point(150, 400));
			LineBorder border = new LineBorder(Color.black, 3);
			ScrabbleFrame.stepOnceButton.setBorder(border);
			ScrabbleFrame.stepOnceButton
					.setBackground(new Color(204, 153, 255));
			ScrabbleFrame.stepOnceButton
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							GameState gs = ScrabbleFrame.currentScrabble
									.playOnce();
							if (gs == null) {
								JOptionPane.showMessageDialog(null,
										"No play can be made.", "Game Over",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								updateBoardFromGameState(gs);
								ScrabbleFrame.sc = gs;
							}
							if (ScrabbleFrame.currentScrabble.getGameType()
									.equals("2")
									&& ScrabbleFrame.currentScrabble
											.getPlayer().equals("C")) {
								gs = ScrabbleFrame.currentScrabble.playOnce();
								if (gs == null) {
									JOptionPane.showMessageDialog(null,
											"No play can be made.",
											"Game Over",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									updateBoardFromGameState(gs);
									ScrabbleFrame.sc = gs;
								}
							}
						}
					});
		}
		return ScrabbleFrame.stepOnceButton;
	}

	/**
	 * This method initializes playWordButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getPlayWordButton() {
		if (ScrabbleFrame.playWordButton == null) {
			ScrabbleFrame.playWordButton = new JButton();
			ScrabbleFrame.playWordButton.setText("Play Word");
			ScrabbleFrame.playWordButton.setSize(new Dimension(100, 50));
			ScrabbleFrame.playWordButton.setMnemonic(KeyEvent.VK_UNDEFINED);
			ScrabbleFrame.playWordButton.setLocation(new Point(25, 400));
			LineBorder border = new LineBorder(Color.black, 3);
			ScrabbleFrame.playWordButton.setBorder(border);
			ScrabbleFrame.playWordButton
					.setBackground(new Color(204, 153, 255));
			ScrabbleFrame.playWordButton
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								// scrabble word checks whether it is a valid
								// word or not.
								ScrabbleWord wrd = new ScrabbleWord(
										ScrabbleBoard.wordList);
								// main word played by the user
								ScrabbleFrame.mainWord.add(wrd.toString());
								// sub words formed after playing the main word.
								ScrabbleFrame.subWords.add(wrd.getSubWords());
								// takes care of double and triple word scores.
								// points scored in this turn.
								ScrabbleFrame.points.add(wrd.getPoints());
								// sets the background of the valid word to
								// purple color to indicate that the word cannot
								// be changed now.
								for (ScrabbleTile tile : ScrabbleBoard.wordList) {
									if (Character.isLetter(tile.getLetter())) {
										ScrabbleBoard.getBoardLabel()[tile
												.getRow()][tile.getColumn()]
												.setBackground(new Color(160,
														32, 240));
										ScrabbleBoard.getBoardColor()[tile
												.getRow()][tile.getColumn()] = new Color(
												160, 32, 240);
										ScrabbleFrame.currentScrabble
												.setBoardChar(tile.getLetter(),
														tile.getRow(),
														tile.getColumn());
									}
								}
								ScrabbleBoard.wordList.clear();
								// sets the tiles in hand of the scrabble class
								// to the current tiles in hand.
								for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
									ScrabbleFrame.currentScrabble
											.getTilesInHand(0)
											.set(i,
													UserHandPanel.userHandLabels[i]
															.getText()
															.charAt(0));
								}
								// if there are more letters left in the tile
								// bag then it picks up letters from the tile
								// bag to put in the empty spots of the tile
								// hand of the user, and updates the scrabble
								// class's tiles in hand accordingly.
								if (ScrabbleFrame.currentScrabble.getTileBag()
										.size() > 0) {
									for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
										if (!Character
												.isLetter((UserHandPanel.userHandLabels[i]
														.getText().charAt(0)))) {
											Character c = ScrabbleFrame.currentScrabble
													.getTileBag()
													.remove(ScrabbleFrame.currentScrabble
															.getTileBag()
															.size() - 1);
											UserHandPanel.userHandLabels[i]
													.setText(c + "");
											ScrabbleFrame.currentScrabble
													.getTilesInHand(0)
													.set(i, c);
										}

									}

								} else {
									for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
										ScrabbleFrame.currentScrabble
												.getTilesInHand(0)
												.set(i,
														UserHandPanel.userHandLabels[i]
																.getText()
																.charAt(0));
									}
								}
								// updates the game state.
								int points = ScrabbleFrame.sc.getPlayerScore()
										+ wrd.getPoints();
								GameState gs = ScrabbleFrame.currentScrabble
										.playWord(wrd);
								if (ScrabbleFrame.currentScrabble.getGameType()
										.equals("2")) {
									gs.setPlayerScore(points);
								}
								updateBoardFromGameState(gs);
								ScrabbleFrame.sc = gs;
								// updates the total score.
								ScrabbleFrame.totalPoints.add(ScrabbleFrame.sc
										.getTotalScore());
								if (ScrabbleFrame.currentScrabble.getGameType() == "2") {
									gs = ScrabbleFrame.currentScrabble
											.playOnce();
									if (gs == null) {
										JOptionPane
												.showMessageDialog(
														null,
														"No play can be made.",
														"Game Over",
														JOptionPane.INFORMATION_MESSAGE);
									} else {
										gs.setPlayerScore(points);
										updateBoardFromGameState(gs);
										ScrabbleFrame.sc = gs;
									}
								}
							} catch (IndexOutOfBoundsException exp) {
								// pops up a window indicating that the word is
								// not valid.
								JFrame frame = new JFrame();
								frame.setSize(new Dimension(350, 100));
								JLabel message = new JLabel(
										"The word is not valid. Please, try again!");
								Font f = new Font("Times New Roman",
										Font.PLAIN, 19);
								message.setFont(f);
								frame.add(message);
								frame.setVisible(true);
								frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
							}
						}
					});
		}
		return ScrabbleFrame.playWordButton;
	}

	/**
	 * This method initializes playGameButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getPlayGameButton() {
		if (ScrabbleFrame.playGameButton == null) {
			ScrabbleFrame.playGameButton = new JButton("Play Rest");
			ScrabbleFrame.playGameButton.setSize(new Dimension(100, 50));
			ScrabbleFrame.playGameButton.setLocation(new Point(275, 400));
			LineBorder border = new LineBorder(Color.black, 3);
			ScrabbleFrame.playGameButton.setBorder(border);
			ScrabbleFrame.playGameButton
					.setBackground(new Color(204, 153, 255));
			ScrabbleFrame.playGameButton
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Date start = new Date();
							ArrayList<GameState> states = ScrabbleFrame.currentScrabble
									.playGame();
							Date end = new Date();
							long elapsedTime = end.getTime() - start.getTime();
							System.out.println(elapsedTime + " ms");
							if (states == null) {
								System.out
										.println("ArrayList of GameStates is null");
							} else {
								updateBoardFromGameState(states.get(states
										.size() - 1));
								ScrabbleFrame.sc = states.get(states.size() - 1);
							}
						}
					});
		}
		return ScrabbleFrame.playGameButton;
	}

	/**
	 * This method initializes quitButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getQuitButton() {
		if (this.quitButton == null) {
			this.quitButton = new JButton("Quit");
			this.quitButton.setSize(new Dimension(100, 50));
			this.quitButton.setLocation(new Point(400, 400));
			LineBorder border = new LineBorder(Color.black, 3);
			this.quitButton.setBorder(border);
			this.quitButton.setBackground(new Color(204, 153, 255));
			this.quitButton
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.exit(0);
						}
					});
		}
		return this.quitButton;
	}

	/**
	 * This method initializes jContentPane and adds all tha labels to the
	 * content pane.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (this.jContentPane == null) {
			this.scoreThisTurnLabel = new JLabel();
			this.scoreThisTurnLabel.setText("");
			this.scoreThisTurnLabel.setSize(new Dimension(150, 50));
			this.scoreThisTurnLabel
					.setHorizontalAlignment(SwingConstants.CENTER);
			this.scoreThisTurnLabel.setFont(new Font("Dialog", Font.BOLD, 36));
			this.scoreThisTurnLabel.setLocation(new Point(345, 125));
			this.scoreTurnLabel = new JLabel();
			this.scoreTurnLabel.setText("Score This Turn:");
			this.scoreTurnLabel.setSize(new Dimension(150, 30));
			this.scoreTurnLabel.setFont(new Font("Dialog", Font.BOLD, 18));
			this.scoreTurnLabel.setLocation(new Point(345, 95));
			this.curScoreLabel = new JLabel();
			this.curScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
			this.curScoreLabel.setFont(new Font("Dialog", Font.BOLD, 36));
			this.curScoreLabel.setSize(new Dimension(150, 44));
			this.curScoreLabel.setLocation(new Point(345, 45));
			this.curScoreLabel.setText("");
			this.scoreLabel = new JLabel();
			this.scoreLabel.setFont(new Font("Dialog", Font.BOLD, 24));
			this.scoreLabel.setSize(new Dimension(150, 30));
			this.scoreLabel.setLocation(new Point(345, 15));
			this.scoreLabel.setText("Total Score:");
			this.doubleLetterScoreLabel = new JLabel();
			this.doubleLetterScoreLabel.setText("Double Letter Score");
			this.doubleLetterScoreLabel.setSize(new Dimension(150, 25));
			this.doubleLetterScoreLabel.setBackground(doubleLetterScoreColor);
			this.doubleLetterScoreLabel.setForeground(Color.white);
			this.doubleLetterScoreLabel
					.setHorizontalAlignment(SwingConstants.CENTER);
			this.doubleLetterScoreLabel.setLocation(new Point(345, 285));
			this.doubleLetterScoreLabel.setOpaque(true);
			this.tripleLetterScoreLabel = new JLabel();
			this.tripleLetterScoreLabel.setText("Triple Letter Score");
			this.tripleLetterScoreLabel.setSize(new Dimension(150, 25));
			this.tripleLetterScoreLabel.setBackground(tripleLetterScoreColor);
			this.tripleLetterScoreLabel.setForeground(Color.white);
			this.tripleLetterScoreLabel
					.setHorizontalAlignment(SwingConstants.CENTER);
			this.tripleLetterScoreLabel.setLocation(new Point(345, 250));
			this.tripleLetterScoreLabel.setOpaque(true);
			this.doubleWordScoreLabel = new JLabel();
			this.doubleWordScoreLabel.setPreferredSize(new Dimension(150, 60));
			this.doubleWordScoreLabel.setLocation(new Point(345, 215));
			this.doubleWordScoreLabel.setSize(new Dimension(150, 25));
			this.doubleWordScoreLabel.setBackground(doubleWordScoreColor);
			this.doubleWordScoreLabel.setForeground(Color.white);
			this.doubleWordScoreLabel
					.setHorizontalAlignment(SwingConstants.CENTER);
			this.doubleWordScoreLabel.setText("Double Word Score");
			this.doubleWordScoreLabel.setOpaque(true);
			this.tripleWordScoreLabel = new JLabel();
			this.tripleWordScoreLabel.setText("Triple Word Score");
			this.tripleWordScoreLabel.setSize(new Dimension(150, 25));
			this.tripleWordScoreLabel.setBackground(tripleWordScoreColor);
			this.tripleWordScoreLabel.setForeground(Color.white);
			this.tripleWordScoreLabel
					.setHorizontalAlignment(SwingConstants.CENTER);
			this.tripleWordScoreLabel.setLocation(new Point(345, 180));
			this.tripleWordScoreLabel.setOpaque(true);
			this.jContentPane = new JPanel();
			this.jContentPane.setLayout(null);
			ScrabbleFrame.scrabbleBoard = new ScrabbleBoard();
			this.userHand = new UserHandPanel();
			this.jContentPane.add(ScrabbleFrame.scrabbleBoard, null);
			this.jContentPane.add(this.userHand, null);
			this.jContentPane.add(this.tripleWordScoreLabel, null);
			this.jContentPane.add(this.doubleWordScoreLabel, null);
			this.jContentPane.add(this.tripleLetterScoreLabel, null);
			this.jContentPane.add(this.doubleLetterScoreLabel, null);
			this.jContentPane.add(getPlayWordButton(), null);
			this.jContentPane.add(getStepOnceButton(), null);
			this.jContentPane.add(getPlayGameButton(), null);
			this.jContentPane.add(getQuitButton(), null);
			this.jContentPane.add(this.scoreLabel, null);
			this.jContentPane.add(this.curScoreLabel, null);
			this.jContentPane.add(this.scoreTurnLabel, null);
			this.jContentPane.add(this.scoreThisTurnLabel, null);
		}
		return this.jContentPane;
	}

	/**
	 * Returns the scrabble board.
	 * 
	 * @return scrabble board.
	 */
	public static ScrabbleBoard getScrabbleBoard() {
		return scrabbleBoard;
	}

	/**
	 * Stores the list of main words.
	 */
	public static ArrayList<String> mainWord = new ArrayList<String>();

	/**
	 * Returns the menu bar.
	 * 
	 * @return
	 */
	private JMenuBar getModeMenu() {
		if (this.modeBar == null) {
			this.modeBar = new JMenuBar();
			// initializes the file menu for new game, load game, save game and
			// end game.
			JMenu file = new JMenu("File");
			JMenuItem load = new JMenuItem("Load Game");
			JMenuItem save = new JMenuItem("Save Game");
			JMenu newgameMenu = new JMenu("New Game");
			JMenuItem newHumanGame = new JMenuItem("Human");
			JMenuItem newComputerGame = new JMenuItem("Computer");
			JMenuItem new2PlayerGame = new JMenuItem("Human vs. Computer");
			JMenuItem endGame = new JMenuItem("End Game");
			JMenuItem difficulty = new JMenu("Difficulty");
			JMenuItem fastMode = new JMenuItem("Fast");
			JMenuItem completeMode = new JMenuItem("Complete");
			difficulty.add(fastMode);
			difficulty.add(completeMode);
			newgameMenu.add(newHumanGame);
			newgameMenu.add(newComputerGame);
			newgameMenu.add(new2PlayerGame);
			file.add(difficulty);
			file.add(newgameMenu);
			file.add(load);
			file.add(save);
			file.add(endGame);
			this.modeBar.add(file);
			load.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initializeGUIFromFile();
				}
			});
			save.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initializeGUIToFile();
				}
			});
			newHumanGame.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// start new game.
					// enable the play word, play once and play rest buttons.
					ScrabbleFrame.currentScrabble.clearTotalScore();
					ScrabbleFrame.points.clear();
					ScrabbleFrame.totalPoints.clear();
					ScrabbleFrame.mainWord.clear();
					ScrabbleFrame.subWords.clear();
					ScrabbleFrame.playGameButton.setEnabled(false);
					ScrabbleFrame.playWordButton.setEnabled(true);
					ScrabbleFrame.stepOnceButton.setEnabled(true);
					ScrabbleBoard.wordList.clear();
					// reset the tile bag.
					ScrabbleFrame.currentScrabble.resetTileBag();
					ArrayList<Character> tilesInHand = new ArrayList<Character>();
					// remove current hand start with a fresh one.
					ScrabbleFrame.exchange();
					for (JLabel label : UserHandPanel.userHandLabels) {
						tilesInHand.add(label.getText().charAt(0));
					}
					// reset the board characters to null.
					for (int i = 0; i < ScrabbleFrame.currentScrabble
							.getBoardSize(); i++) {
						for (int j = 0; j < ScrabbleFrame.currentScrabble
								.getBoardSize(); j++) {
							ScrabbleFrame.currentScrabble.setBoardChar('\0', i,
									j);
						}
					}
					// reset the board labels and colors.
					ScrabbleBoard.reset();
					// update the game state.
					GameState gs = new GameState(tilesInHand, 0, 0,
							ScrabbleFrame.currentScrabble.getBoardChars(),
							ScrabbleFrame.currentScrabble.getTileBag(), "");
					updateBoardFromGameState(gs);
					ScrabbleFrame.sc = gs;
				}
			});
			newComputerGame
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// start new game.
							// enable the play word, play once and play rest
							// buttons.
							ScrabbleFrame.currentScrabble.clearTotalScore();
							ScrabbleFrame.points.clear();
							ScrabbleFrame.totalPoints.clear();
							ScrabbleFrame.mainWord.clear();
							ScrabbleFrame.subWords.clear();
							ScrabbleFrame.playGameButton.setEnabled(true);
							ScrabbleFrame.playWordButton.setEnabled(false);
							ScrabbleFrame.stepOnceButton.setEnabled(true);
							ScrabbleBoard.wordList.clear();
							// reset the tile bag.
							ScrabbleFrame.currentScrabble.resetTileBag();
							ScrabbleFrame.currentScrabble.setGameType("C");
							ScrabbleFrame.currentScrabble.setPlayer("C");
							ArrayList<Character> tilesInHand = new ArrayList<Character>();
							// remove current hand start with a fresh one.
							ScrabbleFrame.exchange();
							for (JLabel label : UserHandPanel.userHandLabels) {
								tilesInHand.add(label.getText().charAt(0));
							}
							// reset the board characters to null.
							for (int i = 0; i < ScrabbleFrame.currentScrabble
									.getBoardSize(); i++) {
								for (int j = 0; j < ScrabbleFrame.currentScrabble
										.getBoardSize(); j++) {
									ScrabbleFrame.currentScrabble.setBoardChar(
											'\0', i, j);
								}
							}
							// reset the board labels and colors.
							ScrabbleBoard.reset();
							// update the game state.
							GameState gs = new GameState(tilesInHand, 0, 0,
									ScrabbleFrame.currentScrabble
											.getBoardChars(),
									ScrabbleFrame.currentScrabble.getTileBag(),
									"");
							updateBoardFromGameState(gs);
							ScrabbleFrame.sc = gs;
						}
					});
			new2PlayerGame
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// start new game.
							// enable the play word, play once and play rest
							// buttons.
							ScrabbleFrame.currentScrabble.clearTotalScore();
							ScrabbleFrame.points.clear();
							ScrabbleFrame.totalPoints.clear();
							ScrabbleFrame.mainWord.clear();
							ScrabbleFrame.subWords.clear();
							ScrabbleFrame.playGameButton.setEnabled(false);
							ScrabbleFrame.playWordButton.setEnabled(true);
							ScrabbleFrame.stepOnceButton.setEnabled(true);
							ScrabbleBoard.wordList.clear();
							// reset the tile bag.
							ScrabbleFrame.currentScrabble.resetTileBag();
							ScrabbleFrame.currentScrabble.setGameType("2");
							ArrayList<Character> tilesInHand = new ArrayList<Character>();
							// remove current hand start with a fresh one.
							ScrabbleFrame.exchange();
							for (JLabel label : UserHandPanel.userHandLabels) {
								tilesInHand.add(label.getText().charAt(0));
							}
							// reset the board characters to null.
							for (int i = 0; i < ScrabbleFrame.currentScrabble
									.getBoardSize(); i++) {
								for (int j = 0; j < ScrabbleFrame.currentScrabble
										.getBoardSize(); j++) {
									ScrabbleFrame.currentScrabble.setBoardChar(
											'\0', i, j);
								}
							}
							// reset the board labels and colors.
							ScrabbleBoard.reset();
							// update the game state.
							GameState gs = new GameState(tilesInHand, 0, 0,
									ScrabbleFrame.currentScrabble
											.getBoardChars(),
									ScrabbleFrame.currentScrabble.getTileBag(),
									"");
							updateBoardFromGameState(gs);
							ScrabbleFrame.sc = gs;
						}
					});
			endGame.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// pops up a window indicating the game is over and
					// displaying the total score and whether you won or not.
					ScrabbleFrame.endGame();
				}
			});
			completeMode.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub.
					JFrame frame = new JFrame();
					ScrabbleFrame.setDifficultyHard();
					JOptionPane
							.showMessageDialog(
									frame,
									"Warning: We do not guarantee this mode for quick play,\nit uses a more complex but complete algorithm.\nIn general it will find better words,\nbut will take quite a bit longer, use at your own peril.");

				}
			});
			fastMode.addActionListener(new java.awt.event.ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					ScrabbleFrame.setDifficultyNormal();

				}
			});

			// initializes the menu for game play options.
			JMenu options = new JMenu("GamePlay Opitions");
			JMenuItem exchange = new JMenuItem("Exchange Tiles");
			JMenuItem shuffle = new JMenuItem("Shuffle tile bag");
			JMenuItem history = new JMenuItem("Move History");
			options.add(exchange);
			options.add(shuffle);
			options.add(history);
			exchange.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// does not allow to exchange tiles if one your hand tile is
					// missing.
					for (JLabel labels : UserHandPanel.userHandLabels) {
						if (!Character.isLetter(labels.getText().charAt(0))) {
							return;
						}
					}

					// exchanges if there are tiles in the bag.
					if (ScrabbleFrame.currentScrabble.getTileBag().size() > 0) {
						if (ScrabbleFrame.currentScrabble.getPlayer().equals(
								"H")) {
							// exchanges the tiles.
							UserHandPanel.isExchange = true;
							UserHandPanel.exchangeTiles();
							// UserHandPanel.isExchange = false;

						} else {
							ScrabbleFrame.exchange();
						}
						for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
							// updates the tiles in hands of the scrabble class.
							ScrabbleFrame.currentScrabble.getTilesInHand(0)
									.set(i,
											UserHandPanel.userHandLabels[i]
													.getText().charAt(0));
						}
						// updates the game state.
						ScrabbleFrame.sc
								.setTilesInHand(ScrabbleFrame.currentScrabble
										.getTilesInHand(0));
					}
				}
			});
			shuffle.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// shuffle tiles
					ScrabbleFrame.currentScrabble.shuffle();
				}
			});
			history.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// create a frame for displaying the move history.
					JTextArea rul = new JTextArea();
					JFrame newFrame = new JFrame("MOVE HISTORY");
					JScrollPane scrollPane = new JScrollPane(rul);
					scrollPane.setVisible(true);
					rul.setBackground(Color.black);
					rul.setForeground(Color.white);
					Font f = new Font("Times New Roman", Font.PLAIN, 19);
					rul.setFont(f);
					newFrame.setSize(new Dimension(650, 600));
					newFrame.setVisible(true);
					newFrame.add(scrollPane);
					newFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					// loops through all the main words played in the game so
					// far and adds it to the text area.
					for (int i = 0; i < ScrabbleFrame.mainWord.size(); i++) {
						rul.append("Main Word Played: ");
						rul.append(ScrabbleFrame.mainWord.get(i) + " ");
						rul.append("\nSub Words Played: ");
						// loops through each sub word for the given main word
						// and adds it to the text area.
						for (int j = 0; j < ScrabbleFrame.subWords.get(i)
								.size(); j++) {
							if (j == 0) {
								rul.append(ScrabbleFrame.subWords.get(i).get(j)
										.toLowerCase()
										+ " ");
							} else if (j >= 1) {
								if (!ScrabbleFrame.subWords
										.get(i)
										.get(j)
										.equals(ScrabbleFrame.subWords.get(i)
												.get(j - 1))) {
									rul.append(ScrabbleFrame.subWords.get(i)
											.get(j).toLowerCase()
											+ " ");
								}
							}
						}
						rul.append("\n");
						// adds the score of the play to the text area.
						rul.append("Score of the play: "
								+ ScrabbleFrame.points.get(i) + "\n");
						// adds the total score to the text area.
						rul.append("Total Score: "
								+ ScrabbleFrame.totalPoints.get(i) + "\n");
						rul.append("\n");
					}
				}
			});
			this.modeBar.add(options);

			// initializes the menu for gui and game play help.
			JMenu help = new JMenu("Help");
			JMenuItem guiHelp = new JMenuItem("GUI Help");
			JMenuItem rules = new JMenuItem("Scrabble Rules");
			help.add(guiHelp);
			help.add(rules);
			guiHelp.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// creates a frame and reads the help.txt file and appends
					// it to the text area.
					JTextArea rul = new JTextArea();
					JFrame newFrame = new JFrame("GUI HELP");
					JScrollPane scrollPane = new JScrollPane(rul);
					scrollPane.setVisible(true);
					try {
						Scanner in = new Scanner(new File("Help.txt"));
						while (in.hasNextLine()) {
							rul.append(in.nextLine());
							if (in.hasNextLine()) {
								rul.append("\n");
							}
						}
						in.close();
					} catch (FileNotFoundException exception) {
						// do nothing.
					}
					rul.setBackground(Color.black);
					rul.setForeground(Color.white);
					Font f = new Font("Times New Roman", Font.PLAIN, 19);
					rul.setFont(f);
					newFrame.setSize(new Dimension(650, 600));
					newFrame.setVisible(true);
					newFrame.add(scrollPane);
					newFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				}
			});
			rules.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// creates a frame and reads the rules.txt file and appends
					// it to the text area.
					JTextArea rul = new JTextArea();
					JFrame newFrame = new JFrame("SCRABBLE RULES");
					JScrollPane scrollPane = new JScrollPane(rul);
					scrollPane.setVisible(true);
					try {
						Scanner in = new Scanner(new File("Rules.txt"));
						while (in.hasNextLine()) {
							rul.append(in.nextLine());
							if (in.hasNextLine()) {
								rul.append("\n");
							}
						}
						in.close();
					} catch (FileNotFoundException exception) {
						// do nothing.
					}
					rul.setBackground(Color.black);
					rul.setForeground(Color.white);
					Font f = new Font("Times New Roman", Font.PLAIN, 19);
					rul.setFont(f);
					newFrame.setSize(new Dimension(650, 600));
					newFrame.setVisible(true);
					newFrame.add(scrollPane);
					newFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				}
			});
			this.modeBar.add(help);
			this.modeBar.setVisible(true);
		}
		return this.modeBar;
	}

	/**
	 * Ends the game.
	 * 
	 */
	public static void endGame() {
		JFrame frame = new JFrame("Scrabble");
		if (ScrabbleFrame.currentScrabble.getGameType().equals("2")) {
			frame.setSize(new Dimension(390, 150));
		} else {
			frame.setSize(new Dimension(220, 110));
		}
		frame.setLocation(530, 0);
		frame.setLayout(new BorderLayout());
		JLabel message = new JLabel("GAME OVER!!");
		Font f = new Font("Algerian", Font.PLAIN, 30);
		message.setFont(f);
		if (ScrabbleFrame.currentScrabble.getGameType().equals("2")) {

			int points = 0;
			int compPoints = 0;
			for (JLabel label : UserHandPanel.userHandLabels) {
				if (Character.isLetter(label.getText().charAt(0))) {
					ScrabbleTile tile = new ScrabbleTile(label.getText()
							.charAt(0), (byte) 0, (byte) 1, false);
					points += tile.getPoints();
				}
			}
			for (Character c : ScrabbleFrame.currentScrabble.getTilesInHand(1)) {
				if (Character.isLetter(c)) {
					ScrabbleTile tile = new ScrabbleTile(c, (byte) 0, (byte) 1,
							false);
					compPoints += tile.getPoints();
				}
			}
			JLabel score = new JLabel("Player Score: "
					+ (ScrabbleFrame.sc.getPlayerScore() - points)
					+ "     Computer Score: "
					+ (ScrabbleFrame.sc.getComputerScore() - compPoints));
			Font f1 = new Font("Comic San SMS", Font.PLAIN, 19);
			score.setFont(f1);
			frame.add(score, BorderLayout.CENTER);
			JLabel win = new JLabel();
			if ((ScrabbleFrame.sc.getComputerScore() - compPoints) > (ScrabbleFrame.sc
					.getPlayerScore() - points)) {
				win.setText("Computer Wins");
			} else if ((ScrabbleFrame.sc.getComputerScore() - compPoints) < (ScrabbleFrame.sc
					.getPlayerScore() - points)) {
				win.setText("Player Wins");
			} else {
				win.setText("It is a tie");
			}
			win.setFont(f1);
			frame.add(win, BorderLayout.SOUTH);
		} else {
			int points = 0;
			for (JLabel label : UserHandPanel.userHandLabels) {
				if (Character.isLetter(label.getText().charAt(0))) {
					ScrabbleTile tile = new ScrabbleTile(label.getText()
							.charAt(0), (byte) 0, (byte) 1, false);
					points += tile.getPoints();
				}
			}
			JLabel score = new JLabel("Total Score: "
					+ (ScrabbleFrame.sc.getTotalScore() - points));
			Font f1 = new Font("Comic San SMS", Font.PLAIN, 19);
			score.setFont(f1);
			frame.add(score, BorderLayout.SOUTH);
		}

		frame.add(message, BorderLayout.NORTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// disable buttons so that the user cannot continue after
		// ending game and has to quit or start new game.
		ScrabbleFrame.playGameButton.setEnabled(false);
		ScrabbleFrame.playWordButton.setEnabled(false);
		ScrabbleFrame.stepOnceButton.setEnabled(false);
	}

	/**
	 * Exchanges the tiles in hand with 7 tiles from the tile bag.
	 * 
	 */
	public static void exchange() {
		// temp variable to store current hand tiles.
		ArrayList<Character> swap = new ArrayList<Character>();
		for (JLabel labels : UserHandPanel.userHandLabels) {
			swap.add(labels.getText().charAt(0));
		}
		// get 7 tiles from tile bag and add it to the user hand panel.
		for (int i = 0; i < UserHandPanel.userHandLabels.length; i++) {
			int pos = ScrabbleFrame.currentScrabble.getTileBag().size() - 1;
			if (pos == -1) {
				break;
			}
			UserHandPanel.userHandLabels[i]
					.setText(ScrabbleFrame.currentScrabble.getTileBag()
							.remove(ScrabbleFrame.currentScrabble.getTileBag()
									.size() - 1)
							+ "");
		}
		// put the original tiles in hand back into the bag and shuffle.
		for (Character c : swap) {
			ScrabbleFrame.currentScrabble.getTileBag().add(c);
			ScrabbleFrame.currentScrabble.shuffle();
		}
	}

	/**
	 * Exchanges the given tiles.
	 * 
	 * @param exchange
	 */
	public static void exchange(ArrayList<Integer> exchange) {
		ArrayList<Character> ch = new ArrayList<Character>();
		for (Integer i : exchange) {
			if (ScrabbleFrame.currentScrabble.getTileBag().size() > 0) {
				Character c = ScrabbleFrame.currentScrabble.getTileBag()
						.remove(ScrabbleFrame.currentScrabble.getTileBag()
								.size() - 1);
				ch.add(UserHandPanel.userHandLabels[i].getText().charAt(0));
				UserHandPanel.userHandLabels[i].setText(c + "");
				ScrabbleFrame.currentScrabble.getTilesInHand(0).set(i, c);
			}
		}
		for (Character c : ch) {
			ScrabbleFrame.currentScrabble.getTileBag().add(c);
		}
		ScrabbleFrame.currentScrabble.shuffle();
	}

}
