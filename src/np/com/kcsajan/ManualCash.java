package np.com.kcsajan;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import np.com.kcsajan.helper.ConnectionProvider;
import np.com.kcsajan.model.Customer;

public class ManualCash extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel jpManualCash = new JPanel();
	private JButton btnManualWithdraw;
	private JTextField textAmount;

	public Integer cardNumber;

	public ManualCash(Integer cardNumber) {
		this();
		this.cardNumber = cardNumber;
	}

	public ManualCash() {
		// super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super("Manual Cash", false, true, false, true);
		setSize(350, 300);

		jpManualCash.setBounds(0, 0, 500, 150);
		jpManualCash.setLayout(null);

		// Adding Panel to Window.
		getContentPane().add(jpManualCash);

		JLabel lblAmount = new JLabel("Enter Your Amount");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAmount.setBounds(10, 10, 130, 25);
		jpManualCash.add(lblAmount);

		textAmount = new JTextField();
		textAmount.setBounds(150, 13, 178, 25);
		jpManualCash.add(textAmount);
		textAmount.setColumns(10);

		textAmount.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				char c = ke.getKeyChar();
				if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)))) {
					getToolkit().beep();
					ke.consume();
				}
			}
		});

		btnManualWithdraw = new JButton("Withdraw");
		btnManualWithdraw.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnManualWithdraw.setBounds(100, 70, 114, 34);
		jpManualCash.add(btnManualWithdraw);
		btnManualWithdraw.addActionListener(this);

		// Showing the New Account Window.
		setFrameIcon(new ImageIcon("images\\bank.png"));
		setVisible(true);
		setLocation(20, 20);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Connection con = ConnectionProvider.getCon();

		Object obj = e.getSource();
		if (obj == btnManualWithdraw) {
			Integer amount = Integer.parseInt(textAmount.getText().toString());
			if (amount % 500 == 0 && amount < 50000) {
				Customer.WithdrawFromAtm(con, cardNumber, amount);
			} else {
				JOptionPane.showMessageDialog(this, "Amount must be multiple of Rs. 500 and Less than Rs. 50000",
						"MyBank - Invalid Amount", JOptionPane.PLAIN_MESSAGE);
			}
			super.dispose();
		}
	}
}
