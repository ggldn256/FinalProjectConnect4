import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A client for a multi-player tic tac toe game. Loosely based on an example in
 * Deitel and Deitel’s “Java How to Program” book. For this project I created a
 * new application-level protocol called TTTP (for Tic Tac Toe Protocol), which
 * is entirely plain text. The messages of TTTP are:
 *
 * Client -> Server MOVE <n> QUIT
 *
 * Server -> Client WELCOME <char> VALID_MOVE OTHER_PLAYER_MOVED <n>
 * OTHER_PLAYER_LEFT VICTORY DEFEAT TIE MESSAGE <text>
 */
public class ConnectFourClient {

	private Socket socket;
	private Scanner in;
	private PrintWriter out;

	public ConnectFourClient(String serverAddress) throws Exception {

		socket = new Socket(serverAddress, 58904);
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream(), true);

	}

	/**
	 * The main thread of the client will listen for messages from the server. The
	 * first message will be a "WELCOME" message in which we receive our mark. Then
	 * we go into a loop listening for any of the other messages, and handling each
	 * message appropriately. The "VICTORY", "DEFEAT", "TIE", and
	 * "OTHER_PLAYER_LEFT" messages will ask the user whether or not to play another
	 * game. If the answer is no, the loop is exited and the server is sent a "QUIT"
	 * message.
	 */
	public void play() throws Exception {
		try {
			Scanner input = new Scanner(System.in);
			int height = 6, width = 8;
			ConnectFour board = new ConnectFour(width, height);
			System.out.println(board);
			var response = in.nextLine();
			var mark = response.charAt(8);
			var opponentMark = mark == 'X' ? 'O' : 'X';
			int consoleResponse = -1;
			System.out.println("Welcome Player " + mark);
			out.println("READY");
			while (in.hasNextLine()) {
				response = in.nextLine();
				if (response.startsWith("CHOOSE_MOVE")) {
					System.out.println("Choose a column 0-7");
					consoleResponse = input.nextInt();
					out.println("MOVE " + consoleResponse);
					
					
					
				} else if (response.startsWith("VALID_MOVE")) {
					System.out.println("Valid move, please wait");
					board.chooseAndDrop(mark, consoleResponse);
					System.out.println(board);
					
					
					
				} else if (response.startsWith("OPPONENT_MOVED")) {
					var loc = Integer.parseInt(response.substring(15));
					board.chooseAndDrop(opponentMark, loc);
					System.out.println(board);
					System.out.println("Opponent moved, your turn");
					
					
					
				} else if (response.startsWith("MESSAGE")) {
					System.out.println(response.substring(8));
					
					
				} else if (response.startsWith("VICTORY")) {
					System.out.println("You Won!");
					break;
				} else if (response.startsWith("DEFEAT")) {
					System.out.println("You Lost!");
					break;
				} else if (response.startsWith("TIE")) {
					System.out.println("You tied");
					break;
				} else if (response.startsWith("OTHER_PLAYER_LEFT")) {
					System.out.println("The other player left.");
					break;
				}
			}
			out.println("QUIT");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			socket.close();

		}
	}

	public static void main(String[] args) throws Exception {

		ConnectFourClient client = new ConnectFourClient("localhost");
		client.play();
	}
}
