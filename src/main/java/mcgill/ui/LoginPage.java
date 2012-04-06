package mcgill.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import mcgill.game.Client;
import mcgill.game.Config;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginPage {

	private JFrame frmLogin;
	private JTextField textEmail;
	private JPasswordField passwordField;
	
	private Client client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExecutorService executor = Executors.newSingleThreadExecutor();
					Client client = new Client(Config.REDIS_HOST, Config.REDIS_PORT);
					
					executor.execute(client);
					
					LoginPage window = new LoginPage();
					window.open(client);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
	}
	
	public void open(Client client) {
		this.client = client;
		frmLogin.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setResizable(false);
		frmLogin.setBackground(Color.WHITE);
		frmLogin.setTitle("Log-in");
		frmLogin.setBounds(100, 100, 356, 302);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton login = new JButton("Log-in");
		login.setBounds(53, 191, 113, 23);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textEmail.getText();
				String password = new String(passwordField.getPassword());
				
				Boolean result = client.loginUI(username, password);
				
				if (result) {
					MainWindow main = new MainWindow();
					main.open(client);
					frmLogin.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(frmLogin, "No you failed...");
				}
			}
		});
		frmLogin.getContentPane().setLayout(null);
		frmLogin.getContentPane().add(login);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(35, 126, 49, 14);
		frmLogin.getContentPane().add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(35, 151, 70, 14);
		frmLogin.getContentPane().add(lblPassword);
		
		textEmail = new JTextField();
		textEmail.setBounds(93, 123, 210, 20);
		frmLogin.getContentPane().add(textEmail);
		textEmail.setColumns(10);
		
		JButton forgotPass = new JButton("Forgot Password");
		forgotPass.setBounds(53, 225, 113, 23);
		frmLogin.getContentPane().add(forgotPass);
		
		JButton register = new JButton("Register");
		register.setBounds(176, 191, 113, 23);
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Registration register = new Registration();
				register.open(client);
				frmLogin.setVisible(false);
			}
		});
		frmLogin.getContentPane().add(register);
		
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		quit.setBounds(176, 225, 113, 23);
		frmLogin.getContentPane().add(quit);
		
		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(93, 148, 210, 20);
		frmLogin.getContentPane().add(passwordField);
	}
	
}
