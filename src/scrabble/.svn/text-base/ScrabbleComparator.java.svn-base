package scrabble;

import java.util.Comparator;

/**
 * A comparator that organizes the tiles according to their order in the board,
 * left to right if horizontal, and top to bottom if vertical
 * 
 * @author timaeudg. Created May 13, 2012.
 */
public class ScrabbleComparator implements Comparator<Object> {
	private char orientationTemp;

	/**
	 * Checks for the orientation.
	 * 
	 * @param orientation
	 */
	public ScrabbleComparator(char orientation) {
		this.orientationTemp = orientation;
	}

	@Override
	public int compare(Object arg0, Object arg1) {
		ScrabbleTile first = (ScrabbleTile) arg0;
		ScrabbleTile second = (ScrabbleTile) arg1;
		if (this.orientationTemp == 'V') {
			return first.getRow() - second.getRow();
		} else {
			return first.getColumn() - second.getColumn();
		}

	}

}