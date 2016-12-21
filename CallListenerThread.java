package Chat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

///////////////////////////// ���� ����������� ������ ��������

public class CallListenerThread implements Runnable {
    private ServerSocket s;
    private Socket incoming;
    private boolean status; // busy = false
    private String LocalLogin;
    private String remoteLogin;
    NewJFrame m ;
    Connection c;
	private Thread thr;

    public CallListenerThread(String LL,NewJFrame NewJFrame) throws IOException {
        this.m=NewJFrame;
    	s = new ServerSocket(Protocole.getPort());
        LocalLogin=LL;
        thr= new Thread(this);
        thr.start();
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Socket getIncoming() {
        return incoming;
    }

    public void run() {
        while (true) {

            try {
                incoming = s.accept();
                c=new Connection(incoming);
                System.out.println(c);
                this.m.setConnection(c);
                this.m.getClt().strtClt();
                if (m.getRemoteLogin().equals("username")){
                	thr.sleep(10);
                    System.out.println(remoteLogin);
                }
                System.out.println(m.getRemoteLogin()+"!");  
                connection_request fr = new connection_request(incoming,m.getRemoteLogin(),this,m);
                if (incoming!=null){
                	this.m.getjButton1().setEnabled(true);
                	this.m.getjButton5().setEnabled(true);
                }
                
                /*Connection con = new Connection(incoming);
                CommandListenerThread clt = new CommandListenerThread(con);*/
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }

    }

    public String getLocalLogin() {
        return LocalLogin;
    }

    public void setLocalLogin(String localLogin) {
        this.LocalLogin = localLogin;
    }

	public void setConnection(Connection c2) {
		// TODO Auto-generated method stub
		this.c=c2;
	}

}
