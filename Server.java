

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.net.*;

class Server implements Runnable {
	private ServerSocket s;

	public Server() throws IOException {
		s = new ServerSocket(8112);
		System.out.println("server: is alive");
	}

	public static void main(String[] args) throws IOException {
		Server ser = new Server();
		Thread t = new Thread(ser);
		t.start();
	}

	public void stop() throws InterruptedException {
		System.out.println("server: is dead");
		this.stop();
	}
	public void chat(Socket incoming) throws IOException{
		while (true){
			InputStream in = incoming.getInputStream();
			BufferedReader i = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
			String str = i.readLine();
			System.out.println("server: message received");
			System.out.println("server: message: �" + str+"�");
			

            
			System.out.println("server: message sent");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (true) {
			try {
				Socket incoming = s.accept();
				System.out.println("server: client accepted");
                this.chat(incoming);
 
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
