package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ChecksumFinder {

	public static void main(String[] args) {
		//Find input file, instantiate variables to hold
		//nums of twice and thrice repeated characters
		File file = new File("data/inputDay2.txt");
		int twos = 0;
		int threes = 0;
		try {
			//Read in and store each line of the file
		    String line;
			BufferedReader br = new BufferedReader(new FileReader(file));
		    while ((line = br.readLine()) != null) {
		    	//If current line has any characters that repeat twice or thrice,
		    	//increment the appropriate corresponding variable
		    	if(countOccurrences(line,2)) {
		    		twos++;
		    	}
		    	if(countOccurrences(line,3)) {
		    		threes++;
		    	}
		    }
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Twos: " + twos + "; Threes: " + threes + "; Checksum: " + (twos * threes));
	}
	
	static boolean countOccurrences(String str, int counter) 
    {
		boolean matches = false;
        // Create an array of size 256 i.e. ASCII_SIZE 
        int count[] = new int[256]; 
  
        int len = str.length(); 
  
        // Initialize count array index 
        for (int i = 0; i < len; i++) {
            count[str.charAt(i)]++;
        }
        
        //Loop through count array and find if count matches input target count
        for (int i = 0; i < len; i++) {
        	if(counter == count[str.charAt(i)]) {
        		matches = true;
        	}        
        }
        return matches;
    } 
}