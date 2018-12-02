package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CommonCharacterFinder {

	public static void main(String[] args) {
		//Get the file
		File file = new File("Day2/data/input.txt");
		//ArrayList of strings to store input data
		ArrayList<String> stringList = new ArrayList<String>();
		try {
			//Read each line and add to the ArrayList
		    String line;
			BufferedReader br = new BufferedReader(new FileReader(file));
		    while ((line = br.readLine()) != null) {
		    	stringList.add(line);
		    }
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		//Loop through arraylist
		for(int i = 0; i < stringList.size();i++) {
			//Store char array of current string in arraylist
			char[] currentString = stringList.get(i).toCharArray();
			//Loop through remainder of arraylist
			for(int j = i; j < stringList.size(); j++) {
				//Store compare string, instantiate variables to store 
				//num of differences and index of differences
				char[] compareString = stringList.get(j).toCharArray();
				int differences = 0;
				int diffIndex = 0;
				// Loop through each character in both strings
				for(int k = 0; k < currentString.length; k++) {
					//If characters don't match, increase num of differences and store
					//index of difference
					if(currentString[k] != compareString[k]) {
						differences++;
						diffIndex = k;
					}
				}
				//We only care about the case where there's a single difference.
				if(differences == 1) {
					//Use string builder to build result string by leaving out the character
					//in the position of the index of the difference.
					StringBuilder sb = new StringBuilder();
					for(int c=0; c < currentString.length; c++) {
						if(c != diffIndex) {
							sb.append(currentString[c]);
						}
					}
					System.out.println(sb);
				}
			}
		}
	}
}
