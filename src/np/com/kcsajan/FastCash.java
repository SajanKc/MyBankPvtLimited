package np.com.kcsajan;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import np.com.kcsajan.helper.ConnectionProvider;
import np.com.kcsajan.model.Customer;

public class FastCash extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel jpFastCash = new JPanel();

	private JButton btnFiveHundred, btnOneThousand, btnTwoThousand, btnFiveThousand, btnTenThousand, btnTwentyThousand;

	public Integer cardNumber;

	public FastCash(Integer cardNumber) {
		this();
		this.cardNumber = cardNumber;
	}

	public FastCash() {
		// super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super("Fast Cash", false, true, false, true);
		setSize(350, 300);

		jpFastCash.setBounds(0, 0, 500, 150);
		jpFastCash.setLayout(null);

		// Adding Panel to Window.
		getContentPane().add(jpFastCash);

		btnFiveHundred = new JButton("Rs. 500");
		btnFiveHundred.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnFiveHundred.setBounds(10, 10, 150, 35);
		jpFastCash.add(btnFiveHundred);
		btnFiveHundred.addActionListener(this);

		btnOneThousand = new JButton("Rs. 1,000");
		btnOneThousand.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnOneThousand.setBounds(178, 10, 150, 35);
		jpFastCash.add(btnOneThousand);
		btnOneThousand.addActionListener(this);

		btnTwoThousand = new JButton("Rs. 2,000");
		btnTwoThousand.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnTwoThousand.setBounds(10, 80, 150, 35);
		jpFastCash.add(btnTwoThousand);
		btnTwoThousand.addActionListener(this);

		btnFiveThousand = new JButton("Rs. 5,000");
		btnFiveThousand.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnFiveThousand.setBounds(178, 80, 150, 35);
		jpFastCash.add(btnFiveThousand);
		btnFiveThousand.addActionListener(this);

		btnTenThousand = new JButton("Rs. 10,000");
		btnTenThousand.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnTenThousand.setBounds(10, 155, 150, 35);
		jpFastCash.add(btnTenThousand);
		btnTenThousand.addActionListener(this);

		btnTwentyThousand = new JButton("Rs. 20,000");
		btnTwentyThousand.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnTwentyThousand.setBounds(178, 155, 150, 35);
		jpFastCash.add(btnTwentyThousand);
		btnTwentyThousand.addActionListener(this);

		// Showing the New Account Window.
		setFrameIcon(new ImageIcon("images\\bank.png"));
		setVisible(true);
		setLocation(20, 20);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Connection con = ConnectionProvider.getCon();

		Object obj = e.getSource();

		if (obj == btnFiveHundred) {
			Customer.WithdrawFromAtm(con, cardNumber, 500);
		} else if (obj == btnOneThousand) {
			Customer.WithdrawFromAtm(con, cardNumber, 1000);
		} else if (obj == btnTwoThousand) {
			Customer.WithdrawFromAtm(con, cardNumber, 2000);
		} else if (obj == btnFiveThousand) {
			Customer.WithdrawFromAtm(con, cardNumber, 5000);
		} else if (obj == btnTenThousand) {
			Customer.WithdrawFromAtm(con, cardNumber, 10000);
		} else if (obj == btnTwentyThousand) {
			Customer.WithdrawFromAtm(con, cardNumber, 20000);
		} else {
		}
		super.dispose();
	}
}
