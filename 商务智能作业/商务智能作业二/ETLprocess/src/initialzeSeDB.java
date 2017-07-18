import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import ETL.SQLCon;

/**
 * 初始化客服系统数据
 * @author alice
 *
 */
public class initialzeSeDB {
	private String[] namelist = {"王丽莉","白浅","白凤九","夜华","东华","莫渊","玄女","叠风","令羽","白真","折颜","阿离","奈奈","司命","连宋","成玉",
			"迷谷","子澜","少辛"};
	private String[] phone_number = {"18261911234","13773568900","13951295690"};
	private String[] date = {"2016-12-11","2016-01-02","2016-05-03","2016-08-04","2016-03-05","2016-11-06","2016-04-07","2016-02-08","2016-06-09"};
	private Connection conn ;
	private String DBname = "Service_DB";
	
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
	
	public  static void main(String[] args){
		initialzeSeDB i = new initialzeSeDB();
		//i.insertdata();
		//i.insertToconsult();
		//i. insertToservicer();
		i.insertToComplain();
	}
	
	public void insertdata() {
		for(int i = 0;i<70;i++){
			String custome = "customer"+i;
			String name = namelist[i%19];
			String phonenumber = phone_number[i%3];
			String sqlCus = "INSERT INTO custome VALUE ('"+custome+"','"+name+"','"+phonenumber+"');";
			System.out.println(sqlCus);
			insert(sqlCus);
			
			
			
		}
	}
	
	public void insertToconsult(){
		for(int i = 0;i<70;i++){
			String consult = "consult"+i;
			String custome = "customer"+i;
			String service = "Servicer"+i;
			Date day = Date.valueOf(date[i%9]);
			String type = "consult network service";
			String detail = "咨询网络扣费";
			String sqlcon = "INSERT INTO consult VALUE ('"+consult+"','"+service+"','"+custome+"','"+day+"','"+type+"','"+detail+"');";
			insert(sqlcon);
		}
	}
	
  public void insertToservicer() {
	  for(int i = 0;i<70;i++){
	  String position = "客服";
	  String phonenumber = phone_number[i%3];
	  String service = "Servicer"+i;
		String sname = namelist[i%19];
		String servicer =  "INSERT INTO servicer VALUE ('"+service+"','"+sname+"','"+position+"','"+phonenumber+"');";
	    insert(servicer);
	  }
  }
  
  public void insertToComplain(){
	  for(int i = 0;i<70;i++){
	  String complain = "complain"+i;
	    String ctype = "complain the servcie";
	    String custome = "customer"+i;
	    Date day = Date.valueOf(date[i%9]);
		String service = "Servicer"+i;
		String cdetail = "服务态度不好";
		String sqlcom = "INSERT INTO complain VALUE ('"+complain+"','"+service+"','"+custome+"','"+day+"','"+ctype+"','"+cdetail+"');";
		insert(sqlcom);
	  }
  }
  

}
