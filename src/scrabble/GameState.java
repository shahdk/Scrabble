package scrabble;

import java.util.ArrayList;

/**
 * Contains the results of the last play (board, hand, tile bag, score info).
 * You may add fields (and corresponding parameters to the constructor) and add
 * new methods. You should not remove any fields or modify the existing public
 * methods.
 * 
 * @author stephaap, modified by CWA
 * 
 */

// NOTE: This class is currently only set up for a single-player game. You will
// need to modify it to also
// be able to also represent a human-computer game, including indicating whose
// turn it is.

public class GameState {
	private ArrayList<Character> tilesInHand; // The contents of the player's
												// hand after the play.
	private int totalScore;
	private int scoreThisPlay;
	private static int computerScore = 0;
	private int playerScore = 0;
	private char[][] boardChars;
	private ArrayList<Character> tileBag;
	private String lastWordPlayed;

	/**
	 * Constructor.
	 * 
	 * @param tilesInHand
	 *            - the current tiles in the player's had
	 * @param scoreThisPlay
	 *            - the score earned on the this move
	 * @param totalScore
	 *            - the score earned overall
	 * @param boardChars
	 *            - current tiles on the board
	 * @param tileBag
	 *            - tiles to be drawn from
	 * @param lastWordPlayed
	 *            contains entire word, which may be a "superstring" of the the
	 *            characters actually played on this Turn. If one of the
	 *            characters in the word is played by a blank, it should be in
	 *            lowercase. e.g. If character 2 in XENIA is a blank that
	 *            represents N, the value of this parameter should be XEnIA
	 */
	public GameState(ArrayList<Character> tilesInHand, int totalScore,
			int scoreThisPlay, char[][] boardChars,
			ArrayList<Character> tileBag, String lastWordPlayed) {
		super();
		this.tilesInHand = tilesInHand;
		this.totalScore = totalScore;
		this.scoreThisPlay = scoreThisPlay;
		this.boardChars = boardChars;
		this.tileBag = tileBag;
		this.lastWordPlayed = lastWordPlayed;
	}

	/**
	 * @return current tiles on the board
	 */
	public char[][] getBoardChars() {
		return this.boardChars;
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @param points
	 */
	public void setPlayerScore(int points) {
		this.playerScore = points;
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @return
	 */
	public int getPlayerScore() {
		return this.playerScore;
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @param points
	 */
	public void setComputerScore(int points) {
		GameState.computerScore = points;
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @return
	 */
	public int getComputerScore() {
		return GameState.computerScore;
	}

	/**
	 * @return the score earned on the this move
	 */
	public int getScoreThisPlay() {
		return this.scoreThisPlay;
	}

	/**
	 * Sets the current score play to the given points.
	 * 
	 * @param points
	 */
	public void setScoreThisPlay(int points) {
		this.scoreThisPlay = points;
	}

	/**
	 * @return the current tiles in hand
	 */
	public ArrayList<Character> getTilesInHand() {
		return this.tilesInHand;
	}

	/**
	 * Sets to the current tile in hand.
	 * 
	 * @param tilesInHand
	 */
	public void setTilesInHand(ArrayList<Character> tilesInHand) {
		this.tilesInHand = tilesInHand;
	}

	/**
	 * Adds the given points to the total points.
	 * 
	 * @param addPoints
	 */
	public void setTotalScore(int addPoints) {
		this.totalScore += addPoints;
	}

	/**
	 * @return the score earned overall
	 */
	public int getTotalScore() {
		return this.totalScore;
	}

	/**
	 * @return tiles to be drawn from
	 */
	public ArrayList<Character> getTileBag() {
		return this.tileBag;
	}

	/**
	 * Sets the tile bag to the given bag.
	 * 
	 * @param tilebag
	 */
	public void setTileBag(ArrayList<Character> tilebag) {
		this.tileBag = tilebag;
	}

	/**
	 * @return the lastWordPlayed
	 */
	public String getLastWordPlayed() {
		return this.lastWordPlayed;
	}

	/**
	 * Sets the last word to the given word.
	 * 
	 * @param string
	 */
	public void setLastWord(String string) {
		this.lastWordPlayed = string;

	}

	/**
	 * Sets the total score to 0.
	 * 
	 * @param i
	 * @param b
	 */
	public void setTotalScore(int i, boolean b) {
		if (b) {
			this.totalScore = 0;
		}

	}
}
