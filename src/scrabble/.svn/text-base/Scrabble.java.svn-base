package scrabble;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Maintains the state of the game, checks for legal plays, calculates scores,
 * finds best plays.
 * 
 * 
 * @author Amanda Stephan, Significantly refactored by Claude Anderson and Matt
 *         Boutell April, 2011
 * 
 */
public class Scrabble {

	/**
	 * Default board size
	 */
	public static final int DEFAULT_BOARD_SIZE = 15;
	/**
	 * Default maximum number of tiles in hand
	 */
	public static final int DEFAULT_MAX_HAND_SIZE = 7;
	/**
	 * Default board configuration
	 */
	public static final char[][] DEFAULT_BOARD_LAYOUT = new char[][] {
			{ 'T', ' ', ' ', 'd', ' ', ' ', ' ', 'T', ' ', ' ', ' ', 'd', ' ',
					' ', 'T' },
			{ ' ', 'D', ' ', ' ', ' ', 't', ' ', ' ', ' ', 't', ' ', ' ', ' ',
					'D', ' ' },
			{ ' ', ' ', 'D', ' ', ' ', ' ', 'd', ' ', 'd', ' ', ' ', ' ', 'D',
					' ', ' ' },
			{ 'd', ' ', ' ', 'D', ' ', ' ', ' ', 'd', ' ', ' ', ' ', 'D', ' ',
					' ', 'd' },
			{ ' ', ' ', ' ', ' ', 'D', ' ', ' ', ' ', ' ', ' ', 'D', ' ', ' ',
					' ', ' ' },
			{ ' ', 't', ' ', ' ', ' ', 't', ' ', ' ', ' ', 't', ' ', ' ', ' ',
					't', ' ' },
			{ ' ', ' ', 'd', ' ', ' ', ' ', 'd', ' ', 'd', ' ', ' ', ' ', 'd',
					' ', ' ' },
			{ 'T', ' ', ' ', 'd', ' ', ' ', ' ', '*', ' ', ' ', ' ', 'd', ' ',
					' ', 'T' },
			{ ' ', ' ', 'd', ' ', ' ', ' ', 'd', ' ', 'd', ' ', ' ', ' ', 'd',
					' ', ' ' },
			{ ' ', 't', ' ', ' ', ' ', 't', ' ', ' ', ' ', 't', ' ', ' ', ' ',
					't', ' ' },
			{ ' ', ' ', ' ', ' ', 'D', ' ', ' ', ' ', ' ', ' ', 'D', ' ', ' ',
					' ', ' ' },
			{ 'd', ' ', ' ', 'D', ' ', ' ', ' ', 'd', ' ', ' ', ' ', 'D', ' ',
					' ', 'd' },
			{ ' ', ' ', 'D', ' ', ' ', ' ', 'd', ' ', 'd', ' ', ' ', ' ', 'D',
					' ', ' ' },
			{ ' ', 'D', ' ', ' ', ' ', 't', ' ', ' ', ' ', 't', ' ', ' ', ' ',
					'D', ' ' },
			{ 'T', ' ', ' ', 'd', ' ', ' ', ' ', 'T', ' ', ' ', ' ', 'd', ' ',
					' ', 'T' } };
	/*
	 * t = tripleWordScore D = doubleWordScore t = tripleLetterScore d =
	 * doubleLetterScore
	 */

	/**
	 * Array storing the standard letter point values A-Z (blank is at the end).
	 */
	public static final int[] DEFAULT_POINT_VALUES = { 1, 3, 3, 2, 1, 4, // A-Z,
																			// then
																			// the
																			// blank
			2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10, 0 };

	/**
	 * Array storing the standard number of tiles for each letter A-Z (blank is
	 * at the end)
	 */
	public static final int[] DEFAULT_LETTER_COUNTS = { 9, 2, 2, 4, 12, 2, // A-Z,
																			// then
																			// the
																			// blank
			3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1, 2 };

	private static final char blankTile = 'b';

	// The fields below are not necessarily required fields. As long as your get
	// methods below return something
	// equivalent to these fields, you may change these fields around. If you
	// do, you may need to adjust the
	// GUI code.

	private ScrabbleDictionary dictionary;
	private int boardSize; // Number of rows (equals number of columns)
	private char[][] boardConfiguration; // Where are the double and triple
											// letters and words?
	private char[][] boardChars; // Current letters played on the board
	private ArrayList<Character> tileBag; // Remaining tiles not on board or in
											// player's hand.
											// Last character in list is the
											// next character to be drawn.
	private int maxHandSize; // Maximum # of tiles player can have in hand at
								// once.
	private ArrayList<Character>[] tilesInHand; // Current tiles in the player's
												// hand.
	private int scoreThisTurn;
	private int[] totalScore;
	private String lastWordPlayed;
	private String gameType;
	private String player;
	public static char difficulty;

	/**
	 * Perform initializations common to all Scrabble games.
	 */
	@SuppressWarnings("unchecked")
	public Scrabble() {
		this.scoreThisTurn = 0;
		this.totalScore = new int[2];
		this.totalScore[0] = this.totalScore[1] = 0;
		this.tilesInHand = new ArrayList[2];
		this.lastWordPlayed = "";
		this.gameType = "H";
		this.player = this.gameType;
		Scrabble.difficulty = 'F';
	}

	/**
	 * Creates a new standard scrabble game with random tile bag
	 * 
	 * @param dictionary
	 * @throws Exception
	 */
	public Scrabble(ScrabbleDictionary dictionary) throws Exception {
		this(dictionary, (ArrayList<Character>) null);
	}

	/**
	 * Create a new scrabble game in which no plays have been made.
	 * 
	 * @param dictionary
	 *            - dictionary to be used in this instance of Scrabble
	 * @param tileBag
	 *            - tiles to be drawn from
	 */
	public Scrabble(ScrabbleDictionary dictionary, ArrayList<Character> tileBag) {
		this();
		this.boardSize = Scrabble.DEFAULT_BOARD_SIZE;
		this.boardConfiguration = Scrabble.DEFAULT_BOARD_LAYOUT;
		this.maxHandSize = Scrabble.DEFAULT_MAX_HAND_SIZE;
		this.boardChars = new char[this.boardSize][this.boardSize];
		if (tileBag == null) {
			this.tileBag = new ArrayList<Character>();
			for (int i = 0; i < Scrabble.DEFAULT_LETTER_COUNTS.length - 1; i++) { // don't
																					// include
																					// blanks
																					// here.
				for (int j = 0; j < Scrabble.DEFAULT_LETTER_COUNTS[i]; j++) {
					this.tileBag.add((char) ('A' + i));
				}
			}
			for (int i = 0; i < Scrabble.DEFAULT_LETTER_COUNTS[Scrabble.DEFAULT_LETTER_COUNTS.length - 1]; i++) {
				this.tileBag.add(Scrabble.blankTile);
			}
			shuffle();

		} else {
			this.tileBag = tileBag;
		}
		this.tilesInHand[0] = this.tilesInHand[1] = new ArrayList<Character>();
		for (int i = 0; i < this.maxHandSize; i++)
			this.drawTile(0);
		if (this.gameType == "2")
			for (int i = 0; i < this.maxHandSize; i++)
				this.drawTile(1);
	}

	/**
	 * Resets the tile bag for new game.
	 * 
	 */
	public void resetTileBag() {
		this.tileBag.clear();
		for (int i = 0; i < Scrabble.DEFAULT_LETTER_COUNTS.length - 1; i++) {
			for (int j = 0; j < Scrabble.DEFAULT_LETTER_COUNTS[i]; j++) {
				this.tileBag.add((char) ('A' + i));
			}
		}
		for (int i = 0; i < Scrabble.DEFAULT_LETTER_COUNTS[Scrabble.DEFAULT_LETTER_COUNTS.length - 1]; i++) {
			this.tileBag.add(Scrabble.blankTile);
		}
		shuffle();
	}

	/**
	 * Move last tile in tileBag to the player's hand
	 * 
	 * @param hand
	 *            the hand to add the tiles to
	 */
	public void drawTile(int hand) {
		this.tilesInHand[hand]
				.add(this.tileBag.remove(this.tileBag.size() - 1));
	}

	/**
	 * Creates a Scrabble object using the data in the given .scrabble file (the
	 * state of the game at any given moment).
	 * 
	 * The format of the .scrabble file must follow the example shown in the
	 * specification document.
	 * 
	 * @param dictionary
	 *            A Scrabble dictionary
	 * @param fileName
	 *            name (must end in '.scrabble')
	 * @throws IOException
	 *             if the given file name is not found or does not end in
	 *             scrabble
	 */
	public Scrabble(ScrabbleDictionary dictionary, String fileName)
			throws IOException {
		this();
		this.dictionary = dictionary;

		// reader for the input test file
		BufferedReader myReader;

		// verifies that it is a .scrabble file and the file exists
		// String[] check = input.split(".");
		// if(check[1].equals("scrabble")){
		myReader = new BufferedReader(new FileReader(new File(fileName)));
		// }
		// else {
		// throw new IOException("Not a .scrabble file");
		// }

		// Gets the handsize, boardsize, tilesInHand, & sDictionary from the
		// first line of a test file
		String line = myReader.readLine();
		String[] line1 = line.split(" ");
		this.maxHandSize = Integer.parseInt(line1[0]);
		this.boardSize = Integer.parseInt(line1[1]);
		this.gameType = line1[2];
		// You probably want to make mode a field, and you definitely
		// want to handle all three modes
		line = myReader.readLine();
		line1 = line.split(" ");
		this.tilesInHand[0] = new ArrayList<Character>();
		this.tilesInHand[1] = new ArrayList<Character>();
		for (int i = 0; i < line1[0].length(); i++) {
			this.tilesInHand[0].add(line1[0].charAt(i));
		}
		this.totalScore[0] = Integer.parseInt(line1[1]);
		if (this.gameType.equals("2")) {
			line = myReader.readLine();
			line1 = line.split(" ");
			for (int i = 0; i < line1[0].length(); i++) {
				this.tilesInHand[1].add(line1[0].charAt(i));
			}
			this.totalScore[1] = Integer.parseInt(line1[1]);
			line = myReader.readLine();
			line1 = line.split(" ");
			this.player = line1[0];
		}

		// Sets the boards point square layout from the test file
		this.boardConfiguration = new char[this.boardSize][this.boardSize];
		for (int i = 0; i < this.boardSize; i++) {
			line = myReader.readLine();
			for (int j = 0; j < this.boardSize; j++) {
				this.boardConfiguration[i][j] = line.charAt(j);
			}
		}

		// gets the current setup on the board (which letters have been played
		// where)
		this.boardChars = new char[this.boardSize][this.boardSize];
		for (int i = 0; i < this.boardSize; i++) {
			line = myReader.readLine();
			for (int j = 0; j < this.boardSize; j++) {
				this.boardChars[i][j] = line.charAt(j);
			}
		}

		// gets the provided tile bag (the last line in the scrabble file)
		line = myReader.readLine();
		this.tileBag = new ArrayList<Character>();
		for (int i = 0; i < line.length(); i++) {
			this.tileBag.add(line.charAt(i));
		}
	}

	/**
	 * Accessor method for Scrabble's currentGameState field.
	 * 
	 * @return the current GameState in the Scrabble class.
	 */
	public GameState getCurrentGameState() {
		if (this.gameType.equals("2") && this.player.equals("C"))
			return new GameState(this.tilesInHand[1], this.totalScore[1],
					this.scoreThisTurn, this.boardChars, this.tileBag,
					this.lastWordPlayed);
		return new GameState(this.tilesInHand[0], this.totalScore[0],
				this.scoreThisTurn, this.boardChars, this.tileBag,
				this.lastWordPlayed);
	}

	/**
	 * Will play one turn in the Scrabble game and return a new GameState that
	 * contains the values that have changed during play. See the assignment
	 * document for more specifics.
	 * 
	 * @return a GameState containing the changed values after a single turn.
	 */
	public GameState playOnce() {

		GameState gs = ScrabbleComputer.play(this.gameType, this.player,
				difficulty);
		if (this.gameType == "2") {
			if (this.player == "C")
				this.player = "H";
			else
				this.player = "C";
		}
		if (gs == null) {
			ScrabbleFrame.endGame();
		}
		return gs;
	}

	/**
	 * Will play the entire game and return an ArrayList of GameStates that
	 * represent each step of play from the end of the next play until the end
	 * of the game.
	 * 
	 * @return ArrayList of GameState objects representing each step in the
	 *         game.
	 */
	public ArrayList<GameState> playGame() {
		/*
		 * While there are tiles left in the computer player's hand and there
		 * are valid moves left on the board, playOnce(). Then, return the
		 * arraylist of gamestates that were returned by playOnce.
		 */
		ArrayList<GameState> states = new ArrayList<GameState>();
		GameState state;

		while ((state = this.playOnce()) != null) {
			ScrabbleFrame.sc = state;
			states.add(state);
		}

		return states;
	}

	/**
	 * Plays toPlay (in the order given), starting at zero-based position
	 * (rowStart, colStart). Plays vertically if vertical==true, otherwise plays
	 * horizontally. If there is already a letter in a square in which we try to
	 * place a tile, skip over it and place that tile in the next available
	 * space.
	 * 
	 * @param toPlay
	 * @param rowStart
	 * @param colStart
	 * @param vertical
	 * @return the new state of the game after this play (including replacing
	 *         player's tiles from the tileBag.
	 * @throws IllegalArgumentException
	 *             if the play tries to make an illegal word.
	 * @throws IndexOutOfBoundsException
	 *             if the placement is illegal (some tiles would be outside the
	 *             board, first play does not include the center square,
	 *             subsequent play does not touch any previously-played letters.
	 * 
	 */
	public GameState playOnceHuman(String toPlay, int rowStart, int colStart,
			boolean vertical) throws IllegalArgumentException,
			IndexOutOfBoundsException {
		return null;
	}

	/**
	 * @param word
	 *            a ScrabbleWord representation of the last word played
	 * @param lastWord
	 *            the word played on the last turn
	 * @param turnScore
	 *            the score earned by the last word played
	 * @return a new game state
	 */
	public GameState playWord(ScrabbleWord word) {
		this.totalScore[0] += word.getPoints();
		if (this.gameType == "2")
			this.player = "C";
		return new GameState(this.tilesInHand[0], this.getTotalScore(),
				word.getPoints(), this.boardChars, this.tileBag,
				word.toString());
	}

	/**
	 * Takes in a certain number of tiles, x, that should be removed from the
	 * users hand and placed back in the tileBag. The tileBag must then be
	 * shuffled and the last x tiles placed into the hand.
	 * 
	 * @param tilesToReturn
	 *            - tiles to be put back in the tileBag
	 * @param hand
	 *            - the hand from which the tiles are being swapped
	 */
	public void returnTiles(char[] tilesToReturn, int hand) {
		for (char c : tilesToReturn)
			this.tileBag.add(c);
		Collections.shuffle(this.tileBag);
		int num = 0;
		while (num < tilesToReturn.length) {
			this.tilesInHand[hand].set(
					this.tilesInHand[hand].indexOf(tilesToReturn[num]),
					this.tileBag.remove(this.tileBag.size()));
			num++;
		}
	}

	/**
	 * Should shuffle this game's tileBag into a new random order.
	 */
	public void shuffle() {
		Collections.shuffle(this.tileBag);
	}

	/**
	 * Returns a new ScrabbleDictionary created by reading the file specified in
	 * the fileName parameter.
	 * 
	 * @param fileName
	 *            - the name of the file in which the dictionary is located.
	 * @return a new ScrabbleDictionary
	 */
	public static ScrabbleDictionary createDictionary(String fileName) {
		return new ScrabbleDictionary(fileName);
	}

	/**
	 * @return the characters currently on the board
	 */
	public char[][] getBoardChars() {
		return this.boardChars;
	}

	/**
	 * Sets the specified board location to the specified char
	 * 
	 * @param c
	 *            the char to be set
	 * @param row
	 *            the row the char is to be put in
	 * @param col
	 *            the column the char is to be put in
	 */
	public void setBoardChar(char c, int row, int col) {
		this.boardChars[row][col] = c;
	}

	/**
	 * @return the board's current configuration of point squares
	 */
	public char[][] getBoardConfiguration() {
		return this.boardConfiguration;
	}

	/**
	 * @return the size of the board (this value must be odd)
	 */
	public int getBoardSize() {
		return this.boardSize;
	}

	/**
	 * @return the maximum number of tiles a user can have in hand at any one
	 *         time.
	 */
	public int getMaxHandSize() {
		return this.maxHandSize;
	}

	/**
	 * @return the style of game being played (C, H, or 2)
	 */
	public String getGameType() {
		return this.gameType;
	}

	/**
	 * @param string
	 *            the letter representing the type of game to be played
	 */
	public void setGameType(String string) {
		this.gameType = string;
	}

	/**
	 * Returns the value of the field called 'player'.
	 * 
	 * @return Returns the player.
	 */
	public String getPlayer() {
		return this.player;
	}

	/**
	 * Sets the field called 'player' to the given value.
	 * 
	 * @param player
	 *            The player to set.
	 */
	public void setPlayer(String player) {
		this.player = player;
	}

	/**
	 * @return score received on a single turn
	 */
	public int getScoreThisTurn() {
		return this.scoreThisTurn;
	}

	/**
	 * @return the tileBag containing all letters not yet played
	 */
	public ArrayList<Character> getTileBag() {
		return this.tileBag;
	}

	/**
	 * @param hand
	 *            which hand's tiles are wished to be known
	 * @return an array of characters representing the tiles currently in the
	 *         user's hand.
	 */
	public ArrayList<Character> getTilesInHand(int hand) {
		return this.tilesInHand[hand];
	}

	/**
	 * Sets the tiles in hand to the given set set of tile.
	 * 
	 * @param tiles
	 * @param hand
	 */
	public void setGivenTiles(ArrayList<Character> tiles, int hand) {
		this.tilesInHand[hand] = tiles;
	}

	/**
	 * @return the overall game score
	 */
	public int getTotalScore() {
		return this.totalScore[0] + this.totalScore[1];
	}

	/**
	 * @param player
	 *            the player's who's score wants to be known
	 * @return the score of the desired player
	 */
	public int getScore(int player) {
		return this.totalScore[player];
	}

	/**
	 * @return the current ScrabbleDictionary
	 */
	public ScrabbleDictionary getDictionary() {
		return this.dictionary;
	}

	/**
	 * @return the lastWordPlayed
	 */
	public String getLastWordPlayed() {
		return this.lastWordPlayed;
	}

	/**
	 * Resets the total score of each player
	 */
	public void clearTotalScore() {
		this.totalScore[0] = this.totalScore[1] = 0;
	}
}
