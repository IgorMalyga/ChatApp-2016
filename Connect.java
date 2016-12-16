

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Connect  {
Object m;	
	public Socket connect (String ip) throws Throwable, IOException {
		Socket s = new Socket(ip, 8112);
		System.out.println("chat : is alive");
		return s;
	}
/*	public void run() {
		// TODO Auto-generated method stub
	    System.out.println("Enter friends ip");
	    Scanner sc = new Scanner(System.in);
	    String ip=sc.next();
	    try {
			connect(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			m.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}
