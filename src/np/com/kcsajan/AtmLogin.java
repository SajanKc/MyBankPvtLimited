package np.com.kcsajan;

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

public class AtmLogin implements ActionListener {

	private JFrame frame;
	private JTextField textCardNumber;
	private JPasswordField passwordPIN;
	private JButton btnLogin;

	public AtmLogin() {
		frame = new JFrame("***MyBank Pvt. Limited ### ATM Login***");
		frame.setVisible(true);
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

		JLabel lbCardNumber = new JLabel("Card Number :");
		lbCardNumber.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbCardNumber.setBounds(96, 170, 138, 25);
		frame.getContentPane().add(lbCardNumber);

		textCardNumber = new JTextField();
		textCardNumber.setBounds(333, 171, 150, 25);
		frame.getContentPane().add(textCardNumber);
		textCardNumber.setColumns(10);

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
			String cardNumber = textCardNumber.getText();
			char[] pin = passwordPIN.getPassword();
			String tPin = String.valueOf(pin);

			try {
				String query = "SELECT * FROM card WHERE card_number = ? && pin = ?";
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, cardNumber);
				stmt.setString(2, tPin);
				ResultSet result = stmt.executeQuery();

				if (result.next()) {
					frame.setVisible(false);
					new Atm(Integer.parseInt(cardNumber));
				} else {
					JOptionPane.showMessageDialog(null, "Card Number or PIN Incorrect !!!",
							"MyBank Pvt. Limited - Error", JOptionPane.PLAIN_MESSAGE);
				}
			} catch (Exception error) {
				error.printStackTrace();
			}
		}
	}
}
