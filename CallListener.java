package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

///////////////////////////// ждет подключения других клиентов

public class CallListener implements Runnable {
	private ServerSocket s;
	private Socket s1;

	public CallListener(int port/*Socket s1*/) throws IOException {
		s = new ServerSocket(port);
		s1 = s.accept();
		System.out.println("server: is alive");
	}
   
	public void acceptC() {
		while (true) {
			try {
				Socket s1 = null;
				if (s1 == null) {
					s1 = s.accept();
					System.out.println("server: client accepted");
				} else {
					System.out.println("server: it is busy");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.acceptC();
	}

	public Socket getS1() {
		return s1;
	}

	public void setS1(Socket s1) {
		this.s1 = s1;
	}
}
