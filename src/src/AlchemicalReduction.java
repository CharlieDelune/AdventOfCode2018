package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class AlchemicalReduction {
	
	public static void main(String[] args) {
		//Declare variables to be used for input storage
		File file = new File("data/inputDay5.txt");
		char[] charArray = null;
		StringBuilder sb = new StringBuilder();
		//This block reads the file input and stores each character
		//in a character array, then puts the array into a string builder
		try {
		    String line;
			BufferedReader br = new BufferedReader(new FileReader(file));
			line = br.readLine();
			charArray = line.toCharArray();
			sb.append(charArray);
			br.close();
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Part 1 answer: " + getReactionLength(sb));
		//Initialize variables for holding minimum count and associated letter
		String minLetter = "";
		int minCount = 9999;
		//Loop through alphabet
		for(int i = 0; i < 26; i++) {
			//Temporarily store string and replace all occurrences of current letter of
			//alphabet with blank space.
			String tempString = sb.toString();
			tempString = tempString.replace((char)(i+'a') + "", "");
			tempString = tempString.replace((char)(i+'A') + "", "");
			//Store new string in StringBuilder to re-use code from part 1
			StringBuilder sbTemp = new StringBuilder(tempString);
			//Run alchemical reduction on new StringBuilder
			int tempCount = getReactionLength(sbTemp);
			//Collect minimums if applicable
			if(tempCount < minCount) {
				minCount = tempCount;
				minLetter = (char)(i+'a') + "";
			}
		}
		System.out.println("Part 2 Answer: Lowest Count " + minCount + " achieved by removing unit " + minLetter);
	}

	public static int getReactionLength(StringBuilder sb) {
		//looping boolean
		boolean continueRemoval = true;
		//This block will continuously loop, removing pairs of characters as it goes.
		//If the inner loop ever reaches the end of the string, the outer loop stops. 
		while(continueRemoval) {
			for(int i = 0; i < sb.length()-1; i++) {
				if ((sb.charAt(i) ^ sb.charAt(i + 1)) == 32) {
	                sb.delete(i, i + 2);
	                continueRemoval = true;
	                break;
	            }
				if(i == sb.length()-2) {
					continueRemoval = false;
				}
			}
		}
		//Print the answer to part 1
		return sb.length();	
	}

}
