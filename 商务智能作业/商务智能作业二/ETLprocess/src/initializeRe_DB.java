import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import ETL.SQLCon;

public class initializeRe_DB {
	private Connection conn ;
	private String DBname = "Research_DB";
	private String[] pmlist = {"rem001","rem002","rem003","rem004","rem005"};
	private String[] project = {"rep0001","rep0002","rep0003","rep0004","rep0005","rep0006","rep0007","rep0008","rep0009","rep0010","rep0011","rep0012"};
	private String[] start = {"2016-01-20","2016-03-12","2016-05-23","2016-09-20","2016-12-12"};
	private String[] descriptions = {"电信系统V1.0","蜂窝移动网络V2.0"};
	private String[] namelist = {"王丽莉","白浅","白凤九","夜华","东华","莫渊","玄女","叠风","令羽","白真","折颜"};
	
	public static void main(String[] args) {
		//insertTOproject(String id, String pmid, Date StartDate,Date EndDate,String name, String description) 
		initializeRe_DB initialer = new initializeRe_DB();
		initialer.insertTOproject();
		initialer.insertToresearcher();
		initialer.insertTotask();
	}
	
	private void insert(String sql){
		SQLCon connect = new SQLCon(DBname);
		conn = connect.getConn();
		try {
			Statement statement = conn.createStatement() ;
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void insertTOproject() {
		for(int i = 0;i<70;i++){
			String projectid = "rep"+i;
			String pm = pmlist[i%5];
			Date startdate =Date.valueOf(start[i%5]);
			Date enddate = Date.valueOf("2018-12-10");
			String name = "通信让世界更美好";
			String description = descriptions[i%2];
			String sql = "INSERT INTO project VALUE ("+"'"+projectid+"',"+"'"+pm+"',"+"'"+startdate+"',"+"'"+enddate+"',"+"'"+name+"',"+"'"+description+"');";
			insert(sql);
		}
	}
	
	public void insertToresearcher(){
		String phone_number = "18362927727";
		for(int i = 0;i<70;i++){
			String staffID = "Research"+i;
			String name = namelist[i%11];
			String position = "通信工程师";
			String sql = "INSERT INTO researcher VALUE ("+"'"+staffID+"',"+"'"+name+"',"+
			"'"+phone_number+"',"+"'"+position+"');";
			System.out.println(sql);
			insert(sql);
		}
		
		
	}
	
	public void insertTotask(){
		for(int i = 0;i<70;i++){
			String staffID = "Research"+i;
			String projectid = "rep"+i;
			String sql = "INSERT INTO task VALUE ("+"'"+projectid+"',"+"'"+staffID+"');";
			System.out.println(sql);
			insert(sql);
		}
		
	}

}
