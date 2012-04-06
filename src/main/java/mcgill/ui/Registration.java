package mcgill.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Registration {

	private JFrame registration;
	private JTextField textUsername;
	private JTextField textPassword;
	private JTextField textRePassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registration window = new Registration();
					window.registration.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Registration() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		registration = new JFrame();
		registration.setTitle("Registration Page");
		registration.setBounds(100, 100, 450, 274);
		registration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		registration.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(48dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(74dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("57dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblUsername = new JLabel("Username:");
		registration.getContentPane().add(lblUsername, "6, 4, 3, 1, left, default");
		
		textUsername = new JTextField();
		registration.getContentPane().add(textUsername, "10, 4, 3, 1, fill, default");
		textUsername.setColumns(10);
		
		JLabel lblUsernameHelp = new JLabel("Please Enter a Username between 3 and 12 charactors");
		lblUsernameHelp.setForeground(Color.GRAY);
		registration.getContentPane().add(lblUsernameHelp, "8, 6, 5, 1");
		
		JLabel lblUsernameWarning = new JLabel("That Username is already in use!");
		lblUsernameWarning.setVisible(false);
		lblUsernameWarning.setForeground(Color.RED);
		registration.getContentPane().add(lblUsernameWarning, "8, 8, 5, 1, center, default");
		
		JLabel lblPassword = new JLabel("Password:");
		registration.getContentPane().add(lblPassword, "6, 10, 3, 1, left, default");
		
		textPassword = new JTextField();
		registration.getContentPane().add(textPassword, "10, 10, 3, 1");
		textPassword.setColumns(10);
		
		JLabel lblRetypePassword = new JLabel("Re-type Password:");
		registration.getContentPane().add(lblRetypePassword, "6, 12, 3, 1, left, default");
		
		textRePassword = new JTextField();
		registration.getContentPane().add(textRePassword, "10, 12, 3, 1, fill, default");
		textRePassword.setColumns(10);
		
		JLabel lblPassHelp = new JLabel("Please Eneter a Password between 6 and 12 charactors");
		lblPassHelp.setForeground(Color.GRAY);
		registration.getContentPane().add(lblPassHelp, "8, 14, 5, 1");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel lblThePasswordsDo = new JLabel("The Passwords do not match!");
		lblThePasswordsDo.setForeground(Color.RED);
		lblThePasswordsDo.setVisible(false);
		registration.getContentPane().add(lblThePasswordsDo, "8, 16, 5, 1, center, default");
		registration.getContentPane().add(btnCancel, "8, 18");
		
		JButton btnRegister = new JButton("Register");
		registration.getContentPane().add(btnRegister, "12, 18");
	}

}
