package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ReposeRecord {

	public static void main(String[] args) {
		ArrayList<String> lines = new ArrayList<String>();
	    HashMap<Integer,Guard> guards = new HashMap<Integer, Guard>();
	    
		//Read input data
		File file = new File("data/inputDay4.txt");
		try {
		    String line;
			BufferedReader br = new BufferedReader(new FileReader(file));
		    while ((line = br.readLine()) != null) {
		    	lines.add(line);
		    }
		    br.close();
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		//Sort input data by time (default sort does this perfectly)
	    Collections.sort(lines);
	    
	    //Initialize variables for currentId and start minute
	    int currentId = 0;
	    int startMin = 0;
	    for(String s : lines) {
	    	//If a guard is coming on shift, add the guard to the list of guards
	    	//But only if they're not already in the list
	    	if(s.contains("begins")) {
	    		currentId = Integer.parseInt(s.split("#")[1].split(" ")[0].trim());
	    		if(!guards.containsKey(currentId)) {
	    			guards.put(currentId, new Guard(currentId));
	    		}
	    	}
	    	//Otherwise, we're doing something with a guard's time....
	    	else {
	    		//We need the current minute in both cases...
	    		int min = Integer.parseInt(s.substring(15, 17));
	    		//Guard is falling asleep, store start minute
	    		if(s.contains("falls")){
	    			startMin = min;
	    		}
	    		//Guard is waking up. Add 1 to every minute guard was asleep
	    		else if(s.contains("wakes")){
	    			for(int i = startMin; i < min; i++) {
	    				guards.get(currentId).AddMinute(i);
	    			}
	    		}
	    	}
	    }
	    //Store best guard's ID and guard's best minute, then print
		int bestId = FindGuardWithMostMinutes(guards);
		int bestMinute = guards.get(bestId).GetBestMinute();
		System.out.println("Most Minutes: " + bestId + ", Best Minute: " + bestMinute);
		System.out.println("Your answer (ID * Best Minute) = " + (bestId * bestMinute));
		
		System.out.println("------- Now for Part 2 ------");
		
		//Initialize variables to hold current ID, current highest minute, and sleep number
		int currentMaxId = 0;
		int currentMaxNum = 0;
		int currentMaxMinute = 0;
		//Loop through all guards and store the ID, minute, and number, of the guard that
		//has the highest number of any guard/minute combination
		for(Guard g : guards.values()) {
			for(int i = 0; i < 60; i++) {
				if(g.minuteStatus.get(i) > currentMaxNum) {
					currentMaxNum = g.minuteStatus.get(i);
					currentMaxMinute = i;
					currentMaxId = g.id;
				}
			}
		}
		//Print the results
		System.out.println("Guard ID : " + currentMaxId + ", Highest Minute = " + currentMaxMinute + ", Highest Minute Number: " + currentMaxNum);
		System.out.println("Your answer (ID * Best Minute) = " + (currentMaxId * currentMaxMinute));
	}
	
	//Loop through all guards and return guard with most total sleep time
	public static int FindGuardWithMostMinutes(HashMap<Integer,Guard> guards) {
		int minutes = 0;
		int mostId = 0;
		for(Guard g : guards.values()) {
			if(g.totalMinsAsleep > minutes) {
				minutes = g.totalMinsAsleep;
				mostId = g.id;
			}
		}
		return mostId;
	}
}

//Guard object for data storage
class Guard{
	int id;
	int totalMinsAsleep;
	HashMap<Integer,Integer> minuteStatus;
	
	//Constructor also initializes hashmap of all possible minutes
	//initialized to 0
	public Guard(int inId) {
		this.id = inId;
		minuteStatus = new HashMap<Integer,Integer>();
		for(int i = 0; i < 60; i++) {
			minuteStatus.put(i, 0);
		}
	}
	
	//Adding a minute to the guard's hashmap also increases
	//the total minutes spent asleep
	public void AddMinute(int inMin) {
		int value = minuteStatus.get(inMin);
		value++;
		minuteStatus.remove(inMin);
		minuteStatus.put(inMin, value);
		totalMinsAsleep++;
	}
	
	//Loop through guard's minute hashmap and return the minute
	//That they were most frequently asleep for
	public int GetBestMinute() {
		int highestMin = 0;
		int highestValue = 0;
		for(int i = 0; i < 60; i++) {
			if(minuteStatus.get(i) > highestValue) {
				highestMin = i;
				highestValue = minuteStatus.get(i);
			}
		}
		return highestMin;
	}
}