
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ListIterator;

import javax.swing.JComponent;

/**
   MazeComponent class
   
   A component that displays the maze and path through it if one has been found.
 */
public class MazeComponent extends JComponent
{
	
   private Maze maze;
   
   private static final int START_X = 10; // where to start drawing maze in frame
   private static final int START_Y = 10;
   private static final int BOX_WIDTH = 20;  // width and height of one maze unit
   private static final int BOX_HEIGHT = 20;
   private static final int INSET = 2;  // how much smaller on each side to make entry/exit inner box
   private static final int TWO_SIDE = 2;  // for value of some positions
   private static final Color START_COLOR =  Color.YELLOW;  //define colors of start point, end point, wall and path
   private static final Color END_COLOR = Color.GREEN;
   private static final Color WALL_COLOR = Color.BLACK;
   private static final Color PATH_COLOR = Color.BLUE;
   private static final int FOR_INIT = 0;
                    
   
   
   /**
      Constructs the component.
      @param maze   the maze to display
    */
   public MazeComponent(Maze maze) 
   {   
      this.maze = maze;
   }

   
   /**
      Draws the current state of maze including the path through it if one has
      been found.
      @param g the graphics context
      length: 30 lines
    */
   public void paintComponent(Graphics g)
   {
	   Graphics2D g2 = (Graphics2D) g;
	   // draw start point and end point
	   g2.setColor(START_COLOR);
	   Rectangle myStart = new Rectangle(START_X + maze.getEntryLoc().getCol()*BOX_WIDTH+INSET, START_Y + maze.getEntryLoc().getRow()*BOX_HEIGHT+INSET, BOX_WIDTH-INSET*TWO_SIDE, BOX_HEIGHT-INSET*TWO_SIDE);
	   g2.fill(myStart);
	   g2.setColor(END_COLOR);
	   Rectangle myEnd = new Rectangle(START_X + maze.getExitLoc().getCol()*BOX_WIDTH+INSET, START_Y + maze.getExitLoc().getRow()*BOX_HEIGHT+INSET, BOX_WIDTH-INSET*TWO_SIDE, BOX_HEIGHT-INSET*TWO_SIDE);
	   g2.fill(myEnd);
	   //draw walls and borders
	   g2.setColor(WALL_COLOR);
	   Rectangle myWall;
	   for (int i = FOR_INIT; i < maze.numRows(); i++){
		   for (int j = FOR_INIT; j < maze.numCols(); j++){
			   if (maze.hasWallAt(new MazeCoord(i,j))){
				   myWall= new Rectangle(START_X + j*BOX_WIDTH, START_Y + i*BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
				   g2.fill(myWall);
			   }
		   }
	   }
	   myWall = new Rectangle(START_X,START_Y,maze.numCols()*BOX_WIDTH,maze.numRows()*BOX_HEIGHT);
	   g2.draw(myWall);
	   //draw path use linkedlist 
	   g2.setColor(PATH_COLOR);
	   double x1,y1,x2,y2;
	   ListIterator<MazeCoord> iter = maze.getPath().listIterator(); 
	   MazeCoord temp = null;
	   if (iter.hasNext()){
		   temp = iter.next();
	   }
	   //use iteration to get the value
	   while (iter.hasNext()){
		   x1 = START_X + temp.getCol() * BOX_WIDTH + BOX_WIDTH / TWO_SIDE;
		   y1 = START_Y + temp.getRow() * BOX_HEIGHT + BOX_HEIGHT / TWO_SIDE;
		   temp = iter.next();
		   x2 = START_X + temp.getCol() * BOX_WIDTH + BOX_WIDTH / TWO_SIDE;
		   y2 = START_Y + temp.getRow() * BOX_HEIGHT + BOX_HEIGHT / TWO_SIDE;
		   Line2D.Double myPath = new Line2D.Double(x1, y1, x2, y2);	  
		   g2.draw(myPath);
	   }			   
   }   
}


