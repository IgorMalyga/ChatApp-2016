

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
	private String locLogin = "username";
	private CallListenerThread c;
	private Connection incoming;

	/**
	 * Launch the application.
	 */
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
		}
	}

	public class CallAction implements ActionListener {

		private String ip=null;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ip = textField_2.getText();
			if (ip != null) {
				Caller caller = new Caller(locLogin, ip);
				try {
					incoming = caller.call();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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

					incoming.disconnect();
					incoming = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	private void initialize() throws IOException {
		frmIteration = new JFrame();
		c = new CallListenerThread(locLogin);
		if (c.getIncoming() != null) {
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
		ActionListener call = new CallAction();
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

		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(8, 88, 505, 173);
		panel.add(scrollPane);
	}

}
