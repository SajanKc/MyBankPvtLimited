package np.com.kcsajan;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import np.com.kcsajan.helper.ConnectionProvider;
import np.com.kcsajan.model.Customer;

public class ViewByAccountNumber extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel jpViewByAccountNumber = new JPanel();

	private JLabel lbAccountNumber;
	private JTextField textAccountNumber;
	private JButton btnSearch;

	private JScrollPane scrollPane;
	private JTable table;

	public ViewByAccountNumber() {

		// super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super("View By Account Number", false, true, true, true);
		setSize(565, 400);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 533, 283);
		jpViewByAccountNumber.add(scrollPane);

		jpViewByAccountNumber.setBounds(0, 0, 500, 115);
		jpViewByAccountNumber.setLayout(null);

		lbAccountNumber = new JLabel("Account No:");
		lbAccountNumber.setForeground(Color.black);
		lbAccountNumber.setBounds(10, 10, 80, 25);

		textAccountNumber = new JTextField();
		textAccountNumber.setHorizontalAlignment(JTextField.RIGHT);
		textAccountNumber.setBounds(100, 10, 205, 30);

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
		btnSearch = new JButton("Search");
		btnSearch.setBounds(315, 10, 150, 30);
		btnSearch.addActionListener(this);

		// Adding the All the Controls to Panel.
		jpViewByAccountNumber.add(lbAccountNumber);
		jpViewByAccountNumber.add(textAccountNumber);
		jpViewByAccountNumber.add(btnSearch);

		// Adding Panel to Window.
		getContentPane().add(jpViewByAccountNumber);

		// Showing the New Account Window.
		setFrameIcon(new ImageIcon("images\\viewByAccNumber.png"));
		setVisible(true);
		setLocation(10, 10);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Connection con = ConnectionProvider.getCon();
		Object obj = e.getSource();
		Integer accountNumber = Integer.parseInt(textAccountNumber.getText());

		if (obj == btnSearch) {
			String[][] customerData = new String[1][5];
			String columnFields[] = { "ID", "Account No.", "Full Name", "Registered Date", "Balance" };

			if (textAccountNumber.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please! Provide Account Number of Customer.",
						"MyBank Pvt. Limited - EmptyField", JOptionPane.PLAIN_MESSAGE);
			} else {
				table = new JTable();
				if (Customer.VerifyAccount(con, accountNumber)) {

					try {
						String query = "SELECT * FROM customer WHERE account_number = ?";
						PreparedStatement stmt = con.prepareStatement(query);
						stmt.setInt(1, accountNumber);
						ResultSet result = stmt.executeQuery();

						if (result.next()) {
							customerData[0][0] = result.getString("customer_id");
							customerData[0][1] = result.getString("account_number");
							customerData[0][2] = result.getString("full_name");
							customerData[0][3] = result.getString("registered_date");
							customerData[0][4] = result.getString("balance");

							table.setModel(new DefaultTableModel(customerData, columnFields));
							scrollPane.setViewportView(table);
						}
					} catch (Exception error) {
						error.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(this, "Account Not Found", "MyBank Pvt. Limited - Error",
							JOptionPane.PLAIN_MESSAGE);
					textClear();
				}
			}
		}
	}

	// Function use to Clear all TextFields of Window.
	public void textClear() {
		textAccountNumber.setText("");
		textAccountNumber.requestFocus();
	}
}
