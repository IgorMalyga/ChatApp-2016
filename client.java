
import java.io.*;
import java.net.*;
import java.util.Scanner;

class client implements Runnable {

	public static void main(String args[]) throws IOException, Throwable {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter servers ip");
		String ip = sc.next();
		client cl = new client(ip);
		Thread t1 = new Thread(cl);
		t1.start();
	}

	private Socket s;

	public client(String ip) throws Throwable, IOException {
		s = new Socket(ip, 8112);
		System.out.println("client : is alive");
	}

	public void run() {
		while (true) {

			try {
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter your message");
				String st = sc.nextLine();
				PrintWriter r = new PrintWriter(s.getOutputStream());
				r.println(Command.CommandType.MESSAGE.toString()+" sdffscsdf " + " 0x0a");
				r.flush();
				System.out.println("client : message sent");
				
				InputStream in = s.getInputStream();
				BufferedReader i = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String str = i.readLine();
				System.out.println("client : message received");

				System.out.println("client : message: �" + str+"�");

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
