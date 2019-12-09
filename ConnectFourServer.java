package connectfourgame;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.HashSet;;

public class ConnectFourServer {

// The list of all the print writers for the clients.
	private static Set<PrintWriter> placeholder = new HashSet<>();
	private static ArrayList<PrintWriter> writers = new ArrayList<>();
	private static final char[] players = new char[] { 'X', 'O' };

	public static void main(String[] args) throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(500);
		try (ServerSocket listener = new ServerSocket(59003)) {
			while (true) {
				pool.execute(new Handler(listener.accept()));

			}
		}
	}

	/**
	 * The client handler task.
	 */
	private static class Handler implements Runnable {
		private Socket socket;
		private Scanner in;
		private PrintWriter out;

		public Handler(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				in = new Scanner(socket.getInputStream());
				out = new PrintWriter(socket.getOutputStream(), true);
				
				boolean playAgain = true;

				System.out.println("PrintWriter created");
				placeholder.add(out);


				
				System.out.println(placeholder.size());
				while (placeholder.size() != 2) {
				
					
				}
				if (placeholder.size() == 2) {
					System.out.println("if statement works");
					for (PrintWriter writer : placeholder) {
						System.out.println(writer);
						writer.println("2 players joined starting game...");

					}
				
					while (playAgain == true) {

						try (Scanner input = new Scanner(System.in)) {
							// Make a 6x8 board, and in order to figure out the maximum number of moves
							// multiply row x column.
							// Create a connect four board object with the width and height as the
							// parameters.
							int height = 6, width = 8, moves = height * width;
							ConnectFour board = new ConnectFour(width, height);

							System.out.println("Board created.");
							
							for (PrintWriter writer : placeholder) {
								writers.add(writer);
							}

							for (int player = 0; moves-- > 0; player = 1 - player) {
								// Assigns a player to the corresponding symbol. (X's and O's).
								char symbol = players[player];
								PrintWriter x = writers.get(0);
								PrintWriter o = writers.get(1);
								if (symbol == 'X') {
									System.out.println("test");
									
									x.println("Choose a column 0-" + (width - 1));
									x.println(board);
									int col = in.nextInt();
									System.out.println(col);
									board.chooseAndDrop(symbol, col);
									x.println(board);
									if (board.checkForWin()) {
										for (PrintWriter writer : writers) {
											writer.println("Player 1 wins!");
										}
										x.println("Would you like to play again?(y/n)");
										String xResponse = in.next();
										o.println("Would you like to play again?(y/n)");
										String oResponse = in.next();
										if (xResponse == "n" || oResponse == "n") {
											playAgain = false;
										}
									} else {
										x.println("Waiting for player 2 to choose...");
									}

								} else if (symbol == 'O') {
									
									o.println("Choose a column 0-" + (width - 1));
									o.println(board);
									int col = in.nextInt();
									System.out.println(col);
									System.out.println("test");
									board.chooseAndDrop(symbol, col);
									o.println(board);
									if (board.checkForWin()) {
										for (PrintWriter writer : writers) {
											writer.println("Player 2 wins!");
										}
										x.println("Would you like to play again?(y/n)");
										String xResponse = in.next();
										o.println("Would you like to play again?(y/n)");
										String oResponse = in.next();
										if (xResponse == "n" || oResponse == "n") {
											playAgain = false;
										}
									} else {
										o.println("Waiting for player 1 to choose...");
									}
								}

							}
						}
					}

				}else {
					
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				if (out != null) {
					writers.remove(out);
				}
			
				try {
					socket.close();
				} catch (IOException e) {
				}
			}

		}
	}
}
