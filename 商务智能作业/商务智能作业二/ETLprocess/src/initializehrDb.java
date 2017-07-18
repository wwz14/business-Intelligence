import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class initializehrDb {
	private Connection conn ;
	private String[] namelist = {"王丽莉","白浅","白凤九","夜华","东华","莫渊","玄女","叠风","令羽","白真","折颜","阿离","奈奈","司命","连宋","成玉",
			"迷谷","子澜","少辛","桑籍"};
	public static void main(String[] args){
		initializehrDb initialer = new initializehrDb();
//		initialer.insertTodepartment( "service01","客服一部" ,"sm001","上海静安区客服");
//		initialer.insertTodepartment( "research01","研究一部" ,"rm001","上海静安区研究部");
//		initialer.insertTodepartment( "business01","营业厅" ,"bm001","上海静安区营业部");
//		initialer.insertTodepartment( "finance01","财务处" ,"fm001","上海静安区财务部");
//		initialer.insertTodepartment( "humanre01","人力资源管理处" ,"hm001","上海静安区人力资源部");
//		initialer.insertTodepartment(1, "service02", "sm002");
//		initialer.insertTodepartment(2, "service03", "sm003");
//		initialer.insertTodepartment(3, "finance01", "fm001");
//		initialer.insertTodepartment(4, "finance02", "fm002");
//		initialer.insertTodepartment(5, "investment01", "im001");
//		initialer.insertTodepartment(6, "investment02", "im002");
//		initialer.insertTodepartment(7, "business01", "bm001");
//		initialer.insertTodepartment(8, "business02", "bm002");
//		initialer.insertTodepartment(9, "business03", "bm003");
//		initialer.insertTodepartment(10, "research01", "rm001");
//		initialer.insertTodepartment(11, "research02", "rm002");
//		initialer.insertTodepartment(12, "humanresouce", "hm001");	
		initialer.insertstaff();
	}
public void insertTodepartment(String id, String name, String Pmid,String des ){
	String sql = "INSERT INTO department VALUE ("+"'"+id+"',"+"'"+name+"',"+"'"+Pmid+"','"+des+"');";
	System.out.println(sql);
	insert(sql);
}
public void insertTostaff(String id,String name,String department_id,String position,String phone_number,double salary, int workingyear) {
	String sql = "INSERT INTO staff VALUE ("+"'"+id+"',"+"'"+name+"',"+"'"+department_id+"',"+"'"+position+"',"+
"'"+phone_number+"',"+"'"+salary+"',"+"'"+workingyear+"');";
    System.out.println(sql);
    insert(sql);	
}

private void insert(String sql){
	sqlconnect connect = new sqlconnect();
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
public void insertstaff(){
	int sid = 0;
	for(int i = 0;i<10;i++){
		String  id = "Researcher"+i;
		insertTostaff(id,namelist[i],"research01","研究员","18362921234",2000.0,2);	
	}
	
	for(int i = 0;i<70;i++){
		String  id = "Servicer"+i;
		insertTostaff(id,namelist[i%11],"service01","客服","18362921235",2000.0,2);
	
	}
	
	for(int i = 0;i<70;i++){
		String  id = "Businesser"+i;
		insertTostaff(id,namelist[i%11],"business03","营业员","18362921236",2000.0,2);
	}
	
	for(int i = 0;i<20;i++){
		String  id = "Financer"+i;
		insertTostaff(id,namelist[i%11],"finance01","理财师","18362921237",4000.0,3);
	}
	

	for(int i = 0;i<20;i++){
		String  id = "hrer"+i;
		insertTostaff(id,namelist[i%11],"hr01","人力资源管理师","18362921238",4000.0,3);
		
	}
	
	for(int i = 0;i<25;i++){
		String sdid = "service"+i;
		String spm = "sm"+i;
		insertTodepartment( sdid,"客服" ,spm,"上海静安区客服");
		String fdid = "finance"+i;
		String fpm = "fm"+i;
		insertTodepartment( fdid,"客服" ,fpm,"上海静安区财务");
		String rdid = "research"+i;
		String rpm = "rm"+i;
		insertTodepartment( rdid,"客服" ,rpm,"上海静安区研究部");
		String idid = "investion"+i;
		String ipm = "im"+i;
		insertTodepartment( idid,"客服" ,ipm,"上海静安区投资部");
		String hdid = "humanresource"+i;
		String hpm = "hm"+i;
		insertTodepartment( hdid,"客服" ,hpm,"上海静安区人资部");
		String bdid = "business"+i;
		String bpm = "bm"+i;
		insertTodepartment( bdid,"客服" ,bpm,"上海静安区营业部");
		insertTostaff(spm,namelist[i%11],sdid,"部长","18362921234",8000.0,5);	
		insertTostaff(fpm,namelist[i%11],fdid,"部长","18362921234",8000.0,5);	
		insertTostaff(rpm,namelist[i%11],rdid,"部长","18362921234",10000.0,6);	
		insertTostaff(ipm,namelist[i%11],idid,"部长","18362921234",9000.0,8);	
		insertTostaff(hpm,namelist[i%11],hdid,"部长","18362921234",8000.0,7);	
		insertTostaff(bpm,namelist[i%11],bdid,"部长","18362921234",8000.0,6);	
	}
	
//	int sid6 = 100;
//	for(int i = 0;i<20;i++){
//		insertTostaff(sid3,namelist[i],"business01","柜员","18362921239",4000.0,3);
//		sid6++;
//	}
}
}
