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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

public class LoginPage {

	private JFrame frmLogin;
	private JTextField textEmail;
	private JPasswordField passwordField;
	
	private Client client;
	private JLabel lblTitle;

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
		frmLogin.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginPage.class.getResource("/images/icon.png")));
		frmLogin.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frmLogin.setResizable(false);
		frmLogin.setTitle("Log-in");
		frmLogin.setBounds(100, 100, 356, 302);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton login = new JButton("Log-in");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textEmail.getText();
				String password = new String(passwordField.getPassword());
				
				Boolean result = client.login(username, password);
				
				if (result) {
					MainWindow main = new MainWindow(client);
					main.open();
					frmLogin.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(frmLogin, "No you failed...");
				}
			}
		});
		frmLogin.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("35px"),
				ColumnSpec.decode("68px"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("49px"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("127px"),},
			new RowSpec[] {
				RowSpec.decode("max(19dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("64px"),
				RowSpec.decode("max(13dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				RowSpec.decode("23px"),
				RowSpec.decode("23px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("23px"),}));
		
		lblTitle = new JLabel("Full Suit T3");
		lblTitle.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 30));
		frmLogin.getContentPane().add(lblTitle, "2, 3, 5, 1, center, center");
		frmLogin.getContentPane().add(login, "2, 10, 3, 1, fill, top");
		
		JLabel lblEmail = new JLabel("Username:");
		frmLogin.getContentPane().add(lblEmail, "2, 6, fill, center");
		
		JLabel lblPassword = new JLabel("Password:");
		frmLogin.getContentPane().add(lblPassword, "2, 8, 3, 1, left, center");
		
		textEmail = new JTextField();
		frmLogin.getContentPane().add(textEmail, "4, 6, 3, 1, fill, top");
		textEmail.setColumns(10);
		
		JButton forgotPass = new JButton("Forgot Password");
		forgotPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ForgotPassword forgotPassword = new ForgotPassword();
				forgotPassword.open(client);
				frmLogin.setVisible(false);
			}
		});
		frmLogin.getContentPane().add(forgotPass, "2, 12, 3, 1, fill, top");
		
		JButton register = new JButton("Register");
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Registration register = new Registration();
				register.open(client);
				frmLogin.setVisible(false);
			}
		});
		frmLogin.getContentPane().add(register, "6, 10, fill, top");
		
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		frmLogin.getContentPane().add(quit, "6, 12, fill, top");
		
		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		frmLogin.getContentPane().add(passwordField, "4, 8, 3, 1, fill, top");
	}
	
}
