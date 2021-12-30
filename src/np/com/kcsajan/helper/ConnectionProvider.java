package np.com.kcsajan.helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {

	private static Connection con;

	public static Connection getCon() {
		try {
			if (con == null) {
				String url = "jdbc:mysql://localhost:3306/mybank_pvt_ltd";
				String username = "root";
				String pwd = "MySql@123";
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection(url, username, pwd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
