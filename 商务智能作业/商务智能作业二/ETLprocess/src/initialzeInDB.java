import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import ETL.SQLCon;

public class initialzeInDB {
	private String[] namelist = {"王丽莉","白浅","白凤九","夜华","东华","莫渊","玄女","叠风","令羽","白真","折颜","阿离","奈奈","司命","连宋","成玉",
			"迷谷","子澜","少辛"};
	private String[] phone_number = {"18261911234","13773568900","13951295690"};
	private String[] date = {"2016-12-11","2016-01-02","2016-05-03","2016-08-04","2016-03-05","2016-11-06","2016-04-07","2016-02-08","2016-06-09"};
	private Connection conn ;
	private String DBname = "Investment_DB";
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
	
	public static void main(String[] args){
		initialzeInDB i = new initialzeInDB();
		i.insertToinvest_plan();
		//i.insertToInvester();
	}
	
	public void insertToInvester(){
		for(int i = 0;i<70;i++){
			String invest = "Invester"+i;
			String name = namelist[i%19];
			String phonenumber = phone_number[i%3];
			String position = "研究员";
			String sqlCus = "INSERT INTO invester VALUE ('"+invest+"','"+name+"','"+position+"','"+phonenumber+"');";
			System.out.println(sqlCus);
			insert(sqlCus);
		}
	}
	
	public void insertToinvest_plan(){
		for(int i = 0;i<70;i++){
			String projectid = "inp"+i;
			String name = "赞助三生三世十里桃花";
			String pmid = "im20";
			String object = "广告";
			String quantity = "12345678";
			Date day = Date.valueOf("2016-09-09");
			String des = "广告投资";
			String analyer = "Invester"+i;
			int result = 1;
			String sql = "INSERT INTO invest_plan VALUE ('"+projectid+"','"+name+"','"+day+"','"+analyer+"','"+object+"','"+quantity+"','"+result+"','"+
			des+"');";
			System.out.println(sql);
			String sql2 = "INSERT INTO invest_project VALUE ('"+i+"','"+projectid+"','"+pmid+"','"+object+"','"+
			quantity+"','"+day+"','"+des+"','"+name+"');";
			System.out.println(sql2);
			insert(sql);
			insert(sql2);
		}
	}
}
