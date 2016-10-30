

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;


/**
 * MazeViewer class
 * 
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 * 
 * How to call it from the command line:
 * 
 *      java MazeViewer mazeFile
 * 
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row, followed by the start location, 
 * and ending with the exit location. Each maze location is
 * either a wall (1) or free (0). Here is an example of contents of a file for
 * a 3x4 maze, with start location as the top left, and exit location as the bottom right
 * (we count locations from 0, similar to Java arrays):
 * 
 * 3 4 
 * 0111
 * 0000
 * 1110
 * 0 0
 * 2 3
 * 
 */

public class MazeViewer {
   
   private static final char WALL_CHAR = '1';	// char value to compare with the content of input file
   private static final char FREE_CHAR = '0';
   private static final int FOR_INIT = 0;	// for some initilization
   
   /**
   	 Main method
   	 Generate Jframe, call the readMazeFile method and catch some exceptions
    */
   public static void main(String[] args)  {

       String fileName = "";

       try {
    	   if (args.length < 1) {
    		   System.out.println("ERROR: missing file name command line argument");
    	   }
    	   else {
    		   fileName = args[0];
            
    		   JFrame frame = readMazeFile(fileName);

    		   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    		   frame.setVisible(true);
    	   }

       }
       catch (FileNotFoundException exc) {
    	   System.out.println("File not found: " + fileName);
       }
       catch (IOException exc) {
    	   exc.printStackTrace();
       }
       catch (NumberFormatException exception) {
    	   System.out.println("File content not match: " + fileName);
       } 
   	}

    /**
       readMazeFile reads in maze from the file whose name is given and 
       returns a MazeFrame created from it.
   	
   	   @param fileName
              the name of a file to read from (file format shown in class comments, above)
       @returns a MazeFrame containing the data from the file.
        
   	   @throws FileNotFoundException
               if there's no such file (subclass of IOException)
       @throws IOException
               (hook given in case you want to do more error-checking.
               that would also involve changing main to catch other exceptions)
       length: 29 lines
    */
   private static MazeFrame readMazeFile(String fileName) throws IOException {
	   FileInputStream inputStream = new FileInputStream(fileName);
	   Scanner in = new Scanner(inputStream);
	   int row = FOR_INIT,col=FOR_INIT;
	   MazeCoord start = null,end = null;
	   boolean myMaze[][] = null;
	   // read size of the maze
	   if (in.hasNextInt()){
		   row = in.nextInt();
		   col = in.nextInt();
		   myMaze = new boolean[row][col];
	   }
	   // output some error information
	   else{
		   System.out.println("File content not match: " + fileName);	
	   }
	   in.nextLine();
	   String line = null;
	   // read the maze information
	   for (int i = FOR_INIT; i < row; i++){
		   if (in.hasNextLine()){
			   line =in.nextLine();}
		   else{
			   System.out.println("File content not match: " + fileName);	
		   }
		   for (int j = FOR_INIT; j < col; j++){
				   if (line.charAt(j) == FREE_CHAR){
					   myMaze[i][j] = Maze.FREE;
				   }
				   if (line.charAt(j) == WALL_CHAR){
					   myMaze[i][j] = Maze.WALL;
				   }	
		   }
	   }
	   // read the start point and end point
	   if (in.hasNextLine()){
		   start= new MazeCoord(in.nextInt(),in.nextInt());
		   end= new MazeCoord(in.nextInt(),in.nextInt());
	   }
	   else{
		   System.out.println("File content not match: " + fileName);
	   }
	   return new MazeFrame(myMaze, start, end);
   }
}