package np.com.kcsajan;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import np.com.kcsajan.helper.ConnectionProvider;

public class Login implements ActionListener {

	private JFrame frame;
	private JTextField textUserName;
	private JPasswordField passwordPIN;
	private JButton btnLogin;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("***MyBank Pvt. Limited ### Login***");
		frame.getContentPane().setLayout(null);
		frame.setSize(600, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon("images\\bank.png");
		frame.setIconImage(icon.getImage());

		JLabel bankName = new JLabel("MyBank Pvt. Limited");
		bankName.setFont(new Font("Viner Hand ITC", Font.PLAIN, 40));
		bankName.setBounds(80, 50, 450, 80);
		frame.getContentPane().add(bankName);

		JLabel lbUserName = new JLabel("UserName :");
		lbUserName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbUserName.setBounds(96, 170, 138, 25);
		frame.getContentPane().add(lbUserName);

		textUserName = new JTextField();
		textUserName.setBounds(333, 171, 150, 25);
		frame.getContentPane().add(textUserName);
		textUserName.setColumns(10);

		JLabel lbPIN = new JLabel("PIN :");
		lbPIN.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbPIN.setBounds(96, 209, 138, 25);
		frame.getContentPane().add(lbPIN);

		passwordPIN = new JPasswordField();
		passwordPIN.setBounds(333, 210, 150, 25);
		frame.getContentPane().add(passwordPIN);

		passwordPIN.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				char c = ke.getKeyChar();
				if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)))) {
					ke.consume();
				}
			}
		});

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLogin.setBounds(201, 275, 114, 44);
		btnLogin.addActionListener(this);
		frame.getContentPane().add(btnLogin);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnLogin) {
			Connection con = ConnectionProvider.getCon();
			String userName = textUserName.getText();
			char[] pin = passwordPIN.getPassword();
			String tPin = String.valueOf(pin);

			try {
				String query = "SELECT * FROM teller WHERE user_name = ? && pin = ?";
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, userName);
				stmt.setString(2, tPin);
				ResultSet result = stmt.executeQuery();

				if (result.next()) {
					frame.setVisible(false);
					new MyBank();
				} else {
					JOptionPane.showMessageDialog(null, "UserName or Password Incorrect !!!",
							"MyBank Pvt. Limited - Error", JOptionPane.PLAIN_MESSAGE);
				}
			} catch (Exception error) {
				error.printStackTrace();
			}
		}
	}
}
