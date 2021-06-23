package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cons.Constant;
import dto.GearTable;

// 服飾データdao
public class GearData {
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rset = null;


	public GearData() {
	}

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

	// 服飾データ取得
	public List<GearTable> getTable(String sql) {
		connectDB();

		List<GearTable> closet = new ArrayList<GearTable>();
		int empty = -1;

	    try {

	    	rset = stmt.executeQuery(sql);
	    	conn.commit();


            while(rset.next()){
                closet.add(new GearTable(rset.getInt("id"), rset.getString("type"), rset.getString("color"),rset.getString("makers"),rset.getString("image"),rset.getTimestamp("date_create"),rset.getTimestamp("date_update")));
                empty = rset.getInt("id");
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
	    if(empty > 0) {
	    	return closet;
	    }else {
	    	return null;
	    }

	}

	// 服飾データ変更
	public void setData(String sql) {
		connectDB();

		try {
	    	stmt.executeUpdate(sql);
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
}