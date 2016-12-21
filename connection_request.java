package Chat;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.SwingConstants;
import javax.swing.JList;

public class connection_request  {

    private JFrame frmIteration;
    private Connection c;
    private String localLogin;
    private Socket s;
    private String rem_login="username";
    CallListenerThread clt;
    NewJFrame m;
    /**
     * Launch the application.
     */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					connection_request window = new connection_request(s);
					window.frmIteration.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

    /**
     * Create the application.
     */
    public connection_request(Socket s1,String log,CallListenerThread clt,NewJFrame m) throws IOException {
        this.clt=clt;
        System.out.println(log+"dop form got login");
    	rem_login=log;
        s=s1;
        initialize();
        this.frmIteration.setVisible(true);
        localLogin=clt.getLocalLogin();
        this.m=m;
    }
    public class AcceptAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            try {
                c=new Connection(s);
                c.sendnNickHello( localLogin );
                clt.setConnection(c);
                //rem_login=c.getNickHello();
                frmIteration.dispose();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public class DeclineAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	try {
				c=new Connection(s);
				  m.getjButton1().setEnabled(false);
	                m.getjButton4().setEnabled(true);
	                m.getjButton2().setEnabled(true);
				c.sendReject();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	clt.m.setConnection(null);
			frmIteration.dispose();
        }

    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmIteration = new JFrame();
        frmIteration.setTitle("Iteration1");
        frmIteration.setResizable(false);
        frmIteration.setBounds(400, 250, 363, 226);
        frmIteration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frmIteration.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JButton btnNewButton = new JButton("Accept");
        btnNewButton.setBounds(83, 116, 89, 23);
        panel.add(btnNewButton);
        AcceptAction accept=new AcceptAction();
        btnNewButton.addActionListener(accept);

        JButton btnNewButton_1 = new JButton("Decline");
        btnNewButton_1.setBounds(182, 116, 89, 23);
        panel.add(btnNewButton_1);
        DeclineAction dec=new DeclineAction();
        btnNewButton_1.addActionListener(dec);

        JLabel lblUserXxxxWants = new JLabel("User "+rem_login+" wants to chat");
        lblUserXxxxWants.setHorizontalAlignment(SwingConstants.CENTER);
        lblUserXxxxWants.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUserXxxxWants.setBounds(83, 55, 188, 31);
        panel.add(lblUserXxxxWants);
    }
}
