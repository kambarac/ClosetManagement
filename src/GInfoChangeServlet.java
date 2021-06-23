

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
import dto.GearTable;

/**
 * Servlet implementation class GInfoChangeServlet
 */
@WebServlet("/GInfoChangeServlet")
public class GInfoChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GInfoChangeServlet() {
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

		// SQL文の作成
		String sql = "SELECT id, type, color, makers, image, date_create, date_update FROM closet" + u_id;
		sql += " WHERE CAST(id AS TEXT) = '" + id + "'";

		// SQL文の実行、選択IDをもとにデータを取得
		List<GearTable> closet = new ArrayList<GearTable>();
		GearData gearData = new GearData();
		closet = gearData.getTable(sql);
		request.setAttribute("closet", closet);

		// 服飾データ変更画面へ遷移
		ServletContext context = this.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/change_info.jsp");
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
