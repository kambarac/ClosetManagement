package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cons.Constant;
import dto.ClosetData;

// クロゼット情報dao
public class ClosetInfo {
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rset = null;


	public ClosetInfo() {}

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

	// レコード数取得
	public int getRecordCount(String sql) {
		connectDB();
		int count = -1;
		 try {
		    	rset = stmt.executeQuery(sql);
		    	conn.commit();

		    while(rset.next()) {
		    	count = rset.getInt("count");
		    }

		 }catch(Exception e) {
				e.printStackTrace();
		    	return -1;

		    }finally {

		    		try {
						stmt.close();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		return count;
	}

	// 項目別レコード数取得
	public List<ClosetData> getData(String sql, String columnName){
		connectDB();
		List<ClosetData> data = new ArrayList<ClosetData>();
	    try {

	    	rset = stmt.executeQuery(sql);
	    	conn.commit();

            while(rset.next()){
                data.add(new ClosetData(rset.getString(columnName), rset.getInt("count")));
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
