package np.com.kcsajan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import np.com.kcsajan.model.Bank;

public class About extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel jpAbout = new JPanel();
	private JButton btnBankDetails;

	public About() {

		// super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super("About", false, true, false, true);
		setSize(450, 355);

		jpAbout.setBounds(0, 0, 500, 115);

		JLabel profile = new JLabel(new ImageIcon("images\\pp.png"));
		profile.setLocation(100, 10);
		profile.setSize(250, 250);
		jpAbout.add(profile);

		// Adding Panel to Window.
		getContentPane().add(jpAbout);
		jpAbout.setLayout(null);

		JLabel lbName = new JLabel("Design & Developed by: Sajan K.C.");
		lbName.setBounds(127, 264, 207, 20);
		jpAbout.add(lbName);

		btnBankDetails = new JButton("Show Bank Details");
		btnBankDetails.setBounds(151, 294, 160, 21);
		jpAbout.add(btnBankDetails);
		btnBankDetails.addActionListener(this);

		// Showing the New Account Window.
		setFrameIcon(new ImageIcon("images\\about.png"));
		setVisible(true);
		setLocation(20, 20);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnBankDetails) {
			String msg = "Name: " + Bank.bankName + "\n\nLocation: " + Bank.bankLocation
					+ "\n\nWebsite: https://kcsajan.com.np";
			JOptionPane.showMessageDialog(this, msg, "About MyBank Pvt. Ltd.", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
