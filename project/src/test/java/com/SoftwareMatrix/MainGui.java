import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.DropMode;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import java.io.*
public class MainGui extends JFrame {

	private JLayeredPane MainPanel;
	private JTextPane txtCherryBox;
	private JTextPane textField;
	private JTextPane textField_1;
	private JTextPane txtPassword;
	private JTextPane textField_2;
	private JTextPane textField_3;
	private JTextPane textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGui frame = new MainGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainGui() {
		setBackground(Color.GRAY);
		setTitle("Cherry Box");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1440, 810);
		MainPanel = new JLayeredPane();
		MainPanel.setBackground(Color.WHITE);
		MainPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setContentPane(MainPanel);
		MainPanel.setLayout(new CardLayout(0, 0));
		
		//Box Field
		JPanel BoxPanel = new JPanel();
		BoxPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		BoxPanel.setBackground(Color.WHITE);
		BoxPanel.setBounds(0, 0, 1440, 810);
		MainPanel.add(BoxPanel, "name_1666442199616500");
		
		//Login Field
		JPanel LoginPanel = new JPanel();
		MainPanel.setLayer(LoginPanel, 0);
		LoginPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		LoginPanel.setBackground(Color.WHITE);
		LoginPanel.setBounds(0, 0, 1440, 810);
		MainPanel.add(LoginPanel, "name_1666442225887700");
		LoginPanel.setLayout(null);

		txtCherryBox = new JTextPane();
		txtCherryBox.setEditable(false);
		txtCherryBox.setFont(new Font("����������_ac ExtraBold", Font.PLAIN, 43));
		txtCherryBox.setForeground(Color.BLACK);
		txtCherryBox.setText("Cherry Box");
		txtCherryBox.setBounds(602, 132, 237, 54);
		LoginPanel.add(txtCherryBox);
		
		textField = new JTextPane();
		textField.setEditable(false);
		textField.setText("\uB2E4\uB978 \uC11C\uBE44\uC2A4\uB85C \uB85C\uADF8\uC778");
		textField.setForeground(Color.BLACK);
		textField.setFont(new Font("����������_ac", Font.PLAIN, 22));
		textField.setBounds(630, 238, 189, 31);
		LoginPanel.add(textField);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\YooTaeHyun\\eclipse-workspace\\CherryBox\\src\\images\\facebook.png"));
		btnNewButton.setBounds(560, 292, 64, 64);
		LoginPanel.add(btnNewButton);
		
		JButton button_3 = new JButton("");
		button_3.setIcon(new ImageIcon("C:\\Users\\YooTaeHyun\\eclipse-workspace\\CherryBox\\src\\images\\google.png"));
		button_3.setBounds(644, 292, 64, 64);
		LoginPanel.add(button_3);
		
		JButton button_4 = new JButton("");
		button_4.setIcon(new ImageIcon("C:\\Users\\YooTaeHyun\\eclipse-workspace\\CherryBox\\src\\images\\github.png"));
		button_4.setBounds(728, 292, 64, 64);
		LoginPanel.add(button_4);
		
		JButton button_5 = new JButton("");
		button_5.setIcon(new ImageIcon("C:\\Users\\YooTaeHyun\\eclipse-workspace\\CherryBox\\src\\images\\naver.png"));
		button_5.setBounds(812, 292, 64, 64);
		LoginPanel.add(button_5);
		
		textField_1 = new JTextPane();
		textField_1.setEditable(false);
		textField_1.setForeground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		textField_1.setFont(new Font("����������_ac", Font.PLAIN, 20));
		textField_1.setText("\uB610\uB294");
		textField_1.setBounds(700, 391, 40, 24);
		LoginPanel.add(textField_1);
		
		JFormattedTextField IdTextField = new JFormattedTextField();
		IdTextField.setBounds(615, 427, 333, 41);
		LoginPanel.add(IdTextField);
		
		txtPassword = new JTextPane();
		txtPassword.setEditable(false);
		txtPassword.setFont(new Font("����������_ac ExtraBold", Font.PLAIN, 22));
		txtPassword.setText("ID");
		txtPassword.setBounds(499, 427, 116, 41);
		LoginPanel.add(txtPassword);
		
		textField_2 = new JTextPane();
		textField_2.setEditable(false);
		textField_2.setText("Password");
		textField_2.setFont(new Font("����������_ac ExtraBold", Font.PLAIN, 22));
		textField_2.setBounds(499, 480, 116, 41);
		LoginPanel.add(textField_2);
		
		JFormattedTextField PasswordTextField = new JFormattedTextField();
		PasswordTextField.setBounds(615, 480, 333, 41);
		LoginPanel.add(PasswordTextField);
		
		textField_3 = new JTextPane();
		textField_3.setEditable(false);
		textField_3.setText("\uB85C\uADF8\uC778");
		textField_3.setForeground(UIManager.getColor("FormattedTextField.foreground"));
		textField_3.setFont(new Font("����������_ac", Font.PLAIN, 20));
		textField_3.setBounds(691, 533, 57, 24);
		LoginPanel.add(textField_3);
		
		textField_4 = new JTextPane();
		textField_4.setEditable(false);
		textField_4.setText("\uC544\uC9C1 \uD68C\uC6D0\uC774 \uC544\uB2C8\uC2E0\uAC00\uC694? \uD68C\uC6D0\uAC00\uC785\uD558\uAE30");
		textField_4.setForeground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		textField_4.setFont(new Font("����������_ac", Font.PLAIN, 20));
		textField_4.setBounds(570, 612, 303, 41);
		LoginPanel.add(textField_4);
		
		
		
		
		//Search Text Field
		JTextField Search = new JTextField();
		Search.setBounds(14, 31, 290, 36);
		Search.setFont(new Font("����������_ac Light", Font.PLAIN, 24));
		Search.setHorizontalAlignment(SwingConstants.CENTER);
		Search.setText("\uAC80\uC0C9...");
		Search.setBackground(SystemColor.text);
		BoxPanel.setLayout(null);
		//contentPane.add(Search);
		
		//MyPageButton
		JButton MyPageButton = new JButton("");
		MyPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		MyPageButton.setIcon(new ImageIcon("C:\\Users\\YooTaeHyun\\eclipse-workspace\\CherryBox\\src\\images\\MyPage.png"));
		MyPageButton.setBounds(1367, 12, 41, 41);
		//contentPane.add(MyPageButton);
		
		
		//SearchButton
		JButton SearchButton = new JButton("");
		SearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField_5.setText(Search.getText());
			}
		});
		SearchButton.setIcon(new ImageIcon("C:\\Users\\YooTaeHyun\\eclipse-workspace\\CherryBox\\src\\images\\search.png"));
		SearchButton.setBounds(303, 31, 35, 35);
		BoxPanel.setLayout(null);
		//contentPane.add(SearchButton);
		
		JPanel ProjectPanel = new JPanel();
		ProjectPanel.setBounds(0, 87, 207, 51);
		ProjectPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		ProjectPanel.setBackground(Color.WHITE);
		BoxPanel.add(ProjectPanel);
		ProjectPanel.setLayout(null);
		
		JTextPane txtpnProjects = new JTextPane();
		txtpnProjects.setEditable(false);
		txtpnProjects.setText("Projects");
		txtpnProjects.setFont(new Font("����������_ac ExtraBold", Font.PLAIN, 31));
		txtpnProjects.setBounds(42, 6, 131, 39);
		ProjectPanel.add(txtpnProjects);
		
		JPanel DirPanel = new JPanel();
		DirPanel.setBounds(205, 87, 102, 51);
		DirPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		DirPanel.setBackground(Color.WHITE);
		BoxPanel.add(DirPanel);
		DirPanel.setLayout(null);
		
		JTextPane txtpnDir = new JTextPane();
		txtpnDir.setEditable(false);
		txtpnDir.setText("Dir");
		txtpnDir.setFont(new Font("����������_ac ExtraBold", Font.PLAIN, 31));
		txtpnDir.setBounds(25, 6, 56, 39);
		DirPanel.add(txtpnDir);
		
		JPanel HashtagPanel = new JPanel();
		HashtagPanel.setBounds(305, 87, 102, 51);
		HashtagPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		HashtagPanel.setBackground(Color.WHITE);
		BoxPanel.add(HashtagPanel);
		HashtagPanel.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setFont(new Font("����������_ac ExtraBold", Font.PLAIN, 31));
		textPane.setText("#");
		textPane.setBounds(36, 6, 33, 39);
		HashtagPanel.add(textPane);
		
		JPanel CodePanel = new JPanel();
		CodePanel.setBounds(405, 87, 740, 51);
		CodePanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CodePanel.setBackground(Color.WHITE);
		BoxPanel.add(CodePanel);
		CodePanel.setLayout(null);
		
		JButton HashtagButton = new JButton("");
		HashtagButton.setIcon(new ImageIcon("C:\\Users\\YooTaeHyun\\eclipse-workspace\\CherryBox\\src\\images\\Hashtag.png"));
		HashtagButton.setBounds(6, 6, 40, 40);
		CodePanel.add(HashtagButton);
		
		JButton button = new JButton("");
		button.setSelectedIcon(new ImageIcon("C:\\Users\\YooTaeHyun\\eclipse-workspace\\CherryBox\\src\\images\\\uC218\uC815.png"));
		button.setForeground(Color.WHITE);
		button.setIcon(new ImageIcon("C:\\Users\\YooTaeHyun\\eclipse-workspace\\CherryBox\\src\\images\\\uC218\uC815.png"));
		button.setBounds(58, 6, 40, 40);
		CodePanel.add(button);
		
		JButton button_1 = new JButton("");
		button_1.setIcon(new ImageIcon("C:\\Users\\YooTaeHyun\\eclipse-workspace\\CherryBox\\src\\images\\save.png"));
		button_1.setBounds(112, 8, 40, 40);
		CodePanel.add(button_1);
		
		//�ڵ�˻� ��ư
		JButton button_2 = new JButton("");		
		button_2.setIcon(new ImageIcon("C:\\Users\\YooTaeHyun\\eclipse-workspace\\CherryBox\\src\\images\\test.png"));
		button_2.setBounds(686, 6, 40, 40);
		CodePanel.add(button_2);
		
		JPanel TestResult_panel = new JPanel();
		TestResult_panel.setBounds(1143, 87, 279, 51);
		TestResult_panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		TestResult_panel.setBackground(Color.WHITE);
		BoxPanel.add(TestResult_panel);
		TestResult_panel.setLayout(null);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		textPane_1.setText("\uCF54\uB4DC\uC9C4\uB2E8 \uACB0\uACFC");
		textPane_1.setFont(new Font("����������_ac ExtraBold", Font.PLAIN, 31));
		textPane_1.setBounds(69, 6, 176, 39);
		TestResult_panel.add(textPane_1);
		
		JPanel ProjectContentsPanel = new JPanel();
		ProjectContentsPanel.setBounds(0, 135, 207, 628);
		ProjectContentsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		ProjectContentsPanel.setBackground(Color.WHITE);
		BoxPanel.add(ProjectContentsPanel);
		ProjectContentsPanel.setLayout(null);
		
		JPanel DirContentsPanel = new JPanel();
		DirContentsPanel.setBounds(205, 135, 202, 628);
		DirContentsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		DirContentsPanel.setBackground(Color.WHITE);
		BoxPanel.add(DirContentsPanel);
		DirContentsPanel.setLayout(new CardLayout(0, 0));
		
		JPanel CodeContentsPanel = new JPanel();
		CodeContentsPanel.setBounds(405, 135, 740, 628);
		CodeContentsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CodeContentsPanel.setBackground(Color.WHITE);
		BoxPanel.add(CodeContentsPanel);
		CodeContentsPanel.setLayout(null);
		
		JPanel TestResultContensPanel = new JPanel();
		TestResultContensPanel.setBounds(1143, 135, 279, 628);
		TestResultContensPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		TestResultContensPanel.setBackground(Color.WHITE);
		BoxPanel.add(TestResultContensPanel);
		TestResultContensPanel.setLayout(null);
		
		JTextPane TestResultText = new JTextPane();
		TestResultText.setBounds(14, 12, 251, 604);
		TestResultContensPanel.add(TestResultText);
		
		JPanel MenuPanel = new JPanel();
		MenuPanel.setBounds(0, 0, 1422, 89);
		MenuPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		MenuPanel.setBackground(Color.WHITE);
		BoxPanel.add(MenuPanel);
		MenuPanel.setLayout(null);
		BoxPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{MyPageButton, Search, SearchButton, ProjectPanel, DirPanel, HashtagPanel, CodePanel, TestResult_panel}));
		MenuPanel.add(Search);
		MenuPanel.add(SearchButton);
		MenuPanel.add(MyPageButton);
		
		textField_5 = new JTextField();
		textField_5.setBounds(412, 39, 116, 28);
		MenuPanel.add(textField_5);
		textField_5.setColumns(10);
		
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				TestResultText.setText("�ڵ尡 �Ϻ��մϴ�.");
			}
		});
		
	}
}
