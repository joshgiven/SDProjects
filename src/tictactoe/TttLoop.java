package tictactoe;

import java.util.Scanner;

public class TttLoop {
  static Scanner userInput;

  public static void main(String[] args) {
    userInput = new Scanner(System.in);

    char[][] board = initializeBoard();
    int row, col;
    char player;

    do { // master loop
      player = getUserChar("Who plays first? (X or O) (or Q to quit): ");
      if(player == 'Q') {
        System.out.println("Thanks for playing.");
        break;
      }

      do { // single game loop
        // printBoard(board);
        fancyPrintBoard(board);

        if(detectWin(board)) {
          System.out.println("\nPLAYER "+ switchPlayer(player) +" WINS!!!");
          System.out.println("\nPlay again?");
          board = initializeBoard();
          break;
        }

        if(isBoardFull(board)) {
          System.out.println("\nTie game.");
          System.out.println("\nPlay again?");
          board = initializeBoard();
          break;
        }

        System.out.println("It is Player "+ player +"\'s turn.");
        do {
          row = getUserInt("Enter row:    ");
          col = getUserInt("Enter column: ");

          if(!isValidCoord(row, col)) {
            System.out.println("That's out of range. Try again.");
          }
          else if(!isOpenPosition(board, row, col)) {
            System.out.println("That position is already taken. Try again.");
          }
          else {
            break;
          }
        } while(true);

        System.out.printf("\nPlayer '%s' chose position (%d,%d)\n",
                          player, row, col);

        insertBoardChoice(board, row, col, player);

        player = switchPlayer(player);
      } while(true);
    } while(true);

    userInput.close();
  }

  static char switchPlayer(char oldPlayer) {
  // player = switchPlayer(player);
    if(oldPlayer == 'X') {
      return 'O';
    }
    else {
      return 'X';
    }
  }

  static char[][] initializeBoard() {
    char[][] freshBoard = {
      {'*', '*', '*'},
      {'*', '*', '*'},
      {'*', '*', '*'},
    };

    return freshBoard;
  }

  static void insertBoardChoice(char[][] board,
                                int row, int col,
                                char mark) {
    board[row-1][col-1] = mark;
  }

  static void printBoard(char[][] board) {
    System.out.println();
    System.out.println("   123");
    System.out.println(" +----");

    for(int row=0; row<3; row++) {
      System.out.print((row+1) + "| ");
      for(int col=0; col<3; col++) {
        System.out.print(board[row][col]);
      }
      System.out.println();
    }
    System.out.println();
  }

  // if(isBoardFull(board)) {
  static boolean isBoardFull(char[][] board) {
    for(int row=0; row<3; row++) {
      for(int col=0; col<3; col++) {
        if(board[row][col] == '*') {
          return false;
        }
      }
    }

    return true;
  }

  // if(detectWin(board)) {
  static boolean detectWin(char[][] board){
    int row, col;

    // test rows
    for(row=0;row<3;row++) {
      if (board[row][0] == board[row][1] &&
          board[row][1] == board[row][2] &&
          board[row][2] != '*'){
        return true;
      }
    }

    // test cols
    for(col=0;col<3;col++) {
      if (board[0][col] == board[1][col] &&
          board[1][col] == board[2][col] &&
          board[2][col] != '*') {
        return true;
      }
    }

    // diag
    if(board[0][0] == board[1][1] &&
       board[1][1] == board[2][2] &&
       board[2][2] != '*') {
      return true;
    }

    if(board[0][2] == board[1][1] &&
       board[1][1] == board[2][0] &&
       board[2][0] != '*') {
      return true;
    }

    return false;
  }

  static boolean isOpenPosition(char[][] board, int row, int col) {
    return board[row-1][col-1] == '*';
  }

  static boolean isValidCoord(int row, int col) {
    if(row >= 1 && row <= 3 && col >= 1 && col <= 3) {
      return true;
    }
    else {
      return false;
    }
  }

  static int getUserInt(String prompt) {
    do {
      System.out.print(prompt);
      if(userInput.hasNextInt()) {
        return userInput.nextInt();
      }
      else {
        String junk = userInput.next();
        if(junk.equalsIgnoreCase("q")) {
          System.err.println("QUITTER!\n\n");
          System.exit(-1);
        }
        System.out.println("Bad value. Try again.");
      }
    } while(true);
  }

  static char getUserChar(String prompt) {
    String s;
    do {
      System.out.print(prompt);
      s = userInput.next().toUpperCase();

      if(s.equals("Q") || s.equals("O") || s.equals("X")) {
        return s.charAt(0);
      }
      else {
        System.out.println("Bad value. Try again.");
      }
    } while(true);
  }

  static void fancyPrintBoard(char[][] board) {
    String[] bigX     = { " X   X ",
                          "  X X  ",
                          "   X   ",
                          "  X X  ",
                          " X   X ",
                          "-------"  };

    String[] bigO     = { "  OOO  ",
                          " O   O ",
                          " O   O ",
                          " O   O ",
                          "  OOO  ",
                          "-------"  };

    String[] bigEmpty = { "       ",
                          "       ",
                          "       ",
                          "       ",
                          "       ",
                          "-------" };

    String header1   =    "     1   " + "    2    " + "   3    ";
    String header2   =    "  -------" + "---------" + "------- ";

    String[] leftSide =  { " ",
                           //" ",
                           " ",
                           "1",
                           " ",
                           " ",
                           " ",
                           " ",
                           " ",
                           "2",
                           " ",
                           " ",
                           " ",
                           " ",
                           " ",
                           "3",
                           " ",
                           " ",
                           " "  };

    String[][][] table = new String[3][3][bigX.length];
    int row, col;
    char val;
    String[] bigSet;

    for(row=0; row < 3; row++) {
      for(col=0; col < 3; col++) {
        val = board[row][col];
        if(val == 'X') {
          bigSet = bigX;
        }
        else if(val == 'O') {
          bigSet = bigO;
        }
        else {
          bigSet = bigEmpty;
        }
        table[row][col] = bigSet;
      }
    }

    String s;
    int nSetLines = bigX.length;
    int nLines = 3*nSetLines;
    int i;

    System.out.println(header1);
    System.out.println(header2);
    for(int line=0; line < nLines; line++) {
      s = leftSide[line] + "|";

      row = line / nSetLines;
      i   = line % nSetLines;
      for(col=0; col < 3; col++) {
        s += table[row][col][i];
        s += "|";
      }
      System.out.println(s);
    }
  }

  static void wipeScreen() {
    try {
      Runtime.getRuntime().exec("/usr/bin/clear");
    }
    catch(Exception e) { System.out.println("exceptional");}
  }

  /**
  * @author Biggie Smalls
  */
  static void meBigPoppa() throws Exception {
    System.out.println("I love it!");

    boolean player = true;
    boolean you = ((int)(Math.random()*2) == 1) ? true : false;

    if(you == player) {
      throw new Exception("Your hands in the air");
    }
  }
}
