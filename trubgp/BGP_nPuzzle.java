import java.awt.*;
import trubgp.*;  // TRU Board Games Playground package


public class BGP_nPuzzle
{
  // Game board
  private static Board board;
  
  public static void main(String[] args)
  {
    // Creat the game board
    board = new Board(3, 3, 500, 500, "Line", Color.LIGHT_GRAY);  // Line or NoLine
    
    // Initialize board
    for (int row = 0; row < 3; row++) 
      for (int col = 0; col < 3; col++)
        board.cellContent(row, col, "" + (row * 3 + col + 1), 80);
    board.cellContent(2, 2, "");  // Empty cell
    board.cellBackgroundColor(2, 2, Color.WHITE);
    board.setTitle("3-Puzzle Game");
    
    // Shuffle the board
    shuffle();
    
    // Register the click event listener
    //   The row and column numbers will be passed so that we can see which cell is clicked.
    board.cellsClickEventListener(new BGPEventListener() { @Override public void clicked(int row, int col) {
      /*
        count++;
        board.cellContent(row, col, "" + count);
        board.cellBackgroundColor(row, col, Color.CYAN);
        if (row > 0)
          board.switchCells(row, col, row-1, col);
      */  // The above style is also okay.
      cellClicked(row, col);
      /**/
    }});
  }
  
  
  // Shuffle the board by exchanging the empty cell with its neighbor cell
  
  static void shuffle()
  {
    int count = 0;
    int row = 0, col = 0;
    boolean sleep = false;
    
    while (count < 100) {
      // Find the empty cell
      loop1:
      for (row = 0; row < 3; row++)
        for (col = 0; col < 3; col++)
          if (isEmptyCell(row, col))
            break loop1;
      
      // Select neighbor randomly and switch
      int direction = (int)(Math.random() * 4);
      if (direction == 0 && isValidCell(row-1, col)) {
        board.switchCells(row-1, col, row, col);
        count++;
        sleep = true;
      } else if (direction == 1 && isValidCell(row+1, col)) {
        board.switchCells(row+1, col, row, col);
        count++;
        sleep = true;
      } else if (direction == 2 && isValidCell(row, col-1)) {
        board.switchCells(row, col-1, row, col);
        count++;
        sleep = true;
      } else if (direction == 3 && isValidCell(row, col+1)) {
        board.switchCells(row, col+1, row, col);
        count++;
        sleep = true;
      }
      
      // For good visualization
      if (sleep) {
        board.sleep(50);
        sleep = false;
      }
    }
  }
  
  
  // Check if the row and column number are valid
  
  static boolean isValidCell(int row, int col)
  {
    if (row < 0 || row > 2 || col < 0 || col > 2)
      return false;
    else
      return true;
  }
  
  
  // Check if the cell is the empty cell
  
  static boolean isEmptyCell(int row, int col)
  {
    if (board.cellContent(row, col).equals(""))
      return true;
    else
      return false;
  }
  
  
  // Click event handler
  
  static void cellClicked(int row, int col)
  {
    if (isValidCell(row-1, col) && isEmptyCell(row-1, col)) {
      board.switchCells(row, col, row-1, col);
    }
    else if (isValidCell(row+1, col) && isEmptyCell(row+1, col)) {
      board.switchCells(row, col, row+1, col);
    }
    else if (isValidCell(row, col-1) && isEmptyCell(row, col-1)) {
      board.switchCells(row, col, row, col-1);
    }
    else if (isValidCell(row, col+1) && isEmptyCell(row, col+1)) {
      board.switchCells(row, col, row, col+1);
    }
  }
}