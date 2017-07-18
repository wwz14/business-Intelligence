package ETL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLCon {
	private static Connection conn;
	public static  String DRIVER = "com.mysql.jdbc.Driver";

	public SQLCon(String databasename) {
		buildConnect(databasename);
	}

	public void buildConnect(String databasename) {
    //在conn中修改用户名和密码
	try {		
		Class.forName(DRIVER);
		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+databasename+"?user=root&password=12345a&useUnicode=true&characterEncoding=UTF8");
		if (!conn.isClosed()) {
			System.out.println("Succeeded connecting to the Database!");
		}
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConn() {
		return conn;
	}
}
