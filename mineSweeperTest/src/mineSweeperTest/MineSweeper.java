package mineSweeperTest;
import objectdraw.*;
import java.awt.*;

/*
 * Minesweeper basic code
 * 
 * TODO: Figure out how to split this class up into other classes
 * 
 * @author Dustin Bloom
 * 
 * @version 3-5-21
 *
 */

public class MineSweeper extends WindowController
{
    // Define sizes and spacing for the bricks
    private final static int BRICK_WIDTH=30;
    private final static int BRICK_HEIGHT=30;
    private final static int BRICK_SPACING = 1;
    
    //this value tells you how many columns there are
    private static int BRICKS_PER_ROW;
    
    //this value tells you how many rows there are
    private static int MAX_ROWS;
    
    //this value is how many bombs should be placed for this difficulty
    private static int MAX_BOMBS;
    
    
    //image of the bomb
    private SimpleImage bomb;
    
    //Three parallel arrays of the same size of the board
    //the filled rectangles which hide the tile
    private FilledRect[][] myTiles;
    //a boolean array of whether there is a bomb on this tile
    private boolean[][]    isBomb;
    //an integer of the count of nearby bombs. Should be zero on tiles that are also bombs.
    private int[][]        bombCount;

    
    public static void main(String[] args) {
        //The difficulty should be passed in using main
    	
    	
        String level;
      //TODO: Create a button to set difficulty instead of command line args
        if(args.length == 0) { 
            level = "easy"; 
        } else { 
            level = args[0]; 
        }
        MineSweeper myWindow = new MineSweeper(level);
    }

    public MineSweeper(String level) {
        //this class will be constructed with a level of difficulty.
        switch(level) {
            case "easy":
                BRICKS_PER_ROW = 10;
                MAX_ROWS = 10;
                MAX_BOMBS = 10;
                break;
            case "medium":
                BRICKS_PER_ROW = 20;
                MAX_ROWS = 20;
                MAX_BOMBS = 50;
                break;
            case "hard":
                BRICKS_PER_ROW = 30;
                MAX_ROWS = 20;
                MAX_BOMBS = 100;
                break;
            default:
                BRICKS_PER_ROW = 10;
                MAX_ROWS = 10;
                MAX_BOMBS = 10;
        }
        //create the arrays with the correct sizes
        myTiles = new FilledRect[MAX_ROWS][BRICKS_PER_ROW]; //default to null
        isBomb   = new boolean[MAX_ROWS][BRICKS_PER_ROW];   //default to false
        bombCount= new int[MAX_ROWS][BRICKS_PER_ROW];       //default to zero
        
        //add bomb image here
        bomb = new SimpleImage("bomb.png");
        
        
        setBombLocations();
        calculateBombCount();
        
        //start WindowController with correct size for board. 
        //To do this I need to add 60 to height for file menu
        //don't know why I need to add 15 to width but it works...
        this.startController(BRICKS_PER_ROW * (BRICK_WIDTH+1)+15, MAX_ROWS * (BRICK_HEIGHT+1)+60);
    }

    //this function randomly assigns bomb locations.
    public void setBombLocations() {
    	
        RandomIntGenerator randCol = new RandomIntGenerator(0,BRICKS_PER_ROW-1);
        RandomIntGenerator randRow = new RandomIntGenerator(0,MAX_ROWS-1);
        // loop until you have randomly placed 10 bombs on different squares.  
        // the isBomb boolean array you set to true on the row,column of where the bomb is.
        for (int bomb = 0; bomb < MAX_BOMBS;bomb++){
            boolean bombPlaced = false;
            while(bombPlaced == false){
                
                     if(isBomb[randRow.nextValue()][randCol.nextValue()] == false){
                         isBomb[randRow.nextValue()][randCol.nextValue()] = true;
                         bombPlaced = true;
                
                        }
                      
                    
           }
        }
            }
        
            

    //this function assigns a number per square to tell you how many bombs are near that square.
    public void calculateBombCount() {
        //loop over the entire bombCount array of integers
        //assigning how many bombs are 
            int count = 0;
            for(int row=0;row<MAX_ROWS;row++){
                for(int col=0;col<BRICKS_PER_ROW;col++){
                    //checking which neighbor to the upper left?
                    if (row-1 >=0 && row-1 <MAX_ROWS 
                        && col-1 >=0 && col-1 <BRICKS_PER_ROW){
                        if(isBomb[row-1][col-1]){
                           bombCount[row][col]++;
                        }
                        }
                       // checking which neighbor is to the bottom left ?
                    if (row+1 >=0 && row+1 <MAX_ROWS 
                        && col-1 >=0 && col-1 <BRICKS_PER_ROW){
                        if(isBomb[row+1][col-1]){
                           bombCount[row][col]++;
                        }
                     }
                     // checking which neighbor is to the upper right  ?
                     if (row-1 >=0 && row-1 <MAX_ROWS 
                        && col+1 >=0 && col+1 <BRICKS_PER_ROW){
                        if(isBomb[row-1][col+1]){
                           bombCount[row][col]++;
                        }
                        }
                         // checking which neighbor is to the   ?
                      if (row+1 >=0 && row+1 <MAX_ROWS 
                        && col+1 >=0 && col+1 <BRICKS_PER_ROW){
                       if(isBomb[row+1][col+1]){
                           bombCount[row][col]++;
                        }
                      }
                      // checking which neighbor is to the right   ?
                       if (row >=0 && row <MAX_ROWS 
                        && col+1 >=0 && col+1 <BRICKS_PER_ROW){
                       if(isBomb[row][col+1]){
                           bombCount[row][col]++;
                        }
                      }
                      // checking which neighbor is to the left  ?
                       if (row >=0 && row <MAX_ROWS 
                        && col-1 >=0 && col-1 <BRICKS_PER_ROW){
                       if(isBomb[row][col-1]){
                           bombCount[row][col]++;
                        }
                      }
                       // checking which neighbor is above ?
                       if (row-1 >=0 && row-1 <MAX_ROWS 
                        && col >=0 && col <BRICKS_PER_ROW){
                       if(isBomb[row-1][col]){
                           bombCount[row][col]++;
                        }
                      }
                       // checking which neighbor is below  ?
                        if (row+1 >=0 && row+1 <MAX_ROWS 
                        && col >=0 && col <BRICKS_PER_ROW){
                       if(isBomb[row+1][col]){
                           bombCount[row][col]++;
                        }
                      }
                }
                
            }
            //inside the loop, you must inspect all 8 of your neighbors and 
            //  add 1 to the current bombCount Location for each neighbor with a bomb
            //Remember to make sure you have a neighbor before you have a bomb there 
            //  not all locations have 8 neighbors. corners only have 3.   
    }

    public void begin() {
        FilledRect currentBrick;        
        for(int numRows = 0, nextBrickY = 0; numRows < MAX_ROWS ; numRows++, nextBrickY+=BRICK_SPACING+BRICK_HEIGHT) {
            for(int numBricks = 0, nextBrickX = 0; numBricks < BRICKS_PER_ROW; numBricks++) {
                // draw a single square
                currentBrick = new FilledRect(nextBrickX, nextBrickY, BRICK_WIDTH, BRICK_HEIGHT, canvas);
                myTiles[numRows][numBricks] = currentBrick;
                myTiles[numRows][numBricks].setColor(Color.GRAY);
                
                // Update the next squares's coordinate
                nextBrickX += BRICK_WIDTH + BRICK_SPACING;
            }
        }

    }
    
    public void onMouseClick(Location point) {
        Text myText;
        
        for(int row=0 ; row < MAX_ROWS; row++) {
            for(int col=0; col < BRICKS_PER_ROW; col++) {                
                if( myTiles[row][col].contains(point) ) {
                    Location currentLocation = myTiles[row][col].getLocation();
                    if( myTiles[row][col].isHidden()){
                        return;
                    }else if(isBomb[row][col]) { //If it IS a bomb
                        new VisibleImage(bomb.getRawImage(),currentLocation,canvas);
                        myTiles[row][col].hide();
                    } else if(bombCount[row][col] > 0 ) { //If it is NOT a bomb
                    	
                        //draw text box here
                    	Location textLocation = myTiles[row][col].getLocation();
                        myText = new Text(bombCount[row][col],currentLocation,canvas);//TODO: Fix this line so that the number shows up on proper tile.
                        myText.setBold(true);
                        myText.setFontSize(BRICK_WIDTH);
                        myTiles[row][col].hide();
                    } else {
                        //This condition is when bombCount is zero or blank. AKA: There are no neighbors
                        myTiles[row][col].hide();
                       
                        
                    }
                    return;
                }
            }
        }
    }
}
