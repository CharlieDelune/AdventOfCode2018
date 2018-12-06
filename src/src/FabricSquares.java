package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FabricSquares {
	//Declare Variables
	static int[][] overlapArray;
	static int numAdded, numReplaced;
	static ArrayList<Rectangle> nonOverlaps;

	public static void main(String[] args) {
		//Initialize variables
		overlapArray = new int[1000][1000];
		numReplaced = 0;
		nonOverlaps = new ArrayList<Rectangle>();
		
		//Read input data
		File file = new File("data/inputDay3.txt");
		try {
		    String line;
			BufferedReader br = new BufferedReader(new FileReader(file));
			line = br.readLine();
		    while ((line = br.readLine()) != null) {
		    	//For each line, convert line items to rectangle object
		    	Rectangle rect = new Rectangle(line);
		    	//Add rectangle into array
		    	AddToArray(rect);
		    }
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		//At the end, loop through the entire array and add up
		//All over the squares that overlap
		for(int j = 0; j < overlapArray.length; j++) {
			for(int i = 0; i < overlapArray[j].length; i++) {
				if(overlapArray[j][i] == -1) {
					numReplaced++;
				}
			}
		}
		
		//Copy nonOverlaps to avoid concurrent modification, check all in list
		ArrayList<Rectangle> nonOverlapsCopy = nonOverlaps;
		for(Object r : nonOverlapsCopy.toArray()) {
			CheckOverlapArray((Rectangle)r);
		}
		//Print the overlap
		System.out.println("Overlaps: " + numReplaced + ", Non-Overlapped Square: " + nonOverlaps.get(0).id);
	}
	
	//Add rectangle to array
	//If array contains nothing in slot, put ID in slot
	//if array already contains an ID, mark as -1 to denote overlap
	public static void AddToArray(Rectangle rect) {
		boolean overlapped = false;
		for(int j = rect.fromTop; j < (rect.fromTop + rect.height); j++) {
			for(int i = rect.fromLeft; i < (rect.fromLeft + rect.width); i++) {
				if(overlapArray[j][i] == 0) {
					overlapArray[j][i] = rect.id;
				}
				else {
					overlapArray[j][i] = -1;
					overlapped = true;
				}
			}
		}
		if(!overlapped) {
			nonOverlaps.add(rect);
		}
	}

	//Check rectangle... if it comes across a -1 at all, there's an overlap. Break!
	public static void CheckOverlapArray(Rectangle rect) {
		boolean overlapped = false;
		for(int j = rect.fromTop; j < (rect.fromTop + rect.height); j++) {
			for(int i = rect.fromLeft; i < (rect.fromLeft + rect.width); i++) {
				if(overlapArray[j][i] == -1) {
					overlapped = true;
					break;
				}
			}
		}
		if(overlapped) {
			nonOverlaps.remove(rect);
		}
	}
}

//Rectangle class to hold data
class Rectangle{
	int id,fromLeft, fromTop, width, height;
	public Rectangle(String inData) {
		String[] splitData = inData.split("@");
		id = Integer.parseInt(splitData[0].split("#")[1].trim());
		splitData = splitData[1].split(":");
		this.fromLeft = Integer.parseInt(splitData[0].split(",")[0].trim());
		this.fromTop = Integer.parseInt(splitData[0].split(",")[1].trim());
		this.width = Integer.parseInt(splitData[1].split("x")[0].trim());
		this.height =  Integer.parseInt(splitData[1].split("x")[1].trim());
	}
}