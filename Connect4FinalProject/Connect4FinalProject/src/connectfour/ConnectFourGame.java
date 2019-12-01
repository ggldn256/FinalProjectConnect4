package connectfour;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectFourGame {
    private static final char[] players = new char[] { 'X', 'O' };

    private final int width, height;
    private final char[][] grid;
    private int lastCol = -1, lastTop = -1;

    public ConnectFourGame(int width, int height) {
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
     * Prompts the user for a column, repeating until a valid
     * choice is made.
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
     * The contents of the row containing the last played chip.
     */
    private String horizontal() {
        return new String(this.grid[this.lastTop]);
    }

    /**
     * The contents of the column containing the last played chip.
     */
    private String vertical() {
        StringBuilder sb = new StringBuilder(this.height);
        for (int h = 0; h < this.height; h++) {
            sb.append(this.grid[h][this.lastCol]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            int height = 6, width = 8, moves = height * width;
            ConnectFourGame board = new ConnectFourGame(width, height);
            System.out.println("Use 0-" + (width - 1) + " to choose a column.");
            System.out.println(board);

            for (int player = 0; moves-- > 0; player = 1 - player) {
                char symbol = players[player];
                board.chooseAndDrop(symbol, input);
                System.out.println(board);
            }
            System.out.println("Game over, no winner.");
        }
    }
}
