

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Reject_Message {

	private JFrame frmChatapp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reject_Message window = new Reject_Message();
					window.frmChatapp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Reject_Message() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private class rejectAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			frmChatapp.dispose();
		}
		
	}
	private void initialize() {
		frmChatapp = new JFrame();
		frmChatapp.setTitle("ChatApp");
		frmChatapp.setResizable(false);
		frmChatapp.setBounds(500,200, 375, 226);
		frmChatapp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmChatapp.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);		
		JButton btnNewButton = new JButton("ok");
		rejectAction ra = new rejectAction();
		btnNewButton.setBounds(135, 108, 89, 23);
		btnNewButton.addActionListener(ra);
		panel.add(btnNewButton);
		JLabel lblYourCallRejected = new JLabel("         Your call rejected");
		lblYourCallRejected.setForeground(Color.RED);
		lblYourCallRejected.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblYourCallRejected.setBounds(63, 35, 211, 42);
		panel.add(lblYourCallRejected);
	}
}
