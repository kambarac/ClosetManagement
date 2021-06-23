

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

import dao.ClosetInfo;
import dao.GearData;
import dto.GearTable;

/**
 * 服飾データ削除サーブレット
 */
@WebServlet("/GearInfoDelete")
public class GearInfoDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GearInfoDelete() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// セッションからユーザーIDを取得
		String u_id = (String)session.getAttribute("id");

		// 選択IDを取得
		String id = checkEmpty(String.valueOf(request.getParameter("g_id")));

		// データ削除用SQL文作成
		String sql = "DELETE FROM closet" + u_id +" WHERE id = " + id;

		// SQL文実行
		GearData gearData = new GearData();
		gearData.setData(sql);

		// ページング処理
		// 最大ページ数確認
		String sqlCount = "SELECT COUNT(id) FROM closet" + u_id;
		int maxPage = 0;
		ClosetInfo cloInfo = new ClosetInfo();
		int allCount = cloInfo.getRecordCount(sqlCount);
		if(allCount % 12 > 0) {
			maxPage = allCount / 12 + 1;
		}
		else {
			maxPage = allCount / 12;
		}
		int nowPage = (int)session.getAttribute("nowPage");
		String offset = " offset " + String.valueOf((nowPage - 1) * 12) + " limit 12";

		// DB内の表示服飾データを格納
		List<GearTable> closet = new ArrayList<GearTable>();
		sql = "SELECT id, type, color, makers, image, date_create, date_update FROM closet"+ u_id + " ORDER BY id desc" + offset;
		closet = gearData.getTable(sql);

		System.out.println(sql);

		// 最大ページ数をセッションに格納
		session.setAttribute("maxPage", maxPage);

		// ユーザー画面遷移
		ServletContext context = this.getServletContext();
		request.setAttribute("closet", closet);
		RequestDispatcher dispatcher = context.getRequestDispatcher("/userpage.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// nullチェック
	public String checkEmpty(String str) {
		str = str =="" ? str = null : str.trim();
		return str;
	}

}
