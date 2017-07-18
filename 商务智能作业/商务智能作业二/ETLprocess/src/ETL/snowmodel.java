package ETL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 选取财务资金流动为主题，建立雪花模型，使用到财务管理数据库和人力资源数据库
 * @author 汪文藻
 *
 */
public class snowmodel {
	
	private String DWname = "Financemanage_DW";
	private String DBname = "Financemanage_DB";
	private String HrDBname = "Humanresource_DB";
	private String ReDBname = "Research_DB";
	private String InDBname = "Investment_DB";
	private Connection connDB;
	private Connection connDW;
	private Connection connHr;
	private Connection connRe;
	private Connection connIn;
	
	public static void main(String[] agrs) {
		snowmodel model = new snowmodel();
		try {
			model.etlProcess();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public snowmodel() {
		SQLCon connect = new SQLCon(DWname);
		connDW = connect.getConn();
		SQLCon connect2 = new SQLCon(DBname);
		connDB = connect.getConn();
		SQLCon connect3 = new SQLCon(HrDBname);
		connHr = connect.getConn();
		SQLCon connect4 = new SQLCon(ReDBname);
		connRe = connect.getConn();
		SQLCon connect5 = new SQLCon(InDBname);
		connIn = connect.getConn();
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
		String select = "select * from proprety;";
		PreparedStatement ps = connDB.prepareStatement(select);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			String fund_id = rs.getString(1);
			String date = rs.getString(2);
			time(date);//时间维度表
			String quantity = rs.getString(3);
			String description = rs.getString(4);
			String object_id = rs.getString(5); 
			user(object_id);//用途表
			String inorout = rs.getString(6);
			String department_id = rs.getString(7);
			department(department_id);//部门表
			fund(fund_id, description,object_id);//资金表
			String sql = "INSERT INTO fm_fact VALUE ('"+date+"','"+department_id+"','"+fund_id+"','"+quantity+"','"+inorout+"');";
			System.out.println(sql);//事实表
			insert(sql);
		}
		
	}
	/**
	 * 完成时间维表——>季度表
	 * @param date
	 */
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
//		System.out.println("year:"+year);
//		System.out.println("month:"+month);
//		System.out.println("day:"+day);
//		System.out.println("season:"+season);
		//完成时间维表
		String sql = "INSERT INTO time_dimension VALUE ('"+date+"','"+year+"','"+month+"','"+day+"');";
		System.out.println(sql);
		insert(sql);
		season(month,season);//完成子维度季度维度		
	}
/**
 * 完成部门维表及其子维表负责任表
 * @param departmentid
 * @throws SQLException
 */
	private void department(String departmentid) throws SQLException {
		//
		String select = "select * from department where ID =?;";
		PreparedStatement ps = connHr.prepareStatement(select);
		ps.setString(1, departmentid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			String name = rs.getString(2);
			String pmid= rs.getString(3);
			String description = rs.getString(4);
			String sql = "INSERT INTO department_dimension VALUE ('"+departmentid+"','"+description+"','"+name+"','"+pmid+"');";
			insert(sql);
			//完成部门的pm表的插入
			pm(pmid);
		}
	}
    private void fund(String fund_id, String des,String user_id){
    	String sql = "INSERT INTO fund_dimension VALUE ('"+fund_id+"','"+user_id+"','"+des+"');";
    	insert(sql);
    	
    }
    private void season(String month,int season){
    	String sql = "INSERT INTO season VALUE ('"+month+"','"+season+"');";
    	System.out.println(sql);
    	insert(sql);
    }
    private void pm(String pmid) throws SQLException {
    	String select = "select * from staff where ID =?;";
    	PreparedStatement ps = connHr.prepareStatement(select);
		ps.setString(1, pmid);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			String name = rs.getString(2);
			String position = rs.getString(4);
			String phonenumber = rs.getString(5);
			String sql = "INSERT INTO pm VALUE ('"+pmid+"','"+name+"','"+phonenumber+"','"+position+"');";
			insert(sql);
		}
    }
	
    private void user(String id) throws SQLException {
    	//对这项id信息的查询，分为三种，第一种，如果是rep系列id，即研究项目，则到研究系统查询数据
    	//如果是inp系列项目，则为投资系列项目，需要到投资管理系统查询数据
    	//如果是员工工资，对台收入，则到人力资源管理系统查询
    	
    	String keyword = id.substring(0, 3);
    	if(keyword.equals("rep")){
    		String select = "select * from project where id = ?;";
    		PreparedStatement ps = connRe.prepareStatement(select);
    		ps.setString(1, id);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()){
    			String pmid = rs.getString(2);
    			String name = rs.getString(5);
    			String des = rs.getString(6);
    			String sql = "INSERT INTO user VALUE ('"+id+"','"+pmid+"','"+name+"','"+des+"');";
    			insert(sql);
    			pm(pmid);
    		}
    		
    	}else if(keyword.equals("inp")){
    		String select = "select * from invest_project where projectID = ?;";
    		PreparedStatement ps = connIn.prepareStatement(select);
    		ps.setString(1, id);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()){
    			String pmid = rs.getString(3);
    			String name = rs.getString(8);
    			String des  = rs.getString(7);
    			String sql = "INSERT INTO user VALUE ('"+id+"','"+pmid+"','"+name+"','"+des+"');";
    			insert(sql);
    			pm(pmid);
    		}
    		
    	}else {
    		String select = "select * from staff where ID =?;";
        	PreparedStatement ps = connHr.prepareStatement(select);
    		ps.setString(1, id);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()){
    			String name = rs.getString(2);
    			String position = rs.getString(4);
    			String phonenumber = rs.getString(5);
    			String sql = "INSERT INTO user VALUE ('"+id+"','"+"null"+"','"+name+"','"+position+"');";
    			insert(sql);
    		}
    	}
    	
    }
	
	
}
