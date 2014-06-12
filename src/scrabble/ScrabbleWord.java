package scrabble;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Attempts to create a scrabble word, and throws an IndexOutOfBounds exception
 * if there are any invalid things about the word.
 * 
 * @author timaeudg. Created May 8, 2012.
 */
public class ScrabbleWord {

	private String word;
	private ArrayList<String> subWords;
	private ArrayList<ScrabbleTile> tiles;
	private int points;
	private int crossPoints;
	private byte multiplier;
	private char orientation;

	/**
	 * Constructor for the class.
	 * 
	 * @param letters
	 */
	public ScrabbleWord(ArrayList<ScrabbleTile> letters) {
		this.tiles = new ArrayList<ScrabbleTile>();
		this.subWords = new ArrayList<String>();
		this.multiplier = 1;
		// temporary orientation holder
		this.orientation = 'X';
		ArrayList<ScrabbleTile> newList = new ArrayList<ScrabbleTile>();
		// add only actual letters to the list of letters
		for (ScrabbleTile tile : letters) {
			if (Character.isLetter(tile.getLetter())) {
				newList.add(tile);
			}
		}
		if (newList.size() < 2) {
			throw new IndexOutOfBoundsException();
		}
		// Check the orientation of the word and make sure the tiles are placed
		// correctly
		this.setAndCheckOrientation(newList);
		// Check if the placement of the tiles is valid
		boolean valid = this.isValid(this);
		if (valid) {
			// calculate and set the points
			this.points = this.setPoints();
			if (newList.size() == 7) {
				this.addPoints(50);
			}
		} else {
			// if the word isn't valid, throw an exception to be caught that
			// signals it
			// was not a valid word
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * get the orientation of the word
	 * 
	 * @return orientation
	 */
	public char getOrientation() {
		return this.orientation;
	}

	/**
	 * 
	 * Checks all the tiles that are passed to the word to see if they in a
	 * single line, and sets the orientation of the word to horizontal or
	 * vertical depending on this.
	 * 
	 * @param tiles
	 */
	private void setAndCheckOrientation(ArrayList<ScrabbleTile> tiles) {
		ScrabbleTile first = tiles.get(0);
		if (tiles.size() == 1) {
			ScrabbleTile[][] board = ScrabbleFrame.getScrabbleBoard()
					.getTileList();
			int max = ScrabbleFrame.getScrabbleBoard().getBoardSize() - 1;
			int startX = first.getRow();
			int startY = first.getColumn();
			// Special cases for the corners and edges of the board for 1 tile
			if (startX == 0) {
				if (startY == 0) {
					if (board[startX][startY + 1] != null) {
						this.orientation = 'H';
					} else if (board[startX + 1][startY] != null) {
						this.orientation = 'V';
					}
				} else if (startY == max) {
					if (board[startX + 1][startY] != null) {
						this.orientation = 'V';
					} else if (board[startX][startY - 1] != null) {
						this.orientation = 'H';
					}
				} else {
					if (board[startX][startY - 1] != null
							|| board[startX][startY + 1] != null) {
						this.orientation = 'H';
					} else if (board[startX + 1][startY] != null) {
						this.orientation = 'V';
					}
				}
			} else if (startY == 0) {
				if (startX == max) {
					if (board[startX - 1][startY] != null) {
						this.orientation = 'V';
					} else if (board[startX][startY + 1] != null) {
						this.orientation = 'H';
					}
				} else {
					if (board[startX + 1][startY] != null
							|| board[startX - 1][startY] != null) {
						this.orientation = 'V';
					} else if (board[startX][startY + 1] != null) {
						this.orientation = 'H';
					}
				}
			} else if (startX == max) {
				if (startY == max) {
					if (board[startX + 1][startY] != null) {
						this.orientation = 'V';
					} else if (board[startX][startY - 1] != null) {
						this.orientation = 'H';
					}
				} else {
					if (board[startX][startY + 1] != null
							|| board[startX][startY + 1] != null) {
						this.orientation = 'H';
					} else if (board[startX - 1][startY] != null) {
						this.orientation = 'V';
					}
				}
			} else if (startY == max) {
				if (board[startX][startY - 1] != null) {
					this.orientation = 'H';
				} else if (board[startX + 1][startY] != null
						|| board[startX - 1][startY] != null) {
					this.orientation = 'V';
				}
			} else {
				if (board[startX + 1][startY] != null
						|| board[startX - 1][startY] != null) {
					this.orientation = 'V';
				} else if (board[startX][startY + 1] != null
						|| board[startX][startY - 1] != null) {
					this.orientation = 'H';
				}
			}
		}
		// go through and compare the tiles, they have to all be in either the
		// same
		// row or column, if not, they are wrong, also set the orientation based
		// off this
		for (int k = 0; k < tiles.size(); k++) {
			ScrabbleTile tile = tiles.get(k);
			if (!tile.equals(first)) {
				if (this.orientation == 'X'
						&& tile.getColumn() == first.getColumn()) {
					this.orientation = 'V';
				} else if (this.orientation == 'X'
						&& tile.getRow() == first.getRow()) {
					this.orientation = 'H';
				} else if (this.orientation == 'V'
						&& tile.getColumn() != first.getColumn()) {
					throw new IndexOutOfBoundsException();
				} else if (this.orientation == 'H'
						&& tile.getRow() != first.getRow()) {
					throw new IndexOutOfBoundsException();
				}
			}

			this.tiles.add(tile);
		}
	}

	/**
	 * 
	 * Sets the points of the word by summing the points of the tiles, and
	 * multiplying the score by the whole word's multiplier.
	 * 
	 * @return
	 */

	private int setPoints() {
		this.points = 0;
		for (ScrabbleTile tile : this.tiles) {
			byte x = tile.getRow();
			byte y = tile.getColumn();
			char multi = Scrabble.DEFAULT_BOARD_LAYOUT[x][y];
			switch (multi) {
			case 'T':
				this.multiplier *= 3;
				break;
			case 'D':
				this.multiplier *= 2;
				break;
			case '*':
				this.multiplier *= 2;
				break;
			}
			this.points += tile.getActualPoints();
		}
		return this.points *= this.multiplier;
	}

	/**
	 * @return the number of points scored by this word
	 */
	public int getPoints() {
		return this.points + this.crossPoints;
	}

	/**
	 * Checks to see if a word is valid by sorting the tiles according to their
	 * orientation then iterating through the tiles, if there is a gap between
	 * tiles, there must be tiles that will be part of the word (i.e. if nothing
	 * is there, and there are still tiles that were placed to be looked at,
	 * then the word is invalid) these tiles are then added to the word after
	 * all the tiles that were placed are looked at. The word is then parsed
	 * into a string, and then checked to see if it is in the dictionary.
	 * 
	 * @param word
	 * @return whether the word is valid or not.
	 */
	public boolean isValid(ScrabbleWord word) {
		ScrabbleComparator compare = new ScrabbleComparator(
				word.getOrientation());
		Collections.sort(word.tiles, compare);
		boolean connected = false;
		boolean atFree = false;
		int initialLength = word.tiles.size();
		ArrayList<ScrabbleTile> toAdd = new ArrayList<ScrabbleTile>();
		ScrabbleTile[][] board = ScrabbleFrame.getScrabbleBoard().getTileList();
		byte validCross;
		if (word.orientation == 'V') {
			ScrabbleTile first = word.tiles.get(0);
			validCross = this.checkCrosswords(first, 'V');
			if (validCross == 3) {
				return false;
			}
			ScrabbleTile aboveBelow = first;
			// checks to see if the tile is at the free space
			if (first.getRow() == 7 && first.getColumn() == 7) {
				atFree = true;
			}
			// checks to see if the tiles added are to the right of some other
			// tiles,
			// like a suffix
			if (first.getRow() != 0) {
				while ((aboveBelow.getRow() > 0)
						&& (board[aboveBelow.getRow() - 1][aboveBelow
								.getColumn()] != null)) {
					connected = true;
					aboveBelow = board[aboveBelow.getRow() - 1][aboveBelow
							.getColumn()];
				}
				// go until you hit the original tile
				while ((aboveBelow.getRow() != first.getRow())
						|| (aboveBelow.getColumn() != first.getColumn())) {
					toAdd.add(aboveBelow);
					aboveBelow = board[aboveBelow.getRow() + 1][aboveBelow
							.getColumn()];
				}
			}
			// check all the tiles that were added and check them for valid
			// crosses
			for (byte k = 1; k < word.tiles.size(); k++) {
				byte startx = first.getRow();
				byte starty = first.getColumn();
				ScrabbleTile current = word.tiles.get(k);
				if (current.getRow() == 7 && current.getColumn() == 7) {
					atFree = true;
				}
				byte previousCross = validCross;
				validCross = this.checkCrosswords(current, 'V');
				if (previousCross == 1 && validCross == 2) {
					validCross = 1;
				}
				if (validCross == 3) {
					return false;
				}
				byte difference = (byte) (current.getRow() - startx);
				// if there is a gap between tiles, all the spaces in between
				// must be tiles,
				// otherwise, it is invalid
				if (difference > 1) {
					for (byte j = 1; j < difference; j++) {
						ScrabbleTile space = board[startx + j][starty];
						if (space == null) {
							return false;
						} else {
							toAdd.add(space);

						}

					}
					connected = true;
				}
				first = current;
			}
			aboveBelow = word.tiles.get(initialLength - 1);
			// check and see if there are tiles after the added tiles, ie. check
			// if the
			// added tiles are a prefix
			while ((aboveBelow.getRow() + 1 <= ScrabbleFrame.getScrabbleBoard()
					.getBoardSize() - 1)) {
				if (board[aboveBelow.getRow() + 1][aboveBelow.getColumn()] == null) {
					break;
				}
				connected = true;
				toAdd.add(board[aboveBelow.getRow() + 1][aboveBelow.getColumn()]);
				aboveBelow = board[aboveBelow.getRow() + 1][aboveBelow
						.getColumn()];
			}
			// if the added tiles are not connected to anything, at the free
			// space, or
			// dont cross with some other word, it is invalid
			if (!atFree && validCross == 2 && !connected) {
				return false;
			}
			// add any "extra" tiles that may have been before, after, or in
			// between the
			// original tiles
			word.tiles.addAll(toAdd);
		}
		// all the same as above, but for the horizontal word case.
		else {
			ScrabbleTile first = word.tiles.get(0);
			validCross = this.checkCrosswords(first, 'H');
			if (validCross == 3) {
				return false;
			}
			ScrabbleTile aboveBelow = first;
			if (first.getRow() == 7 && first.getColumn() == 7) {
				atFree = true;
			}
			if (first.getColumn() != 0) {
				while ((aboveBelow.getColumn() > 0)
						&& (board[aboveBelow.getRow()][aboveBelow.getColumn() - 1] != null)) {
					connected = true;
					aboveBelow = board[aboveBelow.getRow()][aboveBelow
							.getColumn() - 1];
				}
				while ((aboveBelow.getRow() != first.getRow())
						|| (aboveBelow.getColumn() != first.getColumn())) {
					toAdd.add(aboveBelow);
					aboveBelow = board[aboveBelow.getRow()][aboveBelow
							.getColumn() + 1];
				}
			}
			for (byte k = 1; k < word.tiles.size(); k++) {
				byte startx = first.getRow();
				byte starty = first.getColumn();
				ScrabbleTile current = word.tiles.get(k);
				if (current.getRow() == 7 && current.getColumn() == 7) {
					atFree = true;
				}
				byte previousCross = validCross;
				validCross = this.checkCrosswords(current, 'H');
				if (previousCross == 1 && validCross == 2) {
					validCross = 1;
				}
				if (validCross == 3) {
					return false;
				}
				byte difference = (byte) (current.getColumn() - starty);
				if (difference > 1) {
					for (byte j = 1; j < difference; j++) {
						ScrabbleTile space = board[startx][starty + j];
						if (space == null) {
							return false;
						} else {
							toAdd.add(space);

						}
					}
					connected = true;
				}
				first = current;
			}

			aboveBelow = word.tiles.get(initialLength - 1);
			while ((aboveBelow.getColumn() + 1 <= ScrabbleFrame
					.getScrabbleBoard().getBoardSize() - 1)) {
				if (board[aboveBelow.getRow()][aboveBelow.getColumn() + 1] == null) {
					break;
				}
				connected = true;
				toAdd.add(board[aboveBelow.getRow()][aboveBelow.getColumn() + 1]);
				aboveBelow = board[aboveBelow.getRow()][aboveBelow.getColumn() + 1];
			}
			if (!atFree && validCross == 2 && !connected) {
				return false;
			}
			word.tiles.addAll(toAdd);
		}

		if (!connected && !atFree && validCross == 2) {
			return false;
		}
		// resort the tiles to get the word in the right order.
		Collections.sort(word.tiles, compare);
		// converts the tiles into a string for the word
		word.word = parseString(word.tiles);
		word.word = word.word.toLowerCase();
		ScrabbleDictionary dictionary = ScrabbleFrame.getDictionary();
		// checks to see if the word is in the dicitonary, if not, it is not a
		// valid
		// word
		String returned = dictionary.findStr(word.word);
		if (returned == null) {
			return false;
		} else {
			return true;
		}
	}

	private static String parseString(ArrayList<ScrabbleTile> tiles) {
		String word = "";
		// go through each tile, and append the character to the end of a string
		for (ScrabbleTile tile : tiles) {
			word += tile.getLetter();
		}
		return word;
	}

	private byte checkCrosswords(ScrabbleTile startTile, char orientation) {
		byte startX = startTile.getRow();
		byte startY = startTile.getColumn();
		ArrayList<ScrabbleTile> cross = new ArrayList<ScrabbleTile>();
		String wordCheck = "";
		ScrabbleTile[][] board = ScrabbleFrame.getScrabbleBoard().getTileList();

		boolean checkUp = false;
		boolean checkLeft = false;
		boolean checkRight = false;
		boolean checkDown = false;
		if (startX != 0) {
			checkUp = board[startTile.getRow() - 1][startTile.getColumn()] != null;
		}
		if (startX != ScrabbleFrame.getScrabbleBoard().getBoardSize() - 1) {
			checkDown = board[startTile.getRow() + 1][startTile.getColumn()] != null;
		}
		if (startY != 0) {
			checkLeft = board[startTile.getRow()][startTile.getColumn() - 1] != null;
		}
		if (startY != ScrabbleFrame.getScrabbleBoard().getBoardSize() - 1) {
			checkRight = board[startTile.getRow()][startTile.getColumn() + 1] != null;
		}

		if (orientation == 'H') {
			ScrabbleTile next = startTile;
			if (!checkUp && !checkDown) {
				return 2;
			}

			// check if the first tile is at the top of the board
			if (startX != 0) {
				// if not starting at the top, go up until there are no more
				// tiles
				while ((next.getRow() != 0)
						&& (board[next.getRow() - 1][next.getColumn()] != null)) {
					if (Character.isLetter(board[next.getRow() - 1][next
							.getColumn()].getLetter())) {
						next = board[next.getRow() - 1][next.getColumn()];
					} else {
						break;
					}
				}
				// starting at the topmost tile, go down until back the original
				// tile, if
				// no tiles above, just stay at the starting tile, either way,
				// add the
				// letters to the string that will be checked for validity, and
				// move down
				while ((next.getRow() != startX)
						|| (next.getColumn() != startY)) {
					wordCheck += next.getLetter();
					cross.add(next);
					next = board[next.getRow() + 1][next.getColumn()];
				}
				wordCheck += next.getLetter();
				cross.add(next);
				// if not at the bottom of the board move down
				if (next.getRow() != ScrabbleFrame.getScrabbleBoard()
						.getBoardSize()) {
					next = board[next.getRow() + 1][next.getColumn()];
				}

			}
			// so long as there are still tiles below, and the edge of the board
			// hasn't been
			// hit, go down, and add the character to the string, and add the
			// tile to a
			// list of the cross letters
			if (next != null
					&& next.getRow() != ScrabbleFrame.getScrabbleBoard()
							.getBoardSize() - 1) {
				while (next != null
						&& (next.getRow() < ScrabbleFrame.getScrabbleBoard()
								.getBoardSize() - 1)) {

					// System.out.println("board: " + board + "   next:" + next
					// + "\n" + board[next.getRow()][next.getColumn()]);
					if (Character.isLetter(board[next.getRow()][next
							.getColumn()].getLetter())) {
						wordCheck += next.getLetter();
						cross.add(next);
						next = board[next.getRow() + 1][next.getColumn()];
					} else {
						break;
					}
					// wordCheck += next.getLetter();
					// cross.add(next);

				}
			}

		}
		// all the same, but for horizontal words that cross a vertical word
		else {
			if (!checkLeft && !checkRight) {
				return 2;
			}
			ScrabbleTile next = startTile;
			if (startY != 0) {
				while ((next.getColumn() != 0)
						&& (board[next.getRow()][next.getColumn() - 1] != null)) {
					if (Character.isLetter(board[next.getRow()][next
							.getColumn() - 1].getLetter())) {
						next = board[next.getRow()][next.getColumn() - 1];
					} else {
						break;
					}
				}
				while ((next.getRow() != startX)
						|| (next.getColumn() != startY)) {
					wordCheck += next.getLetter();
					cross.add(next);
					next = board[next.getRow()][next.getColumn() + 1];
				}
				wordCheck += next.getLetter();
				cross.add(next);
				if (next.getColumn() != ScrabbleFrame.getScrabbleBoard()
						.getBoardSize()) {
					next = board[next.getRow()][next.getColumn() + 1];
				}
			}
			if (next != null
					&& next.getColumn() != ScrabbleFrame.getScrabbleBoard()
							.getBoardSize() - 1) {
				while (next != null
						&& (next.getRow() < ScrabbleFrame.getScrabbleBoard()
								.getBoardSize() - 1)) {
					if (Character.isLetter(board[next.getRow()][next
							.getColumn()].getLetter())) {
						wordCheck += next.getLetter();
						cross.add((next));
						next = board[next.getRow()][next.getColumn() + 1];
					} else {
						break;
					}
				}
				// wordCheck += next.getLetter();
				// cross.add(next);
			}
		}

		// check and see if the string that was found is a valid word
		if (ScrabbleFrame.getDictionary().findStr(wordCheck) == null) {
			// otherwise bad word, so invalid
			return 3;
		} else {
			Color used = new Color(160, 32, 240);
			// so long as the word is longer than length 1, then check for
			// points

			// if the letter has already been used, as given by the label color,
			// then the
			// points should not include any double or triple letters, if they
			// haven't been
			// used, ie. they were the placed tile, the include any bonus.
			// in any case, add the word that was valid to the list of subwords
			// that
			// were made in the play.
			for (ScrabbleTile tile : cross) {
				if (wordCheck.length() > 1) {
					ScrabbleFrame.getScrabbleBoard();
					if (ScrabbleBoard.getBoardLabel()[tile.getRow()][tile
							.getColumn()].getBackground().equals(used)) {
						this.crossPoints += tile.getPoints();
					} else {
						this.crossPoints += tile.getActualPoints();
					}
					this.subWords.add(wordCheck);
				}
			}
			return 1;

		}
	}

	/**
	 * adds points to the word, only used in the case of a bingo
	 * 
	 * @param points
	 */
	public void addPoints(int points) {
		this.points += points;
	}

	/**
	 * returns the word that this scrabble word represents
	 */
	@Override
	public String toString() {
		return this.word;
	}

	/**
	 * returns an array of strings that represent the sub words that were made
	 * indirectly by playing this word.
	 * 
	 * @return array list of sub words formed.
	 */
	public ArrayList<String> getSubWords() {
		return this.subWords;
	}

	/**
	 * Return the tiles that were played by the computer.
	 * 
	 * @return array list for scrabble tiles
	 */
	public ArrayList<ScrabbleTile> getWordTiles() {
		return this.tiles;
	}

}
