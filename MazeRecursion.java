import java.awt.*;
import javax.swing.*;
import trubgp.*;  // TRU Board Games Playground package


public class MazeRecursion
{
  static Board board;  // Game board
  static int SIZE = 30;
  static int maze[][] = new int[SIZE][SIZE];  // 0 means room; 1 means wall
  static boolean valid[][] = new boolean[SIZE][SIZE];
  
  
  public static void main(String[] args)
  {
    // Creat a game board
    create();
    
    // Generate a maze
    generate();
  }
  
  
  // Create a new board
  
  static void create()
  {
    // New maze size
    maze = new int[SIZE][SIZE];
    
    // Construct a new board for a maze
    
    board = new Board(SIZE, SIZE, 500, 500, "Line", Color.LIGHT_GRAY);  // Line or NoLine
    board.setTitle("Maze - Recursion");
    
    board.button1SetName("New Maze");
    board.setText("" + SIZE);
    board.button1ClickEventListener(new BGPEventListener() { @Override public void clicked(int row, int col) {
      button1Clicked();
    }});

    board.button2SetName("Solve");
    board.button2ClickEventListener(new BGPEventListener() { @Override public void clicked(int row, int col) {
      solve();
    }});
  }
  
  
  // Click event handler for Button1
  
  static void button1Clicked()
  {
    // Read the maze size
    SIZE = Integer.parseInt(board.getText());
    
    // Displse the current board
    board.dispose();
    
    // Create a new board
    create();

    // Generate a new maze
    generate();
  }
  
  
  // Generate a maze
  
  static void generate()
  {
    int x, y;
    int count = 0;
    
    for (y = 0; y < SIZE; y++)
      for (x = 0; x < SIZE; x++) {
        maze[y][x] = 1;
        board.cellBackgroundColor(y, x, Color.DARK_GRAY);
      }

    while (count < (SIZE/4)*SIZE) 
    {
      x = (int)(Math.random() * (SIZE-2)) + 1;  // 0 < x < SIZE-1
      y = (int)(Math.random() * (SIZE-2)) + 1;  // 0 < y < SIZE-1
      if (maze[y][x] == 0)
        continue;
      
      expand_path(y, x);
      
      count++;
    }
    
    maze[0][1] = 0;  // Entrance
    maze[1][1] = 0;
    maze[2][1] = 0;
    board.cellBackgroundColor(0, 1, Color.CYAN);
    board.cellBackgroundColor(1, 1, Color.WHITE);
    board.cellBackgroundColor(2, 1, Color.WHITE);
    expand_path(2, 1);
    maze[SIZE-2][SIZE-1] = 0;  // Exit
    maze[SIZE-2][SIZE-2] = 0;
    maze[SIZE-2][SIZE-3] = 0;
    board.cellBackgroundColor(SIZE-2, SIZE-1, Color.CYAN);
    board.cellBackgroundColor(SIZE-2, SIZE-2, Color.WHITE);
    board.cellBackgroundColor(SIZE-2, SIZE-3, Color.WHITE);
    expand_path(SIZE-2, SIZE-3);

    for (int r = 0; r < SIZE; r++)
    for (int c = 0; c < SIZE; c++) {
      visited[r][c] = false;
      if (board.cellBackgroundColor(r, c) == Color.WHITE)
        valid[r][c] = true;
      else
        valid[r][c] = false;
    }
    valid[0][1] = true;
    valid[SIZE-2][SIZE-1] = true;
  }
  
  static void expand_path(int y, int x)
  {
    int direction, times;
    
    direction = (int)(Math.random() * 4);
    times = (int)(Math.random() * 3) + 2;
      
    switch(direction) {
      case 0:  // NORTH
        for (int i = 0; i < times; i++) {
          if (!isValidCell(y, x))
            break;
          maze[y][x] = 0;
          board.cellBackgroundColor(y, x, Color.WHITE);
          y--;
        }
        break;
      case 1:  // EAST
        for (int i = 0; i < times; i++) {
          if (!isValidCell(y, x))
            break;
          maze[y][x] = 0;
          board.cellBackgroundColor(y, x, Color.WHITE);
          x++;
        }
        break;
      case 2:  // SOUTH
        for (int i = 0; i < times; i++) {
          if (!isValidCell(y, x))
            break;
          maze[y][x] = 0;
          board.cellBackgroundColor(y, x, Color.WHITE);
          y++;
        }
        break;
      case 3:  // WEST
        for (int i = 0; i < times; i++) {
          if (!isValidCell(y, x))
            break;
          maze[y][x] = 0;
          board.cellBackgroundColor(y, x, Color.WHITE);
          x--;
        }
        break;
    }
  }
  
  // Check if the position is valid; The outer wall should be excluded.
  
  static boolean isValidCell(int y, int x)
  {
    if (x < 1 || x >= SIZE-1 || y < 1 || y >= SIZE-1)
      return false;
    else
      return true;
  }

  
//////////////////////////////////////////////////////////////////////////
  
  /* 
   * Solve the maze problem using recursion
   */

  static int startRow = 0, startColumn = 1;
  static int goalRow = SIZE-2, goalColumn = SIZE-1;
  static boolean visited[][] = new boolean[SIZE][SIZE];


  // Invoked when "Solve" button is clicked
  static void solve()
  {
    // initialization
    
    for (int r = 0; r < SIZE; r++)
    for (int c = 0; c < SIZE; c++) {
      visited[r][c] = false;
    }
    
    // find a path using a recursion
    
    if (traverse(startRow, startColumn))
      JOptionPane.showMessageDialog(null, "The green color path is found.");
    else
      JOptionPane.showMessageDialog(null, "No path found!");
  }
  
  
  // Recursive method to check the current cell is the goal; if not the goal, then traverse neighbor cells.
  //   row - the row number of the current cell
  //   col - the column number of the current cell
  //   return - the traversal result
  //     true - EXIT found
  //     false - invalid cell; visited already; no EXIT found
  
  static boolean traverse(int row, int col)
  {
    // base case 1 - the current position is not valid
    if (!isValid(row,col))
      return false;
    
    // base case 2 - the current position was visited
    if(isVisited(row,col))
      return false;
    
    // base case 3
    // test if the current cell is the goal
    if(isGoal(row,col)) {
      board.cellBackgroundColor(row, col, Color.GREEN);
      return true;
    }
  
    
    // recursive case
    
    // mark the current cell as 'visited' - you can use setVisited()
    setVisited(row, col);
    
    boolean done = false;
    
    // traverse to NORTH
    done = traverse(row-1, col);
    if (done) {
      board.cellBackgroundColor(row, col, Color.GREEN);
      return true;
    }
    // traverse to SOUTH
    done = traverse(row+1, col);
    if (done) {
      board.cellBackgroundColor(row, col, Color.GREEN);
      return true;
    }
    // traverse to EAST
    done = traverse(row, col+1);
    if (done) {
      board.cellBackgroundColor(row, col, Color.GREEN);
      return true;
    }
    // traverse to WEST
    done = traverse(row, col-1);
    if (done) {
      board.cellBackgroundColor(row, col, Color.GREEN);
      return true;
    }
    // no EXIT found
    else
    return false;
    
  }


//////////////////////////////////////////////////////////////////////////
  
  // utility methods
  

  static boolean isGoal(int row, int col)
  {
    if (row == goalRow && col == goalColumn)
      return true;
    else
      return false;
  }


  static boolean isValid(int r, int c)
  {
    if (r < 0 || r >= SIZE) return false;
    if (c < 0 || c >= SIZE) return false;

    return valid[r][c];
  }


  static boolean isVisited(int r, int c)
  {
    return visited[r][c];
  }


  static void setVisited(int r, int c)
  {
    visited[r][c] = true;
    board.cellBackgroundColor(r, c, Color.YELLOW);
  }
}
