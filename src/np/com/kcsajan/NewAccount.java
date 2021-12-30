package np.com.kcsajan;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import np.com.kcsajan.helper.ConnectionProvider;
import np.com.kcsajan.model.Customer;

public class NewAccount extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel jpNewAccount = new JPanel();

	// auto generated account number
	private Integer accountNumber;

	// Profile picture of customer *** work to do ***

	private Date registeredDate = new Date(System.currentTimeMillis());

	private JLabel lbAccountNumber, lbFullName, lbRegisteredDate, lbBalance;
	private JTextField textAccountNumber, textFullName, textRegisteredDate, textBalance;
	private JButton btnSave, btnCancel;

	public NewAccount() {

		// super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super("Create New Account", false, true, false, true);
		setSize(400, 290);

		jpNewAccount.setBounds(0, 0, 500, 115);
		jpNewAccount.setLayout(null);

		lbAccountNumber = new JLabel("Account No:");
		lbAccountNumber.setForeground(Color.black);
		lbAccountNumber.setBounds(15, 12, 115, 25);

		lbFullName = new JLabel("Full Name:");
		lbFullName.setForeground(Color.black);
		lbFullName.setBounds(15, 67, 115, 25);

		lbRegisteredDate = new JLabel("Registered Date:");
		lbRegisteredDate.setForeground(Color.black);
		lbRegisteredDate.setBounds(15, 121, 115, 25);

		lbBalance = new JLabel("Initial Deposit:");
		lbBalance.setForeground(Color.black);
		lbBalance.setBounds(15, 172, 115, 30);

		accountNumber = GenerateAccountNumber();

		textAccountNumber = new JTextField("" + accountNumber);
		textAccountNumber.setHorizontalAlignment(JTextField.RIGHT);
		textAccountNumber.setEditable(false);
		textAccountNumber.setBounds(173, 10, 205, 30);

		textFullName = new JTextField();
		textFullName.setBounds(173, 65, 205, 30);

		textRegisteredDate = new JTextField(registeredDate.toString());
		textRegisteredDate.setEditable(false);
		textRegisteredDate.setBounds(173, 119, 205, 30);

		textBalance = new JTextField();
		textBalance.setHorizontalAlignment(JTextField.RIGHT);
		textBalance.setBounds(173, 173, 205, 30);

		// Restricting The User Input to only Numerics in Numeric TextBoxes.
		textBalance.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				char c = ke.getKeyChar();
				if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)))) {
					getToolkit().beep();
					ke.consume();
				}
			}
		});

		// Aligning The Buttons.
		btnSave = new JButton("Save");
		btnSave.setBounds(10, 226, 120, 25);
		btnSave.addActionListener(this);

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(258, 226, 120, 25);
		btnCancel.addActionListener(this);

		// Adding the All the Controls to Panel.
		jpNewAccount.add(lbAccountNumber);
		jpNewAccount.add(textAccountNumber);

		jpNewAccount.add(lbFullName);
		jpNewAccount.add(textFullName);

		jpNewAccount.add(lbRegisteredDate);
		jpNewAccount.add(textRegisteredDate);

		jpNewAccount.add(lbBalance);
		jpNewAccount.add(textBalance);

		jpNewAccount.add(btnSave);
		jpNewAccount.add(btnCancel);

		// Adding Panel to Window.
		getContentPane().add(jpNewAccount);

		// Showing the New Account Window.
		setFrameIcon(new ImageIcon("images\\openAccount.png"));
		setVisible(true);
		setLocation(20, 20);
	}

	// Account number generator
	public Integer GenerateAccountNumber() {
		Random random = new Random();
		Integer randomNumber = random.nextInt(999) + 1000;
		if (VerifyAccountNumber(randomNumber)) {
			GenerateAccountNumber(); // regenerating account number if number already exists
		}
		Integer num = Integer.parseInt("10" + randomNumber);
		return num;
	}

	// Check Account number exist or not
	public boolean VerifyAccountNumber(Integer randomNumber) {
		Connection con = ConnectionProvider.getCon();
		try {
			String query = "SELECT customer_id FROM customer WHERE customer_id = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, randomNumber);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Function use By Buttons of Window to Perform Action as User Click Them.
	public void actionPerformed(ActionEvent e) {
		Connection con = ConnectionProvider.getCon();

		Object obj = e.getSource();

		if (obj == btnSave) {
			if (textFullName.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please! Provide Full Name of Customer.",
						"MyBank Pvt. Limited - Error", JOptionPane.PLAIN_MESSAGE);
				textFullName.requestFocus();
			} else if (textBalance.getText().equals("") || Integer.parseInt(textBalance.getText()) < 1000) {
				JOptionPane.showMessageDialog(this, "Amount must be greater than Rs. 1000.",
						"MyBank Pvt. Limited - Error", JOptionPane.PLAIN_MESSAGE);
				textBalance.requestFocus();
			} else {
				String fullName = textFullName.getText();
				Integer balance = Integer.parseInt(textBalance.getText());
				Customer customer = new Customer();
				customer.setAccountNumber(accountNumber);
				customer.setFullName(fullName);
				customer.setBalance(balance);
				customer.setRegisteredDate(registeredDate);
				// Store all inputed value in database and close frame window
				if (Customer.createAccount(con, customer)) {
					textClear();
					dispose();
				}
			}
		}
		if (obj == btnCancel) {
			textClear();
			dispose();
		}
	}

	// Function use to Clear all TextFields of Window.
	public void textClear() {
		textAccountNumber.setText("");
		textFullName.setText("");
		textFullName.requestFocus();
		textBalance.setText("");
	}
}
