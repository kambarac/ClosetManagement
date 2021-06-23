package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cons.Constant;

// ユーザー情報dao
public class UserInfo {
	public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rset = null;


	public UserInfo() {}

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

    // ユーザー情報取得
    public void setData(String sql, String userid) {
    	connectDB();

		try {
	    	stmt.executeUpdate(sql);
	    	String createTable = "CREATE TABLE closet" + userid + " (id SERIAL primary key, type varchar(20) not null,"
	    			+ "color varchar(10) not null, makers varchar(30) not null, image varchar(50),"
	    			+ "date_create timestamp, date_update timestamp)";
	    	stmt.executeUpdate(createTable);
	    	conn.commit();
	    }catch(Exception e) {
			e.printStackTrace();
	    }finally {
	    		try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}
	}

    // ユーザー情報変更
    public boolean changeData(String sql) {
    	connectDB();
    	try {
	    	stmt.executeUpdate(sql);
	    	conn.commit();
	    }catch(Exception e) {
			e.printStackTrace();
			return true;
	    }finally {
	    		try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
    	return false;
    }
}
