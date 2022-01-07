package np.com.kcsajan.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class Customer {

	private Integer customerIid;
	private Integer accountNumber;
	private String fullName;
	private Integer balance;
	private Date registeredDate;

	public Customer() {
	}

	public Integer getCustomerIid() {
		return customerIid;
	}

	public void setCustomerIid(Integer customerIid) {
		this.customerIid = customerIid;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public static boolean createAccount(Connection con, Customer customer) {

		try {
			String query = "INSERT INTO customer(account_number,full_name,balance,registered_date) VALUES(?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, customer.getAccountNumber());
			stmt.setString(2, customer.getFullName());
			stmt.setInt(3, customer.getBalance());
			stmt.setString(4, customer.getRegisteredDate().toString());

			int result = stmt.executeUpdate();
			if (result == 1) {
				JOptionPane.showMessageDialog(null, "New Customer Created Successfully !!!",
						"MyBank Pvt. Limited - Success", JOptionPane.PLAIN_MESSAGE);
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Something went wrong !!!", "MyBank Pvt. Limited - Error",
						JOptionPane.PLAIN_MESSAGE);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void DepositMoney(Connection con, Integer accNumber, Integer balance) {
		try {
			Integer oldBalance = 0, newBalance;
			// Getting old balance
			String getBalanceQuery = "SELECT balance FROM CUSTOMER WHERE account_number = ?";
			PreparedStatement getBalanceStatement = con.prepareStatement(getBalanceQuery);
			getBalanceStatement.setInt(1, accNumber);
			ResultSet resultSet = getBalanceStatement.executeQuery();
			if (resultSet.next()) {
				oldBalance = Integer.parseInt(resultSet.getString("balance"));
			}
			newBalance = oldBalance + balance;

			// Updating new balance
			String updateQuery = "UPDATE customer SET balance = ? WHERE account_number = ?";
			PreparedStatement balanceStatement = con.prepareStatement(updateQuery);
			balanceStatement.setInt(1, newBalance);
			balanceStatement.setInt(2, accNumber);

			balanceStatement.executeUpdate();

			JOptionPane.showMessageDialog(null, "Your Rs. " + balance + " deposited successfully !!!",
					"MyBank Pvt. Limited - Success", JOptionPane.PLAIN_MESSAGE);

		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	public static void WithdrawMoney(Connection con, Integer accNumber, Integer balance) {
		Integer totalAmount;

		try {
			String query = "SELECT balance FROM customer WHERE account_number = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, accNumber);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				totalAmount = Integer.parseInt(result.getString("balance"));

				if (totalAmount < balance) {
					JOptionPane.showMessageDialog(null, "You don't have sufficient balance !!!",
							"MyBank Pvt. Limited - Error", JOptionPane.PLAIN_MESSAGE);
				} else {
					Integer remainingBalance = totalAmount - balance;
					String updateQuery = "UPDATE customer SET balance = ? WHERE account_number = ?";
					PreparedStatement balanceStatement = con.prepareStatement(updateQuery);
					balanceStatement.setInt(1, remainingBalance);
					balanceStatement.setInt(2, accNumber);

					balanceStatement.executeUpdate();

					JOptionPane.showMessageDialog(null, "Collect your money !!!\nRs. " + balance,
							"MyBank Pvt. Limited - Success", JOptionPane.PLAIN_MESSAGE);
				}
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	public static void WithdrawFromAtm(Connection con, Integer cardNumber, Integer balance) {
		Integer totalAmount, accountNumber;

		try {
			String query = "SELECT balance, account_number  FROM customer JOIN card ON customer.customer_id = card.customer_id AND card_number = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, cardNumber);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				totalAmount = Integer.parseInt(result.getString("balance"));
				accountNumber = Integer.parseInt(result.getString("account_number"));

				if (totalAmount < balance) {
					JOptionPane.showMessageDialog(null, "You don't have sufficient balance !!!",
							"MyBank Pvt. Limited - Error", JOptionPane.PLAIN_MESSAGE);
				} else {
					Integer remainingBalance = totalAmount - balance;
					String updateQuery = "UPDATE customer SET balance = ? WHERE account_number = ?";
					PreparedStatement balanceStatement = con.prepareStatement(updateQuery);
					balanceStatement.setInt(1, remainingBalance);
					balanceStatement.setInt(2, accountNumber);

					balanceStatement.executeUpdate();

					JOptionPane.showMessageDialog(null, "Collect your money !!!\nRs. " + balance,
							"MyBank Pvt. Limited - Success", JOptionPane.PLAIN_MESSAGE);
				}
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	public static void UpdateCustomer(Connection con, Integer accNumber, String fullName) {
		try {
			String updateQuery = "UPDATE customer SET full_name = ? WHERE account_number = ?";
			PreparedStatement stmt = con.prepareStatement(updateQuery);
			stmt.setString(1, fullName);
			stmt.setInt(2, accNumber);

			stmt.executeUpdate();

			JOptionPane.showMessageDialog(null, "Your name updated successfully !!!", "MyBank Pvt. Limited - Success",
					JOptionPane.PLAIN_MESSAGE);

		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	public static void DeleteCustomer(Connection con, Integer accNumber) {
		try {
			String query = "DELETE FROM customer WHERE account_number = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, accNumber);
			stmt.executeUpdate();

			JOptionPane.showMessageDialog(null, "Account Deleted Successfully", "MyBank Pvt. Limited - Success",
					JOptionPane.PLAIN_MESSAGE);
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	public static void WithdrawAllMoney(Connection con, Integer accNumber) {
		try {
			String query = "SELECT balance FROM customer WHERE account_number = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, accNumber);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				Integer totalAmount = Integer.parseInt(result.getString("balance"));

				JOptionPane.showMessageDialog(null, "Collect your all money !!!\nRs. " + totalAmount,
						"MyBank Pvt. Limited - Success", JOptionPane.PLAIN_MESSAGE);
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	public static boolean VerifyAccount(Connection con, Integer accountNumber) {
		try {
			String query = "SELECT account_number FROM customer WHERE account_number = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, accountNumber);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				return true;
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
		return false;
	}
}