package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Connection {

	private Socket incoming;
	private PrintWriter out;
	private Scanner in;
	private boolean readed = false;

	public Connection(Socket s1) {
		this.setIncoming(s1);
		InputStream inputStream;
		try {
			inputStream = getIncoming().getInputStream();
			OutputStream outputStream = getIncoming().getOutputStream();
			out = new PrintWriter(outputStream, true);
			in = new Scanner(inputStream);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendnNickHello(String nick) throws IOException {
		// InputStream inputStream = incoming.getInputStream();
		out.println("NICK " + nick);
	}

	public void sendBusy(String nick) throws IOException {
		out.println(Command.CommandType.NICK.toString() + "Sorry, user " + nick + " is busy" + " 0x0a");
	}

	public void sendMessage(String message) throws IOException {
		// out.println(Command.CommandType.MESSAGE.toString());
		out.println("MESSAGE " + message);
	}

	public void sendReject() throws IOException {
		// out.println(Command.CommandType.MESSAGE.toString());
		out.println("REJECT ");
	}

	public void disconnect() throws IOException {
		out.println("DISCONNECT");
		out.flush();
	}

	public void close() throws IOException {
		getIncoming().close();
	}

	public Socket getIncoming() {
		return incoming;
	}

	public void setIncoming(Socket incoming) {
		this.incoming = incoming;
	}
}
