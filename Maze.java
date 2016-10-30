

import java.util.LinkedList;
import java.util.Random;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls
 */

public class Maze {
   
   public static final boolean FREE = false; // Value of FREE and WALL
   public static final boolean WALL = true;
   private static final int FOR_INIT = 0; // Value for some initialization
   private static final int FOR_MOVE = 1; // Value for recursive move
   
   private boolean myMaze[][];
   private boolean visited[][];
   private MazeCoord start,end;
   private LinkedList<MazeCoord> myPath = new LinkedList<MazeCoord>();
   /**
      REPRESENTATION INVARIANT:
      -- myMaze[][] is the boolean two-dimensional array for maze,the value of element should be
      	 false(FREE) or true(WALL)
      -- the number of rows of myMaze[][] should be as same as the number of the lines of binary data
         in the input file
      -- the number of columns of myMaze[][] should be as same as the number of the columns of binary data
         in the input file
      -- the number of rows of visited[][] should be as same as the number of the lines of binary data
         in the input file
      -- the number of columns of visited[][] should be as same as the number of the columns of binary data
         in the input file
      -- the initial value of every elements in visited[][] should be false(FREE)
      -- if the point has been visited in the processing recursive step, change this element of the visited[][] to 
         true(WALL)
      -- the initial value of myPath should be empty   
      -- if we find a path, add the locations in the path to myPath recursively
      -- start and end should be equal to the content of the input file
   */

   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param endLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc)
   {
	   myMaze = mazeData;
	   visited = new boolean[mazeData.length][mazeData[FOR_INIT].length];
	   for (int i = FOR_INIT; i < myMaze.length; i++)
		   for (int j=FOR_INIT; j < myMaze[FOR_INIT].length; j++){
			   visited[i][j] = FREE;
		   }
	   assert startLoc.getRow() >= 0 && startLoc.getRow() < mazeData.length;
	   assert startLoc.getCol() >= 0 && startLoc.getCol() < mazeData[FOR_INIT].length;
	   assert endLoc.getRow() >= 0 && endLoc.getRow() < mazeData.length;
	   assert endLoc.getCol() >= 0 && endLoc.getCol() < mazeData[FOR_INIT].length;
	   
	   start = startLoc;
	   end = endLoc;

   }


   /**
      Returns the number of rows in the maze
      @return number of rows
    */
   public int numRows() {
       return myMaze.length;   
   }

   
   /**
      Returns the number of columns in the maze
      @return number of columns
    */   
   public int numCols() {
       return myMaze[FOR_INIT].length;   
   } 
 
   
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
    */
   public boolean hasWallAt(MazeCoord loc) {
	   
	   assert loc.getRow() >= 0 && loc.getRow() < myMaze.length;
	   assert loc.getCol() >= 0 && loc.getCol() < myMaze[FOR_INIT].length;
	  
       return myMaze[loc.getRow()][loc.getCol()];   
   }
   

   /**
      Returns the entry location of this maze.
    */
   public MazeCoord getEntryLoc() {
       return start;   
   }
   
   
   /**
      Returns the exit location of this maze.
    */
   public MazeCoord getExitLoc() {
       return end;
   }

   
   /**
      Returns the path through the maze. First element is starting location, and
      last element is exit location.  If there was not path, or if this is called
      before search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() {
	   return myPath;   
   }


   /**
      Find a path through the maze if there is one.  Client can access the
      path found via getPath method.
      @return whether path was found.
    */
   public boolean search()  {  
	   // if the start or end position has wall, we can't find the path
       if (hasWallAt(start) || hasWallAt(end)){
    	   return false;
       }
       // if we find the path at the first time, save it and always return success
       if (!myPath.isEmpty()){
    	   return true;
       }
       boolean result = searchRecur(start.getRow(),start.getCol());
       return result;
       
   }
   /**
      Returns true iff there is a path through the input location (myRow, myCol)
      @param myRow the x-coordinate of the location used to do next recursion
      @param myCol the y-coordinate of the location used to do next recursion
      @return whether there is a successful path
      PRE: 0 <= myRow < numRows() and 0 <= myCol < numCols(), actually I do judgements 
      at the start
    */
   private boolean searchRecur(int myRow, int myCol){
	   if (myRow >= myMaze.length || myCol >= myMaze[FOR_INIT].length || myRow < 0 || myCol < 0){
		   return false;
	   }
	   // circle detect
	   if (visited[myRow][myCol]){
		   return false;
	   }
	   // if this is the end position, we find the path
	   if (myRow == end.getRow() && myCol == end.getCol()){
		   myPath.add(new MazeCoord(myRow,myCol));
		   visited[myRow][myCol] = WALL;
		   return true;
	   }
	   // if this is a wall, fail
	   if (myMaze[myRow][myCol]){
		   return false;
	   }
	   // do this to adjacent positions recursively
	   else{
		   boolean result = false;
		   visited[myRow][myCol] = WALL;
		   if (!result && searchRecur(myRow+FOR_MOVE, myCol)){
			   result = true;
		   }
		   if (!result && searchRecur(myRow-FOR_MOVE, myCol)){
			   result = true;
		   }
		   if (!result && searchRecur(myRow, myCol+FOR_MOVE)){
			   result = true;
		   }
		   if (!result && searchRecur(myRow, myCol-FOR_MOVE)){
			   result = true;		   
		   }
		   visited[myRow][myCol] = FREE;
		   // add the correct locations to the path
		   if(result){
			   visited[myRow][myCol] = WALL;
			   myPath.add(new MazeCoord(myRow,myCol));
			   return result;
		   }
		   return false;
	   	}
	   
   }

}
