package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class InstructionOrder {
	
	static HashMap<String, Instruction> instructionsUnsorted;
	static TreeMap<String, Instruction> instructions;
	static ArrayList<Instruction> instructionOrder;
	static ArrayList<Worker> workers;

	public static void main(String[] args) {
		instructionsUnsorted = new HashMap<String,Instruction>();
		instructions = new TreeMap<String,Instruction>();
		instructionOrder = new ArrayList<Instruction>(26);
		
		//Read in data and set up lists
		Initialize();
	    
	    //This loop will continue until the TreeMap is empty.
	    //If it comes upon an entry with an empty set of pre-reqs, it will remove that
	    //entry from the list, add it to the list of completed instructions, and then
	    //remove that entry from every other list in the TreeMap.
	    boolean continueLooping = true;
	    while(continueLooping) {
	    	if(instructions.size() == 0) {
	    		continueLooping = false;
	    		break;
	    	}
	    	else {
	    		for(int i = 0; i < 26; i++) {
	    			if(instructions.get((char)(i + 'A') + "") != null) {
		    			if(instructions.get((char)(i + 'A') + "").reliesOn.isEmpty()) {
		    				for(int j = 0; j < 26; j++) {
		    					if(instructions.get((char)(j + 'A') + "") != null) {
		    						instructions.get((char)(j + 'A') + "").reliesOn.remove((char)(i + 'A') + "");
		    					}
		    				}
		    				instructionOrder.add(instructions.get((char)(i + 'A') + ""));
		    				instructions.remove((char)(i + 'A') + "");
		    				break;
		    			}
	    			}
	    		}
	    	}
	    }
	    //Print the answer to Part 1.
	    for(Instruction in : instructionOrder) {
	    	System.out.print(in.letter);
	    }
	    System.out.println("");
	    
	    //Reset the puzzle
	   Initialize();
	   instructionOrder = new ArrayList<Instruction>();
	    
	    //Create the five workers
	    workers = new ArrayList<Worker>();
	    for(int i = 1; i < 6; i++) {
	    	workers.add(new Worker(i));
	    }
	    
		//For each worker
		//If it has a task already,
		//Increment time spent. Check to see if time spent is greater
		//than timetocomplete. If if is, add the task to the instructionorder
		//and then remove the instruction from the worker
		//If worker doesn't have a task,
		//Find next task
		//Assign task to worker, remove task from instructions
	    continueLooping = true;
	    int second = 0;
	    while(continueLooping) {
	    	if(instructionOrder.size() == 26) {
	    		continueLooping = false;
	    		break;
	    	}
	    	else {
	    		for(Worker w : workers) {
	    			if(w.currentlyWorking != null) {
	    				if(w.timeSpent > w.currentlyWorking.timeToComplete) {
	    					instructionOrder.add(w.currentlyWorking);
		    				for(int j = 0; j < 26; j++) {
		    					if(instructions.get((char)(j + 'A') + "") != null) {
		    						instructions.get((char)(j + 'A') + "").reliesOn.remove(w.currentlyWorking.letter);
		    					}
		    				}
		    				w.currentlyWorking = null;
		    				w.timeSpent = 0;
	    				}
	    				else {
	    					w.timeSpent++;
	    				}
	    			}
	    		}
	    		for(Worker w : workers) {
	    			if(w.currentlyWorking == null) {
	    				for(int i = 0; i < 26; i++) {
	    	    			if(instructions.get((char)(i + 'A') + "") != null) {
	    	    				Instruction tempInst = instructions.get((char)(i + 'A') + "");
	    		    			if(tempInst.reliesOn.isEmpty()) {
	    		    				w.currentlyWorking = tempInst;
	    		    				instructions.remove(tempInst.letter);
	    		    				break;
	    		    			}
	    	    			}
	    	    		}
	    			}
	    		}
	    		second++;
	    	}
	    }
	    //Print the answer to Part 2
	    System.out.println(second);
	}
	
	public static void Initialize() {
		//Read input data and store in hashmap. Later, we'll sort this in a TreeMap
		File file = new File("data/inputDay7.txt");
		//Initialize hashmap with all letters
		for(int i = 0; i < 26; i++) {
			Instruction tempInst = new Instruction((char)(i+'A') + "",60+i);
			instructionsUnsorted.put((char)(i+'A') + "", tempInst);
		}
		
		//Read input data and add to unsorted instructions list
		//Each instruction contains a list of the instructions that must be 
		//completed before it.
		try {
		    String line;
			BufferedReader br = new BufferedReader(new FileReader(file));
		    while ((line = br.readLine()) != null) {
		    	instructionsUnsorted.get(line.substring(36, 37)).reliesOn.add(line.substring(5, 6));
		    }
		    br.close();
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
	    //Put all of the instructions into a TreeMap which automagically sorts by key
	    instructions.putAll(instructionsUnsorted);
	}
}

class Instruction{
	String letter;
	ArrayList<String> reliesOn;
	int timeToComplete;
	
	public Instruction(String inLetter, int inTime) {
		this.letter = inLetter;
		this.timeToComplete = inTime;
		reliesOn = new ArrayList<String>();
	}
}

class Worker{
	int number;
	Instruction currentlyWorking;
	int timeSpent;
	
	public Worker(int inNum) {
		this.number = inNum;
		this.currentlyWorking = null;
	}
}