package ETL;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author 汪文藻
 * 选取柜台营业为主题建立星型模型
 * 数据仓库business_DW
 */
public class starmodel {
private String DWname = "business_DW";
private String DBname = "Business_DB";
private Connection connDB;
private Connection connDW;

public static void main(String[] agrs){
	starmodel model = new starmodel();
	try {
		model.etlProcess();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//	model.time("2016-12-12 00:00:00.0");
}

public starmodel() {
	SQLCon connect = new SQLCon(DWname);
	connDW = connect.getConn();
	SQLCon connect2 = new SQLCon(DBname);
	connDB = connect.getConn();
	
}

private void insert(String sql) {
	try {
		Statement statement = connDW.createStatement() ;
		PreparedStatement ps = connDW.prepareStatement(sql);
		ps.execute();
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
}

public void etlProcess() throws SQLException {
//	PreparedStatement ps = conn.prepareStatement(newSql);
//	ResultSet rs = ps.executeQuery();
	String select = "select * from business_recode;";
	PreparedStatement ps = connDB.prepareStatement(select);
	ResultSet rs = ps.executeQuery();
	while(rs.next()){
		String date = rs.getString(2);//时间维度关键字
		time(date);
		System.out.println("date"+date);
		String staffid = rs.getString(3);//员工关键字
		staff(staffid);
		String customeid = rs.getString(4);//客户关键字
		custome(customeid);
		String type = rs.getString(5);//服务关键字
		type(type);
		String revenue = rs.getString(6);//收益值
		String grade = rs.getString(7);//顾客反馈
		String insertfact = "INSERT INTO business_fact VALUE ('"+date+"','"+staffid+"','"+customeid+"','"+type+"','"+grade+"','"+
	revenue+"');";
	insert(insertfact);
	}
	
}

private void time(String date) {
	//完成时间维度表
	String[] dateStr = date.split(" ");
	String timekey = dateStr[0];
	String[] timestr = timekey.split("-");
	String year = timestr[0];// year
	String month = timestr[1];//month
	String day = timestr[2];//day
	int season = 1;// compute season
	int monthnumber = Integer.parseInt(month);
	if(monthnumber < 4)
		season = 1;
	else if(monthnumber >=4 && monthnumber<7)
		season = 2;
	else if( monthnumber>=7&&  monthnumber<10)
		season = 3;
	else 
		season =4;
	String sql = "INSERT INTO time VALUE ('"+date+"','"+year+"','"+month+"','"+day+"','"+season+"');";
	System.out.println(sql);
	insert(sql);
}

private void staff(String staffid) throws SQLException{
	//完成员工维度表，根据员工id在Business_DB中查找员工信息，然后解析信息插入
	String select = "select * from staff where ID = ?;";
	PreparedStatement ps = connDB.prepareStatement(select);
	ps.setString(1, staffid);
	ResultSet rs = ps.executeQuery();
	while(rs.next()){
		String name = rs.getString(2);
		String position = rs.getString(3);
		String phone = rs.getString(4);
		String sql = "INSERT INTO staffinfo VALUE ('"+staffid+"','"+name+"','"+position+"','"+phone+"');";
		System.out.println(sql);
		insert(sql);
	}
			
}
private void custome(String customeid) throws SQLException{
	//完成顾客维度表，根据顾客id在Business_DB中查找员工信息，然后解析信息存入数据仓库
	String select = "select * from custome where ID = ?;";
	PreparedStatement ps = connDB.prepareStatement(select);
	ps.setString(1, customeid);
	ResultSet rs = ps.executeQuery();
	while(rs.next()){
		String name = rs.getString(2);
		String Id_number = rs.getString(3);
		String phone = rs.getString(4);
		String sql = "INSERT INTO customer VALUE ('"+customeid+"','"+name+"','"+Id_number+"','"+phone+"');";
		System.out.println(sql);
		insert(sql);
	}
}
private void type(String type) throws SQLException{
	//完成服务信息表，先从Business_DB中查找信息，然后插入数据仓库
	String select = "select * from servieType where id = ?;";
	PreparedStatement ps = connDB.prepareStatement(select);
	ps.setString(1, type);
	ResultSet rs = ps.executeQuery();
	while(rs.next()){
		String des = rs.getString(2);
		String price = rs.getString(3);
		String sql = "INSERT INTO servietype VALUE ('"+type+"','"+des+"','"+price+"');";
		System.out.println(sql);
		insert(sql);
	}
}
}
