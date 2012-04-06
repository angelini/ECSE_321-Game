package mcgill.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Insets;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainWindow {

	private JFrame frame;
	private JTextField txtChatHere;
	private JTextField txtBetAmt;
	private JTextField txtGame;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 688, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("474px"),
				ColumnSpec.decode("max(4dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("71px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(54dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("329px"),
				RowSpec.decode("15px"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("26px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(21dlu;default)"),}));
		
		JLabel lblChatWindow = new JLabel("Chat Window");
		lblChatWindow.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblChatWindow, "1, 2, center, bottom");
		
		JTabbedPane chat = new JTabbedPane(JTabbedPane.TOP);
		chat.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(chat, "1, 3, 1, 9, fill, fill");
		
		JPanel panel = new JPanel();
		chat.addTab("Global Chat", null, panel, null);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("86px"),
				ColumnSpec.decode("294px:grow"),
				ColumnSpec.decode("89px"),},
			new RowSpec[] {
				RowSpec.decode("76px:grow"),
				RowSpec.decode("23px"),}));
		
		
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
			}
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane_1, "1, 1, 3, 1, fill, fill");
		
		JLabel chatArea = new JLabel("");
		scrollPane_1.setViewportView(chatArea);
		panel.add(btnNewButton, "3, 2, fill, fill");
		
		txtChatHere = new JTextField();
		txtChatHere.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		txtChatHere.setText("chat here");
		panel.add(txtChatHere, "1, 2, 2, 1, fill, fill");
		txtChatHere.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		chat.addTab("New tab", null, scrollPane, null);
		
		JTabbedPane main = new JTabbedPane(JTabbedPane.TOP);
		main.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(main, "1, 1, fill, fill");
		
		JPanel allGames = new JPanel();
		main.addTab("All Games", null, allGames, null);
		allGames.setLayout(null);
		
		JPanel createGame = new JPanel();
		main.addTab("Create Game", null, createGame, null);
		GridBagLayout gbl_createGame = new GridBagLayout();
		gbl_createGame.columnWidths = new int[]{94, 181, 0, 0};
		gbl_createGame.rowHeights = new int[]{27, 0, 0, 0, 0, 0, 0, 0};
		gbl_createGame.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_createGame.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		createGame.setLayout(gbl_createGame);
		
		JLabel lblGameCreationOptions = new JLabel("Game Creation Options:");
		lblGameCreationOptions.setFont(new Font("Tahoma", Font.BOLD, 22));
		GridBagConstraints gbc_lblGameCreationOptions = new GridBagConstraints();
		gbc_lblGameCreationOptions.gridwidth = 2;
		gbc_lblGameCreationOptions.insets = new Insets(0, 0, 5, 5);
		gbc_lblGameCreationOptions.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblGameCreationOptions.gridx = 0;
		gbc_lblGameCreationOptions.gridy = 0;
		createGame.add(lblGameCreationOptions, gbc_lblGameCreationOptions);
		
		JLabel lblGameName = new JLabel("Game Name:");
		GridBagConstraints gbc_lblGameName = new GridBagConstraints();
		gbc_lblGameName.anchor = GridBagConstraints.EAST;
		gbc_lblGameName.insets = new Insets(0, 0, 5, 5);
		gbc_lblGameName.gridx = 0;
		gbc_lblGameName.gridy = 1;
		createGame.add(lblGameName, gbc_lblGameName);
		
		txtGame = new JTextField();
		txtGame.setText("Game1");
		GridBagConstraints gbc_txtGame = new GridBagConstraints();
		gbc_txtGame.insets = new Insets(0, 0, 5, 5);
		gbc_txtGame.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGame.gridx = 1;
		gbc_txtGame.gridy = 1;
		createGame.add(txtGame, gbc_txtGame);
		txtGame.setColumns(10);
		
		JButton btnResetToDefault = new JButton("Reset to Default");
		GridBagConstraints gbc_btnResetToDefault = new GridBagConstraints();
		gbc_btnResetToDefault.insets = new Insets(0, 0, 0, 5);
		gbc_btnResetToDefault.gridx = 1;
		gbc_btnResetToDefault.gridy = 6;
		createGame.add(btnResetToDefault, gbc_btnResetToDefault);
		
		JButton btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.gridx = 2;
		gbc_btnCreate.gridy = 6;
		createGame.add(btnCreate, gbc_btnCreate);
		
		JPanel currentGame = new JPanel();
		currentGame.setBackground(Color.WHITE);
		main.addTab("Current Game", null, currentGame, null);
		currentGame.setLayout(null);
		
		JLabel label_1 = new JLabel("Bet Amt:");
		label_1.setBounds(413, 23, 44, 14);
		currentGame.add(label_1);
		
		JLabel lblBetAmt = new JLabel("Bet Amt:");
		lblBetAmt.setBounds(10, 23, 46, 14);
		currentGame.add(lblBetAmt);
		
		JLabel pWcard2 = new JLabel("K♥");
		pWcard2.setBounds(48, 61, 37, 45);
		pWcard2.setOpaque(true);
		pWcard2.setFont(new Font("Arial", Font.BOLD, 20));
		pWcard2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pWcard2.setBackground(Color.WHITE);
		currentGame.add(pWcard2);
		
		JLabel pGcard1 = new JLabel("K♥");
		pGcard1.setOpaque(true);
		pGcard1.setFont(new Font("Arial", Font.BOLD, 20));
		pGcard1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pGcard1.setBackground(Color.GRAY);
		pGcard1.setBounds(422, 61, 37, 45);
		currentGame.add(pGcard1);
		
		JLabel pGcard2 = new JLabel("K♥");
		pGcard2.setOpaque(true);
		pGcard2.setFont(new Font("Arial", Font.BOLD, 20));
		pGcard2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pGcard2.setBackground(Color.WHITE);
		pGcard2.setBounds(384, 61, 37, 45);
		currentGame.add(pGcard2);
		
		JLabel pWcard1 = new JLabel("K♥");
		pWcard1.setBounds(10, 61, 37, 45);
		pWcard1.setOpaque(true);
		pWcard1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pWcard1.setBackground(Color.GRAY);
		pWcard1.setFont(new Font("Arial", Font.BOLD, 20));
		currentGame.add(pWcard1);
		
		JLabel pWname = new JLabel("Screen name");
		pWname.setOpaque(true);
		pWname.setBackground(Color.LIGHT_GRAY);
		pWname.setBounds(99, 23, 62, 14);
		currentGame.add(pWname);
		
		JLabel pWcash = new JLabel("$$$$$$$$");
		pWcash.setOpaque(true);
		pWcash.setBackground(Color.LIGHT_GRAY);
		pWcash.setBounds(99, 36, 62, 14);
		currentGame.add(pWcash);
		
		JLabel pGname = new JLabel("Screen name");
		pGname.setBackground(Color.LIGHT_GRAY);
		pGname.setOpaque(true);
		pGname.setBounds(308, 23, 62, 14);
		currentGame.add(pGname);
		
		JLabel pGcash = new JLabel("$$$$$$$$");
		pGcash.setBackground(Color.LIGHT_GRAY);
		pGcash.setOpaque(true);
		pGcash.setBounds(308, 36, 62, 14);
		currentGame.add(pGcash);
		
		JLabel pYname = new JLabel("Screen name");
		pYname.setBackground(Color.LIGHT_GRAY);
		pYname.setOpaque(true);
		pYname.setBounds(364, 120, 62, 14);
		currentGame.add(pYname);
		
		JLabel pYcash = new JLabel("$$$$$$$$");
		pYcash.setBackground(Color.LIGHT_GRAY);
		pYcash.setOpaque(true);
		pYcash.setBounds(364, 133, 62, 14);
		currentGame.add(pYcash);
		
		JLabel pRname = new JLabel("Screen name");
		pRname.setBackground(Color.LIGHT_GRAY);
		pRname.setOpaque(true);
		pRname.setBounds(44, 120, 62, 14);
		currentGame.add(pRname);
		
		JLabel pRcash = new JLabel("$$$$$$$$");
		pRcash.setBackground(Color.LIGHT_GRAY);
		pRcash.setOpaque(true);
		pRcash.setBounds(44, 133, 62, 14);
		currentGame.add(pRcash);
		
		JLabel pBcash = new JLabel("$$$$$$$$");
		pBcash.setBackground(Color.LIGHT_GRAY);
		pBcash.setOpaque(true);
		pBcash.setBounds(202, 276, 62, 14);
		currentGame.add(pBcash);
		
		JLabel pBname = new JLabel("Screen name");
		pBname.setBackground(Color.LIGHT_GRAY);
		pBname.setOpaque(true);
		pBname.setBounds(202, 263, 62, 14);
		currentGame.add(pBname);
		
		JLabel pRed = new JLabel("");
		pRed.setBounds(10, 109, 32, 43);
		pRed.setIcon(new ImageIcon(MainWindow.class.getResource("/images/avatar.png")));
		currentGame.add(pRed);
		
		JLabel pWhite = new JLabel("");
		pWhite.setBounds(65, 12, 32, 43);
		pWhite.setIcon(new ImageIcon(MainWindow.class.getResource("/images/avatar white.png")));
		currentGame.add(pWhite);
		
		JLabel pGrey = new JLabel("");
		pGrey.setBounds(371, 12, 32, 43);
		pGrey.setIcon(new ImageIcon(MainWindow.class.getResource("/images/avatar grey.png")));
		currentGame.add(pGrey);
		
		JLabel pYellow = new JLabel("");
		pYellow.setBounds(427, 109, 32, 43);
		pYellow.setIcon(new ImageIcon(MainWindow.class.getResource("/images/avatar yellow.png")));
		currentGame.add(pYellow);
		
		JLabel pBlue = new JLabel("");
		pBlue.setBounds(217, 218, 32, 43);
		pBlue.setIcon(new ImageIcon(MainWindow.class.getResource("/images/avatar blue.png")));
		currentGame.add(pBlue);
		
		JLabel lblPot = new JLabel("Pot:");
		lblPot.setBounds(202, 21, 20, 14);
		currentGame.add(lblPot);
		
		JLabel potAmt = new JLabel("$$$$$$");
		potAmt.setBounds(224, 21, 46, 14);
		currentGame.add(potAmt);
		
		JLabel pWcard3 = new JLabel("K♥");
		pWcard3.setOpaque(true);
		pWcard3.setFont(new Font("Arial", Font.BOLD, 20));
		pWcard3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pWcard3.setBackground(Color.WHITE);
		pWcard3.setBounds(86, 61, 37, 45);
		currentGame.add(pWcard3);
		
		JLabel pWcard4 = new JLabel("K♥");
		pWcard4.setOpaque(true);
		pWcard4.setFont(new Font("Arial", Font.BOLD, 20));
		pWcard4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pWcard4.setBackground(Color.WHITE);
		pWcard4.setBounds(124, 61, 37, 45);
		currentGame.add(pWcard4);
		
		JLabel pWcard5 = new JLabel("K♥");
		pWcard5.setOpaque(true);
		pWcard5.setFont(new Font("Arial", Font.BOLD, 20));
		pWcard5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pWcard5.setBackground(Color.WHITE);
		pWcard5.setBounds(162, 61, 37, 45);
		currentGame.add(pWcard5);
		
		JLabel pGcard5 = new JLabel("K♥");
		pGcard5.setOpaque(true);
		pGcard5.setFont(new Font("Arial", Font.BOLD, 20));
		pGcard5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pGcard5.setBackground(Color.WHITE);
		pGcard5.setBounds(270, 61, 37, 45);
		currentGame.add(pGcard5);
		
		JLabel pGcard4 = new JLabel("K♥");
		pGcard4.setOpaque(true);
		pGcard4.setFont(new Font("Arial", Font.BOLD, 20));
		pGcard4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pGcard4.setBackground(Color.WHITE);
		pGcard4.setBounds(308, 61, 37, 45);
		currentGame.add(pGcard4);
		
		JLabel pGcard3 = new JLabel("K♥");
		pGcard3.setOpaque(true);
		pGcard3.setFont(new Font("Arial", Font.BOLD, 20));
		pGcard3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pGcard3.setBackground(Color.WHITE);
		pGcard3.setBounds(346, 61, 37, 45);
		currentGame.add(pGcard3);
		
		JLabel pYcard5 = new JLabel("K♥");
		pYcard5.setOpaque(true);
		pYcard5.setFont(new Font("Arial", Font.BOLD, 20));
		pYcard5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pYcard5.setBackground(Color.WHITE);
		pYcard5.setBounds(270, 158, 37, 45);
		currentGame.add(pYcard5);
		
		JLabel pYcard4 = new JLabel("K♥");
		pYcard4.setOpaque(true);
		pYcard4.setFont(new Font("Arial", Font.BOLD, 20));
		pYcard4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pYcard4.setBackground(Color.WHITE);
		pYcard4.setBounds(308, 158, 37, 45);
		currentGame.add(pYcard4);
		
		JLabel pYcard3 = new JLabel("K♥");
		pYcard3.setOpaque(true);
		pYcard3.setFont(new Font("Arial", Font.BOLD, 20));
		pYcard3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pYcard3.setBackground(Color.WHITE);
		pYcard3.setBounds(346, 158, 37, 45);
		currentGame.add(pYcard3);
		
		JLabel pYcard2 = new JLabel("K♥");
		pYcard2.setOpaque(true);
		pYcard2.setFont(new Font("Arial", Font.BOLD, 20));
		pYcard2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pYcard2.setBackground(Color.WHITE);
		pYcard2.setBounds(384, 158, 37, 45);
		currentGame.add(pYcard2);
		
		JLabel pYcard1 = new JLabel("K♥");
		pYcard1.setOpaque(true);
		pYcard1.setFont(new Font("Arial", Font.BOLD, 20));
		pYcard1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pYcard1.setBackground(Color.GRAY);
		pYcard1.setBounds(422, 158, 37, 45);
		currentGame.add(pYcard1);
		
		JLabel pRcard1 = new JLabel("K♥");
		pRcard1.setOpaque(true);
		pRcard1.setFont(new Font("Arial", Font.BOLD, 20));
		pRcard1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pRcard1.setBackground(Color.GRAY);
		pRcard1.setBounds(10, 158, 37, 45);
		currentGame.add(pRcard1);
		
		JLabel pRcard2 = new JLabel("K♥");
		pRcard2.setOpaque(true);
		pRcard2.setFont(new Font("Arial", Font.BOLD, 20));
		pRcard2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pRcard2.setBackground(Color.WHITE);
		pRcard2.setBounds(48, 158, 37, 45);
		currentGame.add(pRcard2);
		
		JLabel pRcard3 = new JLabel("K♥");
		pRcard3.setOpaque(true);
		pRcard3.setFont(new Font("Arial", Font.BOLD, 20));
		pRcard3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pRcard3.setBackground(Color.WHITE);
		pRcard3.setBounds(86, 158, 37, 45);
		currentGame.add(pRcard3);
		
		JLabel pRcard4 = new JLabel("K♥");
		pRcard4.setOpaque(true);
		pRcard4.setFont(new Font("Arial", Font.BOLD, 20));
		pRcard4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pRcard4.setBackground(Color.WHITE);
		pRcard4.setBounds(124, 158, 37, 45);
		currentGame.add(pRcard4);
		
		JLabel pRcard5 = new JLabel("K♥");
		pRcard5.setOpaque(true);
		pRcard5.setFont(new Font("Arial", Font.BOLD, 20));
		pRcard5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pRcard5.setBackground(Color.WHITE);
		pRcard5.setBounds(162, 158, 37, 45);
		currentGame.add(pRcard5);
		
		JLabel pBcard1 = new JLabel("K♥");
		pBcard1.setOpaque(true);
		pBcard1.setFont(new Font("Arial", Font.BOLD, 20));
		pBcard1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pBcard1.setBackground(Color.GRAY);
		pBcard1.setBounds(270, 233, 37, 45);
		currentGame.add(pBcard1);
		
		JLabel pBcard2 = new JLabel("K♥");
		pBcard2.setOpaque(true);
		pBcard2.setFont(new Font("Arial", Font.BOLD, 20));
		pBcard2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pBcard2.setBackground(Color.WHITE);
		pBcard2.setBounds(308, 233, 37, 45);
		currentGame.add(pBcard2);
		
		JLabel pBcard3 = new JLabel("K♥");
		pBcard3.setOpaque(true);
		pBcard3.setFont(new Font("Arial", Font.BOLD, 20));
		pBcard3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pBcard3.setBackground(Color.WHITE);
		pBcard3.setBounds(346, 233, 37, 45);
		currentGame.add(pBcard3);
		
		JLabel pBcard4 = new JLabel("K♥");
		pBcard4.setOpaque(true);
		pBcard4.setFont(new Font("Arial", Font.BOLD, 20));
		pBcard4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pBcard4.setBackground(Color.WHITE);
		pBcard4.setBounds(384, 233, 37, 45);
		currentGame.add(pBcard4);
		
		JLabel pBcard5 = new JLabel("K♥");
		pBcard5.setOpaque(true);
		pBcard5.setFont(new Font("Arial", Font.BOLD, 20));
		pBcard5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pBcard5.setBackground(Color.WHITE);
		pBcard5.setBounds(422, 233, 37, 45);
		currentGame.add(pBcard5);
		
		JButton btnBet = new JButton("Bet");
		btnBet.setBounds(106, 254, 89, 23);
		currentGame.add(btnBet);
		
		JButton btnFold = new JButton("Fold");
		btnFold.setBounds(106, 223, 89, 23);
		currentGame.add(btnFold);
		
		JButton btnCheck = new JButton("Check");
		btnCheck.setBounds(10, 223, 89, 23);
		currentGame.add(btnCheck);
		
		txtBetAmt = new JTextField();
		txtBetAmt.setText("Bet Amount");
		txtBetAmt.setBounds(11, 256, 86, 20);
		currentGame.add(txtBetAmt);
		txtBetAmt.setColumns(10);
		
		JLabel label_2 = new JLabel("Bet Amt:");
		label_2.setBounds(117, 120, 44, 14);
		currentGame.add(label_2);
		
		JLabel label_3 = new JLabel("Bet Amt:");
		label_3.setBounds(310, 120, 44, 14);
		currentGame.add(label_3);
		
		JLabel label_4 = new JLabel("Bet Amt:");
		label_4.setBounds(270, 218, 44, 14);
		currentGame.add(label_4);
		
		JLabel pWbet = new JLabel("$$");
		pWbet.setBounds(9, 36, 46, 14);
		currentGame.add(pWbet);
		
		JLabel pGbet = new JLabel("$$");
		pGbet.setBounds(413, 36, 46, 14);
		currentGame.add(pGbet);
		
		JLabel pRbet = new JLabel("$$");
		pRbet.setBounds(116, 133, 46, 14);
		currentGame.add(pRbet);
		
		JLabel pYbet = new JLabel("$$");
		pYbet.setBounds(308, 133, 46, 14);
		currentGame.add(pYbet);
		
		JLabel pBbet = new JLabel("$$");
		pBbet.setBounds(313, 218, 46, 14);
		currentGame.add(pBbet);
		
		JLabel table = new JLabel("");
		table.setBounds(44, 27, 374, 211);
		table.setIcon(new ImageIcon(MainWindow.class.getResource("/images/table.png")));
		currentGame.add(table);
		
		JTabbedPane friends = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(friends, "2, 1, 7, 1, fill, fill");	
		
		
		
		
		String[] columnNames = {"Name",
                "Status",
                "Options"};
		Object[][] data = {
			    {"Kathy", "Online","something"
			     },
			    {"John", "offline",
			     "no options"},
	
			};
	
		JScrollPane allFriends = new JScrollPane();
		allFriends.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		allFriends.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		friends.addTab("Friends", null, allFriends, null);
		
		table_1 = new JTable(data,columnNames);
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		allFriends.setViewportView(table_1);		
		
		JScrollPane search = new JScrollPane();
		friends.addTab("Search", null, search, null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("D:\\Documents\\!McGill\\ECSE 321\\avatar main.png"));
		frame.getContentPane().add(label, "4, 3, 1, 3, center, bottom");
		
		JButton btnOptions = new JButton("Options");
		frame.getContentPane().add(btnOptions, "6, 5");
		
		JLabel lblScreenName = new JLabel("Screen Name");
		frame.getContentPane().add(lblScreenName, "4, 7, center, center");
		
		JButton btnLogOut = new JButton("Log Out");
		frame.getContentPane().add(btnLogOut, "6, 7");
		
		JLabel cash = new JLabel("$$$$");
		frame.getContentPane().add(cash, "4, 9, center, top");
		
		JButton btnQuit = new JButton("Quit");
		frame.getContentPane().add(btnQuit, "6, 9");
		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{frame.getContentPane()}));
	}
}
