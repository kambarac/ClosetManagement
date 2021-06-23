

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserInfo;

/**
 * 新規ユーザー登録サーブレット
 */
@WebServlet("/UserEntry")
public class UserEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserEntry() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF-8");

		// フォーム入力データ取得
		String id = request.getParameter("user_id").toLowerCase();
		String pass = request.getParameter("user_pass");
		String name = request.getParameter("user_name");

		// ユーザー登録用SQL文作成
		String sql = "INSERT INTO user_info(id,pass,name) values (";
		sql += "'" + id + "',";
		sql += "'" + pass + "',";
		sql += "'" + name + "')";

		// SQL文実行
		UserInfo user = new UserInfo();
		user.setData(sql, id);

		// セッションにユーザー情報を格納
		HttpSession session = request.getSession();
		session.setAttribute("id", id);
		session.setAttribute("pass", pass);
		session.setAttribute("name", name);
		session.setAttribute("maxPage", 0);
		session.setAttribute("nowPage", 0);

		// ユーザー画面遷移
		ServletContext context = this.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/userpage.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
