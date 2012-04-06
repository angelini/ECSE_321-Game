package mcgill.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JButton;


public class LoginError {

	private JFrame frmAttention;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginError window = new LoginError();
					window.frmAttention.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginError() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAttention = new JFrame();
		frmAttention.setResizable(false);
		frmAttention.setTitle("ATTENTION!");
		frmAttention.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginError.class.getResource("/javax/swing/plaf/metal/icons/Warn.gif")));
		frmAttention.setBounds(100, 100, 235, 144);
		frmAttention.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAttention.getContentPane().setLayout(null);
		
		JLabel lblYouHaveIncorrectly = new JLabel("You have incorrectly entered your");
		lblYouHaveIncorrectly.setBounds(35, 25, 165, 14);
		frmAttention.getContentPane().add(lblYouHaveIncorrectly);
		
		JButton btnNewButton = new JButton("Okay");
		btnNewButton.setBounds(75, 64, 89, 23);
		frmAttention.getContentPane().add(btnNewButton);
		
		JLabel lblEmailpassword = new JLabel("Email/Password, please try again.");
		lblEmailpassword.setBounds(37, 39, 165, 14);
		frmAttention.getContentPane().add(lblEmailpassword);
	}

}
