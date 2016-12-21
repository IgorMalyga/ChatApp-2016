package Chat;

import javax.swing.*;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class NewJFrame extends javax.swing.JFrame {
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private javax.swing.JButton jButton7;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JList<String> jList1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private javax.swing.JTextField jTextField3;
	private ArrayList<contact> arr;
	private String locLogin = "username";
	private CallListenerThread c;
	private Connection incoming;
	private CommandListenerThread2 clt;
	private Thread command_listener_thread;
	private String RemoteLogin = "username";
	private String ip;
	String[] strings;
	private DefaultListModel dlm;
	private JLabel remLog;
	private JButton DeleteBut;

	public class ApplyAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// TODO Auto-generated method stub
			locLogin = jTextField2.getText();
			System.out.println(locLogin);
			c.setLocalLogin(locLogin);
			if (incoming != null) {
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
		NewJFrame m;

		public CallAction(NewJFrame newJFrame) {
			this.m = newJFrame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (incoming == null) {
				ip = jTextField1.getText();
				if (ip != null) {
					Caller caller = new Caller(locLogin, ip, m);
					try {
						incoming = caller.call();
						if (incoming != null) {
							getjButton5().setEnabled(true);
							getjButton1().setEnabled(true);
							getjButton2().setEnabled(false);
							jButton4.setEnabled(false);
						}
						setClt(new CommandListenerThread2(incoming));

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
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
							remLog.setText("   Rem login: " + RemoteLogin);
						}
						if (arr[0].equals("REJECT")) {
	       jButton1.setEnabled(false);
	       getjButton2().setEnabled(true);
	       jButton4.setEnabled(true);
	       

							Reject_Message rm = new Reject_Message();

						}

						if (arr[0].equals("MESSAGE")) {
							System.out.println("//�������� �����");
							Calendar cal = Calendar.getInstance();
							Date d = cal.getTime();

							getjTextArea1().append(getRemoteLogin() + ": ");
							for (int j = 1; j < arr.length; j++) {

								getjTextArea1().append(arr[j] + " ");
							}
							getjTextArea1().append("    " + d.getHours() + ":" + d.getMinutes() + ";\n");
						}
						if (arr[0].equals("DISCONNECT")) {
							incoming = null;
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}


	public class DisconnectAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (incoming != null) {
				try {
					RemoteLogin = "username";
					getjTextArea1().setText("");
					incoming.disconnect();
					incoming = null;
					getjButton5().setEnabled(false);
					getjButton2().setEnabled(true);
					jButton4.setEnabled(true);
					jButton1.setEnabled(false);
					remLog.setText("   Rem login:" + RemoteLogin);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public class WriteAction implements ActionListener {
		NewJFrame m;

		public WriteAction(NewJFrame njf) {
			m = njf;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (incoming == null) {
				for (int i = 0; i < arr.size(); i++) {
					if (jList1.isSelectedIndex(i)) {
						Caller caller = new Caller(locLogin, arr.get(i).getIp(), m);
						try {
							incoming = caller.call();
							if (incoming != null) {
								getjButton1().setEnabled(true);
								getjButton2().setEnabled(false);
								jButton4.setEnabled(false);
							}
							setClt(new CommandListenerThread2(incoming));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}

	}

	public class plusAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			contact cont = new contact(RemoteLogin, ip);
			if (arr.contains(cont) == false) {
				if (incoming != null) {
					// TODO Auto-generated method stub
					arr.add(cont);
					dlm.addElement(arr.get(arr.size() - 1).getName());
					/*
					 * ObjectOutputStream ois = null; try { FileOutputStream fos
					 * = new FileOutputStream("d:\\Contacts.txt"); ois = new
					 * ObjectOutputStream(fos);
					 * 
					 * ois.writeObject(cont);
					 * 
					 * } catch (FileNotFoundException e) { // TODO
					 * Auto-generated catch block e.printStackTrace(); } catch
					 * (IOException e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); }
					 */
					String[] arr1 = new String[arr.size()];
					for (int i = 0; i < arr.size(); i++) {
						arr1[i] = arr.get(i).getName();
					}

					strings = arr1;
					System.out.println(strings[0]);
					jList1.repaint();
				}
			}
		}

	}

	public class SendMessageAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (incoming == null) {
				getjTextArea1().append("Disconnected;\n");
				jTextField3.setText("");
				return;
			}
			if (jTextField3 != null) {
				String message = jTextField3.getText();
				try {
					Calendar cal = Calendar.getInstance();
					Date d = cal.getTime();
					getjTextArea1()
							.append(locLogin + ": " + message + "    " + d.getHours() + ":" + d.getMinutes() + ";\n");
					System.out.println(message);
					System.out.println(incoming);
					incoming.sendMessage(message);
					jTextField3.setText("");
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

	/**
	 * Creates new form NewJFrame
	 */
	public NewJFrame() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {
		jList1 = new JList();
		jList1.setFixedCellHeight(30);
		arr = new ArrayList<contact>();

		/** Блок для считывания контактов */
		int counter = 0;
		String name = "", ip = "";

		File file = new File("ContactsList.txt");
		Scanner scFile = null;
		try {
			scFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scFile.hasNext()) {
			// if(scFile.next().compareTo("@") != 0){
			counter++;// }
			if (counter == 1) {
				name = scFile.next();
			}
			if (counter == 2) {
				ip = scFile.next();
				contact tmp_cont = new contact(name, ip);
				counter = 0;
				arr.add(tmp_cont);
			}
		}
		scFile.close();
		dlm = new DefaultListModel();
		jList1.setModel(dlm);
		for (int i = 0; i < arr.size(); i++) {
			dlm.addElement(arr.get(i).getName());
		}
		/**
		 * Конец блока для считывания контактов
		 */

		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				/** Блок для считывания контактво */
				FileWriter writer = null;
				try {
					writer = new FileWriter("ContactsList.txt");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				for (int i = 0; i < arr.size(); i++) {
					try {
						writer.write(arr.get(i).getName() + ' ' + arr.get(i).getIp() + ' ');
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				try {
					writer.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				/**
				 * Окончание блока записи контактов
				 */
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
		});

		String[] strings = new String[100];

		// arr = new ArrayList<contact>();

		clt = new CommandListenerThread2();

		try {
			c = new CallListenerThread(locLogin, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		jButton4 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jScrollPane2 = new javax.swing.JScrollPane();
		// jList1 = new javax.swing.JList<>();

		jTextField1 = new javax.swing.JTextField();
		jTextField2 = new javax.swing.JTextField();
		jTextField3 = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jButton7 = new javax.swing.JButton();
		DeleteBut = new javax.swing.JButton();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		DeleteBut.setText("delete");
		jButton4.setText("write");
		WriteAction wa = new WriteAction(this);
		jButton4.addActionListener(wa);

		jButton5.setText("+");
		jButton5.setEnabled(false);
		plusAction pa = new plusAction();
		jButton5.addActionListener(pa);

		jButton6.setText("send");
		SendMessageAction sm = new SendMessageAction();
		jButton6.addActionListener(sm);

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);
		jScrollPane2.setViewportView(jList1);

		jTextField2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField2ActionPerformed(evt);
			}
		});

		jTextField3.setText("");

		jLabel1.setText("Local login");

		jButton1.setText("disconnect");
		DisconnectAction da = new DisconnectAction();
		jButton1.addActionListener(da);
		jButton1.setEnabled(false);

		jButton2.setText("connect");
		CallAction ca = new CallAction(this);
		jButton2.addActionListener(ca);

		jLabel2.setText("Friend ip");
		jLabel3.setText("Contacts");
		jButton7.setText("apply");
		ApplyAction aa = new ApplyAction();
		jButton7.addActionListener(aa);
		remLog = new JLabel();
		remLog.setText("Rem login: " + RemoteLogin);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addGroup(layout.createSequentialGroup().addComponent(jTextField3)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(jButton6)).addComponent(jScrollPane1,
														javax.swing.GroupLayout.PREFERRED_SIZE, 282,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addComponent(jButton4)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jButton5))
										.addComponent(jScrollPane2)))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addComponent(jLabel1)
												.addGap(18, 18, 18)
												.addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 139,
														javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(51, 51, 51)
										.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 129,
												javax.swing.GroupLayout.PREFERRED_SIZE).addGap(13, 13, 13))
										.addGroup(layout.createSequentialGroup().addComponent(jButton7)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jLabel3).addGap(134, 134, 134)))
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
				.addGap(22, 22, 22)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel1).addComponent(jButton1).addComponent(jLabel2))
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jButton2))
						.addGroup(layout.createSequentialGroup().addGap(25, 25, 25).addComponent(jLabel3))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup()
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jButton7)))
				.addGap(6, 6, 6)
				.addGroup(
						layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton6)
						.addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jButton4).addComponent(jButton5)).addGap(37, 37, 37)));

		pack();
		// this.setLayout(null);
		this.add(remLog);
		remLog.setBounds(80, 50, 140, 25);
	}// </editor-fold>

	private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new NewJFrame().setVisible(true);
			}
		});
	}

	public void setConnection(Connection c2) {
		// TODO Auto-generated method stub
		this.incoming = c2;
	}

	public javax.swing.JTextArea getjTextArea1() {
		return jTextArea1;
	}

	public void setjTextArea1(javax.swing.JTextArea jTextArea1) {
		this.jTextArea1 = jTextArea1;
	}

	public javax.swing.JButton getjButton1() {
		return jButton1;
	}

	public void setjButton1(javax.swing.JButton jButton1) {
		this.jButton1 = jButton1;
	}

	public javax.swing.JButton getjButton5() {
		return jButton5;
	}

	public void setjButton5(javax.swing.JButton jButton5) {
		this.jButton5 = jButton5;
	}

	public javax.swing.JButton getjButton2() {
		return jButton2;
	}

	public void setjButton2(javax.swing.JButton jButton2) {
		this.jButton2 = jButton2;
	}

	public javax.swing.JButton getjButton4() {
		return jButton4;
	}



	// Variables declaration - do not modify

	// End of variables declaration
}
