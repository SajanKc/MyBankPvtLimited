package np.com.kcsajan;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MyBank implements ActionListener {

	private JFrame frame;
	private JDesktopPane desktop;

	private JMenu menuEdit, menuWindow, menuHelp;

	private JMenuItem openNewAccount, closeProgram;
	private JMenuItem deposit, withdraw, deleteCustomer, updateCustomer;
	private JMenuItem viewByAccountNumber, viewAllCustomer;
	private JMenuItem windowClose, windowCloseAll;
	private JMenuItem about;

	public MyBank() {
		frame = new JFrame("***MyBank Pvt. Limited ### Developed By Sajan K.C.***");
		frame.setSize(600, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon("images\\bank.png");
		frame.setIconImage(icon.getImage());

		desktop = new JDesktopPane();
		frame.getContentPane().add(desktop, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);

		// Getting the Current System Date.
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss", Locale.getDefault());
		String formatedDate = sdf.format(currDate);

		JLabel welcome = new JLabel("Welcome Today is " + formatedDate + " ", JLabel.RIGHT);
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(welcome);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);

		openNewAccount = new JMenuItem("Open New Account", new ImageIcon("images\\openAccount.png"));
		menuFile.add(openNewAccount);
		openNewAccount.addActionListener(this);

		closeProgram = new JMenuItem("Exit", new ImageIcon("images\\closeApp.png"));
		menuFile.add(closeProgram);
		closeProgram.addActionListener(this);

		menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);

		deposit = new JMenuItem("Deposit", new ImageIcon("images\\deposit.png"));
		menuEdit.add(deposit);
		deposit.addActionListener(this);

		withdraw = new JMenuItem("Withdraw", new ImageIcon("images\\withdraw.png"));
		menuEdit.add(withdraw);
		withdraw.addActionListener(this);

		updateCustomer = new JMenuItem("Update Customer", new ImageIcon("images\\update.png"));
		menuEdit.add(updateCustomer);
		updateCustomer.addActionListener(this);

		deleteCustomer = new JMenuItem("Delete Customer", new ImageIcon("images\\delete.png"));
		menuEdit.add(deleteCustomer);
		deleteCustomer.addActionListener(this);

		JMenu menuView = new JMenu("View");
		menuBar.add(menuView);

		viewByAccountNumber = new JMenuItem("View By Account Number", new ImageIcon("images\\viewByAccNumber.png"));
		menuView.add(viewByAccountNumber);
		viewByAccountNumber.addActionListener(this);

		viewAllCustomer = new JMenuItem("Vew All Customers", new ImageIcon("images\\viewAll.png"));
		menuView.add(viewAllCustomer);
		viewAllCustomer.addActionListener(this);

		menuWindow = new JMenu("Window");
		menuBar.add(menuWindow);

		windowClose = new JMenuItem("Close Active Window", new ImageIcon("images\\close.png"));
		menuWindow.add(windowClose);
		windowClose.addActionListener(this);

		windowCloseAll = new JMenuItem("Close All Active Window", new ImageIcon("images\\closeAll.png"));
		menuWindow.add(windowCloseAll);
		windowCloseAll.addActionListener(this);

		menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);

		about = new JMenuItem("About", new ImageIcon("images\\about.png"));
		menuHelp.add(about);
		about.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();

		if (obj == openNewAccount) {
			boolean b = openChildWindow("Create New Account");
			if (b == false) {
				NewAccount newAcc = new NewAccount();
				desktop.add(newAcc);
				newAcc.show();
			}
		} else if (obj == closeProgram) {
			exitApplication();
		} else if (obj == deposit) {
			boolean b = openChildWindow("Deposit");
			if (b == false) {
				Deposit deposit = new Deposit();
				desktop.add(deposit);
				deposit.show();
			}
		} else if (obj == withdraw) {
			boolean b = openChildWindow("Withdraw");
			if (b == false) {
				Withdraw withdraw = new Withdraw();
				desktop.add(withdraw);
				withdraw.show();
			}
		} else if (obj == updateCustomer) {
			boolean b = openChildWindow("Update Customer");
			if (b == false) {
				UpdateCustomer updateCustomer = new UpdateCustomer();
				desktop.add(updateCustomer);
				updateCustomer.show();
			}
		} else if (obj == deleteCustomer) {
			boolean b = openChildWindow("Delete Customer");
			if (b == false) {
				DeleteCustomer deleteCustomer = new DeleteCustomer();
				desktop.add(deleteCustomer);
				deleteCustomer.show();
			}
		} else if (obj == viewByAccountNumber) {
			boolean b = openChildWindow("View By Account Number");
			if (b == false) {
				ViewByAccountNumber viewByAccNumber = new ViewByAccountNumber();
				desktop.add(viewByAccNumber);
				viewByAccNumber.show();
			}
		} else if (obj == viewAllCustomer) {
			boolean b = openChildWindow("View All Customer");
			if (b == false) {
				ViewAllCustomer viewAll = new ViewAllCustomer();
				desktop.add(viewAll);
				viewAll.show();
			}
		} else if (obj == windowClose) {
			try {
				desktop.getSelectedFrame().setClosed(true);
			} catch (Exception CloseExc) {
			}
		} else if (obj == windowCloseAll) {
			// Getting all Open Frames
			JInternalFrame Frames[] = desktop.getAllFrames();
			for (int i = 0; i < Frames.length; i++) {
				try {
					Frames[i].setClosed(true);
				} catch (Exception CloseExc) {
				}
			}
		} else if (obj == about) {
			About about = new About();
			desktop.add(about);
			about.show();
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

	private void exitApplication() {
		try {
			int reply = JOptionPane.showConfirmDialog(frame, "Are you really want to exit?",
					"MyBank Pvt. Limited - Exit", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (reply == JOptionPane.YES_OPTION) {
				frame.setVisible(false);
				frame.dispose();
				System.out.println("Thanks for Using MyBank Pvt. Limited.\nDesigned & Developed by - Sajan K.C.");
				System.exit(0);
			} else if (reply == JOptionPane.NO_OPTION) {
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
