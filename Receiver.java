package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;



//////////////////////////////////отвечает за прием сообщений



public class Receiver implements Runnable {

	private Socket s;
	private Thread thr;
    public Receiver(Socket s){
    	this.s=s;
        thr = new Thread(this);
    	thr.start();
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				InputStream in = s.getInputStream();
				BufferedReader i = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String str = i.readLine();
				System.out.println("client of chat: message received");
				System.out.println("client of chat: message: «" + str + "»");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
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
