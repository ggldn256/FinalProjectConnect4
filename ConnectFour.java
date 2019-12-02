package connectfour;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectFour {
    private static final char[] players = new char[] { 'X', 'O' };

    private final int width, height;
    private final char[][] grid;
    private int lastCol = -1, lastTop = -1;

    public ConnectFour(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new char[height][];
        for (int h = 0; h < height; h++) {
            Arrays.fill(this.grid[h] = new char[width], '.');
        }
    }

    public String toString() {
        return IntStream.range(0, this.width)
                        .mapToObj(Integer::toString)
                        .collect(Collectors.joining()) + "\n" +
               Arrays.stream(this.grid)
                     .map(String::new)
                     .collect(Collectors.joining("\n"));
    }

    /**
     * Prompts the user for what column they would like to add the X or O too.
     */
    public void chooseAndDrop(char symbol, Scanner input) {
        do {
            System.out.print("\nPlayer " + symbol + " turn: ");
            int col = input.nextInt();

            if (! (0 <= col && col < this.width)) {
                System.out.println("Column must be between 0 and " +
                                   (this.width - 1));
                continue;
            }
            for (int h = this.height - 1; h >= 0; h--) {
                if (this.grid[h][col] == '.') {
                    this.grid[this.lastTop=h][this.lastCol=col] = symbol;
                    return;
                }
            }

            System.out.println("Column " + col + " is full.");
        } while (true);
    }
  

    /**
     * Checks the row of the last chip played to see if a match was made.
     */
    private String horizontal() {
        return new String(this.grid[this.lastTop]);
    }

    /**
     * Checks the vertical column of the last chip just played to try and find a match.
     */
    private String vertical() {
        StringBuilder sb = new StringBuilder(this.height);
        for (int h = 0; h < this.height; h++) {
            sb.append(grid[h][lastCol]);
        }
        return sb.toString();
    }
    
    public String Diagonal() {
    	StringBuilder reader = new StringBuilder(height);
    	for (int a = 0; a < height; a++) {
    		int b = lastCol + lastTop - a;
    		
    		if (0 <= b && b < width) {
    			reader.append(grid[a][b]);
    		}
    		
    	}
    	return reader.toString();
    }
    
    public String backDiagonal() {
    	StringBuilder reader = new StringBuilder(height);
    	
    	for (int a = 0; a < height; a++) {
    		int b = lastCol-lastTop + a;
    		
    		if (0 <= b && b < width) {
    			reader.append(grid[a][b]);
    		}
    	}
    	return reader.toString();
    }
    
    public static boolean contains(String str, String substring) {
    	return str.indexOf(substring) >=0;
    }
    
    public boolean checkForWin() {
    	if (lastCol == -1) {
    		System.out.println("No move has been made yet");
    		return false;
    	}
    	char sym = grid[lastTop][lastCol];
    	String winningSet = String.format("%c%c%c%c", sym,sym,sym,sym);
    	
    	return contains(horizontal(), winningSet) || contains(vertical(), winningSet) || contains(Diagonal(), winningSet) || contains(backDiagonal(), winningSet);
    	
    }

    public static void main(String[] args) {
    	//Try to create a scanner
    	//If able to, create the board.
        try (Scanner input = new Scanner(System.in)) {
        	//Make a 6x8 board, and in order to figure out the maximum number of moves multiple row x column.
        	//Create a connect four board object with the width and height as the parameters.
            int height = 6, width = 8, moves = height * width;
            ConnectFour board = new ConnectFour(width, height);
            
            //Display the rules on how to select a column.
            //Print the board.
            System.out.println("Use 0-" + (width - 1) + " to choose a column.");
            System.out.println(board);

            //Create a for loop with a player variable, and make sure that moves - 1 is still greater then 0.
            //This is to avoid moves from reaching the negative value, then subtract one from player. (Meaning Player 0 would go first).
            for (int player = 0; moves-- > 0; player = 1 - player) {
            //Assigns a player to the corresponding symbol. (X's and O's).
                char symbol = players[player];
                board.chooseAndDrop(symbol, input);
                System.out.println(board);
                
                //Check for a win
                if (board.checkForWin()) {
                	System.out.println("\n Player" + symbol + " wins!");
                	return;
                }
            }
            
           
           
            System.out.println("Game over, no winner.");
        }
    }
}
