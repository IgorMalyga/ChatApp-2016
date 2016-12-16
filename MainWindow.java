package Chat;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.*;
import javax.swing.*;

public class MainWindow {

	private JFrame frmIteration;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private String locLogin = "username";
	private CallListenerThread c;
	private Connection incoming;
	private CommandListenerThread2 clt;
	private Thread command_listener_thread;
	private String RemoteLogin = "username";

	/**
	 * Launch the application.
	 */
	public void setConnection(Connection c) {
		incoming = c;
	}

	public static void main(String[] args1) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmIteration.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 *
	 * @throws IOException
	 */
	public MainWindow() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 *
	 * @throws IOException
	 */
	public class ApplyAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			locLogin = textField.getText();
			System.out.println(locLogin);
			c.setLocalLogin(locLogin);
			if (incoming!=null){
				try {
					incoming.sendnNickHello(locLogin);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public class CallAction implements ActionListener {
		MainWindow m;
		private String ip = null;

		public CallAction(MainWindow m) {
			this.m = m;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ip = textField_2.getText();
			if (ip != null) {
				Caller caller = new Caller(locLogin, ip, m);
				try {
					incoming = caller.call();
					setClt(new CommandListenerThread2(incoming));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			/*
			 * while (true) { if (clt.getLastCommand()!= null &&
			 * clt.getLastCommand().equals(Command.CommandType.MESSAGE)) {
			 * String message = incoming.getMessage();
			 * textArea.append(incoming.getNickHello() + ": " + message + "\n");
			 * } }
			 */
		}
	}

	public class DisconnectAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (incoming != null) {
				try {

					incoming.disconnect();
					incoming = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public class SendMessageAction implements ActionListener {
		 
		
		
		public void actionPerformed(ActionEvent e) {
			if (incoming==null){
				System.out.println("disconnected");
				return;
			}
			if (textField_4 != null) {
				String message = textField_4.getText();
				try {
					Calendar cal =Calendar.getInstance();
					Date d = cal.getTime();
					textArea.append(locLogin + ": " + message + "    "+d.getHours()+":"+d.getMinutes()+";\n");
					System.out.println(message);
					System.out.println(incoming);
					incoming.sendMessage(message);
					textField_4.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println(message);
				// scrollPane.;
				// textArea.append(incoming.getNickHello() + ": " + message +
				// "\n");
			}
		}
	}

	private void initialize() throws IOException {
		frmIteration = new JFrame();
		clt = new CommandListenerThread2();
		c = new CallListenerThread(locLogin, this);
		if (c.getIncoming() != null) {
			System.out.println("Connection ");
			incoming = new Connection(c.getIncoming());
		}

		frmIteration.setTitle("iteration1");
		frmIteration.setBounds(350, 200, 550, 350);
		frmIteration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmIteration.setResizable(false);

		JPanel panel = new JPanel();
		frmIteration.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(70, 22, 80, 20);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblLocalLogin = new JLabel("local login");
		lblLocalLogin.setBounds(8, 25, 66, 14);
		panel.add(lblLocalLogin);

		JButton btnNewButton = new JButton("Apply");
		btnNewButton.setBounds(160, 21, 66, 23);
		panel.add(btnNewButton);
		ActionListener Apply = new ApplyAction();
		btnNewButton.addActionListener(Apply);

		JButton btnNewButton_1 = new JButton("connect");
		btnNewButton_1.setBounds(435, 21, 89, 23);
		panel.add(btnNewButton_1);
		ActionListener call = new CallAction(this);
		btnNewButton_1.addActionListener(call);

		JButton btnNewButton_2 = new JButton("disconnect");
		btnNewButton_2.setBounds(435, 54, 89, 23);
		panel.add(btnNewButton_2);
		ActionListener dc = new DisconnectAction();
		btnNewButton_2.addActionListener(dc);

		textField_2 = new JTextField();
		textField_2.setBounds(335, 22, 86, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);

		JButton btnNewButton_3 = new JButton("send");
		btnNewButton_3.setForeground(Color.BLACK);
		btnNewButton_3.setBounds(435, 278, 89, 23);
		panel.add(btnNewButton_3);
		ActionListener sm = new SendMessageAction();
		btnNewButton_3.addActionListener(sm);

		textField_4 = new JTextField();
		textField_4.setBounds(8, 279, 385, 20);
		panel.add(textField_4);
		textField_4.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("remote ip");
		lblNewLabel_1.setBounds(248, 25, 87, 14);
		panel.add(lblNewLabel_1);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(8, 88, 489, 173);
		panel.add(textPane);

		// scrollPane = new JScrollPane(textPane);
		// scrollPane.setBounds(8, 88, 505, 173);
		// panel.add(scrollPane);
		textArea = new JTextArea();
		textArea.setBounds(8, 88, 505, 173);
		panel.add(textArea);

	}

	public CommandListenerThread2 getClt() {
		return clt;
	}

	public void setClt(CommandListenerThread2 clt) {
		this.clt = clt;
	}

	public String getRemoteLogin() {
		return RemoteLogin;
	}

	public void setRemoteLogin(String remoteLogin) {
		RemoteLogin = remoteLogin;
	}

	public class CommandListenerThread2 implements Runnable {
		private String lastCommand;
		private Command lastlastCommand = null;
		private Connection connection;
		private boolean isStopped = false;

		public CommandListenerThread2() {

		}

		public CommandListenerThread2(Connection con) {
			connection = con;
			Thread thr = new Thread(this);
			thr.start();
		}

		public void strtClt() {
			Thread thr = new Thread(this);
			thr.start();
		}

		@Override
		public void run() {
			while (true) {
				InputStream in;
				if (incoming != null) {
					try {
						in = incoming.getIncoming().getInputStream();
						BufferedReader i = new BufferedReader(new InputStreamReader(in));

						lastCommand = i.readLine();
						System.out.println("�s: message received");
						System.out.println("cs message: " + lastCommand);
						String[] arr = lastCommand.split(" ");
						System.out.println(arr);

						// if(lastCommand.equals(Command.CommandType.ACCEPT))
						if (arr[0].equals("NICK")) {
							
							System.out.println("received");
							System.out.println("NICK");
							setRemoteLogin(arr[1]);
							System.out.println(RemoteLogin);
						}

						if (arr[0].equals("MESSAGE")) {
							System.out.println("//�������� �����");
							Calendar cal = Calendar.getInstance();
							Date d =cal.getTime();
							
							textArea.append(getRemoteLogin() + ": ");
							for (int j = 1; j < arr.length; j++) {

								textArea.append(arr[j] + " ");
							}
							textArea.append("    " +d.getHours()+":"+d.getMinutes()+";\n");
						}
						if (arr[0].equals("DISCONNECT")) {
							incoming=null;
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}

		public boolean isDisconnected() {
			if (lastCommand.equals(Command.CommandType.DISCONNECT)) {
				return true;
			} else
				return false;
		}

		public void setStoppedStatus(boolean status) {
			isStopped = status;
		}

	}
}
