
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
 * 服飾データ検索サーブレット
 */
@WebServlet("/GearSearch")
public class GearSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GearSearch() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		// 検索フォーム入力データ取得
		String g_id = checkEmpty(request.getParameter("id_search"));
		String g_type = checkEmpty(request.getParameter("type_search"));
		String g_color = null;
		if(request.getParameter("color_search") != null) {
			g_color = checkEmpty(request.getParameter("color_search"));
		}
		String g_makers = checkEmpty(request.getParameter("makers_search"));

		// セッションからユーザーIDを取得
		String u_id = (String)session.getAttribute("id");

		List<GearTable> closet = new ArrayList<GearTable>(); // 服飾データリスト
		closet = null;

		// SQL文の作成
		String sql = "SELECT id, type, color, makers, image, date_create, date_update FROM closet" + u_id;
		String where = " WHERE ";
		if (g_id != null || g_type != null || g_color != null || g_makers != null) {
			if (g_id != null) {
				where += "CAST(id AS TEXT) LIKE '%" + g_id + "%'";
				request.setAttribute("searchID", g_id);
				if(g_type != null || g_color != null || g_makers != null) {
					where += " AND ";
				}
			}
			if (g_type != null) {
				where += "type LIKE '" + g_type + "'";
				request.setAttribute("searchType", g_type);
				if(g_color != null || g_makers != null) {
					where += " AND ";
				}
			}
			if (g_color != null) {
				where += "color LIKE '%" + g_color + "%'";
				request.setAttribute("searchColor", g_color);
				if(g_makers != null) {
					where += " AND ";
				}
			}
			if (g_makers != null) {
				String checked = "";
				for(int i = 0;i < g_makers.length();i++) {
					if(g_makers.charAt(i) == '\'') {
						checked += "'";
					}
					checked += g_makers.charAt(i);
				}
				where += "makers LIKE '%" + checked + "%'";
				request.setAttribute("searchMakers", g_makers);
			}

			sql += where;
			sql += " ORDER BY id desc offset 0 limit 12";
			System.out.println(sql);
			GearData gearData = new GearData();
			session.setAttribute("where", where);

			System.out.println(sql);
			closet = gearData.getTable(sql);
		}

		// DB内の全服飾データを格納
		request.setAttribute("closet", closet);

		// ページング処理
		String sqlCount = "SELECT COUNT(id) FROM closet" + u_id + where;
		System.out.println(sqlCount);
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

		// 最大ページ数をセッションに格納
		session.setAttribute("maxPage", maxPage);
		// 初期表示を1ページ目に設定
		session.setAttribute("nowPage", 1);

		// ユーザー画面遷移
		ServletContext context = this.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/userpage.jsp");
		dispatcher.forward(request, response);
 }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	public String checkEmpty(String str) {
		str = str =="" ? str = null : str.trim();
		return str;
	}

}
