package scrabble;

/**
 * Scrabble Tile class.
 * 
 * @author timaeudg. Created May 8, 2012.
 */
public class ScrabbleTile {

	private byte points;
	private byte multiplier;
	private char letter;
	private byte row;
	private byte column;
	private boolean wasBlank;

	private void setPoints(char letter) {
		if (this.wasBlank) {
			this.letter = ' ';
		}
		switch (letter) {
		case 'Q':
		case 'Z':
			this.points = 10;
			break;
		case 'J':
		case 'X':
			this.points = 8;
			break;
		case 'K':
			this.points = 5;
			break;
		case 'F':
		case 'H':
		case 'V':
		case 'W':
		case 'Y':
			this.points = 4;
			break;
		case 'B':
		case 'C':
		case 'M':
		case 'P':
			this.points = 3;
			break;
		case 'D':
		case 'G':
			this.points = 2;
			break;
		case 'E':
		case 'A':
		case 'I':
		case 'O':
		case 'N':
		case 'R':
		case 'T':
		case 'L':
		case 'S':
		case 'U':
			this.points = 1;
			break;
		default:
			this.points = 0;
			this.letter = letter;
			break;

		}
	}

	/**
	 * Constructor.
	 * 
	 * @param letter
	 * @param x
	 * @param y
	 * @param wasBlank
	 */
	public ScrabbleTile(char letter, byte x, byte y, boolean wasBlank) {
		this.letter = letter;
		this.wasBlank = wasBlank;
		this.row = x;
		this.column = y;
		this.setPoints(letter);
		if (Scrabble.DEFAULT_BOARD_LAYOUT[x][y] == 'd') {
			this.multiplier = 2;
		} else if (Scrabble.DEFAULT_BOARD_LAYOUT[x][y] == 't') {
			this.multiplier = 3;
		} else {
			this.multiplier = 1;
		}
	}

	/**
	 * Returns the value of the field called 'multiplier'.
	 * 
	 * @return Returns the multiplier.
	 */
	public byte getMultiplier() {
		return this.multiplier;
	}

	/**
	 * Sets the field called 'multiplier' to the given value.
	 * 
	 * @param multiplier
	 *            The multiplier to set.
	 */
	public void setMultiplier(byte multiplier) {
		this.multiplier = multiplier;
	}

	/**
	 * Returns the value of the field called 'points'.
	 * 
	 * @return Returns the points.
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * Returns the value of the field called 'letter'.
	 * 
	 * @return Returns the letter.
	 */
	public char getLetter() {
		return this.letter;
	}

	/**
	 * Returns the value of the field called 'xPos'.
	 * 
	 * @return Returns the xPos.
	 */
	public byte getRow() {
		return this.row;
	}

	/**
	 * Returns the value of the field called 'yPos'.
	 * 
	 * @return Returns the yPos.
	 */
	public byte getColumn() {
		return this.column;
	}

	/**
	 * Gets the points.
	 * 
	 * @return points
	 */
	public byte getActualPoints() {
		return (byte) (this.points * this.multiplier);
	}

	@Override
	public int hashCode() {
		return this.letter;
	}

	@Override
	public String toString() {
		return "" + this.letter;
	}

	/**
	 * Checks whether the two tiles are equal or not.
	 *
	 * @param tile
	 * @return true or false
	 */
	public boolean equals(ScrabbleTile tile) {
		return (this.row == tile.getRow()) && (this.column == tile.getColumn());

	}
}
