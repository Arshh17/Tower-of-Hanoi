/*
 * Operands: just one digit such as 0, 1, ..., 9
 * Operators: +, -, /, *, (...); no unary operations, such as -4 or +4
 * Not complete systax checking
 */


import java.awt.*;
import javax.swing.*;
import trubgp.*;  // TRU Board Games Playground package


public class TowerHanoi
{
  static Board board;  // Game board
  static int SIZE = 4;  // size of the tower
  static int disks0[] = {1, 3, 5, 7};  // disk sizes for tower0; from the top
  static int disks1[] = {0, 0, 0, 0};  // disk sizes for tower1
  static int disks2[] = {0, 0, 0, 0};  // disk sizes for tower2
  static int center0 = 4;
  static int center1 = 12;
  static int center2 = 20;
  
  
  public static void main(String[] args)
  {
    // Creat a game board
    create();
  }
  
  
  // Create a new board
  
  static void create()
  {
    // Construct a new board
    
    board = new Board(6, 25, 1000, 162, "Line", Color.WHITE);  // Line or NoLine
    board.setTitle("Tower of Hanoi");
    
    board.button1SetName("Start");
    board.button1ClickEventListener(new BGPEventListener() { @Override public void clicked(int row, int col) {
      start();
    }});

    board.button2SetName("Reset");
    board.button2ClickEventListener(new BGPEventListener() { @Override public void clicked(int row, int col) {
      reset();
    }});
    
    // diplay towers
    
    display(0, disks0);
    display(1, disks1);
    display(2, disks2);
  }


  // Display a tower
  
  static void display(int tower, int disks[])
  {
    int center;
    
    if (tower == 0)
      center = center0;
    else if (tower == 1)
      center = center1;
    else
      center = center2;
    
    for (int i = center-3; i <= center+3; i++) {
      board.cellBackgroundColor(1, i, Color.WHITE);
      board.cellBackgroundColor(2, i, Color.WHITE);
      board.cellBackgroundColor(3, i, Color.WHITE);
      board.cellBackgroundColor(4, i, Color.WHITE);
    }
    
    for (int j = 0; j < 4; j++)
    for (int i = 0; i < disks[j]; i++)
      //board.cellBackgroundColor(j+1, i + center-(disks[j]/2), Color.LIGHT_GRAY);
      board.cellBackgroundColor(j+1, i + center-(disks[j]/2), Color.GRAY);
  }
  
  
  static void reset()
  {
    disks0 = new int[]{1, 3, 5, 7};  // disk sizes for tower0; from the top
    disks1 = new int[]{0, 0, 0, 0};  // disk sizes for tower1
    disks2 = new int[]{0, 0, 0, 0};  // disk sizes for tower2
    
    // diplay towers
    
    display(0, disks0);
    display(1, disks1);
    display(2, disks2);
  }
  
  
//////////////////////////////////////////////////////////////////////////////////
  
  // Invoked when "Start" button is clicked
  
  static void start()
  {
    // move the disks from tower 0 to tower 1
    
    move(0,2,3);  // Move the top 3 disks from tower 0 to tower 2 
    move(0,1,1);  // Move the top 1 disk from tower 0 to tower 1 
    move(2,1,3);  // Move the top 3 disks from tower 2 to tower 1 
  }
  
  
  // Recursive method to move multiple disks from a tower to another tower
  //   from - tower
  //   to - tower
  //   howmany - the number of disks to move from the top
  
  static void move(int from, int to, int howmany)
  {
    int another;  // the tower that is not from nor to
    
    // decide another tower
    
    if (from == 0 && to == 1) another = 2;
    else if (from == 0 && to == 2) another = 1;
    else if (from == 1 && to == 0) another = 2;
    else if (from == 1 && to == 2) another = 0;
    else if (from == 2 && to == 0) another = 1;
    else another = 0;
    
    // move disks
    
    // base case 1 - no more disk
    if (howmany == 0)
      return;
    
    // base case 2 - one disk
    else if (howmany==1) {
      // move a disk; from -> to
      moveDisk(from,to);
      
      // Display towers
      display(0, disks0);
      display(1, disks1);
      display(2, disks2);
      
      JOptionPane.showMessageDialog(null, "Next step!");
    } 
    
    // recursive case
    else {
      move(from,another,howmany-1);  // move disks; from -> another
      move(from,to,howmany-1);  // move a disk; from -> to
      move(another,to,howmany-1);  // move disks; another -> to
    }
  }
  
  // Move the top disk from a tower to another tower
  //   from - tower
  //   to - tower
  
  static void moveDisk(int from, int to)
  {
    int[] disksFrom, disksTo;
    int i;
    
    // Decide the disk size arrays for the from tower and the to tower
    
    if (from == 0) disksFrom = disks0;
    else if (from == 1) disksFrom = disks1;
    else disksFrom = disks2;
    if (to == 0) disksTo = disks0;
    else if (to == 1) disksTo = disks1;
    else disksTo = disks2;
    
    // Find the proper places for each tower to move a disk
    
    int placeFrom = 0;
    for (i = 0; i < 4; i++) {
      if (disksFrom[i] == 0) 
        continue;
      else {
        placeFrom = i;
        break;
      }
    }
    
    int placeTo = 0;
    for (i = 0; i < 4; i++)
      if (disksTo[i] != 0) 
        break;
    placeTo = i - 1;

    disksTo[placeTo] = disksFrom[placeFrom];
    disksFrom[placeFrom] = 0;
  }
}
