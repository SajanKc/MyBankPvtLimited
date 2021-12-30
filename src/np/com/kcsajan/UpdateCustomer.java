package np.com.kcsajan;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import np.com.kcsajan.helper.ConnectionProvider;
import np.com.kcsajan.model.Customer;

public class UpdateCustomer extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel jpUpdateCustomer = new JPanel();

	private JLabel lbAccountNumber, lbFullName, lbRegisteredDate, lbBalance;
	private JTextField textAccountNumber, textFullName, textRegisteredDate, textBalance;
	private JButton btnVerifyAccount, btnSave, btnCancel;

	public UpdateCustomer() {

		// super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super("Update Customer", false, true, false, true);
		setSize(350, 300);

		jpUpdateCustomer.setBounds(0, 0, 500, 115);
		jpUpdateCustomer.setLayout(null);

		lbAccountNumber = new JLabel("Account No:");
		lbAccountNumber.setForeground(Color.black);
		lbAccountNumber.setBounds(15, 15, 80, 25);

		lbFullName = new JLabel("Full Name:");
		lbFullName.setForeground(Color.black);
		lbFullName.setBounds(15, 118, 80, 25);

		lbRegisteredDate = new JLabel("Registered Date:");
		lbRegisteredDate.setForeground(Color.black);
		lbRegisteredDate.setBounds(15, 158, 98, 25);

		lbBalance = new JLabel("Amount:");
		lbBalance.setForeground(Color.black);
		lbBalance.setBounds(15, 195, 80, 30);

		textAccountNumber = new JTextField();
		textAccountNumber.setHorizontalAlignment(JTextField.RIGHT);
		textAccountNumber.setBounds(123, 10, 205, 30);

		textFullName = new JTextField();
		textFullName.setEditable(false);
		textFullName.setBounds(123, 116, 205, 30);

		textRegisteredDate = new JTextField();
		textRegisteredDate.setEditable(false);
		textRegisteredDate.setBounds(123, 156, 205, 30);

		textBalance = new JTextField();
		textBalance.setHorizontalAlignment(JTextField.RIGHT);
		textBalance.setEditable(false);
		textBalance.setBounds(123, 196, 205, 30);

		// Restricting The User Input to only Numerics in Numeric TextBoxes.
		textAccountNumber.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				char c = ke.getKeyChar();
				if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)))) {
					getToolkit().beep();
					ke.consume();
				}
			}
		});

		// Aligning The Buttons.
		btnVerifyAccount = new JButton("Verify Account");
		btnVerifyAccount.setBounds(90, 50, 150, 56);
		btnVerifyAccount.addActionListener(this);

		btnSave = new JButton("Update");
		btnSave.setBounds(15, 236, 120, 25);
		btnSave.addActionListener(this);

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(208, 236, 120, 25);
		btnCancel.addActionListener(this);

		// Adding the All the Controls to Panel.
		jpUpdateCustomer.add(lbAccountNumber);
		jpUpdateCustomer.add(textAccountNumber);
		jpUpdateCustomer.add(btnVerifyAccount);

		jpUpdateCustomer.add(lbFullName);
		jpUpdateCustomer.add(textFullName);

		jpUpdateCustomer.add(lbRegisteredDate);
		jpUpdateCustomer.add(textRegisteredDate);

		jpUpdateCustomer.add(lbBalance);
		jpUpdateCustomer.add(textBalance);

		jpUpdateCustomer.add(btnSave);
		jpUpdateCustomer.add(btnCancel);

		// Adding Panel to Window.
		getContentPane().add(jpUpdateCustomer);

		// Showing the New Account Window.
		setFrameIcon(new ImageIcon("images\\update.png"));
		setVisible(true);
		setLocation(20, 20);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Connection con = ConnectionProvider.getCon();
		Object obj = e.getSource();
		Integer accountNumber = Integer.parseInt(textAccountNumber.getText());
		String fullName, newFullName = "", balance;
		Date registeredDate;

		if (obj == btnVerifyAccount) {
			if (textAccountNumber.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please! Provide Id of Customer.", "MyBank - EmptyField",
						JOptionPane.PLAIN_MESSAGE);
			} else {
				if (Customer.VerifyAccount(con, accountNumber)) {

					try {
						String query = "SELECT full_name,registered_date,balance FROM customer WHERE account_number = ?";
						PreparedStatement stmt = con.prepareStatement(query);
						stmt.setInt(1, accountNumber);
						ResultSet result = stmt.executeQuery();
						if (result.next()) {
							fullName = result.getString("full_name");
							registeredDate = result.getDate("registered_date");
							balance = result.getString("balance");

							textFullName.setText(fullName);
							textFullName.setEditable(true);

							textRegisteredDate.setText(registeredDate.toString());
							textBalance.setText(balance);

						}
					} catch (Exception error) {
						error.printStackTrace();
					}
				} else {
					textClear();
					JOptionPane.showMessageDialog(this, "Account Not Found", "MyBank Pvt. Limited - Error",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		}

		if (obj == btnSave) {
			if (textAccountNumber.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please! Provide Id of Customer.", "MyBank - EmptyField",
						JOptionPane.PLAIN_MESSAGE);
				textAccountNumber.requestFocus();
			} else if (textBalance.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please! Provide withdraw Amount.", "MyBank - EmptyField",
						JOptionPane.PLAIN_MESSAGE);
				textBalance.requestFocus();
			} else {
				newFullName = textFullName.getText();
				if (!newFullName.equals("")) {
					Customer.UpdateCustomer(con, accountNumber, newFullName);
					textFullName.setText("");
					textFullName.setEditable(false);
					textRegisteredDate.setText("");
					textBalance.setText("");
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
		textBalance.setText("");
		textAccountNumber.requestFocus();
	}
}
