package Chat;

//////////////////main 

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class chat {
	public static void main(String[] args) throws Throwable {
		
		Protocole p = new Protocole();
		//Socket s=new Socket();
	    CallListener c = new CallListener(p.getPort());
		Thread callthr = new Thread(c);
		callthr.start();
	    Receiver ch = new Receiver(c.getS1());
 		Sender snd = new Sender(c.getS1());
	}
}
