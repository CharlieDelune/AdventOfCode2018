import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FrequencyShifter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int freq = 0;
		int loops = 0;
		ArrayList<Integer> intList = new ArrayList();
		intList.add(0);
		File file = new File("C:\\Users\\jeffk\\Desktop\\input.txt");
		try { 
		    String line;
		    boolean notYetFound = true;
		    while(notYetFound) {
		    	System.out.println("Looped " + loops++ + " times");
				BufferedReader br = new BufferedReader(new FileReader(file));
			    while ((line = br.readLine()) != null) {
			       // process the line.
			    	int lineInt = Integer.parseInt(line);
			    	freq = freq + lineInt;
			    	if(intList.contains(freq)) {
			    		System.out.println("First reaches " + freq + " twice.");
			    		notYetFound = false;
			    		break;
			    	}
			    	else {
			    		intList.add(freq);
			    	}
			    }
		    }
		}
		catch(Exception e) {
			System.out.println("An error has occurred: " + e.toString());
		}
	}

}
