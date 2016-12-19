

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Caller {
	private String loc_name;
	private String ip;
	private String rem_name; 
	private Object obj; 
						
	private NewJFrame m;

	public Caller(String loc_n, String ip, NewJFrame m2) {
		loc_name = loc_n;
		this.ip = ip;
		this.m = m2;
	}

	public Connection call() throws IOException {
		Socket s = null;
		try {
			s = new Socket(ip, Protocole.getPort());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.m.getjTextArea1().append("Incorrect IP/User offline;\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.m.getjTextArea1().append("Incorrect IP/User offline;\n");
			e.printStackTrace();
		}
		Connection c = new Connection(s);
		c.sendnNickHello(loc_name);
        m.setConnection(c);
		return c;
	}
}
