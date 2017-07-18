import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import ETL.SQLCon;

public class initializeBusinessDB {
	private Connection conn ;
	private String DBname = "Business_DB";
	private String[] namelist = {"白浅","白凤九","夜华","东华","莫渊","玄女","叠风","令羽","白真","折颜","阿离","奈奈","司命","连宋","成玉",
			"迷谷","子澜","少辛","桑籍"};
	private String[] description = {"购买手机卡","充值套餐1","充值套餐2","充值套餐3","月包流量3G","开通家庭网络","更换手机卡","开通和家庭服务","开通企业网络"};
	private String[] price = {"60","100","70","60","150","1000","70","20","5000"};
	private String[] date = {"2016-12-01","2016-12-02","2016-12-03","2016-12-04","2016-12-05","2016-12-06","2016-12-07","2016-12-08","2016-12-09"};
	private String[] grade = {"1","2","3","4","5"};
	public static void main(String[] args){
		initializeBusinessDB i = new initializeBusinessDB();
		//i.insertTostaff();
	//	i.insertTocustome();
		//i.insertToservieType();
		i.insertTobusiness_recode();
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
	
	public void insertTostaff() {
		for(int i = 0;i<100;i++){
			String staffid = "business"+i;
			String name = namelist[i%19];
			String position = "营业厅业务员";
			String phone_number = "18362926973";
			String sql = "INSERT INTO staff VALUE ("+"'"+staffid+"','"+name+"','"+position+"','"+phone_number+"');";
			insert(sql);
		}
	}
	
	public void insertTocustome() {
		for(int i = 0;i<100;i++){
			String id = "custome"+i;
			String name = namelist[i%19];
			String idnumber = "320029199701018890";
			String phone_number = "18362920989";
			String sql = "INSERT INTO custome VALUE ("+"'"+id+"','"+name+"','"+idnumber+"','"+phone_number+"');";
			insert(sql);
		}
	}
	
	public void insertToservieType() {
		for(int i = 0;i<9;i++){
			String descrip = description[i];
			String money = price[i];
			String sql = "INSERT INTO servieType VALUE ("+"'"+i+"','"+descrip+"','"+money+"');";
			insert(sql);
		}
	}
	
	public void insertTobusiness_recode() {
		for(int i = 0;i<100;i++) {
			Date day = Date.valueOf(date[i%9]);
			String staff = "business"+i;
			String custome = "custome"+i;
			int type = i%9; 
		    String money = price[i%9];
		    String point = grade[i%4];
			String sql = "INSERT INTO business_recode VALUE ("+"'"+i+"','"+day+"','"+staff+"','"+custome+"','"+type+"','"+money+"','"+point+"');";
			insert(sql);
		}
		
	}
	

}
