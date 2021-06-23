

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

import dao.CheckInfo;
import dto.UserData;

/**
 * ユーザーID重複チェックサーブレット
 */
@WebServlet("/CheckDuplicate")
public class CheckDuplicate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckDuplicate() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// ユーザー登録画面から入力データを取得
		String id = request.getParameter("user_id").toLowerCase();
		String pass = request.getParameter("user_pass");
		String name = request.getParameter("user_name");

		// ユーザーIDの重複がないかをチェックする
		CheckInfo checked = new CheckInfo();
		UserData user = null;

		// 入力IDのユーザーデータの有無確認用SQL文作成
		String sql = "SELECT * FROM user_info WHERE id ='" + id + "'";
		// SQL文の実行、重複しない場合にnullを返す
		user = checked.duplicateCheck(sql);

		// 新規登録の場合
		if(session.getAttribute("id") == null) {

			// UserData型に値を格納
			List<UserData> entry = new ArrayList<UserData>();
			entry.add(new UserData(id,pass,name));

			// 遷移先送信データ格納メソッド
			resultSet(request,user, entry);

			ServletContext context = this.getServletContext();
			RequestDispatcher dispatcher = context.getRequestDispatcher("/new_user.jsp"); // ユーザー登録画面遷移
			dispatcher.forward(request, response);
		}
		// 既存のデータを変更する場合
		else {
			// nullの項目は既存のデータを使用
			if(id == null) {
				id = (String) session.getAttribute("id");
			}
			if(pass == null) {
				pass = (String) session.getAttribute("pass");
			}
			if(name == null) {
				name = (String) session.getAttribute("name");
			}
			List<UserData> change = new ArrayList<UserData>();
			change.add(new UserData(id,pass,name));

			// 遷移先送信データ格納メソッド
			resultSet(request, user, change);

			ServletContext context = this.getServletContext();
			RequestDispatcher dispatcher = context.getRequestDispatcher("/user_info_change.jsp"); // ユーザー情報変更画面遷移
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void resultSet(HttpServletRequest request,UserData user,List<UserData> list) {
		// IDの重複がない時
		if(user == null) {
			request.setAttribute("result", "OK"); // 結果を格納
			request.setAttribute("userdata", list); // 入力データを格納
		}
		// IDの重複があった時
		else {
			request.setAttribute("result", "NG"); // 結果を格納
			request.setAttribute("userdata", list); // 入力データを格納
		}
	}

}
