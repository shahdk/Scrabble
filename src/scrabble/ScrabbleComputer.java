package scrabble;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Determines the best move for the computer
 * 
 * @author nuanests. Created May 9, 2012.
 */
public class ScrabbleComputer {

	private static Character[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * Plays for the computer.
	 * 
	 * @param isPlayer
	 * @param player
	 * @return gamestate with updated scores and tiles.
	 */
	public static GameState play(String isPlayer, String player, char difficulty) {
		ScrabbleFrame.sc.setScoreThisPlay(0);
		ScrabbleTile[][] list = ScrabbleFrame.getScrabbleBoard().getTileList();
		int hand = 0;
		if (isPlayer.equals("C") || isPlayer.equals("H")) {
			hand = 0;
		} else {
			if (player.equals("H")) {
				hand = 0;
			} else {
				hand = 1;
			}
		}
		ScrabbleDictionary dictionary = ScrabbleFrame.getDictionary();
		ScrabbleDictionary backwardsDictionary = ScrabbleFrame
				.getBackwardsDictionary();
		ArrayList<ScrabbleWord> words = new ArrayList<ScrabbleWord>();
		ArrayList<Character> theHand = ScrabbleFrame.getScrabble()
				.getTilesInHand(hand);
		if (list[7][7] == null) {
			playEmptyLR((byte) 7, (byte) 7, theHand, words, list, dictionary,
					backwardsDictionary);
			playEmptyUD((byte) 7, (byte) 7, theHand, words, list, dictionary,
					backwardsDictionary);
		} else {
			for (byte i = 0; i < 15; i++) {
				for (byte j = 0; j < 15; j++) {
					if (list[i][j] != null
							&& ((j < 14 && list[i][j + 1] == null) || j == 14)) {
						ArrayList<ScrabbleTile> currentWord = new ArrayList<ScrabbleTile>();
						ArrayList<ArrayList<ScrabbleTile>> leftRight = tryLeft(
								list, i, j, backwardsDictionary, currentWord,
								theHand, "", list[i][j]);
						words.addAll(tryRight(list, i, j, dictionary,
								leftRight, theHand));
					}
					if (list[i][j] != null
							&& ((i < 14 && list[i + 1][j] == null) || i == 14)) {
						ArrayList<ScrabbleTile> currentWord = new ArrayList<ScrabbleTile>();
						ArrayList<ArrayList<ScrabbleTile>> upDown = tryUp(list,
								i, j, backwardsDictionary, currentWord,
								theHand, "", list[i][j]);
						words.addAll(tryDown(list, i, j, dictionary, upDown,
								theHand));
					}
					if (difficulty == 'C') {
						if (list[i][j] == null
								&& ((j < 14 && list[i][j + 1] != null) || (j > 0 && list[i][j - 1] != null))) {
							playEmptyUD(i, j, theHand, words, list, dictionary,
									backwardsDictionary);
						}
						if (list[i][j] == null
								&& ((i < 14 && list[i + 1][j] != null) || (i > 0 && list[i - 1][j] != null))) {
							playEmptyLR(i, j, theHand, words, list, dictionary,
									backwardsDictionary);
						}
					}
				}
			}
		}

		if (words.size() == 0) {
			// if scrabblebag still has tiles
			// exchange tiles
			if (ScrabbleFrame.getScrabble().getTileBag().size() > 0) {
				if (hand == 0) {
					ScrabbleFrame.exchange();
				} else {
					// temp variable to store current hand tiles.
					ArrayList<Character> swap = new ArrayList<Character>();
					for (Character ch : ScrabbleFrame.getScrabble()
							.getTilesInHand(1)) {
						swap.add(ch);
					}
					// get 7 tiles from tile bag and add it to the user hand
					// panel.
					if (ScrabbleFrame.getScrabble().getTileBag().size() > 0) {
						for (int i = 0; i < ScrabbleFrame.getScrabble()
								.getTilesInHand(1).size(); i++) {
							ScrabbleFrame
									.getScrabble()
									.getTilesInHand(1)
									.set(i,
											ScrabbleFrame
													.getScrabble()
													.getTileBag()
													.remove(ScrabbleFrame
															.getScrabble()
															.getTileBag()
															.size() - 1));
						}
						// put the original tiles in hand back into the bag and
						// suffle.
						for (Character c : swap) {
							ScrabbleFrame.getScrabble().getTileBag().add(c);
							ScrabbleFrame.getScrabble().shuffle();
						}
					}
				}
			}
		}
		if (words.size() == 0) {
			return null;
		}
		ScrabbleWord max = words.get(0);
		for (int i = 1; i < words.size(); i++) {
			if (words.get(i).getPoints() > max.getPoints()) {
				max = words.get(i);
			} else if (words.get(i).getPoints() == max.getPoints()) {
				if (Math.random() > 0.5)
					max = words.get(i);
			}
		}
		for (int i = 0; i < max.getWordTiles().size(); i++) {
			if (max.getWordTiles().get(i).getLetter() == 0) {
				System.out.println("meh: " + max.getWordTiles().remove(i--));
			}
		}
		for (int i = 0; i < max.getWordTiles().size(); i++) {
			for (int j = 0; j < ScrabbleFrame.getScrabble()
					.getTilesInHand(hand).size(); j++) {
				if (list[max.getWordTiles().get(i).getRow()][max.getWordTiles()
						.get(i).getColumn()] == null
						&& (ScrabbleFrame.getScrabble().getTilesInHand(hand)
								.get(j)
								.equals(max.getWordTiles().get(i).getLetter()) || ScrabbleFrame
								.getScrabble().getTilesInHand(hand).get(j)
								.equals('b')
								&& Character.isLowerCase(max.getWordTiles()
										.get(i).getLetter()))) {
					ScrabbleFrame.getScrabble().getTilesInHand(hand)
							.set(j, '\0');
					break;
				}
			}
		}
		if (isPlayer.equals("C") || isPlayer.equals("H")) {
			ScrabbleFrame.getScrabbleBoard().setTilesInHand(
					ScrabbleFrame.getScrabble().getTilesInHand(0));
		} else {
			if (player.equals("H")) {
				ScrabbleFrame.getScrabbleBoard().setTilesInHand(
						ScrabbleFrame.getScrabble().getTilesInHand(0));
			}
			if (ScrabbleFrame.getScrabble().getTileBag().size() > 0) {
				for (int i = 0; i < ScrabbleFrame.getScrabble()
						.getTilesInHand(1).size(); i++) {
					if (!Character.isLetter((ScrabbleFrame.getScrabble()
							.getTilesInHand(1).get(i)))) {
						Character c = ScrabbleFrame
								.getScrabble()
								.getTileBag()
								.remove(ScrabbleFrame.getScrabble()
										.getTileBag().size() - 1);
						ScrabbleFrame.getScrabble().getTilesInHand(1).set(i, c);
					}
				}
			} else {
				for (int i = 0; i < ScrabbleFrame.getScrabble()
						.getTilesInHand(1).size(); i++) {
					ScrabbleFrame
							.getScrabble()
							.getTilesInHand(1)
							.set(i,
									ScrabbleFrame.getScrabble()
											.getTilesInHand(1).get(i));
				}
			}
			ScrabbleFrame.sc.setTilesInHand(ScrabbleFrame.getScrabble()
					.getTilesInHand(1));
		}
		if (isPlayer.equals("2")) {
			if (player.equals("C")) {
				ScrabbleFrame.sc.setComputerScore(ScrabbleFrame.sc
						.getComputerScore() + max.getPoints());
			} else {
				ScrabbleFrame.sc.setPlayerScore(ScrabbleFrame.sc
						.getPlayerScore() + max.getPoints());
			}
		}
		ScrabbleFrame.sc.setScoreThisPlay(max.getPoints());
		ScrabbleFrame.sc.setTotalScore(max.getPoints());

		ScrabbleFrame.sc.setLastWord(max.toString());
		ScrabbleFrame.sc.setTileBag(ScrabbleFrame.getScrabble().getTileBag());
		ScrabbleFrame.getScrabbleBoard().setScrabbleBoard(max);
		return ScrabbleFrame.sc;
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @param j
	 * @param i
	 * 
	 * @param theHand
	 * @param backwardsDictionary
	 * @param dictionary
	 * @param list
	 * @param words
	 */
	private static void playEmptyLR(byte i, byte j,
			ArrayList<Character> theHand, ArrayList<ScrabbleWord> words,
			ScrabbleTile[][] list, ScrabbleDictionary dictionary,
			ScrabbleDictionary backwardsDictionary) {
		for (int k = 0; k < theHand.size(); k++) {
			Character beginPos = theHand.remove(k);
			if (beginPos == 'b') {
				for (int m = 0; m < alphabet.length; m++) {
					ScrabbleTile tile = new ScrabbleTile(alphabet[m], i, j,
							false);
					ArrayList<ScrabbleTile> currentWord = new ArrayList<ScrabbleTile>();
					ArrayList<ArrayList<ScrabbleTile>> leftRight = tryLeft(
							list, i, j, backwardsDictionary, currentWord,
							theHand, "", tile);
					words.addAll(tryRight(list, i, j, dictionary, leftRight,
							theHand));
				}
			} else {
				ScrabbleTile tile = new ScrabbleTile(beginPos, i, j, false);
				ArrayList<ScrabbleTile> currentWord = new ArrayList<ScrabbleTile>();
				ArrayList<ArrayList<ScrabbleTile>> leftRight = tryLeft(list, i,
						j, backwardsDictionary, currentWord, theHand, "", tile);
				words.addAll(tryRight(list, i, j, dictionary, leftRight,
						theHand));
			}
			theHand.add(k, beginPos);
		}
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @param j
	 * @param i
	 * 
	 * @param theHand
	 * @param backwardsDictionary
	 * @param dictionary
	 * @param list
	 * @param words
	 */
	private static void playEmptyUD(byte i, byte j,
			ArrayList<Character> theHand, ArrayList<ScrabbleWord> words,
			ScrabbleTile[][] list, ScrabbleDictionary dictionary,
			ScrabbleDictionary backwardsDictionary) {
		for (int k = 0; k < theHand.size(); k++) {
			Character beginPos = theHand.remove(k);
			if (beginPos == 'b') {
				for (int m = 0; m < alphabet.length; m++) {
					ScrabbleTile tile = new ScrabbleTile(alphabet[m], i, j,
							false);
					ArrayList<ScrabbleTile> currentWord = new ArrayList<ScrabbleTile>();
					ArrayList<ArrayList<ScrabbleTile>> upDown = tryUp(list, i,
							j, backwardsDictionary, currentWord, theHand, "",
							tile);
					words.addAll(tryDown(list, i, j, dictionary, upDown,
							theHand));
				}
			} else {
				ScrabbleTile tile = new ScrabbleTile(beginPos, i, j, false);
				ArrayList<ScrabbleTile> currentWord = new ArrayList<ScrabbleTile>();
				ArrayList<ArrayList<ScrabbleTile>> upDown = tryUp(list, i, j,
						backwardsDictionary, currentWord, theHand, "", tile);
				words.addAll(tryDown(list, i, j, dictionary, upDown, theHand));
			}
			theHand.add(k, beginPos);
		}
	}

	/**
	 * Based on the words in the upDown array, sees what words can be made by
	 * adding letters below
	 * 
	 * @param list
	 * @param i
	 * @param j
	 * @param dictionary
	 * @param upDown
	 * @return
	 */
	private static ArrayList<ScrabbleWord> tryDown(ScrabbleTile[][] list,
			byte i, byte j, ScrabbleDictionary dictionary,
			ArrayList<ArrayList<ScrabbleTile>> upDown,
			ArrayList<Character> compLetters) {
		ArrayList<ScrabbleWord> words = new ArrayList<ScrabbleWord>();
		for (ArrayList<ScrabbleTile> word : upDown) {
			ArrayList<ScrabbleTile> correctOrder = new ArrayList<ScrabbleTile>();
			String letters = "";
			ArrayList<Character> compHand = new ArrayList<Character>();
			for (Character character : compLetters) {
				compHand.add(character);
			}
			for (int k = word.size() - 1; k >= 0; k--) {
				ScrabbleTile tile = word.get(k);
				if (list[tile.getRow()][tile.getColumn()] == null) {
					if (tile.getLetter() >= 'a' && tile.getLetter() <= 'z') {
						compHand.remove((Character) 'b');
					} else {
						compHand.remove((Character) (tile.getLetter()));
					}
				}
				letters = Character.toString(tile.getLetter()) + letters;
				correctOrder.add(tile);
			}
			words.addAll(helpDown(list, i, j, dictionary, compHand,
					correctOrder, letters));
		}
		return words;
	}

	private static HashSet<ScrabbleWord> helpDown(ScrabbleTile[][] list,
			byte i, byte j, ScrabbleDictionary dictionary,
			ArrayList<Character> compLetters, ArrayList<ScrabbleTile> word,
			String letters) {
		HashSet<ScrabbleWord> words = new HashSet<ScrabbleWord>();
		i++;
		for (byte k = 0; k < compLetters.size(); k++) {
			if (i <= 14
					&& Character.isLetter(compLetters.get(k))
					&& list[i][j] == null
					&& dictionary.findSemiWords(letters + compLetters.get(k)) != null) {
				// ScrabbleTile newTile = new
				// ScrabbleTile(compLetters.remove(k),
				// i, j, false);
				// word.add(newTile);
				// words.addAll(helpDown(list, i, j, dictionary, compLetters,
				// word, letters + Character.toString(newTile.getLetter())));
				// word.remove(newTile);
				// compLetters.add(k, newTile.getLetter());
				if (compLetters.get(k) == 'b') {
					for (int m = 0; m < alphabet.length; m++) {
						ScrabbleTile newTile = new ScrabbleTile(alphabet[m], i,
								j, false);
						compLetters.remove(k);
						word.add(newTile);
						words.addAll(helpDown(list, i, j, dictionary,
								compLetters, word, letters + alphabet[m]));
						word.remove(newTile);
						compLetters.add(k, 'b');
					}
				} else {
					ScrabbleTile newTile = new ScrabbleTile(
							compLetters.remove(k), i, j, false);
					word.add(newTile);
					words.addAll(helpDown(list, i, j, dictionary, compLetters,
							word, letters + newTile.getLetter()));
					word.remove(newTile);
					compLetters.add(k, newTile.getLetter());
				}
			}
		}
		ScrabbleTile[][] board = ScrabbleFrame.getScrabbleBoard().getTileList();
		ArrayList<ScrabbleTile> placedTiles = new ArrayList<ScrabbleTile>();
		try {
			for (ScrabbleTile tile : word) {
				if (board[tile.getRow()][tile.getColumn()] == null) {
					board[tile.getRow()][tile.getColumn()] = tile;
					placedTiles.add(tile);
				}
			}
			ScrabbleWord addWord = new ScrabbleWord(placedTiles);
			words.add(addWord);

		} catch (IndexOutOfBoundsException e) {
			// do nothing
		} finally {
			for (ScrabbleTile tile : placedTiles) {
				board[tile.getRow()][tile.getColumn()] = null;
			}
		}
		return words;
	}

	/**
	 * Goes up to see if any letters can be added on to the current word
	 * 
	 * @param list
	 * @param i
	 * @param j
	 * @param backwardsDictionary
	 * @return
	 */
	private static ArrayList<ArrayList<ScrabbleTile>> tryUp(
			ScrabbleTile[][] list, byte i, byte j,
			ScrabbleDictionary backwardsDictionary,
			ArrayList<ScrabbleTile> currentWord,
			ArrayList<Character> compLetters, String word, ScrabbleTile tile) {
		ArrayList<ArrayList<ScrabbleTile>> words = new ArrayList<ArrayList<ScrabbleTile>>();
		word += Character.toString(tile.getLetter());
		currentWord.add(tile);
		while (i > 0 && list[i - 1][j] != null) {
			i--;
			word += Character.toString(list[i][j].getLetter());
			currentWord.add(0, list[i][j]);
		}
		i--;
		for (byte k = 0; k < compLetters.size(); k++) {
			if (i >= 0
					&& Character.isLetter(compLetters.get(k))
					&& list[i][j] == null
					&& backwardsDictionary.findSemiWords(word
							+ compLetters.get(k)) != null) {
				if (compLetters.get(k) == 'b') {
					for (int m = 0; m < alphabet.length; m++) {
						ScrabbleTile newTile = new ScrabbleTile(alphabet[m], i,
								j, false);
						compLetters.remove(k);
						words.addAll(tryUp(list, i, j, backwardsDictionary,
								currentWord, compLetters, word, newTile));
						currentWord.remove(newTile);
						compLetters.add(k, 'b');
					}
				} else {
					ScrabbleTile newTile = new ScrabbleTile(
							compLetters.remove(k), i, j, false);
					words.addAll(tryUp(list, i, j, backwardsDictionary,
							currentWord, compLetters, word, newTile));
					currentWord.remove(newTile);
					compLetters.add(k, newTile.getLetter());
				}
			}
		}
		ArrayList<ScrabbleTile> addWord = new ArrayList<ScrabbleTile>();
		for (ScrabbleTile addTile : currentWord) {
			addWord.add(addTile);
		}
		words.add(addWord);
		return words;
	}

	/**
	 * Based on the words in the leftRight array, sees what words can be made by
	 * adding letters to the right
	 * 
	 * @param list
	 * @param i
	 * @param j
	 * @param dictionary
	 * @param leftRight
	 * @return
	 */
	private static ArrayList<ScrabbleWord> tryRight(ScrabbleTile[][] list,
			byte i, byte j, ScrabbleDictionary dictionary,
			ArrayList<ArrayList<ScrabbleTile>> leftRight,
			ArrayList<Character> compLetters) {
		ArrayList<ScrabbleWord> words = new ArrayList<ScrabbleWord>();
		for (ArrayList<ScrabbleTile> word : leftRight) {
			ArrayList<ScrabbleTile> correctOrder = new ArrayList<ScrabbleTile>();
			String letters = "";
			ArrayList<Character> compHand = new ArrayList<Character>();
			for (Character character : compLetters) {
				compHand.add(character);
			}
			for (int k = word.size() - 1; k >= 0; k--) {
				ScrabbleTile tile = word.get(k);
				if (list[tile.getRow()][tile.getColumn()] == null) {
					if (tile.getLetter() >= 'a' && tile.getLetter() <= 'z') {
						compHand.remove((Character) 'b');
					} else {
						compHand.remove((Character) (tile.getLetter()));
					}
				}
				letters = Character.toString(tile.getLetter()) + letters;
				correctOrder.add(tile);
			}
			words.addAll(helpRight(list, i, j, dictionary, compHand,
					correctOrder, letters));
		}
		return words;
	}

	private static HashSet<ScrabbleWord> helpRight(ScrabbleTile[][] list,
			byte i, byte j, ScrabbleDictionary dictionary,
			ArrayList<Character> compLetters, ArrayList<ScrabbleTile> word,
			String letters) {
		HashSet<ScrabbleWord> words = new HashSet<ScrabbleWord>();
		j++;
		for (byte k = 0; k < compLetters.size(); k++) {
			if (j <= 14
					&& Character.isLetter(compLetters.get(k))
					&& list[i][j] == null
					&& dictionary.findSemiWords(letters + compLetters.get(k)) != null) {
				// ScrabbleTile newTile = new
				// ScrabbleTile(compLetters.remove(k),
				// i, j, false);
				// word.add(newTile);
				// words.addAll(helpRight(list, i, j, dictionary, compLetters,
				// word, letters + Character.toString(newTile.getLetter())));
				// word.remove(newTile);
				// compLetters.add(k, newTile.getLetter());
				if (compLetters.get(k) == 'b') {
					for (int m = 0; m < alphabet.length; m++) {
						ScrabbleTile newTile = new ScrabbleTile(alphabet[m], i,
								j, false);
						compLetters.remove(k);
						word.add(newTile);
						words.addAll(helpRight(list, i, j, dictionary,
								compLetters, word, letters + alphabet[m]));
						word.remove(newTile);
						compLetters.add(k, 'b');
					}
				} else {
					ScrabbleTile newTile = new ScrabbleTile(
							compLetters.remove(k), i, j, false);
					word.add(newTile);
					words.addAll(helpRight(list, i, j, dictionary, compLetters,
							word, letters + newTile.getLetter()));
					word.remove(newTile);
					compLetters.add(k, newTile.getLetter());
				}
			}
		}
		ScrabbleTile[][] board = ScrabbleFrame.getScrabbleBoard().getTileList();
		ArrayList<ScrabbleTile> placedTiles = new ArrayList<ScrabbleTile>();
		try {
			for (ScrabbleTile tile : word) {
				if (board[tile.getRow()][tile.getColumn()] == null) {
					board[tile.getRow()][tile.getColumn()] = tile;
					placedTiles.add(tile);
				}
			}
			ScrabbleWord addWord = new ScrabbleWord(placedTiles);
			words.add(addWord);

		} catch (IndexOutOfBoundsException e) {
			// do nothing
		} finally {
			for (ScrabbleTile tile : placedTiles) {
				board[tile.getRow()][tile.getColumn()] = null;
			}
		}
		return words;
	}

	/**
	 * Goes to the left to see if any letters can be added on to the current
	 * word
	 * 
	 * @param list
	 * @param i
	 * @param j
	 * @param backwardsDictionary
	 * @return
	 */
	private static ArrayList<ArrayList<ScrabbleTile>> tryLeft(
			ScrabbleTile[][] list, byte i, byte j,
			ScrabbleDictionary backwardsDictionary,
			ArrayList<ScrabbleTile> currentWord,
			ArrayList<Character> compLetters, String word, ScrabbleTile tile) {
		ArrayList<ArrayList<ScrabbleTile>> words = new ArrayList<ArrayList<ScrabbleTile>>();
		word += Character.toString(tile.getLetter());
		currentWord.add(tile);
		while (j > 0 && list[i][j - 1] != null) {
			j--;
			word += Character.toString(list[i][j].getLetter());
			currentWord.add(0, list[i][j]);
		}
		j--;
		for (byte k = 0; k < compLetters.size(); k++) {
			if (j >= 0
					&& Character.isLetter(compLetters.get(k))
					&& list[i][j] == null
					&& backwardsDictionary.findSemiWords(word
							+ compLetters.get(k)) != null) {
				if (compLetters.get(k) == 'b') {
					for (int m = 0; m < alphabet.length; m++) {
						ScrabbleTile newTile = new ScrabbleTile(alphabet[m], i,
								j, false);
						compLetters.remove(k);
						words.addAll(tryLeft(list, i, j, backwardsDictionary,
								currentWord, compLetters, word, newTile));
						words.addAll(tryUp(list, i, j, backwardsDictionary,
								currentWord, compLetters, word, newTile));
						currentWord.remove(newTile);
						compLetters.add(k, 'b');
					}
				} else {
					ScrabbleTile newTile = new ScrabbleTile(
							compLetters.remove(k), i, j, false);
					words.addAll(tryLeft(list, i, j, backwardsDictionary,
							currentWord, compLetters, word, newTile));
					currentWord.remove(newTile);
					compLetters.add(k, newTile.getLetter());
				}
			}
		}
		ArrayList<ScrabbleTile> addWord = new ArrayList<ScrabbleTile>();
		for (ScrabbleTile addTile : currentWord) {
			addWord.add(addTile);
		}
		words.add(addWord);
		return words;
	}
}