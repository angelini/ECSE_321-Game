package mcgill.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Toolkit;

import mcgill.game.Client;

/**
 * GUI for forgot password interface
 */
public class ForgotPassword {

	private JFrame frmPasswordRetrieval;
	private JTextField textEnterUsername;
	private JButton btnGetPassword;
	private JButton btnCancel;
	private Client client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgotPassword window = new ForgotPassword();
					window.frmPasswordRetrieval.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ForgotPassword() {
		initialize();
	}

	
	public void open(Client client) {
		this.client = client;
		frmPasswordRetrieval.setVisible(true);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPasswordRetrieval = new JFrame();
		frmPasswordRetrieval.setResizable(false);
		frmPasswordRetrieval.setIconImage(Toolkit.getDefaultToolkit().getImage(ForgotPassword.class.getResource("/images/icon.png")));
		frmPasswordRetrieval.setTitle("Password Retrieval");
		frmPasswordRetrieval.setBounds(100, 100, 450, 170);
		frmPasswordRetrieval.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPasswordRetrieval.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(57dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(33dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(67dlu;default)"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblUsername = new JLabel("Username:");
		frmPasswordRetrieval.getContentPane().add(lblUsername, "4, 4, right, default");
		
		textEnterUsername = new JTextField();
		frmPasswordRetrieval.getContentPane().add(textEnterUsername, "6, 4, 5, 1, fill, default");
		textEnterUsername.setColumns(10);
		
		btnCancel = new JButton("Cancel");
		frmPasswordRetrieval.getContentPane().add(btnCancel, "6, 6");
		
		btnGetPassword = new JButton("Get Password");
		frmPasswordRetrieval.getContentPane().add(btnGetPassword, "10, 6");
	}

}
