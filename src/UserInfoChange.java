

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GearData;
import dao.UserInfo;
import dto.GearTable;

/**
 * ユーザー情報変更サーブレット
 */
@WebServlet("/UserInfoChange")
public class UserInfoChange extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoChange() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// セッションから既存のユーザー情報取得
		String before_id = (String)session.getAttribute("id");
		String before_pass = (String)session.getAttribute("pass");
		String before_name = (String)session.getAttribute("name");

		// フォーム入力データ取得
		String id = checkEmpty(request.getParameter("user_id").toLowerCase());
		String pass = checkEmpty(request.getParameter("user_pass"));
		String name = checkEmpty(request.getParameter("user_name"));


		// sql文作成
		String userSql = "UPDATE user_info SET ";
		if(id != null) {
			userSql += "id='" + id + "'";
			session.setAttribute("id", id);
			if(pass != null || name != null) {
				userSql += ",";
			}
		}
		if(pass != null) {
			userSql += "pass='" + pass + "'";
			session.setAttribute("pass", pass);
			if(name != null) {
				userSql += ",";
			}
		}
		if(name != null) {
			userSql += "name='" + name + "'";
			session.setAttribute("name", name);
		}
		userSql += "WHERE id='" + before_id + "'";

		// SQL文実行
		UserInfo user = new UserInfo();
		boolean error = user.changeData(userSql);

		if(error == false) { // エラーじゃなかったら
			// 服飾データ取得用SQL文作成
			String selectSql = "SELECT id, type, color, makers, image, date_create, date_update FROM closet" + before_id + " ORDER BY id desc offset 0 limit 12";

			// ユーザーID入力時テーブルの名前を変更する
			if(id != null) {
				String closetSql = "ALTER TABLE closet" + before_id + " RENAME TO closet" + id;
				user.changeData(closetSql);
				selectSql = "SELECT id, type, color, makers, image, date_create, date_update FROM closet" + id + " ORDER BY id desc offset 0 limit 12";
			}

			// 服飾データ取得用SQL文実行
			GearData gearData = new GearData();
			List<GearTable> closet = new ArrayList<GearTable>();
			closet = gearData.getTable(selectSql);

			 // DB内の全服飾データを格納
			request.setAttribute("closet", closet);

			// ユーザー画面へ遷移
			ServletContext context = this.getServletContext();
			RequestDispatcher dispatcher = context.getRequestDispatcher("/userpage.jsp");
			dispatcher.forward(request, response);
		}
		else {
			// エラーの時にセッションのユーザー情報を変更前に戻す
			session.setAttribute("id", before_id);
			session.setAttribute("pass", before_pass);
			session.setAttribute("name", before_name);

			// ユーザー情報変更画面へ遷移
			ServletContext context = this.getServletContext();
			RequestDispatcher dispatcher = context.getRequestDispatcher("/user_info_change.jsp");
			dispatcher.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public String checkEmpty(String str) {
		str = str =="" ? str = null : str.trim();
		return str;
	}
}
