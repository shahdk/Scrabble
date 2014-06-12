package scrabble;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Maintains dictionary of legal words to play in a form designed for quick
 * access.
 * 
 * @author stephaap, CWA, and you.
 * 
 *         You must decide how to represent the wordlist, and how to search for
 *         possible words to play.
 * 
 */

// the tree implementation is based on the algorithm provided by Cristian
// Velazquez of University of Illinois-Chicago in 2005.
// We got the implementaion from this website:
// http://logos.cs.uic.edu/340/assignments/Solutions/Wordpopup/ptrzyna/program2/

public class ScrabbleDictionary {

	private Node root; // Trie root
	@SuppressWarnings("unused")
	private Node current; // Current Position Inside the Trie

	/**
	 * Data Structure storing dictionary.
	 */

	/**
	 * Creates a Scrabble dictionary by reading the dictionary with the given
	 * filename.
	 * 
	 * @param filePathName
	 */
	public ScrabbleDictionary(String filePathName) {
		this.root = new Node();
		BufferedReader reader;
		String input;
		try {
			reader = new BufferedReader(new FileReader(filePathName));
			while ((input = reader.readLine()) != null) {
				// Add word to trie
				addWord(input);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		this.current = this.root;
	}

	/**
	 * Description: Node This class implement a node structure which is used as
	 * the structural components of the trie
	 */
	public class Node {
		private String letter; // Letter hold in the node
		/**
		 * flag telling whether its a word or not.
		 */
		boolean isAWord; // Indicates whether this node represents starting
							// position of a word
		private Node next; // Leftmost child pointer
		private Node sibling; // Right sibling pointer

		/**
		 * Creates a new node
		 * 
		 * @param None
		 */
		Node() {
			this.letter = null;
			this.isAWord = false;
			this.next = this.sibling = null;
		}

		/**
		 * Creates a new node with given letter and boolean value
		 * 
		 * @param s
		 */
		Node(String s) {
			this.letter = s;
			this.isAWord = false;
			this.next = this.sibling = null;
		}
	}

	/**
	 * Check if the given letter is in the next level of the trie from the given
	 * node pointer.
	 * 
	 * @param crtPosition
	 * @param letter
	 * @return boolean if it finds it, else returns null
	 */
	public Node findLetter(Node crtPosition, String letter) {
		Node pointer = crtPosition; // Initialize pointer for position reference
									// in trie

		// Word is not in tree if still looking for word letters and there
		// aren't any more
		if (pointer.next == null) {
			return null;
		} else { // There is a child from the current node

			// Check if next node contains the current letter we're looking for
			if (pointer.next.letter.equals(letter)) {
				pointer = pointer.next; // go to next child
			} else { // Check if a sibling contains the same letter
				pointer = pointer.next; // go to start of node sibling list

				// Loop until a node with the same letter is found or sibling
				// list ends
				while (pointer.sibling.sibling != null
						&& !pointer.sibling.letter.equals(letter)) {
					pointer = pointer.sibling;
				}

				// If no sibling with same letter is found, word isn't in trie
				if (pointer.sibling.sibling == null) {
					return null; // String is not in tree
				} else { // A sibling has the letter we're looking for, continue
					pointer = pointer.sibling; // One sibling has same letter
				}
			}
		}
		return pointer;
	}

	/**
	 * Find a given word in the given trie
	 * 
	 * @param word
	 * 
	 * @return A pointer to the node with the starting position (letter) of the
	 *         word found If word is not found, it returns a null pointer
	 */
	public String findStr(String word) {
		Node pointer = this.root; // Initialize pointer for position reference
									// in trie
		String input = word.toLowerCase(); // Word to look up

		// Start a while loop searching the chars of the word in trie starting
		// backwards
		while (input.length() >= 1) {

			// Get the last letter in the word
			String firstChar = input.substring(0, 1);
			input = input.substring(1, input.length()); // Update string minus
														// last letter

			if (!Character.isLetter(firstChar.charAt(0))) { // If char is not a
															// letter skip it
				continue;
			}

			// Word is not in tree if still looking for word letters and there
			// aren't any more
			if (pointer.next == null) {
				return null;
			} else { // There is a child from the current node

				// Check if next node contains the current letter we're looking
				// for
				if (pointer.next.letter.equals(firstChar)) {
					pointer = pointer.next; // go to next child
				} else { // Check if a sibling contains the same letter
					pointer = pointer.next; // go to start of node sibling list

					// Loop until a node with the same letter is found or
					// sibling list ends
					while (pointer.sibling.sibling != null
							&& !pointer.sibling.letter.equals(firstChar)) {
						pointer = pointer.sibling;
					}

					// If no sibling with same letter is found, word isn't in
					// trie
					if (pointer.sibling.sibling == null) {
						return null; // String is not in tree
					} else { // A sibling has the letter we're looking for,
								// continue
						pointer = pointer.sibling; // One sibling has same
													// letter
					}
				}
			}
		}
		// All letters from word match, check if current pointer reference is
		// start of word
		if (pointer.isAWord) {
			this.current = pointer;
			return this.printWord(pointer);
		} else {
			return null;
		}
	}

	/**
	 * Find a given word in the given trie
	 * 
	 * @param word
	 * 
	 * @return A pointer to the node with the starting position (letter) of the
	 *         word found If word is not found, it returns a null pointer
	 */
	public String findSemiWords(String word) {
		Node pointer = this.root; // Initialize pointer for position reference
									// in trie
		String input = word.toLowerCase(); // Word to look up

		// Start a while loop searching the chars of the word in trie starting
		// backwards
		while (input.length() >= 1) {

			// Get the last letter in the word
			String firstChar = input.substring(0, 1);
			input = input.substring(1, input.length()); // Update string minus
														// last letter

			if (!Character.isLetter(firstChar.charAt(0))) { // If char is not a
															// letter skip it
				continue;
			}

			// Word is not in tree if still looking for word letters and there
			// aren't any more
			if (pointer.next == null) {
				return null;
			} else { // There is a child from the current node

				// Check if next node contains the current letter we're looking
				// for
				if (pointer.next.letter.equals(firstChar)) {
					pointer = pointer.next; // go to next child
				} else { // Check if a sibling contains the same letter
					pointer = pointer.next; // go to start of node sibling list

					// Loop until a node with the same letter is found or
					// sibling list ends
					while (pointer.sibling.sibling != null
							&& !pointer.sibling.letter.equals(firstChar)) {
						pointer = pointer.sibling;
					}

					// If no sibling with same letter is found, word isn't in
					// trie
					if (pointer.sibling.sibling == null) {
						return null; // String is not in tree
					} else { // A sibling has the letter we're looking for,
								// continue
						pointer = pointer.sibling; // One sibling has same
													// letter
					}
				}
			}
		}
		this.current = pointer;
		return this.printWord(pointer);
	}

	/**
	 * Add a word to the trie
	 * 
	 * @param input
	 * @return None
	 */
	public Node addWord(String input) {
		Node pointer = this.root;

		input = input.toLowerCase();

		while (input.length() >= 1) {

			// Get the first letter of the word
			String firstChar = input.substring(0, 1);

			// Update string minus first letter
			input = input.substring(1, input.length());

			// If char is not a letter skip it
			if (!Character.isLetter(firstChar.charAt(0))) {
				continue;
			}

			if (pointer.next == null) {
				// No more child nodes, We need to add a new child
				Node newChild = new Node(firstChar);
				// Set child to be new node
				pointer.next = newChild;

				// Create the last sibling in list pointing to parent
				Node lastSibling = new Node();
				lastSibling.sibling = null;
				lastSibling.next = pointer;
				newChild.sibling = lastSibling;

				pointer = newChild; // Update pointer
			}

			else { // There is still children nodes
				if (pointer.next.letter.equals(firstChar)) {
					// node has same letter, go to next child
					pointer = pointer.next;
				} else { // Child has not the same letter we're looking for
					pointer = pointer.next;

					// Check if there's a sibling with same letter
					while (pointer.sibling.sibling != null
							&& !pointer.sibling.letter.equals(firstChar)) {

						pointer = pointer.sibling;
					}

					// Check if we got to the last sibling, node matching letter
					// was not found
					if (pointer.sibling.sibling == null) {

						// Add new node at end of sibling list
						Node newSibling = new Node(firstChar);
						newSibling.sibling = pointer.sibling;
						pointer.sibling = newSibling;
						pointer = newSibling;
					} else {
						// There is a node matching the letter we're looking for
						// update pointer to this position
						pointer = pointer.sibling;
					}
				}
			}
		}
		if (pointer == this.root) { // All characters in word were not a letter
			return null; // No word will be added
		}
		pointer.isAWord = true;
		return pointer;
	}

	/**
	 * Print a word that that starts at the given pointer reference
	 * 
	 * @param pointer
	 * @return string
	 */
	public String printWord(Node pointer) {
		String word = ""; // Declaration of string to hold word to print

		do {
			word = pointer.letter + word; // Store next character of word

			do { // Go to the end of the sibling list
				pointer = pointer.sibling;
			} while (pointer.sibling != null);

			pointer = pointer.next; // Go to the parent of the node

		} while (pointer.sibling != null); // Stop when we get to the root

		return word;
	}
}
