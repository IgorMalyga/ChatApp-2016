package Chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;

import Chat.MainWindow.CommandListenerThread2;
import laba5.tour;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Max
 */
public class NewJFrame extends javax.swing.JFrame {
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton connect;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JList<String> jList1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private ArrayList<contact> arr;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private javax.swing.JTextField jTextField3;
	private String locLogin = "username";
	private CallListenerThread c;
	private Connection incoming;
	private CommandListenerThread2 clt;
	private Thread command_listener_thread;
	private String RemoteLogin = "username";
	private String ip;
	String[] strings;

	/**
	 * Creates new form NewJFrame2
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public NewJFrame() throws IOException, ClassNotFoundException {
		initComponents();
	}

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
			ip = jTextField3.getText();
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
						System.out.println("Òs: message received");
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
							System.out.println("//œËÌˇÚÓÂ ÒÓÓ·˘");
							Calendar cal = Calendar.getInstance();
							Date d = cal.getTime();

							jTextArea1.append(getRemoteLogin() + ": ");
							for (int j = 1; j < arr.length; j++) {

								jTextArea1.append(arr[j] + " ");
							}
							jTextArea1.append("    " + d.getHours() + ":" + d.getMinutes() + ";\n");
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

		/*
		 * while (true) { if (clt.getLastCommand()!= null &&
		 * clt.getLastCommand().equals(Command.CommandType.MESSAGE)) { String
		 * message = incoming.getMessage();
		 * textArea.append(incoming.getNickHello() + ": " + message + "\n"); } }
		 */
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

	public class plusAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			contact cont = new contact(RemoteLogin, ip);
			if (arr.contains(cont) == false) {
				if (incoming != null) {
					// TODO Auto-generated method stub
					arr.add(cont);
					/*ObjectOutputStream ois = null;
					try {
						FileOutputStream fos = new FileOutputStream("d:\\Contacts.txt");
						ois = new ObjectOutputStream(fos);

						ois.writeObject(cont);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					String[] arr1 = new String[arr.size()];
					for (int i = 0; i < arr.size(); i++) {
						arr1[i] = arr.get(i).getName();
					}

					strings = arr1;
					System.out.println(strings);
					jList1.repaint();
				}
			}
		}

	}

	public class SendMessageAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (incoming == null) {
				System.out.println("disconnected");
				return;
			}
			if (jTextField3 != null) {
				String message = jTextField1.getText();
				try {
					Calendar cal = Calendar.getInstance();
					Date d = cal.getTime();
					jTextArea1.append(locLogin + ": " + message + "    " + d.getHours() + ":" + d.getMinutes() + ";\n");
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
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
/*	public void getContacts() throws IOException, ClassNotFoundException {
		File f1 = new File("d:\\Contacts.txt");
		File f2 = new File("d:\\count.txt");
		
		FileInputStream fis = new FileInputStream(f2);
		ObjectInputStream ois = new ObjectInputStream(fis);
		int a=ois.readInt();
	    fis = new FileInputStream(f1);
		ois = new ObjectInputStream(fis);
		for (int i = 0; i <a ; i++) {
			arr.add((contact) ois.readObject());
		}
	}*/

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() throws IOException, ClassNotFoundException {
		arr = new ArrayList();
		/*this.getContacts();
		strings = new String[20];
		for (int i = 0; i < arr.size(); i++) {
			strings[i] = arr.get(i).getName();
		}
		System.out.println("arr" + arr);*/
		clt = new CommandListenerThread2();
		c = new CallListenerThread(locLogin, this);
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jTextField1 = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();
		jTextField2 = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jButton2 = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		jTextField3 = new javax.swing.JTextField();
		jButton3 = new javax.swing.JButton();
		jScrollPane2 = new javax.swing.JScrollPane();
		jList1 = new JList(strings);
		jButton5 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		connect = new JButton();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		jList1.setFixedCellHeight(30);
		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);
		SendMessageAction sa = new SendMessageAction();
		strings = new String[20];
		jButton1.setText("Send");
		jButton1.addActionListener(sa);

		jLabel1.setText("Local login");
		ApplyAction aa = new ApplyAction();
		jButton2.setText("Apply");
		jButton2.addActionListener(aa);

		jLabel2.setText("Friend ip");
		DisconnectAction da = new DisconnectAction();
		jButton3.setText("Disconnect");
		jButton3.addActionListener(da);
		jList1.setModel(new javax.swing.AbstractListModel<String>() {
			
			public int getSize() {
				return strings.length;
			}
            
			public String getElementAt(int i) {
				return strings[i];
				
			}
		});
		// jList1.
		jScrollPane2.setViewportView(jList1);

		CallAction ca = new CallAction(this);
		plusAction pa = new plusAction();
		jButton5.setText("+");
		jButton5.addActionListener(pa);
		jButton4.setText("Write");
		jButton4.addActionListener(ca);
		jButton4.setToolTipText("");

		jLabel3.setText("Contacts");

		this.addWindowListener(new WindowListener() {
			// ...
			public void windowClosing1(WindowEvent event) {

				// [“”“ “¬Œ» ƒ≈…—“¬»ﬂ œŒ «¿ –€“»ﬁ]

			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {

			}

			@Override
			public void windowClosing(WindowEvent arg0) {		
				System.exit(0);
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup().addGap(29, 29, 29)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addComponent(jLabel1).addGap(18, 18, 18)
										.addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 119,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(90, 90, 90).addComponent(jLabel2).addGap(18, 18, 18)
										.addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 113,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jButton3))
								.addGroup(layout.createSequentialGroup()
										.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 87,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(153, 153, 153))))
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addGroup(layout.createSequentialGroup()
												.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 226,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296,
										javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(31, 31, 31)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel3)
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.PREFERRED_SIZE, 204,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(layout.createSequentialGroup().addComponent(jButton5)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jButton4))))))
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(29, 29, 29)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel1).addComponent(jLabel2)
						.addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jButton3))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addComponent(jButton2).addGap(19, 19, 19))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addComponent(jLabel3).addGap(11, 11, 11)))
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jButton1).addComponent(jButton5).addComponent(jButton4))
				.addContainerGap(25, Short.MAX_VALUE)));
		pack();
	}// </editor-fold>

	public class ButAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			jTextArea1.append("wefsdfsdgsgg");
		}

	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
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
				try {
					try {
						new NewJFrame().setVisible(true);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void setConnection(Connection c2) {
		// TODO Auto-generated method stub
		this.incoming = c2;
	}

	// Variables declaration - do not modify

	// End of variables declaration
}
