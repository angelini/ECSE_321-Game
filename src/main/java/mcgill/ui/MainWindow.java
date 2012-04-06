package mcgill.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import mcgill.game.Client;

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
	private JTable allGameTable;
	
	private Client client;

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
	
	public void open(Client client) {
		this.client = client;
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 775, 583);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("562px"),
				ColumnSpec.decode("max(4dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("71px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(54dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("383px"),
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
		
		
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
			}
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane_1, "1, 1, 3, 1, fill, fill");
		
		JLabel chatArea = new JLabel("");
		scrollPane_1.setViewportView(chatArea);
		panel.add(btnSend, "3, 2, fill, fill");
		
		txtChatHere = new JTextField();
		txtChatHere.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		txtChatHere.setText("chat here");
		panel.add(txtChatHere, "1, 2, 2, 1, fill, fill");
		txtChatHere.setColumns(10);
		
		JTabbedPane main = new JTabbedPane(JTabbedPane.TOP);
		main.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(main, "1, 1, fill, fill");
		
		JScrollPane allGames = new JScrollPane();
		main.addTab("All Games", null, allGames, null);
		allGames.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		String[] gameColumnNames = {"Game Name"};
		
		Object[][] gameData = {
			    {"Game 1"}
			};
		
		
		allGameTable = new JTable(gameData,gameColumnNames);
		allGameTable.setColumnSelectionAllowed(true);
		allGames.setViewportView(allGameTable);
		
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
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.gridx = 2;
		gbc_btnCreate.gridy = 6;
		createGame.add(btnCreate, gbc_btnCreate);
		
		JPanel currentGame = new JPanel();
		currentGame.setBackground(Color.WHITE);
		main.addTab("Current Game", null, currentGame, null);
		currentGame.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("33px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("3px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("8px"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("20px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("13px"),
				ColumnSpec.decode("7px"),
				ColumnSpec.decode("17px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("38px"),
				ColumnSpec.decode("37px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("20px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("46px"),
				ColumnSpec.decode("37px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("37px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("13px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("6px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("12px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("19px"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("8px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("4px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("32px"),},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("19px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("45px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("43px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("45px"),
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("14px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("13px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("7px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("13px"),
				RowSpec.decode("14px"),}));
		
		JLabel label_1 = new JLabel("Bet Amt:");
		currentGame.add(label_1, "32, 2, 5, 3, left, center");
		
		JLabel lblBetAmt = new JLabel("Bet Amt:");
		currentGame.add(lblBetAmt, "2, 2, 5, 3, fill, center");
		
		JLabel pWcard2 = new JLabel("K♥");
		pWcard2.setOpaque(true);
		pWcard2.setFont(new Font("Arial", Font.BOLD, 20));
		pWcard2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pWcard2.setBackground(Color.WHITE);
		currentGame.add(pWcard2, "6, 6, 3, 1, fill, fill");
		
		JLabel pGcard1 = new JLabel("K♥");
		pGcard1.setOpaque(true);
		pGcard1.setFont(new Font("Arial", Font.BOLD, 20));
		pGcard1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pGcard1.setBackground(Color.GRAY);
		currentGame.add(pGcard1, "34, 6, 3, 1, fill, fill");
		
		JLabel pGcard2 = new JLabel("K♥");
		pGcard2.setOpaque(true);
		pGcard2.setFont(new Font("Arial", Font.BOLD, 20));
		pGcard2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pGcard2.setBackground(Color.WHITE);
		currentGame.add(pGcard2, "30, 6, 3, 1, fill, fill");
		
		JLabel pWcard1 = new JLabel("K♥");
		pWcard1.setOpaque(true);
		pWcard1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pWcard1.setBackground(Color.GRAY);
		pWcard1.setFont(new Font("Arial", Font.BOLD, 20));
		currentGame.add(pWcard1, "2, 6, 3, 1, fill, fill");
		
		JLabel pWname = new JLabel("Screen name");
		pWname.setOpaque(true);
		pWname.setBackground(Color.LIGHT_GRAY);
		currentGame.add(pWname, "11, 2, 4, 3, left, center");
		
		JLabel pWcash = new JLabel("$$$$$$$$");
		pWcash.setOpaque(true);
		pWcash.setBackground(Color.LIGHT_GRAY);
		currentGame.add(pWcash, "11, 4, 4, 1, fill, top");
		
		JLabel pGname = new JLabel("Screen name");
		pGname.setBackground(Color.LIGHT_GRAY);
		pGname.setOpaque(true);
		currentGame.add(pGname, "22, 2, 5, 3, left, center");
		
		JLabel pGcash = new JLabel("$$$$$$$$");
		pGcash.setBackground(Color.LIGHT_GRAY);
		pGcash.setOpaque(true);
		currentGame.add(pGcash, "22, 4, 5, 1, fill, top");
		
		JLabel pYname = new JLabel("Screen name");
		pYname.setBackground(Color.LIGHT_GRAY);
		pYname.setOpaque(true);
		currentGame.add(pYname, "26, 8, 9, 1, left, center");
		
		JLabel pYcash = new JLabel("$$$$$$$$");
		pYcash.setBackground(Color.LIGHT_GRAY);
		pYcash.setOpaque(true);
		currentGame.add(pYcash, "26, 8, 9, 1, fill, bottom");
		
		JLabel pRname = new JLabel("Screen name");
		pRname.setBackground(Color.LIGHT_GRAY);
		pRname.setOpaque(true);
		currentGame.add(pRname, "4, 8, 8, 1, left, center");
		
		JLabel pRcash = new JLabel("$$$$$$$$");
		pRcash.setBackground(Color.LIGHT_GRAY);
		pRcash.setOpaque(true);
		currentGame.add(pRcash, "4, 8, 8, 1, fill, bottom");
		
		JLabel pBcash = new JLabel("$$$$$$$$");
		pBcash.setBackground(Color.LIGHT_GRAY);
		pBcash.setOpaque(true);
		currentGame.add(pBcash, "17, 19, 3, 1, fill, top");
		
		JLabel pBname = new JLabel("Screen name");
		pBname.setBackground(Color.LIGHT_GRAY);
		pBname.setOpaque(true);
		currentGame.add(pBname, "17, 18, 3, 2, left, top");
		
		JLabel pRed = new JLabel("");
		pRed.setIcon(new ImageIcon(MainWindow.class.getResource("/images/avatar.png")));
		currentGame.add(pRed, "2, 8, right, top");
		
		JLabel pWhite = new JLabel("");
		pWhite.setIcon(new ImageIcon(MainWindow.class.getResource("/images/avatar white.png")));
		currentGame.add(pWhite, "8, 2, 3, 3, left, top");
		
		JLabel pGrey = new JLabel("");
		pGrey.setIcon(new ImageIcon(MainWindow.class.getResource("/images/avatar grey.png")));
		currentGame.add(pGrey, "28, 2, 3, 3, left, top");
		
		JLabel pYellow = new JLabel("");
		pYellow.setIcon(new ImageIcon(MainWindow.class.getResource("/images/avatar yellow.png")));
		currentGame.add(pYellow, "36, 8, left, top");
		
		JLabel pBlue = new JLabel("");
		pBlue.setIcon(new ImageIcon(MainWindow.class.getResource("/images/avatar blue.png")));
		currentGame.add(pBlue, "17, 12, 3, 5, center, top");
		
		JLabel lblPot = new JLabel("Pot:");
		currentGame.add(lblPot, "17, 2, left, bottom");
		
		JLabel potAmt = new JLabel("$$$$$$");
		currentGame.add(potAmt, "19, 2, fill, bottom");
		
		JLabel pWcard3 = new JLabel("K♥");
		pWcard3.setOpaque(true);
		pWcard3.setFont(new Font("Arial", Font.BOLD, 20));
		pWcard3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pWcard3.setBackground(Color.WHITE);
		currentGame.add(pWcard3, "10, 6, 3, 1, fill, fill");
		
		JLabel pWcard4 = new JLabel("K♥");
		pWcard4.setOpaque(true);
		pWcard4.setFont(new Font("Arial", Font.BOLD, 20));
		pWcard4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pWcard4.setBackground(Color.WHITE);
		currentGame.add(pWcard4, "14, 6, fill, fill");
		
		JLabel pWcard5 = new JLabel("K♥");
		pWcard5.setOpaque(true);
		pWcard5.setFont(new Font("Arial", Font.BOLD, 20));
		pWcard5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pWcard5.setBackground(Color.WHITE);
		currentGame.add(pWcard5, "15, 6, fill, fill");
		
		JLabel pGcard5 = new JLabel("K♥");
		pGcard5.setOpaque(true);
		pGcard5.setFont(new Font("Arial", Font.BOLD, 20));
		pGcard5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pGcard5.setBackground(Color.WHITE);
		currentGame.add(pGcard5, "20, 6, fill, fill");
		
		JLabel pGcard4 = new JLabel("K♥");
		pGcard4.setOpaque(true);
		pGcard4.setFont(new Font("Arial", Font.BOLD, 20));
		pGcard4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pGcard4.setBackground(Color.WHITE);
		currentGame.add(pGcard4, "22, 6, fill, fill");
		
		JLabel pGcard3 = new JLabel("K♥");
		pGcard3.setOpaque(true);
		pGcard3.setFont(new Font("Arial", Font.BOLD, 20));
		pGcard3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pGcard3.setBackground(Color.WHITE);
		currentGame.add(pGcard3, "24, 6, 5, 1, fill, fill");
		
		JLabel pYcard5 = new JLabel("K♥");
		pYcard5.setOpaque(true);
		pYcard5.setFont(new Font("Arial", Font.BOLD, 20));
		pYcard5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pYcard5.setBackground(Color.WHITE);
		currentGame.add(pYcard5, "20, 10, fill, fill");
		
		JLabel pYcard4 = new JLabel("K♥");
		pYcard4.setOpaque(true);
		pYcard4.setFont(new Font("Arial", Font.BOLD, 20));
		pYcard4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pYcard4.setBackground(Color.WHITE);
		currentGame.add(pYcard4, "22, 10, fill, fill");
		
		JLabel pYcard3 = new JLabel("K♥");
		pYcard3.setOpaque(true);
		pYcard3.setFont(new Font("Arial", Font.BOLD, 20));
		pYcard3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pYcard3.setBackground(Color.WHITE);
		currentGame.add(pYcard3, "24, 10, 5, 1, fill, fill");
		
		JLabel pYcard2 = new JLabel("K♥");
		pYcard2.setOpaque(true);
		pYcard2.setFont(new Font("Arial", Font.BOLD, 20));
		pYcard2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pYcard2.setBackground(Color.WHITE);
		currentGame.add(pYcard2, "30, 10, 3, 1, fill, fill");
		
		JLabel pYcard1 = new JLabel("K♥");
		pYcard1.setOpaque(true);
		pYcard1.setFont(new Font("Arial", Font.BOLD, 20));
		pYcard1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pYcard1.setBackground(Color.GRAY);
		currentGame.add(pYcard1, "34, 10, 3, 1, fill, fill");
		
		JLabel pRcard1 = new JLabel("K♥");
		pRcard1.setOpaque(true);
		pRcard1.setFont(new Font("Arial", Font.BOLD, 20));
		pRcard1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pRcard1.setBackground(Color.GRAY);
		currentGame.add(pRcard1, "2, 10, 3, 1, fill, fill");
		
		JLabel pRcard2 = new JLabel("K♥");
		pRcard2.setOpaque(true);
		pRcard2.setFont(new Font("Arial", Font.BOLD, 20));
		pRcard2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pRcard2.setBackground(Color.WHITE);
		currentGame.add(pRcard2, "6, 10, 3, 1, fill, fill");
		
		JLabel pRcard3 = new JLabel("K♥");
		pRcard3.setOpaque(true);
		pRcard3.setFont(new Font("Arial", Font.BOLD, 20));
		pRcard3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pRcard3.setBackground(Color.WHITE);
		currentGame.add(pRcard3, "10, 10, 3, 1, fill, fill");
		
		JLabel pRcard4 = new JLabel("K♥");
		pRcard4.setOpaque(true);
		pRcard4.setFont(new Font("Arial", Font.BOLD, 20));
		pRcard4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pRcard4.setBackground(Color.WHITE);
		currentGame.add(pRcard4, "14, 10, fill, fill");
		
		JLabel pRcard5 = new JLabel("K♥");
		pRcard5.setOpaque(true);
		pRcard5.setFont(new Font("Arial", Font.BOLD, 20));
		pRcard5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pRcard5.setBackground(Color.WHITE);
		currentGame.add(pRcard5, "15, 10, fill, fill");
		
		JLabel pBcard1 = new JLabel("K♥");
		pBcard1.setOpaque(true);
		pBcard1.setFont(new Font("Arial", Font.BOLD, 20));
		pBcard1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pBcard1.setBackground(Color.GRAY);
		currentGame.add(pBcard1, "20, 14, 1, 6, fill, fill");
		
		JLabel pBcard2 = new JLabel("K♥");
		pBcard2.setOpaque(true);
		pBcard2.setFont(new Font("Arial", Font.BOLD, 20));
		pBcard2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pBcard2.setBackground(Color.WHITE);
		currentGame.add(pBcard2, "22, 14, 1, 6, fill, fill");
		
		JLabel pBcard3 = new JLabel("K♥");
		pBcard3.setOpaque(true);
		pBcard3.setFont(new Font("Arial", Font.BOLD, 20));
		pBcard3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pBcard3.setBackground(Color.WHITE);
		currentGame.add(pBcard3, "24, 14, 5, 6, fill, fill");
		
		JLabel pBcard4 = new JLabel("K♥");
		pBcard4.setOpaque(true);
		pBcard4.setFont(new Font("Arial", Font.BOLD, 20));
		pBcard4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pBcard4.setBackground(Color.WHITE);
		currentGame.add(pBcard4, "30, 14, 3, 6, fill, fill");
		
		JLabel pBcard5 = new JLabel("K♥");
		pBcard5.setOpaque(true);
		pBcard5.setFont(new Font("Arial", Font.BOLD, 20));
		pBcard5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pBcard5.setBackground(Color.WHITE);
		currentGame.add(pBcard5, "34, 14, 3, 6, fill, fill");
		
		JButton btnBet = new JButton("Bet");
		currentGame.add(btnBet, "12, 16, 4, 4, fill, top");
		
		JButton btnFold = new JButton("Fold");
		currentGame.add(btnFold, "12, 12, 4, 3, fill, bottom");
		
		JButton btnCheck = new JButton("Check");
		currentGame.add(btnCheck, "2, 12, 9, 3, fill, bottom");
		
		txtBetAmt = new JTextField();
		txtBetAmt.setText("Bet Amount");
		currentGame.add(txtBetAmt, "2, 16, 9, 3, center, bottom");
		txtBetAmt.setColumns(10);
		
		JLabel label_2 = new JLabel("Bet Amt:");
		currentGame.add(label_2, "12, 8, 3, 1, right, center");
		
		JLabel label_3 = new JLabel("Bet Amt:");
		currentGame.add(label_3, "22, 8, 3, 1, center, center");
		
		JLabel label_4 = new JLabel("Bet Amt:");
		currentGame.add(label_4, "20, 12, 3, 1, left, top");
		
		JLabel pWbet = new JLabel("$$");
		currentGame.add(pWbet, "2, 4, 5, 1, fill, top");
		
		JLabel pGbet = new JLabel("$$");
		currentGame.add(pGbet, "32, 4, 5, 1, fill, top");
		
		JLabel pRbet = new JLabel("$$");
		currentGame.add(pRbet, "12, 8, 3, 1, fill, bottom");
		
		JLabel pYbet = new JLabel("$$");
		currentGame.add(pYbet, "22, 8, 3, 1, fill, bottom");
		
		JLabel pBbet = new JLabel("$$");
		currentGame.add(pBbet, "22, 12, 3, 1, fill, top");
		
		JLabel table = new JLabel("");
		table.setIcon(new ImageIcon(MainWindow.class.getResource("/images/table.png")));
		currentGame.add(table, "4, 2, 29, 13, left, center");
		
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
