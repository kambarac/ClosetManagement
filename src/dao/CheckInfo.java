package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cons.Constant;
import dto.UserData;

// ユーザー情報dao
public class CheckInfo {
	public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rset = null;

    public CheckInfo() {}

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

	public UserData getData(String sql){
		connectDB();

		UserData data = null;

	    try {
	    	rset = stmt.executeQuery(sql);
	    	conn.commit();

            while(rset.next()){
                data = new UserData(rset.getString("id"), rset.getString("pass"), rset.getString("name"));
            }

			rset.close();

	    }catch(Exception e) {
			e.printStackTrace();
	    	return null;

	    }finally {

    		try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	public UserData duplicateCheck(String sql) {
		connectDB();
		UserData data = null;

		 try {
		    	rset = stmt.executeQuery(sql);
		    	conn.commit();

	            while(rset.next()){
	                data = new UserData(rset.getString("id"), rset.getString("pass"), rset.getString("name"));
	            }

				rset.close();

		    }catch(Exception e) {
				e.printStackTrace();
		    	return null;

		    }finally {

	    		try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		return data;
	}
}