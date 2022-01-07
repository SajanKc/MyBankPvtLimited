package np.com.kcsajan;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Atm implements ActionListener {

	private JFrame frame;
	private JDesktopPane desktop;
	private JInternalFrame atmOptionFrame;

	private JButton btnFastCash, btnManualCash;

	public Integer cardNumber;

	public Atm(Integer cardNumber) {
		this.cardNumber = cardNumber;

		frame = new JFrame("***MyBank Pvt. Limited ### ATM***");
		frame.setSize(600, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon("images\\bank.png");
		frame.setIconImage(icon.getImage());

		desktop = new JDesktopPane();
		frame.getContentPane().add(desktop, BorderLayout.CENTER);

		atmOptionFrame = new JInternalFrame("ATM >>> Options");
		atmOptionFrame.setBounds(10, 10, 565, 300);
		desktop.add(atmOptionFrame);
		atmOptionFrame.setVisible(true);
		atmOptionFrame.getContentPane().setLayout(new GridLayout(1, 2, 0, 0));

		btnFastCash = new JButton("Fast Cash");
		btnFastCash.setFont(new Font("Sitka Heading", Font.BOLD, 24));
		atmOptionFrame.getContentPane().add(btnFastCash);
		btnFastCash.addActionListener(this);

		btnManualCash = new JButton("Manual Cash");
		btnManualCash.setFont(new Font("Sitka Heading", Font.BOLD, 24));
		atmOptionFrame.getContentPane().add(btnManualCash);
		btnManualCash.addActionListener(this);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);

		// Getting the Current System Date.
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss", Locale.getDefault());
		String formatedDate = sdf.format(currDate);

		JLabel welcome = new JLabel("Welcome Today is " + formatedDate + " ", JLabel.RIGHT);
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(welcome);
	}

	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();

		if (obj == btnFastCash) {
			boolean b = openChildWindow("Fast Cash");
			if (b == false) {
				atmOptionFrame.dispose();
				FastCash fastCash = new FastCash(cardNumber);
				desktop.add(fastCash);
				fastCash.show();
			}
		} else if (obj == btnManualCash) {
			boolean b = openChildWindow("Manual Cash");
			if (b == false) {
				atmOptionFrame.dispose();
				ManualCash manualCash = new ManualCash(cardNumber);
				desktop.add(manualCash);
				manualCash.show();
			}
		}
	}

	private boolean openChildWindow(String title) {
		JInternalFrame[] childs = desktop.getAllFrames();
		for (int i = 0; i < childs.length; i++) {
			if (childs[i].getTitle().equalsIgnoreCase(title)) {
				childs[i].show();
				return true;
			}
		}
		return false;
	}
}
