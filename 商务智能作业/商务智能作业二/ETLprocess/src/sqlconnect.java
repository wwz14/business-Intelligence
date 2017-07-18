import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sqlconnect {
	
	// 驱动程序名
	public static  String DRIVER = "com.mysql.jdbc.Driver";
	// URL指向要访问的数据库名nbalabala
	//public static  String URL = "jdbc:mysql://127.0.0.1:3306/Train";
	//public static  String databaseName = "Train";
	//// 用户名
	//public static  String USER = "root";
	//// 密码
	//public static  String PASSWORD = "861910";

	/***如果要修改用户名，就改虾面jdbc:mysql://127.0.0.1:3306/Train?user=root&password=861910&useUnicode=true&characterEncoding=UTF8,上面不用了"**/

	private static Connection conn;

	public sqlconnect() {
		buildConnect();
	}

	public void buildConnect() {

	try {
		//加载驱动，这一句也可写为：Class.forName("com.mysql.jdbc.Driver");
		Class.forName(DRIVER);
		//建立到MySQL的连接
//		conn = DriverManager.getConnection(URL,USER, PASSWORD);
		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Humanresource_DB?user=root&password=12345a&useUnicode=true&characterEncoding=UTF8");
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
