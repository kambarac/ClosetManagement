

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
import dao.ClosetInfo;
import dao.GearData;
import dto.GearTable;
import dto.UserData;

/**
 * ログイン処理サーブレット
 */
@WebServlet("/LoginSearch")
public class LoginSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginSearch() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF-8");

		// フォーム入力データ取得
		String id = request.getParameter("input_id").toLowerCase();
		String pas = request.getParameter("input_pas");

		// ユーザーID、pass確認用SQL文作成
		String sql = "SELECT id, pass, name FROM user_info WHERE id='" + id + "'" + " AND pass='" + pas +"'";

		// SQL文実行
		CheckInfo checked = new CheckInfo();
		UserData userData = null;
		userData = checked.getData(sql);

		GearData gearData = new GearData(); // 服飾データ(DB接続)
		List<GearTable> closet = new ArrayList<GearTable>(); // 服飾データリスト

		// ID,pass確認できた時
		if(userData != null) {
			// DB内の服飾データを格納
			closet = gearData.getTable("SELECT id, type, color, makers, image, date_create, date_update FROM closet" + id + " ORDER BY id desc offset 0 limit 12");
			request.setAttribute("closet", closet);

			// ページング処理
			String sqlCount = "SELECT COUNT(id) FROM closet" + userData.getId();
			ClosetInfo cloInfo = new ClosetInfo();

			// 最大ページ数確認
			int maxPage = 0;
			int allCount = cloInfo.getRecordCount(sqlCount);
			if(allCount % 12 > 0) {
				maxPage = allCount / 12 + 1;
			}
			else {
				maxPage = allCount / 12;
			}

			HttpSession session = request.getSession();
			// セッションにページ情報格納
			session.setAttribute("maxPage", maxPage);
			session.setAttribute("nowPage", 1);
			session.setAttribute("where", null);

			// セッションにユーザーデータを格納
			session.setAttribute("id", userData.getId());
			session.setAttribute("pass", userData.getPass());
			session.setAttribute("name", userData.getName());

			// ユーザー画面遷移
			ServletContext context = this.getServletContext();
			RequestDispatcher dispatcher = context.getRequestDispatcher("/userpage.jsp");
			dispatcher.forward(request, response);
		}
		// ID,pass確認できなかった時
		else {
			request.setAttribute("noData", "NG");
			ServletContext context = this.getServletContext();
			RequestDispatcher dispatcher = context.getRequestDispatcher("/login.jsp"); // ID、PASS誤入力時ログイン画面に戻る
			dispatcher.forward(request, response);
		}


    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}