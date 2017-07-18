package ETL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class etlmain {
	private static String starmodel = "business_DW";
	private static String snowmodel = "Financemanage_DW";
	private static Connection connDW1;
	private static Connection connDW2;
	
	private static void clear(String sql, Connection connDW) {
		try {
			Statement statement = connDW.createStatement() ;
			PreparedStatement ps = connDW.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			
			//e.printStackTrace();
		}
	}
	
	public static void main(String[] agrs) throws SQLException{
		SQLCon connect = new SQLCon(starmodel);
		connDW1 = connect.getConn();
		SQLCon connect2 = new SQLCon(snowmodel);
		connDW2 = connect.getConn();
		String sql1 = "TRUNCATE business_fact";
		clear(sql1,connDW1);
		String sql2 = "TRUNCATE customer";
		clear(sql2,connDW1);
		String sql3 = "TRUNCATE servietype";
		clear(sql3,connDW1);
		String sql4 = "TRUNCATE staffinfo";
		clear(sql4,connDW1);
		String sql5 = "TRUNCATE time";
		clear(sql5,connDW1);
		
		String sql6 = "TRUNCATE time_dimension";
		clear(sql6,connDW2);
		String sql7 = "TRUNCATE department_dimension";
		clear(sql7,connDW2);
		String sql8 = "TRUNCATE fm_fact";
		clear(sql8,connDW2);
		String sql9 = "TRUNCATE fund_dimension";
		clear(sql9,connDW2);
		String sql10 = "TRUNCATE pm";
		clear(sql10,connDW2);
		String sql11 = "TRUNCATE season";
		clear(sql11,connDW2);
		String sql12 = "TRUNCATE user";
		clear(sql12,connDW2);
		
		snowmodel snow = new snowmodel();
		snow.etlProcess();
		starmodel star = new starmodel();
		star.etlProcess();		
	}
}
