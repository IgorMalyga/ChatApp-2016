package Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

//////////////////отправка сообщений

public class Sender implements Runnable {

	private Socket s;
	private Thread thr;

	public Sender(Socket s) {
		this.s = s;
	    thr = new Thread(this);
		thr.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter your message ");
			String st = sc.nextLine();
			PrintWriter r;
			try {
				r = new PrintWriter(s.getOutputStream());
				r.println(st);
				r.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             try {
				thr.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
