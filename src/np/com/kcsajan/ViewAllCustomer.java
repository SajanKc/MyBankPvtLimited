package np.com.kcsajan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import np.com.kcsajan.helper.ConnectionProvider;

public class ViewAllCustomer extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel jpViewByName = new JPanel();

	private JButton btnSearch;

	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel model;

	public ViewAllCustomer() {

		// super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super("View All Customer", false, true, true, true);
		setSize(565, 400);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 533, 303);
		jpViewByName.add(scrollPane);

		jpViewByName.setBounds(0, 0, 500, 115);
		jpViewByName.setLayout(null);

		// Aligning The Buttons.
		btnSearch = new JButton("Display All Customer");
		btnSearch.setBounds(200, 10, 150, 30);
		btnSearch.addActionListener(this);

		jpViewByName.add(btnSearch);

		// Adding Panel to Window.
		getContentPane().add(jpViewByName);

		// Showing the New Account Window.
		setFrameIcon(new ImageIcon("images\\viewAll.png"));
		setVisible(true);
		setLocation(10, 10);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Connection con = ConnectionProvider.getCon();
		Object obj = e.getSource();

		if (obj == btnSearch) {
			String columnFields[] = { "ID", "Account No.", "Full Name", "Registered Date", "Balance" };

			table = new JTable();
			model = new DefaultTableModel();
			model.setColumnIdentifiers(columnFields);
			table.setModel(model);

			try {
				String query = "SELECT * FROM customer";
				Statement stmt = con.createStatement();
				ResultSet result = stmt.executeQuery(query);

				int i = 0;
				while (result.next()) {
					String cid = result.getString("customer_id");
					String acNum = result.getString("account_number");
					String fName = result.getString("full_name");
					String rDate = result.getString("registered_date");
					String balance = result.getString("balance");
					model.addRow(new Object[] { cid, acNum, fName, rDate, balance });
					i++;
				}
				if (i < 0) {
					JOptionPane.showMessageDialog(this, "No Customer Record Found", "MyBank Pvt. Limited - Error",
							JOptionPane.PLAIN_MESSAGE);
				}
				scrollPane.setViewportView(table);

			} catch (Exception error) {
				error.printStackTrace();
			}
		}
	}
}
