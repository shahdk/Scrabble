package scrabble;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Reads in the lines from the current dictionary file and re-writes it in a
 * more logical fashion while removing useless words.
 * 
 * @author eubankct. Created May 11, 2012.
 */
public class DictionaryModifier {

	/**
	 * Modifies the dictionary to our specifications.
	 *
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		HashSet<String> dictionary = new HashSet<String>();
		BufferedReader reader;
		String input;
		try {
			reader = new BufferedReader(new FileReader(
					"scrabbleDictionaries/dictionary01.sd"));
			Here: while ((input = reader.readLine()) != null) {
				System.out.print(input);
				if (input.length() > 15)
					continue;
				for (int i = 0; i < input.length(); i++)
					if (input.charAt(i) > 'z' || input.charAt(i) < 'a') {
						continue Here;
					}
				// dictionary.add(input);
//				String[] inverse = new String[input.length()];
//				int j = 0;
//				for (int i = input.length() - 1; i >= 0; i--) {
//					inverse[j] = "";
//					for (int k = input.substring(0, i).length(); k >= 0; k--) {
//						inverse[j] += input.substring(k, k + 1);
//					}
//					j++;
//				}
//				for (int i = 0; i < inverse.length; i++) {
//					dictionary.add(inverse[i]);
//				}
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		ArrayList<String> words = new ArrayList<String>();
		words.addAll(dictionary);
		Collections.sort(words);
		PrintWriter writer = new PrintWriter(new File(
				"scrabbleDictionaries/dictionary0.sd"));
		int index = 0;
		while (index < words.size()) {
			writer.println(words.get(index));
			index++;
		}
		writer.close();
	}
}
