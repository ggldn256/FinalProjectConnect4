package connectfourgame;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connect4Client {

	String serverAddress;

	Scanner in;
	PrintWriter out;
	

	/**
	 * Constructs the client with the serverAddress
	 */
	public Connect4Client(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	private void start() throws IOException {
		try {
			@SuppressWarnings("resource")
			Socket socket = new Socket(serverAddress, 59003);
			Scanner consoleInput = new Scanner(System.in);
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);

			// Start sending thread
			SendingHandler sendingHandler = new SendingHandler(consoleInput, out);
			Thread sendingThread = new Thread(sendingHandler);
			sendingThread.start();

			// Start receiving thread
			ReceivingHandler receivingHandler = new ReceivingHandler(consoleInput, in);
			Thread receivingThread = new Thread(receivingHandler);
			receivingThread.start();
			System.out.println("Socket works.");

		} finally {
		}
	}

	public static void main(String[] args) throws Exception {
		Connect4Client client = new Connect4Client("localhost");
		client.start();
	}

	class SendingHandler implements Runnable {
		Scanner consoleInput;
		PrintWriter out;

		SendingHandler(Scanner consoleInput, PrintWriter out) {
			this.consoleInput = consoleInput;
			this.out = out;
		}

		@Override
		public void run() {
			String msg = "";
			msg = consoleInput.nextLine();
			out.println(msg);

		}
	}

	class ReceivingHandler implements Runnable {
		Scanner consoleInput;
		Scanner in;

		ReceivingHandler(Scanner consoleInput, Scanner in) {
			this.consoleInput = consoleInput;
			this.in = in;
		}

		@Override
		public void run() {
			while (in.hasNext()) {
				System.out.println(in.next());

			}
		}
	}
}
