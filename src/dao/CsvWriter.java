package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cons.Constant;

// CSV書き込みdao
public class CsvWriter {
	public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rset = null;

	public CsvWriter() {}

	public void connectDB(){
		// JDBCドライバの読み込み
	    try {
	    	// postgresSQLのJDBCドライバを読みこみ
	    	Class.forName("org.postgresql.Driver");
	    } catch(ClassNotFoundException e) {
	    	// JDBCドライバが見つからない場合
	    	e.printStackTrace();
	    }
	    try {
    	// postgre接続
    	conn = DriverManager.getConnection(Constant.JDBC_CONNECTION, Constant.JDBC_USER, Constant.JDBC_PASS);
    	conn.setAutoCommit(false);
    	stmt = conn.createStatement();
    	}catch(Exception e) {
			e.printStackTrace();
    	}
	}

	// CSV書き込み
	public String csvWrite(String sql) {
		connectDB();

	    try {

	    	stmt.executeUpdate(sql);
	    	conn.commit();

	    }catch(Exception e) {
			e.printStackTrace();
			return "error";
	    }finally {

	    		try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	    return "OK";
	}
}
