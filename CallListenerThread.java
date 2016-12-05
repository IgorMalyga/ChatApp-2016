
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

///////////////////////////// ���� ����������� ������ ��������

public class CallListenerThread implements Runnable {
    private ServerSocket s;
    private Socket incoming;
    private boolean status; // busy = false
    private String LocalLogin;

    public CallListenerThread(String LL) throws IOException {
        s = new ServerSocket(Protocole.getPort());
        LocalLogin=LL;
        Thread thr = new Thread(this);
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
                connection_request fr = new connection_request(incoming,LocalLogin);
            } catch (IOException e) {
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

}
