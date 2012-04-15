package mcgill.ui;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;

import mcgill.game.Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Toolkit;

/**
 * GUI for registration interface
 */
public class Registration {

	private JFrame registration;
	private JTextField textUsername;

	private Client client;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Create the application.
	 */
	public Registration() {
		initialize();
	}
	
	public void open(Client client) {
		this.client = client;
		registration.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		registration = new JFrame();
		registration.setIconImage(Toolkit.getDefaultToolkit().getImage(Registration.class.getResource("/images/icon.png")));
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
				ColumnSpec.decode("max(74dlu;default):grow"),
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
		
		JLabel lblUsernameHelp = new JLabel("Please Enter a Username between 3 and 12 characters");
		lblUsernameHelp.setForeground(Color.GRAY);
		registration.getContentPane().add(lblUsernameHelp, "8, 6, 5, 1");
		
		JLabel lblPassword = new JLabel("Password:");
		registration.getContentPane().add(lblPassword, "6, 10, 3, 1, left, default");
		
		passwordField = new JPasswordField();
		registration.getContentPane().add(passwordField, "10, 10, 3, 1, fill, default");
		
		JLabel lblRetypePassword = new JLabel("Re-type Password:");
		registration.getContentPane().add(lblRetypePassword, "6, 12, 3, 1, left, default");
		
		passwordField_1 = new JPasswordField();
		registration.getContentPane().add(passwordField_1, "10, 12, 3, 1, fill, default");
		
		JLabel lblPassHelp = new JLabel("Please Eneter a Password between 6 and 12 characters");
		lblPassHelp.setForeground(Color.GRAY);
		registration.getContentPane().add(lblPassHelp, "8, 14, 5, 1");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage login = new LoginPage();
				login.open(client);
				registration.setVisible(false);
			}
		});
		registration.getContentPane().add(btnCancel, "8, 18");
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textUsername.getText();
				String password = new String(passwordField.getPassword());
				String password_re = new String(passwordField_1.getPassword());
				
				if (!password.equals(password_re)) {
					JOptionPane.showMessageDialog(registration, "The passwords do not match");
					return;
				}
				
				Boolean result = client.register(username, password);
				
				if (result) {
					MainWindow main = new MainWindow(client);
					main.open();
					registration.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(registration, "Username already in use");
				}
			}
		});
		registration.getContentPane().add(btnRegister, "12, 18");
	}

}
