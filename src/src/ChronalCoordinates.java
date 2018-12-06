package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ChronalCoordinates {
	
	static ArrayList<Coordinate> coordinates;
	static int maxX;
	static int maxY;
	static Integer[][] grid;
	
	public static void main(String[] args) {
		File file = new File("data/inputDay6.txt");
		coordinates = new ArrayList<Coordinate>();
		maxX = 0;
		maxY = 0;
		try {
			//Read in and store each line of the file as a Coordinate object
		    String line;
			BufferedReader br = new BufferedReader(new FileReader(file));
			int id = 0;
		    while ((line = br.readLine()) != null) {
		    	String[] tempSplit = line.split(",");
		    	//Create new Coordinate with x and y values and add to coordinates list
		    	Coordinate tempCoord = new Coordinate(id++,Integer.parseInt(tempSplit[0].trim()), Integer.parseInt(tempSplit[1].trim()));
		    	coordinates.add(tempCoord);
		    	//Store maximum x and maximum y for grid size
		    	if(tempCoord.x > maxX) {
		    		maxX = tempCoord.x;
		    	}
		    	if(tempCoord.y > maxY) {
		    		maxY = tempCoord.y;
		    	}
		    	
		    }
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		//Create a grid to store the data in
		grid = new Integer[maxY+1][maxX+1];
		//Place all points on the grid
		for(Coordinate c : coordinates) {
			grid[c.y][c.x] = c.id;
		}
		//For each point on the grid, find the closest point and then replace
		//the point's data with the ID of said closest point
		for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
            	int currentBest = 9999;
            	int currentBestId = -1;
            	for(Coordinate c : coordinates) {
            		int dist = Math.abs(x - c.x) + Math.abs(y - c.y);
            		if(dist< currentBest) {
            			currentBest = dist;
            			currentBestId = c.id;
            		}
            	}
                grid[y][x] = currentBestId;
                coordinates.get(currentBestId).total++;
            }
        }
		//Run along the vertical edges and set infinite regions to total = 0
        for (int y = 0; y < grid.length; y++) {
            int bad = grid[y][0];
            coordinates.get(bad).total = 0;
            bad = grid[y][grid.length-3];
            coordinates.get(bad).total = 0;
        }
        //Run along the horizontal edges and set infinite regions to total = 0
        for (int x = 0; x < grid[0].length; x++) {
            int bad = grid[0][x];
            coordinates.get(bad).total = 0;
            bad = grid[grid[0].length][x];
            coordinates.get(bad).total = 0;
        }
        
        //Find the region with the largest area and store its id
        int maxArea = 0;
        int maxId = 0;
        for(Coordinate c : coordinates) {
        	if(c.total > maxArea) {
        		maxArea = c.total;
        		maxId = c.id;
        	}
        }
		System.out.println("Maximum Area: " + maxArea + ", Max ID: " + maxId);
		
		int regionSize = 0;
		//For each point on the grid, find the distance to EVERY point
		//If the distance is less than 10,000 increment the region size by 1
		for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
            	int totalDistance = 0;
            	for(Coordinate c : coordinates) {
            		totalDistance += Math.abs(x - c.x) + Math.abs(y - c.y);
            	}
            	if(totalDistance < 10000) {
            		regionSize++;
            	}
            }
        }
		System.out.println("The region that contains every point with a distance less than 10000 is of size: " + regionSize);
	}
}
class Coordinate{
	int id;
	int x;
	int y;
	int total;
	
	public Coordinate(int inId, int inX, int inY) {
		this.id = inId;
		this.x = inX;
		this.y = inY;
	}
}
