import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import ETL.SQLCon;

public class initializeDB {
	private Connection conn ;
	private String[] date = {"2016-12-11","2016-01-02","2016-05-03","2016-08-04","2016-03-05","2016-11-06","2016-04-07","2016-02-08","2016-06-09"};
	private String DBname = "Financemanage_DB";
	public static void main(String[] args) {
		initializeDB initialer = new initializeDB();
		initialer.insertToFinaceDB() ;
	}
	
	public void insertToFinaceDB() {
	//	for(int i = 0;i<10;i++){
			//double money = 120.0 + i;
//		  insertToDepartment_bill("Business01",404002.85);
//		  insertToInvesttion_bill();
		//}
		for(int i = 0;i<25;i++){
			String id = "proprety"+i;
			String department = "service"+i;
			String project_id = "Servicer"+i;
			String day = date[i%9];
			String sql = "INSERT INTO proprety VALUE ('"+id+"','"+day+"','100','"+"客服电话维修','"+project_id+"','0','"+department+"');";
			insert(sql);
		}
		
		for(int i = 25;i<50;i++){
			String id = "proprety"+i;
			String department = "research"+(i-25);
			String project_id = "res"+(i-25);
			String day = date[i%9];
			String sql = "INSERT INTO proprety VALUE ('"+id+"','"+day+"','10000','"+"研究项目通信','"+project_id+"','0','"+department+"');";
			insert(sql);
		}
		
		for(int i = 50;i<75;i++){
			String id = "proprety"+i;
			String department = "business"+(i-50);
			String project_id = "Businesser"+i;
			String day = date[i%9];
			String sql = "INSERT INTO proprety VALUE ('"+id+"','"+day+"','100','"+"业务办理','"+project_id+"','1','"+department+"');";
			insert(sql);
		}
		
		for(int i = 75;i<100;i++){
			String id = "proprety"+i;
			String department = "finance"+(i-75);
			String project_id = "Financer"+i;
			String day = date[i%9];
			String sql = "INSERT INTO proprety VALUE ('"+id+"','"+day+"','100','"+"桌椅更新','"+project_id+"','0','"+department+"');";
			insert(sql);
		}
		
	}
	
	public void insertToDepartment_bill(String department,double quantity){
		Date date = Date.valueOf("2016-03-10");
		int id = 700;
		sqlconnect connect = new sqlconnect();
		conn = connect.getConn();
//		String sql = "INSERT INTO Department_bill"+" VALIES ("+"'"+ id + "'"+","+"'"+department+"'"+","+
//		"'"+date+"'"+","+"'"+"0"+"'"+","+"'"+quantity+"'"+","+"'"+"电脑出现故障需要维修"+"');";
		for(int i = 0;i<100;i++){
			id+=1;
			quantity = quantity+i*100;
			String sql = "INSERT INTO Department_bill"+" VALUES ("+"'"+ id + "'"+","+"'"+department+"'"+","+
					"'"+date+"'"+","+"'"+"1"+"'"+","+"'"+quantity+"'"+","+"'"+"业务办理收入"+"');";
			System.out.println(sql);
			try {
				//conn = sqlconnection.getConn();
				
				Statement statement = conn.createStatement() ;
				PreparedStatement ps = conn.prepareStatement(sql);
				//ResultSet rs = ps.executeQuery();
				ps.execute();
				//statement.execute(sql);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}		
	}
	
public void insertToInvesttion_bill() {
		Date date = Date.valueOf("2016-10-12");
		sqlconnect connect = new sqlconnect();
	String sql = "INSERT INTO Investion_bill"+" VALUES ("+"'"+"pr00009"+"'"+","+"'"+"ad00001"+"'"+","+"'"+"m001"+"'"+","+"'"+date+"'"+","+
		"'"+0+"'"+","+"'22000');";
	String sql1 = "INSERT INTO Investion_bill"+" VALUES ("+"'"+"pr00010"+"'"+","+"'"+"pd00001"+"'"+","+"'"+"m002"+"'"+","+"'"+date+"'"+","+
			"'"+0+"'"+","+"'12000');";
	String sql2 = "INSERT INTO Investion_bill"+" VALUES ("+"'"+"pr00011"+"'"+","+"'"+"pd00001"+"'"+","+"'"+"m020"+"'"+","+"'"+date+"'"+","+
			"'"+0+"'"+","+"'30000');";
	String sql3 = "INSERT INTO Investion_bill"+" VALUES ("+"'"+"pr00012"+"'"+","+"'"+"ad00002"+"'"+","+"'"+"m004"+"'"+","+"'"+date+"'"+","+
			"'"+0+"'"+","+"'20000');";
	insert(sql);
	insert(sql1);
	insert(sql2);
	insert(sql3);
	}
private void insert(String sql){
	SQLCon connect = new SQLCon(DBname);
	conn = connect.getConn();
	try {
		//conn = sqlconnection.getConn();
		
		Statement statement = conn.createStatement() ;
		PreparedStatement ps = conn.prepareStatement(sql);
		//ResultSet rs = ps.executeQuery();
		ps.execute();
		//statement.execute(sql);
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
}

public void insertToService_bill(int id,String staffID, Date date, int type,double quantity) {
	String sql = "INSERT INTO Service_bill"+" VALUE ("+"'"+id+"'"+","+"'"+staffID+"'"+","+"'"+date+"'"+","+"'"+type+"'"+",'"+quantity+"');";
	System.out.println(sql);
	insert(sql);	
}
public void insertToSalary_bill(int id, String staffId, int month, double salary){
	String sql = "INSERT INTO Salary_bill"+" VALUE ("+"'"+id+"',"+"'"+staffId+"',"+"'"+month+"',"+"'"+salary+"');";
	System.out.println(sql);
	insert(sql);	
}
public void insertToProprety(int id, Date date,double quantity, String description){
	String sql = "INSERT INTO proprety"+" VALUE ("+"'"+id+"',"+"'"+date+"',"+"'"+quantity+"',"+"'"+description+"');";
	System.out.println(sql);
	insert(sql);
}
}

