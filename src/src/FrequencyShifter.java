package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FrequencyShifter {

	public static void main(String[] args) {
		//Instantiate variables
		int freq = 0;
		ArrayList<Integer> intList = new ArrayList<Integer>();
		//Add 0 because the frequency begins on 0
		intList.add(0);
		File file = new File("data/inputDay1.txt");
		try { 
		    String line;
		    boolean notYetFound = true;
		    System.out.println("Searching for duplicate. This may take a few moments...");
		    //Loop through the list repeatedly until a duplicate is found...
		    while(notYetFound) {
				BufferedReader br = new BufferedReader(new FileReader(file));
			    while ((line = br.readLine()) != null) {
			    	//Parse integer of line, increment/decrement frequency as needed
			    	int lineInt = Integer.parseInt(line);
			    	freq = freq + lineInt;
			    	//If list of frequencies already contains current frequency, this is the answer
			    	if(intList.contains(freq)) {
			    		System.out.println("First reaches " + freq + " twice.");
			    		notYetFound = false;
			    		break;
			    	}
			    	//If list of frequencies does not contain current frequency,
			    	//add current frequency to list of frequencies.
			    	else {
			    		intList.add(freq);
			    	}
			    }
			    br.close();
		    }
		}
		catch(Exception e) {
			System.out.println("An error has occurred: " + e.toString());
		}
	}

}
