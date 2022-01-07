package np.com.kcsajan;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Main implements ActionListener {

	private JFrame frame;
	private JButton btnAtm, btnBank;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {

		frame = new JFrame("<<< MyBank Pvt. Limited >>>");

		GridLayout gridLayout = new GridLayout(1, 2);
		frame.getContentPane().setLayout(gridLayout);

		frame.setSize(600, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon("images\\bank.png");
		frame.setIconImage(icon.getImage());

		btnAtm = new JButton("ATM");
		btnAtm.setBackground(Color.LIGHT_GRAY);
		btnAtm.setFont(new Font("Sitka Heading", Font.BOLD, 40));
		frame.getContentPane().add(btnAtm);
		btnAtm.addActionListener(this);

		btnBank = new JButton("BANK");
		btnBank.setBackground(Color.LIGHT_GRAY);
		btnBank.setFont(new Font("Sitka Heading", Font.BOLD, 40));
		frame.getContentPane().add(btnBank);
		btnBank.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnAtm) {
			frame.setVisible(false);
			new AtmLogin();
		}
		if (obj == btnBank) {
			frame.setVisible(false);
			new Login();
		}
	}
}
