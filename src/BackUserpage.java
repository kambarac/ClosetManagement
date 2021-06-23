

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
 * トップページ(ユーザーページ)遷移サーブレット
 */
@WebServlet("/BackUserpage")
public class BackUserpage extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BackUserpage() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// セッションからユーザーIDを取得
		String u_id = (String)session.getAttribute("id");

		// セッションに保持しているwhere文を削除
		session.setAttribute("where", null);

		// DB内の全服飾データを取得
		List<GearTable> closet = new ArrayList<GearTable>();
		GearData gearData = new GearData();
		String sql = "SELECT id, type, color, makers, image, date_create, date_update FROM closet" + u_id + " ORDER BY id desc offset 0 limit 12";
		closet = gearData.getTable(sql);
		request.setAttribute("closet", closet); // DB内の全服飾データを送信

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

		// 最大ページ数をセッションに格納
		session.setAttribute("maxPage", maxPage);
		// 初期表示を1ページ目に設定
		session.setAttribute("nowPage", 1);

		// ユーザー画面へ遷移
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
